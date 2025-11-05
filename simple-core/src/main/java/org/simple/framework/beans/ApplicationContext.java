package org.simple.framework.beans;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApplicationContext {
    
    private List<Object> applicationContext;

    public ApplicationContext() {
        this.applicationContext = new ArrayList<>();
    }

    public ApplicationContext addAll(List<Object> objs) {
        this.applicationContext.addAll(objs);
        return this;
    }

    public int size() {
        return this.applicationContext.size();
    }

}
