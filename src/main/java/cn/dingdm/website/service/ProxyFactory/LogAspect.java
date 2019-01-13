package cn.dingdm.website.service.ProxyFactory;

import cn.dingdm.website.entities.BaseEntity;
import org.aspectj.lang.Aspects;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Aspect
@Configuration
public class LogAspect {
/*
    @Autowired
    RedisTemplate redisTemplate;

    @Pointcut("execution(public * cn.dingdm.website.service.*.*(..))")
    public void webRedis(){}

    @Around("webRedis()")
    public void around(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("你在想屁吃！");
        joinPoint.proceed();
        System.out.println("你在想屁吃！");
    }*/

}
