package com.mstorn.platform.order.infrastructure.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger log = LoggerFactory.getLogger(LoggingAspect.class);

    @Around("execution(* com.mstorn.platform.order..*(..)) && " +
            "(@within(org.springframework.stereotype.Service) || " +
            "@within(org.springframework.web.bind.annotation.RestController))")
    public Object logMethodExecution(ProceedingJoinPoint joinPoint) throws Throwable {

        String className = joinPoint.getSignature().getDeclaringType().getSimpleName();
        String methodName = joinPoint.getSignature().getName();

        long start = System.currentTimeMillis();

        log.info("▶️ {}.{} started", className, methodName);

        try {
            Object result = joinPoint.proceed();
            long duration = System.currentTimeMillis() - start;

            log.info("⏹️ {}.{} finished in {} ms", className, methodName, duration);

            return result;

        } catch (Throwable ex) {
            long duration = System.currentTimeMillis() - start;

            log.error("❌ {}.{} failed after {} ms", className, methodName, duration, ex);
            throw ex;
        }
    }
}