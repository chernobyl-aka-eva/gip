package com.mygdx.game.cards;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.GipGameProject;
import com.mygdx.game.infobox.InfoBoxManager;
import com.mygdx.game.monster.Monster;
import com.mygdx.game.monster.MonsterManager;
import com.mygdx.game.virus.Virus;
import com.mygdx.game.virus.VirusManager;

import java.util.Random;

public class CardManager {
    private Array<Card> playerCards;
    private Table handTable;
    private Hand hand;
    private Array<Card> drawPile;
    private Array<Card> discardPile;
    private Array<Card> exhaustPile;
    private VirusManager virusManager;
    private MonsterManager monsterManager;
    private Group tableGroup;
    private Group deckScreenGroup;

    private GipGameProject game;
    private Stage stage;
    private Group gameScreenGroup;
    private float elapsed_time;

    //constructor
    public CardManager(GipGameProject game, Stage stage, Group gameScreenGroup) {
        this.game = game;
        this.stage = stage;
        this.gameScreenGroup = gameScreenGroup;
        virusManager = new VirusManager(game, stage, gameScreenGroup);
        monsterManager = new MonsterManager(game, stage, gameScreenGroup);

        playerCards = new Array<>();
        hand = new Hand(this);
        drawPile = new Array<>();
        discardPile = new Array<>();
        exhaustPile = new Array<>();

        game.skin = new Skin(Gdx.files.internal("skin/game-ui.json")); // skin

        handTable = new Table();
        handTable.setBounds(0, 0, stage.getWidth(), 100);
        handTable.center().top().align(Align.center);
        //handTable.debugTable();
        //handTable.debugActor();
        handTable.setName("handTable");
        gameScreenGroup.addActor(handTable);

        initTables();
    }



    //adds card to players deck
    public void addCard(int id) {
        game.textureAtlas = new TextureAtlas("cards/cards.atlas");
        switch (id) {
            case 0: final Card strike = new Card(0, "Strike", "", CardType.ATTACK, 1, game.textureAtlas.findRegion("strike"), elapsed_time, game);
                playerCards.add(strike); drawPile.add(strike); break;
            case 1: final Card defend = new Card(1,"Defend", "", CardType.SKILL, 1, game.textureAtlas.findRegion("defend"), elapsed_time, game);
                playerCards.add(defend); drawPile.add(defend); break;
            case 2: final Card replicate = new Card(2, "Replicate", "ENCODE", CardType.ATTACK, 0, game.textureAtlas.findRegion("replicate"), elapsed_time, game);
                playerCards.add(replicate); drawPile.add(replicate); break;
            case 3: final Card gotoCard = new Card(3, "Go To", "ENCODE. COMPILE", CardType.SKILL, 1, game.textureAtlas.findRegion("go to"), elapsed_time, game);
                playerCards.add(gotoCard); drawPile.add(gotoCard);break;
            case 4: final Card piercingShot = new Card(4, "Piercing Shot", "EXHAUST", CardType.ATTACK, 1, game.textureAtlas.findRegion("piercing shot"), true, elapsed_time, game);
                playerCards.add(piercingShot); drawPile.add(piercingShot);
            case 5: final Card fineTuning = new Card(5, "Fine Tuning", "DEXTERITY", CardType.POWER, 2, game.textureAtlas.findRegion("fine tuning"),elapsed_time, game);
                playerCards.add(fineTuning); drawPile.add(fineTuning);
        }
    }

    //method for playing cards (I'm gonna rewrite this 100%)
    public void playCard(Card card, Virus player, Monster monster) {
        if (virusManager.getPlayer().getEnergyManager().getEnergy() != 0){
            switch (card.getId()) {
                case 0:
                    monster.setHealth(monster.getHealth()-6);
                    virusManager.getPlayer().getEnergyManager().setEnergy(virusManager.getPlayer().getEnergyManager().getEnergy()
                            - card.getCost());
                    if (monster.getHealth() <= 0){
                        monster.remove();
                        monsterManager.setIntentVisible(false);
                    }
                    break;
                case 1:
                    player.setBlock(player.getBlock()+6);
                    virusManager.getPlayer().getEnergyManager().setEnergy(virusManager.getPlayer().getEnergyManager().getEnergy()
                            - card.getCost());
                    break;
                case 2:

                case 3: game.log.debug("case 3");break;
                case 4: game.log.debug("case 3");break;
                case 5:game.log.debug("case 3");break;

            }

            if (!card.isExhaust()){
                discardPile.add(card);
            }else{
                exhaustPile.add(card);
            }

            refreshDisplayTable();
        }
    }

    //draws card in hand (later called by turnManager)
    public void drawcard(int amount) {
        if (drawPile.size <= amount) {
            for (Card card : discardPile) {
                drawPile.add(card);
            }
            discardPile.clear();
        }
        Random random = new Random();
        System.out.println(drawPile.size);
        for (int i = 0; i < amount; i++) {
            if (hand.size < 10) {
                int randomIndex = random.nextInt(drawPile.size);
                hand.add(drawPile.get(randomIndex));
                drawPile.removeIndex(randomIndex);
            }
        }
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
            card.setScale(0.7F);
            card.setSize(card.getTextureRegion().getRegionWidth()*card.getScaleX(), card.getTextureRegion().getRegionHeight()*card.getScaleY());
            card.setOrigin(Align.center);
            //System.out.println("Width: " + card.getWidth() + " Height: " + card.getHeight());
            double padWidth = 0 - card.getWidth()/2.5;
            //System.out.println("Padwidth: " + padWidth);
            handTable.add(card).size(card.getWidth(), card.getHeight()).padLeft((float) padWidth).padRight((float) padWidth);
            card.setContainingTable(handTable);
        }
    }

    public void makeDragable(final Card card) {
        card.addListener(new DragListener() {
            @Override
            public void dragStart(InputEvent event, float x, float y, int pointer) {
                super.dragStart(event, x, y, pointer);
                for (Card card1 : hand) {
                    card1.setDragging(true);
                }
            }

            @Override
            public void drag(InputEvent event, float x, float y, int pointer) {
                super.drag(event, x, y, pointer);
                card.moveBy(x - card.getWidth() / 2, y - card.getHeight() / 2);

            }
            @Override
            public void dragStop(InputEvent event, float x, float y, int pointer) {
                super.dragStop(event, x, y, pointer);
                Action fadeOut = Actions.fadeOut(0.7F);
                Action fadeIn = Actions.fadeIn(0.7F);
                Action completeAction = new Action() {
                    @Override
                    public boolean act(float delta) {
                        for (Card card1 : hand) {
                            card1.setDragging(false);
                        }
                        return true;
                    }
                };
                SequenceAction sequenceAction = new SequenceAction(fadeOut, completeAction, fadeIn);
                card.addAction(sequenceAction);

                for (Actor actor: monsterManager.getMonsterGroup().getChildren()) {
                    if (actor instanceof Monster) {
                        Monster monster = (Monster) actor;
                        //System.out.println(monster.getNameAreaMonster().getX() + " " + monster.getNameAreaMonster().getY());
                        if (card.getX()+250 >= monster.getNameAreaMonster().getX()) {
                            if (card.getY()+150 <= monster.getNameAreaMonster().getY()) {
                                if (virusManager.getPlayer().getEnergyManager().getEnergy() != 0 || card.getCost() == 0){
                                    hand.removeIndex(hand.indexOf(card, false));
                                    //handTable.removeActor(card);
                                    double padWidth = 0 - card.getWidth()/2.5;
                                    handTable.invalidate();
                                    handTable.validate();
                                    playCard(card, virusManager.getPlayer(), monster);
                                }
                            }
                        }
                        if (!(card.getCardType().equals(CardType.ATTACK))) {
                            Virus player = virusManager.getPlayer();
                            //System.out.println("Card: " + card.getX() + " " + card.getY());
                            //System.out.println("Virus X:\t" + player.getNameAreaVirus().getX() + " max x:\t" + (player.getNameAreaVirus().getX() + player.getNameAreaVirus().getWidth()));
                            //System.out.print("Virus Y:\t" + player.getNameAreaVirus().getY() + " max y:\t" + (player.getNameAreaVirus().getY() + player.getNameAreaVirus().getHeight()));
                            if (card.getX() <= 280 && card.getY() <= 280) {
                                if (virusManager.getPlayer().getEnergyManager().getEnergy() != 0 || card.getCost() == 0) {
                                    hand.removeIndex(hand.indexOf(card, false));
                                    double padWidth = 0 - card.getWidth() / 2.5;
                                    handTable.invalidate();
                                    handTable.validate();
                                    discardPile.add(card);
                                    playCard(card, virusManager.getPlayer(), null);
                                }
                            }
                        }
                    }
                }
                for (Card allCards: hand) {
                    allCards.setDragging(false);
                }
                handTable.clear();
                hand.refreshHand();
            }
        });
    }

    public void refreshDisplayTable() {
        tableGroup.clear();
        Table table = displayCards(0);
        Table drawTable = displayCards(1);
        Table discardTable = displayCards(2);

        tableGroup.addActor(table);
        tableGroup.addActor(drawTable);
        tableGroup.addActor(discardTable);
    }

    public void initTables() {
        game.skin = new Skin(Gdx.files.internal("skin/game-ui.json"));
        game.skin.addRegions(new TextureAtlas("skin/game-ui.atlas"));


        deckScreenGroup = new Group();
        tableGroup = new Group();
        Table table = displayCards(0);
        Table drawTable = displayCards(1);
        Table discardTable = displayCards(2);

        tableGroup.addActor(table);
        tableGroup.addActor(drawTable);
        tableGroup.addActor(discardTable);


        Button deckReturn = new Button(game.skin, "return");
        deckReturn.setPosition(stage.getWidth()-deckReturn.getWidth(), 100);
        deckReturn.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {}

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                deckScreenGroup.setVisible(false);
                tableGroup.getChild(0).setVisible(false);
                tableGroup.getChild(1).setVisible(false);
                tableGroup.getChild(2).setVisible(false);
                gameScreenGroup.setVisible(true);
                return true;
            }
        });
        deckScreenGroup.addActor(deckReturn);
        deckScreenGroup.setVisible(false);
        stage.addActor(tableGroup);
        stage.addActor(deckScreenGroup);
    }

    public Table displayCards(int type)  {
        boolean exists = true;

        game.skin = new Skin(Gdx.files.internal("skin/game-ui.json"));
        game.skin.addRegions(new TextureAtlas("skin/game-ui.atlas"));
        Table table = new Table(game.skin);
        table.setVisible(false);
        table.setBounds(0,0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        table.setSize(stage.getWidth(), stage.getHeight()-60);
        table.setOrigin(Align.center);
        table.align(Align.center);
        //table.debug();

        Array<Card> displayList = new Array<>();
        String title = "";
        switch (type) {
            case 0:
                title = "Deck";
                displayList = playerCards; break;
            case 1:
                title = "Draw pile";
                displayList = drawPile; break;
            case 2:
                title = "Discard pile";
                displayList = discardPile; break;
        }


        Table displayDeck = new Table(game.skin);
        displayDeck.center().top().align(Align.center).clip(true);
        //displayDeck.debugActor();
        displayDeck.add().padTop(100F).row();
        for (int i = 1; i < displayList.size+1; i++) {
            //creates copy of card to render in deckscreen (--> uses copy card constructor deckscreen)
            Card cardCopy = new Card(displayList.get(i-1), true);
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

        displayDeck.setSize(stage.getWidth(), stage.getHeight());
        displayDeck.setOrigin(Align.center);
        displayDeck.align(Align.center);
        displayDeck.setBounds(displayDeck.getX(), displayDeck.getY(), stage.getWidth(), stage.getHeight()-60);
        ScrollPane scrollPane = new ScrollPane(displayDeck, game.skin);
        scrollPane.setSize(stage.getWidth(), stage.getHeight());
        scrollPane.setOrigin(Align.center);
        scrollPane.validate();
        table.add(title).row();
        table.add(scrollPane);


        //stage.addActor(table);
        //deckScreenGroup.addActor(table);

        InfoBoxManager infoBoxManager = new InfoBoxManager(game, stage, displayDeck);





        return table;
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

    public Table getHandTable() {
        return handTable;
    }

    public void setHandTable(Table handTable) {
        this.handTable = handTable;
    }

    public Hand getHand() {
        return hand;
    }

    public void setHand(Hand hand) {
        this.hand = hand;
    }

    public Array<Card> getDrawPile() {
        return drawPile;
    }

    public void setDrawPile(Array<Card> drawPile) {
        this.drawPile = drawPile;
    }

    public Array<Card> getDiscardPile() {
        return discardPile;
    }

    public void setDiscardPile(Array<Card> discardPile) {
        this.discardPile = discardPile;
    }

    public Array<Card> getExhaustPile() {
        return exhaustPile;
    }

    public void setExhaustPile(Array<Card> exhaustPile) {
        this.exhaustPile = exhaustPile;
    }

    public Group getTableGroup() {
        return tableGroup;
    }

    public void setTableGroup(Group tableGroup) {
        this.tableGroup = tableGroup;
    }

    public Group getDeckScreenGroup() {
        return deckScreenGroup;
    }

    public void setDeckScreenGroup(Group deckScreenGroup) {
        this.deckScreenGroup = deckScreenGroup;
    }
}
