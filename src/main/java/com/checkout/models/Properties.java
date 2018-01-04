package com.checkout.models;

import java.util.ResourceBundle;

public class Properties {
    private static Properties properties;
    private final ResourceBundle resourceBundle;

    private Properties(String fileName) {
        this.resourceBundle = ResourceBundle.getBundle(fileName);
    }

    public static void createProperties(String fileName) {
        properties = new Properties(fileName);
    }

    public static Properties getProperties() {
        return properties;
    }

    public String getProperty(String propertyName) {
        return resourceBundle.getObject(propertyName).toString();
    }
}
