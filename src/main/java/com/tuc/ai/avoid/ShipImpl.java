package com.tuc.ai.avoid;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.PrintStream;

import com.tuc.ai.AILogger;
import com.tuc.ai.genalg.Chromosome;
import com.tuc.ai.genalg.ChromosomeDoubleImpl;
import com.tuc.ai.genalg.Gene;
import com.tuc.ai.nernet.NeuralNetwork;

public class ShipImpl extends SpaceObjectAbstractImpl implements Ship
{
	public final static int THRUSTER_COUNT = 4;
	public final static int TOP_THRUST = 0;
	public final static int RIGHT_THRUST = 1;
	public final static int BOTTOM_THRUST = 2;
	public final static int LEFT_THRUST = 3;
	
	// Action factors
	private double[] thrust = new double[ THRUSTER_COUNT ];

	private double maxThrust = 1;
	
	// Brains
	private NeuralNetwork brain;
	private int numberTracking;
	
	private long collisions = 1;
	private double distanceTraveled = 0;
	
	private Chromosome chromosome;
	
	private int supremeCount;
	private boolean supreme = false;
	
	private AILogger log;
	
	public ShipImpl( NeuralNetwork brain, double maxThrust, int numberTracking, int supremeCount, AILogger log )
	{
		super();
		this.brain = brain;
		this.maxThrust = maxThrust;
		this.numberTracking = numberTracking;
		this.supremeCount = supremeCount;
		
		this.log = log;
		
	}
	
	public void init(
			double mutationRange,
			DoublePoint position,
			double velocity,
			double direction,
			double size,
			double mass )
	{
		this.chromosome = new ChromosomeDoubleImpl( brain.getWeights().length, mutationRange );
		super.init( position, velocity, direction, size, mass );
	}
	
	@Override
	public void incrCollisions( long x )
	{
		collisions += x;
	}
	
	@Override
	public long getCollisionCount()
	{
		
		return (long)collisions;
	}
	
	@Override
	public void display( Graphics g )
	{
		Point p = new Point();
		p.setLocation( position.x, position.y );
		Graphics2D g2d = (Graphics2D)g;
		
		int redNumber = 0;
		int greenNumber = 0;
		int blueNumber = 0;
		
		if ( collisions < 255 )
		{
			blueNumber = (int)(collisions * 3 >= 255 ? 255 : collisions * 3 );
			greenNumber = 255 - blueNumber;
		}
		if ( collisions >= 255 )
		{
			redNumber = (int)((collisions - 255) * 5 >= 255 ? 255 : (collisions - 255) * 5);
		}
		if ( supreme )
		{
			g2d.setColor( new Color( 255, 200, 0 ) );
		}
		else
		{
			g2d.setColor( new Color( redNumber, greenNumber, blueNumber ) );
		}
		g2d.fillOval( p.x, p.y, (int)size, (int)size );
	}
	
	@Override
	public NeuralNetwork extractBrain()
	{
		return brain;
	}
	
	@Override
	public void implantBrain( NeuralNetwork b )
	{
		brain = b;
	}
	
	@Override
	public void adjustThrusters( double[] inputs )
	{
		double[] tmpThrust = brain.update( inputs );
		
		double totThrust = 0;
		for ( int i = 0; i < tmpThrust.length; i++ )
		{
			totThrust += Math.abs( tmpThrust[ i ] );
		}
		
		for ( int i = 0; i < tmpThrust.length; i++ )
		{
			tmpThrust[ i ] = maxThrust * ( tmpThrust[ i ] / totThrust );
		}
		
		thrust = tmpThrust;
	}

	@Override
	public void updatePosition( double friction, double xExtra, double yExtra )
	{
		super.updatePosition( 
				friction,
				thrust[ LEFT_THRUST ] - thrust[ RIGHT_THRUST ],
				thrust[ TOP_THRUST ] - thrust[ BOTTOM_THRUST ] );
		
		distanceTraveled += velocity;
	}
	
	@Override
	public boolean handleBoundaries( Rectangle r )
	{
		if ( super.handleBoundaries( r ) )
		{
			incrCollisions( 8 );
		}
		
		return false;
	}
	
	@Override
	public int getBrainSize() 
	{
		return chromosome.getNumberOfGenes();
	}

	@Override
	public double score(Chromosome chromosome) 
	{
		return this.collisions;
	}

	@Override
	public boolean pass(double score) 
	{
		return false;
	}

	@Override
	public Chromosome getChromosome() 
	{
		return chromosome;
	}

	public void setChromosome(Chromosome chromosome) 
	{
		this.chromosome = chromosome;
	}

	@Override
	public void display(PrintStream ps) 
	{
		chromosome.display( ps );
	}

	@Override
	public void mutate(double rate) 
	{
		chromosome.mutate( rate );
	}

	@Override
	public Gene<?>[] slice(int start, int end) 
	{
		return chromosome.slice( start, end );
	}

	@Override
	public void splice(int start, int end, Gene<?>[] partialChromosome) 
	{
		chromosome.splice( start, end, partialChromosome );
	}

	@Override
	public int getNumberOfGenes() 
	{
		return chromosome.getNumberOfGenes();
	}

	@Override
	public void resetCollisionCount( long total ) 
	{	
		collisions = 1;
	}

	@Override
	public int compareTo(Ship arg0) 
	{	
		if ( getCollisionCount() == arg0.getCollisionCount() )
		{
			return 0;
		}
		return ( 0 < getCollisionCount() - arg0.getCollisionCount() ) ? 1 : -1 ;
	}

	@Override
	public int getNumberTracking()
	{
		return numberTracking;
	}
	
	@Override
	public boolean isSupreme()
	{
		if ( collisions < supremeCount )
		{
			supreme = true;
		}
		else if ( collisions > supremeCount * 2 )
		{
			supreme = false;
		}
		
		return supreme;
	}
	
	@Override
	public void log( AILogger parentLog, int level )
	{
		switch( level )
		{
			case 1:
				brain.log( parentLog, level );
				break;
			case 2:
				for ( int i = 0; i < THRUSTER_COUNT; i++ )
				{
					log.info( "Output #" + i + ": " + thrust[ i ] );
				}
				break;
		}
	}
}
