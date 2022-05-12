package com.mygdx.game.save;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.mygdx.game.SessionManager;
import com.mygdx.game.cards.Card;
import com.mygdx.game.cards.CardType;
import com.mygdx.game.cards.SavedCard;
import com.mygdx.game.item.Item;
import com.mygdx.game.map.MapEvent;

import java.util.ArrayList;

public class SaveManager {
    private SessionManager sessionManager;
    private SavedState savedState = null;

    public SaveManager(SessionManager sessionManager) {
        Preferences prefs = Gdx.app.getPreferences("Save");
        this.sessionManager = sessionManager;
    }

    public void save() {
        int health = sessionManager.getCardManager().getVirusManager().getPlayer().getHealth();

        int money = sessionManager.getCardManager().getVirusManager().getPlayer().getMoney();

        ArrayList<SavedCard> savedCards = new ArrayList<>();
        for (Card playerCard : sessionManager.getCardManager().getPlayerCards()) {
            int cardId = playerCard.getId();
            String title = playerCard.getTitle();
            String description = playerCard.getDescription();
            CardType cardType = playerCard.getCardType();
            String rarity = playerCard.getRarity();
            int cost = playerCard.getCost();
            boolean upgraded = playerCard.isUpgraded();
            float elapsed_time = playerCard.getTimeAdded();

            SavedCard savedCard = new SavedCard(cardId, title, description, cardType, rarity, cost, upgraded, elapsed_time);
            savedCards.add(savedCard);
        }

        ArrayList<Integer> savedItemIndexes = new ArrayList<>();
        for (Item item : sessionManager.getCardManager().getVirusManager().getPlayer().getItemManager().getPlayerItems()) {
                savedItemIndexes.add(item.getId());

        }

        ArrayList<Integer> mapEventOrder = new ArrayList<>();
        int currentEventId = 0;
        for (MapEvent mapEvent : sessionManager.getMap().getMapBackground().getMapEvents()) {
            mapEventOrder.add(mapEvent.getId());
            if (mapEvent.isCurrentEvent()) {
                currentEventId = mapEvent.getOrder();
            }
        }

        savedState = new SavedState(health, money, savedCards, savedItemIndexes, mapEventOrder, currentEventId);

        Json json = new Json();
        json.setElementType(SavedState.class, "savedCards", SavedCard.class);
        String saveJson = json.prettyPrint(savedState);
        FileHandle file = new FileHandle("saves/savedState.json");
        file.writeString(saveJson, false);

    }

    public void load() {
        Json json = new Json();
        json.setElementType(SavedState.class, "savedCards", SavedCard.class);
        FileHandle file = new FileHandle("saves/savedState.json");

        SavedState loadedState = json.fromJson(SavedState.class, file.readString());
        System.out.println();
    }
}
