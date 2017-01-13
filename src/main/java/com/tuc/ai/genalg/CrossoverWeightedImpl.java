package com.tuc.ai.genalg;

import java.util.List;

import com.tuc.ai.AIConstants;

public class CrossoverWeightedImpl implements Crossover 
{
	private double crossOverRatio;
	
	@Override
	public void mate(Chromosome a1, 
			Chromosome a2, 
			Chromosome c1,
			Chromosome c2, 
			List<Chromosome> chromosomes, 
			int chromoSize,
			double rate) 
	{
		if ( 0 >= Double.compare( AIConstants.RAND.nextDouble(), rate ) )
		{
			c1 = a1;
			c2 = a2;
			for ( int i = 0; i < chromoSize - 1; i++ )
			{
				if ( AIConstants.RAND.nextBoolean() )
				{
					GeneDoubleImpl g1 = (GeneDoubleImpl)a1.slice( i, i + 1 )[ 0 ];
					GeneDoubleImpl g2 = (GeneDoubleImpl)a2.slice( i, i + 1 )[ 0 ];
					
					Double p1 = (Double)g1.getGene();
					Double p2 = (Double)g2.getGene();
					
					Double result = p2 + ( crossOverRatio * ( p1 - p2 ) );
					
					((GeneDoubleImpl)c1.slice( i, i + 1 )[ 0 ]).setGene( result );
					((GeneDoubleImpl)c2.slice( i, i + 1 )[ 0 ]).setGene( result );
					
					chromosomes.add( c1 );
					chromosomes.add( c2 );
				}
				else
				{
					chromosomes.add( a1 );
					chromosomes.add( a2 );
				}
			}
//			chromosomes.add( c1 );
//			chromosomes.add( c2 );
		}		
	}

	public void setCrossOverRatio(double crossOverRatio) {
		this.crossOverRatio = crossOverRatio;
	}
}
