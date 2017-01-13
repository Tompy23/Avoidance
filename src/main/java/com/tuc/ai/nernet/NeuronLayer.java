package com.tuc.ai.nernet;

import java.util.List;

import com.tuc.ai.AILogging;

public interface NeuronLayer extends AILogging
{
	public double[] update( double[] inputs );
	
	public void getWeights( List< Double > weights );
	public int setWeights( double[] weights, int index );
}
