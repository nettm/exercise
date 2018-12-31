package com.nettm.exercise.spring.cat;

import org.springframework.beans.factory.InitializingBean;

public class Persia implements ICat, InitializingBean {

    @Override
    public void run() {

    }

    @Override
    public boolean bathe(String water) {
        return false;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.print("afterPropertiesSet");
    }
}
