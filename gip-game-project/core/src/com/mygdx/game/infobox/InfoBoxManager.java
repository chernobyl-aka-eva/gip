package com.mygdx.game.infobox;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.GipGameProject;
import com.mygdx.game.cards.Card;
import com.mygdx.game.cards.CardInfoType;

public class InfoBoxManager {
    private GipGameProject game;
    private Stage stage;
    private Group infoBoxTableGroup;
    private Table cards;
    private CardInfoType cardInfoType;


    public InfoBoxManager(GipGameProject game, Stage stage, Table cards) {
        this.game = game;
        this.stage = stage;
        this.cards = cards;
        //cards.debugTable();
        //cards.debugActor();
        infoBoxTableGroup = new Group();

        //cards.addActor(infoBoxTableGroup);
    }
    public Table getInfoBoxTable(Card card) {
        Array<InfoBox> infoBoxes = new Array<>();
        Table infoBoxTable = new Table();
        infoBoxTable.left();
        //infoBoxTable.debug();
        //infoBoxTable.debugActor();

        String[] words = card.getDescription().replaceAll("[.]", "").split("\\s");
        boolean isCardInfo = false;
        for (String word : words) {
            for (CardInfoType value : CardInfoType.values()) {
                if (value.name().equals(word)) {
                    final InfoBox infoBox = new InfoBox("", game.skin, "card-info", word, game, stage);
                    //infoBox.setVisible(false);
                    InfoBoxListener infoBoxListener = new InfoBoxListener(infoBoxTable);
                    card.addListener(infoBoxListener);
                    infoBoxes.add(infoBox);
                }
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
            //infoBoxTable.getCell().set
            infoBoxTable.setPosition(cards.getCell(card).getActorX() + cards.getCell(card).getActorWidth()/2 + 120,
                    cards.getCell(card).getActorY() + cards.getCell(card).getActorHeight()/2 + 80);
            //cards.addActor(infoBoxTable);



        }

        return infoBoxTable;

    }
}

