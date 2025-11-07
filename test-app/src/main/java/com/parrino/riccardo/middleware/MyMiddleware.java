package com.parrino.riccardo.middleware;

import java.util.Map.Entry;

import org.simple.application.MiddlewareInterface;
import org.simple.application.annotations.Middleware;

@Middleware
public class MyMiddleware implements MiddlewareInterface{

    @Override
    public Entry<Object, Object> current(Entry<Object, Object> intercept) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'current'");
    }

    @Override
    public Entry<Object, Object> next(Entry<Object, Object> intercept) {
        return intercept;
    }
    
}
