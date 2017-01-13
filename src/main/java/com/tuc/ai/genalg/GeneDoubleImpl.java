package com.tuc.ai.genalg;

import com.tuc.ai.AIConstants;

public class GeneDoubleImpl implements Gene< Double > 
{
	private double value;
	private double mutationRange;
	
	public GeneDoubleImpl( double mutationRange )
	{
		this.mutationRange = mutationRange;
	}
	
	@Override
	public void setGene(Double t) 
	{
		value = t;
	}

	@Override
	public Double getGene() 
	{
		return value;
	}

	@Override
	public void mutate() 
	{
		value += ( ( AIConstants.RAND.nextDouble() * 2 * mutationRange ) - mutationRange );
	}

}
