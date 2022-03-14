package com.mygdx.game.effect;

import com.mygdx.game.GipGameProject;
import com.mygdx.game.TurnManager;
import com.mygdx.game.cards.CardManager;

import java.util.ArrayList;

public class EffectManager {
    private GipGameProject game;
    private TurnManager turnManager;

    // player
    private ArrayList<Effect> effects = new ArrayList<>(); // buffs and debuffs arraylist
    // monster
    private final ArrayList<Effect> effectsEnemy = new ArrayList<>(); // buffs and debuffs arraylist

    public EffectManager(GipGameProject game, TurnManager turnManager) {
        this.game = game;
        this.turnManager = turnManager;
    }



    // =BLOCK TYPE EFFECTS=
    // Dexterity - Increases block gained from cards - Intensity
    public int dexterityBuff(int dexterity, int amount, boolean multiply) {
        if (multiply) {
            dexterity *= amount;
            return dexterity;
        } else {
            dexterity += amount;
            return dexterity;
        }
    }

    // =ATTACK TYPE EFFECTS=
    // Strength - Increases attack damage by X - Intensity
    public int strengthBuff(int strength, int amount, boolean multiply) {
        if (multiply) {
            strength *= amount;
            return strength;
        } else {
            strength += amount;
            return strength;
        }
    }

    // =OTHER TYPE EFFECTS=
    // Regen - At the end of your turn, heal X HP - Intensity
}
