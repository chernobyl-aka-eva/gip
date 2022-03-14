package com.mygdx.game.cards;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.ScaleByAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import com.mygdx.game.GipGameProject;

import java.util.UUID;

public class Card extends Image {
    private GipGameProject game;

    private int id;
    //private Label title;
    private String title;
    private String description;
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
    private CardManager cardManager;
<<<<<<< Updated upstream
=======
<<<<<<< HEAD
>>>>>>> Stashed changes
    private int damage = 0;
    private int block = 0;

    private FreeTypeFontGenerator generator = null;
    private BitmapFont font = null;
    //private Table textTable;
    //private Group titleGroup;
    //private Group descriptionGroup;

    //private Table canInfoTable;
    //private Group cardInfo;
    //private CardInfoType cardInfoType;
<<<<<<< Updated upstream
=======
=======
>>>>>>> 80246bd5905bdf143251ea28e8eacdfa58cf805f
>>>>>>> Stashed changes

    //constructor for card without exhaust
    public Card(int id, String title, String descriptionText, CardType cardType, int cost, TextureRegion textureRegion, float timeAdded, GipGameProject game) {
        this.game = game;
        this.title = title;
        this.description = descriptionText;
        this.id = id;
        this.cardType = cardType;
        this.cost = cost;
        this.textureRegion = textureRegion;
        super.setDrawable(new TextureRegionDrawable(textureRegion));
        //titleGroup = new Group();
        //descriptionGroup = new Group();

        generateTextTable(title);
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
    public Card(int id, String title, String descriptionText, CardType cardType, int cost, TextureRegion textureRegion, boolean exhaust, float timeAdded, GipGameProject game) {
        this.game = game;
        this.title = title;
        this.description = descriptionText;
        this.id = id;
        this.cardType = cardType;
        this.cost = cost;
        this.textureRegion = textureRegion;
        super.setDrawable(new TextureRegionDrawable(textureRegion));
        //titleGroup = new Group();
        //descriptionGroup = new Group();
        this.exhaust = exhaust;
        generateTextTable(title);
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

    //constructor used to make a copy of a card
    public Card(Card card, boolean deckDisplay) {
        this.game = card.getGame();

        //deckscreen card
        if (deckDisplay) {
            this.id = card.getId();
            this.title = card.getTitle();
            this.description = card.getDescription();
            //this.textTable = card.getTextTable();
            this.cardType = card.getCardType();
            this.cost = card.getCost();
            this.textureRegion = card.getTextureRegion();
            super.setDrawable(new TextureRegionDrawable(textureRegion));
            super.setAlign(1);
            super.setScaling(Scaling.fill);
            super.setSize(textureRegion.getRegionWidth(), textureRegion.getRegionHeight());
            this.exhaust = card.isExhaust();
            super.setName(title);
            this.setWidth(textureRegion.getRegionWidth());
            this.setHeight(textureRegion.getRegionHeight());
            this.setScale(super.getScaleX(), super.getScaleY());
            this.timeAdded = card.getTimeAdded();
            this.deckDisplay = deckDisplay;
            this.containingTable = null;
            //this.titleGroup = card.getTitleGroup();
            //this.descriptionGroup = card.getDescriptionGroup();
        }
        initCard();

    }

    public void generateTextTable(String titleText) {
        /*
        generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/pcsenior.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 30;
        font = generator.generateFont(parameter);
        Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.WHITE);
        titleText = titleText.toUpperCase(Locale.ROOT);
        title = new Label(titleText, labelStyle);
        titleGroup.addActor(title);
        titleGroup.setOrigin(Align.center);
        titleGroup.setPosition(getX()+getWidth()/2, getY()+getHeight()-100);
        //title.setPosition();

         */
    }

    //initializes card boundaries + adds listeners
    public void initCard() {
        //setColor(Color.WHITE);
        setColor(1, 1, 1, 1F);
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
                    /*
                    titleGroup.setOrigin(Align.center);
                    descriptionGroup.setOrigin(Align.center);
                    titleGroup.addAction(Actions.sequence(Actions.scaleBy(sba.getAmountX(), sba.getAmountY(), .2F)));
                    descriptionGroup.addAction(Actions.sequence(Actions.scaleBy(sba.getAmountX(), sba.getAmountY(), .2F)));

                     */
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
                    /*
                    titleGroup.addAction(Actions.sequence(Actions.scaleBy(sba.getAmountX(), sba.getAmountY(), .2F)));
                    descriptionGroup.addAction(Actions.sequence(Actions.scaleBy(sba.getAmountX(), sba.getAmountY(),.2F)));
                     */
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
                                //makes sure you aren't hovering over the last card in your hand
                                if (containingTable.getCells().indexOf(containingTable.getCell(currentCard), false) != containingTable.getCells().size - 1) {
                                    containingTable.getCell(currentCard).padRight(containingTable.getCell(currentCard).getPadRight() + currentCard.getWidth() / 3);
                                    containingTable.invalidate();
                                    containingTable.validate();
                                }
                                MoveToAction move = new MoveToAction();
                                move.setPosition(getX(), -200);
                                move.setDuration(0F);
                                Card.this.addAction(move);
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
                                    containingTable.getCell(currentCard).padRight(containingTable.getCell(currentCard).getPadRight() - currentCard.getWidth() / 3);
                                    containingTable.invalidate();
                                    containingTable.validate();
                                }
                                Card.this.setPosition(getX(), -343.0F);
                                containingTable.invalidate();
                                containingTable.validate();
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
        if (this.isVisible()) {
            //titleGroup.draw(batch, parentAlpha);
            //descriptionGroup.draw(batch, parentAlpha);
        }
    }

    //card act
    @Override
    public void act(float delta) {
        super.act(delta);
        /*
        if (titleGroup!=null) {
            titleGroup.act(delta);
        }
        if (descriptionGroup!=null) {
            descriptionGroup.act(delta);
        }

         */


    }

    //is called when position has changed
    @Override
    protected void positionChanged() {
        super.positionChanged();
        //titleGroup.setPosition(getX()+getWidth()/2-title.getWidth()/2, getY()+getHeight()-getHeight()*0.2F);

    }

    //is called when scale has changed
    @Override
    protected void scaleChanged() {
        super.scaleChanged();
    }

    @Override
    public void setScale(float scaleXY) {
        super.setScale(scaleXY);
        //titleGroup.setScale(scaleXY);
        //descriptionGroup.setScale(scaleXY);
    }

    //is called when size has changed
    @Override
    protected void sizeChanged() {
        super.sizeChanged();
    }

    private void dispose() {
        if (generator!=null) {
            generator.dispose();
        }
        if (font !=null) {
            font.dispose();
        }
    }

    //getters & setters


    public GipGameProject getGame() {
        return game;
    }

    public void setGame(GipGameProject game) {
        this.game = game;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

<<<<<<< Updated upstream
=======
<<<<<<< HEAD
>>>>>>> Stashed changes
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getBlock() {
        return block;
    }

    public void setBlock(int block) {
        this.block = block;
    }

<<<<<<< Updated upstream
=======
=======
>>>>>>> 80246bd5905bdf143251ea28e8eacdfa58cf805f
>>>>>>> Stashed changes
}
