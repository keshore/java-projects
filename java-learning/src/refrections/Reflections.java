package refrections;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

class Test {
	private String str;
	public int count;
	protected long length;
	boolean active;

	public String getStr() {
		return str;
	}

	public void setStr(String str) {
		this.str = str;
	}

}

public class Reflections {

	public static void main(String[] args) {
		Class<Test> c = Test.class;
		System.out.println(c.getName());
		for(Method m : c.getMethods()) {
			System.out.println("Method:"+m.getName());
			for(Parameter p : m.getParameters()) {
				System.out.println("Parameter:"+p.getName() + "-" + p.getType());
			}
		}
//		System.out.println(c.getDeclaredFields());
//		System.out.println(c.getDeclaredMethods());
	}

}
