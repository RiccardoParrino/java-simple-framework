package org.simple.framework.beans;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.simple.framework.beans.annotations.Inject;

public class DAGBuilder {

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