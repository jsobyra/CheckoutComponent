package com.checkout.models;

import org.springframework.stereotype.Component;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.stream.Stream;

@Component
public class StorageRepository {
    private final HashMap<String, Item> items = new HashMap<>();
    private final String storageFile = Properties.getProperties().getProperty("storageFile");

    private StorageRepository() {
        ClassLoader classLoader = getClass().getClassLoader();
        String path  = classLoader.getResource(storageFile).getPath();

        try (Stream<String> lines = Files.lines(Paths.get(path)).skip(1)) {
            lines.forEach(line -> addItem(parseLine(line)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addItem(Item item) {
        items.put(item.getName(), item);
    }

    private Item parseLine(String line) {
        String[] parameters = line.split(",");
        return new Item(
                parameters[0],
                Integer.valueOf(parameters[1]),
                Integer.valueOf(parameters[2]),
                Integer.valueOf(parameters[3])
        );
    }

    public Item getItem(String name) {
        return items.get(name);
    }
}
