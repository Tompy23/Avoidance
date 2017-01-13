package com.tuc.ai.avoid;

import com.tuc.ai.genalg.Chromosome;
import com.tuc.ai.genalg.Fitness;
import com.tuc.ai.nernet.NeuralNetwork;

public interface Ship extends SpaceObject, Collidable, Chromosome, Fitness, Comparable< Ship >
{
	public void init( 
			double mutationRange,
			DoublePoint position,
			double velocity,
			double direction,
			double size,
			double mass );
	public NeuralNetwork extractBrain();
	public void implantBrain( NeuralNetwork b );
	public void adjustThrusters( double[] inputs );
	public int getBrainSize();
	public Chromosome getChromosome(); 
	public int getNumberTracking();

	public boolean isSupreme();

}
