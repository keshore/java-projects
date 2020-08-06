package parentchild;

public class Test {
	String name;

	void setName(Test tv, String name) {
		tv.name = name;
	}

	public static void main(String args[]) throws NullPointerException {
		Test t = new Test();
		Test t1 = new Test();
		t1.setName(t, "Hello");
		System.out.println(t.name);
	}
}
