package com.tuc.ai.nernet;

import java.util.ArrayList;
import java.util.List;

import com.tuc.ai.AILogger;

public class NeuralNetworkImpl implements NeuralNetwork 
{
	private List< NeuronLayer > layers = null;
	private int numberInputs;
	private int numberOutputs;
	
	private AILogger log;
	
	public NeuralNetworkImpl( int inputs, int outputs, List< NeuronLayer > layers, AILogger log )
	{
		this.log = log;
		this.layers = layers;
		numberInputs = inputs;
		numberOutputs = outputs;
	}

	@Override
	public double[] update( double[] inputs ) 
	{
		if ( inputs.length != numberInputs )
		{
			// throw new AIException();
		}
		
		double[] outputs = null;
		
		for ( NeuronLayer layer : layers )
		{
			if ( null != outputs )
			{
				inputs = outputs;
			}
			
			outputs = layer.update( inputs );
		}
		
		if ( outputs.length != numberOutputs )
		{
			// throw new AIException();
		}
		
		return outputs;
	}

	@Override
	public double[] getWeights() 
	{
		List< Double > weights = new ArrayList< Double >();
		
		for ( NeuronLayer layer : layers )
		{
			layer.getWeights( weights );
		}
		
		double[] returnValue = new double[ weights.size() ];
		for ( int i = 0; i < weights.size(); i++ )
		{
			returnValue[ i ] = weights.get( i );
		}

		return returnValue;
	}

	@Override
	public void setWeights(double[] weights) 
	{
		int weightIndex = 0;
		for ( NeuronLayer layer : layers )
		{
			weightIndex = layer.setWeights( weights, weightIndex );
		}
	}

	@Override
	public void log(AILogger parentLog, int level) 
	{
		int i = 0;
		for ( NeuronLayer layer : layers )
		{
			log.info( "Neuron Layer[" + i++ + "]" );
			layer.log( log, level );
		}
	}
}
