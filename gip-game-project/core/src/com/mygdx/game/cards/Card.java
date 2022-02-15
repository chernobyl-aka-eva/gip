package com.mygdx.game.cards;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.mygdx.game.TitleScreen;

import java.util.UUID;

public class Card extends Actor {
    private int id;
    private Label title;
    private CardType cardType;
    private Label description;
    private int cost;
    private TextureRegion textureRegion;
    private Image background;
    private boolean exhaust;
    private final UUID uniqueIdentifier = UUID.randomUUID();
    private int handslot = 0;

    public Card(int id, Label title, CardType cardType, Label description, int cost, TextureRegion textureRegion) {
        this.id = id;
        this.title = title;
        this.cardType = cardType;
        this.description = description;
        this.cost = cost;
        this.textureRegion = textureRegion;
        background = new Image(textureRegion);
        this.exhaust = false;
        super.setName(String.valueOf(title.getText()));
        System.out.println(super.getName());
    }

    public Card(int id, Label title, CardType cardType, Label description, int cost, TextureRegion textureRegion, boolean exhaust) {
        this.id = id;
        this.title = title;
        this.cardType = cardType;
        this.description = description;
        this.cost = cost;
        this.textureRegion = textureRegion;
        this.exhaust = exhaust;
        super.setName(title.getName());
    }

    @Override
    public void draw (Batch batch, float parentAlpha) {
        //batch.draw(textureRegion, 600 + (textureRegion.getRegionWidth()*handslot), 100);
        background.draw(batch, parentAlpha);
        title.draw(batch, parentAlpha);
        description.draw(batch, parentAlpha);

    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Label getTitle() {
        return title;
    }

    public void setTitle(Label title) {
        this.title = title;
    }

    public Label getDescription() {
        return description;
    }

    public void setDescription(Label description) {
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

    public UUID getUniqueIdentifier() {
        return uniqueIdentifier;
    }

    public boolean isExhaust() {
        return exhaust;
    }

    public void setExhaust(boolean exhaust) {
        this.exhaust = exhaust;
    }

    public int getHandslot() {
        return handslot;
    }

    public void setHandslot(int handslot) {
        this.handslot = handslot;
    }

    public Image getBackground() {
        return background;
    }

    public void setBackground(Image background) {
        this.background = background;
    }
}
