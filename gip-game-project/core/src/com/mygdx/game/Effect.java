package com.mygdx.game;

public class Effect {
    //   NAME ------------------- EFFECT ------------------- STACKS?
    private String effectName;
    private int counter;
    private int intensity;
    private int duration;

    public Effect(String effectName, int counter, int intensity, int duration) {
        this.effectName = effectName;
        this.counter = counter;
        this.intensity = intensity;
        this.duration = duration;
    }

    public String getEffectName() {
        return effectName;
    }

    public void setEffectName(String effectName) {
        this.effectName = effectName;
    }

    // =BLOCK TYPE EFFECTS=
    // Dexterity - Increases block gained from cards - Intensity
    public int dexterityBuff(int dexterity, int amount, boolean multiply){
        if (multiply){
            dexterity *= amount;
            return dexterity;
        }else{
            dexterity += amount;
            return dexterity;
        }
    }
    // =ATTACK TYPE EFFECTS=
    // Strength - Increases attack damage by X - Intensity
    public int strengthBuff(int strength, int amount, boolean multiply){
        if (multiply){
            strength *= amount;
            return strength;
        }else{
            strength += amount;
            return strength;
        }
    }

    // =OTHER TYPE EFFECTS=
    // Regen - At the end of your turn, heal X HP - Intensity

}
