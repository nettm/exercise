package com.nettm.exercise.clickhouse.guice;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class Run {

    public static void main(String[] args){
        Injector injector = Guice.createInjector(new BasicModule());
        Communication comms = injector.getInstance(Communication.class);
        comms.sendMessage("ok");
    }
}
