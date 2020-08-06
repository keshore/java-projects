package binarytree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Node<T extends Comparable<T>> {
	private Node<T> left;
	private Node<T> right;
	long height;
	long diffHeight;
	private T value;

	public Node(T value) {
		this.setValue(value);
		this.height = 1;
	}

	protected Node<T> add(Node<T> n, T value) {
		if (n == null) {
			return new Node<T>(value);
		}
		if (n.getValue().compareTo(value) > 0) {
			n.setLeft(add(n.getLeft(), value));
		} else if (n.getValue().compareTo(value) < 0) {
			n.setRight(add(n.getRight(), value));
		} else {
			return n;
		}
		n.height = 1+ this.getMaxHeight(n.getLeft(), n.getRight());
		n.diffHeight = this.getHeightDiff(n.getLeft(), n.getRight());
		return n;
	}

	private long getHeight(Node<T> n) {
		if (n == null) {
			return 0;
		}
		return n.height;
	}

	private long getMaxHeight(Node<T> left, Node<T> right) {
		return this.getHeight(left) > this.getHeight(right) ? this.getHeight(left) : this.getHeight(right);
	}

	private long getHeightDiff(Node<T> left, Node<T> right) {
		return this.getHeight(left) - this.getHeight(right);
	}

	public Node<T> getLeft() {
		return left;
	}

	public void setLeft(Node<T> left) {
		this.left = left;
	}

	public Node<T> getRight() {
		return right;
	}

	public void setRight(Node<T> right) {
		this.right = right;
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

	public void print(Node<T> n) {
		new Printer<T>(n);
	}

	@Override
	public String toString() {
		return "Node [left=" + left + ", right=" + right + ", height=" + height + ", value=" + value + "]";
	}

}

class Printer<T> {
	public <T extends Comparable<T>> Printer(Node<T> root) {
		int maxLevel = maxLevel(root);
		printNodeInternal(Collections.singletonList(root), 1, maxLevel);
	}

	private <T extends Comparable<T>> void printNodeInternal(List<Node<T>> nodes, int level, int maxLevel) {
		if (nodes.isEmpty() || isAllElementsNull(nodes))
			return;
		int floor = maxLevel - level;
		int endgeLines = (int) Math.pow(2, (Math.max(floor - 1, 0)));
		int firstSpaces = (int) Math.pow(2, (floor)) - 1;
		int betweenSpaces = (int) Math.pow(2, (floor + 1)) - 1;
		printWhitespaces(firstSpaces);
		List<Node<T>> newNodes = new ArrayList<Node<T>>();
		for (Node<T> node : nodes) {
			if (node != null) {
				System.out.print(node.getValue() + "," + node.height + ":" + node.diffHeight);
				newNodes.add(node.getLeft());
				newNodes.add(node.getRight());
			} else {
				newNodes.add(null);
				newNodes.add(null);
				System.out.print(" ");
			}
			printWhitespaces(betweenSpaces);
		}
		System.out.println("");
		for (int i = 1; i <= endgeLines; i++) {
			for (int j = 0; j < nodes.size(); j++) {
				printWhitespaces(firstSpaces - i);
				if (nodes.get(j) == null) {
					printWhitespaces(endgeLines + endgeLines + i + 1);
					continue;
				}
				if (nodes.get(j).getLeft() != null)
					System.out.print("/");
				else
					printWhitespaces(1);
				printWhitespaces(i + i - 1);
				if (nodes.get(j).getRight() != null)
					System.out.print("\\");
				else
					printWhitespaces(1);
				printWhitespaces(endgeLines + endgeLines - i);
			}
			System.out.println("");
		}
		printNodeInternal(newNodes, level + 1, maxLevel);
	}

	private void printWhitespaces(int count) {
		for (int i = 0; i < count; i++)
			System.out.print(" ");
	}

	private <T extends Comparable<T>> int maxLevel(Node<T> node) {
		if (node == null)
			return 0;
		return Math.max(maxLevel(node.getLeft()), maxLevel(node.getRight())) + 1;
	}

	private <T> boolean isAllElementsNull(List<T> list) {
		for (Object object : list) {
			if (object != null)
				return false;
		}
		return true;
	}
}
