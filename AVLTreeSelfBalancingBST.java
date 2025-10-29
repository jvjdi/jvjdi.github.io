package io.github.jvjdi;

import static java.lang.Math.*;
import static java.lang.IO.*;

/// @author oberio.stanley@gmail.com
public class AVLTreeSelfBalancingBST {
    public static class Node {
        public int key, height;
        public Node left, right;

        public Node(int key) {
            this.key = key;
        }
    }

    private Node root;

    public int heightOfTree(Node root) {
        return root == null ? -1 : root.height;
    }

    public int balanceFactorOfTree(Node root) {
        return heightOfTree(root.right) - heightOfTree(root.left);
    }

    public Node rotateLeft(Node root) {
        var newRoot = root.right;
        var previousLeftOfNewRootAndSubtreeBetweenRootAndNewRootInInorder =
                newRoot.left;
        newRoot.left = root;
        root.right =
                previousLeftOfNewRootAndSubtreeBetweenRootAndNewRootInInorder;
        root.height = max(heightOfTree(root.left), heightOfTree(root.right)) + 1
                ;
        newRoot.height = max(newRoot.left.height, heightOfTree(newRoot.right)) +
                1;
        return newRoot;
    }

    public Node rotateRight(Node root) {
        var newRoot = root.left;
        var previousRightOfNewRootAndSubtreeBetweenNewRootAndRootInInorder =
                newRoot.right;
        newRoot.right = root;
        root.left =
                previousRightOfNewRootAndSubtreeBetweenNewRootAndRootInInorder;
        root.height = max(heightOfTree(root.left), heightOfTree(root.right)) + 1
                ;
        newRoot.height = max(heightOfTree(newRoot.left), newRoot.right.height) +
                1;
        return newRoot;
    }

    public Node makeSureTreeIsAVLGivenLeftAndRightSubtreesAreAVL(Node root) {
        var balanceFactorOfTree = balanceFactorOfTree(root);
        if (balanceFactorOfTree == -2) {
            if (balanceFactorOfTree(root.left) > 0) {
                root.left = rotateLeft(root.left);
            }
            root = rotateRight(root);
        } else if (balanceFactorOfTree == 2) {
            if (balanceFactorOfTree(root.right) < 0) {
                root.right = rotateRight(root.right);
            }
            root = rotateLeft(root);
        }
        root.height = max(heightOfTree(root.left), heightOfTree(root.right)) + 1
                ;
        return root;
    }

    public static class KeyAlreadyPresentException extends RuntimeException {
        public KeyAlreadyPresentException(String message) {
            super(message);
        }
    }

    public Node insertNode(Node root, int key) throws KeyAlreadyPresentException
            {
        if (root == null) {
            return new Node(key);
        } else {
            if (key < root.key) {
                root.left = insertNode(root.left, key);
            } else if (key > root.key) {
                root.right = insertNode(root.right, key);
            } else {
                throw new KeyAlreadyPresentException(key + " already present");
            }
            return makeSureTreeIsAVLGivenLeftAndRightSubtreesAreAVL(root);
        }
    }

    public AVLTreeSelfBalancingBST insert(int key) {
        try {
            root = insertNode(root, key);
        } catch (KeyAlreadyPresentException _) {
            println(key + " already present");
        }
        return this;
    }

    public void printInorderLNR(Node root) {
        if (root != null) {
            printInorderLNR(root.left);
            print(root.key + " ");
            printInorderLNR(root.right);
        }
    }

    public AVLTreeSelfBalancingBST printInorderLNR() {
        printInorderLNR(root);
        println();
        return this;
    }

    public void printPreorderNLR(Node root) {
        if (root != null) {
            print(root.key + " ");
            printPreorderNLR(root.left);
            printPreorderNLR(root.right);
        }
    }

    public AVLTreeSelfBalancingBST printPreorderNLR() {
        printPreorderNLR(root);
        println();
        return this;
    }

    public void printPostorderLRN(Node root) {
        if (root != null) {
            printPostorderLRN(root.left);
            printPostorderLRN(root.right);
            print(root.key + " ");
        }
    }

    public AVLTreeSelfBalancingBST printPostorderLRN() {
        printPostorderLRN(root);
        println();
        return this;
    }

    public Node inorderPredecessorOfNodeWithLeftChild(Node node) {
        node = node.left;
        while (node.right != null) {
            node = node.right;
        }
        return node;
    }

    public Node inorderSuccessorOfNodeWithRightChild(Node node) {
        node = node.right;
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    public static class KeyNotPresentException extends RuntimeException {
        public KeyNotPresentException(String message) {
            super(message);
        }
    }

    public Node deleteNode(Node root, int key) throws KeyNotPresentException {
        if (root == null) {
            throw new KeyNotPresentException(key + " not present");
        }
        if (key < root.key) {
            root.left = deleteNode(root.left, key);
        } else if (key > root.key) {
            root.right = deleteNode(root.right, key);
        } else {
            if (root.left == null) {
                return root.right;
            }
            if (root.right == null) {
                return root.left;
            }
            if (heightOfTree(root.left) > heightOfTree(root.right)) {
                var inorderPredecessorOfRoot =
                        inorderPredecessorOfNodeWithLeftChild(root);
                root.key = inorderPredecessorOfRoot.key;
                root.left = deleteNode(root.left, inorderPredecessorOfRoot.key);
            } else {
                var inorderSuccessorOfRoot =
                        inorderSuccessorOfNodeWithRightChild(root);
                root.key = inorderSuccessorOfRoot.key;
                root.right = deleteNode(root.right, inorderSuccessorOfRoot.key);
            }
        }
        return makeSureTreeIsAVLGivenLeftAndRightSubtreesAreAVL(root);
    }

    public AVLTreeSelfBalancingBST delete(int key) {
        try {
            root = deleteNode(root, key);
        } catch (KeyNotPresentException _) {
            println(key + " not present");
        }
        return this;
    }

    public final static int UNBALANCED = -2;

    public int heightAndIsBalanced(Node root) {
        if (root == null) {
            return -1;
        }
        var leftHeightAndIsBalanced = heightAndIsBalanced(root.left);
        if (leftHeightAndIsBalanced == UNBALANCED) {
            return UNBALANCED;
        }
        var rightHeightAndIsBalanced = heightAndIsBalanced(root.right);
        if (rightHeightAndIsBalanced == UNBALANCED ||
                abs(leftHeightAndIsBalanced - rightHeightAndIsBalanced) > 1) {
            return UNBALANCED;
        }
        return max(leftHeightAndIsBalanced, rightHeightAndIsBalanced) + 1;
    }

    public boolean isBalanced(Node root) {
        return heightAndIsBalanced(root) != UNBALANCED;
    }

    public boolean isBalanced() {
        return isBalanced(root);
    }

    public static void main(String[] commandLineArguments) {
        println("io.github.jvjdi.AVLTreeSelfBalancingBST\n");

        var avlTreeSelfBalancingBST = new AVLTreeSelfBalancingBST();
        avlTreeSelfBalancingBST.insert(37).insert(18).insert(73).insert(27).
                printInorderLNR().insert(70).insert(64).delete(37).
                printInorderLNR().insert(27).delete(37).printPreorderNLR().
                printPostorderLRN();
        println(avlTreeSelfBalancingBST.isBalanced());
        avlTreeSelfBalancingBST = new AVLTreeSelfBalancingBST();
        avlTreeSelfBalancingBST.insert(3).insert(2).insert(1).printInorderLNR().
                printPreorderNLR().printPostorderLRN();
        avlTreeSelfBalancingBST = new AVLTreeSelfBalancingBST();
        avlTreeSelfBalancingBST.insert(3).insert(1).insert(2).printInorderLNR().
                printPreorderNLR().printPostorderLRN();
        avlTreeSelfBalancingBST = new AVLTreeSelfBalancingBST();
        avlTreeSelfBalancingBST.insert(1).insert(2).insert(3).printInorderLNR().
                printPreorderNLR().printPostorderLRN();
        avlTreeSelfBalancingBST = new AVLTreeSelfBalancingBST();
        avlTreeSelfBalancingBST.insert(1).insert(3).insert(2).printInorderLNR().
                printPreorderNLR().printPostorderLRN();
        avlTreeSelfBalancingBST = new AVLTreeSelfBalancingBST();
        avlTreeSelfBalancingBST.insert(37).insert(18).insert(73).insert(27).
                delete(37).printInorderLNR().printPreorderNLR().
                printPostorderLRN();
        println(avlTreeSelfBalancingBST.isBalanced());
    }
}
