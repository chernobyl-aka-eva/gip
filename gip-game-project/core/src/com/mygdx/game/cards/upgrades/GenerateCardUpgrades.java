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
        cardUpgrades.add(new CardUpgrade("Bug Barrage", "Gain [2] WOUNDS.\nCYCLE each STATUS,\ndealing 7 Damage\nfor each.", 1));
        cardUpgrades.add(new CardUpgrade("Oil Spill", "Deal 20 Damage.\nENCODE.\nCOMPILE error -\nINSERT a SLIMED\nEXHAUST.", 2));
        cardUpgrades.add(new CardUpgrade("Wild Strike", "Deal [17] Damage.\nINSERT a WOUND.", 1));
        cardUpgrades.add(new CardUpgrade("Sticky Shield", "Gain [14] Block.\nINSERT 1 SLIMED.", 1));
        cardUpgrades.add(new CardUpgrade("Buggy Mess", "INSERT a DAZED.\nGain 1 energy.\nENCODE.", 0));
        cardUpgrades.add(new CardUpgrade("Frontload", "Gain [11] BLOCK.\nENCODE.\nCOMPILE - Function\ngains RETAIN.", 2));
        cardUpgrades.add(new CardUpgrade("Fragment", "Deal [6] Damage.\nGain [6] Block.\nENCODE.", 1));
        cardUpgrades.add(new CardUpgrade("Mutator", "[Retain].\nGain 1 STRENGTH.\nTransform a STATUS\ninto a copy of this.", 1)); // !
        cardUpgrades.add(new CardUpgrade("Reboot", "Gain 2 Energy.", 0));
        cardUpgrades.add(new CardUpgrade("Iterate", "Deal 2 Damage [5]\ntimes.\nENCODE.", 1));
        cardUpgrades.add(new CardUpgrade("Boost", "Gain 6 BLOCK.\nENCODE.\nCOMPILE - \nGain [3] STRENGTH.", 2));
        cardUpgrades.add(new CardUpgrade("Auto-Shields", "If you have no BLOCK,\ngain [15] BLOCK.", 1));
        cardUpgrades.add(new CardUpgrade("Double Energy", "Double your energy.\nEXHAUST.", 0));

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
