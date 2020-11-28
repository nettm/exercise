package com.nettm.exercise.clickhouse.tpcds;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DropTableSql {

    public static void main(String[] args) {
        List<String> sourceList = readSource();
        convertSql(sourceList);
    }

    private static List<String> readSource() {
        List<String> sourceList = new ArrayList<>(1024);
        String path = DropTableSql.class.getResource("/tpcds.sql").getPath();
        System.out.println("Source file is:" + path);
        try(Scanner scanner = new Scanner(new File(path))) {
            while (scanner.hasNextLine()) {
                sourceList.add(scanner.nextLine());
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }

        return sourceList;
    }

    private static void convertSql(List<String> sourceList) {
        for (String line : sourceList) {
            if (StringUtils.isEmpty(line)) {
                continue;
            }
            if (line.startsWith("--")) {
                continue;
            }

            if (line.contains("create table")) {
                String table = StringUtils.substringAfter(line, "create table").trim();
                System.out.println(("truncate table test." + table + " ON CLUSTER dw_cluster;"));
            } else if (line.equals("(")) {
            } else if (line.contains("primary key")) {
            } else if (line.equals(");")) {
            } else {
            }
        }
    }

}
