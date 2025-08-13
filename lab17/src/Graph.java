import java.util.LinkedList;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import java.util.Stack;
import java.util.HashSet;

public class Graph implements Iterable<Integer> {

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
        edges.add(new Edge(v1, v2, weight)); // Add new edge if it
    }

    /* Adds an undirected Edge (V1, V2) to the graph. That is, adds an edge
       in BOTH directions, from v1 to v2 and from v2 to v1. */
    public void addUndirectedEdge(int v1, int v2) {
        addUndirectedEdge(v1, v2, 0);
    }

    /* Adds an undirected Edge (V1, V2) to the graph with weight WEIGHT. If the
       Edge already exists, replaces the current Edge with a new Edge with
       weight WEIGHT. */
    public void addUndirectedEdge(int v1, int v2, int weight) {
        addEdge(v1, v2, weight); // Add edge from v1 to v2
        addEdge(v2, v1, weight); // Add edge from v2 to v1
    }

    /* Returns true if there exists an Edge from vertex FROM to vertex TO.
       Returns false otherwise. */
    public boolean isAdjacent(int from, int to) {
        // TODO: YOUR CODE HERE
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

    /* Returns an Iterator that outputs the vertices of the graph in topological
       sorted order. */
    public Iterator<Integer> iterator() {
        return new TopologicalIterator();
    }

    /**
     *  A class that iterates through the vertices of this graph,
     *  starting with a given vertex. Does not necessarily iterate
     *  through all vertices in the graph: if the iteration starts
     *  at a vertex v, and there is no path from v to a vertex w,
     *  then the iteration will not include w.
     */
    private class DFSIterator implements Iterator<Integer> {

        private Stack<Integer> fringe;
        private HashSet<Integer> visited;

        public DFSIterator(Integer start) {
            fringe = new Stack<>();
            visited = new HashSet<>();
            fringe.push(start);
        }

        public boolean hasNext() {
            if (!fringe.isEmpty()) {
                int i = fringe.pop();
                while (visited.contains(i)) {
                    if (fringe.isEmpty()) {
                        return false;
                    }
                    i = fringe.pop();
                }
                fringe.push(i);
                return true;
            }
            return false;
        }

        public Integer next() {
            int curr = fringe.pop();
            ArrayList<Integer> lst = new ArrayList<>();
            for (int i : neighbors(curr)) {
                lst.add(i);
            }
            lst.sort((Integer i1, Integer i2) -> -(i1 - i2));
            for (Integer e : lst) {
                fringe.push(e);
            }
            visited.add(curr);
            return curr;
        }

        //ignore this method
        public void remove() {
            throw new UnsupportedOperationException(
                    "vertex removal not implemented");
        }

    }

    /* Returns the collected result of performing a depth-first search on this
       graph's vertices starting from V. */
    public List<Integer> dfs(int v) {
        ArrayList<Integer> result = new ArrayList<Integer>();
        Iterator<Integer> iter = new DFSIterator(v);

        while (iter.hasNext()) {
            result.add(iter.next());
        }
        return result;
    }

    /* Returns true if there exists a path from START to STOP. Assumes both
       START and STOP are in this graph. If START == STOP, returns true. */
    public boolean pathExists(int start, int stop) {
        if (start == stop) {
            return true;
        }
        HashSet<Integer> visited = new HashSet<>();
        Stack<Integer> fringe = new Stack<>();
        fringe.push(start);
        while (!fringe.isEmpty()) {
            int current = fringe.pop();
            if (current == stop) {
                return true; // Found the stop vertex
            }
            if (!visited.contains(current)) {
                visited.add(current);
                for (int neighbor : neighbors(current)) {
                    if (!visited.contains(neighbor)) {
                        fringe.push(neighbor); // Add unvisited neighbors to the stack
                    }
                }
            }
        }
        return false;
    }


    /* Returns the path from START to STOP. If no path exists, returns an empty
       List. If START == STOP, returns a List with START. */
    public List<Integer> path(int start, int stop) {
        ArrayList<Integer> result = new ArrayList<>();
        if (start == stop) {
            result.add(start);
            return result; // Return a list with the start vertex
        }
        HashSet<Integer> visited = new HashSet<>();
        Stack<Integer> fringe = new Stack<>();
        int[] predecessor = new int[vertexCount];
        for (int i = 0; i < vertexCount; i++) {
            predecessor[i] = -1;
        }
        fringe.push(start);
        visited.add(start);
        boolean found = false;
        while (!fringe.isEmpty()){
            int current = fringe.pop();
            for (int neighbor : neighbors(current)) {
                if (!visited.contains(neighbor)) {
                    predecessor[neighbor] = current;
                    if (neighbor == stop) {
                        found = true;
                        break;
                    }
                    fringe.push(neighbor);
                    visited.add(neighbor);
                }
            }
            if (found) {
                break; // Stop searching once the stop vertex is found
            }
        }
        if (!found) {
            return result; // Return empty list if no path found
        }
        // Reconstruct the path from stop to start using predecessor array
        Stack<Integer> pathStack = new Stack<>();
        int curr = stop;
        while (curr != -1) {
            pathStack.push(curr);
            curr = predecessor[curr];
        }
        // Pop from stack to get path from start to stop
        while (!pathStack.isEmpty()) {
            result.add(pathStack.pop());
        }
        return result; // Return the path from start to stop
    }

    public List<Integer> topologicalSort() {
        ArrayList<Integer> result = new ArrayList<Integer>();
        Iterator<Integer> iter = new TopologicalIterator();
        while (iter.hasNext()) {
            result.add(iter.next());
        }
        return result;
    }

    private class TopologicalIterator implements Iterator<Integer> {

        private Stack<Integer> fringe;

        private int[] currentInDegree;

        TopologicalIterator() {
            fringe = new Stack<Integer>();
            currentInDegree = new int[vertexCount];
            for (int i = 0; i < vertexCount; i++) {
                currentInDegree[i] = inDegree(i);
            }
            for (int i = 0; i < vertexCount; i++) {
                if (currentInDegree[i] == 0) {
                    fringe.push(i);
                }
            }
            // TODO: YOUR CODE HERE
        }

        public boolean hasNext() {
            // TODO: YOUR CODE HERE

            return !fringe.isEmpty();
        }

        public Integer next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }
            int v = fringe.pop();
            for (int u : neighbors(v)) {
                currentInDegree[u]--;
                if (currentInDegree[u] == 0) {
                    fringe.push(u);
                }
            }
            return v;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

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

    }

    private void generateG1() {
        addEdge(0, 1);
        addEdge(0, 2);
        addEdge(0, 4);
        addEdge(1, 2);
        addEdge(2, 0);
        addEdge(2, 3);
        addEdge(4, 3);
    }

    private void generateG2() {
        addEdge(0, 1);
        addEdge(0, 2);
        addEdge(0, 4);
        addEdge(1, 2);
        addEdge(2, 3);
        addEdge(4, 3);
    }

    private void generateG3() {
        addUndirectedEdge(0, 2);
        addUndirectedEdge(0, 3);
        addUndirectedEdge(1, 4);
        addUndirectedEdge(1, 5);
        addUndirectedEdge(2, 3);
        addUndirectedEdge(2, 6);
        addUndirectedEdge(4, 5);
    }

    private void generateG4() {
        addEdge(0, 1);
        addEdge(1, 2);
        addEdge(2, 0);
        addEdge(2, 3);
        addEdge(4, 2);
    }

    private void printDFS(int start) {
        System.out.println("DFS traversal starting at " + start);
        List<Integer> result = dfs(start);
        Iterator<Integer> iter = result.iterator();
        while (iter.hasNext()) {
            System.out.println(iter.next() + " ");
        }
        System.out.println();
        System.out.println();
    }

    private void printPath(int start, int end) {
        System.out.println("Path from " + start + " to " + end);
        List<Integer> result = path(start, end);
        if (result.size() == 0) {
            System.out.println("No path from " + start + " to " + end);
            return;
        }
        Iterator<Integer> iter = result.iterator();
        while (iter.hasNext()) {
            System.out.println(iter.next() + " ");
        }
        System.out.println();
        System.out.println();
    }

    private void printTopologicalSort() {
        System.out.println("Topological sort");
        List<Integer> result = topologicalSort();
        Iterator<Integer> iter = result.iterator();
        while (iter.hasNext()) {
            System.out.println(iter.next() + " ");
        }
    }

    public static void main(String[] args) {
        Graph g1 = new Graph(5);
        g1.generateG1();
        g1.printDFS(0);
        g1.printDFS(2);
        g1.printDFS(3);
        g1.printDFS(4);

        g1.printPath(0, 3);
        g1.printPath(0, 4);
        g1.printPath(1, 3);
        g1.printPath(1, 4);
        g1.printPath(4, 0);

        Graph g2 = new Graph(5);
        g2.generateG2();
        g2.printTopologicalSort();
    }
}