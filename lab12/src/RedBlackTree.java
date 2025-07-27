public class RedBlackTree<T extends Comparable<T>> {

    /* Root of the tree. */
    RBTreeNode<T> root;

    static class RBTreeNode<T> {

        final T item;
        boolean isBlack;
        RBTreeNode<T> left;
        RBTreeNode<T> right;

        /* Creates a RBTreeNode with item ITEM and color depending on ISBLACK
           value. */
        RBTreeNode(boolean isBlack, T item) {
            this(isBlack, item, null, null);
        }

        /* Creates a RBTreeNode with item ITEM, color depending on ISBLACK
           value, left child LEFT, and right child RIGHT. */
        RBTreeNode(boolean isBlack, T item, RBTreeNode<T> left,
                   RBTreeNode<T> right) {
            this.isBlack = isBlack;
            this.item = item;
            this.left = left;
            this.right = right;
        }
    }

    /* Creates an empty RedBlackTree. */
    public RedBlackTree() {
        root = null;
    }

    /* Creates a RedBlackTree from a given 2-3 TREE. */
    public RedBlackTree(TwoThreeTree<T> tree) {
        Node<T> ttTreeRoot = tree.root;
        root = buildRedBlackTree(ttTreeRoot);
    }

    /* Builds a RedBlackTree that has isometry with given 2-3 tree rooted at
       given node R, and returns the root node. */
    RBTreeNode<T> buildRedBlackTree(Node<T> r) {
        if (r == null) {
            return null;
        }

        if (r.getItemCount() == 1) {
            // TODO: Replace with code to create a 2-node equivalent
            RBTreeNode left = buildRedBlackTree(r.getChildAt(0));
            RBTreeNode right = buildRedBlackTree(r.getChildAt(1));
            RBTreeNode node = new RBTreeNode(true, r.getItemAt(0), left, right);
            return node;
        } else {
            // TODO: Replace with code to create a 3-node equivalent
            RBTreeNode child0 = buildRedBlackTree(r.getChildAt(0));
            RBTreeNode child1 = buildRedBlackTree(r.getChildAt(1));
            RBTreeNode child2 = buildRedBlackTree(r.getChildAt(2));
            RBTreeNode nodeRed = new RBTreeNode(false,r.getItemAt(0), child0, child1);
            RBTreeNode nodeBlack = new RBTreeNode(true,r.getItemAt(1),nodeRed, child2);
            return nodeBlack;
        }
    }

    /**
     * Flips the color of node and its children. Assume that NODE has both left
     * and right children
     * @param node
     */
    void flipColors(RBTreeNode<T> node) {
        // TODO: YOUR CODE HERE
        node.isBlack = !node.isBlack;
        node.left.isBlack = !node.left.isBlack;
        node.right.isBlack = !node.right.isBlack;
    }

    /**
     * Rotates the given node to the right. Returns the new root node of
     * this subtree. For this implementation, make sure to swap the colors
     * of the new root and the old root!
     * @param node
     * @return
     */
    RBTreeNode<T> rotateRight(RBTreeNode<T> node) {
        RBTreeNode newRoot = node.left;
        node.left = newRoot.right;
        newRoot.right = node;
        newRoot.isBlack = node.isBlack;
        node.isBlack = false;
        return newRoot;
    }

    /**
     * Rotates the given node to the left. Returns the new root node of
     * this subtree. For this implementation, make sure to swap the colors
     * of the new root and the old root!
     * @param node
     * @return
     */
    RBTreeNode<T> rotateLeft(RBTreeNode<T> node) {
        RBTreeNode newRoot = node.right;
        node.right = newRoot.left;
        newRoot.left = node;
        newRoot.isBlack = newRoot.isBlack;
        node.isBlack = false;
        return newRoot;
    }

    /**
     * Inserts the item into the Red Black Tree. Colors the root of the tree black.
     * @param item
     */
    public void insert(T item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        root = insert(root, item);
        root.isBlack = true;
    }

    /**
     * Inserts the given node into this Red Black Tree. Comments have been provided to help break
     * down the problem. For each case, consider the scenario needed to perform those operations.
     * Make sure to also review the other methods in this class!
     * @param node
     * @param item
     * @return
     */
    private RBTreeNode<T> insert(RBTreeNode<T> node, T item) {
        if (node == null) {
            return new RBTreeNode(false, item, null, null);
        }
        int comp = item.compareTo(node.item);
        if (comp > 0) {
            node.right = insert(node.right, item);
        }else if(comp < 0) {
            node.left = insert(node.left, item);
        }
        if (isRed(node.right) && !isRed(node.left)) {
            node = rotateLeft(node);
        }
        if (isRed(node.left) && isRed(node.left.left)) {
            node = rotateRight(node);
        }
        if (isRed(node.left) && isRed(node.right)) {
            flipColors(node);
        }
        return node;
    }

    /**
     * Helper method that returns whether the given node is red. Null nodes (children or leaf
     * nodes) are automatically considered black.
     * @param node
     * @return
     */
    private boolean isRed(RBTreeNode<T> node) {
        return node != null && !node.isBlack;
    }

}
