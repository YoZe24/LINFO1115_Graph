package utils;

import com.opencsv.CSVReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.HashSet;

public class CSVHandler {
    public static HashMap<Integer, HashSet<Integer>> createGraph(String followers_file){
        HashMap<Integer,HashSet<Integer>> graph = new HashMap<>();

        try {
            CSVReader reader = new CSVReader(new FileReader(followers_file));
            reader.skip(1);

            String[] line = reader.readNext();
            while(line != null){
                int follower = Integer.parseInt(line[0]);
                int followed = Integer.parseInt(line[1]);

                if (!graph.containsKey(follower)) {
                    graph.put(follower, new HashSet<>());
                }
                graph.get(follower).add(followed);
                line = reader.readNext();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return graph;
    }
}
