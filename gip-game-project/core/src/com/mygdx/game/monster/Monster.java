package com.mygdx.game.monster;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;

public class Monster extends Actor {

    private int id;

    //position and dimension
    private int positionX = 1480;
    // characterics
    //name
    private String name = "Monster";
    //health
    private int health = 100;
    //block
    private int block = 0;
    //texture region
    private Animation<TextureRegion> monsterIdleAnimation;


    public Monster(int id, String name, int health, int block) {
        this.id = id;
        this.name = name;
        this.health = health;
        this.block = block;
        this.monsterIdleAnimation = monsterIdleAnimation;

        TextureAtlas monsterIdleSet = new TextureAtlas(Gdx.files.internal("animation/enemyidle.atlas"));
        monsterIdleAnimation = new Animation<TextureRegion>(1 / 10f, monsterIdleSet.findRegions(name));
        monsterIdleAnimation.setFrameDuration(1 / 10f);
    }

    public int getPositionX() {
        return positionX;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getBlock() {
        return block;
    }

    public void setBlock(int block) {
        this.block = block;
    }

}
