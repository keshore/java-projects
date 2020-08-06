package parentchild;

public class A {

	protected class C {
		
	}
	static void a() {
		
		System.out.println("static hello from a");
	};

	void a(int g) {
		C l;
		System.out.println("static hello from " + g);
	};

	protected void run() {
		System.out.println("Helllo from A");
	}

}
