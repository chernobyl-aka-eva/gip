package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
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
    private Settings settingsScreen;
    private final TextureRegion[] BACKGROUNDS = new TextureRegion[4]; // textures for main menu background
    private Stage stage;


    // timing
    private final float[] BACKGROUNDOFFSETS = {0, 0, 0, 0};
    private final float BACKGROUNDSCROLLINGSPEED = (float) 1080 / 4;

    // main menu buttons
    private Button newSession;
    private Button settings;
    private Button quit;

    private Group titlescreenGroup;


    public TitleScreen(final GipGameProject game) {

        this.game = game;

        stage = new Stage(new ScreenViewport()); // creates new stage
        Gdx.input.setInputProcessor(stage); // enables input in stage
        titlescreenGroup = new Group();
        settingsScreen = new Settings(game, stage);

        // set up texture atlas for background
        initBackground();

        // setup texture atlas and skin for buttons
        game.skin = new Skin(Gdx.files.internal("skin/titlescreen-ui.json"));
        game.skin.addRegions(new TextureAtlas("skin/titlescreen-ui.atlas"));
        initMenuButtons();

    }

    public void initBackground() { // initializes backgrounds in array
        game.textureAtlas = new TextureAtlas("backgrounds.atlas");
        BACKGROUNDS[0] = game.textureAtlas.findRegion("binary-bg-0");
        BACKGROUNDS[1] = game.textureAtlas.findRegion("binary-bg-1");
        BACKGROUNDS[2] = game.textureAtlas.findRegion("binary-bg-2");
        BACKGROUNDS[3] = game.textureAtlas.findRegion("binary-bg-3");

    }

    private void initMenuButtons() { // initializes main menu buttons
        // creating button objects
        newSession = new TextButton("New Session", game.skin, "menu-button-big");
        newSession.setSize(510, 80);

        settings = new TextButton("Settings", game.skin, "menu-button-small");
        settings.setSize(510, 40);

        quit = new TextButton("Quit", game.skin, "menu-button-exit");
        settings.setSize(510, 40);

        // setting position of button
        float lastY = 250;
        newSession.setPosition(100, lastY);

        // adding click listener
        newSession.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {}
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new GameScreen(game));
                dispose();
                return true;
            }
        });

        // adding the button to group as an actor
        titlescreenGroup.addActor(newSession);

        lastY -= settings.getHeight() + 1; // calculates height of next button
        settings.setPosition(100, lastY);
        settings.addListener(new InputListener() { // on click opens settings window
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {}
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                settingsScreen.getSettingsGroup().setVisible(true);
                titlescreenGroup.setVisible(false);
                return true;
            }
        });

        // adding the button to group as an actor
        titlescreenGroup.addActor(settings);

        lastY -= quit.getHeight() + 1; // calculates height of next button
        quit.setPosition(100, lastY);
        quit.addListener(new InputListener() { // on click, exits game and disposes
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {}
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                dispose();
                game.dispose();
                Gdx.app.exit();
                return true;
            }
        });

        // adding the button to group as an actor
        titlescreenGroup.addActor(quit);
        // adding actor group to stage
        stage.addActor(titlescreenGroup);
    }

    @Override
    public void render(float deltaTime) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // this cryptic line clears the screen
        game.batch.begin();

        // scrolling background

        renderBackground(deltaTime);

        game.batch.end();
        game.batch.begin();
        stage.draw();
        stage.act();
        game.batch.end();

        if (settingsScreen.getSettingsGroup().isVisible()) { // renders settings screen if visible
            settingsScreen.render(deltaTime, titlescreenGroup);
        }


    }

    // code for rendering the background
    public void renderBackground(float deltaTime) {
        BACKGROUNDOFFSETS[0] += deltaTime * BACKGROUNDSCROLLINGSPEED / 8;
        BACKGROUNDOFFSETS[1] += deltaTime * BACKGROUNDSCROLLINGSPEED / 4;
        BACKGROUNDOFFSETS[2] += deltaTime * BACKGROUNDSCROLLINGSPEED / 2;
        BACKGROUNDOFFSETS[3] += deltaTime * BACKGROUNDSCROLLINGSPEED;

        for (int layer = 0; layer < BACKGROUNDOFFSETS.length; layer++) {
            if (BACKGROUNDOFFSETS[layer] > 1080) {
                BACKGROUNDOFFSETS[layer] = 0;
            }
            game.batch.draw(BACKGROUNDS[layer],
                    0, -BACKGROUNDOFFSETS[layer],
                    1920, 1080);
            game.batch.draw(BACKGROUNDS[layer], 0, -BACKGROUNDOFFSETS[layer] + 1080,
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
        for (int i = 0; i < BACKGROUNDS.length; i++) {
            BACKGROUNDS[i].getTexture().dispose();
        }
        settingsScreen.dispose();

    }

    @Override
    public void show() {

    }
}

