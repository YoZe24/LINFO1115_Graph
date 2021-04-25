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
        HashMap<Integer, HashSet<Integer>> graph = CSVHandler.createGraph(FOLLOWERS_FILE);

        printGraph(graph);
    }

    public static void printGraph(HashMap<Integer,HashSet<Integer>> graph){
        for (Map.Entry<Integer, HashSet<Integer>> entry : graph.entrySet()) {
            System.out.println(entry);
        }
    }
}
