package com.android.soundrecorder.ai.base.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/* JADX INFO: loaded from: classes.dex */
@Aspect
public class InterceptorPointHandler {
    @Pointcut("execution(@InterceptorAspect * *(..))")
    public void interceptPointcut() {
    }

    @Around("interceptPointcut()")
    public Object intercept(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        InterceptorAspect interceptorAspect = (InterceptorAspect) proceedingJoinPoint.getSignature().getMethod().getAnnotation(InterceptorAspect.class);
        if (interceptorAspect != null) {
            InterceptorChain interceptorChainBuildChain = new InterceptorChain().buildChain(interceptorAspect.interceptors());
            if (interceptorChainBuildChain.process()) {
                return interceptorChainBuildChain.interceptReturnValue();
            }
        }
        return proceedingJoinPoint.proceed();
    }
}
