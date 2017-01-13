package com.tuc.ai.nernet;

import com.tuc.ai.AILogging;

public interface NeuralNetwork extends AILogging
{
	public double[] update( double[] inputs );
	
	public double[] getWeights();
	public void setWeights( double[] weights );
}
