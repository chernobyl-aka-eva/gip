package com.mygdx.game.cards;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.GipGameProject;
import com.mygdx.game.SessionManager;
import com.mygdx.game.cards.upgrades.GenerateCardUpgrades;
import com.mygdx.game.infobox.InfoBoxManager;
import com.mygdx.game.monster.Monster;
import com.mygdx.game.monster.MonsterManager;
import com.mygdx.game.save.SavedCard;
import com.mygdx.game.save.SavedState;
import com.mygdx.game.virus.Virus;
import com.mygdx.game.virus.VirusManager;

import java.util.Comparator;
import java.util.Random;

public class CardManager {
    private Array<Card> playerCards;
    private Table handTable;
    private Table compileTable;
    private Hand hand;
    private Array<Card> drawPile;
    private Array<Card> discardPile;
    private Array<Card> exhaustPile;
    private Array<Card> displayList = null;
    private VirusManager virusManager;
    private MonsterManager monsterManager;
    private Group tableGroup;
    private Group deckScreenGroup;

    private GipGameProject game;
    private Stage stage;
    private Group gameScreenGroup;
    private Group forGround;
    private Table fadeTable = new Table();
    private float elapsed_time;

    int totalFunctionCards = 0;

    private SessionManager sessionManager;
    private Image inspectCardBackGround;
    private SavedState savedState = null;

    //constructor
    public CardManager(GipGameProject game, Stage stage, Group gameScreenGroup, Group foreGround, SessionManager sessionManager) {
        this.game = game;
        this.stage = stage;
        this.gameScreenGroup = gameScreenGroup;
        this.forGround = foreGround;
        this.sessionManager = sessionManager;
        this.savedState = sessionManager.getSaveManager().getSavedState();
        virusManager = new VirusManager(game, stage, gameScreenGroup, savedState);
        monsterManager = new MonsterManager(game, stage, gameScreenGroup);

        playerCards = new Array<>();
        hand = new Hand(this);
        drawPile = new Array<>();
        discardPile = new Array<>();
        exhaustPile = new Array<>();

        game.skin = new Skin(Gdx.files.internal("skin/game-ui.json")); // skin

        compileTable = new Table();
        compileTable.setPosition(100, 700);
        compileTable.setSize(500, 100);
        //compileTable.debug();


        game.textureAtlas = new TextureAtlas("cards/cards-empty.atlas");
        //Image emptySlot1 = new Image(new TextureRegionDrawable(game.textureAtlas.findRegion("attack-green")));
        //Image emptySlot2 = new Image(new TextureRegionDrawable(game.textureAtlas.findRegion("attack-green")));
        //Image emptySlot3 = new Image(new TextureRegionDrawable(game.textureAtlas.findRegion("attack-green")));
        Card emptyCard = new Card(0, "empty", "", CardType.ATTACK, "green", 0, false, false, 0, game);
        Card emptySlot1 = new Card(emptyCard, compileTable, 0);
        Card emptySlot2 = new Card(emptyCard, compileTable, 1);
        Card emptySlot3 = new Card(emptyCard, compileTable, 2);
        float scale = 0.45F;

        emptySlot1.setSize(emptySlot1.getWidth()*scale/3, emptySlot1.getHeight()*scale/3);
        emptySlot2.setSize(emptySlot2.getWidth()*scale/3, emptySlot2.getHeight()*scale/3);
        emptySlot3.setSize(emptySlot3.getWidth()*scale/3, emptySlot3.getHeight()*scale/3);

        emptySlot1.addAction(Actions.alpha(0.5F));
        emptySlot2.addAction(Actions.alpha(0.5F));
        emptySlot3.addAction(Actions.alpha(0.5F));

        Card emptyFunction = new Card(0, "function(" + totalFunctionCards + ")", "", CardType.SKILL, "purple", 1, false, false, 0, game);
        Card function = new Card(emptyFunction, compileTable, 3);
        function.setSize(function.getWidth()*scale/1.5F, function.getHeight()*scale/1.5F);


        compileTable.add(emptySlot1).size(emptySlot1.getWidth(), emptySlot1.getHeight());
        compileTable.add(emptySlot2).size(emptySlot2.getWidth(), emptySlot2.getHeight());
        compileTable.add(emptySlot3).size(emptySlot3.getWidth(), emptySlot3.getHeight());
        compileTable.add(function).size(function.getWidth(), function.getHeight());






        gameScreenGroup.addActor(compileTable);

        handTable = new Table();
        handTable.setName("handTable");
        handTable.setBounds(0, 0, stage.getWidth(), 100);
        handTable.center().top().align(Align.center);
        //handTable.debugTable();
        //handTable.debugActor();
        handTable.setName("handTable");
        gameScreenGroup.addActor(handTable);

        game.textureAtlas = new TextureAtlas("other/game-ui-2.atlas");
        inspectCardBackGround = new Image(new TextureRegionDrawable(game.textureAtlas.findRegion("card-inspect-fade")));
        inspectCardBackGround.setPosition(0, -60);
        inspectCardBackGround.addAction(Actions.alpha(0.8F));
        inspectCardBackGround.setVisible(false);
        foreGround.addActor(inspectCardBackGround);
        inspectCardBackGround.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                inspectCard(false, null);
                return super.touchDown(event, x, y, pointer, button);
            }
        });
        forGround.addActor(fadeTable);

        initTables();

        new GenerateCardUpgrades(); // used to insert card upgrades to the json file

    }

    //adds card to players deck
    public void addCard(int id) {
        game.textureAtlas = new TextureAtlas("cards/cards.atlas");
        switch (id) {
            case 0: Card strike = new Card(0, "Strike", " Deal 6 Damage ", CardType.ATTACK, "green", 1, false, false, elapsed_time, game);
                addTo(strike); break;
            case 1: Card defend = new Card(1,"Defend", " Gain 5 Block ", CardType.SKILL, "green", 1, false, false,  elapsed_time, game);
                addTo(defend); break;
            case 2: Card replicate = new Card(2, "Replicate", " Deal 4 Damage.\nENCODE.\nadd a copy\nof this card in the\nDISCARD_PILE. ", CardType.ATTACK, "green", 0,  false, false,  elapsed_time, game);
                addTo(replicate); break;
            case 3: Card gotoCard = new Card(3, "Go To", " Draw 1 card.\nENCODE.\nCOMPILE - Draw 1 card. ", CardType.SKILL, "green", 1,  false, false,  elapsed_time, game);
                addTo(gotoCard); break;
            case 4: Card piercingShot = new Card(4, "Piercing Shot", " Deal 10 damage to\nall enemies.\nEXHAUST. ", CardType.ATTACK, "gold", 1, true, false, elapsed_time, game);
                addTo(piercingShot); break;
            case 5: Card fineTuning = new Card(5, "Fine Tuning", " Gain 1 DEXTERITY.\nENCODE.\nCOMPILE - gain 1\nDEXTERITY. \n \n EXHAUST. ", CardType.POWER, "green", 2, true, false, elapsed_time, game);
                addTo(fineTuning); break;
            /*case 6: Card branch = new Card(6, "Branch", "Deal 7 Damage\nor Gain 6 Block. \n The option not \nchosen is ENCODEd. \nEXHAUST.", CardType.ATTACK, "green", 1, true, elapsed_time, game);
                playerCards.add(branch); drawPile.add(branch); branch.setTimeAdded(playerCards.size); break;*/
            case 7: Card bugBarrage = new Card(7, "Bug Barrage", "Gain 1 WOUND.\nCYCLE each STATUS,\ndealing 7 Damage\nfor each.", CardType.ATTACK, "green", 1,  false, false,  elapsed_time, game);
                addTo(bugBarrage); break;
            case 8: Card oilSpill = new Card(8, "Oil Spill", "Deal 20 Damage.\nENCODE.\nCOMPILE error -\nINSERT a SLIMED\nEXHAUST.", CardType.ATTACK, "gold", 3,  false, false,  elapsed_time, game);
                addTo(oilSpill); break;
            case 9: Card wildStrike = new Card(9, "Wild Strike", "Deal 12 Damage.\nINSERT a WOUND.", CardType.ATTACK, "green", 1,  false, false,  elapsed_time, game);
                addTo(wildStrike); break;
            /*case 10: Card undervolt = new Card(10, "Undervolt", "All Enemies\nLose 2 STRENGTH.\nGain 2 BURNS.\nEXHAUST.", CardType.POWER, "blue", 1, true, elapsed_time, game);
                playerCards.add(undervolt); drawPile.add(undervolt); undervolt.setTimeAdded(playerCards.size); break;*/
            case 11: Card stickyShield = new Card(11, "Sticky Shield", "Gain 11 Block.\nINSERT 1 SLIMED.", CardType.SKILL, "green", 2,  false, false,  elapsed_time, game);
                addTo(stickyShield); break;
            case 12: Card burn = new Card(12, "Burn", "UNPLAYABLE.\nAt the end\nof your turn,\ntake 2 Damage.", CardType.STATUS, "", 0,  false, false,  elapsed_time, game);
                addTo(burn); break;
            case 13: Card wound = new Card(13, "Wound", "UNPLAYABLE.", CardType.STATUS, "", 0,  false, false,  elapsed_time, game);
                addTo(wound); break;
            case 14: Card slimed = new Card(14, "Slimed", "EXHAUST.", CardType.STATUS, "", 1, true, false, elapsed_time, game);
                addTo(slimed); break;
            case 15: Card buggyMess = new Card(15, "Buggy Mess", "INSERT a DAZED.\nGain 1 energy.\nENCODE.", CardType.SKILL, "green", 1,  false, false,  elapsed_time, game);
                addTo(buggyMess); break;
            /*case 16: Card deprecate = new Card(16, "Deprecate", "Apply 1 WEAK.\nENCODE.", CardType.SKILL, "green", 0, false, elapsed_time, game);
                playerCards.add(deprecate); drawPile.add(deprecate); deprecate.setTimeAdded(playerCards.size); break;*/
            case 17: Card frontload = new Card(17, "Frontload", "Gain 8 BLOCK.\nENCODE.\nCOMPILE - Function\ngains RETAIN.", CardType.SKILL,"green", 2,  false, false,  elapsed_time, game);
                addTo(frontload); break;
            /*case 18: Card invalidate = new Card(18, "Invalidate", "Apply 1 VULNERABLE.\nENCODE", CardType.SKILL, "green", 0, false, elapsed_time, game);
                playerCards.add(invalidate); drawPile.add(invalidate); invalidate.setTimeAdded(playerCards.size); break;*/
            /*case 19: Card forloop = new Card(19, "For Loop", "The next card you\nENCODE causes X\nadditional copies\nto also be ENCODED.\nEXHAUST.", CardType.SKILL, "blue", 0, true, elapsed_time, game);
                addTo(forloop); break;*/
            case 20: Card fragment = new Card(20, "Fragment", "Deal 4 Damage.\nGain 4 Block.\nENCODE.", CardType.ATTACK, "blue", 1,  false, false,  elapsed_time, game);
                addTo(fragment); break;
            case 21: Card mutator = new Card(21, "Mutator", "Gain 1 STRENGTH.\nTransform a STATUS\ninto a copy of this.", CardType.POWER, "gold", 1,  false, false,  elapsed_time, game);
                addTo(mutator); break;
            case 22: Card regret = new Card(22, "Regret", "UNPLAYABLE.\nAt the end of your\nturn, lose HP equal to\nthe number of cards in\nyour hand.", CardType.CURSE, "", 0,  false, false,  elapsed_time, game);
                addTo(regret); break;
            case 23: Card clumsy = new Card(23, "Clumsy", "UNPLAYABLE.\nETHEREAL.", CardType.CURSE, "", 0,  false, false,  elapsed_time, game);
                addTo(clumsy); break;
            case 24: Card reboot = new Card(24, "Reboot", "Gain 2 Energy.\nEXHAUST.", CardType.SKILL, "blue", 0, true, false, elapsed_time, game);
                addTo(reboot); break;
            case 25: Card iterate = new Card(25, "Iterate", "Deal 2 Damage 3\ntimes.\nENCODE.", CardType.ATTACK, "blue", 1,  false, false,  elapsed_time, game);
                addTo(iterate); break;
            case 26: Card boost = new Card(26, "Boost", "Gain 6 BLOCK.\nENCODE.\nCOMPILE - \nGain 2 STRENGTH.", CardType.SKILL, "blue", 2,  false, false,  elapsed_time, game);
                addTo(boost); break;
            case 27: Card autoshields = new Card(27, "Auto-Shields", "If you have no BLOCK,\ngain 11 BLOCK.", CardType.SKILL, "blue", 1,  false, false,  elapsed_time, game);
                addTo(autoshields); break;
            case 28: Card doubleenergy = new Card(28, "Double Energy", "Double your energy.\nEXHAUST.", CardType.SKILL, "gold", 0, true, false, elapsed_time, game);
                addTo(doubleenergy); break;

        }
    }

    public void addTo(Card card) {
        playerCards.add(card);
        drawPile.add(card);
        card.setTimeAdded(playerCards.size);
        refreshDisplayTable(0);
        refreshDisplayTable(1);
    }

    public void loadCards() {
        for (SavedCard playerCard : savedState.getSavedCards()) {
            Card card = new Card(
                    playerCard.getId(),
                    playerCard.getTitle(),
                    playerCard.getDescriptionText(),
                    playerCard.getCardType(),
                    playerCard.getRarity(),
                    playerCard.getCost(),
                    playerCard.isExhaust(),
                    playerCard.isUpgraded(),
                    elapsed_time,
                    game);
            playerCards.add(card);
        }
        for (SavedCard drawCard : savedState.getDrawCards()) {
            Card card = new Card(
                    drawCard.getId(),
                    drawCard.getTitle(),
                    drawCard.getDescriptionText(),
                    drawCard.getCardType(),
                    drawCard.getRarity(),
                    drawCard.getCost(),
                    drawCard.isExhaust(),
                    drawCard.isUpgraded(),
                    elapsed_time,
                    game);
            drawPile.add(card);
        }
        for (SavedCard handCard : savedState.getHandCards()) {
            Card card = new Card(
                    handCard.getId(),
                    handCard.getTitle(),
                    handCard.getDescriptionText(),
                    handCard.getCardType(),
                    handCard.getRarity(),
                    handCard.getCost(),
                    handCard.isExhaust(),
                    handCard.isUpgraded(),
                    elapsed_time,
                    game);
            hand.add(card);
        }
        for (SavedCard discardedCard : savedState.getDiscardedCards()) {
            Card card = new Card(
                    discardedCard.getId(),
                    discardedCard.getTitle(),
                    discardedCard.getDescriptionText(),
                    discardedCard.getCardType(),
                    discardedCard.getRarity(),
                    discardedCard.getCost(),
                    discardedCard.isExhaust(),
                    discardedCard.isUpgraded(),
                    elapsed_time,
                    game);
            discardPile.add(card);
        }
        for (SavedCard exhaustedCard : savedState.getExhaustedCards()) {
            Card card = new Card(
                    exhaustedCard.getId(),
                    exhaustedCard.getTitle(),
                    exhaustedCard.getDescriptionText(),
                    exhaustedCard.getCardType(),
                    exhaustedCard.getRarity(),
                    exhaustedCard.getCost(),
                    exhaustedCard.isExhaust(),
                    exhaustedCard.isUpgraded(),
                    elapsed_time,
                    game);
            exhaustPile.add(card);
        }
        refreshDisplayTable(0);
        refreshDisplayTable(1);
        refreshDisplayTable(2);
        refreshDisplayTable(3);
        hand.refreshHand();
    }

    //method for playing cards (I'm gonna rewrite this 100%)
    public void playCard(Card card, Virus player, Monster monster) {
        int damage;
        boolean encode = false;
        if (virusManager.getPlayer().getEnergyManager().getEnergy() != 0 || card.getCost() < 1){
            if (!card.isFunctionCard()) {
                switch (card.getId()) {
                    case 0: // Strike
                        damage = 6; // + multiplier (later)
                        playAttackCard(damage, monster);
                        break;
                    case 1: // Defend
                        int stack = player.getEffectManager().getStack(0); // id 0 --> dexterity
                        player.setBlock(player.getBlock() + stack + 6);
                        virusManager.getPlayer().getEnergyManager().setEnergy(virusManager.getPlayer().getEnergyManager().getEnergy()
                                - card.getCost());
                        ;
                        break;
                    case 2: // Replicate
                        damage = 4;
                        playAttackCard(damage, monster);
                        encode = true;
                        Card replicate = new Card(2, "Replicate", " Deal 4 Damage.\nENCODE.\nadd a copy\nof this card in the\nDISCARD_PILE. ", CardType.ATTACK, "green", 0,  false, false,  elapsed_time, game);
                        discardPile.add(replicate);
                        refreshDisplayTable(2);
                        // encode
                        break;
                    case 3:
                        drawcard(1);
                        encode = true;
                        // encode
                        break;
                    case 4:
                        damage = 10; // + multiplier (later)
                        for (int m = 0; m < monsterManager.getMonsterGroup().getChildren().size; m++) {
                            Actor actor = monsterManager.getMonsterGroup().getChild(m);
                            if (actor instanceof Monster) {
                                Monster monsterActor = (Monster) actor;
                                playAttackCard(damage, monsterActor);
                            }
                        }
                        ;
                        break;
                    case 5:
                        game.log.debug("case 5");
                        //adding dexterity
                        player.addEffect(0); //id 0 --> Dexterity | See EffectManager for ids
                        encode = true;
                        break;
                    case 6:
                    /*damage = 7;
                    // branch
                    playAttackCard(damage, monster);*/
                        break;
                    case 7:
                        Card wound = new Card(13, "Wound", "UNPLAYABLE.", CardType.STATUS, "", 0, false, false, elapsed_time, game);
                        hand.add(wound);
                        for (int c = 0; c < hand.size; c++) {
                            if (hand.get(c).getCardType().equals(CardType.STATUS)){
                                System.out.println("before hit " + c + " " + monster.getHealth());
                                monster.setHealth(monster.getHealth() - 7);
                                System.out.println("after hit " + c + " " + monster.getHealth());
                            }
                        }

                        hand.refreshHand();
                        break;
                    case 8:
                }
            } else {
                for (Integer compiledCardsId : card.getCompiledCardsIds()) {
                    switch (compiledCardsId) {
                        case 2:
                            damage = 4;
                            playAttackCard(damage, monster);
                            break;
                        case 3:
                            drawcard(1);
                            break;
                        case 5:
                            player.addEffect(0);
                            break;
                    }
                }
            }
            for (Card playerCard : playerCards) {
                System.out.println("Id: " + playerCard.getId() + " Encode? " + playerCard.getDescription().contains("ENCODE"));
            }
            if (encode) {
                encode(card);
            }

            // is enemy ded?
            if (monster!=null) {
                if (monster.getHealth() <= 0){
                    monster.remove();
                    monsterManager.setIntentVisible(false);
                    sessionManager.eventEnded();
                }
            }


            if (!card.isExhaust()){
                discardPile.add(card);
                refreshDisplayTable(2);
            }else{
                exhaustPile.add(card);
                refreshDisplayTable(3);
            }


        }
    }

    public void playAttackCard(int damage, Monster monster){
        if (monster.getblock() > 0) { // if the enemy has block
            game.log.debug("block before damage : " + monster.getBlock());
            if (monster.getblock() >= damage) {
                monster.setblock(monster.getblock()-damage);
            } else { // else if the enemy can't block fully but can block some...
                int damageAfterBlock;
                int damageBeforeBlock;
                damageBeforeBlock = damage - monster.getblock();
                game.log.debug("damage : " + damage);
                monster.setblock(monster.getblock()-damageBeforeBlock);
                damageAfterBlock = damage - damageBeforeBlock;
                game.log.debug("damage after block : " + damageAfterBlock);
                game.log.debug("health before damage : " + monster.getHealth());
                monster.setHealth(monster.getHealth() - damageAfterBlock);
                game.log.debug("health after damage : " + monster.getHealth());
            }
        } else { // if the enemy doesn't have block
            game.log.debug("health before damage : " + monster.getHealth());
            monster.setHealth(monster.getHealth() - damage);
            game.log.debug("health after damage : " + monster.getHealth());
        }
    }

    private void encode(Card card) {

        System.out.println("");
        float scale = 0.45F;
        CardType swapBackGround = null;
        for (int i = 0; i < compileTable.getCells().size; i++) {
            Cell cell = compileTable.getCells().get(i);
            if (cell.getActor() instanceof Card) {
                Card encodedCard = (Card) cell.getActor();
                if (encodedCard.getTitle().equalsIgnoreCase("empty")) {
                    if (i == 0) {
                        swapBackGround = card.getCardType();
                    }
                    Card sequenceCard = new Card(card, compileTable, encodedCard.getSequenceIndex());
                    sequenceCard.setSize(sequenceCard.getWidth()*scale/3, sequenceCard.getHeight()*scale/3);
                    compileTable.getCell(encodedCard).setActor(sequenceCard);
                    break;
                }
            }
        }

        boolean fullSequence = true;
        Card functionCard = null;
        for (Cell cell : compileTable.getCells()) {
            if (cell.getActor() instanceof Card) {
                Card encodedCard = (Card) cell.getActor();
                if (encodedCard.getTitle().equalsIgnoreCase("empty")) {
                    fullSequence = false;
                } else if (encodedCard.getTitle().contains("function")) {
                    encodedCard.generateCompileCard(card);
                    functionCard = encodedCard;
                    functionCard.changeBackground(swapBackGround);
                }
            }
        }
        if (fullSequence) {

            functionCard.setSequenceCard(false);
            functionCard.generateTextTable();
            functionCard.initCard();

            totalFunctionCards++;
            Card emptyFunction = new Card(0, "function(" + totalFunctionCards + ")", "", CardType.SKILL, "purple", 1, false, false, 0,  game);
            Card function = new Card(emptyFunction, compileTable, 3);
            function.setSize(function.getWidth()*scale/1.5F, function.getHeight()*scale/1.5F);

            compileTable.getCell(functionCard).setActor(function);

            Card emptyCard = new Card(0, "empty", "", CardType.ATTACK, "green", 0, false, false, 0, game);
            for (Cell cell : compileTable.getCells()) {
                if (cell.getActor() instanceof Card) {
                    Card encodedCard = (Card) cell.getActor();
                    if (!encodedCard.getTitle().contains("function")) {
                        Card emptySlot = new Card(emptyCard, compileTable, 0);
                        emptySlot.setSize(emptySlot.getWidth() * scale / 3, emptySlot.getHeight() * scale / 3);
                        emptySlot.addAction(Actions.alpha(0.5F));
                        compileTable.getCell(encodedCard).setActor(emptySlot);
                    }
                }
            }

            hand.add(functionCard);
            hand.refreshHand();


            for (Integer compiledCardsId : card.getCompiledCardsIds()) {
                switch (compiledCardsId) {
                    case 3: drawcard(1); break;
                    case 5: virusManager.getPlayer().addEffect(0); break;
                }
            }

        }



    }

    //draws card in hand (later called by turnManager)
    public void drawcard(int amount) {
        Random random = new Random();
        //System.out.println(drawPile.size);
        for (int i = 0; i < amount; i++) {
            if (hand.size < 10) {
                if (drawPile.size <= 0) {
                    for (Card card : discardPile) {
                        drawPile.add(card);
                    }
                    discardPile.clear();
                }
                System.out.println(drawPile.size);
                int randomIndex = random.nextInt(drawPile.size);
                hand.add(drawPile.get(randomIndex));
                drawPile.removeIndex(randomIndex);
            }
        }
        refreshDisplayTable(1);
        refreshDisplayTable(2);
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

    public void resetHand() {
        hand.clear();
        drawPile.clear();
        discardPile.clear();
        exhaustPile.clear();

        drawPile = new Array<Card>(playerCards);
        drawcard(virusManager.getPlayer().getAmountToDraw());

        refreshDisplayTable(1);
        refreshDisplayTable(2);
        refreshDisplayTable(3);
        hand.refreshHand();
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

                for (Actor actor: monsterManager.getMonsterGroup().getChildren().items) {
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

    public void refreshDisplayTable(int type) {
        try {
            tableGroup.removeActorAt(type, false);
            tableGroup.addActorAt(type, displayCards(type));
        } catch (IndexOutOfBoundsException e) {
            if (tableGroup.getChildren().size == type) {
                tableGroup.addActor(displayCards(type));
            }
        }

    }

    public void initTables() {
        game.skin = new Skin(Gdx.files.internal("skin/game-ui.json"));
        game.skin.addRegions(new TextureAtlas("skin/game-ui.atlas"));


        deckScreenGroup = new Group();
        tableGroup = new Group();
        for (int i = 0; i < 4; i++) {
            refreshDisplayTable(i);
        }


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
                tableGroup.getChild(3).setVisible(false);
                //tableGroup.getChild(3).setVisible(false);
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
        //table.debugActor();

        displayList = new Array<>();
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
            case 3:
                title = "Exhaust pile";
                displayList = exhaustPile; break;
        }
        Table scollWindow = new Table(game.skin);

        if (displayList == playerCards) {
            scollWindow = getDeckSortWindow(scollWindow);
        }
        Table displayDeck = getDisplayDeck();

        scollWindow.add(displayDeck);

        final ScrollPane scrollPane = new ScrollPane(scollWindow, game.skin);
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


        table.add(title).row();
        table.add(scrollPane);

        return table;
    }


    private Table getDisplayDeck() {
        Table displayDeck = new Table();
        displayDeck.center().top().align(Align.center).clip(true);
        //displayDeck.debugActor();


        displayDeck.add().padTop(70F).row();
        InfoBoxManager infoBoxManager = new InfoBoxManager(game, stage, displayDeck);
        for (int i = 1; i < displayList.size+1; i++) {
            //creates copy of card to render in deckscreen (--> uses copy card constructor deckscreen)
            final Card cardCopy = new Card(displayList.get(i-1), true, false);
            cardCopy.setScale(0.6F);
            Card previousCard = null;


            //cardCopy.debug();
            cardCopy.setOrigin(Align.bottomLeft);
            cardCopy.setSize(cardCopy.getWidth()*cardCopy.getScaleX(), cardCopy.getHeight()*cardCopy.getScaleY());
            cardCopy.setOrigin(Align.center);
            int amountPerRow = 5;
            float padWidth = (float) ((stage.getWidth()-(cardCopy.getWidth()*amountPerRow))/5+53);
            float padHeight = 0 - cardCopy.getHeight()/5;
            if (displayList == playerCards) {
                cardCopy.addListener(new ClickListener() {
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        Card inspectCard = new Card(cardCopy, false, true);
                        CardManager.this.inspectCard(true, inspectCard);
                        return super.touchDown(event, x, y, pointer, button);
                    }
                });
            }


            displayDeck.add(cardCopy).size(cardCopy.getWidth(), cardCopy.getHeight()).colspan(amountPerRow).padRight(padWidth).padLeft(padWidth).padTop(padHeight).padBottom(padHeight).center();
            cardCopy.toBack();

            Table infoBoxTable = infoBoxManager.getInfoBoxTable(cardCopy);

            displayDeck.add(infoBoxTable).size(0,0).padLeft(-100);
            infoBoxTable.toFront();


            if (i % amountPerRow == 0) {
                displayDeck.add().row();
            }


        }

        displayDeck.add().padBottom(100F).row();

        displayDeck.setSize(stage.getWidth(), stage.getHeight());
        displayDeck.setOrigin(Align.center);
        displayDeck.align(Align.center);
        displayDeck.setBounds(displayDeck.getX(), displayDeck.getY(), stage.getWidth(), stage.getHeight()-60);
        return displayDeck;
    }

    public void inspectCard(boolean show, final Card inspectCard) {
        inspectCardBackGround.setVisible(show);

        if (show && inspectCard != null) {
            fadeTable.clear();
            fadeTable.setBounds(0, 0, stage.getWidth(), stage.getHeight()-60);
            //fadeTable.debug();
            fadeTable.align(Align.top);
            inspectCard.setScale(0.8F);
            Card previousCard = null;

            inspectCard.previewUpgrade(inspectCard.isUpgraded());

            //cardCopy.debug();
            inspectCard.setOrigin(Align.bottomLeft);
            inspectCard.setSize(inspectCard.getWidth()*inspectCard.getScaleX(), inspectCard.getHeight()*inspectCard.getScaleY());
            inspectCard.setOrigin(Align.center);
            fadeTable.add(inspectCard).size(inspectCard.getWidth(), inspectCard.getHeight());

            fadeTable.row();

            //Label previewUpgrade = new Label("Preview upgrade", game.skin);
            //fadeTable.add(previewUpgrade);

            game.skin = new Skin(Gdx.files.internal("skin/settings-ui.json"));
            //game.skin.addRegions(new TextureAtlas("skin/settings-ui.json"));
            final CheckBox checkBox = new CheckBox(" Preview upgrade", game.skin);
            checkBox.addListener(new EventListener() {
                @Override
                public boolean handle(Event event) {
                    boolean enabled = checkBox.isChecked();
                    inspectCard.previewUpgrade(enabled);
                    return false;
                }
            });
            checkBox.setChecked(inspectCard.isUpgraded());
            fadeTable.add(checkBox).size(checkBox.getWidth(), checkBox.getHeight());



            game.skin = new Skin(Gdx.files.internal("skin/game-ui.json"));
            game.skin.addRegions(new TextureAtlas("skin/game-ui.atlas"));
        } else if (!show) {
            fadeTable.clear();
        }

    }

    private Table getDeckSortWindow(Table scollWindow) {
        scollWindow.add().padTop(40F).row();
        final Window deckSortBackground = new Window( "",game.skin, "deck-sort-background");
        deckSortBackground.align(Align.center);
        deckSortBackground.setSize(1770, 80);

        Label sort = new Label("Sort:", game.skin);

        GlyphLayout glyphord = new GlyphLayout(game.font, sort.getText());
        deckSortBackground.add(sort).size(glyphord.width).pad(150);

        final TextButton sortObtained = new TextButton("Obtained", game.skin, "deck-sorting");
        sortObtained.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (displayList.size == 0) {
                    refreshDisplayTable(0);
                    tableGroup.getChild(0).setVisible(true);
                }
                displayList.sort(new Comparator<Card>() {
                    @Override
                    public int compare(Card o1, Card o2) {
                        return Float.compare(o1.getTimeAdded(), o2.getTimeAdded());
                    }
                });
                refreshDisplayTable(0);
                tableGroup.getChild(0).setVisible(true);

                return super.touchDown(event, x, y, pointer, button);
            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                super.enter(event, x, y, pointer, fromActor);
                if (pointer == -1) {sortObtained.setText("> Obtained");}
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                super.exit(event, x, y, pointer, toActor);
                if (pointer == -1) {sortObtained.setText("Obtained");}
            }
        });
        deckSortBackground.add(sortObtained).pad(150).size(sortObtained.getWidth(), sortObtained.getHeight());


        final TextButton sortType = new TextButton("Type", game.skin, "deck-sorting");
        sortType.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (displayList.size == 0) {
                    refreshDisplayTable(0);
                    tableGroup.getChild(0).setVisible(true);
                }
                displayList.sort(new Comparator<Card>() {

                    @Override
                    public int compare(Card o1, Card o2) {
                        int card1TypeValue = getCardTypeValue(o1.getCardType());
                        int card2TypeValue = getCardTypeValue(o2.getCardType());

                        int typeCompare = Integer.compare(card1TypeValue, card2TypeValue);
                        int nameCompare = o1.getTitle().compareTo(o2.getTitle());

                        return (nameCompare == 0) ? nameCompare : typeCompare;


                    }

                    private int getCardTypeValue(CardType cardType) {
                        int cardTypeValue = 0;
                        switch (cardType) {
                            case ATTACK: break;
                            case SKILL:  cardTypeValue = 1; break;
                            case POWER:  cardTypeValue = 2; break;
                            case STATUS: cardTypeValue = 3; break;
                        }

                        return cardTypeValue;
                    }
                });
                refreshDisplayTable(0);
                tableGroup.getChild(0).setVisible(true);
                return super.touchDown(event, x, y, pointer, button);
            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                super.enter(event, x, y, pointer, fromActor);
                if (pointer == -1) {sortType.setText("> Type");}
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                super.exit(event, x, y, pointer, toActor);
                if (pointer == -1) {sortType.setText("Type");}
            }
        });
        deckSortBackground.add(sortType).pad(150).size(sortType.getWidth(), sortType.getHeight());


        final TextButton sortCost = new TextButton("Cost", game.skin, "deck-sorting");
        sortCost.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (displayList.size == 0) {
                    refreshDisplayTable(0);
                    tableGroup.getChild(0).setVisible(true);
                }
                displayList.sort(new Comparator<Card>() {
                    @Override
                    public int compare(Card o1, Card o2) {
                        int costCompare = Integer.compare(o1.getCost(), o2.getCost());
                        int nameCompare = o1.getTitle().compareTo(o2.getTitle());

                        return (nameCompare == 0) ? nameCompare : costCompare;
                    }
                });
                refreshDisplayTable(0);
                tableGroup.getChild(0).setVisible(true);
                return super.touchDown(event, x, y, pointer, button);
            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                super.enter(event, x, y, pointer, fromActor);
                if (pointer == -1) {sortCost.setText("> Cost");}
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                super.exit(event, x, y, pointer, toActor);
                if (pointer == -1) {sortCost.setText("Cost");}
            }
        });
        deckSortBackground.add(sortCost).pad(150).size(sortCost.getWidth(), sortCost.getHeight());


        final TextButton sortAZ = new TextButton("A-Z", game.skin, "deck-sorting");
        sortAZ.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (displayList.size == 0) {
                    refreshDisplayTable(0);
                    tableGroup.getChild(0).setVisible(true);
                }
                displayList.sort(new Comparator<Card>() {
                    @Override
                    public int compare(Card o1, Card o2) {
                        return o1.getTitle().compareTo(o2.getTitle());
                    }
                });
                refreshDisplayTable(0);
                tableGroup.getChild(0).setVisible(true);
                return super.touchDown(event, x, y, pointer, button);
            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                super.enter(event, x, y, pointer, fromActor);
                if (pointer == -1) {sortAZ.setText("> A-Z");}
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                super.exit(event, x, y, pointer, toActor);
                if (pointer == -1) {sortAZ.setText("A-Z");}
            }
        });
        deckSortBackground.add(sortAZ).pad(150).size(sortAZ.getWidth(), sortAZ.getHeight());


        scollWindow.add(deckSortBackground).size(1770, 80).top().align(Align.top).row();
        scollWindow.add().row();

        return scollWindow;
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
