package com.mygdx.game.infobox;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.GipGameProject;
import com.mygdx.game.cards.Card;
import com.mygdx.game.cards.CardInfoType;

public class InfoBoxManager {
    private GipGameProject game;
    private Stage stage;
    private Table infoBoxTable;
    private Table cards;
    private CardInfoType cardInfoType;


    public InfoBoxManager(GipGameProject game, Stage stage, Table cards) {
        this.game = game;
        this.stage = stage;
        this.cards = cards;
        //cards.debugTable();
        //cards.debugActor();

        for (Cell cell : cards.getCells()) {
            Actor actor = cell.getActor();
            if (actor instanceof Card) {
                Card card = (Card) actor;
                String[] words = card.getDescription().replaceAll("[.]", "").split("\\s");
                boolean isCardInfo = false;
                for (String word : words) {
                    for (CardInfoType value : CardInfoType.values()) {
                        if (value.name().equals(word)) {
                            final InfoBox infoBox = new InfoBox("", game.skin, "card-info", word, game, stage);
                            infoBox.setPosition(cards.getCell(card).getActorX() + cards.getCell(card).getActorWidth()/2 + 120,
                                    cards.getCell(card).getActorY() + cards.getCell(card).getActorHeight()/2 + 80);
                            infoBox.setVisible(false);
                            card.addListener(new ClickListener() {
                                @Override
                                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                                    super.enter(event, x, y, pointer, fromActor);
                                    if (pointer == -1) {
                                        infoBox.setVisible(true);
                                    }
                                }

                                @Override
                                public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                                    super.exit(event, x, y, pointer, toActor);
                                    if (pointer == -1) {
                                        infoBox.setVisible(false);
                                    }
                                }
                            });
                            stage.addActor(infoBox);
                        }
                    }
                }
            }



        }
    }
}
