package com.nettm.exercise.ast.parse;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 描述
 */
public class JavaCodeParser {

    private static final String ROOT = "/Users/tianmu/gitlab/saas-tnt";

    private Map<String, JavaClass> javaClassMap = new HashMap<>();

    public static void main(String[] args) {
        JavaCodeParser parser = new JavaCodeParser();
        List<String> sourceList = parser.scanJavaSource(ROOT, new ArrayList<>());
        sourceList = parser.filter(sourceList);

        // 解析源码
        Map<String, JavaClass> map = parser.parse(sourceList);
        map.forEach((k, v) -> System.out.println(k));
    }

    private Map<String, JavaClass> parse(List<String> sourceList) {
        Map<String, JavaClass> map = new HashMap<>(sourceList.size());

        for (String source : sourceList) {
            JavaClass javaClass = new JavaClass();
            javaClass.setSource(source);

            ParseVisitorAdapter adapter = new ParseVisitorAdapter(javaClass);
            try {
                CompilationUnit unit = StaticJavaParser.parse(new File(source));
                adapter.visit(unit, null);
                map.put(javaClass.getFullName(), javaClass);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        return map;
    }

    private List<String> filter(List<String> sourceList) {
        return sourceList.stream()
            .filter(s -> !s.contains("Test"))
            .filter(s -> !s.contains("test"))
            .filter(s -> !s.endsWith("Enum.java"))
            .filter(s -> !s.endsWith("Vo.java"))
            .filter(s -> !s.endsWith("Query.java"))
            .filter(s -> !s.endsWith("Dto.java"))
            .collect(Collectors.toList());
    }

    private List<String> scanJavaSource(String root, List<String> sourceList) {
        File dir = new File(root);
        if (!dir.exists()) {
            System.out.println("目录不存在");
            return sourceList;
        }

        if (dir.isDirectory()) {
            File[] files = dir.listFiles();
            if (Objects.nonNull(files)) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        scanJavaSource(file.getAbsolutePath(), sourceList);
                    } else if (file.getName().endsWith("java")) {
                        sourceList.add(file.getAbsolutePath());
                    }
                }
            }
        } else if (dir.getName().endsWith("java")) {
            sourceList.add(dir.getAbsolutePath());
        }

        return sourceList;
    }

}
