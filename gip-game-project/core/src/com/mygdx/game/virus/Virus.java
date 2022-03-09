package com.mygdx.game.virus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.GipGameProject;

public class Virus extends Actor{

    private GipGameProject game;

    //position and dimension
    private int positionX = 400;

    // characterics
    //name
    private String name;
    //health
    //private int health = 75;
    private int maxHealth = 75;
    //block
    //private int block = 0;
    private int maxBlock = 200;
    //relics ?

    //potions ?

    //texture region
    private Animation<TextureRegion> idleAnimation;

    //stage
    Stage stage;

    private final Label virusName;
    private ProgressBar virusHealthBar;
    private Actor nameAreaVirus;

    private float elapsed_time;

    // constructor
    public Virus(GipGameProject game, String name, int maxHealth, int maxBlock, Stage stage) {
        this.name = name;
        this.maxHealth = maxHealth;
        this.maxBlock = maxBlock;
        this.game = game;
        this.stage = stage;

        game.skin = new Skin(Gdx.files.internal("skin/game-ui.json"));

        elapsed_time = 0f;
        // Animation
        TextureAtlas idleSet = new TextureAtlas(Gdx.files.internal("animation/idle.atlas"));
        idleAnimation = new Animation<TextureRegion>(1 / 10f, idleSet.findRegions("idle"));
        idleAnimation.setFrameDuration(3 / 10f);

        nameAreaVirus = new Actor();

        nameAreaVirus.setSize(idleAnimation.getKeyFrame(0).getRegionWidth() * 6, // sets size of area to check
                idleAnimation.getKeyFrame(0).getRegionHeight() * 6);
        nameAreaVirus.setPosition(positionX, stage.getHeight() - 750); // sets position of checking area

        // Virus Name
        virusName = new Label(name, new Label.LabelStyle(game.font, Color.WHITE));
        float x = nameAreaVirus.getX() + (nameAreaVirus.getWidth() - virusName.getWidth()) / 2;
        float y = nameAreaVirus.getY() - 70;
        virusName.setPosition(x, y);
        virusName.setVisible(false);

        initVirus();

        // Health Bar
        virusHealthBar = new ProgressBar(0, 100, 1, false, game.skin);
        virusHealthBar.setValue(50);
        virusHealthBar.setPosition(positionX, stage.getHeight() - 780);

    }

    public void initVirus(){
        nameAreaVirus.addListener(new ClickListener() { // detects if hovering over player
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                virusName.setVisible(true);
                game.log.debug("hover " + idleAnimation.getKeyFrame(0).getRegionWidth() + " " + idleAnimation.getKeyFrame(0).getRegionHeight());
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                virusName.setVisible(false);
                game.log.debug("not hover");
            }
        });
        stage.addActor(nameAreaVirus);
    }

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        if (visible) {
            nameAreaVirus.setVisible(true);
            virusHealthBar.setVisible(true);
        }else{
            virusName.setVisible(false);
            nameAreaVirus.setVisible(false);
            virusHealthBar.setVisible(false);
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if(this.isVisible()){
            elapsed_time += Gdx.graphics.getDeltaTime();
            TextureRegion currentFrame = idleAnimation.getKeyFrame(elapsed_time, true);
            game.batch.draw(currentFrame,
                    positionX,
                    stage.getHeight() - 750,
                    currentFrame.getRegionWidth() * 6,
                    currentFrame.getRegionHeight() * 6);

            if (virusName.isVisible())  {
                virusName.draw(batch, parentAlpha);
            }
            virusHealthBar.draw(batch, parentAlpha);
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (this.isVisible()) {
            virusHealthBar.act(delta);

            nameAreaVirus.act(delta);
            if (virusName.isVisible()) {
                virusName.act(delta);
            }
        }
    }

    // setters and getters

    public int getPositionX() {
        return positionX;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public int getMaxBlock() {
        return maxBlock;
    }

    public void setMaxBlock(int maxBlock) {
        this.maxBlock = maxBlock;
    }

    @Override
    public Stage getStage() {
        return stage;
    }

    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Label getVirusName() {
        return virusName;
    }

    public ProgressBar getVirusHealthBar() {
        return virusHealthBar;
    }

    public void setVirusHealthBar(ProgressBar virusHealthBar) {
        this.virusHealthBar = virusHealthBar;
    }

    public Actor getNameAreaVirus() {
        return nameAreaVirus;
    }

    public void setNameAreaVirus(Actor nameAreaVirus) {
        this.nameAreaVirus = nameAreaVirus;
    }
}
