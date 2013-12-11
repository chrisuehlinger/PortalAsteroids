package com.PortalAsteroidjava;

public class LargeAsteroid extends Asteroid{

	public static final float SIZE = 64;
	
	public LargeAsteroid(float x, float y, float vX, float vY)
	{
		super(PortalAsteroidjava.largeAsteroidTexture, x, y, SIZE, vX, vY);
		hitsRemaining = 3;
	}
}
