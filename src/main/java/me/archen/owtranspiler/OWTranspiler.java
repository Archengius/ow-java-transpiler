package me.archen.owtranspiler;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.Statement;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class OWTranspiler {

    public static void main(String[] args) throws IOException {
        JavaParser javaParser = new JavaParser();
        ParseResult<CompilationUnit> parseResult = javaParser.parse(Paths.get("Test.java"));
        if(!parseResult.isSuccessful() || !parseResult.getResult().isPresent()) {
            throw new IllegalStateException("Parsing failed: " + parseResult.getProblems().toString());
        }
        CompilationUnit compilationUnit = parseResult.getResult().get();
        TypeDeclaration<?> typeDeclaration = compilationUnit.getPrimaryType()
                .orElseThrow(() -> new IllegalArgumentException("Missing type declaration"));
        List<MethodDeclaration> entryPoints = typeDeclaration.getMethods().stream()
                .filter(it -> it.isAnnotationPresent("EventHandler"))
                .filter(it -> it.getParameters().size() == 1 && it.getBody().isPresent())
                .collect(Collectors.toList());



        entryPoints.forEach(entryPoint -> {
            //noinspection OptionalGetWithoutIsPresent
            Blo ckStmt methodBody = entryPoint.getBody().get();
            for(Statement statement : methodBody.getStatements()) {

            }
        });
    }


}
