package com.mygdx.game.monster;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mygdx.game.GipGameProject;


public class MonsterManager {
    private Group monsterGroup;
    private Stage stage;
    private GipGameProject game;



    public MonsterManager(GipGameProject game, Stage stage) {
        this.stage = stage;
        this.game = game;

        monsterGroup = new Group();

        // Animation Textures
        game.textureAtlas = new TextureAtlas(Gdx.files.internal("animation/enemyidle.atlas"));
        game.skin = new Skin(Gdx.files.internal("skin/game-ui.json"));


        stage.addActor(monsterGroup);
    }

    public void addMonster(int monsterId){
        switch (monsterId) { //545
            case 0: final Monster single = new Monster(
                        game,
                        0,
                        "Single",
                        100,
                        0,
                             stage
                ); monsterGroup.addActor(single);

        }
    }

    public void drawMonster(){
        game.batch.begin();
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
        game.batch.end();
    }

    public void setVisible(boolean isVisible){
        if (isVisible){
            for (Actor actor:monsterGroup.getChildren()){
                if (actor instanceof Monster){
                    Monster monster = (Monster) actor;
                    monster.setVisible(true);
                }
            }
        }else{
            for (Actor actor:monsterGroup.getChildren()){
                if (actor instanceof Monster){
                    Monster monster = (Monster) actor;
                    monster.setVisible(false);
                }
            }
        }
    }

    // getters and setters


    public Group getMonsterGroup() {
        return monsterGroup;
    }

    public void setMonsterGroup(Group monsterGroup) {
        this.monsterGroup = monsterGroup;
    }
}
