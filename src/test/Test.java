package test;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class Test {

	private static final Logger logger = LogManager.getLogger(Test.class);
	
	public static void main(String[] args) {		
		logger.trace("Hello, World!");
	}
}
