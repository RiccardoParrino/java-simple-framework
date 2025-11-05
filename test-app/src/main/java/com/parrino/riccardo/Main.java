package com.parrino.riccardo;

import java.util.Scanner;

import org.simple.framework.beans.ApplicationContext;
import org.simple.framework.beans.SimpleApplication;
import org.simple.framework.beans.annotations.SimpleFrameworkApplication;

@SimpleFrameworkApplication
public class Main {
    
    public static void main(String[] args) {
        SimpleApplication.start(Main.class, args);
        ApplicationContext applicationContext = SimpleApplication.applicationContext;
        System.out.println(applicationContext.size());
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        scanner.close();
    }

}