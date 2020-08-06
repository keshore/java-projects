package binarytree;

public class Test {

	static int getRandom() {
		return (int) Math.floor(Math.random() * 100);
	}

	public static void main(String[] args) {
		Node<String> root = new Node<String>("3");
		int i = 0;
		while (i < 5) {
			int value = getRandom();
			root.add(root, value + "");
			i++;
		}
//		System.out.println(root);
		root.print(root);
	}

}
