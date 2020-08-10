package sningning.community.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

/**
 * @author: Song Ningning
 * @date: 2020-08-10 23:19
 */
// @Component
// @Aspect
public class AlphaAspect {

    /**
     * 定义 Pointcut
     */
    @Pointcut("execution(* sningning.community.service.*.*(..))")
    public void pointcut() {

    }

    /**
     * 定义 Advice
     */
    @Before("pointcut()")
    public void before() {
        System.out.println("before");
    }

    @After("pointcut()")
    public void after() {
        System.out.println("after");
    }

    @AfterReturning("pointcut()")
    public void AfterReturning() {
        System.out.println("AfterReturning");
    }

    @AfterThrowing("pointcut()")
    public void AfterThrowing() {
        System.out.println("AfterThrowing");
    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("Around before");
        Object obj = joinPoint.proceed();
        System.out.println("Around after");
        return obj;
    }
}
