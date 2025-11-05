package com.parrino.riccardo;

import org.simple.framework.beans.SimpleApplication;
import org.simple.framework.beans.annotations.SimpleFrameworkApplication;

@SimpleFrameworkApplication
public class Main {
    
    public static void main(String[] args) {
        SimpleApplication.start(Main.class, args);
    }

}