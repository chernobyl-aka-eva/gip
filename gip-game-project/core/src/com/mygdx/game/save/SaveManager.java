package com.mygdx.game.save;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.mygdx.game.SessionManager;

public class SaveManager {
    private SessionManager sessionManager;

    public SaveManager(SessionManager sessionManager) {
        Preferences prefs = Gdx.app.getPreferences("Save");
    }

    private void save() {

    }

    private void load() {

    }
}
