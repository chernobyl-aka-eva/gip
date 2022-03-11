package com.mygdx.game.monster;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.GipGameProject;

public class Monster extends Actor {

    private int id;

    private GipGameProject game;

    //position and dimension
    private int positionX = 1480;
    // characterics
    //name
    private String name;
    //health
    private int health;
    private int maxHealth;
    //block
    private int maxBlock;
    //texture region
    private Animation<TextureRegion> monsterIdleAnimation;
    //stage
    Stage stage;
    private Group gameScreenGroup;

    private final Label monsterName;
    private ProgressBar monsterHealthBar;
    private Actor nameAreaMonster;

    private float elapsed_time;


    public Monster(final GipGameProject game, int id, String name, int maxHealth, int maxBlock, Stage stage, Group gameScreenGroup) {
        this.id = id;
        this.name = name;
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        this.maxBlock = maxBlock;
        this.game = game;
        this.stage = stage;
        this.gameScreenGroup = gameScreenGroup;

        game.skin = new Skin(Gdx.files.internal("skin/game-ui.json"));

        elapsed_time = 0f;
        // Animation
        TextureAtlas monsterIdleSet = new TextureAtlas(Gdx.files.internal("animation/enemyidle.atlas"));
        monsterIdleAnimation = new Animation<TextureRegion>(1 / 10f, monsterIdleSet.findRegions("enemyidle"));
        monsterIdleAnimation.setFrameDuration(3 / 10f);

        nameAreaMonster = new Actor();

        nameAreaMonster.setSize(monsterIdleAnimation.getKeyFrame(0).getRegionWidth() * 6,
                monsterIdleAnimation.getKeyFrame(0).getRegionHeight() * 6);
        nameAreaMonster.setPosition(positionX, stage.getHeight() - 750);
        // Monster Name
        monsterName = new Label(name, new Label.LabelStyle(game.font, Color.WHITE));
        float x = nameAreaMonster.getX() + (nameAreaMonster.getWidth() - monsterName.getWidth()) / 2;
        float y = nameAreaMonster.getY() - 70;
        monsterName.setPosition(x, y);
        monsterName.setVisible(false);

        initMonster();

        // Health Bar
        monsterHealthBar = new ProgressBar(0, 100, 1, false, game.skin);
        monsterHealthBar.setValue(health);
        monsterHealthBar.setPosition(positionX, stage.getHeight() - 780);




    }

    public void initMonster() {

        nameAreaMonster.addListener(new ClickListener() {
            GipGameProject gameProject = game; // detects if hovering over enemy
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                if (pointer == -1) {
                    monsterName.setVisible(true);
                    gameProject.log.debug("hover " + monsterIdleAnimation.getKeyFrame(0).getRegionWidth() + " " + monsterIdleAnimation.getKeyFrame(0).getRegionHeight());
                    System.out.println("cock");
                }
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                if (pointer == -1) {
                    monsterName.setVisible(false);
                    gameProject.log.debug("nein hover");
                    System.out.println("non cock");
                }
            }
        });
        gameScreenGroup.addActor(nameAreaMonster);
    }

    //rip setvisible 05/03/2022 - 05/03/2022 🥺

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        if (visible) {
            //monsterName.setVisible(true);
            nameAreaMonster.setVisible(true);
            monsterHealthBar.setVisible(true);
        } else {
            monsterName.setVisible(false);
            nameAreaMonster.setVisible(false);
            monsterHealthBar.setVisible(false);
        }

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if (this.isVisible()){
            elapsed_time += Gdx.graphics.getDeltaTime();
            TextureRegion currentFrameMonster = monsterIdleAnimation.getKeyFrame(elapsed_time, true);
            game.batch.draw(currentFrameMonster,
                    1480,
                    Gdx.graphics.getHeight() - 750,
                    currentFrameMonster.getRegionWidth() * 6,
                    currentFrameMonster.getRegionHeight() * 6);


            if (monsterName.isVisible()) {
                monsterName.draw(batch, parentAlpha);
            }
            monsterHealthBar.draw(batch, parentAlpha);
        }

    }



    @Override
    public void act(float delta) {
        super.act(delta);
        if (this.isVisible()) {
            monsterHealthBar.act(delta);

            nameAreaMonster.act(delta);
            if (monsterName.isVisible()) {
                monsterName.act(delta);
            }
        }

    }

    // getters and setters


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

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
        monsterHealthBar.setValue(health);
    }

    public int getMaxBlock() {
        return maxBlock;
    }

    public void setMaxBlock(int maxBlock) {
        this.maxBlock = maxBlock;
    }

    public Animation<TextureRegion> getMonsterIdleAnimation() {
        return monsterIdleAnimation;
    }

    public void setMonsterIdleAnimation(Animation<TextureRegion> monsterIdleAnimation) {
        this.monsterIdleAnimation = monsterIdleAnimation;
    }

    public Label getMonsterName() {
        return monsterName;
    }

    public ProgressBar getMonsterHealthBar() {
        return monsterHealthBar;
    }

    public void setMonsterHealthBar(ProgressBar monsterHealthBar) {
        this.monsterHealthBar = monsterHealthBar;
    }

    public Actor getNameAreaMonster() {
        return nameAreaMonster;
    }

    public void setNameAreaMonster(Actor nameAreaMonster) {
        this.nameAreaMonster = nameAreaMonster;
    }
}


