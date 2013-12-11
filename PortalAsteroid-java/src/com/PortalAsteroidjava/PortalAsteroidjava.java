package com.PortalAsteroidjava;

import com.badlogic.gdx.*;
import com.badlogic.gdx.audio.*;
import com.badlogic.gdx.Files.*;
import com.badlogic.gdx.files.*;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;

import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class PortalAsteroidjava implements ApplicationListener {
	private SpriteBatch batch;
	private GameModel model;
	private GameControl controller;

	public static TextureRegion playerTexture;
	public static TextureRegion smallAsteroidTexture;
	public static TextureRegion largeAsteroidTexture;
	public static TextureRegion missileTexture;
	public static TextureRegion nebulaTexture;
	public static TextureRegion explosionTexture;
	
	/** explosion sound **/
	private static Sound playerExplosion;
	/** shot sound **/
	private static Sound shot;
	
	private BitmapFont font;
	private String displayPanel;

	@Override
	public void create() {
		playerTexture = new TextureRegion(new Texture(Gdx.files.internal("data/spaceship.png")));
		smallAsteroidTexture = new TextureRegion(new Texture(Gdx.files.internal("data/asteroid.png")));
		largeAsteroidTexture = new TextureRegion(new Texture(Gdx.files.internal("data/largeAsteroid.png")));
		missileTexture = new TextureRegion(new Texture(Gdx.files.internal("data/missile.gif")));
		nebulaTexture = new TextureRegion(new Texture(Gdx.files.internal("data/nebula1.png")));
		explosionTexture = new TextureRegion(new Texture(Gdx.files.internal("data/explosion.png")));
		playerExplosion = Gdx.app.getAudio().newSound(Gdx.app.getFiles().getFileHandle("data/playerExplosion.wav", FileType.Internal));
		shot = Gdx.app.getAudio().newSound(Gdx.app.getFiles().getFileHandle("data/shot.mp3", FileType.Internal));
		font = new BitmapFont();
		
		batch = new SpriteBatch();
		controller = new GameControl();
		model = new GameModel(controller);

		Gdx.input.setInputProcessor(controller);
	}

	@Override
	public void dispose() {
		
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void render() {
		model.update();
		Gdx.gl.glClearColor(0,0,0,0);
		TextureRegion background = new TextureRegion(nebulaTexture);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		float xPos = -(2048 - Gdx.graphics.getWidth()) + (1024 - Gdx.graphics.getWidth()) * model.getPlayer().getX() / Gdx.graphics.getWidth();
		float yPos = -(2048 - Gdx.graphics.getHeight()) + (1024 - Gdx.graphics.getHeight()) * model.getPlayer().getY() / Gdx.graphics.getHeight();
		
		batch.begin();
		batch.disableBlending();
		batch.draw(background, 0, 0);
		batch.enableBlending();
		
		for(Sprite s : model.getSprites())
			s.draw(batch);
		
		displayPanel = "Score: " + model.getScore() + "   Lives: " + model.getLives();
		font.draw(batch, displayPanel, 0, Gdx.graphics.getHeight());
		batch.end();
	}
	
	public static void explosion () {
		playerExplosion.play();
	}

	public static void shot () {
		shot.play();
	}

	@Override
	public void resize(int arg0, int arg1) {
		
	}

	@Override
	public void resume() {
		
	}
	
    public void keyPressed(KeyEvent e)
    {
    	Gdx.app.log("PortalAsteroidjava", "Key Pressed");
    }

}
