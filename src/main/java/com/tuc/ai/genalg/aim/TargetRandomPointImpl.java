package com.tuc.ai.genalg.aim;

import java.io.PrintStream;
import java.util.Random;

public class TargetRandomPointImpl implements Target2D
{
	private int xValue;
	private int yValue;

	private Random numGen = new Random( System.currentTimeMillis() );
	
	public void init()
	{
		xValue = numGen.nextInt();
		yValue = numGen.nextInt();
	}

	@Override
	public double getMissDistance(double x, double y) 
	{
		return Math.sqrt( Math.pow( x - xValue, 2 ) + Math.pow( y - yValue, 2) );
	}
	
	@Override
	public void display( PrintStream ps )
	{
		ps.println( "X: " + xValue );
		ps.println( "Y: " + yValue );
	}

}
