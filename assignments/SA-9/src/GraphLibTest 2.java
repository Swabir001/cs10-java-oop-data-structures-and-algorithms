import java.util.*;

public class GraphLibTest {
    public static void main(String[] args) {
        // Create a directed graph using AdjacencyMapGraph from class
        Graph<String, String> g = new AdjacencyMapGraph<>();

        // Add vertices
        String[] vertices = {"A", "B", "C", "D", "E"};
        for (String v : vertices) {
            g.insertVertex(v);
        }

        // Add directed edges
        g.insertDirected("A", "B", "A-B");
        g.insertDirected("A", "C", "A-C");
        g.insertDirected("A", "D", "A-D");
        g.insertDirected("A", "E", "A-E");
        g.insertDirected("B", "A", "B-A");
        g.insertDirected("B", "C", "B-C");
        g.insertDirected("C", "A", "C-A");
        g.insertDirected("C", "B", "C-B");
        g.insertDirected("C", "D", "C-D");
        g.insertDirected("E", "B", "E-B");
        g.insertDirected("E", "C", "E-C");


        // Test 1: Random walks
        System.out.println("Random walk from A (5 steps):");
        System.out.println(GraphLib.randomWalk(g, "A", 5));

        System.out.println("\nRandom walk from C (4 steps):");
        System.out.println(GraphLib.randomWalk(g, "C", 4));

        // Test 2: Vertices sorted by in-degree
        System.out.println("\nVertices sorted by in-degree:");
        System.out.println(GraphLib.verticesByInDegree(g));
    }
}
