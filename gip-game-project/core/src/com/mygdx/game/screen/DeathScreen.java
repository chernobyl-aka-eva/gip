package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.GipGameProject;

public class DeathScreen implements Screen {
    final private GipGameProject game;

    // screen
    private final TextureRegion[] BACKGROUNDS = new TextureRegion[4];

    // timing for the background
    private final float[] BACKGROUNDOFFSETS = {0, 0, 0, 0};
    private final float BACKGROUNDSCROLLINGSPEED = (float) 1080 / 4;

    // you died label and deathscreen buttons
    private final Label label;
    private final Label.LabelStyle style;
    private final BitmapFont font;
    private Button mainMenu;
    private Button quit;
    

    // stage
    private final Stage deathStage;

    // for going back to titlescreen
    private final TitleScreen titleScreen;

    public DeathScreen(GipGameProject game) {
        this.game = game;


        titleScreen = new TitleScreen(game);

        deathStage = new Stage(new ScreenViewport()); // creates new stage
        Gdx.input.setInputProcessor(deathStage); // enables input in stage

        // set up texture atlas for background
        initBackground();

        // setup texture skin for buttons

        style = new Label.LabelStyle();
        font = new BitmapFont(Gdx.files.internal("fonts/pcsenior.fnt"));
        style.font = font;
        style.fontColor = Color.WHITE;

        label = new Label("You Died", style);
        label.setFontScale(3f);
        label.setPosition((deathStage.getWidth() / 2) - 200, deathStage.getHeight() / 2 + 300);

        deathStage.addActor(label);


        // setup texture atlas and skin for buttons
        
        initButton();

    }

    private void initBackground(){
        game.textureAtlas = new TextureAtlas("screen/backgrounds.atlas");
        BACKGROUNDS[0] = game.textureAtlas.findRegion("binary-bg-0");
        BACKGROUNDS[1] = game.textureAtlas.findRegion("binary-bg-1");
        BACKGROUNDS[2] = game.textureAtlas.findRegion("binary-bg-2");
        BACKGROUNDS[3] = game.textureAtlas.findRegion("binary-bg-3");
    }

    private void initButton(){

        // initializing Main Menu button
        mainMenu = new TextButton("Main Menu", titleScreen.getSkin(), "menu-button-small");
        mainMenu.setSize(400, 80);

        quit = new TextButton("Quit", titleScreen.getSkin(), "menu-button-exit");
        quit.setSize(400, 40);

        mainMenu.setPosition((deathStage.getWidth() / 2) - 220, deathStage.getHeight() - 700);


        mainMenu.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {}
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new TitleScreen(titleScreen.game));
                dispose();
                return true;
            }
        });

        deathStage.addActor(mainMenu);

        // initializing Quit button

        quit.setPosition((deathStage.getWidth() / 2) - 220, deathStage.getHeight() - 750);

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

        deathStage.addActor(quit);

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float deltaTime) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // this cryptic line clears the screen
        game.batch.begin();

        // scrolling background

        renderBackground(deltaTime);

        game.batch.end();

        game.batch.begin();
        deathStage.draw();
        deathStage.act();
        game.batch.end();


    }

    // code for rendering the background
    public void renderBackground(float deltaTime) {
        BACKGROUNDOFFSETS[0] += deltaTime * BACKGROUNDSCROLLINGSPEED/4;
        BACKGROUNDOFFSETS[1] += deltaTime * BACKGROUNDSCROLLINGSPEED;
        BACKGROUNDOFFSETS[2] += deltaTime * BACKGROUNDSCROLLINGSPEED;
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
    public void resize(int i, int i1) {

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
