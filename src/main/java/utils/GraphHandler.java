package utils;

import com.opencsv.CSVReader;
import model.Designer;
import model.Follow2;
import model.Graph;
import model.Shot;

import java.io.FileReader;
import java.util.HashMap;
import java.util.HashSet;

public class GraphHandler {
    // Create a graph without all information, only with followers.csv
    public static HashMap<Designer,HashSet<Follow2>> createPartialGraph(String followers_file){
        HashMap<Designer,HashSet<Follow2>> graph = new HashMap<>();
        try {
            CSVReader reader = new CSVReader(new FileReader(followers_file));
            reader.skip(1);

            String[] line = reader.readNext();
            while(line != null){
                Designer follower = new Designer(Integer.parseInt(line[0]));
                Designer followed = new Designer(Integer.parseInt(line[1]));
                Follow2 follow = new Follow2(follower,followed,Integer.parseInt(line[2]));

                if (!graph.containsKey(follower)) {
                    graph.put(follower, new HashSet<>());
                }
                graph.get(follower).add(follow);
                line = reader.readNext();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return graph;
    }

    // Create a graph with all information
    public static HashMap<Designer, HashSet<Follow2>> createCompleteGraph(String followers_file, String designers_file, String shots_file){
        HashMap<Designer, HashSet<Follow2>> graph = new HashMap<>();

        // Shots
        HashMap<Integer, HashSet<Shot>> shots = new HashMap<>();
        try {
            CSVReader reader = new CSVReader(new FileReader(shots_file));
            reader.skip(1);

            String[] line = reader.readNext();
            while(line != null){
                Designer designer = new Designer(Integer.parseInt(line[0]));
                Shot shot = new Shot(Integer.parseInt(line[1]),Integer.parseInt(line[2]));

                if (!shots.containsKey(designer.getId())) {
                    shots.put(designer.getId(),new HashSet<>());
                }
                shots.get(designer.getId()).add(shot);
                line = reader.readNext();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        // Designers
        try {
            CSVReader reader = new CSVReader(new FileReader(designers_file));
            reader.skip(1);

            String[] line = reader.readNext();
            while(line != null){
                int id = Integer.parseInt(line[0]);
                Designer designer = new Designer(id, line[1].trim(),shots.get(id) == null ? new HashSet<>() : shots.get(id));

                if (!graph.containsKey(designer)) {
                    graph.put(designer, new HashSet<>());
                }

                line = reader.readNext();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        // Followers
        try {
            CSVReader reader = new CSVReader(new FileReader(followers_file));
            reader.skip(1);

            String[] line = reader.readNext();
            while(line != null){
                Designer follower = new Designer(Integer.parseInt(line[0]));

                int idFollowed = Integer.parseInt(line[1]);
                Designer followed = new Designer(idFollowed, line[1].trim(),shots.get(idFollowed) == null ? new HashSet<>() : shots.get(idFollowed));
                Follow2 follow = new Follow2(follower, followed, Integer.parseInt(line[2]));

                graph.get(follower).add(follow);
                line = reader.readNext();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return graph;
    }

    // Create a graph without all information, only with followers.csv
    public static HashMap<Designer,HashSet<Follow2>> createUndirectedGraph(String followers_file){
        HashMap<Designer,HashSet<Follow2>> graph = new HashMap<>();
        int i = 0;
        try {
            CSVReader reader = new CSVReader(new FileReader(followers_file));
            reader.skip(1);


            String[] line = reader.readNext();
            while(line != null){
                Designer follower = new Designer(Integer.parseInt(line[0]));
                Designer followed = new Designer(Integer.parseInt(line[1]));

                Follow2 follow1 = new Follow2(follower, followed,Integer.parseInt(line[2]));
                Follow2 follow2 = new Follow2(followed, follower,Integer.parseInt(line[2]));

                if (!graph.containsKey(follower)) {
                    graph.put(follower, new HashSet<>());
                }

                if (!graph.containsKey(followed)) {
                    graph.put(followed, new HashSet<>());
                }

                if (graph.get(follower).contains(follow1)) i ++;
                if (graph.get(followed).contains(follow2)) i ++;

                graph.get(follower).add(follow1);
                graph.get(followed).add(follow2);
                line = reader.readNext();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(i);
        return graph;
    }

    // Creates a mock graph based on the resources/mockGraph.png
    public static HashMap<Designer, HashSet<Follow2>> createMockDirectedGraph(){
        HashMap<Designer, HashSet<Follow2>> graph = new HashMap<>();
        for (int i = 1; i <= 10; i++) {
            Designer designer = new Designer(i);
            graph.put(new Designer(i),new HashSet<>());
            switch (i){
                case 1:
                    graph.get(designer).add(new Follow2(designer,new Designer(2),1));
                    graph.get(designer).add(new Follow2(designer,new Designer(5),2));
                    break;
                case 3:
                    graph.get(designer).add(new Follow2(designer,new Designer(1),3));
                    break;
                case 4:
                    graph.get(designer).add(new Follow2(designer,new Designer(5),4));
                    break;
                case 5:
                    graph.get(designer).add(new Follow2(designer,new Designer(3),2));
                    graph.get(designer).add(new Follow2(designer,new Designer(8),1));
                    break;
                case 6:
                    graph.get(designer).add(new Follow2(designer,new Designer(10),3));
                    break;
                case 7:
                    graph.get(designer).add(new Follow2(designer,new Designer(5),5));
                    graph.get(designer).add(new Follow2(designer,new Designer(6),1));
                    break;
                case 8:
                    graph.get(designer).add(new Follow2(designer,new Designer(6),1));
                    break;
                case 10:
                    graph.get(designer).add(new Follow2(designer,new Designer(3),2));
            }
        }
        return graph;
    }

    public static HashMap<Designer, HashSet<Follow2>> createMockUndirectedGraph(){
        Graph graph = new Graph();
        graph.addUndirectedEdge(new Designer(1),new Designer(2),1);
        graph.addUndirectedEdge(new Designer(1),new Designer(5),1);
        graph.addUndirectedEdge(new Designer(1),new Designer(3),1);
        graph.addUndirectedEdge(new Designer(3),new Designer(5),1);
        graph.addUndirectedEdge(new Designer(3),new Designer(10),1);
        graph.addUndirectedEdge(new Designer(5),new Designer(4),1);
        graph.addUndirectedEdge(new Designer(5),new Designer(7),1);
        graph.addUndirectedEdge(new Designer(5),new Designer(8),1);
        graph.addUndirectedEdge(new Designer(7),new Designer(6),1);
        graph.addUndirectedEdge(new Designer(6),new Designer(10),1);
        graph.addUndirectedEdge(new Designer(8),new Designer(6),1);

        //graph.addUndirectedEdge(new Designer(9),new Designer(11), 1);
        return graph.getGraph();
    }
}
