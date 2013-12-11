package com.PortalAsteroidjava;

public class SmallAsteroid extends Asteroid{

	public static final float SIZE = 20;
	
	public SmallAsteroid(float x, float y, float vX, float vY)
	{
		super(PortalAsteroidjava.smallAsteroidTexture, x, y, SIZE, vX, vY);
		hitsRemaining = 1;
	}
}
