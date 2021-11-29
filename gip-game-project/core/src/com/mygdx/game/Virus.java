package com.mygdx.game;

public class Virus {

    //position and dimension
    private int positionX = 400;

    // characterics
    //name
    private String name = "Virus";
    //health
    private int health = 75;
    //block
    private int block = 0;
    //relics ?

    //potions ?

    // constructor
    public Virus(String name, int health, int block) {
        this.name = name;
        this.health = health;
        this.block = block;
    }

    // setters & getters
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

    public int getPositionX() {
        return positionX;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

}
