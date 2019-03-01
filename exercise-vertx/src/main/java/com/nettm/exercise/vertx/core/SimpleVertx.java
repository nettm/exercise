package com.nettm.exercise.vertx.core;

import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;

/**
 * 描述
 *
 * @author mu.tian
 * @date 2019-03-01 09:37
 */
public class SimpleVertx {

    public static void main(String[] args) {
        VertxOptions options = new VertxOptions().setWorkerPoolSize(40);
        Vertx vertx = Vertx.vertx(options);

        vertx.setPeriodic(1000, id -> {
            System.out.println("timer fired!");
        });

        while (true) {

        }

    }

}
