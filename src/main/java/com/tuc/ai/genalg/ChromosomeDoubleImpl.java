package com.tuc.ai.genalg;

import java.io.PrintStream;

import com.tuc.ai.AIConstants;

public class ChromosomeDoubleImpl extends ChromosomeAbstractImpl implements Chromosome 
{
	private Gene< Double >[] genes = null;
	private double mutationRange;
	
	public ChromosomeDoubleImpl( int mySize, double mutationRange )
	{
		super();
		genes = new GeneDoubleImpl[ mySize ];
		
		for ( int i = 0; i < mySize; i++ )
		{
			genes[ i ] = new GeneDoubleImpl( mutationRange );
			genes[ i ].setGene( AIConstants.RAND.nextDouble() );
		}
		
		this.mutationRange = mutationRange;
	}

	@Override
	public Gene< Double >[] slice(int start, int end) 
	{
		Gene< Double >[] newSlice = new GeneDoubleImpl[ end - start ];
		
		int geneIndex = 0;
		for ( int i = start; i < end; i++ )
		{
			newSlice[ geneIndex ] = new GeneDoubleImpl( mutationRange );
			newSlice[ geneIndex++ ].setGene( genes[ i ].getGene() );
		}
		
		return newSlice;
	}

	@Override
	public void splice(int start, int end, Gene< ? >[] partialChromosome) 
	{
		int geneIndex = 0;
		for ( int i = start; i < end; i++ )
		{
			genes[ i ].setGene( (Double)partialChromosome[ geneIndex++ ].getGene() );
		}
	}

	@Override
	public void mutate(double rate) 
	{
		super.mutate( rate, genes );
	}
	
	@Override
	public int getNumberOfGenes() 
	{
		return super.getNumberOfGenes( genes );
	}

	@Override
	public void display(PrintStream ps) {
		// TODO Auto-generated method stub

	}
}
