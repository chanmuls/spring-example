package com.chanmul.client.redis.aspect;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.chanmul.client.redis.annotaion.DistributedLock;
import com.chanmul.client.redis.util.CustomSpringELParser;
import com.chanmul.core.exception.ApiException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class DistributedLockAop {
	private static final String REDISSON_KEY_PREFIX = "RLOCK:";
	private final RedissonClient redissonClient;

	@Around("@annotation(com.chanmul.client.redis.annotaion.DistributedLock)")
	public Object lock(final ProceedingJoinPoint joinPoint) throws Throwable {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();

		// (1) @DistributedLock annotation을 가져옴
		DistributedLock distributedLock = method.getAnnotation(DistributedLock.class);

		// (2) @DistributeLock에 전달한 key를 가져오기 위해 SpringEL 표현식을 파싱
		String key = REDISSON_KEY_PREFIX + CustomSpringELParser.getDynamicValue(signature.getParameterNames(),
				joinPoint.getArgs(), distributedLock.key());

		// (3)  Redisson에 해당 락의 RLock 인터페이스를 가져옴
		RLock rLock = redissonClient.getLock(key);

		try {
			// (4) tryLock method를 이용해 Lock 획득을 시도 (획득 실패시 Lock이 해제 될 때까지 subscribe)
			boolean available = rLock.tryLock(distributedLock.waitTime(), distributedLock.leaseTime(), distributedLock.timeUnit());
			if (!available) {
				return false;
			}

			log.info("get lock success {}" , key);

			// (5) @DistributeLock이 선언된 메소드의 로직 수행(별도 트랜잭션으로 분리)
			return joinPoint.proceed();
		} catch (ApiException e) {
			log.error("get lock fail ApiException {}, {}" , key, e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			log.error("get lock fail {}, {}" , key, e.getMessage(), e);
			throw new InterruptedException();
		} finally {
			log.info("get unlock success {}" , key);
			if(rLock.isHeldByCurrentThread()) {
				rLock.unlock();
			}
			// (6) 종료 혹은 예외 발생시 finally에서 Lock을 해제함
		}
	}
}