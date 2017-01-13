package com.tuc.ai.genalg;

public interface Fitness
{
	public double score( Chromosome chromosome );
	public boolean pass( double score );
}
