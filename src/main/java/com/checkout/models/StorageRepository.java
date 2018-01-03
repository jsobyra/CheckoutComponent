package com.checkout.models;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.stream.Stream;


public class StorageRepository {
    private static StorageRepository storageRepository = new StorageRepository();
    private final HashMap<String, Item> items = new HashMap<>();
    private final String storageFile = "products.csv";

    private StorageRepository() {
        ClassLoader classLoader = getClass().getClassLoader();
        String path  = classLoader.getResource(storageFile).getPath();

        try (Stream<String> lines = Files.lines(Paths.get(path)).skip(1)) {
            lines.forEach(line -> addItem(parseLine(line)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static StorageRepository getStorage() {
        return storageRepository;
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
