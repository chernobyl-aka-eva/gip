package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

// class where all main menu logic goes

public class TitleScreen implements Screen {

    final GipGameProject game;

    // screen
    OrthographicCamera camera;

    private final TextureRegion[] backgrounds;

    //setup buttons
    private final TextureRegion[] buttons;
    private TextureRegionDrawable[] buttonsDrawable;
    private Stage stage;
    private ImageButton[] buttonObjects;

    private TextureRegion mainMenuTextureRegion; // add more later ?

    // timing
    private final float[] backgroundOffsets = {0, 0, 0, 0};
    private final float backgroundMaxScrollingSpeed;

    public TitleScreen(final GipGameProject game) {

        this.game = game;

        camera = new OrthographicCamera();

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


        /* WORK IN PROGRESS
        buttonsDrawable = new TextureRegionDrawable[buttons.length];
        buttonObjects = new ImageButton[buttons.length]; //Set the buttons up
        stage = new Stage(game.screenViewport);
        for (int i = 0; i < buttons.length; i++) {
            buttonsDrawable[i] = new TextureRegionDrawable(buttons[i]);
            buttonObjects[i] = new ImageButton(buttonsDrawable[i]);
        }
        stage.addActor(buttonObjects[0]);
        stage.addActor(buttonObjects[2]);
        stage.addActor(buttonObjects[0]);
        stage.addActor(buttonObjects[0]);
        stage.addActor(buttonObjects[4]);
        Array<Actor> actors = stage.getActors();
        int lastY = 350;
        int spaceBetweenButtons = 1;
        for (int i = 0; i < actors.size; i++) {
            actors.get(i).setPosition(100, lastY);
            switch (i) {
                case 0:
                case 2:
                case 3: lastY -= (buttons[0].getRegionHeight()) + spaceBetweenButtons; break;
                case 1:
                case 4: lastY = (buttons[2].getRegionHeight()) + spaceBetweenButtons; break;

            }
        }
        Gdx.input.setInputProcessor(stage);

         */


    }

    @Override
    public void render(float deltaTime) {
        game.batch.begin();
        // scrolling background
        renderBackground(deltaTime);
        //stage.draw(); WORK IN PROGRESS

        //buttons

        int lastY = 350;
        int spaceBetweenButtons = 1;
        game.batch.draw(buttons[0], 100, lastY, buttons[0].getRegionWidth(), buttons[0].getRegionHeight());

        lastY -= (buttons[2].getRegionHeight()) + spaceBetweenButtons;
        game.batch.draw(buttons[2], 100, lastY, buttons[2].getRegionWidth(), buttons[2].getRegionHeight());

        lastY -= (buttons[0].getRegionHeight()) + spaceBetweenButtons;
        game.batch.draw(buttons[0], 100, lastY, buttons[0].getRegionWidth(), buttons[0].getRegionHeight());

        lastY -= (buttons[0].getRegionHeight()) + spaceBetweenButtons;
        game.batch.draw(buttons[0], 100, lastY, buttons[0].getRegionWidth(), buttons[0].getRegionHeight());

        lastY -= (buttons[4].getRegionHeight()) + spaceBetweenButtons;
        game.batch.draw(buttons[4], 100, lastY, buttons[4].getRegionWidth(), buttons[4].getRegionHeight());


        game.batch.end();




        // this is where the code comes for changing screens !!!
        if (Gdx.input.isTouched()){
            game.setScreen(new GameScreen(game));
            dispose();
        }


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

