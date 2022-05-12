package com.mygdx.game.cards.upgrades;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;

import java.util.ArrayList;

public class GenerateCardUpgrades {

    public GenerateCardUpgrades() {
        generateUpgrades();
    }

    public void generateUpgrades() {
        ArrayList<CardUpgrade> cardUpgrades = new ArrayList<>();
        cardUpgrades.add(new CardUpgrade("Strike", " Deal [9] Damage ", 1));
        cardUpgrades.add(new CardUpgrade("Defend", " Gain [8] Block ", 1));
        cardUpgrades.add(new CardUpgrade("Replicate", " Deal [6] Damage.\n ENCODE. \n add a copy \n of this card in the \n DISCARD_PILE. ", 0));
        cardUpgrades.add(new CardUpgrade("Go To", " Draw 1 card. \n ENCODE. \n COMPILE - Draw 1 card. ", 0));
        cardUpgrades.add(new CardUpgrade("Piercing Shot", " Deal [12] damage to \n all enemies. \n EXHAUST. ", 0));
        cardUpgrades.add(new CardUpgrade("Fine Tuning", " Gain [2] DEXTERITY. \n ENCODE. \n COMPILE - gain [2] \n DEXTERITY. \n \n EXHAUST. ", 2));
        cardUpgrades.add(new CardUpgrade("Test Card", " This is an \n [upgraded] test Card \n EXHAUST. ", 5));


        Json json = new Json();
        json.addClassTag("cardUpgrade", CardUpgrade.class);
        String cardJSON = json.prettyPrint(cardUpgrades);
        FileHandle file = new FileHandle("cards/cardUpgrades.json");
        file.writeString(cardJSON, false);

        /*
        ArrayList<CardUpgrade> cardUpgradeArrayList = json.fromJson(ArrayList.class, CardUpgrade.class, file);
        for (CardUpgrade cardUpgrade : cardUpgradeArrayList) {
            System.out.println(cardUpgrade.getName() + ": " + cardUpgrade.getDescription() + ": " + cardUpgrade.getCost());
        }
         */
    }
}
