package com.mygdx.game.cards;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.ScaleByAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;

import java.util.UUID;

public class Card extends Image {
    private int id;
    private String title;
    private Label description;
    private CardType cardType;
    private int cost;
    private TextureRegion textureRegion;
    private boolean exhaust;
    private boolean deckDisplay = false;
    private final UUID uniqueIdentifier = UUID.randomUUID(); //generates unique number to identify card
    private int handslot = 0;
    private float timeAdded;
    private Table containingTable;
    private boolean isDragging;

    //constructor for card without exhaust
    public Card(int id, String title, CardType cardType, int cost, TextureRegion textureRegion, float timeAdded) {
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
        this.timeAdded = timeAdded;
        this.deckDisplay = false;
        this.containingTable = null;
        initCard();
    }

    //constructor for card with specified exhaust
    public Card(int id, String title, CardType cardType, int cost, TextureRegion textureRegion, boolean exhaust, float timeAdded) {
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
        this.timeAdded = timeAdded;
        this.deckDisplay = false;
        this.containingTable = null;
        initCard();
    }

    //constructor used to make a copy of a card (deckscreen)
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
        this.timeAdded = handCard.getTimeAdded();
        this.deckDisplay = !handCard.deckDisplay;
        this.containingTable = null;
        initCard();
    }

    //initializes card boundaries + adds listeners
    public void initCard() {
        isDragging = false;
        setBounds(getX(), getY(), getWidth(), getHeight());
        //setTouchable(Touchable.enabled);

        if (deckDisplay) {
            this.addListener(new ClickListener() {
                boolean exited = true;

                @Override
                //called when mouse hovers over card
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    super.enter(event, x, y, pointer, fromActor);
                    ScaleByAction sba = new ScaleByAction();
                    sba.setAmount(0.1F);
                    sba.setDuration(.2F);
                    setOrigin(Align.center);
                    setAlign(Align.center);
                    Card.this.addAction(sba);
                    exited = false;

                }

                @Override
                //called when mouse stops hovering over card
                public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                    super.exit(event, x, y, pointer, toActor);
                    ScaleByAction sba = new ScaleByAction();
                    sba.setAmount(-0.1F);
                    sba.setDuration(.2F);
                    setOrigin(Align.center);
                    setAlign(Align.center);
                    Card.this.addAction(sba);
                    exited = true;

                }
            });
        } else {
            final Card currentCard = this;
            this.addListener(new ClickListener() {
                @Override
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    super.enter(event, x, y, pointer, fromActor);
                    if (containingTable!=null) {
                        if (pointer == -1) {
                            if (!isDragging) {
                                if (containingTable.getCells().indexOf(containingTable.getCell(currentCard), false) != containingTable.getCells().size - 1) {
                                    containingTable.getCell(currentCard).padRight(containingTable.getCell(currentCard).getPadRight() + currentCard.getWidth() / 4);
                                    containingTable.invalidate();
                                    containingTable.validate();
                                }
                            }
                        }
                    }

                }
                @Override
                public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                    super.exit(event, x, y, pointer, toActor);
                    if (containingTable!=null) {
                        if (pointer == -1) {
                            if (!isDragging) {
                                if (containingTable.getCells().indexOf(containingTable.getCell(currentCard), false) != containingTable.getCells().size - 1) {
                                    containingTable.getCell(currentCard).padRight(containingTable.getCell(currentCard).getPadRight() - currentCard.getWidth() / 4);
                                    containingTable.invalidate();
                                    containingTable.validate();
                                }
                            }
                        }
                    }

                }
            });
        }

    }

    //card draw
    @Override
    public void draw (Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }

    //card act
    @Override
    public void act(float delta) {
        super.act(delta);
    }

    //is called when position has changed
    @Override
    protected void positionChanged() {
        super.positionChanged();
    }

    //is called when scale has changed
    @Override
    protected void scaleChanged() {
        super.scaleChanged();
    }

    //is called when size has changed
    @Override
    protected void sizeChanged() {
        super.sizeChanged();
    }

    //getters & setters


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

    public boolean isExhaust() {
        return exhaust;
    }

    public void setExhaust(boolean exhaust) {
        this.exhaust = exhaust;
    }

    public boolean isDeckDisplay() {
        return deckDisplay;
    }

    public void setDeckDisplay(boolean deckDisplay) {
        this.deckDisplay = deckDisplay;
    }

    public UUID getUniqueIdentifier() {
        return uniqueIdentifier;
    }

    public int getHandslot() {
        return handslot;
    }

    public void setHandslot(int handslot) {
        this.handslot = handslot;
    }

    public float getTimeAdded() {
        return timeAdded;
    }

    public void setTimeAdded(float timeAdded) {
        this.timeAdded = timeAdded;
    }

    public Table getContainingTable() {
        return containingTable;
    }

    public void setContainingTable(Table containingTable) {
        this.containingTable = containingTable;
    }

    public boolean isDragging() {
        return isDragging;
    }

    public void setDragging(boolean dragging) {
        isDragging = dragging;
        containingTable.invalidate();
        containingTable.validate();
    }
}
