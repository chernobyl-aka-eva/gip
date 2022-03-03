package com.mygdx.game.cards;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.SnapshotArray;
import com.mygdx.game.GipGameProject;
import com.mygdx.game.enemy.Enemy;
import com.mygdx.game.virus.Virus;

import java.util.Random;

public class CardManager {
    private Array<Card> playerCards;
    private Hand hand;
    private Group drawPile;
    private Group discardPile;
    private Group exhaustPile;
    private Group deckDisplay;
    private Virus player;
    private Enemy enemy;
    private GipGameProject game;
    private Stage stage;

    //constructor
    public CardManager(Virus player, Enemy enemy, GipGameProject game, Stage stage) {
        this.player = player;
        this.enemy = enemy;
        this.game = game;
        this.stage = stage;
        playerCards = new Array<>();
        hand = new Hand(); //extends group
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


    //adds card to players deck
    public void addCard(int id) {
        switch (id) {
            case 0: final Card strike = new Card(0, "Strike", CardType.ATTACK, 1, game.textureAtlas.findRegion("strike"));
            playerCards.add(strike); drawPile.addActor(strike); strike.setScale(0.8F); /*strike.getImage().setScale(strike.getScaleX(), strike.getScaleY()); */ break;
            case 1: final Card defend = new Card(1,"Defend", CardType.SKILL, 1, game.textureAtlas.findRegion("defend"));
            playerCards.add(defend); drawPile.addActor(defend); defend.setScale(0.8F); /*defend.getImage().setScale(defend.getScaleX(), defend.getScaleY());*/ break;
        }

    }

    //method for playing cards (I'm gonna rewrite this 100%)
    public void playCard (int id) {
        boolean inhand = false;
        SnapshotArray<Actor> cardActors = hand.getChildren();
        for (Actor actorCard: cardActors.items) {
            if (actorCard instanceof Card) {
                Card card = (Card) actorCard;

            }
        }

    }

    //draws card in hand (later called by turnManager)
    public void drawcard(int amount) {
        Random random = new Random();
        System.out.println(drawPile.getChildren().size);
        for (int i = 0; i < amount; i++) {
            int randomIndex = random.nextInt(drawPile.getChildren().size);
            hand.addActor(drawPile.getChild(randomIndex));
        }
        //hand.getChild(0).
    }

    //rendering hand actor group on screen
    public void renderHand() {
        game.batch.begin();
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();

        game.batch.end();
    }

    //constructs deckscreen with all cards of player
    public Table getdisplayDeck()  {
        game.skin = new Skin(Gdx.files.internal("skin/game-ui001.json"));
        game.skin.addRegions(new TextureAtlas("skin/game-ui001.atlas"));
        Table displayDeck = new Table(game.skin);
        for (int i = 1; i < playerCards.size+1; i++) {
            //creates copy of card to render in deckscreen (--> uses copy card constructor)
            Card cardCopy = new Card(playerCards.get(i-1));


            cardCopy.setScale(0.6F);





            //adds cards to table displaydeck (WIP)
            if (i % 5 == 0) {
                displayDeck.add(cardCopy).size((cardCopy.getWidth()*cardCopy.getScaleX())-(cardCopy.getWidth()*cardCopy.getScaleX())/4,
                        (cardCopy.getHeight()*cardCopy.getScaleY())-(cardCopy.getHeight()*cardCopy.getScaleY())/4).row();
            } else {
                displayDeck.add(cardCopy).size((cardCopy.getWidth()*cardCopy.getScaleX())-(cardCopy.getWidth()*cardCopy.getScaleX())/4,
                        (cardCopy.getHeight()*cardCopy.getScaleY())-(cardCopy.getHeight()*cardCopy.getScaleY())/4).colspan(5);
            }



        }

        return displayDeck;
        /*
        int counter = 0;
        for (Card card: playerCards) {
            Card cardClone = new Card(card);
            cardClone.setScale(0.5F);
            cardClone.getImage().setScale(cardClone.getScaleX(), cardClone.getScaleY());
            //deckDisplay.addActor(cardClone);
            //deckDisplay.add(cardClone.getImage());
            if (counter == 4) {
                counter = 0;
                deckDisplay.add(cardClone.getImage()).size(cardClone.getImage().getWidth()*cardClone.getImage().getScaleX(), cardClone.getImage().getHeight()*cardClone.getImage().getScaleY()).row();

            }
            deckDisplay.add(cardClone.getImage()).size(cardClone.getImage().getWidth()*cardClone.getScaleX(), cardClone.getImage().getHeight()*cardClone.getScaleY());
            counter++;

        }
        deckDisplay.pad(0);
        deckDisplay.padLeft(10F);
        deckDisplay.getColumnWidth(0);
        deckDisplay.getRowHeight(0);

         */


    }

    //getters & setters
    public Virus getPlayer() {
        return player;
    }

    public Enemy getEnemy() {
        return enemy;
    }

    public GipGameProject getGame() {
        return game;
    }

    public Array<Card> getPlayerCards() {
        return playerCards;
    }

    public void setPlayerCards(Array<Card> playerCards) {
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
