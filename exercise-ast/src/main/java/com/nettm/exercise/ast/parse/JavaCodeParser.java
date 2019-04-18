package com.nettm.exercise.ast.parse;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.visitor.VoidVisitor;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 描述
 */
public class JavaCodeParser {

    private static final String ROOT = "/Users/admin/test";

    public static void main(String[] args) {
        JavaCodeParser parser = new JavaCodeParser();
        List<String> sourceList = parser.getSourceList(ROOT);
        System.out.println("一共发现[" + sourceList.size() + "]个类");

        // 解析源码
        List<JavaClass> javaClasses = parser.parse(sourceList);
        javaClasses.forEach(s -> System.out.println(s));
    }

    public List<String> getSourceList(String root) {
        List<String> sourceList = scanJavaSource(root, new ArrayList<>());
        return filter(sourceList);
    }

    public List<JavaClass> parse(List<String> sourceList) {
        System.out.println("解析开始：" + sourceList.size());

        List<JavaClass> javaClasses = new ArrayList<>(sourceList.size());
        for (String source : sourceList) {
            JavaClass javaClass = new JavaClass();
            javaClass.setSource(source);

            VoidVisitor adapter = new ParseVisitorAdapter(javaClass);
            try {
                CompilationUnit unit = StaticJavaParser.parse(new File(source));
                adapter.visit(unit, null);
                javaClasses.add(javaClass);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        System.out.println("解析结束");
        return javaClasses;
    }

    private List<String> filter(List<String> sourceList) {
        return sourceList.stream()
            .filter(s -> !s.contains("Test"))
            .filter(s -> !s.contains("test"))
            .filter(s -> !s.endsWith("Enum.java"))
            .filter(s -> !s.endsWith("Vo.java"))
            .filter(s -> !s.endsWith("VO.java"))
            .filter(s -> !s.endsWith("Query.java"))
            .filter(s -> !s.endsWith("Dto.java"))
            .filter(s -> !s.endsWith("DTO.java"))
            .filter(s -> !s.contains("package-info"))
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
