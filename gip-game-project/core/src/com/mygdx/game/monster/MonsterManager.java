package com.mygdx.game.monster;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mygdx.game.GipGameProject;
import com.mygdx.game.virus.Virus;

public class MonsterManager {
    private Group monsterGroup;
    private Virus player;
    private Monster monster;
    private Stage stage;
    private GipGameProject game;
    private ProgressBar monsterHealthBar;
    private final Label monsterName;

    public MonsterManager(GipGameProject game, Virus player, Monster monster, Stage stage) {
        this.player = player;
        this.monster = monster;
        this.stage = stage;
        this.game = game;

        monsterGroup = new Group();

        // Animation Textures
        game.textureAtlas = new TextureAtlas(Gdx.files.internal("animation/enemyidle.atlas"));
        game.skin = new Skin(Gdx.files.internal("skin/game-ui.json"));

        // Health Bar
        monsterHealthBar = new ProgressBar(0, 100, 1, false, game.skin);
        monsterName = new Label()

        stage.addActor(monsterGroup); monsterGroup.setVisible(false);
    }

    public void addMonster(int monsterId){
        switch (monsterId) {
            case 0:
                final Monster single = new Monster(
                        0,
                        "Single",
                        100,
                        0
                );
                monsterHealthBar.setValue(single.getHealth());
                monsterGroup.addActor(single);
                monsterGroup.addActor(monsterHealthBar);

        }
    }






}
