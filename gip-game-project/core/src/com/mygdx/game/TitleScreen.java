package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

// class where all main menu logic goes

public class TitleScreen implements Screen {

    final GipGameProject game;
    // screen
    OrthographicCamera camera;

    private final TextureRegion[] backgrounds;

    private Stage stage;

    // timing
    private final float[] backgroundOffsets = {0, 0, 0, 0};
    private final float backgroundMaxScrollingSpeed;

    public TitleScreen(final GipGameProject game) {

        this.game = game;

        camera = new OrthographicCamera();

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        // set up texture atlas for background

        game.textureAtlas = new TextureAtlas("backgrounds.atlas");



        backgrounds = new TextureRegion[4];
        backgrounds[0] = game.textureAtlas.findRegion("binary-bg-0");
        backgrounds[1] = game.textureAtlas.findRegion("binary-bg-1");
        backgrounds[2] = game.textureAtlas.findRegion("binary-bg-2");
        backgrounds[3] = game.textureAtlas.findRegion("binary-bg-3");

        backgroundMaxScrollingSpeed = (float) 1080 / 4;


        //setup texture atlas and skin for buttons
        game.skin = new Skin(Gdx.files.internal("menu-buttons.json"));
        game.skin.addRegions(new TextureAtlas("menu-buttons.atlas"));
        initMenuButtons();

    }
    public void initMenuButtons() {
        //Creating button objects
        Button newSession = new TextButton("New Session", game.skin,"menu-button-big");
        newSession.setSize(510, 80);

        Button settings = new TextButton("Settings", game.skin, "menu-button-small");
        settings.setSize(510, 40);

        Button quit = new TextButton("Quit", game.skin, "menu-button-exit");
        settings.setSize(510, 40);

        //setting position of button
        float lastY = 250;
        newSession.setPosition(100, lastY);
        //adding click listener
        newSession.addListener(new InputListener() {
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {}
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new GameScreen(game));
                dispose();
                return true;
            }
        });
        //adding the button to the stage as an actor, so it can be drawn
        stage.addActor(newSession);

        lastY -= settings.getHeight()+1;
        settings.setPosition(100, lastY);
        settings.addListener(new InputListener() {
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {}
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                //change to settings screen
                return true;
            }
        });
        stage.addActor(settings);

        lastY -= quit.getHeight()+1;
        quit.setPosition(100, lastY);
        quit.addListener(new InputListener() {
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {}
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                dispose();
                Gdx.app.exit();
                return true;
            }
        });
        stage.addActor(quit);
    }


    @Override
    public void render(float deltaTime) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // This cryptic line clears the screen
        game.batch.begin();

        // scrolling background

        renderBackground(deltaTime);

        game.batch.end();
        game.batch.begin();

        stage.draw();
        stage.act();

        game.batch.end();
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
        stage.dispose();
        game.skin.getAtlas().dispose();
        game.skin.dispose();
    }

    @Override
    public void show() {

    }
}

