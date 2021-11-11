package com.mygdx.game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

// class where all main menu logic goes

public class TitleScreen implements Screen {

    final GipGameProject game;

    // screen
    OrthographicCamera camera;

    private final TextureRegion[] backgrounds;

    private TextureRegion mainMenuTextureRegion; // add more later ?

    // timing
    private final float[] backgroundOffsets = {0, 0, 0, 0};
    private final float backgroundMaxScrollingSpeed;

    public TitleScreen(final GipGameProject game) {

        this.game = game;

        camera = new OrthographicCamera();

        // set up texture atlas

        game.textureAtlas = new TextureAtlas("backgrounds.atlas");

        backgrounds = new TextureRegion[4];
        backgrounds[0] = game.textureAtlas.findRegion("binary-bg-0");
        backgrounds[1] = game.textureAtlas.findRegion("binary-bg-1");
        backgrounds[2] = game.textureAtlas.findRegion("binary-bg-2");
        backgrounds[3] = game.textureAtlas.findRegion("binary-bg-3");

        backgroundMaxScrollingSpeed = (float) 1080 / 4;
    }

    @Override
    public void render(float deltaTime) {
        game.batch.begin();
        // scrolling background
        renderBackground(deltaTime);
        game.batch.end();


        // this is where the code comes for changing screens !!!
        /*if (...){
            game.setScreen(new GameScreen(game));
            dispose();
        }*/


    }

    // code for rendering the background
    private void renderBackground(float deltaTime) {
        backgroundOffsets[0] += deltaTime * backgroundMaxScrollingSpeed / 8;
        backgroundOffsets[1] += deltaTime * backgroundMaxScrollingSpeed / 4;
        backgroundOffsets[2] += deltaTime * backgroundMaxScrollingSpeed / 2;
        backgroundOffsets[3] += deltaTime * backgroundMaxScrollingSpeed;

        for (int layer = 0; layer < backgroundOffsets.length; layer++) {
            if (backgroundOffsets[layer] > 1080) {
                backgroundOffsets[layer] = 0;
            }
            game.batch.draw(backgrounds[layer],
                    0, -backgroundOffsets[layer],
                    1920, 1080);
            game.batch.draw(backgrounds[layer], 0, -backgroundOffsets[layer] + 1080,
                    1920, 1080);
        }
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

    @Override
    public void show() {

    }
}

