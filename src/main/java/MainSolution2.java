import com.opencsv.CSVReader;
import model.Edge;
import model.Follow;
import model.ProbableTriadicClosure;

import java.io.FileReader;
import java.util.*;

public class MainSolution2 {
    static String RESOURCES_PATH = "src/main/resources/";
    static String FOLLOWERS_FILE = RESOURCES_PATH + "followers.csv";
    static String DESIGNERS_FILE = RESOURCES_PATH + "designers.csv";
    static String SHOTS_FILE = RESOURCES_PATH + "shots.csv";
    static int YEAR_IN_SECONDS = 31622400;
    public static void main(String[] args) {
        HashMap<Integer, HashSet<Integer>> graph = createGraph();

        HashMap<Integer, HashSet<Integer>> subgraph;
//        HashMap<Integer, Float> pageRank;
//        HashMap<Integer, String> designers = createGraphDesigner();
//        HashMap<Integer, List<Integer>> shots = createGraphShots();
//
//        System.out.println("Nodes : " + graph.size());
//        System.out.println("Edges : " + countEdges(graph));
//        System.out.println("Components : " + countComponents(graph));
//        bridge(graph);
//        System.out.println("Bridges : " + count);

        subgraph = largestComponent(graph);
//        HashMap<Integer, HashSet<Integer>> reverse = reverseGraph((HashMap<Integer, HashSet<Integer>>) subgraph.clone());

        System.out.println("Largest component size : " + subgraph.size());
        System.out.println("Largest component edges : " + countEdges(subgraph));

        System.out.println("Local bridges : " + localBridge(graph));

//        System.out.println(reverseGraph(mockDirectedGraph()));
//        pageRank = computePageRank(subgraph,100,0.9f);
//        List<Integer> maxs = getHighestValues(20, (HashMap<Integer, Float>) pageRank.clone());
//
//        System.out.println("---------------------------------");
//        System.out.println("PAGE RANK : ");
//        System.out.println("---------------------------------");
//        int i = 1;
//        for(int max:maxs){
////            System.out.print("Top " + i++ + ", designer : "+max);
////            System.out.printf(", pr value : %10.9f", pageRank.get(max));
////            System.out.print(", "+designers.get(max));
////            float avgLikes = 0;
////            if(shots.get(max) != null) {
////                for (int v : shots.get(max)) {
////                    avgLikes += v;
////                }
////                avgLikes /= shots.get(max).size();
////            }
////            System.out.println(", avg likes : "+avgLikes+", followed : "+subgraph.get(max)+", follower : "+reverse.get(max));
//            System.out.print(i++ + " & "+max);
//            System.out.printf(" & %12.11f", pageRank.get(max));
//            System.out.print(" & 0 & 0 & 0 & " +designers.get(max) + "\n");
//        }
//
//        float sum = 0f;
//        for(int v:pageRank.keySet()){
//            sum+=pageRank.get(v);
//        }
////        sum = Arrays.stream(pageRank).reduce(0f,(f1,f2) -> f1 + f2);
//        System.out.println("PR SUM : " +sum);

        computeTriadicClosure(subgraph);

    }

    public static List<ProbableTriadicClosure> computeTriadicClosure(HashMap<Integer,HashSet<Integer>> G){
        HashMap<Integer, HashSet<Follow>> graph = createFollowGraph(getLargestNodes(G));

        int minYear = Integer.MAX_VALUE;
        for(int v:graph.keySet()){
            for(Follow follow:graph.get(v)){
                if(follow.getTimeStamp() < minYear){
                    minYear = follow.getTimeStamp();
                }
            }
        }

        HashMap<Integer,HashSet<ProbableTriadicClosure>> probableOverYears = new HashMap<>();
        HashSet<ProbableTriadicClosure> probables = new HashSet<>();

        minYear = (minYear / YEAR_IN_SECONDS) * YEAR_IN_SECONDS;
        int year =1975+ (minYear/YEAR_IN_SECONDS);
        System.out.println(year);
        for(int curYear = year; curYear <= 2021 ; curYear++){

            for(int v: graph.keySet()){
                if(graph.get(v).size() >= 2){

                    for(Follow follow: graph.get(v)){

                        probables.addAll(getProbableClosure(graph,graph.get(v),graph.get(follow.getFollowed()),curYear));

                    }

                }
            }
            probableOverYears.put(curYear,probables);

        }

        List<ProbableTriadicClosure> closuresRealised = new ArrayList<>();

        for(int y :probableOverYears.keySet()){

            for(ProbableTriadicClosure closure: probableOverYears.get(y)) {
                for (Follow follow : graph.get(closure.getDesigner())){
                    if (follow.getFollowed() == closure.getProbableFollowed()) {
                        closure.setTimeStampRealised(follow.getTimeStamp());
                        closuresRealised.add(closure);
                    }
                }
            }

        }

        return closuresRealised;

    }

    public static HashSet<ProbableTriadicClosure> getProbableClosure(HashMap<Integer,HashSet<Follow>> graph, HashSet<Follow> fFollower, HashSet<Follow> fFollowed, int year){
        HashSet<ProbableTriadicClosure> closures = new HashSet<>();

        ProbableTriadicClosure closure;
        for(Follow follow:fFollower){
            for(Follow f:fFollowed){
                if(!graph.get(f.getFollowed()).contains(follow.getFollower())){
                    if(f.getTimeStamp() >= year && f.getTimeStamp() < year + YEAR_IN_SECONDS) {
                        closure = new ProbableTriadicClosure(follow.getFollower(), follow.getFollowed(), f.getFollower(), f.getTimeStamp());
                        closures.add(closure);
                    }
                }
            }
        }

        return closures;
    }


    public static List<Integer> getHighestValues(int top,HashMap<Integer,Float> values){
        List<Integer> maxs = new ArrayList<>();
        float max;
        int indexMax;
        for (int i = 0; i < top; i++) {
            max = 0f;
            indexMax = 0;
            for (int v:values.keySet()) {
                if(values.get(v) > max){
                    max = values.get(v);
                    indexMax = v;
                }
            }
            maxs.add(new Integer(indexMax));
            values.remove(indexMax);
        }
        return maxs;
    }

    public static HashMap<Integer, Float> computePageRank(HashMap<Integer,HashSet<Integer>> graph, int k, float alpha){
        HashMap<Integer,Float> pageRankValues = new HashMap<>();
        HashMap<Edge,Float> edgePageRankValues = new HashMap<>();
        HashMap<Integer,HashSet<Integer>> reverseGraph = reverseGraph(graph);

        for (int v:graph.keySet()) {
            pageRankValues.put(v, 1.f/graph.size());

            for(int w:graph.get(v)){
                edgePageRankValues.put(new Edge(v,w), 1.f/graph.size());
            }
        }

        int out;
        float fluidOut,fluidIn;
        Edge edge;

        for (int i = 0; i < k; i++) {
            for(int v:graph.keySet()){
                out = graph.get(v).size();

                fluidOut = pageRankValues.get(v) / out;
                for(int outgoing :graph.get(v)){
                    edge = new Edge(v,outgoing);
                    edgePageRankValues.put(edge,fluidOut);
                }
            }

            for(int v:graph.keySet()){
                fluidIn = 0;

                if(graph.get(v).size() == 0){
                    fluidIn = pageRankValues.get(v);
                }
                for (int ingoing : reverseGraph.get(v)) {
                    edge = new Edge(ingoing,v);
                    fluidIn += edgePageRankValues.get(edge);
                }

                if(alpha != -1)
                    fluidIn  = alpha * fluidIn + ((1.f - alpha) / graph.size() );

                pageRankValues.put(v,fluidIn);
            }
        }
        return pageRankValues;
    }

    public static HashMap<Integer,HashSet<Integer>> reverseGraph(HashMap<Integer,HashSet<Integer>> graph){
        HashMap<Integer,HashSet<Integer>> reverse = new HashMap<>();
        for(int v: graph.keySet()){
            reverse.put(v,new HashSet<>());
        }

        for(int v: graph.keySet()){
            for(int w: graph.get(v)){
                if(!reverse.containsKey(w)) {
                    reverse.put(w, new HashSet<>());
                }
                reverse.get(w).add(v);
            }
        }

        return reverse;
    }


    static HashSet<Integer> lastMarked = new HashSet<Integer>();

    public static HashSet<Integer> getLargestNodes(HashMap<Integer,HashSet<Integer>> graph){
        marked = new HashSet<>();
        HashSet<Integer> largestCCMarked = new HashSet<>();

        int maxCpt = Integer.MIN_VALUE;
        for (int v : graph.keySet()) {
            if (!marked.contains(v) && !graph.get(v).isEmpty()) {
                lastMarked.clear();
                dfsLargest(graph, v);

                if(lastMarked.size() > maxCpt){
                    maxCpt = lastMarked.size();
                    largestCCMarked = (HashSet<Integer>) lastMarked.clone();
                }
                count++;
            }
        }
        return largestCCMarked;
    }

    public static HashMap<Integer, HashSet<Integer>> largestComponent(HashMap<Integer, HashSet<Integer>> graph){


//        HashMap<Integer,HashSet<Integer>> subgraph = createSubgraph(graph,largestCCMarked);
        HashMap<Integer,HashSet<Integer>> subgraph = createGraphWithMarked(getLargestNodes(graph));
        return subgraph;
    }

    public static HashMap<Integer,HashSet<Integer>> createSubgraph(HashMap<Integer,HashSet<Integer>> graph,HashSet<Integer> marked){
        HashMap<Integer,HashSet<Integer>> subgraph = new HashMap<>();
        for(int v:marked){
            if(!subgraph.containsKey(v)){
                subgraph.put(v,graph.get(v));
            }
        }
        return subgraph;
    }


    public static void dfsLargest(HashMap<Integer, HashSet<Integer>> graph, int src){
        lastMarked.add(src);
        marked.add(src);
        if (graph.get(src) != null){
            for (int v : graph.get(src)) {
                if (!marked.contains(v)) {
                    dfsLargest(graph, v);
                }
            }
        }
    }


    public static int localBridge(HashMap<Integer, HashSet<Integer>> graph){
        int count = 0;
        for (int v : graph.keySet()) {
            for (int w : graph.get(v)) {
                if (compareNeighbours(graph.get(v), graph.get(w))){
                    count++;
                }
            }
        }
        return count/2;
    }

    public static boolean compareNeighbours(HashSet<Integer> a, HashSet<Integer> b){
        for (int x : a) {
            if (b.contains(x)){
                return false;
            }
        }
        return true;
    }

    static int time = 0;
    static final int NIL = -1;
    static int count = 0;

    public static void bridgeUtil(HashMap<Integer, HashSet<Integer>> graph, int u, HashSet<Integer> visited, HashMap<Integer, Integer> disc, HashMap<Integer, Integer> low, HashMap<Integer, Integer> parent)
    {
        visited.add(u);
        time++;
        disc.put(u, time);
        low.put(u, time);

        Iterator<Integer> i = graph.get(u).iterator();
        while (i.hasNext())
        {
            int v = i.next();
            if (!visited.contains(v))
            {
                parent.put(v, u);
                bridgeUtil(graph, v, visited, disc, low, parent);

                low.put(u, Math.min(low.get(u), low.get(v)));

                if (low.get(v) > disc.get(u)){
                    count++;
                }
            }

            else if (v != parent.get(u)){
                low.put(u, Math.min(low.get(u), disc.get(v)));
            }
        }
    }

    public static void bridge(HashMap<Integer, HashSet<Integer>> graph)
    {
        HashSet<Integer> visited = new HashSet<>();
        HashMap<Integer, Integer> disc = new HashMap<>();
        HashMap<Integer, Integer> low = new HashMap<>();
        HashMap<Integer, Integer> parent = new HashMap<>();

        for (int v : graph.keySet()) {
            parent.put(v, NIL);
        }

        for (int v : graph.keySet()) {
            if (!visited.contains(v)){
                bridgeUtil(graph, v, visited, disc, low, parent);
            }
        }
    }

    public static HashSet<Integer> marked;

    public static int bridges(HashMap<Integer, HashSet<Integer>> graph){
        HashMap<Integer, HashSet<Integer>> copy = copy(graph);
        int count = 0;
        int i = 0;

        for (int v : graph.keySet()) {
            for (int adj : graph.get(v)) {
                int before = countComponents(copy);
                copy.get(v).remove(adj);
                copy.get(adj).remove(v);
                int after = countComponents(copy);

                if (before == after){
                    count ++;
                }
                copy.get(v).add(adj);
                copy.get(adj).add(v);
                i++;
                System.out.println("Edges : " + i + " | Bridges : " + count);
            }
        }

        return count/2;
    }

    public static HashMap<Integer, HashSet<Integer>> copy(HashMap<Integer, HashSet<Integer>> original) {
        HashMap<Integer, HashSet<Integer>> copy = new HashMap<>();
        for (Map.Entry<Integer, HashSet<Integer>> entry : original.entrySet()) {
            copy.put(entry.getKey(), new HashSet<>(entry.getValue()));
        }
        return copy;
    }

    public static int countEdges(HashMap<Integer, HashSet<Integer>> graph){
        int count = 0;
        for (int v : graph.keySet()) {
            count += graph.get(v).size();
        }
        return count;
    }

    public static int countComponents(HashMap<Integer, HashSet<Integer>> graph) {
        int count = 0;
        marked = new HashSet<>();
        for (int v : graph.keySet()) {
            if (!marked.contains(v) && !graph.get(v).isEmpty()){
                dfs(graph, v);
                count++;
            }
        }
        return count;
    }

    public static void dfs(HashMap<Integer, HashSet<Integer>> graph, int src){
        marked.add(src);
        if (graph.get(src) != null){
            for (int v : graph.get(src)) {
                if (!marked.contains(v)) {
                    dfs(graph, v);
                }
            }
        }
    }

    public static HashMap<Integer, HashSet<Integer>> createGraph(){
        HashMap<Integer, HashSet<Integer>> graph = new HashMap<>();
        try {
            CSVReader reader = new CSVReader(new FileReader(FOLLOWERS_FILE));
            reader.skip(1);

            String[] line = reader.readNext();
            while(line != null){
                int follower = Integer.parseInt(line[0]);
                int followed = Integer.parseInt(line[1]);

                if (!graph.containsKey(follower)) {
                    graph.put(follower, new HashSet<>());
                }

                if (!graph.containsKey(followed)) {
                    graph.put(followed, new HashSet<>());
                }

                graph.get(follower).add(followed);
                graph.get(followed).add(follower);
                line = reader.readNext();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return graph;
    }

    public static HashMap<Integer, HashSet<Integer>> createGraphWithMarked(HashSet<Integer> marked){
        HashMap<Integer, HashSet<Integer>> graph = new HashMap<>();

        for(int v:marked){
            graph.put(v,new HashSet<>());
        }

        try {
            CSVReader reader = new CSVReader(new FileReader(FOLLOWERS_FILE));
            reader.skip(1);

            String[] line = reader.readNext();
            while(line != null){
                int follower = Integer.parseInt(line[0]);
                int followed = Integer.parseInt(line[1]);

                if(graph.containsKey(follower) ){
                    graph.get(follower).add(followed);
                }

                line = reader.readNext();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return graph;
    }

    public static HashMap<Integer, String> createGraphDesigner(){
        HashMap<Integer, String> graph = new HashMap<>();
        try {
            CSVReader reader = new CSVReader(new FileReader(DESIGNERS_FILE));
            reader.skip(1);

            String[] line = reader.readNext();
            while(line != null){
                int designer = Integer.parseInt(line[0]);
                String location = line[1];

                if(!graph.containsKey(designer) ){
                    graph.put(designer, location.trim());
                }

                line = reader.readNext();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return graph;
    }

    public static HashMap<Integer,HashSet<Follow>> createFollowGraph(HashSet<Integer> marked){
        HashMap<Integer, HashSet<Follow>> graph = new HashMap<>();

        for(int v:marked){
            graph.put(v,new HashSet<>());
        }

        try {
            CSVReader reader = new CSVReader(new FileReader(FOLLOWERS_FILE));
            reader.skip(1);

            String[] line = reader.readNext();
            while(line != null){
                int follower = Integer.parseInt(line[0]);
                int followed = Integer.parseInt(line[1]);
                int ts = Integer.parseInt(line[2]);

                Follow follow = new Follow(follower,followed,ts);
                if(graph.containsKey(follower) ){
                    graph.get(follower).add(follow);
                }

                Follow follow2 = new Follow(followed,follower,ts);
                if(!graph.containsKey(followed)){
                    graph.put(followed,new HashSet<>());
                }
                graph.get(followed).add(follow2);

                line = reader.readNext();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return graph;
    }

    public static HashMap<Integer, List<Integer>> createGraphShots(){
        HashMap<Integer, List<Integer>> graph = new HashMap<>();
        try {
            CSVReader reader = new CSVReader(new FileReader(SHOTS_FILE));
            reader.skip(1);

            String[] line = reader.readNext();
            while(line != null){
                int designer = Integer.parseInt(line[0]);
                int shotID = Integer.parseInt(line[1]);
                int like = Integer.parseInt(line[2]);

                if(!graph.containsKey(designer) ){
                    graph.put(designer, new ArrayList<>());
                }

                graph.get(designer).add(like);

                line = reader.readNext();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return graph;
    }

    public static HashMap<Integer,HashSet<Integer>> mockDirectedGraph(){
        HashMap<Integer,HashSet<Integer>> mock = new HashMap<>();
        mock.put(0,new HashSet<>(Arrays.asList(1,2,3)));
        mock.put(1,new HashSet<>(Arrays.asList(2,4)));
        mock.put(2,new HashSet<>(Arrays.asList(4)));
        mock.put(3,new HashSet<>(Arrays.asList(2,4)));
        mock.put(4,new HashSet<>(Arrays.asList(0)));
        return mock;
    }
}
