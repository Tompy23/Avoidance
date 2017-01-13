package com.tuc.ai.genalg;

import java.util.List;

import com.tuc.ai.AIConstants;

public class CrossoverTwoImpl implements Crossover
{

	@Override
	public void mate( 
			Chromosome a1, 
			Chromosome a2, 
			Chromosome c1, 
			Chromosome c2, 
			List< Chromosome > chromosomes, 
			int chromoSize, 
			double rate )
	{
		if ( 0 >= Double.compare( AIConstants.RAND.nextDouble(), rate ) )
		{
			int cutpoint = AIConstants.RAND.nextInt( chromoSize - 1 ) + 1;
			Gene< ? >[] c1Slice1 = a1.slice( 0, cutpoint - 1 );
			Gene< ? >[] c1Slice2 = a1.slice( cutpoint, chromoSize );
			Gene< ? >[] c2Slice1 = a2.slice( 0, cutpoint - 1 );
			Gene< ? >[] c2Slice2 = a2.slice( cutpoint, chromoSize );
			
			c1.splice( 0, cutpoint - 1, c1Slice1 );
			c1.splice( cutpoint, chromoSize, c2Slice2 );
			
			c2.splice( 0, cutpoint - 1, c2Slice1 );
			c2.splice( cutpoint, chromoSize, c1Slice2 );
			
			chromosomes.add( c1 );
			chromosomes.add( c2 );
		}
		else
		{
			chromosomes.add( a1 );
			chromosomes.add( a2 );
		}

	}

}
