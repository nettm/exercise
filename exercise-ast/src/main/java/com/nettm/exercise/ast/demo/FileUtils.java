package com.nettm.exercise.ast.demo;

import lombok.experimental.UtilityClass;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@UtilityClass
public class FileUtils {

    public String readFileToString(File file) {
        Path path = Paths.get(file.getAbsolutePath());
        byte[] text = null;
        try {
            text = Files.readAllBytes(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new String(text, StandardCharsets.UTF_8);
    }

    public void writeStringToFile(File file, String text) {
        Path path = Paths.get(file.getAbsolutePath());
        byte[] b = text.getBytes(StandardCharsets.UTF_8);

        try {
            Files.write(path, b);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
