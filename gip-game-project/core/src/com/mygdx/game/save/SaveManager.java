package com.mygdx.game.save;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.mygdx.game.SessionManager;
import com.mygdx.game.cards.Card;
import com.mygdx.game.cards.CardType;
import com.mygdx.game.item.Item;
import com.mygdx.game.map.MapEvent;
import com.mygdx.game.map.MapEventType;

import java.util.ArrayList;

public class SaveManager {
    private final SessionManager sessionManager;
    private SavedState savedState;

    public SaveManager(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
        this.savedState = load();
    }

    public void save() {
        int health = sessionManager.getCardManager().getVirusManager().getPlayer().getHealth();

        int block = sessionManager.getCardManager().getVirusManager().getPlayer().getBlock();

        int money = sessionManager.getCardManager().getVirusManager().getPlayer().getMoney();

        ArrayList<SavedCard> playerCards = savedCards(sessionManager.getCardManager().getPlayerCards());
        ArrayList<SavedCard> drawCards = savedCards(sessionManager.getCardManager().getDrawPile());
        ArrayList<SavedCard> handCards = savedCards(sessionManager.getCardManager().getHand());
        ArrayList<SavedCard> discardedCards = savedCards(sessionManager.getCardManager().getDiscardPile());
        ArrayList<SavedCard> exhaustedCards = savedCards(sessionManager.getCardManager().getExhaustPile());



        ArrayList<Integer> savedItemIndexes = new ArrayList<>();
        for (Item item : sessionManager.getCardManager().getVirusManager().getPlayer().getItemManager().getPlayerItems()) {
                savedItemIndexes.add(item.getId());
        }

        SavedMap savedMap = savedMap();


        savedState = new SavedState(health, block, money, playerCards, drawCards, handCards, discardedCards, exhaustedCards, savedItemIndexes, savedMap);

        Json json = new Json();
        json.setElementType(SavedState.class, "savedCards", SavedCard.class);
        String saveJson = json.prettyPrint(savedState);

        FileHandle file = new FileHandle("saves/savedState.json");
        file.writeString(saveJson, false);


    }

    private SavedMap savedMap() {
        ArrayList<MapEventType> mapEventTypes = new ArrayList<>();
        ArrayList<Integer> mapEventIds = new ArrayList<>();
        int currentEventId = 0;
        Array<MapEvent> mapEvents = sessionManager.getEventManager().getMap().getMapBackground().getMapEvents();
        for (MapEvent mapEvent : mapEvents) {
            mapEventTypes.add(mapEvent.getMapEventType());
            mapEventIds.add(mapEvent.getId());
            if (mapEvent.isCurrentEvent()) {
                currentEventId = mapEvent.getOrder();
            }
        }
        return new SavedMap(mapEventTypes, mapEventIds, currentEventId);
    }

    private ArrayList<SavedCard> savedCards(Array<Card> cards) {
        ArrayList<SavedCard> savedCards = new ArrayList<>();
        for (Card playerCard : cards) {
            int cardId = playerCard.getId();
            String title = playerCard.getTitle();
            String description = playerCard.getDescription();
            CardType cardType = playerCard.getCardType();
            String rarity = playerCard.getRarity();
            int cost = playerCard.getCost();
            boolean upgraded = playerCard.isUpgraded();
            boolean exhaust = playerCard.isExhaust();
            boolean isUnplayable = playerCard.isUnplayable();
            float elapsed_time = playerCard.getTimeAdded();

            SavedCard savedCard = new SavedCard(cardId, title, description, cardType, rarity, cost, upgraded, exhaust, isUnplayable, elapsed_time);
            savedCards.add(savedCard);
        }
        return savedCards;
    }

    public SavedState load() {
        Json json = new Json();
        json.setElementType(SavedState.class, "savedCards", SavedCard.class);
        FileHandle file = Gdx.files.local("saves/savedState.json");
        SavedState loadedState = null;
        try {
            if (Gdx.files.local("saves/savedState.json").exists()) {
                loadedState = json.fromJson(SavedState.class, file.readString());
                return loadedState;
            } else {
                return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void abandon() {
        Gdx.files.local("saves/savedState.json").delete();
    }

    public static boolean exists() {
        return Gdx.files.local("saves/savedState.json").exists();
    }


    public SavedState getSavedState() {
        return savedState;
    }

    public void setSavedState(SavedState savedState) {
        this.savedState = savedState;
    }
}
