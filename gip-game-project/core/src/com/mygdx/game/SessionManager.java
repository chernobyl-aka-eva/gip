package com.mygdx.game;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.cards.CardManager;

public class SessionManager {
    private GipGameProject game;
    private Stage stage;
    private Group gameScreenGroup;
    //private DungeonManager dungeonManager;
    private TurnManager turnManager;
    private final CardManager cardManager;
    private final Button endTurn;

    public SessionManager(GipGameProject game, Stage stage, Group group) {
        this.game = game;
        this.stage = stage;
        this.gameScreenGroup = group;

        cardManager = new CardManager(game, stage, group);

        cardManager.getMonsterManager().addMonster(0);


        Array<Integer> startingDeck = cardManager.getVirusManager().getPlayer().getStartingDeck();
        endTurn = new Button(game.skin, "end-turn");
        endTurn.setPosition(stage.getWidth()-50-endTurn.getWidth(), 150);
        group.addActor(endTurn);

        for (int i = 0; i < startingDeck.size; i++) {
            //turnManager.getCardManager().addCard(startingDeck.get(i));
            cardManager.addCard(startingDeck.get(i));
            game.log.debug("added card id: " + startingDeck.get(i));
            game.log.debug("drawpile size: " + cardManager.getDrawPile().size);
        }

        cardManager.drawcard(cardManager.getVirusManager().getPlayer().getAmountToDraw());
        cardManager.refreshDisplayTable();



        turnManager = new TurnManager(game, stage, group, endTurn, cardManager);
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

    public Group getGameScreenGroup() {
        return gameScreenGroup;
    }

    public void setGameScreenGroup(Group gameScreenGroup) {
        this.gameScreenGroup = gameScreenGroup;
    }

    public TurnManager getTurnManager() {
        return turnManager;
    }

    public void setTurnManager(TurnManager turnManager) {
        this.turnManager = turnManager;
    }
}
