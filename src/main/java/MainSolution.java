import model.Designer;
import model.Graph;
import model.GraphType;
import utils.Algorithms;

public class MainSolution {
    public static void main(String[] args) {
        Algorithms algorithms = new Algorithms();
        Graph graph = new Graph(GraphType.UNDIRECTED);
        graph.setE(algorithms.countDirectedEdges(graph));

        int nodes = graph.getV();
        int countEdges = algorithms.countUndirectedEdges(graph);
        int countComponents = algorithms.countComponents(graph);
        int bridges = 0;
        int localBridges = 0;

        System.out.println(
                            "Nombre de noeuds : " + nodes + "\n"
                            + "Nombre d'edges : " + countEdges + "\n"
                            + "Nombre de components : " + countComponents + "\n"
                            + "Nombre de bridges : " + bridges + "\n"
                            + "Nombre de local bridges : " + localBridges + "\n"
        );

        //printGraph(graph);
    }

    public static void printGraph(Graph graph){
        for (Designer designer: graph.getAllV()) {
            System.out.println(designer + " : " + graph.adjFollows(designer));
        }
    }
}