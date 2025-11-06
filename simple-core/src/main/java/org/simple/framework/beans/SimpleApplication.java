package org.simple.framework.beans;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;

import org.simple.framework.server.SimpleServer;

import com.parrino.riccardo.Tuple;

public class SimpleApplication {

    public static ApplicationContext applicationContext;
    public static SimpleServer simpleServer;

    public static void start(Class<?> mainClass, String[]args) {
        System.out.println("\n");
        System.out.println("""
             ██╗ █████╗ ██╗   ██╗ █████╗      █████╗ ███████╗██████╗  ██████╗ 
             ██║██╔══██╗██║   ██║██╔══██╗    ██╔══██╗██╔════╝██╔══██╗██╔═══██╗
             ██║███████║██║   ██║███████║    ███████║█████╗  ██████╔╝██║   ██║
        ██   ██║██╔══██║╚██╗ ██╔╝██╔══██║    ██╔══██║██╔══╝  ██╔══██╗██║   ██║
        ╚█████╔╝██║  ██║ ╚████╔╝ ██║  ██║    ██║  ██║███████╗██║  ██║╚██████╔╝
        ╚════╝ ╚═╝  ╚═╝  ╚═══╝  ╚═╝  ╚═╝    ╚═╝  ╚═╝╚══════╝╚═╝  ╚═╝ ╚═════╝ 
                            Arts from: https://patorjk.com/
        """);

        System.out.println("\nStarting Simple Application...");
        SimpleApplication.applicationContext = new ApplicationContext();

        System.out.println("Building application context");
        List<Object> beans = ComponentScanning.scan(mainClass);
        applicationContext.addAll(beans);

        System.out.println("Loading rest controller");
        Map<String, Tuple<Method,Object>> endpoints = RestControllerDiscoverer.find(applicationContext);

        System.out.println("Starting server");
        SimpleApplication.simpleServer = new SimpleServer(8080, Executors.newFixedThreadPool(200), endpoints);
        try {
            SimpleApplication.simpleServer.start();
        } catch (Exception e) {}
    }

}
