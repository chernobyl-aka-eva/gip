package com.mygdx.game.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.GipGameProject;
import com.mygdx.game.TurnManager;
import com.mygdx.game.cards.Card;
import com.mygdx.game.cards.CardManager;
import com.mygdx.game.save.SavedState;
import com.mygdx.game.screen.GameScreen;

import java.util.Random;
import java.util.UUID;

public class EventManager {
    private Map map;
    private TurnManager turnManager;
    private CardManager cardManager;
    private GipGameProject game;
    private Stage stage;

    private Button endTurn;
    private TextButton confirmUpgrade;
    private Button returnButton;
    private Image fadeImage;
    private Table fadeTable = new Table();
    private Table restTable = new Table();
    private Table upgradeTable = new Table();
    private UUID selectedCardUUID;

    public boolean inspectingCard = false;
    private SavedState savedState;

    private GameScreen gameScreen;

    private TextureAtlas textureAtlas;

    private Window rewardWindow;
    private Table rewardCards;

    public EventManager(SavedState savedState, final GameScreen gameScreen, final CardManager cardManager, Stage stage, GipGameProject game) {
        this.savedState = savedState;
        this.gameScreen = gameScreen;
        this.cardManager = cardManager;
        this.game = game;
        this.stage = stage;






        map = new Map(gameScreen, savedState);
        map.getMapBackground().setCurrentEvent(map.getCurrentEvent());

        textureAtlas = new TextureAtlas("other/game-ui-2.atlas");
        fadeImage = new Image(new TextureRegionDrawable(textureAtlas.findRegion("card-inspect-fade")));
        fadeImage.setPosition(0, -60);
        fadeImage.addAction(Actions.alpha(0.8F));
        fadeImage.setVisible(false);
        fadeImage.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (inspectingCard) {
                    inspectCard(false, null);
                }
                return super.touchDown(event, x, y, pointer, button);
            }
        });

        endTurn = new Button(game.skin(), "end-turn");
        endTurn.setPosition(stage.getWidth()-120-endTurn.getWidth(), 150);
        gameScreen.getGameScreenGroup().addActor(endTurn);

        confirmUpgrade = new TextButton("Confirm", game.skin(), "restButton");
        confirmUpgrade.setVisible(false);
        //confirmUpgrade.setPosition(stage.getWidth()-confirmUpgrade.getWidth(), gameScreen.getStage().getHeight() - 870);
        confirmUpgrade.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                confirmUpgrade.setVisible(false);
                upgradeTable.setVisible(false);
                fadeTable.setVisible(false);
                fadeImage.setVisible(false);
                endTurn.setVisible(true);
                cardManager.getHandTable().setVisible(true);
                gameScreen.getExhaustpile().setVisible(true);
                gameScreen.getDiscardpile().setVisible(true);
                gameScreen.getDrawpile().setVisible(true);
                cardManager.getVirusManager().getPlayer().setVisible(true);
                cardManager.getVirusManager().getPlayer().getEnergyManager().setVisible(true);

                if (selectedCardUUID != null) {
                    for (Card playerCard : cardManager.getPlayerCards()) {
                        if (playerCard.getUniqueIdentifier() == selectedCardUUID) {
                            playerCard.setUpgraded(true);
                        }
                    }
                }
                restReset(restTable);
                return super.touchDown(event, x, y, pointer, button);
            }
        });

        gameScreen.getForGroundGroup().addActor(fadeImage);
        fadeTable.setVisible(true);
        gameScreen.getForGroundGroup().addActor(fadeTable);
        //gameScreen.getForGroundGroup().addActor(confirmUpgrade);






        turnManager = new TurnManager(game, stage, gameScreen.getGameScreenGroup(), endTurn, cardManager, savedState);

    }

    public void generateRewards() {
        rewardWindow = new Window("", game.skin());
        rewardWindow.align(Align.top|Align.center);
        rewardWindow.setSize(1000, 700);
        rewardWindow.setPosition((stage.getWidth() - rewardWindow.getWidth()) / 2, (stage.getHeight() - rewardWindow.getHeight()) / 2);
        gameScreen.getForGroundGroup().addActor(rewardWindow);
        rewardWindow.add("REWARDS!").padTop(50).row();
        rewardWindow.add("Pick a card").row();
        rewardWindow.setVisible(true);

        rewardCards = new Table();
        //rewardCards.debug();
        rewardCards.setSize(rewardWindow.getWidth(), 200);

        rewardWindow.add(rewardCards);
        rewardCards.clearChildren();
        final int[] chosenCardIndex = {-1};
        for (int i = 0; i < 3; i++) {
            Random random = new Random();
            int chance = random.nextInt(100) + 1;
            int randomCardId = 0;
            if (chance <= 50) {
                randomCardId = cardManager.getCardList().getGreenCards().get(random.nextInt(cardManager.getCardList().getGreenCards().size));
            } else if (chance <= 80) {
                randomCardId = cardManager.getCardList().getBlueCards().get(random.nextInt(cardManager.getCardList().getBlueCards().size));
            } else {
                randomCardId = cardManager.getCardList().getGoldCards().get(random.nextInt(cardManager.getCardList().getGoldCards().size));
            }
            final Card rewardCard = cardManager.getCardList().getCard(randomCardId);
            final Card cardCopy = new Card(rewardCard, true, false);
            float scale = 0.6F;
            int upgradedChance = random.nextInt(100) + 1;
            if (upgradedChance <= 30) {
                cardCopy.setUpgraded(true);
            }

            cardCopy.setScale(scale);
            cardCopy.setOrigin(Align.bottomLeft);
            cardCopy.setSize(cardCopy.getWidth()*scale, cardCopy.getHeight()*scale);
            cardCopy.setOrigin(Align.center);

            cardCopy.addListener(new ClickListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    if (pointer != -1) {
                        for (int i = 0; i < rewardCards.getCells().size; i++) {
                            Cell cell = rewardCards.getCells().get(i);
                            if (cell.getActor() instanceof Card) {
                                Card cellCard = (Card) cell.getActor();
                                if (cellCard.equals(cardCopy)) {
                                    chosenCardIndex[0] = i;
                                    cellCard.addAction(Actions.alpha(1));
                                } else {
                                    cellCard.addAction(Actions.alpha(0.5F));
                                }
                            }
                        }
                    }
                    return super.touchDown(event, x, y, pointer, button);
                }
            });
            rewardCards.add(cardCopy).size(cardCopy.getWidth(), cardCopy.getHeight()).pad(-100);
        }
        rewardWindow.row();
        TextButton confirmCardReward = new TextButton("Confirm", game.skin(), "restButton");
        confirmCardReward.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println(chosenCardIndex[0]);
                if (chosenCardIndex[0] == -1) {
                    // no choice made
                    SequenceAction sqa = new SequenceAction();
                    sqa.addAction(Actions.rotateBy(0.5F, 0.2F));
                    sqa.addAction(Actions.rotateBy(-1F, 0.2F));
                    sqa.addAction(Actions.rotateBy(0.5F, 0.2F));

                    SequenceAction sqa1 = new SequenceAction();
                    sqa1.addAction(Actions.rotateBy(0.5F, 0.2F));
                    sqa1.addAction(Actions.rotateBy(-1F, 0.2F));
                    sqa1.addAction(Actions.rotateBy(0.5F, 0.2F));

                    SequenceAction sqa2 = new SequenceAction();
                    sqa2.addAction(Actions.rotateBy(0.5F, 0.2F));
                    sqa2.addAction(Actions.rotateBy(-1F, 0.2F));
                    sqa2.addAction(Actions.rotateBy(0.5F, 0.2F));

                    //rewardCards.addAction(sqa);

                    rewardCards.getCells().get(0).getActor().addAction(sqa);
                    rewardCards.getCells().get(1).getActor().addAction(sqa1);
                    rewardCards.getCells().get(2).getActor().addAction(sqa2);

                } else {
                    // choice made
                    Card chosenCard = cardManager.getCardList().getCard(((Card) rewardCards.getCells().get(chosenCardIndex[0]).getActor()).getId());
                    chosenCard.setUpgraded(((Card) rewardCards.getCells().get(chosenCardIndex[0]).getActor()).isUpgraded());
                    cardManager.addTo(chosenCard);

                    endTurn.setVisible(true);
                    cardManager.getHandTable().setVisible(true);
                    gameScreen.getExhaustpile().setVisible(true);
                    gameScreen.getDiscardpile().setVisible(true);
                    gameScreen.getDrawpile().setVisible(true);
                    cardManager.getVirusManager().getPlayer().setVisible(true);
                    cardManager.getVirusManager().getPlayer().getEnergyManager().setVisible(true);

                    fadeImage.setVisible(false);
                    rewardWindow.setVisible(false);


                    nextEvent();
                }
                return super.touchDown(event, x, y, pointer, button);
            }
        });

        rewardWindow.add(confirmCardReward);



    }

    public void eventEnded() {
        cardManager.getVirusManager().getPlayer().getEffectManager().getEffects().clear();
        cardManager.getVirusManager().getPlayer().getEffectManager().getEffectTable().clearChildren();
        // rewardScreen
        switch (map.getMapBackground().getMapEvents().get(map.getMapBackground().getCurrentEvent()).getMapEventType()) {
            case MONSTER:
                endTurn.setVisible(false);
                cardManager.getHandTable().setVisible(false);
                gameScreen.getExhaustpile().setVisible(false);
                gameScreen.getDiscardpile().setVisible(false);
                gameScreen.getDrawpile().setVisible(false);
                cardManager.getVirusManager().getPlayer().setVisible(false);
                cardManager.getVirusManager().getPlayer().getEnergyManager().setVisible(false);

                fadeImage.setVisible(true);
                // show rewardScreen
                generateRewards();


                break;

            default: nextEvent();
        }


        cardManager.resetFunction();


    }

    public static <T extends Enum<?>> T randomEnum(Class<T> enumClass){
        Random random = new Random();
        int x = random.nextInt(enumClass.getEnumConstants().length);
        return enumClass.getEnumConstants()[x];
    }

    public void nextEvent() {
        endTurn.setVisible(true);
        cardManager.getHandTable().setVisible(true);
        gameScreen.getExhaustpile().setVisible(true);
        gameScreen.getDiscardpile().setVisible(true);
        gameScreen.getDrawpile().setVisible(true);
        cardManager.getVirusManager().getPlayer().setVisible(true);
        cardManager.getCompileTable().setVisible(true);
        cardManager.getVirusManager().getPlayer().getEnergyManager().setVisible(true);
        if (returnButton != null) {
            returnButton.setVisible(false);
        }
        restTable.setVisible(false);
        fadeImage.setVisible(false);

        map.setShowMap(true);
        map.setPreviousState(false);
        gameScreen.getGameScreenGroup().setVisible(false);
        map.getMapScreenGroup().setVisible(true);
        if (map.getCurrentEvent() == 6) {
            System.out.println();
        }
        map.setCurrentEvent(map.getCurrentEvent()+1);
        map.getMapBackground().setCurrentEvent(map.getCurrentEvent());


        switch (map.getMapBackground().getMapEvents().get(map.getMapBackground().getCurrentEvent()).getMapEventType()) {
            case MONSTER:
                cardManager.getMonsterManager().addMonster(0);
                cardManager.resetHand();
                turnManager = new TurnManager(game, stage, gameScreen.getGameScreenGroup(), endTurn, cardManager, null);
                break;
            case REST:
                System.out.println("This is a rest site");
                endTurn.setVisible(false);
                cardManager.getHandTable().setVisible(false);
                gameScreen.getExhaustpile().setVisible(false);
                gameScreen.getDiscardpile().setVisible(false);
                gameScreen.getDrawpile().setVisible(false);
                cardManager.getVirusManager().getPlayer().setVisible(false);
                cardManager.getVirusManager().getPlayer().getEnergyManager().setVisible(false);
                cardManager.getCompileTable().setVisible(false);

                restTable = new Table();
                restTable.clear();
                restTable.setBounds(0, 0, stage.getWidth(), stage.getHeight()-60);
                restTable.align(Align.top);
                Group restGroup = new Group();
                Label restTitle = new Label("REST SITE",game.skin());
                restGroup.addActor(restTitle);
                restGroup.scaleBy(1.5F);
                TextButton heal = new TextButton("HEAL", game.skin(), "restButton");
                TextButton upgrade = new TextButton("UPGRADE", game.skin(), "restButton");
                GlyphLayout glyphord = new GlyphLayout(getGame().font, restTitle.getText());
                restTable.add(restGroup).size(glyphord.width*1.5F/2, glyphord.height).pad(100).row();
                restTable.add(heal).pad(20);
                restTable.add(upgrade).pad(20);




                upgradeTable = new Table(game.skin());
                upgradeTable.setBounds(0,0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
                upgradeTable.setSize(stage.getWidth(), stage.getHeight()-60);
                upgradeTable.setVisible(false);
                Array<Card> displayList = cardManager.getPlayerCards();

                String windowTitle = "DECK";
                Table scrollWindow = new Table(game.skin());

                Table displayDeck = new Table();
                displayDeck.center().top().align(Align.center).clip(true);
                displayDeck.add().padTop(70F).row();

                for (int i = 1; i < displayList.size+1; i++) {
                    final Card cardCopy = new Card(displayList.get(i-1), true, false);
                    cardCopy.setScale(0.6F);
                    Card previousCard = null;

                    cardCopy.setOrigin(Align.bottomLeft);
                    cardCopy.setSize(cardCopy.getWidth()*cardCopy.getScaleX(), cardCopy.getHeight()*cardCopy.getScaleY());
                    cardCopy.setOrigin(Align.center);
                    int amountPerRow = 5;
                    float padWidth = (float) ((stage.getWidth()-(cardCopy.getWidth()*amountPerRow))/5+53);
                    float padHeight = 0 - cardCopy.getHeight()/5;

                    cardCopy.addListener(new ClickListener() {
                        @Override
                        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                            selectedCardUUID = cardCopy.getUniqueIdentifier();
                            Card inspectCard = new Card(cardCopy, false, true);
                            EventManager.this.inspectCard(true, inspectCard);
                            return super.touchDown(event, x, y, pointer, button);
                        }
                    });

                    displayDeck.add(cardCopy).size(cardCopy.getWidth(), cardCopy.getHeight()).colspan(amountPerRow).padRight(padWidth).padLeft(padWidth).padTop(padHeight).padBottom(padHeight).center();
                    cardCopy.toBack();
                    if (i % amountPerRow == 0) {
                        displayDeck.add().row();
                    }
                }

                displayDeck.add().padBottom(100F).row();

                displayDeck.setSize(stage.getWidth(), stage.getHeight());
                displayDeck.setOrigin(Align.center);
                displayDeck.align(Align.center);
                displayDeck.setBounds(displayDeck.getX(), displayDeck.getY(), stage.getWidth(), stage.getHeight()-60);

                scrollWindow.add(displayDeck);

                final ScrollPane scrollPane = new ScrollPane(scrollWindow, game.skin());
                scrollPane.setSize(stage.getWidth(), stage.getHeight());
                scrollPane.setOrigin(Align.center);

                scrollPane.addListener(new InputListener() {
                    @Override
                    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        super.enter(event, x, y, pointer, fromActor);
                        if (pointer == -1) {
                            stage.setScrollFocus(scrollPane);
                        }
                    }

                    @Override
                    public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                        super.exit(event, x, y, pointer, toActor);
                        if (pointer == -1) {
                            stage.setScrollFocus(null);
                        }
                    }
                });


                upgradeTable.add(windowTitle).row();
                upgradeTable.add(scrollPane);

                returnButton = new Button(game.skin(), "return");
                returnButton.setVisible(false);
                returnButton.setPosition(0, gameScreen.getStage().getHeight() - 870);
                returnButton.addListener(new InputListener() {
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        upgradeTable.setVisible(false);
                        fadeImage.setVisible(false);
                        restTable.setVisible(true);
                        returnButton.setVisible(false);
                        return super.touchDown(event, x, y, pointer, button);
                    }
                });

                heal.addListener(new InputListener() {
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        cardManager.getVirusManager().getPlayer().setHealth(cardManager.getVirusManager().getPlayer().getHealth() + 25);
                        if (cardManager.getVirusManager().getPlayer().getHealth() > cardManager.getVirusManager().getPlayer().getMAX_HP()) {
                            cardManager.getVirusManager().getPlayer().setHealth(cardManager.getVirusManager().getPlayer().getMAX_HP());
                        }
                        restReset(restTable);
                        return super.touchDown(event, x, y, pointer, button);
                    }
                });
                upgrade.addListener(new InputListener() {
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        upgradeTable.setVisible(true);
                        fadeImage.setVisible(false);
                        restTable.setVisible(false);
                        returnButton.setVisible(true);

                        return super.touchDown(event, x, y, pointer, button);
                    }
                });


                gameScreen.getGameScreenGroup().addActor(restTable);
                gameScreen.getGameScreenGroup().addActor(upgradeTable);
                gameScreen.getGameScreenGroup().addActor(returnButton);

                //fadeImage.setVisible(true);

        }
    }



    public void inspectCard(boolean show, final Card inspectCard) {
        fadeImage.setVisible(show);
        inspectingCard = show;
        confirmUpgrade.setVisible(show);
        if (show && inspectCard != null) {
            fadeTable.clear();
            fadeTable.setVisible(true);
            fadeTable.setBounds(0, 0, stage.getWidth(), stage.getHeight()-60);
            fadeTable.align(Align.top);
            inspectCard.setScale(0.8F);


            inspectCard.setOrigin(Align.bottomLeft);
            inspectCard.setSize(inspectCard.getWidth()*inspectCard.getScaleX(), inspectCard.getHeight()*inspectCard.getScaleY());
            inspectCard.setOrigin(Align.center);
            fadeTable.add(inspectCard).size(inspectCard.getWidth(), inspectCard.getHeight());

            fadeTable.row().pad(20);
            inspectCard.previewUpgrade(true);

            fadeTable.add(confirmUpgrade);

        } else if (!show) {
            fadeTable.clear();
        }

    }


    private void restReset(Table table) {
        endTurn.setVisible(true);
        cardManager.getHandTable().setVisible(true);
        gameScreen.getExhaustpile().setVisible(true);
        gameScreen.getDiscardpile().setVisible(true);
        gameScreen.getDrawpile().setVisible(true);
        cardManager.getVirusManager().getPlayer().setVisible(true);
        cardManager.getCompileTable().setVisible(true);
        cardManager.getVirusManager().getPlayer().getEnergyManager().setVisible(true);
        returnButton.setVisible(false);
        table.setVisible(false);

        fadeImage.setVisible(false);
        eventEnded();
    }

    public void dispose() {
        textureAtlas.dispose();
        map.dispose();
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public TurnManager getTurnManager() {
        return turnManager;
    }

    public void setTurnManager(TurnManager turnManager) {
        this.turnManager = turnManager;
    }

    public CardManager getCardManager() {
        return cardManager;
    }

    public void setCardManager(CardManager cardManager) {
        this.cardManager = cardManager;
    }

    public GipGameProject getGame() {
        return game;
    }

    public void setGame(GipGameProject game) {
        this.game = game;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Button getEndTurn() {
        return endTurn;
    }

    public void setEndTurn(Button endTurn) {
        this.endTurn = endTurn;
    }

    public SavedState getSavedState() {
        return savedState;
    }

    public void setSavedState(SavedState savedState) {
        this.savedState = savedState;
    }

    public GameScreen getGameScreen() {
        return gameScreen;
    }

    public void setGameScreen(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }
}
