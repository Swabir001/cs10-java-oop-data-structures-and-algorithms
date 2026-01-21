import java.util.*;

/**
 *
 * Author: Swabir Mohamed Bwana
 * CS10 SA-9
 * Oct 22nd, 2025
 *
 * Library for graph analysis
 *
 * @author Chris Bailey-Kellogg, Dartmouth CS 10, Fall 2016
 * @author Tim Pierson, Dartmouth CS10, provided for Winter 2024
 *
 */
public class GraphLib {
    /**
     * Takes a random walk from a vertex, up to a given number of steps
     * So a 0-step path only includes start, while a 1-step path includes start and one of its out-neighbors,
     * and a 2-step path includes start, an out-neighbor, and one of the out-neighbor's out-neighbors
     * Stops earlier if no step can be taken (i.e., reach a vertex with no out-edge)
     * @param g		graph to walk on
     * @param start	initial vertex (assumed to be in graph)
     * @param steps	max number of steps
     * @return		a list of vertices starting with start, each with an edge to the sequentially next in the list;
     * 			    null if start isn't in graph
     */

    public static <V,E> List<V> randomWalk(Graph<V,E> g, V start, int steps) {
        // TODO: your code here

        // Check that the starting vertex exists in the graph
        if (!g.hasVertex(start)) {
            return null;
        }

        List<V> path = new ArrayList<>();
        path.add(start);
        V current = start;
        Random rand = new Random();

        // Perform up to "steps" random moves
        for (int i = 0; i < steps; i++) {
            // Get all outgoing neighbors of the current vertex
            Iterable<V> neighbors = g.outNeighbors(current);

            // Store them in a list so we can randomly choose one
            List<V> neighborList = new ArrayList<>();
            for (V v : neighbors) {
                neighborList.add(v);
            }

            // If there are no neighbors, stop the walk early
            if (neighborList.isEmpty()) {
                break;
            }

            // Randomly pick one neighbor to move to next
            V next = neighborList.get(rand.nextInt(neighborList.size()));
            path.add(next);

            // Update current vertex
            current = next;
        }

        return path;
    }


    /**
     * Orders vertices in decreasing order by their in-degree
     * @param g		graph
     * @return		list of vertices sorted by in-degree, decreasing (i.e., largest at index 0)
     */

    public static <V,E> List<V> verticesByInDegree(Graph<V,E> g) {
        // TODO: your code here
        // Create a list and add all vertices from the graph
        List<V> vertices = new ArrayList<>();
        for (V v : g.vertices()) {
            vertices.add(v);
        }

        // Sort the list in decreasing order based on in-degree
        vertices.sort((v1, v2) -> Integer.compare(g.inDegree(v2), g.inDegree(v1)));

        // Return the sorted list
        return vertices;
    }

}