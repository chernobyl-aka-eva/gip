package com.mygdx.game.virus;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.GipGameProject;


public class EnergyManager extends com.badlogic.gdx.scenes.scene2d.ui.Label {

    private GipGameProject game;
    private Stage stage;
    private int energy;

    public EnergyManager(GipGameProject game, Stage stage, int energy, BitmapFont font) {
        super(String.valueOf(energy), new LabelStyle(font, Color.WHITE));
        this.game = game;
        this.stage = stage;
        this.energy = energy;

        this.setPosition(50, 200);
        LabelStyle labelStyle = new LabelStyle(font, Color.WHITE);
        labelStyle.font = font;
    }

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        this.setVisible(visible);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        this.setText(energy + "/3");
    }

    public GipGameProject getGame() {
        return game;
    }

    public void setGame(GipGameProject game) {
        this.game = game;
    }

    @Override
    public Stage getStage() {
        return stage;
    }

    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }
}
