package com.tuc.ai.avoid;

public interface InputAdjustment 
{
	public double adjustDistance( double distance, double maxDistance );
	public double adjustVelocity( double velocity );
	public double adjustAngle( double angle );
}
