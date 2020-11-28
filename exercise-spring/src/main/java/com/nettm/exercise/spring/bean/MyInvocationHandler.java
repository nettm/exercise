package com.nettm.exercise.spring.bean;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

@Slf4j
public class MyInvocationHandler implements InvocationHandler {

    private final Object target;

    public MyInvocationHandler(Object obj) {
        this.target = obj;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        log.info("Begin:" + method.getName() + ":" + System.currentTimeMillis());
        Object obj = method.invoke(target, args);
        log.info("End:" + method.getName() + ":" + System.currentTimeMillis());
        return obj;
    }
}
