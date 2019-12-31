package com.wulang.redis.utils.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class BeanFactoryUtils implements ApplicationContextAware {
	private static ApplicationContext context;

	@SuppressWarnings("unchecked")
	public static <T> T getInstance(Class<T> t) {
		return context.getBean(t);
	}

	@SuppressWarnings("unchecked")
	public static <T> T getInstance(String name,Class<T> t) {
		return context.getBean(name,t);
	}
	@SuppressWarnings("unchecked")
	public static <T> T getInstance(String name) {
		return (T) context.getBean(name);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		BeanFactoryUtils.context = applicationContext;
	}

	public static ApplicationContext getContext(){
		return BeanFactoryUtils.context;
	}

	/**
	 * 获取当前环境
	 * @return {@link String}
	 */
	public static String getActiveProfile() {
		return context.getEnvironment().getActiveProfiles()[0];
	}
}
