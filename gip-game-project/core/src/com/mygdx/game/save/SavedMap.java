package com.mygdx.game.save;

import com.mygdx.game.map.MapEventType;

import java.util.ArrayList;

public class SavedMap {
    private ArrayList<MapEventType> mapEventTypes;
    private ArrayList<Integer> mapEventIds;
    private int currentEventId;

    public SavedMap(ArrayList<MapEventType> mapEventTypes, ArrayList<Integer> mapEventIds, int currentEventId) {
        this.mapEventTypes = mapEventTypes;
        this.mapEventIds = mapEventIds;
        this.currentEventId = currentEventId;
    }

    public SavedMap() {
    }

    public ArrayList<MapEventType> getMapEventTypes() {
        return mapEventTypes;
    }

    public void setMapEventTypes(ArrayList<MapEventType> mapEventTypes) {
        this.mapEventTypes = mapEventTypes;
    }

    public ArrayList<Integer> getMapEventIds() {
        return mapEventIds;
    }

    public void setMapEventIds(ArrayList<Integer> mapEventIds) {
        this.mapEventIds = mapEventIds;
    }

    public int getCurrentEventId() {
        return currentEventId;
    }

    public void setCurrentEventId(int currentEventId) {
        this.currentEventId = currentEventId;
    }
}
