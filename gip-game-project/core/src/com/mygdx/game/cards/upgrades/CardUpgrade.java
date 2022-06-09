package com.mygdx.game.cards.upgrades;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CardUpgrade implements Json.Serializable{
    private String name;
    private String description;
    private String descriptionColorless;
    private int cost;

    public CardUpgrade(String name, String description, int cost) {
        this.name = name;
        this.description = description.toUpperCase(Locale.ROOT);
        this.descriptionColorless = this.description;
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
        descriptionColorless = description;

        Pattern pattern = Pattern.compile("\\[(.*?)\\]");
        Matcher matcher = pattern.matcher(this.description);

        if( matcher.find() ) {
            this.descriptionColorless = description.replaceAll("\\[.*?\\]", matcher.group(1));;
            this.description = this.description.replaceAll("\\[.*?\\]", "[#368930]" + matcher.group(1) + "[]");
            System.out.println(this.name);
            System.out.println(this.descriptionColorless);
        }
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

    public String getDescriptionColorless() {
        return descriptionColorless;
    }

    public void setDescriptionColorless(String descriptionColorless) {
        this.descriptionColorless = descriptionColorless;
    }
}
