package com.chanmul.client.redis.annotaion;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DistributedLock {
	String key(); // (1) key: 락의 이름
	TimeUnit timeUnit() default TimeUnit.SECONDS; // (2) timeUnit: 시간 단위(MILLISECONDS, SECONDS, MINUTE..)
	long waitTime() default 5L; // (3) waitTime: 락을 획득하기 위한 대기 시간
	long leaseTime() default 3L; // (4) leaseTime: 락을 임대하는 시간
}
