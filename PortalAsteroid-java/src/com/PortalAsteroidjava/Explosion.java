/**
 * This class will detail the constants and behavior of the asteroids
 * in this game.  
 */

package com.PortalAsteroidjava;

import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.utils.MathUtils;

public class Explosion extends SpaceObject {
	
	public static final float SIZE = 128;
	
	private int explosionTimer;

	 public Explosion(float x, float y)
	 {
		 super(PortalAsteroidjava.explosionTexture, x, y, SIZE, 0, 0);
		 PortalAsteroidjava.explosion();
		 explosionTimer = 100;
	 }
	 
	 public void update(float t, GameModel m)
	 {
		explosionTimer -= t;
		super.update(t, m);
	 }
	 
	 public int getExplosionTimer()
	 {
		 return explosionTimer;
	 }
	 
}
