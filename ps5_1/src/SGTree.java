/**
 * ScapeGoat Tree class
 * <p>
 * This class contains some basic code for implementing a ScapeGoat tree. This version does not include any of the
 * functionality for choosing which node to scapegoat. It includes only code for inserting a node, and the code for
 * rebuilding a subtree.
 */

public class SGTree {

    // Designates which child in a binary tree
    enum Child {LEFT, RIGHT}
    private TreeNode[] nodeArray;
    private int index;

    /**
     * TreeNode class.
     * <p>
     * This class holds the data for a node in a binary tree.
     * <p>
     * Note: we have made things public here to facilitate problem set grading/testing. In general, making everything
     * public like this is a bad idea!
     */
    public static class TreeNode {
        int key;
        int weight;
        public TreeNode left = null;
        public TreeNode right = null;

        TreeNode(int k) {
            this.weight = 1;
            key = k;
        }
    }

    // Root of the binary tree
    public TreeNode root = null;

    /**
     * Counts the number of nodes in the specified subtree.
     *
     * @param node  the parent node, not to be counted
     * @param child the specified subtree
     * @return number of nodes
     */
    public int countNodes(TreeNode node, Child child) {
        // TODO: Implement this
        if(child == Child.LEFT) {
            return countNodesHelper(node.left);
        } else {
            return countNodesHelper(node.right);
        }
    }
    public int countNodesHelper(TreeNode node) {
        if (node == null) {
            return 0;
        }
        else {
            int left = countNodesHelper(node.left);
            int right = countNodesHelper(node.right);
            return 1 + left + right;
        }
    }
    /**
     * Builds an array of nodes in the specified subtree
     *
     * @param node  the parent node, not to be included in returned array
     * @param child the specified subtree
     * @return array of nodes
     */
    public TreeNode[] enumerateNodes(TreeNode node, Child child) {
        // TODO: Implement this
        if(node == null || child == null) {
            return null;
        }
        int size = countNodes(node, child);
        index = 0;
        nodeArray = new TreeNode[size];
        if (child == Child.LEFT) {
            enumerateNodesHelper(node.left);
        } else {
            enumerateNodesHelper(node.right);
        }
        return nodeArray;
    }
    public void enumerateNodesHelper(TreeNode node) {
        if(node == null) {
            return;
        }
        enumerateNodesHelper(node.left);
        nodeArray[this.index] = node;
        this.index++;
        enumerateNodesHelper(node.right);
    }
    /**
     * Builds a tree from the list of nodes
     * Returns the node that is the new root of the subtree
     *
     * @param nodeList ordered array of nodes
     * @return the new root node
     */
    public TreeNode buildTree(TreeNode[] nodeList) {
        // TODO: Implement this
        return buildTreeHelper(nodeList, 0, nodeList.length - 1);
    }
    public TreeNode buildTreeHelper(TreeNode[] nodeList, int left, int right) {
        int mid = left + (right - left) / 2;
        if(left > right) {
            return null;
        }
        TreeNode midNode = nodeList[mid];
        midNode.left = buildTreeHelper(nodeList, left, mid - 1);
        midNode.right = buildTreeHelper(nodeList, mid + 1, right);
        return midNode;
    }

    public void fixWeights(TreeNode u, Child child) {
        if (child == Child.LEFT) {
            fixWeightsHelper(u.left);
        } else {
            fixWeightsHelper(u.right);
        }
    }

    public void fixWeightsHelper(TreeNode u) {
        if (u.left == null && u.right == null) {
        } else if (u.left == null) {
            u.weight += countNodes(u, Child.RIGHT);
        } else if (u.right == null) {
            u.weight += countNodes(u, Child.LEFT);
        } else {
            u.weight += countNodes(u, Child.LEFT) + countNodes(u, Child.RIGHT);
        }
    }
    /**
     * Determines if a node is balanced. If the node is balanced, this should return true. Otherwise, it should return
     * false. A node is unbalanced if either of its children has weight greater than 2/3 of its weight.
     *
     * @param node a node to check balance on
     * @return true if the node is balanced, false otherwise
     */
    public boolean checkBalance(TreeNode node) {
        // TODO: Implement this
        if (node == null) {
            return true;
        }
        double parentNodeWeight = node.weight;
        if(node.left != null && node.right == null) {
            if (node.left.weight > (2.0 / 3.0) * parentNodeWeight) {
                return false;
            }
            return true;
        } else if (node.left == null && node.right != null) {
            if (node.right.weight > (2.0 / 3.0) * parentNodeWeight) {
                return false;
            }
            return true;
        } else {
            return node.left.weight <= (2.0 / 3.0) * parentNodeWeight &&
                    node.right.weight <= (2.0 / 3.0) * parentNodeWeight;
        }
    }

    /**
     * Rebuilds the specified subtree of a node.
     *
     * @param node  the part of the subtree to rebuild
     * @param child specifies which child is the root of the subtree to rebuild
     */
    public void rebuild(TreeNode node, Child child) {
        // Error checking: cannot rebuild null tree
        if (node == null) return;
        // First, retrieve a list of all the nodes of the subtree rooted at child
        TreeNode[] nodeList = enumerateNodes(node, child);
        // Then, build a new subtree from that list
        TreeNode newChild = buildTree(nodeList);
        // Finally, replace the specified child with the new subtree
        if (child == Child.LEFT) {
            node.left = newChild;
        } else if (child == Child.RIGHT) {
            node.right = newChild;
        }
    }

    /**
     * Inserts a key into the tree.
     *
     * @param key the key to insert
     */
    public void insert(int key) {
        if (root == null) {
            root = new TreeNode(key);
            return;
        }

        TreeNode node = root;

        while (true) {
            if (key <= node.key) {
                if (node.left == null) break;
                node.weight++;
                if(!checkBalance(node)) {
                    rebuild(node, Child.LEFT);
                }
                node = node.left;
            } else {
                if (node.right == null) break;
                node.weight++;
                if (!checkBalance(node)) {
                    rebuild(node, Child.RIGHT);
                }
                node = node.right;
            }
        }

        if (key <= node.key) {
            node.left = new TreeNode(key);
        } else {
            node.right = new TreeNode(key);
        }
    }

    // Simple main function for debugging purposes
    public static void main(String[] args) {
        SGTree tree = new SGTree();
        for (int i = 0; i < 100; i++) {
            tree.insert(i);
        }
        tree.rebuild(tree.root, Child.RIGHT);
    }
}
