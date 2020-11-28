package com.nettm.exercise.clickhouse.guice;

import com.google.inject.AbstractModule;
import com.nettm.exercise.clickhouse.guice.aop.Communicationmode;

public class BasicModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(Communicator.class).to(Communicationmode.class).asEagerSingleton();
        bind(Communication.class).toInstance(new Communication(true));
    }

}
