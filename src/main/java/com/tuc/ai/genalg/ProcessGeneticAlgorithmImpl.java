package com.tuc.ai.genalg;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.springframework.context.ApplicationContext;

import com.tuc.ai.AIProcess;

public class ProcessGeneticAlgorithmImpl implements AIProcess 
{
	private int population;

	private Fitness fitnessFunction;
	private Crossover crossoverFunction;
	
	private double crossoverRate;
	private double mutationRate;
	
	private ChromosomeDisplay chromoDisplay;
	
	private String chromosomeBeanName;
	
	public ProcessGeneticAlgorithmImpl( 
			int p, 
			Fitness fFunction, 
			Crossover
			cFunction, 
			double cRate, 
			double mRate,
			ChromosomeDisplay cDisplay,
			String cbName )
	{
		population = p;
		fitnessFunction = fFunction;
		crossoverFunction = cFunction;
		crossoverRate = cRate;
		mutationRate = mRate;
		chromoDisplay = cDisplay;
		chromosomeBeanName = cbName;
		
	}
	
	@Override
	public int process(ApplicationContext ctx, Properties properties) 
	{
		// Initialize chromosomes
		List< Chromosome > chromosomes = Utility.initializeChromosomes( 
				ctx, 
				chromosomeBeanName, 
				population, 
				new ArrayList< Chromosome >() );
		

		boolean keepTrying = true;
		int epochCounter = 0;
		while ( keepTrying )
		{
			// Crossover
			List< Chromosome > children = Utility.crossover(
					ctx, 
					chromosomeBeanName, 
					population,
					fitnessFunction, 
					crossoverFunction, 
					crossoverRate, 
					chromosomes, 
					new ArrayList< Chromosome >() );
			
			// Mutate
			Utility.mutate( mutationRate, children );

			// Repopulate
			chromosomes.addAll( children );

			// Score check
			Chromosome best = Utility.checkScores( fitnessFunction, chromosomes );
			if ( null != best )
			{
				System.out.println( "Epoch: " + epochCounter );
				chromoDisplay.display( System.out, best );
				break;
			}
			
			epochCounter++;
		}
		
		return 0;
	}
	
}
