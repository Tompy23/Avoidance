package com.tuc.ai.genalg;

import com.tuc.ai.AIConstants;

public abstract class ChromosomeAbstractImpl implements Chromosome
{
	public void mutate( double rate, Gene< ? > genes[] ) 
	{
		for ( int i = 0; i < genes.length; i++ )
		{
			if ( 0 >= Double.compare( AIConstants.RAND.nextDouble(), rate ) )
			{
				genes[ i ].mutate();
			}
		}
	}

	public int getNumberOfGenes( Gene< ? > genes[] )
	{
		return genes.length;
	}

}
