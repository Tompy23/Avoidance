package com.tuc.ai.nernet;

import java.util.List;

import com.tuc.ai.AIConstants;
import com.tuc.ai.AILogger;


public class NeuronImpl implements Neuron 
{
	private double[] inputWeights = null;
	private double bias;
	private double activationResponse; 

	private AILogger log;
	
	public NeuronImpl( int numberInputs, double bias, double min, double max, double activationResponse, AILogger log )
	{
		this.log = log;
		this.bias = bias;
		this.activationResponse = activationResponse;
		inputWeights = new double[ numberInputs ];
		
		for ( int i = 0; i < numberInputs; i++ )
		{
			inputWeights[ i ] = ( ( AIConstants.RAND.nextDouble() * ( max - min ) ) + min )
				* ( AIConstants.RAND.nextBoolean() ? 1 : -1 );
		}
	}
	
	@Override
	public void getWeights( List< Double > weights )
	{
		for ( int i = 0; i < inputWeights.length; i++ )
		{
			weights.add( inputWeights[ i ] );
		}
	}
	
	@Override
	public int setWeights( double[] w, int index )
	{
		for ( int i = 0; i < inputWeights.length; i++ )
		{
			inputWeights[ i ] = w[ index++ ];
		}
		
		return index;
	}
	
	@Override
	public double update(double[] inputs) 
	{
		double output = 0;
		
		for ( int i = 0; i < inputWeights.length; i++ )
		{
			output += inputs[ i ] * inputWeights[ i ];
		}
		
		output -= bias;

		return sigmoid( output );
	}

	@Override
	public void log(AILogger parentLog, int level)
	{
		for ( int i = 0; i < inputWeights.length; i++ )
		{
			log.info( "Weight [" + i + "]: " + inputWeights[ i ] );
		}
		
		log.info( "Bias: " + bias );
	}
	
	private double sigmoid( double input )
	{
		return 1 / ( 1 + ( Math.pow( Math.E, ( -1 * input ) / activationResponse ) ) );
	}
	
	
}
