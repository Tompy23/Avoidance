package com.tuc.ai.genalg;

public interface Gene< T > 
{
	public void setGene( T t );
	public T getGene();
	public void mutate();
}
