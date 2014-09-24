package org.question.dao.users;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserDaoWithTimeLogger implements InvocationHandler {
	private static final Logger logger = LoggerFactory.getLogger(UserDaoWithTimeLogger.class);
	
	Object target;
	
	public UserDaoWithTimeLogger(Object target) {
		this.target = target;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		long time = System.currentTimeMillis();
		
		Object ret = method.invoke(target, args);
		
		time = System.currentTimeMillis() - time;
		logger.debug("TIME: {}", time);
		
		return ret;
	}

}
