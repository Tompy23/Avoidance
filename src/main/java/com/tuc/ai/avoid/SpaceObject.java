package com.tuc.ai.avoid;

import java.awt.Rectangle;

import com.tuc.ai.AILogging;

public interface SpaceObject extends Displayable, Restartable, AILogging
{
	public void init( DoublePoint position,
			double velocity,
			double direction,
			double size,
			double mass );
	
	public void updatePosition( double friction, double xExtra, double yExtra );
	public void updatePosition( double friction );
	public boolean handleBoundaries( Rectangle bounds );
	public void setVelocityDirection( double xVelocity, double yVelocity );
	public double rotateXVelocity( double angle );
	public double rotateYVelocity( double angle );
	public double getDistanceFrom( DoublePoint p );
	public double getDirectionFrom( DoublePoint p );
	public DoublePoint getCenter();
	public double getRadius();
	
	public double getDirection();
	public double getVelocity();
	public double getSize();
	public double getMass();
	
}
