package com.mygdx.game.screen.settings;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.mygdx.game.GipGameProject;

public class Settings {
    private final GipGameProject game;
    private final String SETT_MUSIC_VOLUME = "volume";
    private final String SETT_MUSIC_ENABLED = "music.enabled";
    private final String SETT_SOUND_ENABLED = "sound.enabled";
    private final String SETT_SOUND_VOL = "sound";
    private final String SETT_FULLSCREEN_ENABLED = "fullscreen.enabled";
    //private final String SETT_VSYNC_ENABLED = "vsync.enabled";
    //private final String SETT_MSAA_SAMPLES = "MSAA samples";
    private final String SETT_RESOLUTION_X = "resX";
    private final String SETT_RESOLUTION_Y = "resY";

    private Preferences preferences;

    private List<Object> variablesList;

    public Settings(GipGameProject game) {
        this.game = game;
    }

    protected Preferences getSettings() {
        if (preferences == null) {
            String SETT_NAME = "com.user.beanssoftwareSettings.settings";
            preferences = Gdx.app.getPreferences(SETT_NAME);
        }
        return preferences;
    }

    public void initVariables() {
        if (variablesList.getItems().size > 0) {
            variablesList.getItems().clear();
            variablesList.getItems().addAll(isFullscreenEnabled(), getResX(), getResY(), getMusicVolume(), isMusicEnabled(), getSoundVolume(), isSoundEffectsEnabled());

        }else{
            variablesList.getItems().addAll(isFullscreenEnabled(), getResX(), getResY(), getMusicVolume(), isMusicEnabled(), getSoundVolume(), isSoundEffectsEnabled());
        }
    }

    public List<Object> getVariablesList() {
        return this.variablesList;
    }

    public void saveSettings() {
        getSettings().flush();
    }

    // enable sound effects
    public boolean isSoundEffectsEnabled() {
        return getSettings().getBoolean(SETT_SOUND_ENABLED, true);
    }

    public void setSoundEffectsEnabled(boolean soundEffectsEnabled) {
        if (game.getSound() != null) {
            if (soundEffectsEnabled) {
                game.getSound().setVolume(0, 0);
            } else {
                game.getSound().setVolume(0, getMusicVolume());
            }
        }
        getSettings().putBoolean(SETT_SOUND_ENABLED, soundEffectsEnabled);
    }

    // enable music
    public boolean isMusicEnabled() {
        return getSettings().getBoolean(SETT_MUSIC_ENABLED, true);
    }

    public void setMusicEnabled(boolean musicEnabled) {
        if (game.getMusic() != null) {
            if (musicEnabled) {
                game.getMusic().play();
            } else {
                game.getMusic().stop();
            }
        }

        getSettings().putBoolean(SETT_MUSIC_ENABLED, musicEnabled);
    }

    // sound volume
    public float getSoundVolume() {
        return getSettings().getFloat(SETT_SOUND_VOL, 0.5f);
    }

    public void setSoundVolume(float volume) {
        if (game.getSound() != null) {
            game.getSound().setVolume(0, volume);
            getSettings().putFloat(SETT_SOUND_VOL, volume);
        }
    }

    // music volume
    public float getMusicVolume() {
        return getSettings().getFloat(SETT_MUSIC_VOLUME, 0.5f);
    }

    public void setMusicVolume(float volume) {
        if (game.getMusic() != null) {
            game.getMusic().setVolume(volume);
            getSettings().putFloat(SETT_MUSIC_VOLUME, volume);
        }

    }

    // enable fullscreen
    public boolean isFullscreenEnabled() {
        return getSettings().getBoolean(SETT_FULLSCREEN_ENABLED, true);
    }

    public void setFullscreenEnabled(boolean fullscreenEnabled) {
        if (fullscreenEnabled) {
            Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
            Gdx.graphics.isFullscreen();
        } else { // !
            Gdx.graphics.setWindowedMode(1920, 1080);
        }
        getSettings().putBoolean(SETT_FULLSCREEN_ENABLED, fullscreenEnabled);
    }

    // resolution
    public int getResX() {
        return getSettings().getInteger(SETT_RESOLUTION_X);
    }

    public void setResX(int resX) {
        getSettings().putInteger(SETT_RESOLUTION_X, resX);
    }

    public int getResY() {
        return getSettings().getInteger(SETT_RESOLUTION_Y);
    }

    public void setResY(int resY) {
        getSettings().putInteger(SETT_RESOLUTION_Y, resY);
    }

    public void changeResolution() {
        game.resize(getResX(), getResY());
    }
}
