package com.mygdx.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.ScreenUtils;

public class ApplicationListenerSample implements ApplicationListener {

	//logger is used to print to our console and be able to debug our game
	private static final Logger log = new Logger(ApplicationListenerSample.class.getName(), Logger.DEBUG);
	private boolean renderInterrupted = true;

	@Override
	public void create() {
		// used to initalize game and load resources
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		log.debug("create()");
	}

	@Override
	public void resize(int width, int height) {
		// used to handle setting a new screen size
		log.debug("resize() width= " + width + " height= " + height);
	}

	@Override
	public void render() {
		// used to update and render the game elements called 60 times per second
		if (renderInterrupted){
			log.debug("render()");
			renderInterrupted = false;
		}
	}

	@Override
	public void pause() {
		// used to save game state when it loses focus, which doesn't involve the actual
		// gameplay being paused unless the developer wants it to pause
		log.debug("pause()");
		renderInterrupted = true;
	}

	@Override
	public void resume() {
		// used to handle the game coming back from being paused and restores game state
		log.debug("resume()");
		renderInterrupted = true;
	}

	@Override
	public void dispose() {
		// used to free resources and clean up
		log.debug("dispose()");
	}
}
