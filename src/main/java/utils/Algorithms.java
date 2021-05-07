package utils;

import model.Designer;
import model.Follow;
import model.Graph;

import java.util.HashSet;

public class Algorithms {
    public HashSet<Designer> marked;

    public Algorithms() {}

    public int countDirectedEdges(Graph graph){
        int count = 0;
        for (Designer designer : graph.getAllV()) {
            count += graph.adjFollows(designer).size();
        }
        return count;
    }

    public int countUndirectedEdges(Graph graph){
        return countDirectedEdges(graph)/2;
    }

    public int countComponents(Graph graph) {
        int count = 0;
        marked = new HashSet<>();
        for (Designer designer : graph.getAllV()) {
            if (!marked.contains(designer) && !graph.adjFollows(designer).isEmpty()){
                dfs(graph, designer);
                count++;
            }
        }
        return count;
    }

    public void dfs(Graph graph, Designer src){
        marked.add(src);
        if (graph.adjFollows(src) != null){
            for (Follow follow : graph.adjFollows(src)) {
                Designer d = follow.getFollowed();
                if (!marked.contains(d)) {
                    dfs(graph, d);
                }
            }
        }
    }
    
    public int bridges(Graph graph){
        HashSet<Follow> edges = graph.getAllE();
        HashSet<Follow> bridges = new HashSet<>();

        for (Follow edge : edges) {
            marked = new HashSet<>();
            graph.removeUndirectedEdge( edge);
            dfs(graph, edge.getFollower());
            if (marked.contains(edge.getFollowed())) {
                if (!bridges.contains(new Follow(edge.getFollower(),edge.getFollowed(), edge.getTimeStamp())) && !bridges.contains(new Follow(edge.getFollowed(), edge.getFollower(), edge.getTimeStamp()))){
                    bridges.add(edge);
                }
            }
            graph.addUndirectedEdge(edge.getFollower(), edge.getFollowed(), edge.getTimeStamp());
        }
        return bridges.size();
    }
}
