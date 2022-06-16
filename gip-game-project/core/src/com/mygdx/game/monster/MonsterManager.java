package com.mygdx.game.monster;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.GipGameProject;

import java.util.Random;


public class MonsterManager {
    private Group monsterGroup;
    private Stage stage;
    private Group gameScreenGroup;
    private GipGameProject game;
    private MonsterIntent monsterIntent;
    private Array<Integer> monsterIntents = new Array<>();

    private TextureAtlas atlas;
    private Image attackIntent;
    private Image defendIntent;
    private Label intentLabel;


    public MonsterManager(GipGameProject game, Stage stage, Group group) {
        this.stage = stage;
        this.gameScreenGroup = group;
        this.game = game;

        // intent icons
        atlas = new TextureAtlas("skin/game-ui.atlas");
        attackIntent = new Image(new TextureRegionDrawable(atlas.findRegion("attack")));
        defendIntent = new Image(new TextureRegionDrawable(atlas.findRegion("defend")));
        intentLabel = new Label("", game.skin());
        gameScreenGroup.addActor(attackIntent);
        gameScreenGroup.addActor(defendIntent);
        gameScreenGroup.addActor(intentLabel);

        // ♦ monster army ♦
        monsterGroup = new Group();
        gameScreenGroup.addActor(monsterGroup);



    }

    public void addMonster(int monsterId) {
        switch (monsterId) { //545
            case 0:
                final Monster enemy = new Monster(
                        game,
                        0,
                        "Enemy",
                        35,
                        0,
                        stage,
                        gameScreenGroup
                );
                monsterGroup.addActor(enemy);

        }
    }

    public void dispose() {
        atlas.dispose();
        for (Actor child : monsterGroup.getChildren()) {
            if (child instanceof Monster) {
                ((Monster) child).dispose();
            }
        }
    }

    public void drawMonster() {
        game.batch.begin();
        //stage.act(Gdx.graphics.getDeltaTime());
        //stage.draw();
        game.batch.end();
    }

    public void setVisible(boolean isVisible) {
        if (isVisible) {
            for (Actor actor : monsterGroup.getChildren()) {
                if (actor instanceof Monster) {
                    Monster monster = (Monster) actor;
                    monster.setVisible(true);
                }
            }
        } else {
            for (Actor actor : monsterGroup.getChildren()) {
                if (actor instanceof Monster) {
                    Monster monster = (Monster) actor;
                    monster.setVisible(false);
                }
            }
        }
    }


    public void intent() {
        monsterIntents.clear();
        for (Actor actor : getMonsterGroup().getChildren()) {
            if (actor instanceof Monster) {
                Monster monsterInstance = (Monster) actor;

                Monster monster = monsterInstance;
                attackIntent.setPosition(
                        monster.getNameAreaMonster().getX() + 60,
                        monster.getNameAreaMonster().getY() + monster.getNameAreaMonster().getHeight() + 50);
                defendIntent.setPosition(
                        monster.getNameAreaMonster().getX() + 60,
                        monster.getNameAreaMonster().getY() + monster.getNameAreaMonster().getHeight() + 50);

                attackIntent.setVisible(false);
                defendIntent.setVisible(false);

                int intent = randomIntent(2);
                monsterIntents.add(intent);

                for (int m = 0; m < monsterIntents.size; m++) {
                    //switch (monsterIntents.get(m)){
                    switch (monsterInstance.getId()) {
                        case 0:
                            switch (monsterIntents.get(m)) {
                                case 0:
                                    game.log.debug("number: " + intent);
                                    intentLabel.setText(32);
                                    intentLabel.setPosition(
                                            attackIntent.getX() + 50,
                                            attackIntent.getY());
                                    intentLabel.setVisible(true);
                                    defendIntent.setVisible(false);
                                    attackIntent.setVisible(true);
                                    break;

                                case 1:
                                    game.log.debug("number: " + intent);
                                    intentLabel.setText(12);
                                    intentLabel.setPosition(
                                            attackIntent.getX() + 50,
                                            attackIntent.getY());
                                    intentLabel.setVisible(true);
                                    attackIntent.setVisible(false);
                                    defendIntent.setVisible(true);
                                    break;
                            }
                    }
                }
            }
        }
    }


    public int randomIntent(int bound) {
        Random random = new Random();
        return random.nextInt(bound);
    }

    public void setIntentVisible(boolean visible){
        for (int m = 0; m < monsterIntents.size; m++) {
            switch (monsterIntents.get(m)){
                case 0:
                    if (visible){
                        attackIntent.setVisible(true);
                        intentLabel.setVisible(true);
                    }else{
                        attackIntent.setVisible(false);
                        intentLabel.setVisible(false);
                    }break;
                case 1:
                    if (visible){
                        defendIntent.setVisible(true);
                        intentLabel.setVisible(true);
                    }else{
                        defendIntent.setVisible(false);
                        intentLabel.setVisible(false);
                    }break;
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

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Group getGameScreenGroup() {
        return gameScreenGroup;
    }

    public void setGameScreenGroup(Group gameScreenGroup) {
        this.gameScreenGroup = gameScreenGroup;
    }

    public GipGameProject getGame() {
        return game;
    }

    public void setGame(GipGameProject game) {
        this.game = game;
    }

    public MonsterIntent getMonsterIntent() {
        return monsterIntent;
    }

    public void setMonsterIntent(MonsterIntent monsterIntent) {
        this.monsterIntent = monsterIntent;
    }

    public Array<Integer> getMonsterIntents() {
        return monsterIntents;
    }

    public void setMonsterIntents(Array<Integer> monsterIntents) {
        this.monsterIntents = monsterIntents;
    }

    public TextureAtlas getAtlas() {
        return atlas;
    }

    public void setAtlas(TextureAtlas atlas) {
        this.atlas = atlas;
    }

    public Image getAttackIntent() {
        return attackIntent;
    }

    public void setAttackIntent(Image attackIntent) {
        this.attackIntent = attackIntent;
    }

    public Image getDefendIntent() {
        return defendIntent;
    }

    public void setDefendIntent(Image defendIntent) {
        this.defendIntent = defendIntent;
    }

    public Label getIntentLabel() {
        return intentLabel;
    }

    public void setIntentLabel(Label intentLabel) {
        this.intentLabel = intentLabel;
    }
}

