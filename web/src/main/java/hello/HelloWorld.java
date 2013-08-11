package hello;

public class HelloWorld {

	public String sayHello() {
		return "hello Maven";
	}

	public static void main(String[] args) {
		System.out.println(new HelloWorld().sayHello());
	}
}
