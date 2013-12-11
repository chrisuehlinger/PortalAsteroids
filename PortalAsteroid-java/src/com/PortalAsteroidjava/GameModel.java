package com.PortalAsteroidjava;

import java.awt.*;


import com.badlogic.gdx.*;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.utils.*;

public class GameModel {	
	AsteroidsPanel gameControl = new AsteroidsPanel();
	
    private int gameState;
    public static final int GAME_NOT_STARTED = -1;
	public static final int GAME_PLAY = 0;
	public static final int GAME_OVER = 1;
	public static final int GAME_FINISHED = 2;
    
    GameControl controller;

	
	//Make an array list for all of the sprites (except player
	//who will only have one sprite at any time)
	public static Array<SpaceObject> sprites;
	
	public Player player;
	
	// Time until next asteroid is made
	private int asteroidTimer;
	
	// Time until next missile can be fired
	private int missileTimer;
	
	// Max number of seconds until another asteroid appears
	// Starts at 10 and decreases as the game progresses.
	private int difficulty;
	
	// Measures length of the game loop in milliseconds
	private long loopStart, loopTime;
	
	private int score;
	
	private int lives;
	
	private boolean playerAlive;
	
	private int respawnTimer;
	
	public GameModel (GameControl c) {
		
		float height = Gdx.graphics.getHeight();
		float width = Gdx.graphics.getWidth();
		
		controller = c;
		sprites = new Array< SpaceObject >();
		player = new Player(width / 2, height / 2);
		sprites.add(player);
		
		missileTimer = 0;
		asteroidTimer = 0;
		difficulty = 500;
		score = 0;
		lives = 3;
		gameState = GAME_PLAY;
		playerAlive = true;
		
		loopStart = System.currentTimeMillis();
	}
	
	public void update()
	{
		loopTime = System.currentTimeMillis() - loopStart;
		Gdx.app.log("Score", score + ". Loop completed in " + loopTime + " ms. Ship angle at " + ((int)(player.getShipAngle() * MathUtils.radiansToDegrees % 360)) + " degrees. Difficulty: " + difficulty + ".");
		
		// sleep for 10ms
		// If the loop takes more than 0ms to run,
		// that time is subtracted from sleep to maintain smoothness
		try{
			if(loopTime < 10)
				Thread.sleep(10 - loopTime);
		}
		catch(InterruptedException ie){}
		
		loopStart = System.currentTimeMillis();
		
		asteroidTimer -= 10;
		missileTimer -= 10;
		respawnTimer -= 10;
		
		if(gameState == GAME_PLAY && playerAlive == false && respawnTimer <= 0)
			respawnPlayer();
		
		if(gameState == GAME_PLAY && playerAlive == true)
		{
			
			if(controller.buttons[GameControl.UP])
				player.setVelocity(player.getVelocityX(), player.getVelocityY() + Player.THRUST_STEP);
			
			if (controller.buttons[GameControl.DOWN])
				player.setVelocity(player.getVelocityX(), player.getVelocityY() - Player.THRUST_STEP);
		
			if (controller.buttons[GameControl.LEFT])
				player.setVelocity(player.getVelocityX() - Player.THRUST_STEP, player.getVelocityY());
			
			if (controller.buttons[GameControl.RIGHT])
				player.setVelocity(player.getVelocityX() + Player.THRUST_STEP, player.getVelocityY());
		
			// Doesn't work
			if (controller.buttons[GameControl.B])
				player.stop();
		
			if ((controller.buttons[GameControl.SPACE] || Gdx.input.isTouched()) && missileTimer <= 0)
				shoot();
			
				player.setVelocity(player.getVelocityX() + Gdx.input.getAccelerometerY(), player.getVelocityY() - Gdx.input.getAccelerometerX());
		}
		if(asteroidTimer <= 0)
			makeAsteroid();
		
		float x, y;
		float height = Gdx.graphics.getHeight();
		float width = Gdx.graphics.getWidth();
		
		for(SpaceObject s: sprites)
		{	
			x = s.getX() + s.getOriginX();
			y = s.getY() + s.getOriginY();
			if(s instanceof Explosion && ((Explosion)s).getExplosionTimer() <= 0)
				sprites.removeValue(s, true);
			else if(s instanceof Missile && (x > width || x < 0 || y > height || y < 0))
				sprites.removeValue(s, true);
			else {
				if(x > width || x < 0)
					x = width - x;
			
				if(y > height || y < 0)
					y = height - y;
			
				s.setPosition(x - s.getOriginX(), y - s.getOriginY());
			}
			s.update(.01f, this);
		}
		
		checkCollisions();
	}
	
	public Array<SpaceObject> getSprites()
	{
		return sprites;
	}
	
	/**
	 * Fires a missile in the direction the player is facing.
	 */
	
	private void shoot()
	{
		//precompute some trig
		
		float originX = player.getX() + player.getOriginX();
		float originY = player.getY() + player.getOriginY();
		
		//Place the missile some distance from the ship
		//so it doesn't immediately collide.
		float missileX = originX + (player.SIZE + 2) * MathUtils.sin(player.getShipAngle()) / 2;
		float missileY = originY + (player.SIZE + 2) * MathUtils.cos(player.getShipAngle()) / 2;
		
		//create the missile and add it to the game
		Missile missile = new Missile(missileX, missileY, Missile.MAX_SPEED * MathUtils.cos(-player.getShipAngle() + MathUtils.PI / 2), Missile.MAX_SPEED * MathUtils.sin(-player.getShipAngle() + MathUtils.PI / 2));
		sprites.add(missile);
		PortalAsteroidjava.shot();
		
		missileTimer = 100;
	}
	
	private void makeAsteroid()
	{
		int side = MathUtils.random(3);
		float astroX = 0, astroY = 0;
		float angle = MathUtils.random(90) + 45;
		
		switch(side)
		{
		case GameControl.UP:
			astroX = MathUtils.random(Gdx.graphics.getWidth());
			astroY = Gdx.graphics.getHeight();
			angle += 180;
			break;
		case GameControl.DOWN:
			astroX = MathUtils.random(Gdx.graphics.getWidth());
			astroY = 0;
			angle += 0; 
			break;
		case GameControl.LEFT:
			astroX = 0;
			astroY = MathUtils.random(Gdx.graphics.getHeight());
			angle += 270;
			break;
		case GameControl.RIGHT:
			astroX = Gdx.graphics.getWidth();
			astroY = MathUtils.random(Gdx.graphics.getHeight());
			angle += 90;
			break;
		}
		
		angle *= MathUtils.degreesToRadians;
		float vX = Asteroid.MAX_SPEED * MathUtils.cos(angle);
		float vY = Asteroid.MAX_SPEED * MathUtils.sin(angle);
		
		Asteroid asteroid;
		
		if(MathUtils.random(difficulty) < 300)
			asteroid = new LargeAsteroid(astroX - LargeAsteroid.SIZE / 2, astroY - LargeAsteroid.SIZE / 2, vX, vY);
		else
			asteroid = new SmallAsteroid(astroX - SmallAsteroid.SIZE / 2, astroY - SmallAsteroid.SIZE / 2, vX, vY);
		sprites.add(asteroid);
		
		asteroidTimer = MathUtils.random(difficulty/10) * 100;
		if(difficulty > 0)
			difficulty--;
	}
	
	private void breakAsteroid(LargeAsteroid broken)
	{
		float x = broken.getX() + broken.getOriginX();
		float y = broken.getY() + broken.getOriginY();
		float angle = MathUtils.random(360);
		float vX, vY;
		
		Explosion explosion = new Explosion(x, y);
    	sprites.add(explosion);
		
			angle += 1 * 90 * MathUtils.degreesToRadians;
			vX = Asteroid.MAX_SPEED * MathUtils.cos(angle);
			vY = Asteroid.MAX_SPEED * MathUtils.sin(angle);
			SmallAsteroid piece1 = new SmallAsteroid(x, y, vX, vY);
			sprites.add(piece1);
			
			angle += 2 * 90 * MathUtils.degreesToRadians;
			vX = Asteroid.MAX_SPEED * MathUtils.cos(angle);
			vY = Asteroid.MAX_SPEED * MathUtils.sin(angle);
			SmallAsteroid piece2 = new SmallAsteroid(x, y, vX, vY);
			sprites.add(piece2);
			
			angle += 3 * 90 * MathUtils.degreesToRadians;
			vX = Asteroid.MAX_SPEED * MathUtils.cos(angle);
			vY = Asteroid.MAX_SPEED * MathUtils.sin(angle);
			SmallAsteroid piece3 = new SmallAsteroid(x, y, vX, vY);
			sprites.add(piece3);
	}
	
    /**
     * Checks for and handles collisions between sprites.
     */

    private void checkCollisions()
    {
    	// iterate over the n(n-1)/2 distinct pairs of sprites
    	
    	for (int i = 0; i < sprites.size; i++)
    	{
    		SpaceObject s1 = sprites.get(i);

    		for (int j = i + 1; j < sprites.size; j++)
    		{
    			SpaceObject s2 = sprites.get(j);
    			
    			if (close(s1, s2))// && s1.collidesWith(s2))
    			{
    				Gdx.app.log("Collision", "Detected");
    				handleCollision(s1, s2);
    			}
    		}
    	}
    }
    
    
    
    private boolean close(SpaceObject s1, SpaceObject s2)
    {
    	double sumRadii = (s1.getHeight() + s2.getHeight()) / 2;

    	return (Math.sqrt(Math.pow((s1.getX()) - (s2.getX()), 2)
    			+ Math.pow((s1.getY()) - (s2.getY()), 2)) < sumRadii);
    }
    
    
    private void handleCollision(SpaceObject s1, SpaceObject s2)
    {

    	if (s1 instanceof Asteroid || s2 instanceof Asteroid)
    	{
    		// make sure that s2 is an Asteroid
    		if (s1 instanceof Asteroid)
    		{
    			SpaceObject temp = s1;
				s1 = s2;
				s2 = temp;
    		}

    		if (s1 instanceof Player)
    		{
    			killPlayer();
    			if(s2 instanceof LargeAsteroid)
					breakAsteroid((LargeAsteroid)s2);
				
				sprites.removeValue(s2, true);
    		}else if(s1 instanceof Missile)
    		{
    			sprites.removeValue(s1, true);
    			if(((Asteroid)s2).hitTaken())
    			{
    				if(s2 instanceof LargeAsteroid)
    				{
    					score += 30;
    					breakAsteroid((LargeAsteroid)s2);
    				}else
    					score += 10;
    				
    				sprites.removeValue(s2, true);
    			}
    		}else if(s1 instanceof Asteroid)
    		{
    			if(((Asteroid)s1).hitTaken())
    			{
    				if(s1 instanceof LargeAsteroid)
    					breakAsteroid((LargeAsteroid)s1);
    				
    				sprites.removeValue(s2, true);
    			}
    			
    			if(((Asteroid)s2).hitTaken())
    			{
    				if(s2 instanceof LargeAsteroid)
    					breakAsteroid((LargeAsteroid)s2);
    				
    				sprites.removeValue(s2, true);
    			}
    		}
    	}

    }

    
    private void killPlayer()
    {
    	Explosion explosion = new Explosion(player.getX(), player.getY());
    	sprites.add(explosion);
    	sprites.removeValue(player, true);
    	playerAlive = false;
    	lives--;
    	if(lives <= 0)
    		gameState = GAME_OVER;
    	else
    		respawnTimer = 3000;
    }
    
    private void respawnPlayer()
    {
    	player = new Player(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
		sprites.add(player);
		playerAlive = true;
    }
    
    public int getScore()
    {
    	return score;
    }
    
    public int getLives()
    {
    	return lives;
    }
    
    public Player getPlayer()
    {
    	return player;
    }
    
}
