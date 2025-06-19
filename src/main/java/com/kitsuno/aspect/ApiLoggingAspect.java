package com.kitsuno.aspect;

import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.kitsuno.service.UserService;

import java.time.LocalDateTime;


@Aspect
@Component
public class ApiLoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger("KitsunoAPI");

    private final UserService userService;

    public ApiLoggingAspect(UserService userService) {
        this.userService = userService;
    }

    @Pointcut("execution(* com.kitsuno.rest..*(..))")
    public void restControllerMethods() {}

    @Around("restControllerMethods()")
    public Object logApiCall(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();

        Object result = joinPoint.proceed();

        long duration = System.currentTimeMillis() - start;
        String method = joinPoint.getSignature().toShortString();
        String dateTime = LocalDateTime.now().toString();

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        String apiKey = request.getHeader("API-KEY");
        String email = userService.findByApiKey(apiKey).get().getEmail();

        logger.info("Email: {} | DateTime: {} | API Call: {} | Execution time: {} ms",
                email, dateTime, method, duration);

        return result;
    }
}
