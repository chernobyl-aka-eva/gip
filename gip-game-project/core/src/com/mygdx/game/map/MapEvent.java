package com.mygdx.game.map;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;

public class MapEvent extends Table {
    private int id;
    private int order;
    private TextureAtlas atlas;
    private MapEventType mapEventType;
    private Image mapEvent;
    private boolean currentEvent = false;
    private RepeatAction repeatAction = new RepeatAction();

    public MapEvent(TextureAtlas textureAtlas, MapEventType mapEventType, int id, int order) {
        this.id = id;
        this.atlas = textureAtlas;
        this.mapEventType = mapEventType;
        this.order = order;
        init();
    }

    private void init() {
        setBackground(new TextureRegionDrawable(atlas.findRegion("normalField")));
        setSize(atlas.findRegion("normalField").getRegionWidth(), atlas.findRegion("normalField").getRegionHeight());
        mapEvent = getEventTable();
        mapEvent.setOrigin(Align.center);
        add(mapEvent).size(mapEvent.getWidth(), mapEvent.getHeight());
    }

    public Image getEventTable() {

        Image mapEvent;
        switch (mapEventType) {
            case REST:      mapEvent = new Image(new TextureRegionDrawable(atlas.findRegion("rest"))); break;
            default:        mapEvent = new Image(new TextureRegionDrawable(atlas.findRegion("monster"))); break;
        }
        return mapEvent;
    }

    public boolean isCurrentEvent() {
        return currentEvent;
    }

    public void setCurrentEvent(boolean nextEvent) {
        if (currentEvent && !nextEvent) {
            repeatAction.finish();

        } else if (nextEvent) {
            repeatAction = new RepeatAction();
            SequenceAction sqa = new SequenceAction();
            sqa.addAction(Actions.scaleBy(0.5F, 0.5F, 1F));
            sqa.addAction(Actions.scaleBy(-0.5F, -0.5F, 1F));
            repeatAction.setAction(sqa);
            repeatAction.setCount(RepeatAction.FOREVER);
            repeatAction.restart();
            mapEvent.addAction(repeatAction);
        }
        this.currentEvent = nextEvent;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public MapEventType getMapEventType() {
        return mapEventType;
    }

    public void setMapEventType(MapEventType mapEventType) {
        this.mapEventType = mapEventType;
    }
}
