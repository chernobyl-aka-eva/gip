package com.mygdx.game.item;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.GipGameProject;
import com.mygdx.game.infobox.InfoBoxManager;

public class ItemManager {
    private Group virusItems;
    private Table table;
    private TextureAtlas textureAtlas = new TextureAtlas("other/game-ui-2.atlas");
    private InfoBoxManager infoBoxManager;

    public ItemManager(Group virusItems, Stage stage, GipGameProject game) {
        this.virusItems = virusItems;
        table = new Table();
        table.setBounds(20, Gdx.graphics.getHeight()-120, 1000, 50);
        //table.debug();
        table.align(Align.left);
        virusItems.addActor(table);

        infoBoxManager = new InfoBoxManager(game, stage, table);
    }

    public void addItem(int id) {
        switch (id) {
            case 0: Item dataDisc = new Item(0, "Data Disk", "common", "DATADISK", textureAtlas.findRegion("data-disk"), virusItems, infoBoxManager);
            table.add(dataDisc).size(dataDisc.getWidth(), dataDisc.getHeight()).pad(10);
            break;
        }
    }


    public void dispose() {
        textureAtlas.dispose();
    }
}
