package com.mygdx.game.virus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mygdx.game.GipGameProject;


public class VirusManager {
    private GipGameProject game;

    private Virus player;

    private Stage stage;

    public VirusManager(GipGameProject game, Stage stage) {
        this.game = game;
        this.stage = stage;

        player = new Virus(game, "Poopiehead", 100, 100, stage);
        stage.addActor(player);

        // Animation Textures
        game.textureAtlas = new TextureAtlas(Gdx.files.internal("animation/idle.atlas"));
        game.skin = new Skin(Gdx.files.internal("skin/game-ui.json"));
    }

    public void drawVirus(){
        game.batch.begin();
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
        game.batch.end();
    }

    public void setVisible(boolean isVisible){
        player.setVisible(isVisible);
        System.out.println(isVisible);
    }

   //getters and setter

    public GipGameProject getGame() {
        return game;
    }

    public void setGame(GipGameProject game) {
        this.game = game;
    }

    public Virus getPlayer() {
        return player;
    }

    public void setPlayer(Virus player) {
        this.player = player;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
