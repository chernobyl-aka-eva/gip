package com.mygdx.game.enemy;

public class Enemy {

    //position and dimension
    private int positionX = 1480;

    // characterics
    //name
    private String name = "Enemy";
    //health
    private int health = 100;
    //block
    private int block = 0;
    //relics ?

    //potions ?

    public Enemy(String name, int health, int block) {
        this.name = name;
        this.health = health;
        this.block = block;
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
