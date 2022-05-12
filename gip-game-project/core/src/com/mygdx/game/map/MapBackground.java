package com.mygdx.game.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;

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

    public MapBackground(TextureRegion region) {
        //debug();
        setBackground(new TextureRegionDrawable(region));
        setSize(region.getRegionWidth(), region.getRegionHeight());
        align(Align.top|Align.left);


        int amountEvents = 7;
        mapEvents = new Array<>();
        for (int i = 0; i < amountEvents; i++) {
            addMapEvent(i, amountEvents - i - 1);
        }
    }

    private void addMapEvent(int index, int count) {
        boolean suitable = false;
        final int maxElite = 2;
        switch (intSinceElite) {
            case 0:
                while (!suitable) {
                    mapEventType = randomEnum(MapEventType.class);
                    if (mapEventType != MapEventType.ELITE) {
                        suitable = true;
                        intSinceElite++;
                    }
                }
                break;
            case 1:
                while (!suitable) {
                    mapEventType = randomEnum(MapEventType.class);
                    if (mapEventType == MapEventType.ELITE) {
                        if (countElite <= maxElite) {
                            suitable = true;
                            intSinceElite = 0;
                            countElite++;
                        }
                    } else {
                        suitable = true;
                        intSinceElite++;
                    }
                }
                break;
            case 2:
                if (countElite > maxElite) {
                    while (!suitable) {
                        mapEventType = randomEnum(MapEventType.class);
                        if (mapEventType != MapEventType.ELITE) {
                            suitable = true;
                            intSinceElite++;
                        }
                    }
                } else {
                    mapEventType = MapEventType.ELITE;
                    intSinceElite = 0;
                    countElite++;
                }
        }
        if (count == 0) {
            mapEventType = MapEventType.MONSTER;
        }


        MapEvent eventBackground;
        if (mapEventType == MapEventType.ELITE) {
            eventBackground = new MapEvent(atlas, mapEventType, index);
        } else {
            eventBackground = new MapEvent(atlas, mapEventType, index);
        }
        mapEvents.add(eventBackground);

        add(eventBackground).size(eventBackground.getWidth(), eventBackground.getHeight()).pad((index == 0) ? 330 : 103, 160, 0, 0).row();



    }



    public static <T extends Enum<?>> T randomEnum(Class<T> enumClass){
        Random random = new Random();
        int x = random.nextInt(enumClass.getEnumConstants().length);
        return enumClass.getEnumConstants()[x];
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
        switch (currentEvent) {
            case 6: currentEvent = 0; break;
            case 5: currentEvent = 1; break;
            case 4: currentEvent = 2; break;
            case 3: currentEvent = 3; break;
            case 2: currentEvent = 4; break;
            case 1: currentEvent = 5; break;
            case 0: currentEvent = 6; break;
            default: currentEvent = 0;
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
