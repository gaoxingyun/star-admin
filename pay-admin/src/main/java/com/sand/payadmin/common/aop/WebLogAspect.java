package com.sand.payadmin.common.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * controller日志切面类
 **/
@Aspect
@Component
@Order(1)
public class WebLogAspect {

	private Logger log = LoggerFactory.getLogger(WebLogAspect.class);

	private ThreadLocal<Long> startTime = new ThreadLocal<>();

	@Pointcut("execution(public * com.sand.payadmin.controller..*.*(..))")
	public void webLog() {
	}

	@Before("webLog()")
	public void doBefore(JoinPoint joinPoint) throws Throwable {
		startTime.set(System.currentTimeMillis());
		// 接收到请求，记录请求内容
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();

		// 记录下请求内容
		log.info("URL : {}", request.getRequestURL().toString());
		log.info("HTTP_METHOD : {}", request.getMethod());
		log.info("HTTP_HEAD_ACCEPT : {}", request.getHeader("accept"));
		log.info("HTTP_HEAD_CONTENTTYPE : {}", request.getHeader("content-type"));
		log.info("IP : {}", request.getRemoteAddr());
		log.info("CLASS_METHOD : {}.{}", joinPoint.getSignature().getDeclaringTypeName(),
				joinPoint.getSignature().getName());
		log.info("ARGS : {}", Arrays.toString(joinPoint.getArgs()));

	}

	@AfterReturning(returning = "ret", pointcut = "webLog()")
	public void doAfterReturning(Object ret) throws Throwable {
		// 处理完请求，返回内容
		log.info("RESPONSE : {}", ret);
		log.info("SPEND TIME : {}", (System.currentTimeMillis() - startTime.get()));
	}

}