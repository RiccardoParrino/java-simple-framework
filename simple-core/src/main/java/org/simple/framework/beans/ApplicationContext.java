package org.simple.framework.beans;

import java.util.ArrayList;
import java.util.List;

public class ApplicationContext {
    
    private List<Object> beans;

    public ApplicationContext() {
        this.beans = new ArrayList<>();
    }

    public ApplicationContext addAll(List<Object> objs) {
        this.beans.addAll(objs);
        return this;
    }

    public int size() {
        return this.beans.size();
    }

    public List<Object> getBeans() {
        return this.beans;
    }

}
