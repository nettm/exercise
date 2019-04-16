package com.nettm.exercise.ast.demo;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.printer.DotPrinter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import static com.github.javaparser.ast.Modifier.Keyword.*;

public class JavaParser {

    public static void main(String[] args) throws IOException {
        JavaParser parser = new JavaParser();
        parser.parsing();
        parser.printATS();
        parser.generate();
    }

    private void parsing() {
        CompilationUnit compilationUnit = StaticJavaParser.parse(getSource());

        List<MethodDeclaration> methods = compilationUnit.findAll(MethodDeclaration.class);
        for (MethodDeclaration method : methods) {
            BlockStmt stmt = method.getBody().get();
            System.out.println(stmt);
        }

        List<FieldDeclaration> fields = compilationUnit.findAll(FieldDeclaration.class);
        for (FieldDeclaration field : fields) {
            System.out.println(field);
        }

        compilationUnit.findAll(FieldDeclaration.class).stream()
            .filter(f -> f.isPublic() && !f.isStatic())
            .forEach(f -> System.out.println("Check field at line " +
                f.getRange().map(r -> r.begin.line).orElse(-1)));

        compilationUnit.findAll(ClassOrInterfaceDeclaration.class).stream()
            .filter(c -> !c.isInterface()
                && c.isAbstract()
                && !c.getNameAsString().startsWith("Abstract"))
            .forEach(c -> {
                String oldName = c.getNameAsString();
                String newName = "Abstract" + oldName;
                System.out.println("Renaming class " + oldName + " into " + newName);
                c.setName(newName);
            });

        compilationUnit.accept(new MethodVisitor(), null);
    }

    private void generate() {
        CompilationUnit compilationUnit = new CompilationUnit();
        ClassOrInterfaceDeclaration myClass = compilationUnit
            .addClass("MyClass")
            .setPublic(true);
        myClass.addField(int.class, "A_CONSTANT", PUBLIC, STATIC);
        myClass.addField(String.class, "name", PRIVATE);
        String code = myClass.toString();
        System.out.println(code);
    }

    private void printATS() throws IOException {
        CompilationUnit cu = StaticJavaParser.parse(getSource());

        DotPrinter printer = new DotPrinter(true);
        try (FileWriter fileWriter = new FileWriter("ast.dot");
             PrintWriter printWriter = new PrintWriter(fileWriter)) {
            printWriter.print(printer.output(cu));
        }
    }

    private String getSource() {
        File file = new File(
            "/Users/tianmu/github/commons-dbcp/src/main/java/org/apache/commons/dbcp2/PoolableConnection.java");
        return FileUtils.readFileToString(file);
    }

    static class MethodVisitor extends VoidVisitorAdapter<Void> {

        @Override
        public void visit(MethodDeclaration n, Void arg) {
            System.out.println("method:" + n.getName());
            super.visit(n, arg);
        }

        @Override
        public void visit(ClassOrInterfaceDeclaration n, Void arg) {
            System.out.println("class:" + n.getName());
            System.out.println("extends:" + n.getExtendedTypes());
            System.out.println("implements:" + n.getImplementedTypes());

            super.visit(n, arg);
        }

        @Override
        public void visit(PackageDeclaration n, Void arg) {
            System.out.println("package:" + n.getName());
            super.visit(n, arg);
        }

    }

}
