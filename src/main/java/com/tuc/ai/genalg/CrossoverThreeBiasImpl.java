package com.tuc.ai.genalg;

import java.util.List;

import com.tuc.ai.AIConstants;

public class CrossoverThreeBiasImpl implements Crossover
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
			int cutpoint1 = AIConstants.RAND.nextInt( chromoSize / 2 ) + 1;
			int cutpoint2 = AIConstants.RAND.nextInt( chromoSize - cutpoint1 ) + cutpoint1 + 1;
			int[] length = new int[ 3 ];
			length[ 0 ] = cutpoint1;
			length[ 1 ] = cutpoint2 - cutpoint1;
			length[ 2 ] = chromoSize - cutpoint2;
			Gene< ? >[] c1Slice1 = a1.slice( 0, cutpoint1 - 1 );
			Gene< ? >[] c1Slice2 = a1.slice( cutpoint1, cutpoint2 - 1 );
			Gene< ? >[] c1Slice3 = a1.slice( cutpoint2, chromoSize );
			Gene< ? >[] c2Slice1 = a2.slice( 0, cutpoint1 - 1 );
			Gene< ? >[] c2Slice2 = a2.slice( cutpoint1, cutpoint2 - 1 );
			Gene< ? >[] c2Slice3 = a2.slice( cutpoint2, chromoSize );
			
			int max = 0;
			int min = chromoSize;
			int maxIndex = 0;
			int minIndex = 0;
			for ( int i = 0; i < 3; i++ )
			{
				if ( length[ i ] > max )
				{
					max = length[ i ];
					maxIndex = i;
				}
				if ( length[ i ] < min )
				{
					min = length[ i ];
					minIndex = i;
				}
			} 
			if ( 0 == maxIndex )
			{
				c2.splice( 0, cutpoint1 - 1, c1Slice1 );
				c2.splice( cutpoint1, cutpoint2 - 1, c2Slice2 );
				c2.splice( cutpoint2, chromoSize, c2Slice3 );
				if ( 1 == minIndex )
				{
					c1.splice( 0, cutpoint1 - 1, c1Slice1 );
					c1.splice( cutpoint1, cutpoint2 - 1, c2Slice2 );
					c1.splice( cutpoint2, chromoSize, c1Slice3 );
				}
				else
				{
					c1.splice( 0, cutpoint1 - 1, c1Slice1 );
					c1.splice( cutpoint1, cutpoint2 - 1, c1Slice2 );
					c1.splice( cutpoint2, chromoSize, c2Slice3 );
				}
			}
			else if ( 1 == maxIndex )
			{
				c2.splice( 0, cutpoint1 - 1, c2Slice1 );
				c2.splice( cutpoint1, cutpoint2 - 1, c1Slice2 );
				c2.splice( cutpoint2, chromoSize, c2Slice3 );
				if ( 0 == minIndex )
				{
					c1.splice( 0, cutpoint1 - 1, c2Slice1 );
					c1.splice( cutpoint1, cutpoint2 - 1, c1Slice2 );
					c1.splice( cutpoint2, chromoSize, c1Slice3 );
				}
				else
				{
					c1.splice( 0, cutpoint1 - 1, c1Slice1 );
					c1.splice( cutpoint1, cutpoint2 - 1, c1Slice2 );
					c1.splice( cutpoint2, chromoSize, c2Slice3 );
				}
			}
			else
			{
				c2.splice( 0, cutpoint1 - 1, c2Slice1 );
				c2.splice( cutpoint1, cutpoint2 - 1, c2Slice2 );
				c2.splice( cutpoint2, chromoSize, c1Slice3 );
				if ( 0 == minIndex )
				{
					c1.splice( 0, cutpoint1 - 1, c2Slice1 );
					c1.splice( cutpoint1, cutpoint2 - 1, c1Slice2 );
					c1.splice( cutpoint2, chromoSize, c1Slice3 );
				}
				else
				{
					c1.splice( 0, cutpoint1 - 1, c1Slice1 );
					c1.splice( cutpoint1, cutpoint2 - 1, c2Slice2 );
					c1.splice( cutpoint2, chromoSize, c1Slice3 );
				}
			}

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
