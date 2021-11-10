package com.mygdx.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class InputPollingSample implements ApplicationListener {

	private OrthographicCamera camera;
	private Viewport viewport;
	private SpriteBatch batch;
	private BitmapFont font;

	@Override
	public void create() {
		Gdx.app.setLogLevel(Application.LOG_DEBUG);

		camera = new OrthographicCamera();
		viewport = new FitViewport(1080,720,camera);
		batch = new SpriteBatch();
		font = new BitmapFont(Gdx.files.internal("fonts/oswald-32.fnt"));
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height, true);
	}

	@Override
	public void render() {
		// clear screen
		Gdx.gl.glClearColor(0, 0, 0,1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.setProjectionMatrix(camera.combined); //tell batch which camera to use (position, zoom of camera)
		batch.begin();
		draw();
		batch.end();
	}

	private void draw(){
		// mouse / touch x/y
		int mouseX = Gdx.input.getX(); //mouse position x
		int mouseY = Gdx.input.getY(); //mouse position y

		// mouse buttons
		boolean leftPressed = Gdx.input.isButtonPressed(Input.Buttons.LEFT); //mouse left button pressed
		boolean rightPressed = Gdx.input.isButtonPressed(Input.Buttons.RIGHT); //mouse right button pressed

		font.draw(batch,
				"Mouse/Touch : x= " + mouseX + " y= " + mouseY,
				20f,
				720-20);

		font.draw(batch,
				leftPressed ? "Left button pressed" : "Left button not pressed",
				20f,
				720-50f);

		font.draw(batch,
				rightPressed ? "Right button pressed" : "Right button not pressed",
				20f,
				720-80f);

		// keys
		boolean wPressed = Gdx.input.isKeyPressed(Input.Keys.W);
		boolean sPressed = Gdx.input.isKeyPressed(Input.Keys.S);

		font.draw(batch,
				wPressed ? "W is pressed" : "W is not pressed",
				20f,
				720-110f);

		font.draw(batch,
				sPressed ? "S is pressed" : "S is not pressed",
				20f,
				720-140f);
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {
		batch.dispose();
		font.dispose();
	}
}
