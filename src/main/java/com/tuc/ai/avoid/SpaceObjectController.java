package com.tuc.ai.avoid;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import org.springframework.context.ApplicationContext;

import com.tuc.ai.AIConstants;
import com.tuc.ai.AILogger;
import com.tuc.ai.genalg.Chromosome;
import com.tuc.ai.genalg.ChromosomeDoubleImpl;
import com.tuc.ai.genalg.Crossover;
import com.tuc.ai.genalg.Gene;
import com.tuc.ai.genalg.GeneDoubleImpl;
import com.tuc.ai.genalg.Utility;

public class SpaceObjectController 
{
	private int initNumberOfShips;
	private int initNumberOfAsteroids;
	private Rectangle initPositionBounds;
	private int initSeparation;
	
	private InputAdjustment adjuster;

	private double shipMinVelocity;
	private double shipMaxVelocity;
	private int shipMinSize;
	private int shipMaxSize;
	private double shipMassFactor;
	private double shipMassConstant;
	private double shipMutationRange;
	private double shipMutationRate;
	private Crossover shipCrossoverFunction;
	private double shipCrossoverRate;
	
	private double asteroidSizeMin;
	private double asteroidSizeMax;
	private double asteroidVelocityMin;
	private double asteroidVelocityMax;
	private double asteroidMassFactor;
	private double asteroidMassConstant;
	
	private String shipBeanName;
	private String asteroidBeanName;
	
	private int shipSwapCount;
	
	private List< SpaceObject > spaceObjects = new ArrayList< SpaceObject >();
	private List< Ship > ships = new ArrayList< Ship >();
	
	private AILogger log;
	private int logFrequency;
	private int logFrequencyCount = 0;
	
	private double maxDistance = 0;
	
	public static final int STAT_BEST = 0;
	public static final int STAT_WORST = 1;
	public static final int STAT_AVG = 2;
	private List< long[] > statisticsList = new ArrayList< long[] >();
	
	public SpaceObjectController( 
			int initNumberOfShips,
			int initNumberOfAsteroids,
			int initSeparation,
			int x, 
			int y, 
			int w, 
			int h,
			double shipMinVelocity, 
			double shipMaxVelocity, 
			int shipMinSize, 
			int shipMaxSize, 
			double shipMassFactor, 
			double shipMassConstant, 
			double shipMutationRange,
			double shipMutationRate,
			Crossover shipCrossoverFunction,
			double shipCrossoverRate,
			double asteroidVelocityMin,
			double asteroidVelocityMax,
			double asteroidSizeMin,
			double asteroidSizeMax,
			double asteroidMassFactor, 
			double asteroidMassConstant, 
			String shipBeanName,
			String asteroidBeanName,
			AILogger log,
			int logFrequency,
			int shipSwapCount )
	{
		this.initNumberOfShips = initNumberOfShips;
		this.initNumberOfAsteroids = initNumberOfAsteroids;
		this.initSeparation = initSeparation;
		this.initPositionBounds = new Rectangle( x, y, w, h );
		this.shipMinVelocity = shipMinVelocity;
		this.shipMaxVelocity = shipMaxVelocity;
		this.shipMinSize = shipMinSize;
		this.shipMaxSize = shipMaxSize;
		this.shipMassFactor = shipMassFactor;
		this.shipMassConstant = shipMassConstant;
		this.shipMutationRange = shipMutationRange;
		this.shipMutationRate = shipMutationRate;
		this.shipCrossoverFunction = shipCrossoverFunction;
		this.shipCrossoverRate = shipCrossoverRate;
		this.asteroidVelocityMin = asteroidVelocityMin;
		this.asteroidVelocityMax = asteroidVelocityMax;
		this.asteroidSizeMin = asteroidSizeMin;
		this.asteroidSizeMax = asteroidSizeMax;
		this.asteroidMassFactor = asteroidMassFactor;
		this.asteroidMassConstant = asteroidMassConstant;
		this.shipBeanName = shipBeanName;
		this.asteroidBeanName = asteroidBeanName;
		
		this.log = log;
		this.logFrequency = logFrequency;
		
		this.shipSwapCount = shipSwapCount;
		
		maxDistance = Math.sqrt( h*h + w*w );
	}
	
	public void init( ApplicationContext ctx )
	{
		// Create ships
		for ( int i = 0; i < initNumberOfShips; i++ )
		{
			int size = (int)( AIConstants.RAND.nextDouble() * ( shipMaxSize - shipMinSize ) + shipMinSize );
			
			Ship newShip = (Ship)ctx.getBean( shipBeanName );
			newShip.init( 
					shipMutationRange,
					findPlacement(), 
					AIConstants.RAND.nextDouble() * ( shipMaxVelocity - shipMinVelocity ) + shipMinVelocity,
					AIConstants.RAND.nextDouble() * ( Math.PI ) - ( 2 * Math.PI ),
					size, 
					shipMassConstant + ( size * shipMassFactor ) );
			ships.add( newShip );
			spaceObjects.add( newShip );
		}
		
		// Create Asteroids
		for ( int i = 0; i < initNumberOfAsteroids; i++ )
		{
			int size = (int)( AIConstants.RAND.nextDouble() * ( asteroidSizeMax - asteroidSizeMin ) + asteroidSizeMin );
			
			Asteroid newAsteroid = (Asteroid)ctx.getBean( asteroidBeanName );
			newAsteroid.init( 
					findPlacement(), 
					AIConstants.RAND.nextDouble() * ( asteroidVelocityMax - asteroidVelocityMin ) + asteroidVelocityMin,
					AIConstants.RAND.nextDouble() * ( Math.PI ) - ( 2 * Math.PI ),
					size, 
					asteroidMassConstant + ( size * asteroidMassFactor ) );
			spaceObjects.add( newAsteroid );
		}
	}
	
	private DoublePoint findPlacement()
	{
		double x = 0;
		double y = 0;
		boolean placementOk = false;

		while ( ! placementOk )
		{
			x = AIConstants.RAND.nextDouble() * ( initPositionBounds.width - initPositionBounds.x ) + initPositionBounds.x;
			y = AIConstants.RAND.nextDouble() * ( initPositionBounds.height - initPositionBounds.y ) + initPositionBounds.y;
			placementOk = true;
			for ( SpaceObject spaceObject : spaceObjects )
			{
				if ( 0 > Double.compare( spaceObject.getDistanceFrom( new DoublePoint( x, y ) ), initSeparation ) )
				{
					placementOk = false;
					break;
				}
			}
		}
		
		return new DoublePoint( x, y );
	}
	public void restart()
	{
		for ( SpaceObject spaceObject : spaceObjects )
		{
			spaceObject.restart();
		}
	}
	
	public void display( Graphics2D g )
	{
		for ( SpaceObject spaceObject : spaceObjects )
		{
			spaceObject.display( g );
		}
	}
	
	public List< long[] > getStats()
	{
		return statisticsList;
	}
	
	public void feedShipsInformation( Rectangle bounds )
	{
		double farthest = Math.sqrt( Math.pow( bounds.width - bounds.x, 2 ) + Math.pow( bounds.height - bounds.y, 2 ) );
		
		int shipIndex = 0;
		for ( Ship ship : ships )
		{
			DoublePoint[] nearObjects = new DoublePoint[ ship.getNumberTracking() ];

			DoublePoint c = ship.getCenter();
			double r = ship.getRadius();
			for ( int i = 0; i < ship.getNumberTracking(); i++ )
			{
				nearObjects[ i ] = new DoublePoint( farthest, 0 );
			}

			// Check bounds
			
			subFarthest( nearObjects, new DoublePoint( c.x - r - bounds.x, ship.getDirectionFrom( new DoublePoint( bounds.x, c.y ) ) ) );
			subFarthest( nearObjects, new DoublePoint( bounds.width - c.x - r, ship.getDirectionFrom( new DoublePoint( bounds.width, c.y ) ) ) );
			subFarthest( nearObjects, new DoublePoint( c.y - r - bounds.y, ship.getDirectionFrom( new DoublePoint( c.x, bounds.y ) ) ) );
			subFarthest( nearObjects, new DoublePoint( bounds.height - c.y - r, ship.getDirectionFrom( new DoublePoint( c.x, bounds.height ) ) ) );
 
			// Check all other ships
			for ( SpaceObject other : spaceObjects )
			{
				if ( ship != other )
				{
					DoublePoint oc = other.getCenter();
					double or = other.getRadius();
					subFarthest( nearObjects, new DoublePoint( ship.getDistanceFrom( oc ) - r - or, ship.getDirectionFrom( oc ) ) );
				}
			}
			
			// Create input factors
			int index = 0;
			//double[] info = new double[ ( ship.getNumberTracking() * 2 ) + 2 ];
			double[] info = new double[ ( ship.getNumberTracking() * 2 ) ];
			//info[ index++ ] = adjuster.adjustVelocity( ship.getVelocity() );
			//info[ index++ ] = adjuster.adjustAngle( ship.getDirection() );
			for ( int i = 0; i < ship.getNumberTracking(); i++ )
			{
				info[ index++ ] = adjuster.adjustDistance( nearObjects[ i ].x, maxDistance );
				info[ index++ ] = adjuster.adjustAngle( nearObjects[ i ].y );
			}
			
			ship.adjustThrusters( info );

			if ( logFrequencyCount >= logFrequency )
			{
				log.info( "Ship #" + shipIndex++ + ":" );
				for ( int i = 0; i < info.length; i++ )
				{
					log.info( "Input #" + i + ": " + info[ i ] );
				}
				
				ship.log( null, 2 );
				ship.log( null, 1 );
			}
		}
		if ( logFrequencyCount >= logFrequency )
		{
			logFrequencyCount = 0;
		}
		logFrequencyCount++;
	}
	
	private void subFarthest( DoublePoint[] list, DoublePoint check )
	{
		for ( int i = 0; i < list.length; i++ )
		{
			if ( 0 > Double.compare( check.x, list[ i ].x ) )
			{
				for ( int j = list.length - 1; j > i; j-- )
				{
					list[ j ].setLocation( list[ j -1 ].x, list[ j -1 ].y );
				}
				list[ i ].x = check.x;
				list[ i ].y = check.y;
				break;
			}
		}
	}
	
	// returns true if equilibrium has been reached.
	public boolean endOfEpoch()
	{
		boolean returnValue = true;
		
		// Weed out a couple of nasties
		SortedSet< Ship > sortedShips = new TreeSet< Ship >();
		int totalCollisions = 0;
		for ( Ship ship : ships )
		{
			sortedShips.add( ship );
			totalCollisions += ship.getCollisionCount();
		}
		Ship[] best = new Ship[ sortedShips.size() ];
		int index = 0;
		for ( Ship ship : sortedShips )
		{
			best[ index++ ] = ship;
		}
		
		if ( sortedShips.size() >= ( shipSwapCount * 2 ) + 2 )
		{
			index = 1;
			for ( int i = 0; i < shipSwapCount; i++ )
			{
//				if ( ! best[ sortedShips.size() - index++ ].isSupreme() )
//				{
					best[ sortedShips.size() - index++ ].extractBrain().setWeights( best[ i ].extractBrain().getWeights() );
//				}
			}
		}
		
		long[] statistics = new long[ 3 ];
		statistics[ STAT_BEST ] = best[ 0 ].getCollisionCount();
		statistics[ STAT_WORST ] = best[ sortedShips.size() - 1 ].getCollisionCount();
		statistics[ STAT_AVG ] = totalCollisions/ships.size();
		statisticsList.add( statistics );
		
		if ( statisticsList.size() > 10 )
		{
			long[] statsTest = new long[ 3 ];
			statsTest[ STAT_BEST ] = statistics[ STAT_BEST ];
			statsTest[ STAT_WORST ] = statistics[ STAT_WORST ];
			statsTest[ STAT_AVG ] = statistics[ STAT_AVG ];
			for ( int i = statisticsList.size() - 1; i > statisticsList.size() - 4; i-- )
			{
				if ( statsTest[ STAT_BEST ] != statisticsList.get( i )[ STAT_BEST ]
					|| statsTest[ STAT_WORST ] != statisticsList.get( i )[ STAT_WORST ] 
					|| statsTest[ STAT_AVG ] != statisticsList.get( i )[ STAT_AVG ] )
				{
					returnValue = false;
					break;
				}
			}
		}
		else
		{
			returnValue = false;
		}
		List< Chromosome > chromosomes = new ArrayList< Chromosome >();
		for ( Ship ship : ships )
		{
			double[] d = ship.extractBrain().getWeights();
			Gene< Double >[] genes = new GeneDoubleImpl[ d.length ];
			
			for ( int i = 0; i < d.length; i++ )
			{
				genes[ i ] = new GeneDoubleImpl( shipMutationRange );
				genes[ i ].setGene( d[ i ] );
			}
			ship.splice( 0, genes.length, genes );
			chromosomes.add( ship );
			
		}

		List< Chromosome > children = new ArrayList< Chromosome >();
		for ( int i = 0; i < ships.size(); i += 2 )
		{
			// Determine suitable parents
			Ship a1 = getWeightedShip( ships );
			chromosomes.remove( a1 );
			Ship a2 = getWeightedShip( ships );
			chromosomes.remove( a2 );
			
			if ( a1.getCollisionCount() > a2.getCollisionCount() )
			{
				Ship a3 = a1;
				a1 = a2;
				a2 = a3;
			}
			
			// mate
			shipCrossoverFunction.mate( 
					a1, 
					a2, 
					new ChromosomeDoubleImpl( chromosomes.get( 0 ).getNumberOfGenes(), shipMutationRange ), 
					new ChromosomeDoubleImpl( chromosomes.get( 0 ).getNumberOfGenes(), shipMutationRange ), 
					children, 
					a1.getNumberOfGenes(), 
					shipCrossoverRate );
		}
		
		
		// Mutate
		Utility.mutate( shipMutationRate, children );

		// Repopulate
		int chromoIndex = 0;
		for ( Ship ship : ships )
		{
			Gene< ? >[] genes = children.get( chromoIndex++ ).slice( 0, ship.getBrainSize() );
			double[] weights = new double[ genes.length ];
			for ( int i = 0; i < genes.length; i++ )
			{
				weights[ i ] = (Double)genes[ i ].getGene();
			}
			if ( ! ship.isSupreme() )
			{
				ship.extractBrain().setWeights( weights );
			}
			ship.resetCollisionCount( totalCollisions );
		}
		
		return returnValue;
	}
	
	private Ship getWeightedShip( List< Ship > ships )
	{
		double totalScore = 0;
		double[] rawScoreWheel = new double[ ships.size() ];
		int index = 0;
		for ( Ship ship : ships )
		{
			double score = ship.score( null );
			totalScore += score;
			rawScoreWheel[ index++ ] = score;
		}
		

		Ship ship = null;
		double spinner = AIConstants.RAND.nextDouble() * totalScore;
		double runningTotal = 0;
		for ( int i = 0; i < ships.size(); i++ )
		{
			if ( 0 < Double.compare( rawScoreWheel[ i ] + runningTotal, spinner ) )
			{
				ship = ships.get( i );
				break;
			}
			runningTotal += rawScoreWheel[ i ];
		}
		
		return ship;
	}

	public void compute( Rectangle bounds, double friction )
	{
		for ( SpaceObject spaceObject : spaceObjects )
		{
			spaceObject.updatePosition( friction, 0, 0 );
			spaceObject.handleBoundaries( bounds );
		}
		
		for ( int i = 0; i < spaceObjects.size(); i++ )
		{
			for ( int j = 0; j < spaceObjects.size(); j++ )
			{
				if ( i < j )
				{
					SpaceObject b1 = spaceObjects.get( i );
					SpaceObject b2 = spaceObjects.get( j );
					if ( detectCollision( b1, b2 ) )
					{
						handleCollision( b1, b2, friction );
					}
				}
			}
		}
	}
	
	private void handleCollision( SpaceObject ship1, SpaceObject ship2, double friction )
	{
		if ( ships.contains( ship1 ) )
		{
			((Collidable)(ship1)).incrCollisions( 1 );
		}
		if ( ships.contains( ship2 ) )
		{
			((Collidable)(ship2)).incrCollisions( 1 );
		}
		DoublePoint c1 = ship1.getCenter();
		DoublePoint c2 = ship2.getCenter();
		double angle = Math.atan2( ( c1.y - c2.y ), ( c1.x - c2.x ) );
		
		double rotatedVelocityX1 = ship1.rotateXVelocity( angle );
		double rotatedVelocityY1 = ship1.rotateYVelocity( angle );
		double rotatedVelocityX2 = ship2.rotateXVelocity( angle );
		double rotatedVelocityY2 = ship2.rotateYVelocity( angle );

		double finalVelocityX1 = ( ( ship1.getMass() - ship2.getMass() ) * rotatedVelocityX1 + ( ship2.getMass() + ship2.getMass() ) * rotatedVelocityX2 ) / ( ship1.getMass() + ship2.getMass() );
		double finalVelocityX2 = ( ( ship1.getMass() + ship1.getMass() ) * rotatedVelocityX1 + ( ship2.getMass() - ship1.getMass() ) * rotatedVelocityX2 ) / ( ship1.getMass() + ship2.getMass() );
		double finalVelocityY1 = rotatedVelocityY1;
		double finalVelocityY2 = rotatedVelocityY2;
		
		double newVelocityX1 = Math.cos( angle ) * finalVelocityX1 + Math.cos( angle + Math.PI / 2 ) * finalVelocityY1;
		double newVelocityY1 = Math.sin( angle ) * finalVelocityX1 + Math.sin( angle + Math.PI / 2 ) * finalVelocityY1;
		double newVelocityX2 = Math.cos( angle ) * finalVelocityX2 + Math.cos( angle + Math.PI / 2 ) * finalVelocityY2;
		double newVelocityY2 = Math.sin( angle ) * finalVelocityX2 + Math.sin( angle + Math.PI / 2 ) * finalVelocityY2;
		
		ship1.setVelocityDirection( newVelocityX1, newVelocityY1 );
		ship2.setVelocityDirection( newVelocityX2, newVelocityY2 ); 

		ship1.updatePosition( friction, 0, 0 );
		ship2.updatePosition( friction, 0, 0 );

		while ( detectCollision( ship1, ship2 ) )
		{
			ship1.updatePosition( 0 );
			ship2.updatePosition( 0 );
		}
	}
	
	private boolean detectCollision( SpaceObject ship1, SpaceObject ship2 )
	{
		boolean collision = false;
		if (0 < Double.compare( ship1.getRadius() + ship2.getRadius(), distance( ship1.getCenter(), ship2.getCenter() ) ) )
		{
			collision = true;
		}
		return collision;
	}
	
	private double distance( DoublePoint p1, DoublePoint p2 )
	{
		return Math.sqrt( 
				Math.pow( p1.x - p2.x, 2 ) +
				Math.pow( p1.y - p2.y, 2 ) );
	}

	public void setAdjuster(InputAdjustment adjuster) {
		this.adjuster = adjuster;
	}
	
}
