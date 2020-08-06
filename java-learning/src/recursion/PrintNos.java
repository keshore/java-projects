package recursion;

public class PrintNos {

	static String printNos(int i) {
		return "" + printNos(--i);
	}

	public static void main(String[] args) {
		System.out.println(printNos(4));
	}

}
