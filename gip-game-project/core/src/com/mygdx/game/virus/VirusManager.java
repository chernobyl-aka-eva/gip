package com.mygdx.game.virus;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.GipGameProject;
import com.mygdx.game.save.SavedState;


public class VirusManager {
    private GipGameProject game;

    private Virus player;

    private Stage stage;
    private final Group gameScreenGroup;

    public VirusManager(GipGameProject game, Stage stage, Group group, SavedState savedState) {
        this.game = game;
        this.stage = stage;
        this.gameScreenGroup = group;


        if (savedState == null) {
            player = new Virus(game, "Player", 100, 0, stage, gameScreenGroup, savedState);
        } else {
            player = new Virus(game, "Player", savedState.getHealth(), savedState.getBlock(), stage, gameScreenGroup, savedState);
        }
        gameScreenGroup.addActor(player);


    }

    public void dispose() {
        player.dispose();
    }

    public void drawVirus(){
        game.batch.begin();
        //stage.act(Gdx.graphics.getDeltaTime());
        //stage.draw();
        game.batch.end();
    }

    public void setVisible(boolean isVisible){
        player.setVisible(isVisible);
        //.out.println(isVisible);
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
