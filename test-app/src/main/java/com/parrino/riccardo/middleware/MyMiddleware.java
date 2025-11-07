package com.parrino.riccardo.middleware;

import java.util.function.BiFunction;

import org.simple.application.annotations.Middleware;

@Middleware
public class MyMiddleware implements BiFunction<Object, Object, Object> {

    @Override
    public Object apply(Object request, Object response) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'apply'");
    }

}
