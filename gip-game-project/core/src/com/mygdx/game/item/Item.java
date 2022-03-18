package com.mygdx.game.item;

public class Item {
    private String itemName;
    private String rarity;
    private String description;

    public Item(String relicName, String rarity, String description) {
        this.itemName = relicName;
        this.rarity = rarity;
        this.description = description;
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
}
