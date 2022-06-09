package com.mygdx.game;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.cards.CardManager;
import com.mygdx.game.map.EventManager;
import com.mygdx.game.map.Map;
import com.mygdx.game.save.SaveManager;
import com.mygdx.game.screen.GameScreen;

public class SessionManager {
    private GipGameProject game;
    private Stage stage;
    private Group gameScreenGroup;
    private SaveManager saveManager;
    private EventManager eventManager;
    //private DungeonManager dungeonManager;
    private TurnManager turnManager;
    private GameScreen gameScreen;
    private Map map;
    private final CardManager cardManager;


    public SessionManager(GipGameProject game, Stage stage, Group foreGround, GameScreen gameScreen) {
        this.game = game;
        this.stage = stage;
        this.gameScreen = gameScreen;
        this.gameScreenGroup = gameScreen.getGameScreenGroup();
        this.saveManager = new SaveManager(this);


        cardManager = new CardManager(game, stage, gameScreenGroup, foreGround, this);

        cardManager.getMonsterManager().addMonster(0);

        eventManager = new EventManager(saveManager.getSavedState(), gameScreen, cardManager, stage, game);

        Array<Integer> startingDeck = cardManager.getVirusManager().getPlayer().getStartingDeck();


        if (saveManager.getSavedState() == null) {
            for (int i = 0; i < startingDeck.size; i++) {
                //turnManager.getCardManager().addCard(startingDeck.get(i));
                cardManager.addCard(startingDeck.get(i));
                game.log.debug("added card id: " + startingDeck.get(i));
                game.log.debug("drawpile size: " + cardManager.getDrawPile().size);
            }
            cardManager.drawcard(cardManager.getVirusManager().getPlayer().getAmountToDraw());
        } else {
            cardManager.loadCards();
        }





    }

    public void dispose() {
        cardManager.dispose();
        eventManager.dispose();
    }

    public void eventEnded() {
        eventManager.eventEnded();
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

    public SaveManager getSaveManager() {
        return saveManager;
    }

    public void setSaveManager(SaveManager saveManager) {
        this.saveManager = saveManager;
    }

    public GameScreen getGameScreen() {
        return gameScreen;
    }

    public void setGameScreen(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public CardManager getCardManager() {
        return cardManager;
    }

    public Group getGameScreenGroup() {
        return gameScreenGroup;
    }

    public void setGameScreenGroup(Group gameScreenGroup) {
        this.gameScreenGroup = gameScreenGroup;
    }

    public EventManager getEventManager() {
        return eventManager;
    }

    public void setEventManager(EventManager eventManager) {
        this.eventManager = eventManager;
    }

    public TurnManager getTurnManager() {
        return turnManager;
    }

    public void setTurnManager(TurnManager turnManager) {
        this.turnManager = turnManager;
    }
}
