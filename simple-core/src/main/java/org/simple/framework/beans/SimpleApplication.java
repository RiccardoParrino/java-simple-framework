package org.simple.framework.beans;

import java.util.List;
import java.util.concurrent.Executors;

import org.simple.framework.server.SimpleServer;

public class SimpleApplication {

    public static ApplicationContext applicationContext;
    public static SimpleServer simpleServer;

    public static void start(Class<?> mainClass, String[]args) {
        SimpleApplication.applicationContext = new ApplicationContext();
        SimpleApplication.simpleServer = new SimpleServer(200, Executors.newFixedThreadPool(200));
        System.out.println("Starting Simple Application...");
        
        System.out.println("""
             ██╗ █████╗ ██╗   ██╗ █████╗      ███████╗██╗███╗   ███╗██████╗ ██╗     ███████╗
             ██║██╔══██╗██║   ██║██╔══██╗     ██╔════╝██║████╗ ████║██╔══██╗██║     ██╔════╝
             ██║███████║██║   ██║███████║     █████╗  ██║██╔████╔██║██████╔╝██║     █████╗  
        ██   ██║██╔══██║╚██╗ ██╔╝██╔══██║     ██╔══╝  ██║██║╚██╔╝██║██╔══██╗██║     ██╔══╝  
        ╚█████╔╝██║  ██║ ╚████╔╝ ██║  ██║     ██║     ██║██║ ╚═╝ ██║██████╔╝███████╗███████╗
         ╚════╝ ╚═╝  ╚═╝  ╚═══╝  ╚═╝  ╚═╝     ╚═╝     ╚═╝╚═╝     ╚═╝╚═════╝ ╚══════╝╚══════╝
        """);
        
        List<Object> beans = ComponentScanning.scan(mainClass);
        applicationContext.addAll(beans);
    }

}
