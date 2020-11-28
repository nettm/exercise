package com.nettm.exercise.clickhouse.guice.aop;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.nettm.exercise.clickhouse.guice.BasicModule;
import com.nettm.exercise.clickhouse.guice.Communication;
import com.nettm.exercise.clickhouse.guice.Communicator;

import java.util.logging.Logger;

public class Communicationmode implements Communicator {

    @Inject
    private Logger logger;

    @Override
    @MessageSentLoggable
    public boolean sendMessage(String message) {
        logger.info("SMS message sent");
        return true;
    }

    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new BasicModule(), new AOPModule());
        Communication comms = injector.getInstance(Communication.class);
        comms.sendMessage("ok");
    }
}
