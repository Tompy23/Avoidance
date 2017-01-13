package com.tuc.ai.avoid;

public interface Asteroid extends SpaceObject 
{
	public void init( 
			DoublePoint position,
			double velocity,
			double direction,
			double size,
			double mass );
}
