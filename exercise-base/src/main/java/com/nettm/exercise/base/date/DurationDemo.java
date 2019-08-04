package com.nettm.exercise.base.date;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalTime;

public class DurationDemo {

    public static void main(String[] args) {
        Instant start = Instant.parse("2017-10-03T10:15:30.00Z");
        Instant end = Instant.parse("2017-10-03T10:16:30.00Z");

        Duration duration = Duration.between(start, end);
        System.out.println(duration.getSeconds());

        LocalTime start1 = LocalTime.of(1, 20, 25, 1024);
        LocalTime end1 = LocalTime.of(3, 22, 27, 1544);

        long time = Duration.between(start1, end1).getSeconds();
        System.out.println(time);
    }
}
