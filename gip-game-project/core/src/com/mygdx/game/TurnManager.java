package com.mygdx.game;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.cards.CardManager;

public class TurnManager {

    private GipGameProject game;
    private Stage stage;
    private CardManager cardManager;

    public TurnManager(GipGameProject game, Stage stage) {
        this.game = game;
        this.stage = stage;
        cardManager = new CardManager(game, stage);

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
