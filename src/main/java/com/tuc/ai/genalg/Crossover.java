package com.tuc.ai.genalg;

import java.util.List;

public interface Crossover
{
	public void mate( 
			Chromosome a1, 
			Chromosome a2, 
			Chromosome c1, 
			Chromosome c2, 
			List< Chromosome > chromosomes, 
			int chromoSize, 
			double rate );
}
