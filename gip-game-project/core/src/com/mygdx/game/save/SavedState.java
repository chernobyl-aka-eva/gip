package com.mygdx.game.save;

import com.mygdx.game.cards.SavedCard;

import java.util.ArrayList;

public class SavedState {
    private int health;
    private int money;
    private ArrayList<SavedCard> savedCards;
    private ArrayList<Integer> savedItemIndexes;
    private ArrayList<Integer> mapEventIds;
    private int currentEventId;

    public SavedState(int health, int money, ArrayList<SavedCard> savedCards, ArrayList<Integer> savedItemIndexes, ArrayList<Integer> mapEventIds, int currentEventId) {
        this.health = health;
        this.money = money;
        this.savedCards = savedCards;
        this.savedItemIndexes = savedItemIndexes;
        this.mapEventIds = mapEventIds;
        this.currentEventId = currentEventId;
    }

    public SavedState() {
    }


    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
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

    public ArrayList<Integer> getSavedItemIndexes() {
        return savedItemIndexes;
    }

    public void setSavedItemIndexes(ArrayList<Integer> savedItemIndexes) {
        this.savedItemIndexes = savedItemIndexes;
    }

    public ArrayList<Integer> getMapEventIds() {
        return mapEventIds;
    }

    public void setMapEventIds(ArrayList<Integer> mapEventIds) {
        this.mapEventIds = mapEventIds;
    }

    public int getCurrentEventId() {
        return currentEventId;
    }

    public void setCurrentEventId(int currentEventId) {
        this.currentEventId = currentEventId;
    }
}
