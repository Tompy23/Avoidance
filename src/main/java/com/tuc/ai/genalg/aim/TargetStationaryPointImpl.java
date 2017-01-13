package com.tuc.ai.genalg.aim;

import java.io.PrintStream;

public class TargetStationaryPointImpl implements Target2D
{
	private int xValue;
	private int yValue;
	
	public TargetStationaryPointImpl( int x, int y )
	{
		xValue = x;
		yValue = y;
	}
	
	@Override
	public double getMissDistance( double x, double y ) 
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
