package com.mygdx.game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

// class where all game logic goes

public class GameScreen implements Screen {

    private TextureRegion gameBackground;

    final GipGameProject game;

    public GameScreen(final GipGameProject game) {
        this.game = game;
        game.textureAtlas = new TextureAtlas("gameGuidelines.atlas");
        gameBackground = new TextureRegion();
        gameBackground = game.textureAtlas.findRegion("guidelines");
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        game.batch.begin();
       game.batch.draw(gameBackground, 0,0,
                1920, 1080);
        game.batch.end();

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
