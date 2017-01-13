package com.tuc.ai.genalg;

public class GeneBooleanImpl implements Gene< Boolean > 
{
	private Boolean gene;

	@Override
	public void setGene(Boolean t) 
	{
		gene = t;
	}

	@Override
	public Boolean getGene() 
	{
		return gene;
	}

	@Override
	public void mutate() 
	{
		gene = ! gene;
	}

}
