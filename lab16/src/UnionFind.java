public class UnionFind {

    /* TODO: Add instance variables here. */
    private int[] parent;
    /* Creates a UnionFind data structure holding N items. Initially, all
       items are in disjoint sets. */
    public UnionFind(int N) {
        parent = new int[N];
        for (int i = 0; i < N; i++) {
            parent[i] = -1;
        }
    }

    /* Returns the size of the set V belongs to. */
    public int sizeOf(int v) {
        int cnt = find(v);
        return -parent[cnt];
    }

    /* Returns the parent of V. If V is the root of a tree, returns the
       negative size of the tree for which V is the root. */
    public int parent(int v) {
        return parent[v];
    }

    /* Returns true if nodes V1 and V2 are connected. */
    public boolean connected(int v1, int v2) {
        if (v1 < 0 || v2 < 0 || v1 >= parent.length || v2 >= parent.length) {
            throw new IllegalArgumentException();
        }
        return find(v1) == find(v2);
    }

    /* Returns the root of the set V belongs to. Path-compression is employed
       allowing for fast search-time. If invalid items are passed into this
       function, throw an IllegalArgumentException. */
    public int find(int v) {
        if (v < 0 || v >= parent.length) {
            throw new IllegalArgumentException("index " + v + " is not between 0 and " + (parent.length - 1));
        }
        if (parent[v] < 0) {
            return v;
        }
        parent[v] = find(parent[v]);
        return parent[v];
    }

    /* Connects two items V1 and V2 together by connecting their respective
       sets. V1 and V2 can be any element, and a union-by-size heuristic is
       used. If the sizes of the sets are equal, tie break by connecting V1's
       root to V2's root. */
    public void union(int v1, int v2) {
        if (v1 < 0 || v2 < 0 || v1 > parent.length - 1 || v2 > parent.length - 1) {
            throw new IllegalArgumentException("index " + v1 + " is not between 0 and " + (parent.length - 1));
        }
        int p1 = find(v1);
        int p2 = find(v2);
        if (p1 == p2) {
            return;
        }
        if (parent[p1] < parent[p2]) {
            parent[p1] += parent[p2];
            parent[p2] = p1;
        }
        else {
            parent[p2] += parent[p1];
            parent[p1] = p2;
        }
    }
}
