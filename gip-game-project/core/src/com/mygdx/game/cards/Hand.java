package com.mygdx.game.cards;

import com.badlogic.gdx.utils.Array;

public class Hand extends Array<Card> {
    private CardManager cardManager;
    public Hand(CardManager cardManager) {
        super();
        this.cardManager = cardManager;
    }

    @Override
    public void add(Card value) {
        super.add(value);
        int index = this.lastIndexOf(value, false);
        cardManager.positionHand(index);
        cardManager.makeDragable(value);
    }
    public void refreshHand() {
        //cardManager.getHandTable().clearChildren();
        for (int i = 0; i < size; i++) {
            System.out.println(i);
            cardManager.positionHand(i);
            cardManager.makeDragable(get(i));
        }

    }
}
