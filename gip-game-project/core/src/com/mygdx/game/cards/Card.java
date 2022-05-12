package com.mygdx.game.cards;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.ScaleByAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Json;
import com.mygdx.game.GipGameProject;
import com.mygdx.game.cards.upgrades.CardUpgrade;
import com.mygdx.game.infobox.InfoboxTypes;


import java.util.ArrayList;
import java.util.Locale;
import java.util.UUID;

public class Card extends Table {
    private GipGameProject game;

    private int id;
    //private Label title;
    private String title;
    private String description;

    private CardType cardType;
    private int cost;
    private TextureRegion textureRegion;
    private Image cardImage;
    private String rarity;
    private boolean exhaust;
    private boolean upgraded;
    private boolean preview;
    private boolean inspect = false;
    private boolean deckDisplay = false;
    private final UUID uniqueIdentifier = UUID.randomUUID(); //generates unique number to identify card
    private int handslot = 0;
    private float timeAdded;
    private Table containingTable;
    private boolean isDragging;
    private int damage = 0;
    private int block = 0;


    private FreeTypeFontGenerator generator = null;
    private BitmapFont font = null;



    //constructor for card without exhaust
    public Card(int id, String title, String descriptionText, CardType cardType, String rarity, int cost, float timeAdded, GipGameProject game) {
        this.game = game;
        game.textureAtlas = new TextureAtlas("cards/cards-empty.atlas");
        this.title = title;
        this.description = descriptionText;
        this.id = id;
        this.cardType = cardType;
        this.rarity = rarity;
        this.cost = cost;
        this.textureRegion = game.textureAtlas.findRegion(cardType.name().toLowerCase(Locale.ROOT) + "-" + rarity);
        this.cardImage = new Image(new TextureRegionDrawable(textureRegion));
        super.setBackground(cardImage.getDrawable());
        this.setClip(true);
        this.align(1);
        this.setOrigin(Align.center);
        //super.setAlign(1);
        //super.setScaling(Scaling.fill);
        this.upgraded = false;
        this.preview = false;
        generateTextTable();
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
    public Card(int id, String title, String descriptionText, CardType cardType, String rarity, int cost, boolean exhaust, float timeAdded, GipGameProject game) {
        this.game = game;
        game.textureAtlas = new TextureAtlas("cards/cards-empty.atlas");
        this.title = title;
        this.description = descriptionText;
        this.id = id;
        this.cardType = cardType;
        this.cost = cost;
        this.rarity = rarity;
        this.textureRegion = game.textureAtlas.findRegion(cardType.name().toLowerCase(Locale.ROOT) + "-" + rarity);
        this.cardImage = new Image(new TextureRegionDrawable(textureRegion));
        super.setBackground(cardImage.getDrawable());
        this.setClip(true);
        this.align(1);
        this.setOrigin(Align.center);
        //super.setAlign(1);
        //super.setScaling(Scaling.fill);
        this.upgraded = false;
        this.preview = false;
        generateTextTable();
        super.setSize(textureRegion.getRegionWidth(), textureRegion.getRegionHeight());
        this.exhaust = exhaust;
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
        this.inspect = !deckDisplay;
        //deckscreen card

        this.deckDisplay = deckDisplay;
        this.id = card.getId();
        this.title = card.getTitle();
        this.description = card.getDescription();
        //this.textTable = card.getTextTable();
        this.cardType = card.getCardType();
        this.cost = card.getCost();
        this.textureRegion = card.getTextureRegion();
        this.cardImage = new Image(new TextureRegionDrawable(textureRegion));
        super.setBackground(cardImage.getDrawable());
        this.setClip(true);
        this.align(1);
        this.setOrigin(Align.center);
        //super.setAlign(1);
        //super.setScaling(Scaling.fill);
        this.upgraded = card.isUpgraded();
        this.preview = card.isPreview();
        generateTextTable();
        super.setSize(textureRegion.getRegionWidth(), textureRegion.getRegionHeight());
        this.exhaust = card.isExhaust();
        super.setName(title);
        this.setWidth(textureRegion.getRegionWidth());
        this.setHeight(textureRegion.getRegionHeight());
        this.setScale(super.getScaleX(), super.getScaleY());
        this.timeAdded = card.getTimeAdded();
        this.containingTable = null;
        //this.titleGroup = card.getTitleGroup();
        //this.descriptionGroup = card.getDescriptionGroup();

        initCard();

    }

    public void generateTextTable() {
        clearChildren();
        game.font.getData().markupEnabled = true;
        String upgradeDescription = description.toUpperCase(Locale.ROOT);
        String colorlessDescription = upgradeDescription;
        int displayCost = cost;
        if (upgraded || preview) {
            Json json = new Json();
            json.addClassTag("cardUpgrade", CardUpgrade.class);
            FileHandle file = new FileHandle("cards/cardUpgrades.json");
            ArrayList<CardUpgrade> cardUpgradeArrayList = json.fromJson(ArrayList.class, CardUpgrade.class, file);
            for (CardUpgrade upgrade : cardUpgradeArrayList) {
                if (title.equalsIgnoreCase(upgrade.getName())) {
                    displayCost = upgrade.getCost();
                    upgradeDescription = upgrade.getDescription();
                    colorlessDescription = upgrade.getDescriptionColorless();
                }
            }

        }



        GlyphLayout glyphord = new GlyphLayout();
        Label titleLabel = new Label((!(upgraded || preview)) ? title.toUpperCase(Locale.ROOT) : (title + "+").toUpperCase(Locale.ROOT),new LabelStyle(game.font, Color.WHITE));
        Group titleGroup = new Group();
        titleGroup.addActor(titleLabel);
        float scaleBy = (deckDisplay) ? 0.5F : (!inspect) ? 0.7F : 1F;
        titleGroup.addAction(Actions.scaleBy(scaleBy, scaleBy));
        align(Align.top);
        float spaceTop = (deckDisplay) ? 120 : (!inspect) ? 150 : 170;
        //add().space(spaceTop).row();
        add(titleGroup).size(titleLabel.getWidth()*scaleBy* ((deckDisplay) ? 3F : (!inspect) ? 2.5F : 2F) , titleLabel.getHeight()*scaleBy*1.5F);
        String descriptionText = upgradeDescription;
        String[] descWords = upgradeDescription.replaceAll("[.]", "").split("\\s");
        for (String descWord : descWords) {
            for (InfoboxTypes.CardInfoType cardInfoType : InfoboxTypes.CardInfoType.values()) {
                if (cardInfoType.name().equals(descWord)) {
                    descriptionText = descriptionText.replace(descWord, "[#f9a720]" + descWord.replace("_", " ") + "[]");
                }
            }
        }
        add().padTop((deckDisplay) ? 250 : (!inspect) ? 270 : 290).row();
        String[] descriptionLinesColor = descriptionText.split("\\R");
        String[] descriptionLines = colorlessDescription.split("\\R");
        for (int i = 0; i < descriptionLinesColor.length; i++) {
            Label descriptionLabel = new Label(descriptionLinesColor[i], new LabelStyle(game.font, Color.WHITE));
            Group descriptionGroup = new Group();
            descriptionGroup.addActor(descriptionLabel);
            scaleBy = (deckDisplay) ? 0.15F : (!inspect) ? 0.2F : 0.25F;
            descriptionGroup.addAction(Actions.scaleBy(scaleBy, scaleBy));


            glyphord.setText(game.font, descriptionLines[i]);

            if (i == 0) {
                add(descriptionGroup).size(glyphord.width * (1 + scaleBy), glyphord.height * (1 + scaleBy)).padTop((deckDisplay ? 130 : (!inspect) ? 170 : 200));
            } else {
                add(descriptionGroup).size(glyphord.width * (1 + scaleBy), glyphord.height * (1 + scaleBy)).padTop(10);
            }


            row();
        }

        Label costLabel = new Label(String.valueOf(displayCost), new LabelStyle(game.font, Color.WHITE));
        Group costGroup = new Group();
        costGroup.addActor(costLabel);
        scaleBy = (deckDisplay) ? 0.5F : (!inspect) ? 0.7F : 1F;
        costGroup.addAction(Actions.scaleBy(scaleBy, scaleBy));
        //add(costGroup).size(costLabel.getWidth(), costLabel.getHeight()).bottom().left().align(Align.bottomLeft).padTop(((deckDisplay) ? 160 : 180)-glyphord.height);
        addActor(costGroup);
        costGroup.moveBy((deckDisplay) ? 95 : (!inspect) ? 110 : 120, (deckDisplay) ? 115 : (!inspect) ? 130 : 150);
        pack();
    }


    //initializes card boundaries + adds listeners
    public void initCard() {
        upgraded = false;
        setTouchable(Touchable.enabled);
        //this.debug();
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
                    if (pointer == -1) {
                        ScaleByAction sba = new ScaleByAction();
                        sba.setAmount(0.1F);
                        sba.setDuration(.2F);
                        setOrigin(Align.center);
                        //setAlign(Align.center);
                        Card.this.addAction(sba);

                    /*
                    titleGroup.setOrigin(Align.center);
                    descriptionGroup.setOrigin(Align.center);
                    titleGroup.addAction(Actions.sequence(Actions.scaleBy(sba.getAmountX(), sba.getAmountY(), .2F)));
                    descriptionGroup.addAction(Actions.sequence(Actions.scaleBy(sba.getAmountX(), sba.getAmountY(), .2F)));

                     */
                        exited = false;

                    }
                }

                @Override
                //called when mouse stops hovering over card
                public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                    super.exit(event, x, y, pointer, toActor);
                    if (pointer == -1) {
                        ScaleByAction sba = new ScaleByAction();
                        sba.setAmount(-0.1F);
                        sba.setDuration(.2F);
                        setOrigin(Align.center);
                        //setAlign(Align.center);
                    /*
                    titleGroup.addAction(Actions.sequence(Actions.scaleBy(sba.getAmountX(), sba.getAmountY(), .2F)));
                    descriptionGroup.addAction(Actions.sequence(Actions.scaleBy(sba.getAmountX(), sba.getAmountY(),.2F)));
                     */
                        Card.this.addAction(sba);


                        exited = true;
                    }
                }
            });
        } else {
            final Card currentCard = this;
            this.addListener(new ClickListener() {
                @Override
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    super.enter(event, x, y, pointer, fromActor);
                    if (containingTable!=null && containingTable.getName().equals("handTable")) {
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
                    if (containingTable!=null && containingTable.getName().equals("handTable")) {
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

    public void previewUpgrade(boolean preview) {
        this.preview = preview;
        generateTextTable();
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


    public String getRarity() {
        return rarity;
    }

    public void setRarity(String rarity) {
        this.rarity = rarity;
    }

    public boolean isUpgraded() {
        return upgraded;
    }

    public void setUpgraded(boolean upgraded) {
        this.upgraded = upgraded;
        if (upgraded) {
            generateTextTable();
        }
    }

    public boolean isPreview() {
        return preview;
    }


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

}
