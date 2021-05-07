package model;

import utils.GraphHandler;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Graph {
    private int V;
    private int E;
    private HashMap<Designer, HashSet<Follow2>> graph;

    public Graph(){
        graph = new HashMap<>();
    }

    public Graph(GraphType graphType){
        String RESOURCES_PATH = "src/main/resources/";
        String FOLLOWERS_FILE = RESOURCES_PATH + "followers.csv";
        String DESIGNERS_FILE = RESOURCES_PATH + "designers.csv";
        String SHOTS_FILE = RESOURCES_PATH + "shots.csv";

        switch (graphType) {
            case FOLLOWERS:
                this.graph = GraphHandler.createPartialGraph(FOLLOWERS_FILE);
                break;
            case COMPLETE:
                this.graph = GraphHandler.createCompleteGraph(FOLLOWERS_FILE, DESIGNERS_FILE, SHOTS_FILE);
                break;
            case MOCK:
                this.graph = GraphHandler.createMockDirectedGraph();
                break;
            case UNDIRECTED:
                this.graph = GraphHandler.createUndirectedGraph(FOLLOWERS_FILE);
                break;
            case MOCK_UNDIRECTED:
                this.graph = GraphHandler.createMockUndirectedGraph();
                break;
        }

        this.V = this.graph.size();
    }

    public int getV() {
        return V;
    }

    public void setV(int v) {
        V = v;
    }

    public int getE() {
        return E;
    }

    public void setE(int e) {
        E = e;
    }

    public void addEdge(Designer v, Designer w, int timeStamp){
        Follow2 follow = new Follow2(v,w,timeStamp);

        if (!graph.containsKey(v)) {
            graph.put(v, new HashSet<>());
        }

        graph.get(v).add(follow);
    }

    public void addUndirectedEdge(Designer v, Designer w, int timeStamp){
        Follow2 follow1 = new Follow2(v,w,timeStamp);
        Follow2 follow2 = new Follow2(w,v,timeStamp);

        if (!graph.containsKey(v)) {
            graph.put(v, new HashSet<>());
        }

        if (!graph.containsKey(w)) {
            graph.put(w, new HashSet<>());
        }

        graph.get(v).add(follow1);
        graph.get(w).add(follow2);
    }

    public HashSet<Follow2> adjFollows(Designer v){
        return graph.get(v);
    }

    public HashSet<Designer> adjDesigners(Designer v){
        HashSet<Designer> neighbours = new HashSet<>();
        for (Follow2 follow : adjFollows(v)) {
            neighbours.add(follow.getFollowed());
        }
        return neighbours;
    }

    public Set<Designer> getAllV(){
        return graph.keySet();
    }
    
    public HashSet<Follow2> getAllE(){
        HashSet<Follow2> set = new HashSet<>();
        for (Designer designer : graph.keySet()) {
            set.addAll(graph.get(designer));
        }
        return set;
    }

    public void removeDirectedEdge(Designer v, Follow2 follow) {
        graph.get(v).remove(follow);
    }

    public void removeUndirectedEdge(Follow2 follow) {
        graph.get(follow.getFollowed()).remove(new Follow2(follow.getFollowed(), follow.getFollower(), follow.getTimeStamp()));
        graph.get(follow.getFollower()).remove(new Follow2(follow.getFollower(),follow.getFollowed(),follow.getTimeStamp()));
    }

    public HashMap<Designer, HashSet<Follow2>> getGraph() {
        return graph;
    }

    public void setGraph(HashMap<Designer, HashSet<Follow2>> graph) {
        this.graph = graph;
    }
}
