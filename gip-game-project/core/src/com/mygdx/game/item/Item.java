package com.mygdx.game.item;

public class Item {
    private String relicName;
    private String rarity;
    private String description;

    public Item(String relicName, String rarity, String description) {
        this.relicName = relicName;
        this.rarity = rarity;
        this.description = description;
    }

    public String getRelicName() {
        return relicName;
    }

    public void setRelicName(String relicName) {
        this.relicName = relicName;
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
}
