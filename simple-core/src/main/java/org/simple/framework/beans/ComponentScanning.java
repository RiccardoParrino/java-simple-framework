package org.simple.framework.beans;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.simple.framework.beans.annotations.Bean;
import org.simple.framework.beans.annotations.Inject;

public class ComponentScanning {
    
    public static void scan (Class<?> mainClass)  {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
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

class TopologicalSort {

    // making topological sort using dfs
    public static List<Integer> run (List<List<Integer>> dag) {
        List<Integer> sorted = new ArrayList<>();
        Boolean[] visited = new Boolean[dag.size()];
        for ( int i = 0; i < visited.length; i++ )
            visited[i] = false;
        dfs(dag, 0, visited, sorted);
        return sorted;
    }

    private static void dfs(List<List<Integer>> dag, Integer currentNode, Boolean[] visited, List<Integer> sorted) {
        while( ! Arrays.stream(visited).reduce(true, (a,b) -> a & b) ) {
            for (int i = 0; i < visited.length; i++) 
                if (visited[i] == false) 
                    subroutine(dag, i, visited, sorted);
        }
    }

    private static void subroutine(List<List<Integer>> dag, Integer currentNode, Boolean[] visited, List<Integer> sorted) {
        if ( dag.get(currentNode).size() == 0 ) {
            visited[currentNode] = true;
            sorted.add(currentNode);
            return;
        } else {
            visited[currentNode] = true;
            for(Integer node : dag.get(currentNode)) {
                if (visited[node] == false)
                    subroutine(dag, node, visited, sorted);
            }
            sorted.add(currentNode);
            return;
        }
    }

}

class DAGBuilder {

    public static List<List<Integer>> run (List<Class<?>> beans) {
        List<List<Integer>> dag = new ArrayList<>();
        for ( int i = 0; i < beans.size(); i++ ) {
            dag.add(new ArrayList<>());
        }

        for ( int i = 0; i < beans.size(); i++ ) {
            Field[] fields = beans.get(i).getDeclaredFields();

            for ( Field field : fields ) {
                if (field.getAnnotation(Inject.class) != null) {
                    for (int j = 0; j < beans.size(); j++) {
                        if (beans.get(j).getSimpleName().equals(field.getType().getSimpleName())) {
                            dag.get(i).add(j);
                        }
                    }
                }

            }
        }
        return dag;
    }
}