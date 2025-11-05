package org.simple.framework.beans;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.simple.framework.beans.annotations.Bean;

public class ComponentScanning {
    
    public static List<Object> scan (Class<?> mainClass)  {
        try {
            String classpath = System.getProperty("java.class.path").split(";")[0];
            // retrieving class files
            List<File> files = filesDiscoverer(new File(classpath)).stream()
                    .filter(f -> !f.isDirectory())
                        .filter(f -> f.getName().endsWith(".class"))
                            .filter(f -> !f.getName().contains("Main.class"))
                                .collect(Collectors.toList());
            
            // Loading classes
            List<Class<?>> clazzs = new ArrayList<>();
            for ( File f : files ) {
                String packageAsString = f.getPath()
                    .split("classes")[1]
                        .substring(1)
                            .replace("\\", ".")
                                .replace(".class", "");
                URL classUrl = new URL("file:\\"+f.getName().split("\\.")[0]);
                URLClassLoader loader = new URLClassLoader(new URL[]{classUrl});
                Class<?> clazz = loader.loadClass(packageAsString);
                clazzs.add(clazz);
                loader.close();
            } 

            // Filtering out beans
            List<Class<?>> beans = clazzs.stream()
                .filter(c -> c.getAnnotation(Bean.class) != null)
                    .collect(Collectors.toList());
            
            // DAG building
            List<List<Integer>> dag = DAGBuilder.run(beans); // build dag
            
            // Topological sort of bean
            List<Integer> topsort = TopologicalSort.run(dag); // make topological sort
            
            // reordered beans for instatiation
            List<Class<?>> reorderBeans = topsort.stream()
                .map(i -> beans.get(i))
                    .collect(Collectors.toList());
            
            // creating the object
            List<Object> objs = 
                reorderBeans.stream()
                    .map( c -> {
                            try {
                                return c.getDeclaredConstructor().newInstance();
                            } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
                                    | InvocationTargetException | NoSuchMethodException | SecurityException e) {
                                e.printStackTrace();
                            }
                            return null;
                        } ).collect(Collectors.toList());
            
            return objs;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static List<File> filesDiscoverer (File file) {
        if (!file.isDirectory()) {
            List<File> files = new ArrayList<>();
            files.add(file);
            return files;
        } else {
            File[] subfiles = file.listFiles();
            List<File> returningFiles = new ArrayList<>();
            for(int i = 0; i < subfiles.length; i++) {
                returningFiles.addAll(filesDiscoverer(subfiles[i]));
            }
            return returningFiles;
        }
    }

}