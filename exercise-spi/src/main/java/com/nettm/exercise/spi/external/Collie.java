package com.nettm.exercise.spi.external;

import com.nettm.exercise.spi.inner.IDog;

public class Collie implements IDog {

    @Override
    public void run() {
        System.out.println("Collie");
    }
}
