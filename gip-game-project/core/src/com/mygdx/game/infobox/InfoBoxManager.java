package com.mygdx.game.infobox;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.GipGameProject;
import com.mygdx.game.cards.Card;
import com.mygdx.game.item.Item;

public class InfoBoxManager {
    private GipGameProject game;
    private Stage stage;
    private Group infoBoxTableGroup;
    private Table table;


    public InfoBoxManager(GipGameProject game, Stage stage, Table table) {
        this.game = game;
        this.stage = stage;
        this.table = table;
        //table.debugTable();
        //table.debugActor();
        infoBoxTableGroup = new Group();

        //table.addActor(infoBoxTableGroup);
    }
    public Table getInfoBoxTable(Actor actor) {
        boolean isCard = false;
        Card card = null;
        Item item = null;
        String[] words = {""};
        if (actor instanceof Card) {
            isCard = true;
            card = (Card) actor;
            words = card.getDescription().replaceAll("[.]", "").split("\\s");
        } else if (actor instanceof Item) {
            item = (Item) actor;
            words = item.getDescription().replaceAll("[.]", "").split("\\s");
        }
        Array<InfoBox> infoBoxes = new Array<>();
        Table infoBoxTable = new Table();
        infoBoxTable.left();
        //infoBoxTable.debug();
        //infoBoxTable.debugActor();


        boolean found = false;
        for (String word : words) {
            for (InfoboxTypes.CardInfoType value : InfoboxTypes.CardInfoType.values()) {
                if (value.name().equals(word)) {
                    found = true;
                    final InfoBox infoBox = new InfoBox(game.skin(), word, game, stage);
                    infoBoxes.add(infoBox);
                }
            }
            for (InfoboxTypes.ItemInfoType itemInfoType : InfoboxTypes.ItemInfoType.values()) {
                if (itemInfoType.name().equals(word)) {
                    found = true;
                    final InfoBox infoBox = new InfoBox(game.skin(), word, game, stage);
                    infoBoxes.add(infoBox);
                }
            }
        }
        if (found) {
            InfoBoxListener infoBoxListener = new InfoBoxListener(infoBoxTable, isCard);
            if (isCard) {
                card.addListener(infoBoxListener);
            } else {
                assert item != null : "Item is null"; // throws assert exception with msg "Item is null"
                item.addListener(infoBoxListener);
            }
        }
        infoBoxTable.setSize(300, 300);

        infoBoxTable.setVisible(false);
        float maxHeight = 0;
        for (int i = 0; i < infoBoxes.size; i++) {
            float currentHeight = infoBoxes.get(i).getHeight();
            if (currentHeight > maxHeight) {
                maxHeight = currentHeight + 5;
            }
            infoBoxTable.setHeight(maxHeight);
            infoBoxTable.add(infoBoxes.get(i)).left().row();
        }
        if (infoBoxTable.getChildren().size>0) {
            if (isCard) {
                infoBoxTable.setPosition(table.getCell(card).getActorX() + table.getCell(card).getActorWidth()/2 + 120,
                        table.getCell(card).getActorY() + table.getCell(card).getActorHeight()/2 + 80);
            }


            //cards.addActor(infoBoxTable);



        }

        return infoBoxTable;

    }

}

