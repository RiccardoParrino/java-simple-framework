package org.simple.framework.beans;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TopologicalSort {

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