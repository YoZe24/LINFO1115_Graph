package utils;

import model.Designer;
import model.Followed;
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
        int count = 0;
        for (Designer designer : graph.getAllV()) {
            count += graph.adjFollows(designer).size();
        }
        return count/2;
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
            for (Followed follow : graph.adjFollows(src)) {
                Designer d = follow.getWho();
                if (!marked.contains(d)) {
                    dfs(graph, d);
                }
            }
        }
    }
    
    public int bridges(Graph graph){
        HashSet<Followed> edges = graph.getAllE();
        HashSet<Followed> bridges = new HashSet<>();

        for (Followed edge : edges) {
            marked = new HashSet<>();
            graph.removeUndirectedEdge(edge.getFollower(), edge);
            dfs(graph, edge.getFollower());
            if (marked.contains(edge.getWho())) {
                if (!bridges.contains(new Followed(edge.getFollower(),edge.getWho(), edge.getTimeStamp())) && !bridges.contains(new Followed(edge.getWho(), edge.getFollower(), edge.getTimeStamp()))){
                    bridges.add(edge);
                }
            }
            graph.addUndirectedEdge(edge.getFollower(), edge.getWho(), edge.getTimeStamp());
        }
        return bridges.size();
    }
}
