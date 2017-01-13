package com.tuc.ai.genalg;

import java.io.PrintStream;

import com.tuc.ai.AIConstants;

public class ChromosomeBooleanImpl extends ChromosomeAbstractImpl implements Chromosome
{
	private Gene< Boolean >[] genes = null;
	
	public ChromosomeBooleanImpl( int mySize )
	{
		super();
		genes = new GeneBooleanImpl[ mySize ];
		
		for ( int i = 0; i < mySize; i++ )
		{
			genes[ i ] = new GeneBooleanImpl();
			genes[ i ].setGene( AIConstants.RAND.nextBoolean() );
		}
	}
	
	@Override
	public Gene< Boolean >[] slice(int start, int end) 
	{
		Gene< Boolean >[] newSlice = new GeneBooleanImpl[ end - start ];
		
		int geneIndex = 0;
		for ( int i = start; i < end; i++ )
		{
			newSlice[ geneIndex ] = new GeneBooleanImpl();
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
			genes[ i ].setGene( (Boolean)partialChromosome[ geneIndex++ ].getGene() );
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
	public void display( PrintStream ps ) 
	{
		ps.println( "Solution" );
		int col = 0;
		for ( int i = 0; i < genes.length; i++ )
		{
			ps.print( genes[ i ].getGene() ? 1 : 0 );
			if ( 16 <= ++col )
			{
				col = 0;
				ps.println();
			}
		}
	}
}
