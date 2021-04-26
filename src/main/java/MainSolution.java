import model.Designer;
import model.Followed;
import utils.CSVHandler;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class MainSolution {
    public static final String RESOURCES_PATH = "src/main/resources/";

    public static final String FOLLOWERS_FILE = RESOURCES_PATH + "followers.csv";
    public static final String DESIGNERS_FILE = RESOURCES_PATH + "designers.csv";
    public static final String SHOTS_FILE = RESOURCES_PATH + "shots.csv";

    public static void main(String[] args) {
        //HashMap<Designer, HashSet<Followed>> graph = CSVHandler.createPartialGraph(FOLLOWERS_FILE);

        HashMap<Designer, HashSet<Followed>> graph = CSVHandler.createCompleteGraph(FOLLOWERS_FILE, DESIGNERS_FILE, SHOTS_FILE);
        System.out.println(Algorithms.countComponents(graph));

        printGraph(graph);
    }

    public static void printGraph(HashMap<Designer, HashSet<Followed>> graph){
        for (Map.Entry<Designer, HashSet<Followed>> entry : graph.entrySet()) {
            System.out.println(entry);
        }
    }
}
