package org.simple.framework.beans;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;

import org.simple.framework.server.SimpleServer;

public class SimpleApplication {

    public static ApplicationContext applicationContext;
    public static SimpleServer simpleServer;

    public static void start(Class<?> mainClass, String[]args) {
        System.out.println("\n");
        System.out.println("""
             ██╗ █████╗ ██╗   ██╗ █████╗      ███████╗██╗███╗   ███╗██████╗ ██╗     ███████╗
             ██║██╔══██╗██║   ██║██╔══██╗     ██╔════╝██║████╗ ████║██╔══██╗██║     ██╔════╝
             ██║███████║██║   ██║███████║     █████╗  ██║██╔████╔██║██████╔╝██║     █████╗  
        ██   ██║██╔══██║╚██╗ ██╔╝██╔══██║     ██╔══╝  ██║██║╚██╔╝██║██╔══██╗██║     ██╔══╝  
        ╚█████╔╝██║  ██║ ╚████╔╝ ██║  ██║     ██║     ██║██║ ╚═╝ ██║██████╔╝███████╗███████╗
         ╚════╝ ╚═╝  ╚═╝  ╚═══╝  ╚═╝  ╚═╝     ╚═╝     ╚═╝╚═╝     ╚═╝╚═════╝ ╚══════╝╚══════╝
        """);
        System.out.println("\nStarting Simple Application...");
        SimpleApplication.applicationContext = new ApplicationContext();
        List<Object> beans = ComponentScanning.scan(mainClass);
        applicationContext.addAll(beans);
        Map<String, Method> endpoints = RestControllerDiscoverer.find(applicationContext);
        SimpleApplication.simpleServer = new SimpleServer(8080, Executors.newFixedThreadPool(200), endpoints);
        try {
            SimpleApplication.simpleServer.start();
        } catch (Exception e) {}
    }

}
