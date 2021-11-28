package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

// class where all game logic goes

public class GameScreen implements Screen {

    final GipGameProject game;

    // variable for tracking elapsed time
    private float elapsed_time;

    // background texture region
    private TextureRegion gameBackground;

    // animation object virus
    Animation <TextureRegion> idleAnimation;

    // animation object enemy
    Animation <TextureRegion> enemyIdleAnimation;

    // health bars
    ProgressBar health;
    ProgressBar healthEnemy;

    // stage for drawing widgets
    private Stage stage;

    public GameScreen(final GipGameProject game) {
        this.game = game;

        // game background
        game.textureAtlas = new TextureAtlas("gameGuidelines.atlas");
        gameBackground = new TextureRegion();
        gameBackground = game.textureAtlas.findRegion("guidelines");

        // game UI
        game.skin = new Skin(Gdx.files.internal("skin/game-ui.json"));
        game.skin.addRegions(new TextureAtlas("skin/game-ui.atlas"));

        // game stage
        stage = new Stage(new ScreenViewport());

        // atlas for virus and enemy textures (animation)
        TextureAtlas idleSet = new TextureAtlas(Gdx.files.internal("animation/idle.atlas"));
        idleAnimation = new Animation<TextureRegion>(1 / 10f, idleSet.findRegions("idle"));
        idleAnimation.setFrameDuration(1/10f);

        TextureAtlas enemyIdleSet = new TextureAtlas(Gdx.files.internal("animation/enemyidle.atlas"));
        enemyIdleAnimation = new Animation<TextureRegion>(1 / 10f, enemyIdleSet.findRegions("enemyidle"));
        enemyIdleAnimation.setFrameDuration(1/10f);

        // initializing health bar virus
        health = new ProgressBar(0, 100, 1, false, game.skin);
        health.setValue(100);
        health.setPosition(400, 1080-780);
        stage.addActor(health);

        // initializing health bar enemy
        healthEnemy = new ProgressBar(0, 100, 1, false, game.skin);
        healthEnemy.setValue(3);
        healthEnemy.setPosition(1520, 1050-750);
        stage.addActor(healthEnemy);
    }

    @Override
    public void render(float delta) {
        // clear screen
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // delta = the time in seconds since last render
        elapsed_time += delta;

        // getting the right frame for the animation
        TextureRegion currentFrame = idleAnimation.getKeyFrame(elapsed_time, true);
        TextureRegion currentFrameEnemy = enemyIdleAnimation.getKeyFrame(elapsed_time, true);

        game.batch.begin();
        // draw background
        game.batch.draw(gameBackground, 0,0,
                1920, 1080);

        // draw virus
        game.batch.draw(currentFrame,
                380,
                1080-750,
                currentFrame.getRegionWidth()*6,
                currentFrame.getRegionHeight()*6);

        // draw enemy
        game.batch.draw(currentFrameEnemy,
                1500,
                1080-750,
                currentFrameEnemy.getRegionWidth()*6,
                currentFrameEnemy.getRegionHeight()*6);

        stage.draw();
        stage.act(delta);
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
    public void show() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
