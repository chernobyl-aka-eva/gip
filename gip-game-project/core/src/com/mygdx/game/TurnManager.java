package com.mygdx.game;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.cards.CardManager;

public class TurnManager {

    private GipGameProject game;
    private Stage stage;
    private Group group;
    private CardManager cardManager;

    public TurnManager(GipGameProject game, Stage stage, Group group) {
        this.game = game;
        this.stage = stage;
        this.group = group;
        cardManager = new CardManager(game, stage, group);

        // starting deck
        cardManager.addCard(0);
        cardManager.addCard(0);
        cardManager.addCard(0);
        cardManager.addCard(0);
        cardManager.addCard(1);
        cardManager.addCard(1);
        cardManager.addCard(1);
        cardManager.addCard(1);
        cardManager.addCard(0);
        cardManager.addCard(0);
        cardManager.addCard(0);
        cardManager.addCard(0);
        cardManager.addCard(1);
        cardManager.addCard(1);
        cardManager.addCard(1);
        cardManager.addCard(1);



        cardManager.getMonsterManager().addMonster(0);
        cardManager.drawcard(10);

    }


    public void drawVirus(){
        cardManager.getVirusManager().drawVirus();
    }

    public void drawMonster(){
        cardManager.getMonsterManager().drawMonster();
    }

    public CardManager getCardManager() {
        return cardManager;
    }

    public void setCardManager(CardManager cardManager) {
        this.cardManager = cardManager;
    }
}
