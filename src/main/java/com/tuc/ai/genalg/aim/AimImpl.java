package com.tuc.ai.genalg.aim;

import java.io.PrintStream;

import com.tuc.ai.genalg.ChromosomeDisplay;
import com.tuc.ai.genalg.Chromosome;
import com.tuc.ai.genalg.Fitness;
import com.tuc.ai.genalg.Gene;
import com.tuc.ai.genalg.GeneDecoder;

public class AimImpl implements Fitness, GeneDecoder< Integer >, ChromosomeDisplay
{
	private static final int ELEMENT_COUNT = 5;
	private static final int ROTATION = 0;
	private static final int ANGLE = 1;
	private static final int VELOCITY = 2;
	private static final int X_ORIGIN = 3;
	private static final int Y_ORIGIN = 4;
	
	private static final int REAL_COUNT = 6;
	private static final int REAL_ROTATION = 0;
	private static final int REAL_ANGLE = 1;
	private static final int REAL_VELOCITY = 2;
	private static final int REAL_DISTANCE = 3;
	private static final int REAL_X = 4;
	private static final int REAL_Y = 5;
	
	private static final double G = 32;
	
	private int elementSize;
	
	private Target2D target;
	
	private int scoreGoal;
	
	public AimImpl( int eSize, Target2D t, int sGoal )
	{
		elementSize = eSize;
		target = t;
		scoreGoal = sGoal;
	}
	
	
	@Override
	public double score( Chromosome chromosome ) 
	{
		Integer[] elements = decode( chromosome.slice( 0, chromosome.getNumberOfGenes() ) );
		
		double[] realValues = calculateRealValues( elements );
		
		return target.getMissDistance( realValues[ REAL_X ], realValues[ REAL_Y ] );
	}
	
	private double[] calculateRealValues( Integer[] elements )
	{
		double[] realValues = new double[ REAL_COUNT ];

		realValues[ REAL_ANGLE ] = Math.abs( ( elements[ ANGLE ] * 180 ) / Math.pow( 2, elementSize ) );
		realValues[ REAL_ROTATION ] = ( elements[ ROTATION ] * 360 ) / Math.pow( 2, elementSize );
		realValues[ REAL_VELOCITY ] = Math.abs( elements[ VELOCITY ] );
		
		realValues[ REAL_DISTANCE ] = ( Math.pow( realValues[ REAL_VELOCITY ], 2 ) / G ) * Math.sin( ( 2 * ( realValues[ REAL_ANGLE ] * Math.PI / 180 ) ) );
		
		realValues[ REAL_Y ] = elements[ Y_ORIGIN ] + ( Math.sin( realValues[ REAL_ROTATION ] * Math.PI / 180 ) * realValues[ REAL_DISTANCE ] );
		realValues[ REAL_X ] = elements[ X_ORIGIN ] + ( Math.cos( realValues[ REAL_ROTATION ] * Math.PI / 180 ) * realValues[ REAL_DISTANCE ] );
		
		return realValues;
}
	
	@Override
	public Integer[] decode(Gene< ? >[] genes) 
	{
		StringBuffer sb = new StringBuffer();
		
		for ( int i = 0; i < genes.length ; i++ )
		{
			sb.append( (Boolean)genes[ i ].getGene() ? "1" : "0" );
		}
		
		Integer[] elementIntegers = new Integer[ ELEMENT_COUNT ];
		int index = 0;
		for ( int i = 0; i < genes.length ; i += elementSize )
		{
			Integer value = Integer.parseInt( sb.substring( i, i + elementSize ), 2 );
			if ( sb.substring( i, i + 1 ).contains( "1" ) )
			{
				value = -1 * ( value - (int)( Math.pow( 2, elementSize  ) / 2 ) );
			}
			elementIntegers[ index++ ] = value;
		}
		
		return elementIntegers;
	}

	@Override
	public void display(PrintStream ps, Chromosome c ) 
	{
		c.display( ps );
		
		Integer[] elements = decode( c.slice( 0, c.getNumberOfGenes() ) );
		
		double[] realValues = calculateRealValues( elements );

		double myScore = target.getMissDistance( realValues[ REAL_X ], realValues[ REAL_Y ] );


		System.out.println( "-----" );
		System.out.println( "Score: " + myScore );
		System.out.println( "-----" );
		System.out.println( "Angle: " + elements[ ANGLE ] );
		System.out.println( "Rotation: " + elements[ ROTATION ] );
		System.out.println( "Velocity: " + elements[ VELOCITY ] );
		System.out.println( "X: " + elements[ X_ORIGIN ] );
		System.out.println( "Y: " + elements[ Y_ORIGIN ] );		
		System.out.println( "real Angle: " + realValues[ REAL_ANGLE ] );
		System.out.println( "real Rotation: " + realValues[ REAL_ROTATION ] );
		System.out.println( "real Velocity: " + realValues[ REAL_VELOCITY ] );
		System.out.println( "distance: " + realValues[ REAL_DISTANCE] );
		System.out.println( "xTarget: " + realValues[ REAL_X ] );
		System.out.println( "yTarget: " + realValues[ REAL_Y ] );
		System.out.println( "-----" );
	}

	@Override
	public boolean pass(double score) 
	{
		return 0 < Double.compare( scoreGoal, score );
	}
}
