package com.nettm.exercise.ast.parse;

import lombok.Getter;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * 调用树
 */
public class ParseTree {

    private List<JavaClass> javaClassList;

    public ParseTree(List<JavaClass> javaClassList) {
        this.javaClassList = javaClassList;
    }

    public Optional<Node> getCallTree(String iface, String method) {
        System.out.println("输出调用树开始");
        Optional<JavaClass> root = findJavaClass(iface);
        if (root.isPresent()) {
            JavaClass javaClass = root.get();
            Optional<Method> m = findMethod(javaClass, method);
            if (m.isPresent()) {
                Node node = new Node(javaClass, m.get());
                findChild(node);
                System.out.println("输出调用树结束");
                return Optional.of(node);
            }
        }

        return Optional.empty();
    }

    private void findChild(Node node) {
        Method method = null;
        JavaClass javaClass = node.getJavaClass();
        if (javaClass.getIsInterface()) {
            Optional<JavaClass> optional = findImplClass(javaClass);
            if (optional.isPresent()) {
                Optional<Method> m = findMethod(optional.get(), node.getMethod().getName());
                if (m.isPresent()) {
                    method = m.get();
                }
            }
        } else {
            method = node.getMethod();
        }

        if (Objects.isNull(method)) {
            return;
        }

        List<MethodCall> callList = method.getMethodCallList();
        for (MethodCall call : callList) {
            Optional<JavaClass> jc = findJavaClass(call.getType());
            if (jc.isPresent()) {
                Optional<Method> m = findMethod(jc.get(), call.getName());
                if (m.isPresent()) {
                    Node n = new Node(jc.get(), m.get());
                    node.addChild(n);
                    findChild(n);
                }
            }
        }
    }

    private Optional<JavaClass> findJavaClass(String iface) {
        for (JavaClass jc : this.javaClassList) {
            System.out.println(jc);
            if (jc.getName().equals(iface) || jc.getFullName().equals(iface)) {
                return Optional.of(jc);
            }
        }

        return Optional.empty();
    }

    private Optional<Method> findMethod(JavaClass javaClass, String method) {
        List<Method> methodList = javaClass.getMethodList();
        for (Method m : methodList) {
            if (m.getName().equals(method)) {
                return Optional.of(m);
            }
        }

        String parent = javaClass.getParent();
        if (Objects.nonNull(parent)) {
            Optional<JavaClass> optional = findJavaClass(parent);
            if (optional.isPresent()) {
                return findMethod(optional.get(), method);
            }
        }

        return Optional.empty();
    }

    private Optional<JavaClass> findImplClass(JavaClass javaClass) {
        if (javaClass.getIsInterface()) {
            for (JavaClass jc : javaClassList) {
                if (jc.getIsInterface()) {
                    continue;
                }

                List<String> ifaces = jc.getInterfaceList();
                if (ifaces.contains(javaClass.getFullName())) {
                    return Optional.of(jc);
                }

            }
        }

        return Optional.empty();
    }

    static class Node {

        @Getter
        private List<Node> childNodes;

        @Getter
        private JavaClass javaClass;

        @Getter
        private Method method;

        public Node(JavaClass javaClass, Method method) {
            this.javaClass = javaClass;
            this.method = method;
        }

        public boolean hasChild() {
            return Objects.nonNull(childNodes) && !childNodes.isEmpty();
        }

        public void addChild(Node node) {
            if (Objects.isNull(childNodes)) {
                childNodes = new LinkedList<>();
            }
            childNodes.add(node);
        }

        public void addChild(JavaClass javaClass, Method method) {
            if (Objects.isNull(childNodes)) {
                childNodes = new LinkedList<>();
            }
            childNodes.add(new Node(javaClass, method));
        }

    }

}
