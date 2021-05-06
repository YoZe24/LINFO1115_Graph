import model.Designer;
import model.Followed;
import utils.GraphHandler;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class MainSolution {
    public static final String RESOURCES_PATH = "src/main/resources/";

    public static final String FOLLOWERS_FILE = RESOURCES_PATH + "followers.csv";
    public static final String DESIGNERS_FILE = RESOURCES_PATH + "designers.csv";
    public static final String SHOTS_FILE = RESOURCES_PATH + "shots.csv";

    // Choose 0 | 1 | 2 | 3
    public static final int GRAPH_CHOOSER = 2;
    public static HashMap<Designer, HashSet<Followed>> graph = null;

    public static void main(String[] args) {
        graph = chooseGraph(GRAPH_CHOOSER);

        int countEdges = Algorithms.countEdges(graph);
        int countComponents = Algorithms.countComponents(graph);

        System.out.println("Nombre d'edges : " + countEdges + "\n" +
                           "Nombre de components : " + countComponents + "\n");

        //printGraph(graph);
    }

    public static HashMap<Designer, HashSet<Followed>> chooseGraph(int r){
        HashMap<Designer, HashSet<Followed>> graph = null;
        switch (r) {
            case 0:
                graph = GraphHandler.createPartialGraph(FOLLOWERS_FILE);
                break;
            case 1:
                graph = GraphHandler.createCompleteGraph(FOLLOWERS_FILE, DESIGNERS_FILE, SHOTS_FILE);
                break;
            case 2:
                graph = GraphHandler.createUndirectedGraph(FOLLOWERS_FILE);
                break;
            case 3:
                graph = GraphHandler.createMockGraph();
                break;
        }
        return graph;
    }

    public static void printGraph(HashMap<Designer, HashSet<Followed>> graph){
        for (Map.Entry<Designer, HashSet<Followed>> entry : graph.entrySet()) {
            System.out.println(entry);
        }
    }
}
