package com.mygdx.game.item;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.GipGameProject;
import com.mygdx.game.infobox.InfoBoxManager;

public class ItemManager {
    private Array<Item> playerItems;
    private Group virusItemTableGroup;
    private Table table;
    private TextureAtlas textureAtlas = new TextureAtlas("other/game-ui-2.atlas");
    private InfoBoxManager infoBoxManager;

    public ItemManager(Group virusItemTableGroup, Stage stage, GipGameProject game) {
        this.virusItemTableGroup = virusItemTableGroup;
        table = new Table();
        table.setBounds(20, Gdx.graphics.getHeight()-120, 1000, 50);
        //table.debug();
        table.align(Align.left);
        virusItemTableGroup.addActor(table);
        playerItems = new Array<>();

        infoBoxManager = new InfoBoxManager(game, stage, table);
    }

    public void addItem(int id) {
        switch (id) {
            case 0: Item dataDisc = new Item(0, "Data Disk", "common", "DATADISK", textureAtlas.findRegion("data-disk"), virusItemTableGroup, infoBoxManager);
            table.add(dataDisc).size(dataDisc.getWidth(), dataDisc.getHeight()).pad(10); playerItems.add(dataDisc);
            break;
        }
    }


    public void dispose() {
        textureAtlas.dispose();
    }

    public Array<Item> getPlayerItems() {
        return playerItems;
    }

    public void setPlayerItems(Array<Item> playerItems) {
        this.playerItems = playerItems;
    }

    public Group getVirusItemTableGroup() {
        return virusItemTableGroup;
    }

    public void setVirusItemTableGroup(Group virusItemTableGroup) {
        this.virusItemTableGroup = virusItemTableGroup;
    }
}
