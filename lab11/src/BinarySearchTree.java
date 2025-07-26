public class BinarySearchTree<T extends Comparable<T>> extends BinaryTree<T> {

    /* Creates an empty BST. Super() calls the constructor for BinaryTree (not in scope). */
    public BinarySearchTree() {
        super();
    }

    /* Creates a BST with root as ROOT. */
    public BinarySearchTree(TreeNode root) {
        super(root);
    }

    /* Returns true if the BST contains the given KEY. */
    public boolean contains(T key) {
        // TODO: YOUR CODE HERE: an extra helper method might be useful
        if (root == null) {
            return false;
        }
        return containsHelp(key,root) == 0;
    }
    public int containsHelp(T key,TreeNode<T> root) {
        if (root == null) {
            return 1;
        }
        int cmp = root.item.compareTo(key);
        if (cmp == 0) {
            return 0;
        }
        else if (cmp < 0) {
            return containsHelp(key, root.right);
        }
        else{
            return containsHelp(key, root.left);
        }
    }

    /* Adds a node for KEY iff KEY isn't in the BST already. */
    public void add(T key) {
        // TODO: YOUR CODE HERE: an extra helper method might be useful
        root = addHelp(key,root);
    }
    public TreeNode<T> addHelp(T key,TreeNode<T> root) {
        if (root == null) {
            return new TreeNode<>(key);
        }
        if (key.compareTo(root.item) < 0) {
            root.left = addHelp(key, root.left);
        }
        else if (key.compareTo(root.item) > 0) {
            root.right = addHelp(key,root.right);
        }
        return root;
    }

    /* Deletes a node from the BST. 
     * Even though you do not have to implement delete, you 
     * should read through and understand the basic steps.
    */
    public T delete(T key) {
        TreeNode<T> parent = null;
        TreeNode<T> curr = root;
        TreeNode<T> delNode = null;
        TreeNode<T> replacement = null;
        boolean rightSide = false;

        while (curr != null && !curr.item.equals(key)) {
            if (curr.item.compareTo(key) > 0) {
                parent = curr;
                curr = curr.left;
                rightSide = false;
            } else {
                parent = curr;
                curr = curr.right;
                rightSide = true;
            }
        }
        delNode = curr;
        if (curr == null) {
            return null;
        }

        if (delNode.right == null) {
            if (root == delNode) {
                root = root.left;
            } else {
                if (rightSide) {
                    parent.right = delNode.left;
                } else {
                    parent.left = delNode.left;
                }
            }
        } else {
            curr = delNode.right;
            replacement = curr.left;
            if (replacement == null) {
                replacement = curr;
            } else {
                while (replacement.left != null) {
                    curr = replacement;
                    replacement = replacement.left;
                }
                curr.left = replacement.right;
                replacement.right = delNode.right;
            }
            replacement.left = delNode.left;
            if (root == delNode) {
                root = replacement;
            } else {
                if (rightSide) {
                    parent.right = replacement;
                } else {
                    parent.left = replacement;
                }
            }
        }
        return delNode.item;
    }
}
