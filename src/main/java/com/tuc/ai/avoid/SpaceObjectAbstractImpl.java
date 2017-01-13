package com.tuc.ai.avoid;

import java.awt.Rectangle;

public abstract class SpaceObjectAbstractImpl implements SpaceObject 
{
	protected DoublePoint position;
	protected double velocity;
	protected double direction;
	
	protected double size;
	protected double mass;
	
	private DoublePoint initPosition = null;
	private double initVelocity = 0;
	private double initDirection = 0;

	@Override
	public void init ( 
			DoublePoint position,
			double velocity,
			double direction,
			double size,
			double mass )
	{
		this.position = new DoublePoint( position.x, position.y );
		this.velocity = velocity;
		this.direction = direction;
		this.size = size;
		this.mass = mass;
		
		initPosition = new DoublePoint( position.x, position.y );
		initVelocity = velocity;
		initDirection = direction;
	}
	
	@Override
	public void restart()
	{
		position = new DoublePoint( initPosition.x, initPosition.y );
		velocity = initVelocity;
		direction = initDirection;
	}
	
	@Override
	public void updatePosition( double friction, double xExtra, double yExtra )
	{
		double velocityX = Math.cos( direction ) * ( velocity - ( velocity * friction ) ) + xExtra;
		double velocityY = Math.sin( direction ) * ( velocity - ( velocity * friction ) ) + yExtra;
		
		direction = Math.atan2( velocityY, velocityX );
		velocity = ( Math.sin( direction ) * velocityY ) + ( Math.cos( direction ) * velocityX );
		
		position.setLocation( 
				position.x + Math.cos( direction ) * velocity, 
				position.y + Math.sin( direction ) * velocity );
	}
	
	@Override 
	public void updatePosition( double friction )
	{
		updatePosition( friction, 0, 0 );
	}

	@Override
	public boolean handleBoundaries( Rectangle r )
	{
		boolean hitBoundary = false;
		if ( position.x >= r.width - size || position.x <= 0 )
		{
			direction += Math.PI - ( 2 * direction );
			hitBoundary = true;
			if ( position.x <= 0 )
			{
				position.x = 1;
			}
			else
			{
				position.x = r.width - size - 1;
			}
		}
		
		if ( position.y <= 0 || position.y >= r.height - size )
		{
			direction -= 2 * direction;
			hitBoundary = true;
			if ( position.y <= 0 )
			{
				position.y = 1;
			}
			else
			{
				position.y = r.height - size - 1;
			}
		}
		
		if ( direction > Math.PI )
		{
			direction -= 2 * Math.PI;
		}
		
		if ( direction < -1 * Math.PI )
		{
			direction += 2 * Math.PI;
		}

		while ( position.x >= r.width - size || position.x <= 0 || position.y <= 0 || position.y >= r.height - size )
		{
			updatePosition( 0, 0, 0 );
		}
		
		return hitBoundary;
	}

	@Override
	public void setVelocityDirection( double x, double y )
	{
		direction = Math.atan2( y, x );
		velocity = Math.sqrt( Math.pow( x, 2 ) + Math.pow( y, 2 ) );
	}

	@Override
	public double rotateXVelocity( double angle )
	{
		return velocity * Math.cos( direction - angle );
	}

	@Override
	public double rotateYVelocity( double angle )
	{
		return velocity * Math.sin( direction - angle );
	}
	
	@Override
	public double getDistanceFrom( DoublePoint p )
	{
		DoublePoint c = getCenter();
		return Math.sqrt( Math.pow( p.y - c.y, 2 ) + Math.pow( p.x - c.x, 2 ) );
	}
	
	@Override
	public double getDirectionFrom( DoublePoint p )
	{
		DoublePoint c = getCenter();
		return Math.atan2( c.y - p.y, c.x - p.x );
	}
	
	@Override
	public DoublePoint getCenter()
	{
		return new DoublePoint( position.x + ( size / 2 ), position.y + ( size / 2 ) );
	}
	
	@Override
	public double getRadius()
	{
		return size / 2;
	}

	@Override
	public double getSize() 
	{
		return size;
	}

	@Override
	public double getMass() 
	{
		return mass;
	}
	
	@Override
	public double getVelocity()
	{
		return velocity;
	}
	
	@Override
	public double getDirection()
	{
		return direction;
	}
}
