package com.tuc.ai.avoid;

public interface Collidable 
{
	public void incrCollisions( long x );
	public long getCollisionCount();
	public void resetCollisionCount( long total );
}
