package com.tuc.ai.genalg;

public interface GeneDecoder<T> 
{
	public T[] decode(Gene< ? >[] genes);
}
