package com.mygdx.game.screen.settings;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class KeyboardController {
    private final String KEY_CONFIRM_CARD = "keyConfirmCard";
    private final String KEY_CANCEL_EXIT = "keyCancelExit";
    private final String KEY_MAP = "keyMap";
    private final String KEY_VIEW_DECK = "keyViewDeck";
    private final String KEY_DRAW_PILE = "keyDrawPile";
    private final String KEY_DISCARD_PILE = "keyDiscardPile";
    private final String KEY_EXHAUST_PILE = "keyExhaustPile";
    private final String KEY_END_TURN = "keyEndTurn";
    private final String KEY_PEEK = "keyPeek";
    private final String KEY_UP = "keyUp";
    private final String KEY_DOWN = "keyDown";
    private final String KEY_LEFT = "keyLeft";
    private final String KEY_RIGHT = "keyRight";
    private final String KEY_SELECT_CARD_1 = "keyCard1";
    private final String KEY_SELECT_CARD_2 = "keyCard2";
    private final String KEY_SELECT_CARD_3 = "keyCard3";
    private final String KEY_SELECT_CARD_4 = "keyCard4";
    private final String KEY_SELECT_CARD_5 = "keyCard5";
    private final String KEY_SELECT_CARD_6 = "keyCard6";
    private final String KEY_SELECT_CARD_7 = "keyCard7";
    private final String KEY_SELECT_CARD_8 = "keyCard8";
    private final String KEY_SELECT_CARD_9 = "keyCard9";
    private final String KEY_SELECT_CARD_0 = "keyCard0";
    private final String KEY_RELEASE_CARD = "keyReleaseCard";

    private Preferences preferences;

    public KeyboardController() {
    }

    protected Preferences getSettings(){
        if (preferences == null) {
            String SETT_NAME = "keyboardController.settings";
            preferences = Gdx.app.getPreferences(SETT_NAME);
        }
        return preferences;
    }

    public int getKey(int keyId) {
        int key = 0;
        switch (keyId) {
            case 0: key = getKeyConfirmCard();break;
            case 1: key = getKeyCancelExit();break;
            case 2: key = getKeyMap();break;
            case 3: key = getKeyViewDeck();break;
            case 4: key = getKeyDrawPile();break;
            case 5: key = getKeyDiscardPile();break;
            case 6: key = getKeyExhaustPile();break;
            case 7: key = getKeyEndTurn();break;
            case 8: key = getKeyPeek();break;
            case 9: key = getKeyUp();break;
            case 10: key = getKeyDown();break;
            case 11: key = getKeyLeft();break;
            case 12: key = getKeyRight();break;
            case 13: key = getKeyCard1();break;
            case 14: key = getKeyCard2();break;
            case 15: key = getKeyCard3();break;
            case 16: key = getKeyCard4();break;
            case 17: key = getKeyCard5();break;
            case 18: key = getKeyCard6();break;
            case 19: key = getKeyCard7();break;
            case 20: key = getKeyCard8();break;
            case 21: key = getKeyCard9();break;
            case 22: key = getKeyCard0();break;
            case 23: key = getKeyReleaseCard();break;
        }
        return key;
    }

    public void changeKey(int keyId, int keyCode){
        switch (keyId){
            case 0: changeKeyConfirmCard(keyCode);break;
            case 1: changeKeyCancelExit(keyCode);break;
            case 2: changeKeyMap(keyCode);break;
            case 3: changeKeyViewDeck(keyCode);break;
            case 4: changeKeyDrawPile(keyCode);break;
            case 5: changeKeyDiscardPile(keyCode);break;
            case 6: changeKeyExhaustPile(keyCode);break;
            case 7: changeKeyEndTurn(keyCode);break;
            case 8: changeKeyPeek(keyCode);break;
            case 9: changeKeyUp(keyCode);break;
            case 10: changeKeyDown(keyCode);break;
            case 11: changeKeyLeft(keyCode);break;
            case 12: changeKeyRight(keyCode);break;
            case 13: changeKeyCard1(keyCode);break;
            case 14: changeKeyCard2(keyCode);break;
            case 15: changeKeyCard3(keyCode);break;
            case 16: changeKeyCard4(keyCode);break;
            case 17: changeKeyCard5(keyCode);break;
            case 18: changeKeyCard6(keyCode);break;
            case 19: changeKeyCard7(keyCode);break;
            case 20: changeKeyCard8(keyCode);break;
            case 21: changeKeyCard9(keyCode);break;
            case 22: changeKeyCard0(keyCode);break;
            case 23: changeKeyReleaseCard(keyCode);break;
        }
    }


    //Als een gebruiker wil ik de input voor “Confirm Card” kunnen veranderen zodat ik mijn gekozen kaart kan bevestigen.
    public void changeKeyConfirmCard(int key) {
        getSettings().putInteger(KEY_CONFIRM_CARD, key);
        getSettings().flush();
    }

    public int getKeyConfirmCard() {
        return getSettings().getInteger(KEY_CONFIRM_CARD);
    }

    //Als een gebruiker wil ik de input voor “Cancel/Exit” kunnen veranderen zodat ik mijn actie kan annuleren.
    public void changeKeyCancelExit(int key) {
        getSettings().putInteger(KEY_CANCEL_EXIT, key);
        getSettings().flush();
    }

    public int getKeyCancelExit() {
        return getSettings().getInteger(KEY_CANCEL_EXIT);
    }

    //Als een gebruiker wil ik de input voor “Map” kunnen veranderen zodat ik de kaart te zien krijg.
    public void changeKeyMap(int key) {
        getSettings().putInteger(KEY_MAP, key);
        getSettings().flush();
    }

    public int getKeyMap() {
        return getSettings().getInteger(KEY_MAP);
    }

    //Als een gebruiker wil ik de input voor “View Deck” kunnen veranderen zodat ik al mijn kaarten kan zien.
    public void changeKeyViewDeck(int key) {
        getSettings().putInteger(KEY_VIEW_DECK, key);
        getSettings().flush();
    }

    public int getKeyViewDeck() {
        return getSettings().getInteger(KEY_VIEW_DECK);
    }

    //Als een gebruiker wil ik de input voor “Draw Pile” kunnen veranderen zodat ik mijn trekstapel kan zien.
    public void changeKeyDrawPile(int key) {
        getSettings().putInteger(KEY_DRAW_PILE, key);
        getSettings().flush();
    }

    public int getKeyDrawPile() {
        return getSettings().getInteger(KEY_DRAW_PILE);
    }

    //Als een gebruiker wil ik de input voor “Discard Pile” kunnen veranderen zodat ik mijn aflegstapel kan zien.
    public void changeKeyDiscardPile(int key) {
        getSettings().putInteger(KEY_DISCARD_PILE, key);
        getSettings().flush();
    }

    public int getKeyDiscardPile() {
        return getSettings().getInteger(KEY_DISCARD_PILE);
    }

    //Als een gebruiker wil ik de input voor “Exhaust Pile” kunnen veranderen zodat ik mijn uitput stapel kan zien.
    public void changeKeyExhaustPile(int key) {
        getSettings().putInteger(KEY_EXHAUST_PILE, key);
        getSettings().flush();
    }

    public int getKeyExhaustPile() {
        return getSettings().getInteger(KEY_EXHAUST_PILE);
    }

    //Als een gebruiker wil ik de input voor “End Turn” kunnen veranderen zodat ik mijn beurt kan beëindigen.
    public void changeKeyEndTurn(int key) {
        getSettings().putInteger(KEY_END_TURN, key);
        getSettings().flush();
    }

    public int getKeyEndTurn() {
        return getSettings().getInteger(KEY_END_TURN);
    }

    //Als een gebruiker wil ik de input voor “Peek” kunnen veranderen zodat ik een kaart kan bekijken.
    public void changeKeyPeek(int key) {
        getSettings().putInteger(KEY_PEEK, key);
        getSettings().flush();
    }

    public int getKeyPeek() {
        return getSettings().getInteger(KEY_PEEK);
    }

    //Als een gebruiker wil ik de input voor “Up” kunnen veranderen.
    public void changeKeyUp(int key) {
        getSettings().putInteger(KEY_UP, key);
        getSettings().flush();
    }

    public int getKeyUp() {
        return getSettings().getInteger(KEY_UP);
    }

    //Als een gebruiker wil ik de input voor “Down” kunnen veranderen.
    public void changeKeyDown(int key) {
        getSettings().putInteger(KEY_DOWN, key);
        getSettings().flush();
    }

    public int getKeyDown() {
        return getSettings().getInteger(KEY_DOWN);
    }

    //Als een gebruiker wil ik de input voor “Left” kunnen veranderen.
    public void changeKeyLeft(int key) {
        getSettings().putInteger(KEY_LEFT, key);
        getSettings().flush();
    }

    public int getKeyLeft() {
        return getSettings().getInteger(KEY_LEFT);
    }

    //Als een gebruiker wil ik de input voor “Right” kunnen veranderen.
    public void changeKeyRight(int key) {
        getSettings().putInteger(KEY_RIGHT, key);
        getSettings().flush();
    }

    public int getKeyRight() {
        return getSettings().getInteger(KEY_RIGHT);
    }

    //Als een gebruiker wil ik de input voor “Select Card #1” kunnen veranderen zodat ik kaart 1 kan kiezen.
    public void changeKeyCard1(int key) {
        getSettings().putInteger(KEY_SELECT_CARD_1, key);
        getSettings().flush();
    }

    public int getKeyCard1() {
        return getSettings().getInteger(KEY_SELECT_CARD_1);
    }

    //Als een gebruiker wil ik de input voor “Select Card #2” kunnen veranderen zodat ik kaart 2 kan kiezen.
    public void changeKeyCard2(int key) {
        getSettings().putInteger(KEY_SELECT_CARD_2, key);
        getSettings().flush();
    }

    public int getKeyCard2() {
        return getSettings().getInteger(KEY_SELECT_CARD_2);
    }

    //Als een gebruiker wil ik de input voor “Select Card #3” kunnen veranderen zodat ik kaart 3 kan kiezen.
    public void changeKeyCard3(int key) {
        getSettings().putInteger(KEY_SELECT_CARD_3, key);
        getSettings().flush();
    }

    public int getKeyCard3() {
        return getSettings().getInteger(KEY_SELECT_CARD_3);
    }

    //Als een gebruiker wil ik de input voor “Select Card #4” kunnen veranderen zodat ik kaart 4 kan kiezen.
    public void changeKeyCard4(int key) {
        getSettings().putInteger(KEY_SELECT_CARD_4, key);
        getSettings().flush();
    }

    public int getKeyCard4() {
        return getSettings().getInteger(KEY_SELECT_CARD_4);
    }

    //Als een gebruiker wil ik de input voor “Select Card #5” kunnen veranderen zodat ik kaart 5 kan kiezen.
    public void changeKeyCard5(int key) {
        getSettings().putInteger(KEY_SELECT_CARD_5, key);
        getSettings().flush();
    }

    public int getKeyCard5() {
        return getSettings().getInteger(KEY_SELECT_CARD_5);
    }

    //Als een gebruiker wil ik de input voor “Select Card #6” kunnen veranderen zodat ik kaart 6 kan kiezen.
    public void changeKeyCard6(int key) {
        getSettings().putInteger(KEY_SELECT_CARD_6, key);
        getSettings().flush();
    }

    public int getKeyCard6() {
        return getSettings().getInteger(KEY_SELECT_CARD_6);
    }

    //Als een gebruiker wil ik de input voor “Select Card #7” kunnen veranderen zodat ik kaart 7 kan kiezen.
    public void changeKeyCard7(int key) {
        getSettings().putInteger(KEY_SELECT_CARD_7, key);
        getSettings().flush();
    }

    public int getKeyCard7() {
        return getSettings().getInteger(KEY_SELECT_CARD_7);
    }

    //Als een gebruiker wil ik de input voor “Select Card #8” kunnen veranderen zodat ik kaart 8 kan kiezen.
    public void changeKeyCard8(int key) {
        getSettings().putInteger(KEY_SELECT_CARD_8, key);
        getSettings().flush();
    }

    public int getKeyCard8() {
        return getSettings().getInteger(KEY_SELECT_CARD_8);
    }

    //Als een gebruiker wil ik de input voor “Select Card #9” kunnen veranderen zodat ik kaart 9 kan kiezen.
    public void changeKeyCard9(int key) {
        getSettings().putInteger(KEY_SELECT_CARD_9, key);
        getSettings().flush();
    }

    public int getKeyCard9() {
        return getSettings().getInteger(KEY_SELECT_CARD_9);
    }

    //Als een gebruiker wil ik de input voor “Select Card #0” kunnen veranderen zodat ik kaart 0 kan kiezen.
    public void changeKeyCard0(int key) {
        getSettings().putInteger(KEY_SELECT_CARD_0, key);
        getSettings().flush();
    }

    public int getKeyCard0() {
        return getSettings().getInteger(KEY_SELECT_CARD_0);
    }

    //Als een gebruiker wil ik de input voor “Release Card” kunnen veranderen zodat ik een kaart los kan laten.
    public void changeKeyReleaseCard(int key) {
        getSettings().putInteger(KEY_RELEASE_CARD, key);
        getSettings().flush();
    }

    public int getKeyReleaseCard() {
        return getSettings().getInteger(KEY_RELEASE_CARD);
    }


}
