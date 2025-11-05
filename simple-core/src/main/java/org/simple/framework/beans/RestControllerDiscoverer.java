package org.simple.framework.beans;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.simple.framework.annotations.Delete;
import org.simple.framework.annotations.Get;
import org.simple.framework.annotations.Post;
import org.simple.framework.annotations.Put;
import org.simple.framework.annotations.Update;

public class RestControllerDiscoverer {

    public static Map<String,Method> find(ApplicationContext applicationContext) {
        List<Object> beans = applicationContext.getBeans();

        Map<String,Method> getEndpoints = beans.stream()
            .map(f -> f.getClass())
                .flatMap( f -> Arrays.stream(f.getDeclaredMethods()) )
                    .filter( f -> f.getAnnotation(Get.class) != null)
                        .collect(Collectors.toMap(f -> f.getAnnotation(Get.class).path(), f -> f));
        
        Map<String,Method> postEndpoints = beans.stream()
            .map(f -> f.getClass())
                .flatMap( f -> Arrays.stream(f.getDeclaredMethods()) )
                    .filter( f -> f.getAnnotation(Post.class) != null)
                        .collect(Collectors.toMap(f -> f.getAnnotation(Post.class).path(), f -> f));

        Map<String,Method> putEndpoints = beans.stream()
            .map(f -> f.getClass())
                .flatMap( f -> Arrays.stream(f.getDeclaredMethods()) )
                    .filter( f -> f.getAnnotation(Put.class) != null)
                        .collect(Collectors.toMap(f -> f.getAnnotation(Put.class).path(), f -> f));

        Map<String,Method> deleteEndpoints = beans.stream()
            .map(f -> f.getClass())
                .flatMap( f -> Arrays.stream(f.getDeclaredMethods()) )
                    .filter( f -> f.getAnnotation(Delete.class) != null)
                        .collect(Collectors.toMap(f -> f.getAnnotation(Delete.class).path(), f -> f));
        
        Map<String,Method> updateEndpoints = beans.stream()
            .map(f -> f.getClass())
                .flatMap( f -> Arrays.stream(f.getDeclaredMethods()) )
                    .filter( f -> f.getAnnotation(Update.class) != null)
                        .collect(Collectors.toMap(f -> f.getAnnotation(Update.class).path(), f -> f));
        
        Map<String,Method> endpoints = new HashMap<>();
        endpoints.putAll(getEndpoints);
        endpoints.putAll(postEndpoints);
        endpoints.putAll(putEndpoints);
        endpoints.putAll(deleteEndpoints);
        endpoints.putAll(updateEndpoints);
        
        return endpoints;
    }

}
