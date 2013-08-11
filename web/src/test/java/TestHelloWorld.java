import static org.junit.Assert.*;
import hello.HelloWorld;

import org.junit.Test;

public class TestHelloWorld {

	@Test
	public void testSayHello() {
		HelloWorld hw = new HelloWorld();
		assertEquals("hello Maven", hw.sayHello());
	}

}
