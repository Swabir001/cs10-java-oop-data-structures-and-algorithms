import java.util.*;
/**
 * Authors: Swabir M. Bwana & Joy Maina
 * 10/31/2025
 * CS10 PS4
 * Library of graph algorithms used by the Kevin Bacon game.
 */


public class GraphLib {

    /**
     * Perform BFS on g from source and build a shortest-path tree.
     * Tree edges point from child -> parent (toward source).
     *
     * @param g the input graph
     * @param source the root of the tree
     * @return a Graph containing only vertices reached by BFS and
     *         edges child -> parent on one shortest path to source
     */
    public static <V,E> Graph<V,E> bfs(Graph<V,E> g, V source) {
        // Outline:
        // 1) Queue<V> q; Map<V,V> parent = new HashMap<>();
        // 2) enqueue source; parent.put(source, null);
        // 3) while q not empty:
        //       v = q.remove();
        //       for (nbr in g.neighbors(v)):  // NOTE: g’s direction
        //           if !parent.containsKey(nbr):
        //               parent.put(nbr, v);
        //               q.add(nbr);
        // 4) Build tree: add all parent keys as vertices; for each v != source, addEdge(v, parent.get(v), null)
        Graph<V,E> tree = new Graph<>();
        if (!g.containsVertex(source)) return tree;

        Queue<V> q = new ArrayDeque<>();
        Map<V,V> parent = new HashMap<>();
        q.add(source);
        parent.put(source, null);

        while (!q.isEmpty()) {
            V v = q.remove();
            for (V nbr : g.neighbors(v)) {
                if (!parent.containsKey(nbr)) {
                    parent.put(nbr, v);
                    q.add(nbr);
                }
            }
            // If the underlying movie graph is undirected, it will
            // also iterate incoming neighbors. For that, either store
            // both directions when building the graph, or add a
            // Graph.undirectedAdd(u,v) helper and only iterate out-neighbors here.
        }

        for (V v : parent.keySet()) tree.addVertex(v);
        for (Map.Entry<V,V> e : parent.entrySet()) {
            V v = e.getKey();
            V p = e.getValue();
            if (p != null) tree.addEdge(v, p, null); // child -> parent
        }
        return tree;
    }


    /**
     * Given a shortest-path tree and a vertex, return the path from v back to root.
     */
    public static <V,E> List<V> getPath(Graph<V,E> tree, V v) {
        List<V> path = new ArrayList<>();
        if (!tree.containsVertex(v)) return path; // not in tree → return empty list

        V current = v;
        path.add(current);

        // Follow child -> parent edges until reaching root
        while (true) {
            Set<V> neighbors = tree.neighbors(current);
            if (neighbors.isEmpty()) break; // reached root (no parent)
            V parent = neighbors.iterator().next(); // only one parent in BFS tree
            path.add(parent);
            current = parent;
        }
        return path;
    }


    /**
     * Return vertices in graph that are missing from subgraph.
     */
    public static <V,E> Set<V> missingVertices(Graph<V,E> graph, Graph<V,E> subgraph) {
        Set<V> missing = new HashSet<>(graph.vertices());
        missing.removeAll(subgraph.vertices());
        return missing;
    }


    /**
     * Average distance from root in a shortest-path tree.
     */
    public static <V,E> double averageSeparation(Graph<V,E> tree, V root) {
        // Start recursive helper from root at depth 0
        Result r = averageSeparationHelper(tree, root, 0, new HashSet<>());

        // r.count includes the root, so subtract 1 to exclude it
        if (r.count <= 1) return 0.0; // no other vertices

        return (double) r.sum / (r.count - 1);
    }

    // helper class to hold sum of depths + count together
    private static class Result {
        int sum;   // sum of depths
        int count; // number of vertices visited
        Result(int sum, int count) { this.sum = sum; this.count = count; }
    }

    // DFS over child->parent reversed, so we must find children of each node
    private static <V,E> Result averageSeparationHelper(Graph<V,E> tree, V v, int depth, Set<V> visited) {
        visited.add(v);
        int totalSum = depth;
        int totalCount = 1;

        for (V child : childrenOf(tree, v)) {
            if (!visited.contains(child)) {
                Result r = averageSeparationHelper(tree, child, depth + 1, visited);
                totalSum += r.sum;
                totalCount += r.count;
            }
        }
        return new Result(totalSum, totalCount);
    }

    // returns all nodes whose parent is v (reverse edge direction)
    private static <V,E> Set<V> childrenOf(Graph<V,E> tree, V parent) {
        Set<V> s = new HashSet<>();
        for (V v : tree.vertices()) {
            if (tree.neighbors(v).contains(parent)) {
                s.add(v);
            }
        }
        return s;
    }



    public static void main(String[] args) {
            Graph<String,Void> g = new Graph<>();
            // Undirected toy: add both directions
            g.addEdge("Kevin Bacon","Bob", null); g.addEdge("Bob","Kevin Bacon", null);
            g.addEdge("Bob","Charlie", null);     g.addEdge("Charlie","Bob", null);
            g.addEdge("Alice","Kevin Bacon", null); g.addEdge("Kevin Bacon","Alice", null);
            g.addEdge("Charlie","Dartmouth", null); g.addEdge("Dartmouth","Charlie", null);
            // "Nobody" component (disconnected)
            g.addEdge("Nobody","Nobody's Friend", null); g.addEdge("Nobody's Friend","Nobody", null);

            Graph<String,Void> tree = GraphLib.bfs(g, "Kevin Bacon");



            System.out.println("Tree vertices: " + tree.vertices());
            System.out.println("Bob’s parent? " + tree.neighbors("Bob").contains("Kevin Bacon"));
            System.out.println("Charlie has parent? " + (!tree.neighbors("Charlie").isEmpty()));
            System.out.println("Contains Nobody? " + tree.containsVertex("Nobody"));

            // 1.2
            System.out.println("Path from Dartmouth: " + GraphLib.getPath(tree, "Dartmouth"));
            System.out.println("Path from Alice: " + GraphLib.getPath(tree, "Alice"));
            System.out.println("Path from Kevin Bacon (root): " + GraphLib.getPath(tree, "Kevin Bacon"));
            System.out.println("Path from Nobody (not in tree): " + GraphLib.getPath(tree, "Nobody"));

            // 1.3
            System.out.println("Missing vertices: " + GraphLib.missingVertices(g, tree));

            // 1.4
            System.out.println("Average separation: " + GraphLib.averageSeparation(tree, "Kevin Bacon"));

    }

}

