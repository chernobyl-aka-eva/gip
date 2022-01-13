package com.mygdx.game.cards;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Card {
    private int id;
    private String name;
    private CardType cardType;
    private String description;
    private int cost;
    private TextureRegion textureRegion;

    public Card(int id, String name, CardType cardType, String description, int cost, TextureRegion textureRegion) {
        this.id = id;
        this.name = name;
        this.cardType = cardType;
        this.description = description;
        this.cost = cost;
        this.textureRegion = textureRegion;
    }

    public Card(int id, String name, CardType cardType, String description, int cost) {
        this.id = id;
        this.name = name;
        this.cardType = cardType;
        this.description = description;
        this.cost = cost;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CardType getCardType() {
        return cardType;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public TextureRegion getTextureRegion() {
        return textureRegion;
    }

    public void setTextureRegion(TextureRegion textureRegion) {
        this.textureRegion = textureRegion;
    }
}
