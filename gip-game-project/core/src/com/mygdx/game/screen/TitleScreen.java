package com.mygdx.game.screen;

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
import com.mygdx.game.GipGameProject;
import com.mygdx.game.save.SaveManager;
import com.mygdx.game.screen.settings.Settings;
import com.mygdx.game.screen.settings.SettingsScreen;

// class where all main menu logic goes

public class TitleScreen implements Screen {

    final GipGameProject game;

    // screen
    private final SettingsScreen settingsScreen;
    private final TextureRegion[] BACKGROUNDS = new TextureRegion[4]; // textures for main menu background
    private final Stage stage;


    // timing
    private final float[] BACKGROUNDOFFSETS = {0, 0, 0, 0};
    private final float BACKGROUNDSCROLLINGSPEED = (float) 1080 / 4;

    // main menu buttons
    private Button play;
    private Button continueRun;
    private Button abandonRun;
    private Button settings;
    private Button quit;

    private final Group titlescreenGroup;

    private Skin skin;


    public TitleScreen(final GipGameProject game) {
        System.out.println(SaveManager.exists());
        this.game = game;

        stage = new Stage(new ScreenViewport()); // creates new stage
        Gdx.input.setInputProcessor(stage); // enables input in stage
        titlescreenGroup = new Group();
        settingsScreen = new SettingsScreen(game, stage);

        // set up texture atlas for background
        initBackground();

        // setup texture atlas and skin for buttons
        skin = new Skin(Gdx.files.internal("skin/titlescreen-ui.json"));
        skin.addRegions(new TextureAtlas("skin/titlescreen-ui.atlas"));
        initMenuButtons();

        Settings settings = new Settings(game);
        settings.setFullscreenEnabled(settings.isFullscreenEnabled());
        //settings.changeResolution();
        settings.setMusicEnabled(settings.isMusicEnabled());
        settings.setSoundEffectsEnabled(settings.isSoundEffectsEnabled());
        settings.setSoundVolume(settings.getSoundVolume());
        settings.setMusicVolume(settings.getMusicVolume());

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
        play = new TextButton("Play", skin, "menu-button-big");
        play.setSize(510, 80);

        continueRun = new TextButton("Continue Run", skin, "menu-button-big");
        continueRun.setSize(510, 80);

        abandonRun = new TextButton("Abandon Run", skin, "menu-button-small");
        abandonRun.setSize(510, 40);

        settings = new TextButton("Settings", skin, "menu-button-small");
        settings.setSize(510, 40);

        quit = new TextButton("Quit", skin, "menu-button-exit");
        settings.setSize(510, 40);

        // setting position of button




        // adding click listener
        play.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {}
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new GameScreen(game));
                dispose();
                return true;
            }
        });

        continueRun.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {}
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new GameScreen(game));
                dispose();
                return true;
            }
        });


        abandonRun.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                SaveManager.abandon();
                abandonRun();
                return true;
            }
        });

        // adding the button to group as an actor

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

        posButtons();


        // adding actor group to stage
        stage.addActor(titlescreenGroup);
    }

    private void abandonRun() {
        titlescreenGroup.removeActor(continueRun);
        titlescreenGroup.removeActor(abandonRun);
        titlescreenGroup.addActor(play);
    }

    private void posButtons() {
        float lastY = 168.0F;
        quit.setPosition(100, lastY);
        lastY += quit.getHeight() + 1; // calculates height of next button
        settings.setPosition(100, lastY);
        lastY += settings.getHeight() + 1;

        play.setPosition(100, lastY);

        abandonRun.setPosition(100, lastY);
        lastY += abandonRun.getHeight() + 1;
        continueRun.setPosition(100, lastY);

        if (!SaveManager.exists()) {
            titlescreenGroup.addActor(play);
        } else {
            titlescreenGroup.addActor(continueRun);
            titlescreenGroup.addActor(abandonRun);
        }
        titlescreenGroup.addActor(settings);
        titlescreenGroup.addActor(quit);

        /*
        quit.setPosition(100, -40);
        settings.setPosition(100, -40);
        play.setPosition(100, -80);
        abandonRun.setPosition(100, -40);
        continueRun.setPosition(100, -80);


        if (!SaveManager.exists()) {
            titlescreenGroup.addActor(play);
        } else {
            titlescreenGroup.addActor(continueRun);
            titlescreenGroup.addActor(abandonRun);
        }
        titlescreenGroup.addActor(settings);
        titlescreenGroup.addActor(quit);

        float lastY = 168F;
        float duration = 0.5F;
        Action quitAction = Actions.moveTo(100, lastY, duration);
        quitAction.setActor(quit);
        lastY += quit.getHeight() + 1;
        Action settingsAction = Actions.moveTo(100, lastY, duration);
        settingsAction.setActor(settings);
        lastY += settings.getHeight() + 1;

        Action playAction = Actions.moveTo(100, lastY, duration);
        playAction.setActor(play);
        Action abandonAction = Actions.moveTo(100, lastY, duration);
        abandonAction.setActor(abandonRun);
        lastY += abandonRun.getHeight() + 1;
        Action continueAction = Actions.moveTo(100, lastY, duration);
        continueAction.setActor(continueRun);

        SequenceAction sqa = new SequenceAction();
        if (SaveManager.exists()) {
            sqa.addAction();
        }


         */



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
        skin.dispose();
    }

    @Override
    public void show() {

    }
}

