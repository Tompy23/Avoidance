package com.tuc.ai.nernet;

import java.util.List;

import com.tuc.ai.AILogging;


public interface Neuron extends AILogging
{
	public void getWeights( List< Double > weights );
	public int setWeights( double[] w, int index );
	
	public double update( double[] inputs );
}
