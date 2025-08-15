import java.util.*;

public class Graph {

    private LinkedList<Edge>[] adjLists;
    private int vertexCount;

    /* Initializes a graph with NUMVERTICES vertices and no Edges. */
    public Graph(int numVertices) {
        adjLists = (LinkedList<Edge>[]) new LinkedList[numVertices];
        for (int k = 0; k < numVertices; k++) {
            adjLists[k] = new LinkedList<Edge>();
        }
        vertexCount = numVertices;
    }

    /* Adds a directed Edge (V1, V2) to the graph. That is, adds an edge
       in ONE directions, from v1 to v2. */
    public void addEdge(int v1, int v2) {
        addEdge(v1, v2, 0);
    }

    /* Adds an undirected Edge (V1, V2) to the graph. That is, adds an edge
       in BOTH directions, from v1 to v2 and from v2 to v1. */
    public void addUndirectedEdge(int v1, int v2) {
        addUndirectedEdge(v1, v2, 0);
    }

    /* Adds a directed Edge (V1, V2) to the graph with weight WEIGHT. If the
       Edge already exists, replaces the current Edge with a new Edge with
       weight WEIGHT. */
    public void addEdge(int v1, int v2, int weight) {
        LinkedList<Edge> edges = adjLists[v1];
        for (Edge edge : edges) {
            if (edge.to == v2) {
                edge.weight = weight; // Update weight if edge already exists
                return;
            }
        }
        edges.add(new Edge(v1, v2, weight));
    }

    /* Adds an undirected Edge (V1, V2) to the graph with weight WEIGHT. If the
       Edge already exists, replaces the current Edge with a new Edge with
       weight WEIGHT. */
    public void addUndirectedEdge(int v1, int v2, int weight) {
        addEdge(v1, v2, weight); // Add edge from v1 to v2
        addEdge(v2, v1, weight);
    }

    /* Returns true if there exists an Edge from vertex FROM to vertex TO.
       Returns false otherwise. */
    public boolean isAdjacent(int from, int to) {
        LinkedList<Edge> edges = adjLists[from];
        for (Edge edge : edges) {
            if (edge.to == to) {
                return true; // Edge exists from 'from' to 'to'
            }
        }
        return false;
    }

    /* Returns a list of all the vertices u such that the Edge (V, u)
       exists in the graph. */
    public List<Integer> neighbors(int v) {
        LinkedList<Edge> edges = adjLists[v];
        ArrayList<Integer> neighbors = new ArrayList<>();
        for (Edge edge : edges) {
            neighbors.add(edge.to); // Add the destination vertex of each edge
        }
        neighbors.sort((Integer i1, Integer i2) -> -(i1 - i2)); // Sort in descending order
        return neighbors;
    }
    /* Returns the number of incoming Edges for vertex V. */
    public int inDegree(int v) {
        int count = 0;
        for (int i = 0; i < vertexCount; i++) {
            LinkedList<Edge> edges = adjLists[i];
            for (Edge edge : edges) {
                if (edge.to == v) {
                    count++; // Increment count if edge points to vertex v
                }
            }
        }
        return count;
    }


    /* Returns a list of the vertices that lie on the shortest path from start to stop. 
    If no such path exists, you should return an empty list. If START == STOP, returns a List with START. */
    public ArrayList<Integer> shortestPath(int start, int stop) {
        // TODO: YOUR CODE HERE
        ArrayList<Integer> path = new ArrayList<>();
        if (start == stop) {
            path.add(start);
            return path;
        }
        HashMap<Integer, Integer> distance = new HashMap<>();
        HashMap<Integer, Integer> preNode = new HashMap<>();
        boolean[] visited = new boolean[vertexCount];
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));
        for (int i = 0; i < vertexCount; i++) {
            distance.put(i, Integer.MAX_VALUE);
            preNode.put(i, -1);
        }
        distance.put(start, 0);
        pq.offer(new int[]{start, 0});
        while (!pq.isEmpty()) {
            int[] curr = pq.poll();
            int u = curr[0];
            int distanceU = curr[1];
            if (u == stop) {
                break;
            }
            if (distanceU > distance.get(u)) {
                continue;
            }
            for (int v : neighbors(u)) {
                Edge e = getEdge(u, v);
                if (e == null) continue;
                int weight = e.weight;
                int newDistance = distanceU + weight;
                if (newDistance < distance.get(v)) {
                    distance.put(v, newDistance);
                    preNode.put(v, u);
                    pq.offer(new int[]{v, newDistance});
                }
            }
        }
        if (distance.get(stop) == Integer.MAX_VALUE) {
            return path;
        }
        int at = stop;
        while(at != -1) {
            path.add(at);
            at = preNode.get(at);
        }
        Collections.reverse(path);
        return path;
    }

    private Edge getEdge(int v1, int v2) {
        for (Edge e : adjLists[v1]) {
            if (e.to == v2) {
                return e; // Return the edge if it exists
            }
        }
        return null;
    }

    private class Edge {

        private int from;
        private int to;
        private int weight;

        Edge(int from, int to, int weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }

        public String toString() {
            return "(" + from + ", " + to + ", weight = " + weight + ")";
        }

        public int to() {
            return this.to;
        }

    }

    
}