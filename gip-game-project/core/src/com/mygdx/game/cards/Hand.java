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
}
