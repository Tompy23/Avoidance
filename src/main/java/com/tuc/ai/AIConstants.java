package com.tuc.ai;

import java.util.Random;

public final class AIConstants 
{
	// Return status codes
	public static final int SUCCESS_STATUS = 0;
	public static final int FAILURE_STATUS = 5;
	
	// Property name constants
	public static final String PROP_HOME = "PROP_HOME";
	
	// Spring bean names
	public static final String SPRING_MAIN = "spring.main";
	public static final String MAIN_PROCESS = "main.process";
	
	// Random Number Generator
	public static final Random RAND = new Random( System.currentTimeMillis() );

}
