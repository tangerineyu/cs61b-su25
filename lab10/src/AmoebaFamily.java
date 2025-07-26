package src;

import javax.swing.tree.TreeNode;
import java.util.*;

/* An src.AmoebaFamily is a tree, where nodes are Amoebas, each of which can have
   any number of children. */
public class AmoebaFamily implements Iterable<AmoebaFamily.Amoeba> {

    /* ROOT is the root amoeba of this src.AmoebaFamily */
    private Amoeba root = null;

    /* Creates an src.AmoebaFamily, where the first Amoeba's name is NAME. */
    public AmoebaFamily(String name) {
        root = new Amoeba(name, null);
    }

    /* Adds a new Amoeba with CHILDNAME to this src.AmoebaFamily as the youngest
       child of the Amoeba named PARENTNAME. This src.AmoebaFamily must contain an
       Amoeba named PARENTNAME. */
    public void addChild(String parentName, String childName) {
        if (root != null) {
            root.addChildHelper(parentName, childName);
        }
    }

    /* Returns the length of the longest name in this src.AmoebaFamily. */
    public int longestNameLength() {
        if (root != null) {
            return root.longestNameLengthHelper();
        }
        return 0;
    }

    /* Returns the longest name in this src.AmoebaFamily. */
    public String longestName() {
        // TODO: YOUR CODE HERE
        if (root != null) {
            return root.longestNameHelper();
        }
        return "";
    }

    /* Returns an Iterator for this src.AmoebaFamily. */
    public Iterator<Amoeba> iterator() {
        return new AmoebaBFSIterator(root);
    }

    /* Creates a new src.AmoebaFamily and prints it out. */
    public static void main(String[] args) {
        AmoebaFamily family = new AmoebaFamily("Amos McCoy");
        family.addChild("Amos McCoy", "mom/dad");
        family.addChild("Amos McCoy", "auntie");
        family.addChild("mom/dad", "me");
        family.addChild("mom/dad", "Fred");
        family.addChild("mom/dad", "Wilma");
        family.addChild("me", "Mike");
        family.addChild("me", "Homer");
        family.addChild("me", "Marge");
        family.addChild("Mike", "Bart");
        family.addChild("Mike", "Lisa");
        family.addChild("Marge", "Bill");
        family.addChild("Marge", "Hilary");
        System.out.println("Here's the family!");
        // Optional TODO: use the iterator to print out the family!
        for (Amoeba amoeba : family) {
            System.out.println(amoeba);
        }
    }

    /* An Amoeba is a node of an src.AmoebaFamily. */
    public static class Amoeba {

        private String name;
        private Amoeba parent;
        private ArrayList<Amoeba> children;

        public Amoeba(String name, Amoeba parent) {
            this.name = name;
            this.parent = parent;
            this.children = new ArrayList<Amoeba>();
        }

        public String toString() {
            return name;
        }

        public Amoeba getParent() {
            return parent;
        }

        public ArrayList<Amoeba> getChildren() {
            return children;
        }

        /* Adds child with name CHILDNAME to an Amoeba with name PARENTNAME. */
        public void addChildHelper(String parentName, String childName) {
            if (name.equals(parentName)) {
                Amoeba child = new Amoeba(childName, this);
                children.add(child);
            } else {
                for (Amoeba a : children) {
                    a.addChildHelper(parentName, childName);
                }
            }
        }

        /* Returns the length of the longest name between this Amoeba and its
           children. */
        public int longestNameLengthHelper() {
            int maxLengthSeen = name.length();
            for (Amoeba a : children) {
                maxLengthSeen = Math.max(maxLengthSeen,
                                         a.longestNameLengthHelper());
            }
            return maxLengthSeen;
        }
        public String longestNameHelper() {
            String maxLengthName = name;
            for (Amoeba a : children) {
                String longestNameInChild = a.longestNameHelper();
                if (longestNameInChild.length() > maxLengthName.length()) {
                    maxLengthName = longestNameInChild;
                }
            }
            return maxLengthName;
        }
        // POSSIBLE HELPER FUNCTIONS HERE

    }

    /* An Iterator class for the src.AmoebaFamily, running a DFS traversal on the
       src.AmoebaFamily. Complete enumeration of a family of N Amoebas should take
       O(N) operations. */
    public class AmoebaDFSIterator implements Iterator<Amoeba> {

        // Optional TODO: IMPLEMENT THE CLASS HERE

        /* AmoebaDFSIterator constructor. Sets up all of the initial information
           for the AmoebaDFSIterator. */
        private Stack<Amoeba> stack = new Stack<Amoeba>();
        public AmoebaDFSIterator(Amoeba root) {
           if (root != null) {
               stack.push(root);
           }

        }
        /* Returns true if there is a next element to return. */
        public boolean hasNext() {
            return !stack.isEmpty();
        }

        /* Returns the next element. */
        public Amoeba next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Amoeba node = stack.pop();
            if (node.children != null) {
                for (int i = node.children.size() - 1; i >= 0; i--) {
                    stack.push(node.children.get(i));
                }
            }
            return node;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    /* An Iterator class for the src.AmoebaFamily, running a BFS traversal on the
       src.AmoebaFamily. Complete enumeration of a family of N Amoebas should take
       O(N) operations. */
    public class AmoebaBFSIterator implements Iterator<Amoeba> {

        // Optional TODO: IMPLEMENT THE CLASS HERE

        /* AmoebaBFSIterator constructor. Sets up all of the initial information
           for the AmoebaBFSIterator. */
        private Queue<Amoeba> queue = new LinkedList<Amoeba>();
        public AmoebaBFSIterator(Amoeba root) {
            if (root != null) {
                queue.offer(root);
            }
        }

        /* Returns true if there is a next element to return. */
        public boolean hasNext() {
            return !queue.isEmpty();
        }

        /* Returns the next element. */
        public Amoeba next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Amoeba node = queue.poll();
            if (node.children != null) {
                for (Amoeba a : node.children) {
                    queue.offer(a);
                }
            }
            return node;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
