package com.mygdx.game.effect;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.GipGameProject;
import com.mygdx.game.monster.Monster;
import com.mygdx.game.virus.Virus;

import java.util.Locale;

public class EffectManager {
    private GipGameProject game;

    private Actor target;
    private Array<Effect> effects = new Array<>();
    private Table effectTable;
    private Stage stage;

    private TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("other/game-ui-2.atlas"));

    public EffectManager(Actor target, GipGameProject game, Stage stage) {
        this.game = game;
        this.target = target;
        this.stage = stage;

        effectTable = new Table();
        if (target instanceof Virus) {
            Virus virus = (Virus) target;
            effectTable.setPosition(virus.getVirusHealthBar().getX()-virus.getVirusHealthBar().getWidth()/2+5, virus.getVirusHealthBar().getY()-25);
            effectTable.setSize(virus.getVirusHealthBar().getWidth(), 20);
        } else if (target instanceof Monster) {
            Monster monster = (Monster) target;
            effectTable.setPosition(monster.getMonsterHealthBar().getX()-monster.getMonsterHealthBar().getWidth()/2+5, monster.getMonsterHealthBar().getY()-25);
            effectTable.setSize(monster.getMonsterHealthBar().getWidth(), 20);
        }
        //effectTable.setPosition(200, 200);

        stage.addActor(effectTable);
    }

    public void addEffect(int id) {
        Effect addedEffect = null;
        switch (id) {
            case 0: Effect dexterity = new Effect(0, "Dexterity", true); addedEffect = dexterity; break;
            case 1: Effect strength = new Effect(1, "Strength", true); addedEffect = strength; break;
        }

        boolean effectFound = false;
        for (Effect effect : effects) {
            if (effect.getId() == id) {
                if (addedEffect.isStacks()) {
                    effect.setStack(addedEffect.getStack()+1); effectFound = true; break;
                }
            }
        }
        if (!effectFound) {
            effects.add(addedEffect);
        }
        Image effectIcon = new Image(new TextureRegionDrawable(atlas.findRegion(addedEffect.getEffectName().toLowerCase(Locale.ROOT))));
        effectIcon.setPosition(100, 100);
        //stage.addActor(effectIcon);
        effectTable.add(effectIcon).size(effectIcon.getWidth(), effectIcon.getHeight()).left();
    }

    public int getStack(int id) {
        int stack = 0;
        for (Effect effect : effects) {
            if (effect.getId() == id) {
                stack = effect.getStack();
            }
        }
        return stack;
    }

    public void dispose() {
        atlas.dispose();
    }

    public Table getEffectTable() {
        return effectTable;
    }

    public void setEffectTable(Table effectTable) {
        this.effectTable = effectTable;
    }

    public GipGameProject getGame() {
        return game;
    }

    public void setGame(GipGameProject game) {
        this.game = game;
    }

    public Actor getTarget() {
        return target;
    }

    public void setTarget(Actor target) {
        this.target = target;
    }

    public Array<Effect> getEffects() {
        return effects;
    }

    public void setEffects(Array<Effect> effects) {
        this.effects = effects;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public TextureAtlas getAtlas() {
        return atlas;
    }

    public void setAtlas(TextureAtlas atlas) {
        this.atlas = atlas;
    }
}
