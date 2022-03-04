package com.mygdx.game.monster;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.GipGameProject;
import com.mygdx.game.virus.Virus;

public class MonsterManager {
    private Group monsterGroup;
    private Virus player;
    private Monster monster;
    private Stage stage;
    private GipGameProject game;

    public MonsterManager(Group monsterGroup, Virus player, Monster monster, Stage stage, GipGameProject game) {
        this.monsterGroup = monsterGroup;
        this.player = player;
        this.monster = monster;
        this.stage = stage;
        this.game = game;
    }






}
