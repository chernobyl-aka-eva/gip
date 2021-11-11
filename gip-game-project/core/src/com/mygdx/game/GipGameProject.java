package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class GipGameProject extends Game {

	// Created by Eva-Veronica Szeredi & Philip Scott ☺

	public SpriteBatch batch;

	// used for text see https://github.com/libgdx/libgdx/wiki/Gdx-freetype
	// https://www.dafont.com/pc-senior.font already included
	// https://www.dafont.com/corrupted-file.font needs to be yet included (title) ? -maybe picture instead
	public FreeTypeFontGenerator generator;
	public FreeTypeFontGenerator.FreeTypeFontParameter parameter;

	// graphics
	public TextureAtlas textureAtlas;

	@Override
	public void create() {
		generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/pcsenior.ttf"));
		batch = new SpriteBatch();
		parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
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
	}

}
