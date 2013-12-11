/*
 * This class will detail the constants
 * of the player's sprite.  Will extend
 * the sprite class.  The player will be able
 * to rotate the sprite with the arrow keys
 * as well as thrust which increases the ship's 
 * velocity in the direction it is facing.
 */
package com.PortalAsteroidjava;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.utils.MathUtils;
import com.badlogic.gdx.files.FileHandle;

public class Player extends SpaceObject{
	//The size of the ship
	public static final int SIZE = 64;
	
	//The angle of each step of rotation in radians (if the ship is rotating)
	public static final float ROTATION = MathUtils.PI / 180;
	
	//Change in velocity for each step while thrusting
	public static final float THRUST_STEP = 1f;
	
	//The square of the ship's max speed
	public static final float MAX_SPEED = 200;
	
	public static final float DRAG = .05f;
	
	//The cooldown for the ship's grapple ability (in seconds)
	private int grappleCooldown;
	
	//The angle that the ship is facing
	private float shipAngle;

	
	/**
	 * This method creates a ship.  Since this is the player's
	 * ship, it will always start at the same position with the same
	 * initial velocity (zero) and the same width and height.
	 * The ship will initially face the positive x-axis
	 * and the life count will default to 3.
	 */
	
	public Player(float x, float y){
		super(PortalAsteroidjava.playerTexture, x, y, SIZE);
		shipAngle = 0;
		setRotation(-shipAngle);
	}
	
    public void update(float t, GameModel m)
    {
    	float decel = 1 - (DRAG * t);
    	setVelocity(getVelocityX()*decel, getVelocityY()*decel);
    	
    	shipAngle = MathUtils.atan2(getVelocityX(), getVelocityY());

    	setRotation(-shipAngle * MathUtils.radiansToDegrees);
    	
    	super.update(t,m);
    }
	
	public void accelerate(float dir)
	{
		float speed = dir*THRUST_STEP;
		float x = getVelocityX() + speed*MathUtils.sin(shipAngle);
		float y = getVelocityY() + speed*MathUtils.cos(shipAngle);
		setVelocity(x, y);
	}
	
	public void changeAngle(float dir)
	{
		float angle = dir*ROTATION;
		shipAngle += angle;
		setRotation(-shipAngle * MathUtils.radiansToDegrees);
		//Gdx.app.log("Angle: ", "The Ship's angle is " + (int)(shipAngle * MathUtils.radiansToDegrees % 360) + " degrees.");
	}
	
	public void stop()
	{
		setVelocity(0,0);
	}
	
    public float getMaximumSpeed()
    {
    	return MAX_SPEED;
    }
    
    public float getShipAngle()
    {
    	return shipAngle;
    }
	
	/**
	 * Handles a collision between the player's ship
	 * and any of the obstacles he must avoid, including
	 * enemy ships, asteroids, and enemy ship projectiles. 
	 * Takes in the Sprite the player has collided with as 
	 * an argument.
	 */
	
	/*public void collision(Sprite s)
	{
		//TO DO
	}*/
	

	
	/**
	 * Returns the speed of the missiles the ship fires
	 */
	
	/*public double getMissileSpeed()
	{
		return MISSILE_SPEED;
	}*/
	
	/**
	 * Returns the x and y coordinate of this ship's midpoint
	 */
	
	/*public double getMidX()
	{
		return this.position.x + SIZE / 2.0;
	}
	
	public double getMidY()
	{
		return this.position.y + SIZE / 2.0;
	}*/
}

