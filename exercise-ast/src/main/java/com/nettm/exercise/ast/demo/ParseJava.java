package com.nettm.exercise.ast.demo;

import org.eclipse.jdt.core.compiler.IProblem;
import org.eclipse.jdt.core.dom.*;
import org.eclipse.jface.text.Document;

import java.io.File;
import java.util.List;

public class ParseJava {

    public static void main(String[] args) {
        ParseJava parse = new ParseJava();
        File file = new File(
            "/Users/tianmu/github/commons-dbcp/src/main/java/org/apache/commons/dbcp2/PoolableConnection.java");
        parse.processJavaFile(file);
    }

    public void processJavaFile(File file) {
        String source = FileUtils.readFileToString(file);
        Document document = new Document(source);
        ASTParser parser = ASTParser.newParser(AST.JLS3);
        parser.setSource(document.get().toCharArray());
        CompilationUnit unit = (CompilationUnit)parser.createAST(null);
        unit.recordModifications();

        // to get the imports from the file
        List<ImportDeclaration> imports = unit.imports();
        for (ImportDeclaration i : imports) {
            System.out.println(i.getName().getFullyQualifiedName());
        }

        // to iterate through methods
        List<AbstractTypeDeclaration> types = unit.types();
        for (AbstractTypeDeclaration type : types) {
            if (type.getNodeType() == ASTNode.TYPE_DECLARATION) {
                // Class def found
                List<BodyDeclaration> bodies = type.bodyDeclarations();
                for (BodyDeclaration body : bodies) {
                    if (body.getNodeType() == ASTNode.METHOD_DECLARATION) {
                        MethodDeclaration method = (MethodDeclaration)body;
                        System.out.println("name: " + method.getName().getFullyQualifiedName());
                    }
                }
            }
        }

        // comment
        List commecnts = unit.getCommentList();
        commecnts.forEach(System.out::println);

        // message
        Message[] messages =unit.getMessages();
        for (Message msg : messages) {
            System.out.println(msg.getMessage());
        }

        IProblem[] problems = unit.getProblems();
        for (IProblem problem : problems) {
            System.out.println(problem);
        }
    }

}
