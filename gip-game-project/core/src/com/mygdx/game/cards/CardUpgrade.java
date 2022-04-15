package com.mygdx.game.cards;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

public class CardUpgrade implements Json.Serializable{
    private String name;
    private String description;
    private int cost;

    public CardUpgrade(String name, String description, int cost) {
        this.name = name;
        this.description = description;
        this.cost = cost;
    }

    public CardUpgrade() {
    }

    @Override
    public void write(Json json) {
        json.writeValue(name, description);
        json.writeValue("cost", cost);
    }

    @Override
    public void read(Json json, JsonValue jsonData) {
        name = jsonData.get(1).name();
        description = jsonData.get(1).asString();
        cost = jsonData.get(2).asInt();
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

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

}
