package com.nettm.exercise.spring.cat;

import org.springframework.stereotype.Component;

@Component
public class MyCatService implements MyCat {

    @Override
    public void run() {
        System.out.println("run");
    }
}
