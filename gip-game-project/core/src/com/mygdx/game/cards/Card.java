package com.mygdx.game.cards;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.ScaleByAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;

import java.util.UUID;

public class Card extends Image {
    private int id;
    private String title;
    private CardType cardType;
    private int cost;
    private TextureRegion textureRegion;
    private boolean exhaust;
    private boolean deckDisplay = false;
    private final UUID uniqueIdentifier = UUID.randomUUID();
    private int handslot = 0;



    public Card(int id, String title, CardType cardType, int cost, TextureRegion textureRegion) {
        this.id = id;
        this.title = title;
        this.cardType = cardType;
        this.cost = cost;
        this.textureRegion = textureRegion;
        super.setDrawable(new TextureRegionDrawable(textureRegion));
        super.setAlign(1);
        super.setScaling(Scaling.fill);
        super.setSize(textureRegion.getRegionWidth(), textureRegion.getRegionHeight());
        this.exhaust = false;
        super.setName(title);
        this.setWidth(textureRegion.getRegionWidth());
        this.setHeight(textureRegion.getRegionHeight());
        initCard();
    }

    public Card(int id, String title, CardType cardType, int cost, TextureRegion textureRegion, boolean exhaust) {
        this.id = id;
        this.title = title;
        this.cardType = cardType;
        this.cost = cost;
        this.textureRegion = textureRegion;
        super.setDrawable(new TextureRegionDrawable(textureRegion));
        super.setAlign(1);
        super.setScaling(Scaling.fill);
        super.setSize(textureRegion.getRegionWidth(), textureRegion.getRegionHeight());
        this.exhaust = exhaust;
        super.setName(title);
        this.setWidth(textureRegion.getRegionWidth());
        this.setHeight(textureRegion.getRegionHeight());
        this.setScale(super.getScaleX(), super.getScaleY());
        initCard();
    }

    public Card(Card handCard) {
        this.id = handCard.getId();
        this.title = handCard.getTitle();
        this.cardType = handCard.getCardType();
        this.cost = handCard.getCost();
        this.textureRegion = handCard.getTextureRegion();
        //image = new Image(textureRegion);
        super.setDrawable(new TextureRegionDrawable(textureRegion));
        super.setAlign(1);
        super.setScaling(Scaling.fill);
        super.setSize(textureRegion.getRegionWidth(), textureRegion.getRegionHeight());
        this.exhaust = handCard.isExhaust();
        super.setName(title);
        this.setWidth(textureRegion.getRegionWidth());
        this.setHeight(textureRegion.getRegionHeight());
        this.setScale(super.getScaleX(), super.getScaleY());
        initCard();
    }

    public void initCard() {

        setBounds(getX(), getY(), getWidth(), getHeight());
        //setTouchable(Touchable.enabled);
        this.addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                super.enter(event, x, y, pointer, fromActor);
                ScaleByAction sba = new ScaleByAction();
                sba.setAmount(0.2F);
                sba.setDuration(.2F);
                setOrigin(Align.center);
                setAlign(Align.center);
                Card.this.addAction(sba);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                super.exit(event, x, y, pointer, toActor);
                System.out.println("oi exit");
                ScaleByAction sba = new ScaleByAction();
                sba.setAmount(-0.2F);
                sba.setDuration(.2F);
                setOrigin(Align.center);
                setAlign(Align.center);
                Card.this.addAction(sba);
            }
        });
    }

    @Override
    public void draw (Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    protected void positionChanged() {
        super.positionChanged();
    }

    @Override
    protected void scaleChanged() {
        super.scaleChanged();
    }

    @Override
    protected void sizeChanged() {
        super.sizeChanged();
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



}
