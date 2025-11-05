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

import com.parrino.riccardo.Tuple;


public class RestControllerDiscoverer {

    public static Map<String, Tuple<Method,Object>> find(ApplicationContext applicationContext) {
        List<Object> beans = applicationContext.getBeans();

        new Tuple<Object, Object>(null, null);

        Map<String, Tuple<Method,Object>> endpoints = new HashMap<>();

        for (Object bean : beans) {
            System.out.println(bean.getClass().getSimpleName());
            Arrays.asList(bean.getClass().getDeclaredMethods()).stream()
                .filter( f -> f.getAnnotation(Get.class) != null)
                    .collect(Collectors.toMap(f -> f.getAnnotation(Get.class).path(), f -> new Tuple<Method,Object>(f,bean)));

            Map<String,Tuple<Method,Object>> getEndpoints = 
                    Arrays.asList(bean.getClass().getDeclaredMethods()).stream()
                        .filter( f -> f.getAnnotation(Get.class) != null)
                            .collect(Collectors.toMap(f -> f.getAnnotation(Get.class).path(), f -> new Tuple<Method,Object>(f,bean)));
            
            Map<String,Tuple<Method,Object>> postEndpoints = 
                    Arrays.asList(bean.getClass().getDeclaredMethods()).stream()
                        .filter( f -> f.getAnnotation(Post.class) != null)
                            .collect(Collectors.toMap(f -> f.getAnnotation(Post.class).path(), f -> new Tuple<>(f,bean)));

            Map<String,Tuple<Method,Object>> putEndpoints = 
                    Arrays.asList(bean.getClass().getDeclaredMethods()).stream()
                        .filter( f -> f.getAnnotation(Put.class) != null)
                            .collect(Collectors.toMap(f -> f.getAnnotation(Put.class).path(), f -> new Tuple<>(f,bean)));

            Map<String,Tuple<Method,Object>> deleteEndpoints = 
                    Arrays.asList(bean.getClass().getDeclaredMethods()).stream()
                        .filter( f -> f.getAnnotation(Delete.class) != null)
                            .collect(Collectors.toMap(f -> f.getAnnotation(Delete.class).path(), f -> new Tuple<>(f,bean)));
            
            Map<String,Tuple<Method,Object>> updateEndpoints = 
                    Arrays.asList(bean.getClass().getDeclaredMethods()).stream()
                        .filter( f -> f.getAnnotation(Update.class) != null)
                            .collect(Collectors.toMap(f -> f.getAnnotation(Update.class).path(), f -> new Tuple<>(f,bean)));
        
            endpoints.putAll(getEndpoints);
            endpoints.putAll(postEndpoints);
            endpoints.putAll(putEndpoints);
            endpoints.putAll(deleteEndpoints);
            endpoints.putAll(updateEndpoints);
        }
        
        return endpoints;
    }

}
