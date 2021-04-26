import model.Designer;
import model.Followed;

import java.util.HashMap;
import java.util.HashSet;

public class Algorithms {
    public static HashSet<Designer> marked;

    public static int countComponents(HashMap<Designer, HashSet<Followed>> graph) {
        int count = 0;
        marked = new HashSet<>();
        for (Designer designer : graph.keySet()) {
            if (!marked.contains(designer) && !graph.get(designer).isEmpty()){
                dfs(graph, designer);
                count++;
            }
        }
        return count;
    }

    public static void dfs(HashMap<Designer, HashSet<Followed>> graph, Designer src){
        marked.add(src);
        if (graph.get(src) != null){
            for (Followed follow : graph.get(src)) {
                Designer d = follow.getWho();
                if (!marked.contains(d)) {
                    dfs(graph, d);
                }
            }
        }
    }
}
