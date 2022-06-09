package com.mygdx.game.item;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.ScaleByAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.infobox.InfoBoxManager;

public class Item extends Image {
    private int id;
    private String itemName;
    private String rarity;
    private String description;
    private TextureRegion textureRegion;
    private Group virusGroup;
    private InfoBoxManager infoBoxManager;
    private final Table infoBoxTable;


    public Item(int id, String itemName, String rarity, String description, TextureRegion textureRegion, Group virusGroup, InfoBoxManager infoBoxManager) {
        super(textureRegion);
        this.id = id;
        this.itemName = itemName;
        this.rarity = rarity;
        this.description = description;
        this.textureRegion = textureRegion;
        this.virusGroup = virusGroup;
        this.infoBoxManager = infoBoxManager;

        this.setOrigin(Align.center);
        this.addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                super.enter(event, x, y, pointer, fromActor);
                if (pointer == -1) {
                    ScaleByAction sba = new ScaleByAction();
                    sba.setDuration(0.1F);
                    sba.setAmount(0.2F);
                    Item.this.addAction(sba);
                }
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                super.exit(event, x, y, pointer, toActor);
                if (pointer == -1) {
                    ScaleByAction sba = new ScaleByAction();
                    sba.setDuration(0.3F);
                    sba.setAmount(-0.2F);
                    Item.this.addAction(sba);
                }
            }
        });
        infoBoxTable = infoBoxManager.getInfoBoxTable(this);
        infoBoxTable.setPosition(0,0);
        virusGroup.addActor(infoBoxTable);
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if (infoBoxTable.isVisible()) {
            infoBoxTable.setPosition(Gdx.input.getX() + 20, Gdx.graphics.getHeight()-Gdx.input.getY()-infoBoxTable.getHeight()-10);
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getRarity() {
        return rarity;
    }

    public void setRarity(String rarity) {
        this.rarity = rarity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TextureRegion getTextureRegion() {
        return textureRegion;
    }

    public void setTextureRegion(TextureRegion textureRegion) {
        this.textureRegion = textureRegion;
    }
}
