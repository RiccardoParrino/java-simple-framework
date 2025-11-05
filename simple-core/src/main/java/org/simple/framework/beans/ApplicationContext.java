package org.simple.framework.beans;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApplicationContext {
    
    private List<Class<?>> applicationContext;

    public ApplicationContext() {
        this.applicationContext = new ArrayList<>();
    }

}
