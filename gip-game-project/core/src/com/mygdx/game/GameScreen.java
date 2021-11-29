package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.ArrayList;

// class where all game logic goes

public class GameScreen implements Screen {

    final GipGameProject game;

    // variable for tracking elapsed time
    private float elapsed_time;

    // background texture region
    private TextureRegion gameBackground;
    private TextureRegion hudbar;
    private TextureRegion mapBackground;

    private boolean showMap;
    private boolean previousState = true;

    // effects texture region
    private ArrayList<TextureRegion> icons = new ArrayList<>();
    private ArrayList<TextureRegion> iconsEnemy = new ArrayList<>();

    // virus
    ArrayList<Effect> effects = new ArrayList<>();
    Virus eva = new Virus("Eva", 33, 0);

    // enemy
    ArrayList<Effect> effectsEnemy = new ArrayList<>();
    Enemy philip = new Enemy("Philip", 100, 0);

    // effect
    Effect strength = new Effect("strength", 1, 1, 5);
    Effect dexterity = new Effect("dexterity", 1, 1, 5);

    // animation object virus
    Animation <TextureRegion> idleAnimation;

    // animation object enemy
    Animation <TextureRegion> enemyIdleAnimation;

    // health bars
    ProgressBar health;
    ProgressBar healthEnemy;

   float x;
   float y;

    // stage for drawing widgets
    private Stage stage;

    private final Group gameScreenGroup;
    private final Group mapScreenGroup;

    public GameScreen(final GipGameProject game) {
        this.game = game;

        // adding effects
        effects.add(strength);
        effects.add(dexterity);

        effectsEnemy.add(dexterity);
        effectsEnemy.add(strength);

        // game stage
        stage = new Stage(new ScreenViewport());

        Gdx.input.setInputProcessor(stage);

        gameScreenGroup = new Group();
        mapScreenGroup = new Group();


        // game background
        game.textureAtlas = new TextureAtlas("other/game-ui-2.atlas");
        gameBackground = new TextureRegion();
        gameBackground = game.textureAtlas.findRegion("guidelines");
        hudbar = game.textureAtlas.findRegion("hud-bar");
        mapBackground = game.textureAtlas.findRegion("map-background");

        // effect icons
        for (int i = 0; i < effects.size(); i++){
            icons.add(game.textureAtlas.findRegion(effects.get(i).getEffectName()));
        }

        for (int i = 0; i < effectsEnemy.size(); i++){
            iconsEnemy.add(game.textureAtlas.findRegion(effectsEnemy.get(i).getEffectName()));
        }

        // game UI
        game.textureAtlas = new TextureAtlas("skin/game-ui.atlas");
        game.skin = new Skin(Gdx.files.internal("skin/game-ui.json"));
        game.skin.addRegions(new TextureAtlas("skin/game-ui.atlas"));

        Button map = new Button(game.skin);
        map.setPosition(stage.getWidth()-215, stage.getHeight()-map.getHeight()-5);

        map.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {

            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                if (previousState){
                    showMap = true;
                    previousState = false;
                    gameScreenGroup.setVisible(false);
                    mapScreenGroup.setVisible(true);
                }else{
                    showMap = false;
                    previousState = true;
                    gameScreenGroup.setVisible(true);
                    mapScreenGroup.setVisible(false);
                }
                return true;
            }
        });

        stage.addActor(map);

        Button mapReturn = new Button(game.skin, "return");
        mapReturn.setPosition(0, stage.getHeight()-870);
        mapScreenGroup.setVisible(false);
        mapReturn.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {

            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                showMap = false;
                previousState = true;
                mapScreenGroup.setVisible(false);
                gameScreenGroup.setVisible(true);
                return true;
            }
        });

        mapScreenGroup.addActor(mapReturn);
        stage.addActor(mapScreenGroup);

        // atlas for virus and enemy textures (animation)
        TextureAtlas idleSet = new TextureAtlas(Gdx.files.internal("animation/idle.atlas"));
        idleAnimation = new Animation<TextureRegion>(1 / 10f, idleSet.findRegions("idle"));
        idleAnimation.setFrameDuration(1/10f);

        TextureAtlas enemyIdleSet = new TextureAtlas(Gdx.files.internal("animation/enemyidle.atlas"));
        enemyIdleAnimation = new Animation<TextureRegion>(1 / 10f, enemyIdleSet.findRegions("enemyidle"));
        enemyIdleAnimation.setFrameDuration(1/10f);

        Actor nameAreaVirus = new Actor();
        nameAreaVirus.setSize(idleAnimation.getKeyFrame(0).getRegionWidth()*6,
                idleAnimation.getKeyFrame(0).getRegionHeight()*6);

        nameAreaVirus.setPosition(eva.getPositionX(), stage.getHeight()-750);
        final Label nameVirus = new Label("Virus", new Label.LabelStyle(game.font, Color.WHITE));
        x = nameAreaVirus.getX() + (nameAreaVirus.getWidth()-nameVirus.getWidth())/2;
        y = nameAreaVirus.getY() - 70;
        nameVirus.setPosition(x, y);
        gameScreenGroup.addActor(nameVirus);
        nameVirus.setVisible(false);
        nameAreaVirus.addListener(new ClickListener(){
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor){
                nameVirus.setVisible(true);
                game.log.debug("hover " + idleAnimation.getKeyFrame(0).getRegionWidth() + " " +  idleAnimation.getKeyFrame(0).getRegionHeight());
            }
            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor){
                nameVirus.setVisible(false);
                game.log.debug("nein hover" );
            }
        });

        stage.addActor(nameAreaVirus);

        Actor nameAreaEnemy = new Actor();
        nameAreaEnemy.setSize(enemyIdleAnimation.getKeyFrame(0).getRegionWidth()*6,
                enemyIdleAnimation.getKeyFrame(0).getRegionHeight()*6);
        nameAreaEnemy.setPosition(philip.getPositionX(), stage.getHeight()-750);
        final Label nameEnemy = new Label("Enemy", new Label.LabelStyle(game.font, Color.WHITE));
        x = nameAreaEnemy.getX() + (nameAreaEnemy.getWidth()-nameEnemy.getWidth())/2;
        y = nameAreaEnemy.getY() - 70;
        nameEnemy.setPosition(x, y);
        gameScreenGroup.addActor(nameEnemy);
        nameEnemy.setVisible(false);
        nameAreaEnemy.addListener(new ClickListener(){
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor){
                nameEnemy.setVisible(true);
                game.log.debug("hover " + enemyIdleAnimation.getKeyFrame(0).getRegionWidth() + " " +  enemyIdleAnimation.getKeyFrame(0).getRegionHeight());
            }
            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor){
                nameEnemy.setVisible(false);
                game.log.debug("nein hover" );
            }
        });
        stage.addActor(nameAreaEnemy);

        // initializing health bar virus
        health = new ProgressBar(0, eva.getHealth(), 1, false, game.skin);
        health.setValue(60); // hoeveel health de virus momenteel heeft
        //health.setPosition(400, 1080-780);
        health.setPosition(eva.getPositionX(), stage.getHeight()-780);
        gameScreenGroup.addActor(health);

        // initializing health bar enemy
        healthEnemy = new ProgressBar(0, 100, 1, false, game.skin);
        healthEnemy.setValue(3);
        healthEnemy.setPosition(philip.getPositionX(), stage.getHeight()-780);
        gameScreenGroup.addActor(healthEnemy);




        stage.addActor(gameScreenGroup);

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
        game.batch.draw(gameBackground,
                0,
                0,
                1920,
                1080);

        // draw virus
        game.batch.draw(currentFrame,
                eva.getPositionX(),
                stage.getHeight()-750,
                currentFrame.getRegionWidth()*6,
                currentFrame.getRegionHeight()*6);

        // draw enemy
        game.batch.draw(currentFrameEnemy,
                philip.getPositionX(),
                stage.getHeight()-750,
                currentFrameEnemy.getRegionWidth()*6,
                currentFrameEnemy.getRegionHeight()*6);

        if (effects.size() > 0){
            int padding = 0;
            for (int i = 0; i < effects.size(); i++){
                game.batch.draw(icons.get(i), eva.getPositionX()+padding, 1080-805);
                padding += 25;
            }
        }

        if (effectsEnemy.size() > 0){
            int padding = 0;
            for (int i = 0; i < effectsEnemy.size(); i++){
                game.batch.draw(iconsEnemy.get(i), philip.getPositionX()+padding, 1080-805);
                padding += 25;
            }
        }

        if (showMap){
            game.batch.draw(mapBackground, 0, 0, 1920, 1080);
        }

        // draw hud-bar
        game.batch.draw(hudbar, 0, stage.getHeight()-hudbar.getRegionHeight());

        game.batch.end();
        game.batch.begin();
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
