//package binarytree;
//
//public class BST {
//
//	private Node<Integer> root;
//
//	public void add(int i) {
//		if (root == null) {
//			root = new Node<Integer>(i);
//		} else {
//			Node<Integer> n = root;
//			while (n.getValue() != i) {
//				if (n.getValue() < i) {
//					if (n.getRight() == null) {
//						n.setRight(new Node<Integer>(i));
//					}
//					n = n.getRight();
//				} else {
//					if (n.getLeft() == null) {
//						n.setLeft(new Node<Integer>(i));
//					}
//					n = n.getLeft();
//				}
//			}
//		}
//	}
//
//	private void print(Node<Integer> n) {
//		if (n != null) {
//			System.out.print(n.getValue() + ",");
//			print(n.getLeft());
//			print(n.getRight());
//		}
//	}
//
//	public void print() {
//		print(root);
//	}
//}
