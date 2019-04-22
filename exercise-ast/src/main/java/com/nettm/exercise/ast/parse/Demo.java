package com.nettm.exercise.ast.parse;

import java.util.*;

public class Demo {

    public static void main(String[] args) {
        String ROOT = "/Users/admin/test";
        String iface = "ITestService";
        String method = "demo";

        JavaCodeParser parser = new JavaCodeParser();
        List<String> sourceList = parser.getSourceList(ROOT);

        // 解析源码
        List<JavaClass> javaClasses = parser.parse(sourceList);

        ParseTree parseTree = new ParseTree(javaClasses);
        Optional<ParseTree.Node> root = parseTree.getCallTree(iface, method);
        if (root.isPresent()) {
            printCall(root.get(), 1);
        }

    }

    public static void printCall(ParseTree.Node node, int level) {
        List<ParseTree.Node> nodeList = node.getChildNodes();

        JavaClass javaClass = node.getJavaClass();
        Method method = node.getMethod();

        System.out.println(level + ":" + javaClass.getName() + "." + method.getName());

        if (Objects.nonNull(nodeList)) {
            level++;
            for (ParseTree.Node n : nodeList) {
                printTree(level);
                printCall(n, level);
            }
        }
    }

    private static void printTree(int level) {
        System.out.println("|");
        for (int i=0; i<level; i++) {
            System.out.print("-");
        }
    }

}
