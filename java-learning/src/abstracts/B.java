package abstracts;

public class B extends A {
	
	public B() {
		super();
	}

	static void a() {
		System.out.println("static from B");
	};
	
	@Override
	void run() {
		
	}
	
	void execute(String h, int b) {
		System.out.println();
		super.execute();
	}
	
	void execute(int h, String l) {
		
	}
	
	@Override
	void execute() {
		System.out.println();
		super.execute();
	}

}
