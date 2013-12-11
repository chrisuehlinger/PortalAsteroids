/**
 * This class will detail the constants and behavior of the asteroids
 * in this game.  
 */

package com.PortalAsteroidjava;

import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.utils.MathUtils;

public class Asteroid extends SpaceObject {
	
	//The max speed of an asteroid
	public static final float MAX_SPEED = 10.0f;
	
	//How many hits each asteroid can take
	public int hitsRemaining;
	
	private float rotationSpeed;
	
	/** 
	 * A constructor for the asteroids.
	 * It will take some starting coordinates
	 * and will always initialize with a velocity
	 * equal to the max.
	 */
	 public Asteroid(TextureRegion texture, float x, float y, float size, float vX, float vY)
	 {
		 super(texture, x, y, size, vX, vY);
		 rotationSpeed = MathUtils.random(30) - 15;
	 }
	 
	 public void update(float t, GameModel m)
	 {
		this.rotate(rotationSpeed*t);
		super.update(t, m);
	 }
	 
	 public float getMaximumSpeed()
	    {
		return MAX_SPEED;
	    }
	 
	 /**
	  * Handles a collision between this asteroid and any obstacles
	  * it can collide with, including the player's ship, missles,
	  * and enemy ships.
	  */
	 public boolean hitTaken()
	 {
		 hitsRemaining--;
		 if(hitsRemaining <=0)
			 return true;
		 
		 return false;
	 }

}
