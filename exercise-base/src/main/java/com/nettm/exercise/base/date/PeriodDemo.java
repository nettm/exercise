package com.nettm.exercise.base.date;

import java.time.LocalDate;
import java.time.Period;

public class PeriodDemo {

    public static void main(String[] args) {
        LocalDate start = LocalDate.of(2019, 7, 19);
        LocalDate end = LocalDate.of(2019, 8, 23);
        Period period = Period.between(start, end);
        System.out.println(period.getYears() + ":" + period.getMonths() + ":" + period.getDays());

        Period fromCharYears = Period.parse("P2Y");
        System.out.println(fromCharYears.getYears() + ":" + fromCharYears.getMonths() + ":" + fromCharYears.getDays());

        Period fromCharUnits = Period.parse("P2Y3M5D");
        System.out.println(fromCharUnits.getYears() + ":" + fromCharUnits.getMonths() + ":" + fromCharUnits.getDays());
    }
}
