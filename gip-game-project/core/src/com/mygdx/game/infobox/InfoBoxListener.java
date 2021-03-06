package com.mygdx.game.infobox;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class InfoBoxListener extends ClickListener {
    private Table infoBoxTable;
    private boolean isCard;

    public InfoBoxListener(Table infoBoxTable, boolean isCard) {
        this.infoBoxTable = infoBoxTable;
        this.isCard = isCard;
    }

    @Override
    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
        super.enter(event, x, y, pointer, fromActor);
        if (pointer == -1) {
            infoBoxTable.setVisible(true);
        }
    }

    @Override
    public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
        super.exit(event, x, y, pointer, toActor);
        if (pointer == -1) {
            infoBoxTable.setVisible(false);
            if (!isCard) {
                infoBoxTable.setPosition(0, 0);
            }
        }
    }


}
