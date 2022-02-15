package com.mygdx.game.cards;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.SnapshotArray;
import com.mygdx.game.enemy.Enemy;
import com.mygdx.game.GipGameProject;
import com.mygdx.game.virus.Virus;

import java.util.Random;

public class CardManager {
    private Group playerCards;
    private Hand hand;
    private Group drawPile;
    private Group discardPile;
    private Group exhaustPile;
    private Virus player;
    private Enemy enemy;
    private GipGameProject game;
    private Stage stage;

    public CardManager(Virus player, Enemy enemy, GipGameProject game, Stage stage) {
        this.player = player;
        this.enemy = enemy;
        this.game = game;
        this.stage = stage;
        playerCards = new Group();
        hand = new Hand();
        drawPile = new Group();
        discardPile = new Group();
        exhaustPile = new Group();
        game.textureAtlas = new TextureAtlas("cards/cards.atlas");
        game.skin = new Skin(Gdx.files.internal("skin/game-ui.json")); // skin
        stage.addActor(hand);
        stage.addActor(drawPile); drawPile.setVisible(false);
        stage.addActor(discardPile); discardPile.setVisible(false);
        stage.addActor(exhaustPile); exhaustPile.setVisible(false);
        hand.setScale(0.5F);
    }


    public void addCard(int id) {
        switch (id) {
            case 0: final Card strike = new Card(0, new Label("Strike", game.skin), CardType.ATTACK, new Label("Deal 7 damage", game.skin), 1, game.textureAtlas.findRegion("common"));
            playerCards.addActorAt(0, strike); drawPile.addActor(strike); break;
            case 1: final Card defend = new Card(1, new Label("Defend", game.skin), CardType.SKILL, new Label("Gain 5 block", game.skin), 1, game.textureAtlas.findRegion("common"));
            playerCards.addActorAt(1, defend); drawPile.addActor(defend); break;
        }
    }

    public void playCard (int id) {
        boolean inhand = false;
        SnapshotArray<Actor> cardActors = hand.getChildren();
        for (Actor actorCard: cardActors.items) {
            if (actorCard instanceof Card) {
                Card card = (Card) actorCard;

            }
        }

    }
    public void drawcard(int amount) {
        Random random = new Random();
        System.out.println(drawPile.getChildren().size);
        for (int i = 0; i < amount; i++) {
            int randomIndex = random.nextInt(drawPile.getChildren().size);
            hand.addActor(drawPile.getChild(randomIndex));
        }
        //hand.getChild(0).
    }

    public void renderHand() {
        game.batch.begin();
        stage.draw();
        stage.act();

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

    public Group getPlayerCards() {
        return playerCards;
    }

    public void setPlayerCards(Group playerCards) {
        this.playerCards = playerCards;
    }

    public Hand getHand() {
        return hand;
    }

    public void setHand(Hand hand) {
        this.hand = hand;
    }

    public Group getDrawPile() {
        return drawPile;
    }

    public void setDrawPile(Group drawPile) {
        this.drawPile = drawPile;
    }

    public Group getDiscardPile() {
        return discardPile;
    }

    public void setDiscardPile(Group discardPile) {
        this.discardPile = discardPile;
    }

    public Group getExhaustPile() {
        return exhaustPile;
    }

    public void setExhaustPile(Group exhaustPile) {
        this.exhaustPile = exhaustPile;
    }

    public void setPlayer(Virus player) {
        this.player = player;
    }

    public void setEnemy(Enemy enemy) {
        this.enemy = enemy;
    }
}
