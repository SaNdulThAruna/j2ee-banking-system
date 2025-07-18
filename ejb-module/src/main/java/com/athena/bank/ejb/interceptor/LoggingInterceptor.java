package com.athena.bank.ejb.interceptor;

import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;

@Interceptor
public class LoggingInterceptor {

    @AroundInvoke
    public Object logInvocation(InvocationContext context) throws Exception {
        String methodName = context.getMethod().getDeclaringClass().getSimpleName() + "." + context.getMethod().getName();
        System.out.println("[LOG] Entering: " + methodName);
        long startTime = System.currentTimeMillis();

        try {
            Object result = context.proceed(); // continue to the actual method
            return result;
        } finally {
            long duration = System.currentTimeMillis() - startTime;
            System.out.println("[LOG] Exiting: " + methodName + " (Execution Time: " + duration + "ms)");
        }
    }

}
