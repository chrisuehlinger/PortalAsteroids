/**
 * The main panel for this game.   
 */

package com.PortalAsteroidjava;

public class AsteroidsPanel {
	
	//define some variables that 
	//will be used over the course of
	//this game.
	boolean gameInProgress;
	long score;
	int scoreMultiplier;
	int bonusScore;
	boolean multiplierHeld;
	int lives;
	
	public void startNewGame(){
		score = 0;
		scoreMultiplier = 1;
		multiplierHeld = false;
		gameInProgress = true;
		lives = 2;
	}
	
	/**
	 * This method will reset the player's 
	 * multiplier if they lose a life.  It will
	 * also take care of decrementing the life count
	 * and iniating a game over if the player has
	 * lost all lives.
	 */
	public void loseMultiplier () {
		if (multiplierHeld) {
			multiplierHeld = false;
		}
		else {
			scoreMultiplier = 1;
		}
		
		if (this.getRemainingLives() > 0){
			this.decrementLife();
		}
		else {
			//game over case
			gameInProgress = false;
		}
		
	}
	
	/**
	 * Some score adjustment methods for use
	 * when the player scores points.
	 */
	
	public void addScore (long points) {
		score += points * scoreMultiplier;
	}
	
	public void incrementScoreMultiplier()
	{
		++scoreMultiplier;
	}
	
	public long getScore(){
		return score;
	}
	
	public int getScoreMultiplier() {
		return scoreMultiplier;
	}
	
	public boolean isMultiplierHeld() {
		return multiplierHeld;
	}
	
	/**
	 * Checks if game is over and checks
	 * life count
	 */
	public boolean isGameInProgress () {
		return gameInProgress;
	}
	
	public int getRemainingLives()
	{
		return lives;
	}
	
	public void decrementLife()
	{
		lives--;
	}
	
	
}