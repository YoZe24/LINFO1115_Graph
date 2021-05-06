package model;

import utils.GraphHandler;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Graph {
    private int V;
    private int E;
    private HashMap<Designer, HashSet<Followed>> graph;

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
            case MOCK_UNDIRECTED:
                this.graph = GraphHandler.createMockUndirectedGraph();
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
        Followed follow = new Followed(v,w,timeStamp);

        if (!graph.containsKey(v)) {
            graph.put(v, new HashSet<>());
        }

        graph.get(v).add(follow);
    }

    public void addUndirectedEdge(Designer v, Designer w, int timeStamp){
        Followed follow1 = new Followed(v,w,timeStamp);
        Followed follow2 = new Followed(w,v,timeStamp);

        if (!graph.containsKey(v)) {
            graph.put(v, new HashSet<>());
        }

        if (!graph.containsKey(w)) {
            graph.put(w, new HashSet<>());
        }

        graph.get(v).add(follow1);
        graph.get(w).add(follow2);
    }

    public HashSet<Followed> adjFollows(Designer v){
        return graph.get(v);
    }

    public HashSet<Designer> adjDesigners(Designer v){
        HashSet<Designer> neighbours = new HashSet<>();
        for (Followed follow : adjFollows(v)) {
            neighbours.add(follow.getWho());
        }
        return neighbours;
    }

    public Set<Designer> getAllV(){
        return graph.keySet();
    }
    
    public HashSet<Followed> getAllE(){
        HashSet<Followed> set = new HashSet<>();
        for (Designer designer : graph.keySet()) {
            set.addAll(graph.get(designer));
        }
        return set;
    }

    public void removeDirectedEdge(Designer v, Followed follow) {
        graph.get(v).remove(follow);
    }

    public void removeUndirectedEdge(Designer v, Followed follow) {
        graph.get(v).remove(follow);
        graph.get(follow.getWho()).remove(new Followed(follow.getWho(),follow.getFollower(),follow.getTimeStamp()));
    }

    public HashMap<Designer, HashSet<Followed>> getGraph() {
        return graph;
    }

    public void setGraph(HashMap<Designer, HashSet<Followed>> graph) {
        this.graph = graph;
    }
}
