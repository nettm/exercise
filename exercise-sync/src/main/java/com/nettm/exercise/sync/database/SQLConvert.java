package com.nettm.exercise.sync.database;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class SQLConvert {

    private static final String CONFIG_FILE = "source_table.xml";

    private static final String SOURCE_CSV_FILE = "source.csv";

    private static final String COMMA_DELIMITER = ",";

    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        MetadataConfig config = new MetadataConfig(CONFIG_FILE);
        Table table = config.getTable();

        List<Column> sourceList = config.getSourceColumns();
        List<Column> targetList = config.getTargetColumns();

        SQLConvert convert = new SQLConvert();
        List<List<Column>> sourceValueList = convert.getSourceValue(sourceList);
        List<String> sqlList = convert.generateSQL(table.getTarget(), sourceValueList, targetList);
        convert.writeSqlFile(table.getTarget(), sqlList);
    }

    private void writeSqlFile(String table, List<String> sqlList) {
        URL url = SQLConvert.class.getClassLoader().getResource(".");
        File file = new File(url.getPath() + table + ".sql");
        System.out.println("SQL文件:" + file.getPath());
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            for (String sql : sqlList) {
                bw.write(sql + "\r\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<String> generateSQL(String targetTable, List<List<Column>> sourceValueList, List<Column> targetList) {
        List<String> sqlList = new ArrayList<>(100);
        for (List<Column> columns : sourceValueList) {
            Map<String, Column> map = columns.stream().collect(Collectors.toMap(Column::getTarget, item -> item));
            StringBuilder buf = new StringBuilder(512);
            buf.append("insert into ");
            buf.append(targetTable);
            buf.append("(");

            // 列名
            targetList.forEach(s -> {
                buf.append(s.getTarget());
                buf.append(", ");
            });
            buf.delete(buf.length() - 2, buf.length());

            // 列值
            buf.append(") values (");
            targetList.forEach(s -> {
                buf.append(convertValueType(map.get(s.getTarget()), s));
                buf.append(", ");
            });
            buf.delete(buf.length() - 2, buf.length());
            buf.append(");");

            sqlList.add(buf.toString());
        }

        return sqlList;
    }

    private String convertValueType(Column source, Column target) {
        Column column;
        if (Objects.isNull(source)) {
            column = target;
        } else {
            column = source;
        }

        String targetType = column.getTargetType();
        String val = column.getValue();
        if (Objects.isNull(val) && Objects.nonNull(column.getDefaultValue())) {
            val = column.getDefaultValue();
        }

        String newValue;
        switch (targetType) {
            case "bigint":
            case "smallint":
            case "tinyint":
            case "decimal":
                newValue = val;
                break;
            case "varchar":
            case "text":
                newValue = "'" + val + "'";
                break;
            case "timestamp":
            case "datetime":
            case "date":
            case "time":
                newValue = "'" + val + "'";
                break;
            default:
                newValue = val;
                break;
        }

        return newValue;
    }

    private List<List<Column>> getSourceValue(List<Column> sourceList)
        throws InvocationTargetException, IllegalAccessException {
        List<List<Column>> sources = new ArrayList<>();

        List<List<String>> records = readCsv();
        for (List<String> columns : records) {
            if (sourceList.size() != columns.size()) {
                throw new RuntimeException("列数不匹配:" + sourceList.size() + ":" + columns.size());
            }

            List<Column> rows = new ArrayList<>();
            for (int i = 0; i < sourceList.size(); i++) {
                Column c = new Column();
                BeanUtils.copyProperties(c, sourceList.get(i));
                String val = columns.get(i);
                if ("\"\"".equals(val)) {
                    val = "";
                }
                c.setValue(val);
                rows.add(c);
            }
            sources.add(rows);
        }

        return sources;
    }

    private List<List<String>> readCsv() {
        List<List<String>> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(
            new InputStreamReader(Metadata.class.getResourceAsStream("/" + SOURCE_CSV_FILE)))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = StringUtils.splitPreserveAllTokens(line, COMMA_DELIMITER);
                records.add(Arrays.asList(values));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return records;
    }

}
