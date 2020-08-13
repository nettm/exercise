package com.nettm.exercise.clickhouse.guice;

public class DefaultCommunicatorImpl implements Communicator {

    @Override
    public boolean sendMessage(String message) {
        System.out.println(message);
        return false;
    }
}
