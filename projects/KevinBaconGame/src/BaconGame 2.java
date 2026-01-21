/**
 * Authors: Swabir M. Bwana & Joy Maina
 * 10/31/2025
 * CS10 PS4
 *
 * BaconGame builds a graph of actors and the movies they
 * appeared in together. It lets the user pick a “center of the
 * universe” (default Kevin Bacon) and then run different commands to explore
 * shortest paths, actor distances, degree statistics, and center rankings.
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class BaconGame {

    // maps to hold the raw data from the input files before the graph is built.
    private Map<String, String> actorIdToName = new HashMap<>();   // actorID → actor name
    private Map<String, String> movieIdToName = new HashMap<>();   // movieID → movie title
    private Map<String, Set<String>> movieToActors = new HashMap<>(); // movieID → set of actorIDs

    // The main graph: each vertex is an actor name, and each edge label is a set of movies they co-starred in.
    private Graph<String, Set<String>> graph = new Graph<>();

    /**
     * Loads the dataset and builds the co-star graph.
     * @param actorsFile path to actors.txt (actorID|actorName)
     * @param moviesFile path to movies.txt (movieID|movieName)
     * @param movieActorsFile path to movie-actors.txt (movieID|actorID)
     */
    public void loadTestData(String actorsFile, String moviesFile, String movieActorsFile) throws FileNotFoundException {
        loadActors(actorsFile);
        loadMovies(moviesFile);
        loadMovieActors(movieActorsFile);
        buildGraph();
    }

    /**
     * Reads the actors file and stores ID → Name.
     * Each line is formatted like: 123|Robert Downey Jr.
     */
    private void loadActors(String filename) throws FileNotFoundException {
        Scanner sc = new Scanner(new File(filename));
        while (sc.hasNextLine()) {
            String[] parts = sc.nextLine().split("\\|");
            actorIdToName.put(parts[0], parts[1]);
        }
        sc.close();
        System.out.println("Loaded actors: " + actorIdToName.size());
    }

    /**
     * Reads the movies file and stores ID → Title.
     * Each line example: 456|Avengers: Endgame (2019)
     */
    private void loadMovies(String filename) throws FileNotFoundException {
        Scanner sc = new Scanner(new File(filename));
        while (sc.hasNextLine()) {
            String[] parts = sc.nextLine().split("\\|");
            movieIdToName.put(parts[0], parts[1]);
        }
        sc.close();
        System.out.println("Loaded movies: " + movieIdToName.size());
    }

    /**
     * Reads which actors appeared in which movies.
     * Every line links one movieID to one actorID.
     */
    private void loadMovieActors(String filename) throws FileNotFoundException {
        Scanner sc = new Scanner(new File(filename));
        while (sc.hasNextLine()) {
            String[] parts = sc.nextLine().split("\\|");
            String movie = parts[0];
            String actor = parts[1];
            movieToActors.computeIfAbsent(movie, k -> new HashSet<>()).add(actor);
        }
        sc.close();
        System.out.println("Loaded movie-actor pairs: " + movieToActors.size());
    }

    /**
     * Converts the raw data into the actual graph:
     * - Every actor becomes a vertex.
     * - If two actors co-starred in a movie, create an undirected edge between them labeled with that movie.
     */
    private void buildGraph() {
        for (String movieId : movieToActors.keySet()) {
            Set<String> actorIds = movieToActors.get(movieId);
            List<String> actors = new ArrayList<>();

            // Convert actor IDs to real actor names and add them as graph vertices.
            for (String id : actorIds) {
                actors.add(actorIdToName.get(id));
                graph.addVertex(actorIdToName.get(id));
            }

            // For every pair of actors in the same movie, connect them.
            for (int i = 0; i < actors.size(); i++) {
                for (int j = i + 1; j < actors.size(); j++) {
                    addCoStarEdge(actors.get(i), actors.get(j), movieIdToName.get(movieId));
                }
            }
        }
        System.out.println("Graph built with " + graph.vertices().size() + " actors.");
    }

    /**
     * Adds or updates an undirected edge between two actors with the movie they appeared in.
     */
    private void addCoStarEdge(String a1, String a2, String movie) {
        Set<String> movies = graph.getLabel(a1, a2);
        if (movies == null) {
            movies = new HashSet<>();
            graph.addEdge(a1, a2, movies);
            graph.addEdge(a2, a1, movies); // store both directions
        }
        movies.add(movie);
    }

    /**
     * Computes the shortest-path distance for every actor in the BFS tree.
     * @param tree BFS tree returned by GraphLib.bfs()
     * @param center the root of the BFS tree
     * @return Map of actor name → distance in edges
     */
    private Map<String, Integer> getDistances(Graph<String, Set<String>> tree, String center) {
        Map<String, Integer> dist = new HashMap<>();
        for (String actor : tree.vertices()) {
            List<String> path = GraphLib.getPath(tree, actor);
            if (!path.isEmpty()) {
                dist.put(actor, path.size() - 1);
            }
        }
        return dist;
    }

    /**
     * Computes how many co-stars each actor has (their node degree in the graph).
     * @return Map of actor name → number of neighbors
     */
    private Map<String, Integer> getDegrees() {
        Map<String, Integer> deg = new HashMap<>();
        for (String v : graph.vertices()) {
            deg.put(v, graph.neighbors(v).size());
        }
        return deg;
    }

    /**
     * Finds the actors with the best (or worst) average separation score.
     * Positive limit = best first, negative limit = worst first.
     */
    private void listCentersByAverageSeparation(int limit) {

        // Compute degrees first and take the top 200 most connected actors to speed things up.
        Map<String, Integer> degrees = getDegrees();
        List<String> topActors = degrees.entrySet().stream()
                .sorted((a, b) -> b.getValue() - a.getValue())
                .limit(200)
                .map(Map.Entry::getKey)
                .toList();

        System.out.println("Testing top " + topActors.size() + " most connected actors...");

        List<Map.Entry<String, Double>> scores = new ArrayList<>();

        // Compute average separation for each candidate center.
        for (String actor : topActors) {
            Graph<String, Set<String>> tree = GraphLib.bfs(graph, actor);
            double avg = GraphLib.averageSeparation(tree, actor);
            scores.add(Map.entry(actor, avg));
        }

        scores.sort(Map.Entry.comparingByValue());

        if (limit < 0) {
            Collections.reverse(scores);
            limit = -limit;
        }

        System.out.println("\nTop " + limit + " centers by average separation:");
        scores.stream().limit(limit)
                .forEach(e -> System.out.println(String.format("%.3f  %s", e.getValue(), e.getKey())));
    }


    // MAIN PROGRAM

    public static void main(String[] args) throws Exception {
        BaconGame game = new BaconGame();

        // Load full dataset (uncomment small test dataset if needed)
        game.loadTestData(
                "/Users/swabir/Desktop/Intellij/cs10a/PS4/src/actors.txt",
                "/Users/swabir/Desktop/Intellij/cs10a/PS4/src/movies.txt",
                "/Users/swabir/Desktop/Intellij/cs10a/PS4/src/movie-actors.txt");

        Scanner in = new Scanner(System.in);

        // Default center
        String center = "Kevin Bacon";
        Graph<String, Set<String>> tree = GraphLib.bfs(game.graph, center);

        System.out.println("\nCenter of the universe is now: " + center);
        System.out.println("Reachable actors: " + tree.vertices().size());
        System.out.println("Average separation: " + GraphLib.averageSeparation(tree, center));
        System.out.println("""
        Commands:
          u <name>     change center of universe
          p <name>     find path from actor to center
          i            list actors not connected to center
          s a b        list actors with separation in [a,b]
          d a b        list actors with degree in [a,b]
          c n          list best/worst n centers by avg separation
          q            quit
        """);

        while (true) {
            System.out.print("\nBacon game > ");
            String line = in.nextLine().trim();

            if (line.equals("q")) break;

            // COMMAND: u <actor>
            if (line.startsWith("u ")) {
                String name = line.substring(2).trim();
                if (!game.graph.containsVertex(name)) {
                    System.out.println("No such actor: " + name);
                    continue;
                }
                center = name;
                tree = GraphLib.bfs(game.graph, center);
                System.out.println("\nCenter changed to: " + center);
                System.out.println("Reachable actors: " + tree.vertices().size());
                System.out.println("Average separation: " + GraphLib.averageSeparation(tree, center));
            }

            // COMMAND: p <actor>
            else if (line.startsWith("p ")) {
                String name = line.substring(2).trim();
                if (!game.graph.containsVertex(name)) {
                    System.out.println("No such actor: " + name);
                    continue;
                }
                List<String> path = GraphLib.getPath(tree, name);
                if (path.isEmpty()) {
                    System.out.println(name + " is not connected to " + center);
                    continue;
                }

                System.out.println(name + "'s number is " + (path.size() - 1));
                for (int i = 0; i < path.size() - 1; i++) {
                    String a1 = path.get(i);
                    String a2 = path.get(i + 1);
                    System.out.println(a1 + " appeared in " + game.graph.getLabel(a1, a2) + " with " + a2);
                }
            }

            // COMMAND: i
            else if (line.equals("i")) {
                Set<String> missing = GraphLib.missingVertices(game.graph, tree);
                System.out.println("Actors not connected to " + center + ":");
                System.out.println(missing);
            }

            // COMMAND: s low high
            else if (line.startsWith("s ")) {
                String[] parts = line.split("\\s+");
                if (parts.length != 3) {
                    System.out.println("Usage: s <low> <high>");
                    continue;
                }

                int low = Integer.parseInt(parts[1]);
                int high = Integer.parseInt(parts[2]);

                Map<String, Integer> dist = game.getDistances(tree, center);

                System.out.println("\nActors with separation between " + low + " and " + high + ":");
                dist.entrySet().stream()
                        .filter(e -> e.getValue() >= low && e.getValue() <= high)
                        .sorted(Map.Entry.comparingByValue())
                        .forEach(e -> System.out.println(e.getValue() + ": " + e.getKey()));
            }

            // COMMAND: d low high
            else if (line.startsWith("d ")) {
                String[] parts = line.split("\\s+");
                if (parts.length != 3) {
                    System.out.println("Usage: d <low> <high>");
                    continue;
                }
                int low = Integer.parseInt(parts[1]);
                int high = Integer.parseInt(parts[2]);

                Map<String,Integer> deg = game.getDegrees();

                System.out.println("\nActors with degree between " + low + " and " + high + ":");
                deg.entrySet().stream()
                        .filter(e -> e.getValue() >= low && e.getValue() <= high)
                        .sorted((a,b) -> {
                            int cmp = Integer.compare(b.getValue(), a.getValue());
                            return (cmp != 0) ? cmp : a.getKey().compareTo(b.getKey());
                        })
                        .forEach(e -> System.out.println(e.getValue() + ": " + e.getKey()));
            }

            // COMMAND: c n
            else if (line.startsWith("c ")) {
                try {
                    int n = Integer.parseInt(line.substring(2).trim());
                    game.listCentersByAverageSeparation(n);
                } catch (Exception e) {
                    System.out.println("Usage: c <number>");
                }
            }

            else {
                System.out.println("Unknown command");
            }
        }

        System.out.println("Goodbye!");
    }
}

