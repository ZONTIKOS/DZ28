package org.example;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class DB {
    private static final String FILE_NAME = Paths.get("src", "main", "resources", "output.txt").toString();

    public static void save(String text) {
        try {
            File file = new File(FILE_NAME);
            file.createNewFile();

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
                writer.write(text);
                writer.newLine();
            }

            System.out.println("Дані успішно записані.");
        } catch (IOException e) {
            System.out.println("Помилка при записі у файл: " + e.getMessage());
        }
    }

    public static ArrayList<String> read() {
        ArrayList<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME, StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            System.out.println("Помилка при зчитуванні файлу: " + e.getMessage());
        }
        return lines;
    }
}