package com.tuc.ai.genalg;

import java.util.List;

import org.springframework.context.ApplicationContext;

import com.tuc.ai.AIConstants;

public class Utility 
{
	public static List< Chromosome > initializeChromosomes(
			ApplicationContext ctx,
			String chromosomeBeanName,
			int population, 
			List< Chromosome > chromosomes )
	{
		for ( int i = 0; i < population; i++ )
		{
			chromosomes.add( (Chromosome)ctx.getBean( chromosomeBeanName ) );
		}
		
		return chromosomes;
	}
	
	public static List< Chromosome > crossover(
			ApplicationContext ctx,
			String chromosomeBeanName,
			int population, 
			Fitness fitnessFunction,
			Crossover crossoverFunction,
			double crossoverRate,
			List< Chromosome > chromosomes, 
			List< Chromosome > children )
	{
		for ( int j = 0; j < population; j += 2 )
		{
			// Determine suitable parents
			Chromosome a1 = getWeightedChromosome( fitnessFunction, chromosomes );
			chromosomes.remove( a1 );
			Chromosome a2 = getWeightedChromosome( fitnessFunction, chromosomes );
			chromosomes.remove( a2 );

			// mate
			crossoverFunction.mate( 
					a1, 
					a2, 
					(Chromosome)ctx.getBean( chromosomeBeanName ), 
					(Chromosome)ctx.getBean( chromosomeBeanName ),
					children, 
					a1.getNumberOfGenes(), 
					crossoverRate );
		}
		
		return children;
	}
	
	public static void mutate( 
			double mutationRate,
			List< Chromosome> chromosomes )
	{
		for ( Chromosome c : chromosomes )
		{
			c.mutate( mutationRate );
		}
	}
	
	public static Chromosome checkScores(
			Fitness fitnessFunction,
			List< Chromosome > chromosomes )
	{
		Chromosome winner = null;
		for ( Chromosome chromosome : chromosomes )
		{
			double score = fitnessFunction.score( chromosome );
			if ( fitnessFunction.pass( score ) )
			{
				winner = chromosome;
				break;
			}
		}
		
		return winner;
	}

	public static Chromosome getWeightedChromosome(
			Fitness fitnessFunction, 
			List< Chromosome > chromsomes )
	{
		double totalScore = 0;
		double[] rawScoreWheel = new double[ chromsomes.size() ];
		int index = 0;
		for ( Chromosome chromosome : chromsomes )
		{
			double score = fitnessFunction.score( chromosome );
			totalScore += score;
			rawScoreWheel[ index++ ] = score;
		}
		

		Chromosome chromosome = null;
		double spinner = AIConstants.RAND.nextDouble() * totalScore;
		double runningTotal = 0;
		for ( int i = 0; i < chromsomes.size(); i++ )
		{
			if ( 0 < Double.compare( rawScoreWheel[ i ] + runningTotal, spinner ) )
			{
				chromosome = chromsomes.get( i );
				break;
			}
			runningTotal += rawScoreWheel[ i ];
		}
		
		return chromosome;
	}

}
