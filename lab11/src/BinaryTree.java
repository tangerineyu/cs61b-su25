import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BinaryTree<T> {

    // Do not modify the TreeNode class.
    static class TreeNode<T> {
        T item;
        TreeNode<T> left;
        TreeNode<T> right;

        public TreeNode(T item) {
            this.item = item; left = right = null;
        }

        public TreeNode(T item, TreeNode<T> left, TreeNode<T> right) {
            this.item = item;
            this.left = left;
            this.right = right;
        }

        public T getItem() {
            return item;
        }

        public TreeNode<T> getLeft() {
            return left;
        }

        public TreeNode<T> getRight() {
            return right;
        }
    }

    // protected gives subclasses the ability to access this instance variable,
    // but not other classes.
    TreeNode<T> root;

    public BinaryTree() {
        root = null;
    }

    public BinaryTree(TreeNode<T> t) {
        root = t;
    }

    public TreeNode<T> getRoot() {
        return root;
    }

    /** Optional constructor, see optional exercise in lab (or last week's theoretical lab). */
    public BinaryTree(ArrayList<T> pre, ArrayList<T> in) {
        if (pre == null || in == null || pre.isEmpty() || in.isEmpty()) {
            this.root = null;
            return;
        }
        Map<T,Integer> inMap = new HashMap<>();
        for (int i = 0; i < in.size(); i++) {
            inMap.put(in.get(i), i);
        }
        this.root = buildHelper(pre, 0, pre.size() - 1,inMap, 0,in.size() - 1);
    }
    private TreeNode<T> buildHelper(ArrayList<T> pre, int preStart, int preEnd,
                                    Map<T,Integer> inMap, int inStart, int inEnd) {
        if (preStart > preEnd) {
            return null;
        }
        T rootValue = pre.get(preStart);
        TreeNode<T> root = new TreeNode<>(rootValue);
        int rootIndexIn = inMap.get(rootValue);
        int leftSubtreeSize = rootIndexIn - inStart;
        root.left = buildHelper(pre, preStart + 1,preStart + leftSubtreeSize, inMap,inStart, rootIndexIn -1);
        root.right = buildHelper(pre,preStart + leftSubtreeSize + 1, preEnd,inMap, rootIndexIn + 1,inEnd);
        return root;
    }

    /* Print the values in the tree in preorder. */
    public void printPreorder() {
        if (root == null) {
            System.out.println("(empty tree)");
        } else {
            printPreorderHelper(root);
            System.out.println();
        }
    }

    private void printPreorderHelper(TreeNode<T> node) {
        if (node == null) {
            return;
        }
        System.out.print(node.item + " ");
        printPreorderHelper(node.left);
        printPreorderHelper(node.right);
    }

    /* Print the values in the tree in inorder: values in the left subtree
       first (in inorder), then the root value, then values in the first
       subtree (in inorder). */
    public void printInorder() {
        if (root == null) {
            System.out.println("(empty tree)");
        } else {
            printInorderHelper(root);
            System.out.println();
        }
    }

    /* Prints the nodes of the BinaryTree in inorder. Used for your testing. */
    private void printInorderHelper(TreeNode<T> node) {
        if (node == null) {
            return;
        }
        printInorderHelper(node.left);
        System.out.print(node.item + " ");
        printInorderHelper(node.right);
    }

    /* Prints out the contents of a BinaryTree with a description in both
       preorder and inorder. */
    static void print(BinaryTree t, String description) {
        System.out.println(description + " in preorder");
        t.printPreorder();
        System.out.println(description + " in inorder");
        t.printInorder();
        System.out.println();
    }

    /* Fills this BinaryTree with values a, b, and c. DO NOT MODIFY. */
    public static BinaryTree<String> sampleTree1() {
        TreeNode<String> root = new TreeNode("a",
                new TreeNode("b"),
                new TreeNode("c"));
        return new BinaryTree<>(root);
    }

    /* Fills this BinaryTree with values a, b, and c, d, e, f. DO NOT MODIFY. */
    public static BinaryTree<String> sampleTree2() {
        TreeNode root = new TreeNode("a",
                new TreeNode("b",
                        new TreeNode("d",
                                new TreeNode("e"),
                                new TreeNode("f")),
                        null),
                new TreeNode("c"));
        return new BinaryTree<>(root);
    }

    /* Fills this BinaryTree with the values a, b, c, d, e, f. DO NOT MODIFY. */
    public static BinaryTree<String> sampleTree3() {
        TreeNode<String> root = new TreeNode("a",
                new TreeNode("b"),
                new TreeNode("c",
                        new TreeNode("d",
                                new TreeNode("e"),
                                new TreeNode("f")),
                        null));
        return new BinaryTree<>(root);
    }

    /* Fills this BinaryTree with the same leaf TreeNode. DO NOT MODIFY. */
    public static BinaryTree<String> sampleTree4() {
        TreeNode<String> leafNode = new TreeNode("c");
        TreeNode<String> root = new TreeNode("a", new TreeNode("b", leafNode, leafNode),
                new TreeNode("d", leafNode, leafNode));
        return new BinaryTree<>(root);
    }

    /* Creates two BinaryTrees and prints them out in inorder. */
    public static void main(String[] args) {
        BinaryTree<String> t = new BinaryTree<>();
        print(t, "the empty tree");
        t = BinaryTree.sampleTree1();
        print(t, "sample tree 1");
        t = BinaryTree.sampleTree2();
        print(t, "sample tree 2");
        t = BinaryTree.sampleTree3();
        print(t, "sample tree 3");
        t = BinaryTree.sampleTree4();
        print(t, "sample tree 4");
    }

    /* Returns the height of the tree. */
    public int height() {
        // TODO: YOUR CODE HERE
        if (root == null) {
            return 0;
        }
        return heightHelper(root);
    }
    int leftHeight = 0;
    int rightHeight = 0;
    public int heightHelper(TreeNode<T> root) {
        TreeNode<T> left = root.left;
        TreeNode<T> right = root.right;
        if (left != null) {
            leftHeight += 1;
            heightHelper(left);
        }
        if (right != null) {
            rightHeight += 1;
            heightHelper(right);
        }
        int height = Math.max(leftHeight, rightHeight) + 1;
        return height;
    }

    /* Returns true if the tree's left and right children are the same height
       and are themselves completely balanced. */
    public boolean isCompletelyBalanced() {
        return BalancedHelper(root) != -1;
    }
    public int BalancedHelper(TreeNode<T> root) {
        if (root == null) {
            return 0;
        }
        int leftHeight = BalancedHelper(root.left);
        if (leftHeight == -1) {
            return -1;
        }
        int rightHeight = BalancedHelper(root.right);
        if (rightHeight == -1) {
            return -1;
        }
        if (leftHeight != rightHeight) {
            return -1;
        }
        return 1 + leftHeight;
    }

    /* Returns a BinaryTree representing the Fibonacci calculation for N. */
    public static BinaryTree<Integer> fibTree(int N) {
        BinaryTree<Integer> result = new BinaryTree<Integer>();
        // TODO: YOUR CODE HERE
        if (N < 0) {
            return result;
        }
        result.root = fibTreeHelper(N);
        return result;
    }
    public static TreeNode<Integer> fibTreeHelper(int n) {
        if (n == 0) {
            return new TreeNode<>(0);
        }
        if (n == 1) {
            return new TreeNode<>(1);
        }
        TreeNode<Integer> left = fibTreeHelper(n - 1);
        TreeNode<Integer> right = fibTreeHelper(n - 2);
        int rootVal = left.getItem() + right.getItem();
        TreeNode<Integer> root = new TreeNode<>(rootVal);
        root.left = left;
        root.right = right;
        return root;
    }
}
