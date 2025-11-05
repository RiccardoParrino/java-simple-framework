package org.simple.framework.beans;

import java.util.List;

public class SimpleApplication {

    public static ApplicationContext applicationContext;

    public static void start(Class<?> mainClass, String[]args) {
        SimpleApplication.applicationContext = new ApplicationContext();
        System.out.println("Starting Simple Application...");
        List<Object> beans = ComponentScanning.scan(mainClass);
        applicationContext.addAll(beans);
    }

}
