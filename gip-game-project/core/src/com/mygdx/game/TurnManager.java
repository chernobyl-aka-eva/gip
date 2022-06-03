package com.mygdx.game;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.animations.TurnAnimation;
import com.mygdx.game.cards.Card;
import com.mygdx.game.cards.CardManager;
import com.mygdx.game.monster.Monster;
import com.mygdx.game.monster.MonsterIntent;
import com.mygdx.game.save.SavedState;
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
    private SavedState savedState;
    private final MonsterIntent monsterIntent;
    private Button endTurn;
    private TurnAnimation turnAnimation;




    public TurnManager(final GipGameProject game, Stage stage, Group group, final Button endTurn, final CardManager cardManager, SavedState savedState) {
        this.game = game;
        this.stage = stage;
        this.group = group;
        this.endTurn = endTurn;
        this.cardManager = cardManager;
        this.savedState = savedState;
        monsterIntent = new MonsterIntent(game, cardManager);
        turnAnimation = new TurnAnimation(game, stage, this);

        if (savedState == null) {
            playerTurnStart();
        }
        for (int i = 0; i < 4; i++) {
            cardManager.refreshDisplayTable(i);
        }

        endTurn.addListener(new ClickListener() { // listens for button press
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                endTurn.setVisible(false); // set end turn button invisible
                for (int c = 0; c < cardManager.getHand().size; c++){
                    if (cardManager.getHand().get(c).isEthereal()){
                        cardManager.getExhaustPile().add(cardManager.getHand().get(c));
                        cardManager.getHand().removeIndex(c);
                        cardManager.refreshDisplayTable(3);
                        cardManager.getHand().refreshHand();
                    }
                }
                int burnCounter = 0;
                for (int c = 0; c < cardManager.getHand().size; c++) {
                    if (cardManager.getHand().get(c).getId() == 12) {
                        burnCounter++;
                    }
                }
                if (burnCounter > 0) {
                    int damage;
                    if (cardManager.getVirusManager().getPlayer().getBlock() > 0) { // if the player has block
                        //game.log.debug("block before damage : " + cardManager.getVirusManager().getPlayer().getBlock());
                        if (cardManager.getVirusManager().getPlayer().getBlock() >= 2) { // if the player can block fully
                            damage = 2;
                            cardManager.getVirusManager().getPlayer().setBlock(cardManager.getVirusManager().getPlayer().getBlock()-damage);
                            //game.log.debug("damage : " + damage);
                            //game.log.debug("block after damage : " + cardManager.getVirusManager().getPlayer().getBlock());
                        } else { // else if the player can't block fully but can block some...
                            int damageAfterBlock;
                            damage = 2 - cardManager.getVirusManager().getPlayer().getBlock();
                            //game.log.debug("damage : " + damage);
                            cardManager.getVirusManager().getPlayer().setBlock(cardManager.getVirusManager().getPlayer().getBlock()-damage);
                            damageAfterBlock = 2 - damage; // damage done after breaking block
                            //game.log.debug("damage after block : " + damageAfterBlock);
                            //game.log.debug("health before damage : " + cardManager.getVirusManager().getPlayer().getHealth());
                            cardManager.getVirusManager().getPlayer().setHealth(cardManager.getVirusManager().getPlayer().getHealth() - damageAfterBlock);
                            //game.log.debug("health after damage : " + cardManager.getVirusManager().getPlayer().getHealth());
                        }
                    } else { // if the player doesn't have block
                        //game.log.debug("health before damage : " + cardManager.getVirusManager().getPlayer().getHealth());
                        cardManager.getVirusManager().getPlayer().setHealth(cardManager.getVirusManager().getPlayer().getHealth()-2);
                        //game.log.debug("health after damage : " + cardManager.getVirusManager().getPlayer().getHealth());
                    }

                    if (cardManager.getVirusManager().getPlayer().getHealth() <= 0) {
                        game.setScreen(new DeathScreen(game));
                    }
                }

                emptyHand(); // empty hand
                playerTurnEnd(); // end turn
                monsterTurnStart(); // begin monster turn
                return super.touchDown(event, x, y, pointer, button);
            }
        });


    }

    public void emptyHand(){
        Array<Card> cards = new Array<>();

        for (int i = 0; i < cardManager.getHand().size; i++) {
            if (!cardManager.getHand().get(i).isRetain()) {
                cardManager.getDiscardPile().add(cardManager.getHand().get(i));
                cardManager.getHandTable().removeActor(cardManager.getHand().get(i));
            } else {
                cards.add(cardManager.getHand().get(i));
            }
        }

        cardManager.getHand().clear();
        cardManager.getHandTable().clearChildren();

        for (Card card : cards) {
            cardManager.getHand().add(card);
        }

        cardManager.refreshDisplayTable(2);

        cardManager.getHand().refreshHand();
    }

    public void monsterTurnStart() {
        monsterTurn = true;
        // set block
        //turnAnimation.startAnimation(1);


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
        turnCounter++;
        playerTurn = true;
        //turnAnimation.startAnimation(1, turnCounter);

        game.log.debug("TURN " + turnCounter + "\n==============");
        endTurn.setVisible(true); // sets end turn button visible
        // draw cards
        if (turnCounter != 1 || savedState != null){
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
            //game.log.debug("(intent code) :" + cardManager.getMonsterManager().getMonsterIntents().get(0));
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
