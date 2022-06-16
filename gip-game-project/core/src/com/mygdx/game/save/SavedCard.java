package com.mygdx.game.save;

import com.mygdx.game.cards.CardType;

public class SavedCard {
    private int id;
    private String title;
    private String descriptionText;
    private CardType cardType;
    private String rarity;
    private int cost;
    private boolean exhaust;
    private boolean upgraded;
    private boolean isUnplayable;
    private float elapsed_time;

    public SavedCard(int id, String title, String descriptionText, CardType cardType, String rarity, int cost, boolean upgraded, boolean exhaust, boolean isUnplayable, float elapsed_time) {
        this.id = id;
        this.title = title;
        this.descriptionText = descriptionText;
        this.cardType = cardType;
        this.rarity = rarity;
        this.cost = cost;
        this.exhaust = exhaust;
        this.upgraded = upgraded;
        this.isUnplayable = isUnplayable;
        this.elapsed_time = elapsed_time;
    }

    public SavedCard() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescriptionText() {
        return descriptionText;
    }

    public void setDescriptionText(String descriptionText) {
        this.descriptionText = descriptionText;
    }

    public CardType getCardType() {
        return cardType;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

    public String getRarity() {
        return rarity;
    }

    public void setRarity(String rarity) {
        this.rarity = rarity;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public boolean isExhaust() {
        return exhaust;
    }

    public void setExhaust(boolean exhaust) {
        this.exhaust = exhaust;
    }

    public boolean isUpgraded() {
        return upgraded;
    }

    public void setUpgraded(boolean upgraded) {
        this.upgraded = upgraded;
    }

    public boolean isUnplayable() {
        return isUnplayable;
    }

    public void setUnplayable(boolean unplayable) {
        isUnplayable = unplayable;
    }

    public float getElapsed_time() {
        return elapsed_time;
    }

    public void setElapsed_time(float elapsed_time) {
        this.elapsed_time = elapsed_time;
    }
}
