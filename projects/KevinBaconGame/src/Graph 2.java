import java.util.*;

/**
 * A simple directed, labeled graph.
 * V = vertex type, E = edge label type.
 */
public class Graph<V,E> {
    private final Map<V, Map<V,E>> adj = new HashMap<>();

    /** Add vertex if absent. */
    public void addVertex(V v) {
        adj.computeIfAbsent(v, k -> new HashMap<>());
    }

    /** Add/update a directed edge u -> v with label. */
    public void addEdge(V u, V v, E label) {
        addVertex(u);
        addVertex(v);
        adj.get(u).put(v, label);
    }

    /** True if graph has vertex v. */
    public boolean containsVertex(V v) { return adj.containsKey(v); }

    /** Out-neighbors of v. */
    public Set<V> neighbors(V v) {
        return adj.getOrDefault(v, Map.of()).keySet();
    }

    /** All vertices. */
    public Set<V> vertices() { return adj.keySet(); }

    /** Get label on edge u -> v, or null. */
    public E getLabel(V u, V v) {
        Map<V,E> m = adj.get(u);
        return (m == null) ? null : m.get(v);
    }
}
