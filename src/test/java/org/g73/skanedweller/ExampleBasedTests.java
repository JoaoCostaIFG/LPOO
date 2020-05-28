package org.g73.skanedweller;

import net.jqwik.api.*;

public class ExampleBasedTests {
	@Example
	boolean add1plu3is4() {
		return (1 + 3) == 4;
	}
}
