package com.mygdx.game;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.cards.CardManager;
import com.mygdx.game.monster.Monster;
import com.mygdx.game.monster.MonsterIntent;
import com.mygdx.game.screen.DeathScreen;

public class TurnManager {


    private int turnCounter = 0;
    private boolean playerTurn = false;
    private boolean monsterTurn = false;
    private int energy;

    private GipGameProject game;
    private Stage stage;
    private Group group;
    private CardManager cardManager;
    private final MonsterIntent monsterIntent;
    private Button endTurn;





    public TurnManager(GipGameProject game, Stage stage, Group group, final Button endTurn, CardManager cardManager) {
        this.game = game;
        this.stage = stage;
        this.group = group;
        this.endTurn = endTurn;
        this.cardManager = cardManager;
        monsterIntent = new MonsterIntent(game, cardManager);
        playerTurnStart();
        cardManager.refreshDisplayTable();

        endTurn.addListener(new ClickListener() { // listens for button press
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                endTurn.setVisible(false); // set end turn button invisible
                emptyHand(); // empty hand
                playerTurnEnd(); // end turn
                monsterTurnStart(); // begin monster turn
                return super.touchDown(event, x, y, pointer, button);
            }
        });

        /*
        for (Card playerCard : cardManager.getPlayerCards()) {
            cardManager.getDrawPile().add(playerCard);
        }

         */
    }

    public void emptyHand(){
        for (int i = 0; i < cardManager.getHand().size; i++) {
            cardManager.getDiscardPile().add(cardManager.getHand().get(i));
            cardManager.getHandTable().removeActor(cardManager.getHand().get(i));

        }
        cardManager.refreshDisplayTable();
        cardManager.getHand().clear();
        cardManager.getHandTable().clear();
        cardManager.getHand().refreshHand();
    }

    public void monsterTurnStart() {
        monsterTurn = true;
        // set block
        for (Actor actor : cardManager.getMonsterManager().getMonsterGroup().getChildren()) {
            if (actor instanceof Monster) {
                Monster monster = (Monster) actor;
                monster.setblock(0);
            }
        }
        monsterIntent.monsterTurn(cardManager.getMonsterManager().getMonsterIntents());
        cardManager.getMonsterManager().setIntentVisible(false);
        if (cardManager.getVirusManager().getPlayer().getHealth() <= 0) {
            game.setScreen(new DeathScreen(game));
        }
        monsterTurnEnd();
    }

    public void monsterTurnEnd() {
        monsterTurn = false;
        playerTurnStart();
    }

    public void playerTurnStart() {
        // ♦ is listening to end turn button press ♦
        playerTurn = true;
        turnCounter++;
        game.log.debug("TURN " + turnCounter + "\n==============");
        endTurn.setVisible(true); // sets end turn button visible
        // draw cards
        if (turnCounter != 1){
            cardManager.drawcard(cardManager.getVirusManager().getPlayer().getAmountToDraw());
        }
        // set energy
        cardManager.getVirusManager().getPlayer().getEnergyManager().setEnergy(cardManager.getVirusManager().getPlayer().getEnergy());
        // set block
        cardManager.getVirusManager().getPlayer().setBlock(0);
        // get monster intents
        cardManager.getMonsterManager().intent();
        // refresh hand
        cardManager.getHandTable().invalidate(); cardManager.getHandTable().validate();



        game.log.debug("drawpile size: " + cardManager.getDrawPile().size);

        game.log.debug("(amount of intents) : " + cardManager.getMonsterManager().getMonsterIntents().size);
        for (int i = 0; i < cardManager.getMonsterManager().getMonsterIntents().size; i++){
            game.log.debug("(intent code) :" + cardManager.getMonsterManager().getMonsterIntents().get(0));
        }


    }

    public void playerTurnEnd() {
        playerTurn = false;
    }

    public int getTurnCounter() {
        return turnCounter;
    }

    public void setTurnCounter(int turnCounter) {
        this.turnCounter = turnCounter;
    }

    public boolean isPlayerTurn() {
        return playerTurn;
    }

    public void setPlayerTurn(boolean playerTurn) {
        this.playerTurn = playerTurn;
    }

    public boolean isMonsterTurn() {
        return monsterTurn;
    }

    public void setMonsterTurn(boolean monsterTurn) {
        this.monsterTurn = monsterTurn;
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public GipGameProject getGame() {
        return game;
    }

    public void setGame(GipGameProject game) {
        this.game = game;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public CardManager getCardManager() {
        return cardManager;
    }

    public void setCardManager(CardManager cardManager) {
        this.cardManager = cardManager;
    }

    public Button getEndTurn() {
        return endTurn;
    }

    public void setEndTurn(Button endTurn) {
        this.endTurn = endTurn;
    }
}
