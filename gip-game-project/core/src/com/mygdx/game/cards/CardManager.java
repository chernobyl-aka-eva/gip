package com.mygdx.game.cards;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.GipGameProject;
import com.mygdx.game.monster.Monster;
import com.mygdx.game.monster.MonsterManager;
import com.mygdx.game.virus.Virus;
import com.mygdx.game.virus.VirusManager;

import java.util.Random;

public class CardManager {
    private Array<Card> playerCards;
    private Table handTable;
    private Array<Card> hand;
    private Array<Card> drawPile;
    private Array<Card> discardPile;
    private Array<Card> exhaustPile;
    private VirusManager virusManager;
    private MonsterManager monsterManager;

    private GipGameProject game;
    private Stage stage;
    private float elapsed_time;

    //constructor
    public CardManager(GipGameProject game, Stage stage) {
        this.game = game;
        this.stage = stage;

        virusManager = new VirusManager(game, stage);
        monsterManager = new MonsterManager(game, stage);

        playerCards = new Array<>();
        hand = new Hand(this);
        drawPile = new Array<>();
        discardPile = new Array<>();
        exhaustPile = new Array<>();
        game.textureAtlas = new TextureAtlas("cards/cards.atlas");
        game.skin = new Skin(Gdx.files.internal("skin/game-ui.json")); // skin

        handTable = new Table();
        handTable.setBounds(0, 0, Gdx.graphics.getWidth(), 100);
        handTable.center().top().align(Align.center);
        //handTable.debugTable();
        //handTable.debugActor();
        handTable.setName("handTable");
        stage.addActor(handTable);
    }



    //adds card to players deck
    public void addCard(int id) {
        switch (id) {
            case 0: final Card strike = new Card(0, "Strike", CardType.ATTACK, 1, game.textureAtlas.findRegion("strike"), elapsed_time);
                playerCards.add(strike); drawPile.add(strike); break;
            case 1: final Card defend = new Card(1,"Defend", CardType.SKILL, 1, game.textureAtlas.findRegion("defend"), elapsed_time);
                playerCards.add(defend); drawPile.add(defend); break;
        }



    }

    //method for playing cards (I'm gonna rewrite this 100%)
    public void playCard(int id, Virus player, Monster monster) {
        switch (id) {
            case 0:
                monster.setHealth(monster.getHealth()-6);
                break;
            case 1:

                break;
        }
    }

    //draws card in hand (later called by turnManager)
    public void drawcard(int amount) {
        Random random = new Random();
        System.out.println(drawPile.size);
        for (int i = 0; i < amount; i++) {
            if (hand.size < 10) {
                int randomIndex = random.nextInt(drawPile.size);
                hand.add(drawPile.get(randomIndex));
                drawPile.removeIndex(randomIndex);
            }
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

    public void positionHand(int index) {
        if (handTable.getCells().size < 10) {
            Card card = hand.get(index);
            card.setScale(0.6F);
            card.setSize(card.getTextureRegion().getRegionWidth()*card.getScaleX(), card.getTextureRegion().getRegionHeight()*card.getScaleY());
            card.setOrigin(Align.center);
            double padWidth = 0 - card.getWidth()/2.5;
            System.out.println("Padwidth: " + padWidth);
            handTable.add(card).size(card.getWidth(), card.getHeight()).padLeft((float) padWidth).padRight((float) padWidth);
            card.setContainingTable(handTable);
        }
    }

    public void makeDragable(final Card card) {
        DragAndDrop dragAndDrop = new DragAndDrop();
        DragAndDrop.Source source = new DragAndDrop.Source(card) {
            @Override
            public DragAndDrop.Payload dragStart(InputEvent event, float x, float y, int pointer) {
                DragAndDrop.Payload payload = new DragAndDrop.Payload();
                payload.setDragActor(card);
                for (Card allCards: hand) {
                    allCards.setDragging(true);
                }
                return payload;
            }
            @Override
            public void dragStop(InputEvent event, float x, float y, int pointer, DragAndDrop.Payload payload, DragAndDrop.Target target) {
                super.dragStop(event, x, y, pointer, payload, target);
                System.out.println("Test");
                System.out.println("Card: " + card.getX() + " " + card.getY());
                for (Actor actor: monsterManager.getMonsterGroup().getChildren()) {
                    if (actor instanceof Monster) {
                        Monster monster = (Monster) actor;
                        System.out.println(monster.getNameAreaMonster().getX() + " " + monster.getNameAreaMonster().getY());
                        if (card.getX()+260 >= monster.getNameAreaMonster().getX()) {
                            if (card.getY()+150 <= monster.getNameAreaMonster().getY()) {
                                hand.removeIndex(hand.indexOf(card, false));
                                handTable.removeActor(card);
                                double padWidth = 0 - card.getWidth()/2.5;
                                handTable.clear();
                                for (int i = 0; i < hand.size; i++) {
                                    positionHand(i);
                                }
                                handTable.invalidate();
                                handTable.validate();
                                discardPile.add(card);
                                playCard(card.getId(), virusManager.getPlayer(), monster);
                            }
                        }
                        if (!(card.getCardType().equals(CardType.ATTACK))) {

                        }
                    }
                }
                for (Card allCards: hand) {
                    allCards.setDragging(false);
                }
            }
        };
        dragAndDrop.addSource(source);
        dragAndDrop.cancelTouchFocusExcept(source);
        dragAndDrop.setDragActorPosition(card.getWidth() / 2, -card.getHeight() / 2); //centering actor on cursor


    }

    //constructs deckscreen with all cards of player
    public Table getdisplayDeck()  {
        game.skin = new Skin(Gdx.files.internal("skin/game-ui.json"));
        game.skin.addRegions(new TextureAtlas("skin/game-ui.atlas"));
        Table displayDeck = new Table(game.skin);
        displayDeck.center().top().align(Align.center).clip(true);
        //displayDeck.debugActor();
        displayDeck.add().padTop(100F).row();
        for (int i = 1; i < playerCards.size+1; i++) {
            //creates copy of card to render in deckscreen (--> uses copy card constructor)
            Card cardCopy = new Card(playerCards.get(i-1));
            cardCopy.setScale(0.6F);
            //cardCopy.debug();
            //adds cards to ta ble displaydeck (WIP)
            cardCopy.setOrigin(Align.bottomLeft);
            cardCopy.setSize(cardCopy.getWidth()*cardCopy.getScaleX(), cardCopy.getHeight()*cardCopy.getScaleY());
            cardCopy.setOrigin(Align.center);
            int amountPerRow = 5;
            float padWidth = (float) ((stage.getWidth()-(cardCopy.getWidth()*amountPerRow))/5+53);
            float padHeight = 0 - cardCopy.getHeight()/5;
            displayDeck.add(cardCopy).size(cardCopy.getWidth(), cardCopy.getHeight()).colspan(amountPerRow).space(0).padRight(padWidth).padLeft(padWidth).padTop(padHeight).padBottom(padHeight).center();
            if (i % amountPerRow == 0) {
                displayDeck.add().row();
            }

        }
        displayDeck.add().padBottom(100F).row();


        return displayDeck;
    }

    //getters & setters


    public VirusManager getVirusManager() {
        return virusManager;
    }

    public void setVirusManager(VirusManager virusManager) {
        this.virusManager = virusManager;
    }

    public MonsterManager getMonsterManager() {
        return monsterManager;
    }

    public void setMonsterManager(MonsterManager monsterManager) {
        this.monsterManager = monsterManager;
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

    public float getElapsed_time() {
        return elapsed_time;
    }

    public void setElapsed_time(float elapsed_time) {
        this.elapsed_time = elapsed_time;
    }
}
