/**
 * This class details the constants and behavior of a 
 * missile in this game.  A missile can be fired by the 
 * player's ship or the enemy ships.  
 */

package com.PortalAsteroidjava;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;

public class Missile extends SpaceObject{
	
	//The size of a missile sprite
	public static final int SIZE = 5;
	
	//The speed of a missile launched from the ship
	public static final float MAX_SPEED = 300;
	
	/**
	 * Creates a missile at the given position with
	 * the given velocity.
	 */
	
	public Missile(float x, float y, float velX, float velY)
	{
		super(PortalAsteroidjava.missileTexture, x, y, SIZE, velX, velY);
	}
	
	/**
	 * Updates the position of the missile.
	 */
	/*public void tick()
	{
		super.tick();
	}*/
}
