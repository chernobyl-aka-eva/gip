package com.mygdx.game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Logger;

// class where all main menu logic goes

public class TitleScreen implements Screen {

    final GipGameProject game;

    // screen
    OrthographicCamera camera;

    private final TextureRegion[] backgrounds;
    private final TextureRegion[] buttons;

    private TextureRegion mainMenuTextureRegion; // add more later ?

    // timing
    private final float[] backgroundOffsets = {0, 0, 0, 0};
    private final float backgroundMaxScrollingSpeed;

    public TitleScreen(final GipGameProject game) {

        this.game = game;

        camera = new OrthographicCamera();
        //setup logger with current classname
        game.log = new Logger(this.getClass().getName(), Logger.DEBUG);

        // set up texture atlas for background

        game.textureAtlas = new TextureAtlas("backgrounds.atlas");


        backgrounds = new TextureRegion[4];
        backgrounds[0] = game.textureAtlas.findRegion("binary-bg-0");
        backgrounds[1] = game.textureAtlas.findRegion("binary-bg-1");
        backgrounds[2] = game.textureAtlas.findRegion("binary-bg-2");
        backgrounds[3] = game.textureAtlas.findRegion("binary-bg-3");

        backgroundMaxScrollingSpeed = (float) 1080 / 4;


        //setup texture atlas for buttons
        game.textureAtlas = new TextureAtlas("titlescreen-buttons.atlas");
        buttons = new TextureRegion[6];
        buttons[0] = game.textureAtlas.findRegion("menu-button-big-0");
        buttons[1] = game.textureAtlas.findRegion("menu-button-big-1");
        buttons[2] = game.textureAtlas.findRegion("menu-button-small-0");
        buttons[3] = game.textureAtlas.findRegion("menu-button-small-1");
        buttons[4] = game.textureAtlas.findRegion("exit-button-0");
        buttons[5] = game.textureAtlas.findRegion("exit-button-1");
    }

    @Override
    public void render(float deltaTime) {
        game.batch.begin();
        // scrolling background
        renderBackground(deltaTime);


        //buttons
        int spaceBetweenButtons = 0;
        int lastY = 430;
        game.batch.draw(buttons[0], 100, lastY, buttons[0].getRegionWidth(), buttons[0].getRegionHeight());
        game.log.debug(String.valueOf(lastY));

        lastY -= (buttons[2].getRegionHeight()) + spaceBetweenButtons;
        game.batch.draw(buttons[2], 100, lastY, buttons[2].getRegionWidth(), buttons[2].getRegionHeight());
        game.log.debug(String.valueOf(lastY));

        lastY -= (buttons[0].getRegionHeight()) + spaceBetweenButtons;
        game.batch.draw(buttons[0], 100, lastY, buttons[0].getRegionWidth(), buttons[0].getRegionHeight());
        game.log.debug(String.valueOf(lastY));


        lastY -= (buttons[0].getRegionHeight()) + spaceBetweenButtons;
        game.batch.draw(buttons[0], 100, lastY, buttons[0].getRegionWidth(), buttons[0].getRegionHeight());
        game.log.debug(String.valueOf(lastY));


        lastY -= (buttons[4].getRegionHeight()) + spaceBetweenButtons;
        game.batch.draw(buttons[4], 100, lastY, buttons[4].getRegionWidth(), buttons[4].getRegionHeight());
        game.log.debug(String.valueOf(lastY));


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

