package com.mygdx.game.save;

import java.util.ArrayList;

public class SavedState {
    private int health;
    private int block;
    private int money;
    private ArrayList<SavedCard> savedCards;
    private ArrayList<SavedCard> drawCards;
    private ArrayList<SavedCard> handCards;
    private ArrayList<SavedCard> discardedCards;
    private ArrayList<SavedCard> exhaustedCards;
    private ArrayList<Integer> savedItemIndexes;
    private SavedMap savedMap;

    public SavedState(int health, int block, int money, ArrayList<SavedCard> savedCards, ArrayList<SavedCard> drawCards, ArrayList<SavedCard> handCards, ArrayList<SavedCard> discardedCards, ArrayList<SavedCard> exhaustedCards, ArrayList<Integer> savedItemIndexes, SavedMap savedMap) {
        this.health = health;
        this.block = block;
        this.money = money;
        this.savedCards = savedCards;
        this.drawCards = drawCards;
        this.handCards = handCards;
        this.discardedCards = discardedCards;
        this.exhaustedCards = exhaustedCards;
        this.savedItemIndexes = savedItemIndexes;
        this.savedMap = savedMap;
    }

    public SavedState() {
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

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public ArrayList<SavedCard> getSavedCards() {
        return savedCards;
    }

    public void setSavedCards(ArrayList<SavedCard> savedCards) {
        this.savedCards = savedCards;
    }

    public ArrayList<SavedCard> getDrawCards() {
        return drawCards;
    }

    public void setDrawCards(ArrayList<SavedCard> drawCards) {
        this.drawCards = drawCards;
    }

    public ArrayList<SavedCard> getHandCards() {
        return handCards;
    }

    public void setHandCards(ArrayList<SavedCard> handCards) {
        this.handCards = handCards;
    }

    public ArrayList<SavedCard> getDiscardedCards() {
        return discardedCards;
    }

    public void setDiscardedCards(ArrayList<SavedCard> discardedCards) {
        this.discardedCards = discardedCards;
    }

    public ArrayList<SavedCard> getExhaustedCards() {
        return exhaustedCards;
    }

    public void setExhaustedCards(ArrayList<SavedCard> exhaustedCards) {
        this.exhaustedCards = exhaustedCards;
    }

    public ArrayList<Integer> getSavedItemIndexes() {
        return savedItemIndexes;
    }

    public void setSavedItemIndexes(ArrayList<Integer> savedItemIndexes) {
        this.savedItemIndexes = savedItemIndexes;
    }

    public SavedMap getSavedMap() {
        return savedMap;
    }

    public void setSavedMap(SavedMap savedMap) {
        this.savedMap = savedMap;
    }
}
