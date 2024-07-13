package com.chanmul.core.util;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class StaticContextAccessUtil {
	private static StaticContextAccessUtil instance;

	@Autowired
	private ApplicationContext applicationContext;

	@PostConstruct
	public void registerInstance() {
		instance = this;
	}

	public static <T> T getBean(Class<T> clazz) {
		return instance.applicationContext.getBean(clazz);
	}

	public static Object getBean(String beanName) {
		// Tiger tiger = (Tiger) context.getBean("lion");
		return instance.applicationContext.getBean(beanName);
	}
}
