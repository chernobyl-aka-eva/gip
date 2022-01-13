package com.mygdx.game.cards;

import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Enemy;
import com.mygdx.game.Virus;

public class CardManager {
    private Array<Card> playerCards;
    private Array<Card> hand;
    private Array<Card> drawPile;
    private Array<Card> discardPile;
    private Array<Card> exhaustPile;
    private Virus player;
    private Enemy enemy;

    public void addCard(int id) {
        switch (id) {
            case 0: Card strike = new Card(0, "Strike", CardType.ATTACK, "Deal 7 damage", 1);
            playerCards.add(strike); break;
        }
    }
    public void playCard (int id) {
        boolean inhand = false;
        for (Card card: hand) {
            if (card.getId() == id) {
                inhand = true;
                break;
            }
        }
        if (inhand) {
            switch (id) {
                case 0: enemy.setHealth(enemy.getHealth()-7); break;
            }
        }
    }
    

}
