package com.tuc.ai.genalg;


public interface Chromosome extends Display, Mutate
{
	public Gene< ? >[] slice( int start, int end );
	
	public void splice( int start, int end, Gene< ? >[] partialChromosome );
	
	public int getNumberOfGenes();
}
