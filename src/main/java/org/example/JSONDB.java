package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class JSONDB {
    private static final String FILE_NAME = Paths.get("src", "main", "resources", "logs.json").toString();
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static void save(String text) {
        List<String> logs = read();
        logs.add(text);

        try (Writer writer = new FileWriter(FILE_NAME, StandardCharsets.UTF_8)) {
            gson.toJson(logs, writer);
        } catch (IOException e) {
            System.out.println("Помилка при записі у JSON файл: " + e.getMessage());
        }
    }

    public static List<String> read() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            return new ArrayList<>();
        }

        try (Reader reader = new FileReader(FILE_NAME, StandardCharsets.UTF_8)) {
            return gson.fromJson(reader, new TypeToken<List<String>>() {}.getType());
        } catch (IOException e) {
            System.out.println("Помилка при зчитуванні JSON файлу: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}
