package com.mygdx.game.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.save.SavedMap;
import com.mygdx.game.save.SavedState;

import java.util.Random;

public class MapBackground extends Table {
    private Array<MapEvent> mapEvents;
    private TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("other/map-ui.atlas"));

    private int currentEvent = 0;

    private int countRest = 0;
    private int countElite = 0;
    private int intSinceElite = 1;
    private MapEventType mapEventType = MapEventType.MONSTER;
    private boolean selected;

    private SavedState savedState;

    public MapBackground(TextureRegion region, SavedState savedState) {
        //debug();
        setBackground(new TextureRegionDrawable(region));
        setSize(region.getRegionWidth(), region.getRegionHeight());
        align(Align.top|Align.left);

        this.savedState = savedState;

        int amountEvents = 7;
        mapEvents = new Array<>();
        if (savedState == null) {
            initMapEvents();
        } else {
            loadMapBackGround();
        }
    }

    private void initMapEvents() {
        MapEvent event1 = new MapEvent(atlas, MapEventType.MONSTER, 0, 0);
        MapEvent event2 = new MapEvent(atlas, MapEventType.REST, 0, 1);
        MapEvent event3 = new MapEvent(atlas, MapEventType.MONSTER, 0, 2);
        MapEvent event4 = new MapEvent(atlas, MapEventType.REST, 0, 2);
        MapEvent event5 = new MapEvent(atlas, MapEventType.MONSTER, 0, 4);
        MapEvent event6 = new MapEvent(atlas, MapEventType.RANDOM, 0, 2);
        MapEvent event7 = new MapEvent(atlas, MapEventType.MONSTER, 0, 2);

        mapEvents.add(event1, event2, event3, event4);
        mapEvents.add(event5, event6, event7);

        for (int i = mapEvents.size - 1; i >= 0; i--) {
            add(mapEvents.get(i)).size(mapEvents.get(i).getWidth(), mapEvents.get(i).getHeight()).pad((i == 6) ? 330 : 103, 160, 0, 0).row();
        }
    }

    private void loadMapBackGround() {
        SavedMap savedMap = savedState.getSavedMap();
        currentEvent = savedMap.getCurrentEventId();

        for (int i = 0; i < savedMap.getMapEventTypes().size(); i++) {
            MapEvent mapEvent = new MapEvent(atlas, savedMap.getMapEventTypes().get(i), savedMap.getMapEventIds().get(i), i);
            mapEvents.add(mapEvent);
        }

        for (int i = mapEvents.size - 1; i >= 0; i--) {
            add(mapEvents.get(i)).size(mapEvents.get(i).getWidth(), mapEvents.get(i).getHeight()).pad((i == 6) ? 330 : 103, 160, 0, 0).row();
        }
    }





    public void dispose() {
        atlas.dispose();
    }

    public int getCurrentEvent() {
        return currentEvent;
    }

    public void setCurrentEvent(int currentEvent) {
        for (MapEvent mapEvent : mapEvents) {
            mapEvent.setCurrentEvent(false);
        }

        this.currentEvent = currentEvent;
        MapEvent mapEvent = mapEvents.get(currentEvent);
        mapEvent.setCurrentEvent(true);

    }

    public Array<MapEvent> getMapEvents() {
        return mapEvents;
    }

    public void setMapEvents(Array<MapEvent> mapEvents) {
        this.mapEvents = mapEvents;
    }

    public TextureAtlas getAtlas() {
        return atlas;
    }

    public void setAtlas(TextureAtlas atlas) {
        this.atlas = atlas;
    }

    public int getCountRest() {
        return countRest;
    }

    public void setCountRest(int countRest) {
        this.countRest = countRest;
    }

    public int getCountElite() {
        return countElite;
    }

    public void setCountElite(int countElite) {
        this.countElite = countElite;
    }

    public int getIntSinceElite() {
        return intSinceElite;
    }

    public void setIntSinceElite(int intSinceElite) {
        this.intSinceElite = intSinceElite;
    }

    public MapEventType getMapEventType() {
        return mapEventType;
    }

    public void setMapEventType(MapEventType mapEventType) {
        this.mapEventType = mapEventType;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
