package com.mygdx.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Logger;

public class GipGameProject extends Game {

	// Created by Eva-Veronica Szeredi & Philip Scott ☺

	public SpriteBatch batch;

	// used for text see https://github.com/libgdx/libgdx/wiki/Gdx-freetype
	// https://www.dafont.com/pc-senior.font already included
	// https://www.dafont.com/corrupted-file.font needs to be yet included (title) ? -maybe picture instead
	public FreeTypeFontGenerator generator;
	public FreeTypeFontGenerator.FreeTypeFontParameter parameter;
	public BitmapFont font;

	//loading public skin for all buttons
	public Skin skin;

	// graphics
	public TextureAtlas textureAtlas;

	//public logger
	public Logger log;



	@Override
	public void create() {
		generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/pcsenior.ttf"));
		batch = new SpriteBatch();
		parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		font = generator.generateFont(parameter);


		//viewport WORK IN PROGRESS screenViewport = new ScreenViewport();
		log = new Logger(this.getClass().getName(), Logger.DEBUG);

		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		this.setScreen(new TitleScreen(this));

	}

	@Override
	public void render() {
		super.render(); // important !
	}

	@Override
	public void dispose() {
		batch.dispose();
		generator.dispose();
		skin.getAtlas().dispose();
		skin.dispose();
	}

}
