package com.tuc.ai.avoid;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

import com.tuc.ai.AILogger;


public class AsteroidImpl extends SpaceObjectAbstractImpl implements Asteroid 
{
	@Override 
	public void updatePosition( double friction, double xExtra, double yExtra )
	{
		double velocityX = Math.cos( direction ) * velocity;
		double velocityY = Math.sin( direction ) * velocity;
		
		direction = Math.atan2( velocityY, velocityX );
		velocity = ( Math.sin( direction ) * velocityY ) + ( Math.cos( direction ) * velocityX );
		
		position.setLocation( 
				position.x + Math.cos( direction ) * velocity, 
				position.y + Math.sin( direction ) * velocity );
	}

	@Override
	public void display( Graphics g ) 
	{
		Point p = new Point();
		p.setLocation( position.x, position.y );
		Graphics2D g2d = (Graphics2D)g;
		g2d.setColor( new Color( 200, 80, 50 ) );
		g2d.fillOval( p.x, p.y, (int)size, (int)size );
	}
	
	@Override
	public void log( AILogger parentLog, int level) {
		// TODO Auto-generated method stub
		
	}

}
