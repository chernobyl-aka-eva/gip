package com.mygdx.game.cards;

import com.badlogic.gdx.utils.Array;
import com.mygdx.game.GipGameProject;

public class CardList extends Array<Card> {

    private CardManager cardManager;
    private GipGameProject game;

    private Array<Integer> greenCards = new Array<>();
    private Array<Integer> blueCards  = new Array<>();
    private Array<Integer> goldCards  = new Array<>();

    public CardList(GipGameProject game, CardManager cardManager) {
        this.game = game;
        this.cardManager = cardManager;
        float elapsed_time = 0;

        Card strike = new Card(0, "Strike", " Deal 6 Damage ", CardType.ATTACK, "green", 1, false, false, elapsed_time, game);
        add(strike); 
        Card defend = new Card(1,"Defend", " Gain 5 Block ", CardType.SKILL, "green", 1, false, false,elapsed_time, game);
        add(defend); 
        Card replicate = new Card(2, "Replicate", " Deal 4 Damage.\nENCODE.\nadd a copy\nof this card in the\nDISCARD_PILE. ", CardType.ATTACK, "green", 0,  false, false, elapsed_time, game);
        add(replicate); 
        Card gotoCard = new Card(3, "Go To", " Draw 1 card.\nENCODE.\nCOMPILE - Draw 1 card. ", CardType.SKILL, "green", 1,  false, false, elapsed_time, game);
        add(gotoCard);
        Card piercingShot = new Card(4, "Piercing Shot", " Deal 10 damage to\nall enemies.\nEXHAUST. ", CardType.ATTACK, "gold", 1, true, false, elapsed_time, game);
        add(piercingShot);
        Card fineTuning = new Card(5, "Fine Tuning", " Gain 1 DEXTERITY.\nENCODE.\nCOMPILE - gain 1\nDEXTERITY. \n \n EXHAUST. ", CardType.POWER, "green", 2, true, false, elapsed_time, game);
        add(fineTuning);
         /*6: Card branch = new Card(6, "Branch", "Deal 7 Damage\nor Gain 6 Block. \n The option not \nchosen is ENCODEd. \nEXHAUST.", CardType.ATTACK, "green", 1, true, elapsed_time, game);
             playerCards.add(branch); drawPile.add(branch); branch.setTimeAdded(playerCards.size); */
        Card bugBarrage = new Card(7, "Bug Barrage", "Gain 1 WOUND.\nCYCLE each STATUS,\ndealing 7 Damage\nfor each.", CardType.ATTACK, "green", 1,  false, false, elapsed_time, game);
        add(bugBarrage);
        Card oilSpill = new Card(8, "Oil Spill", "Deal 20 Damage.\nENCODE.\nCOMPILE error -\nINSERT a SLIMED\nEXHAUST.", CardType.ATTACK, "gold", 3,  false, false, elapsed_time, game);
        add(oilSpill);
        Card wildStrike = new Card(9, "Wild Strike", "Deal 12 Damage.\nINSERT a WOUND.", CardType.ATTACK, "green", 1,  false, false, elapsed_time, game);
        add(wildStrike);
         /*10: Card undervolt = new Card(10, "Undervolt", "All Enemies\nLose 2 STRENGTH.\nGain 2 BURNS.\nEXHAUST.", CardType.POWER, "blue", 1, true, elapsed_time, game);
             playerCards.add(undervolt); drawPile.add(undervolt); undervolt.setTimeAdded(playerCards.size); */
        Card stickyShield = new Card(11, "Sticky Shield", "Gain 11 Block.\nINSERT 1 SLIMED.", CardType.SKILL, "green", 2,  false, false, elapsed_time, game);
        add(stickyShield);
        Card burn = new Card(12, "Burn", "UNPLAYABLE.\nAt the end\nof your turn,\ntake 2 Damage.", CardType.STATUS, "", 0,  false, false, elapsed_time, game);
        add(burn);
        Card wound = new Card(13, "Wound", "UNPLAYABLE.", CardType.STATUS, "", 0,  false, false, elapsed_time, game);
        add(wound);
        Card slimed = new Card(14, "Slimed", "EXHAUST.", CardType.STATUS, "", 1, true, false, elapsed_time, game);
        add(slimed);
        Card buggyMess = new Card(15, "Buggy Mess", "INSERT a DAZED.\nGain 1 energy.\nENCODE.", CardType.SKILL, "green", 1,  false, false, elapsed_time, game);
        add(buggyMess);
         /*16: Card deprecate = new Card(16, "Deprecate", "Apply 1 WEAK.\nENCODE.", CardType.SKILL, "green", 0, false, elapsed_time, game);
             playerCards.add(deprecate); drawPile.add(deprecate); deprecate.setTimeAdded(playerCards.size); */
        Card frontload = new Card(17, "Frontload", "Gain 8 BLOCK.\nENCODE.\nCOMPILE - Function\ngains RETAIN.", CardType.SKILL,"green", 2,  false, false, elapsed_time, game);
        add(frontload);
         /*18: Card invalidate = new Card(18, "Invalidate", "Apply 1 VULNERABLE.\nENCODE", CardType.SKILL, "green", 0, false, elapsed_time, game);
             playerCards.add(invalidate); drawPile.add(invalidate); invalidate.setTimeAdded(playerCards.size); */
         /*19: Card forloop = new Card(19, "For Loop", "The next card you\nENCODE causes X\nadditional copies\nto also be ENCODED.\nEXHAUST.", CardType.SKILL, "blue", 0, true, elapsed_time, game);
             addTo(forloop); */
        Card fragment = new Card(20, "Fragment", "Deal 4 Damage.\nGain 4 Block.\nENCODE.", CardType.ATTACK, "blue", 1,  false, false, elapsed_time, game);
        add(fragment);
        Card mutator = new Card(21, "Mutator", "Gain 1 STRENGTH.\nTransform a STATUS\ninto a copy of this.", CardType.POWER, "gold", 1,  false, false, elapsed_time, game);
        add(mutator);
        Card regret = new Card(22, "Regret", "UNPLAYABLE.\nAt the end of your\nturn, lose HP equal to\nthe number of cards in\nyour hand.", CardType.CURSE, "", 0,  false, false, elapsed_time, game);
        add(regret);
        Card clumsy = new Card(23, "Clumsy", "UNPLAYABLE.\nETHEREAL.", CardType.CURSE, "", 0,  false, false, elapsed_time, game);
        add(clumsy);
        Card reboot = new Card(24, "Reboot", "Gain 2 Energy.\nEXHAUST.", CardType.SKILL, "blue", 0, true, false, elapsed_time, game);
        add(reboot);
        Card iterate = new Card(25, "Iterate", "Deal 2 Damage 3\ntimes.\nENCODE.", CardType.ATTACK, "blue", 1,  false, false, elapsed_time, game);
        add(iterate);
        Card boost = new Card(26, "Boost", "Gain 6 BLOCK.\nENCODE.\nCOMPILE - \nGain 2 STRENGTH.", CardType.SKILL, "blue", 2,  false, false, elapsed_time, game);
        add(boost);
        Card autoshields = new Card(27, "Auto-Shields", "If you have no BLOCK,\ngain 11 BLOCK.", CardType.SKILL, "blue", 1,  false, false, elapsed_time, game);
        add(autoshields);
        Card doubleenergy = new Card(28, "Double Energy", "Double your energy.\nEXHAUST.", CardType.SKILL, "gold", 1, true, false, elapsed_time, game);
        add(doubleenergy);
        Card dazed = new Card(29, "Dazed", "UNPLAYABLE.\nETHEREAL.", CardType.STATUS, "", 0, false, false, elapsed_time, game);
        dazed.setEthereal(true);
        add(dazed);
        


        generateRarities();
    }
    
    public Card getCard(int id) {
        for (Card card : this) {
            if (card.getId() == id) {
                return new Card(card.getId(), card.getTitle(), card.getDescription(), card.getCardType(), card.getRarity(), card.getCost(), card.isExhaust(), card.isUpgraded(), cardManager.getPlayerCards().size + 1, game);
            }
        }
        return null;
    }
    
    public void generateRarities() {
        for (Card card : this) {
            if (!card.getCardType().equals(CardType.CURSE) && !card.getCardType().equals(CardType.STATUS)) {
                switch (card.getRarity()) {
                    case "green" :
                        if (!cardManager.getVirusManager().getPlayer().getStartingDeck().contains(card.getId(), true)) {
                        greenCards.add(card.getId());
                        } break;
                    case "blue" : blueCards.add(card.getId()); break;
                    case "gold" : goldCards.add(card.getId()); break;
                }
            }
        }
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

    public Array<Integer> getGreenCards() {
        return greenCards;
    }

    public void setGreenCards(Array<Integer> greenCards) {
        this.greenCards = greenCards;
    }

    public Array<Integer> getBlueCards() {
        return blueCards;
    }

    public void setBlueCards(Array<Integer> blueCards) {
        this.blueCards = blueCards;
    }

    public Array<Integer> getGoldCards() {
        return goldCards;
    }

    public void setGoldCards(Array<Integer> goldCards) {
        this.goldCards = goldCards;
    }
}
