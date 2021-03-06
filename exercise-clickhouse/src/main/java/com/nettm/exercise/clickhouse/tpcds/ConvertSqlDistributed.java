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

public class ConvertSqlDistributed {

    public static void main(String[] args) {
        List<String> sourceList = readSource();
        List<String> clickhouseList = convertSql(sourceList);
        writeSql(clickhouseList);
    }

    private static List<String> readSource() {
        List<String> sourceList = new ArrayList<>(1024);
        String path = ConvertSqlDistributed.class.getResource("/tpcds.sql").getPath();
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

    private static List<String> convertSql(List<String> sourceList) {
        List<String> clickhouseList = new ArrayList<>(1024);
        String table = null;
        for (String line : sourceList) {
            if (StringUtils.isEmpty(line)) {
                clickhouseList.add(StringUtils.EMPTY);
                continue;
            }
            if (line.startsWith("--")) {
                clickhouseList.add(line);
                continue;
            }

            if (line.contains("create table")) {
                table = StringUtils.substringAfter(line, "create table").trim();
                clickhouseList.add("create table test." + table + "_all ON CLUSTER dw_cluster");
            } else if (line.equals("(")) {
                clickhouseList.add(line);
            } else if (line.contains("primary key")) {
            } else if (line.equals(");")) {
                int pos = clickhouseList.size();
                String subLine = StringUtils.substringBeforeLast(clickhouseList.get(pos - 1), ",");
                clickhouseList.set(pos - 1, subLine);

                clickhouseList.add(")");
                clickhouseList.add("ENGINE = Distributed(dw_cluster, test, " + table + ", rand());");

                // clear
                table = null;
            } else {
                String column = getColumn(line);
                String type = getColumnType(line, column);
                boolean notNull = line.contains("not null");
                clickhouseList.add("    `" + column + "` " + notNull(convertColumnType(type), notNull) + ",");
            }
        }

        return clickhouseList;
    }

    private static void writeSql(List<String> clickhouseList) {
        try {
            FileUtils.writeLines(new File("clickhouse.sql"), StandardCharsets.UTF_8.name(), clickhouseList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getColumn(String line) {
        return StringUtils.substringBetween(line, "    ", " ").trim();
    }

    private static String getColumnType(String line, String column) {
        if (line.contains("not null")) {
            return StringUtils.substringBetween(line, column, "not null").trim();
        } else {
            String type = StringUtils.substringAfter(line, column);
            return StringUtils.substringBeforeLast(type, ",").trim();
        }
    }

    private static String convertColumnType(String type) {
        if (type.equalsIgnoreCase("integer")) {
            return "Int32";
        }
        if (type.equalsIgnoreCase("date")) {
            return "Date";
        }
        if (type.equalsIgnoreCase("time")) {
            return "DateTime";
        }
        if (type.startsWith("char")) {
            return type.replace("char", "FixedString");
        }
        if (type.startsWith("varchar")) {
            return "String";
        }
        if (type.startsWith("decimal")) {
            return type.replace("decimal", "Decimal");
        }

        throw new UnsupportedOperationException(type);
    }

    private static String notNull(String type, boolean notNull) {
        if (notNull) {
            return type;
        } else {
            return "Nullable(" + type + ")";
        }
    }

}
