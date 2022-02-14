package com.mygdx.game.cards;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.enemy.Enemy;
import com.mygdx.game.GipGameProject;
import com.mygdx.game.virus.Virus;

import java.util.Random;

public class CardManager {
    private Array<Card> playerCards;
    private Array<Card> hand;
    private Array<Card> drawPile;
    private Array<Card> discardPile;
    private Array<Card> exhaustPile;
    private Virus player;
    private Enemy enemy;
    private GipGameProject game;

    public CardManager(GipGameProject game, Virus player, Enemy enemy) {
        this.player = player;
        this.enemy = enemy;
        this.game = game;
        playerCards = new Array<>();
        hand = new Array<>();
        drawPile = new Array<>();
        discardPile = new Array<>();
        exhaustPile = new Array<>();
        game.textureAtlas = new TextureAtlas("cards/cards.atlas");
        game.skin = new Skin(Gdx.files.internal("skin/game-ui.json")); // skin

    }

    public void addCard(int id) {
        switch (id) {
            case 0: Card strike = new Card(0, "Strike", CardType.ATTACK, "Deal 7 damage", 1, game.textureAtlas.findRegion("common"));
            playerCards.add(strike); drawPile.add(strike); createCard(strike); break;
            case 1: Card defend = new Card(1, "Defend", CardType.SKILL, "Gain 5 block", 1, game.textureAtlas.findRegion("common"));
            playerCards.add(defend); drawPile.add(defend); createCard(defend); break;
        }
    }

    public void createCard(Card card) {
        Label title = new Label(card.getName(), game.skin);
        title.set
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
    public void drawcard(int amount) {
        Random random = new Random();
        for (int i = 0; i < amount; i++) {
            int randomIndex = random.nextInt(drawPile.size);
            hand.add(drawPile.get(randomIndex));
            drawPile.removeIndex(randomIndex);
        }
    }

    public void renderHand() {
        game.batch.begin();
        int x = 100;
        for (Card card: hand) {
            x+= 100;
            game.batch.draw(card.getTextureRegion(), x, 100, 100, 100);
        }

        game.batch.end();
    }

    public Virus getPlayer() {
        return player;
    }

    public Enemy getEnemy() {
        return enemy;
    }

    public GipGameProject getGame() {
        return game;
    }
}
