package org.simple.framework.beans;

public class SimpleApplication {

    public static ApplicationContext applicationContext;

    public static void start(Class<?> mainClass, String[]args) {
        SimpleApplication.applicationContext = new ApplicationContext();
        System.out.println("Starting Simple Application...");
        ComponentScanning.scan(mainClass);
    }

}
