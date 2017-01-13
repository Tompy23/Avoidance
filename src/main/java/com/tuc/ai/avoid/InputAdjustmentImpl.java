package com.tuc.ai.avoid;

public class InputAdjustmentImpl implements InputAdjustment 
{
	private int distanceModes;
	private int velocityModes;
	private int maxVelocity;
	private int angleModes;
	private double distanceExponent;

	@Override
	public double adjustDistance(double distance, double maxDistance) 
	{
		//return (double)Math.round( ( distance * distanceModes ) / maxDistance );
		return (double)( distanceModes - ( Math.round( Math.pow( 1 - ( distance / maxDistance ), distanceExponent ) * distanceModes ) ) );
	}
	@Override
	public double adjustVelocity(double velocity) 
	{
		return (double)Math.round( ( 10 * velocity * velocityModes  ) / maxVelocity );
	}

	@Override
	public double adjustAngle(double angle) 
	{
		if ( 0 > angle )
		{
			angle += Math.PI;
		}
		return (double)Math.round( ( angle * angleModes ) / Math.PI );
	}

	public void setDistanceModes(int distanceModes) {
		this.distanceModes = distanceModes;
	}

	public void setVelocityModes(int velocityModes) {
		this.velocityModes = velocityModes;
	}

	public void setAngleModes(int angleModes) {
		this.angleModes = angleModes;
	}

	public void setMaxVelocity(int maxVelocity) {
		this.maxVelocity = maxVelocity;
	}
	public void setDistanceExponent(double distanceExponent) {
		this.distanceExponent = distanceExponent;
	}

}
