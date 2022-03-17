package com.mygdx.game.effect;

public class Effect {
    //   NAME ------------------- EFFECT ------------------- STACKS?
    private int id;
    private String effectName;
    private boolean stacks;
    private int stack = 1;
    private int limitTurns = -1;
    private boolean active = false;


    public Effect(int id, String effectName, boolean stacks) {
        this.id = id;
        this.effectName = effectName;
        this.stacks = stacks;
    }

    public Effect(int id, String effectName, boolean stacks, int limitTurns) {
        this.id = id;
        this.effectName = effectName;
        this.stacks = stacks;
        this.limitTurns = limitTurns;
    }

    public Effect(int id, String effectName, boolean stacks, int stack, int limitTurns, boolean active) {
        this.id = id;
        this.effectName = effectName;
        this.stacks = stacks;
        this.stack = stack;
        this.limitTurns = limitTurns;
        this.active = active;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStack() {
        return stack;
    }

    public void setStack(int stack) {
        this.stack = stack;
    }

    public String getEffectName() {
        return effectName;
    }

    public void setEffectName(String effectName) {
        this.effectName = effectName;
    }

    public boolean isStacks() {
        return stacks;
    }

    public void setStacks(boolean stacks) {
        this.stacks = stacks;
    }

    public int getLimitTurns() {
        return limitTurns;
    }

    public void setLimitTurns(int limitTurns) {
        this.limitTurns = limitTurns;
    }
}
