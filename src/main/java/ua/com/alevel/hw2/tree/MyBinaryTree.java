package ua.com.alevel.hw2.tree;

import ua.com.alevel.hw2.model.fighter.Fighter;
import ua.com.alevel.hw2.model.Plane;
import ua.com.alevel.hw2.model.comparator.PlaneComparator;
import ua.com.alevel.hw2.service.services.FighterService;

public class MyBinaryTree<E extends Plane> {
    private Node root;
    private final PlaneComparator<E> planeComparator;

    private class Node {
        public E plane;
        public Node left;
        public Node right;

        public Node() {
        }

        public Node(E plane, Node left, Node right) {
            this.plane = plane;
            this.left = left;
            this.right = right;
        }
    }

    public MyBinaryTree() {
        planeComparator = new PlaneComparator<>();
    }

    public void add(E plane) {
        root = insert(root, plane);
    }

    private Node insert(Node current, E plane) {
        if (current == null) {
            return new Node(plane, null, null);
        }

        if (planeComparator.compare(current.plane, plane) > 0) {
            current.left = insert(current.left, plane);
        }
        else if (planeComparator.compare(current.plane, plane) < 0) {
            current.right = insert(current.right, plane);
        }

        return current;
    }

    public int sumOfPricesOfLeftBranch() {
        if (root.left == null) {
            return 0;
        }
        return sum(root.left);
    }

    public int sumOfPricesOfRightBranch() {
        if (root.right == null) {
            return 0;
        }
        return sum(root.right);
    }

    private int sum(Node node) {
        if (node == null) {
            return 0;
        }
        return node.plane.getPrice() + sum(node.left) + sum(node.right);
    }

    public void print() {
        printTree(root, 0);
    }

    private void printTree(Node node, int l) {
        if (node != null) {
            printTree(node.right, l + 1);

            printIndent(l);
            System.out.printf("%5s", node.plane.getClass().getSimpleName());
            System.out.println();

            printIndent(l);
            System.out.printf("ID:%5s", node.plane.getId());
            System.out.println();

            printIndent(l);
            System.out.printf("Price:%5d", node.plane.getPrice());
            System.out.println();

            printTree(node.left, l + 1);
        }
        else {
            System.out.println();
        }
    }

    private void printIndent(int l) {
        for (int i = 0; i < l; i++) {
            System.out.print("                ");
        }
    }

    public static void main(String[] args) {
        FighterService fighterService = FighterService.getInstance();
        for (int i = 0; i < 10; i++) {
            fighterService.save(fighterService.createPlane(1)[0]);
        }

        MyBinaryTree<Fighter> tree = new MyBinaryTree<>();
        fighterService.findAll().forEach(plane -> tree.add(plane));
        tree.print();

        System.out.println("\n\n\n\n\n");
        System.out.println("Left branch sum: " + tree.sumOfPricesOfLeftBranch());
        System.out.println("Right branch sum: " + tree.sumOfPricesOfRightBranch());
    }
}
