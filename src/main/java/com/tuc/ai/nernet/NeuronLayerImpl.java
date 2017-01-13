package com.tuc.ai.nernet;

import java.util.List;

import com.tuc.ai.AILogger;


public class NeuronLayerImpl implements NeuronLayer 
{
	private Neuron[] neurons = null;
	
	private AILogger log;

	public NeuronLayerImpl( int numberNeurons, int neuronInputs, double bias, double min, double max, double activationResponse, AILogger log )
	{
		this.log = log;
		neurons = new NeuronImpl[ numberNeurons ];
		
		for ( int i = 0; i < numberNeurons; i++ )
		{
			neurons[ i ] = new NeuronImpl( neuronInputs, bias, min, max, activationResponse, log );
		}
	}
	
	@Override
	public double[] update(double[] inputs)
	{
		double[] outputs = new double[ neurons.length ];
		
		for ( int i = 0; i < neurons.length; i++ )
		{
			outputs[ i ] =neurons[ i ].update( inputs );
		}
		
		return outputs;
	}

	@Override
	public void getWeights( List< Double > weights ) 
	{
		for ( int i = 0; i < neurons.length; i++ )
		{
			neurons[ i ].getWeights( weights );
		}
	}

	@Override
	public int setWeights(double[] weights, int index ) 
	{
		for ( int i = 0; i < neurons.length; i++ )
		{
			index = neurons[ i ].setWeights( weights, index );
		}
		
		return index;
	}

	@Override
	public void log(AILogger parentLog, int level) 
	{
		for ( int i = 0; i < neurons.length; i++ )
		{
			log.info( "Neuron[" + i + "]" );
			neurons[ i ].log( log, level );
		}
	}
}
