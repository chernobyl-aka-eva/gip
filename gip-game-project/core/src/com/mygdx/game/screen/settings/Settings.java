package com.mygdx.game.screen.settings;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class Settings {
    private final String SETT_MUSIC_VOLUME = "volume";
    private final String SETT_MUSIC_ENABLED = "music.enabled";
    private final String SETT_SOUND_ENABLED = "sound.enabled";
    private final String SETT_SOUND_VOL = "sound";
    private final String SETT_FULLSCREEN_ENABLED = "fullscreen.enabled";
    private final String SETT_VSYNC_ENABLED = "vsync.enabled";
    private final String SETT_MSAA_SAMPLES = "MSAA samples";
    private final String SETT_RESOLUTION_X = "resX";
    private final String SETT_RESOLUTION_Y = "resY";

    private Preferences preferences;

    public Settings() {
    }

    protected Preferences getSettings() {
        if (preferences == null) {
            String SETT_NAME = "beanssoftware.settings";
            preferences = Gdx.app.getPreferences(SETT_NAME);
        }
        return preferences;
    }

    // enable sound effects
    public boolean isSoundEffectsEnabled() {
        return getSettings().getBoolean(SETT_SOUND_ENABLED, true);
    }

    public void setSoundEffectsEnabled(boolean soundEffectsEnabled) {
        getSettings().putBoolean(SETT_SOUND_ENABLED, soundEffectsEnabled);
        getSettings().flush();
    }

    // enable music
    public boolean isMusicEnabled() {
        return getSettings().getBoolean(SETT_MUSIC_ENABLED, true);
    }

    public void setMusicEnabled(boolean musicEnabled) {
        getSettings().putBoolean(SETT_MUSIC_ENABLED, musicEnabled);
        getSettings().flush();
    }

    // music volume
    public float getMusicVolume() {
        return getSettings().getFloat(SETT_MUSIC_VOLUME, 0.5f);
    }

    public void setMusicVolume(float volume) {
        getSettings().putFloat(SETT_MUSIC_VOLUME, volume);
        getSettings().flush();
    }

    // sound volume
    public float getSoundVolume() {
        return getSettings().getFloat(SETT_SOUND_VOL, 0.5f);
    }

    public void setSoundVolume(float volume) {
        getSettings().putFloat(SETT_SOUND_VOL, volume);
        getSettings().flush();
    }

    // enable fullscreen
    public boolean isFullscreenEnabled() {
        return getSettings().getBoolean(SETT_FULLSCREEN_ENABLED, true);
    }

    public void setFullscreenEnabled(boolean fullscreenEnabled) {
        getSettings().putBoolean(SETT_FULLSCREEN_ENABLED, fullscreenEnabled);
        getSettings().flush();
    }

    // enable vsync
    public boolean isVsyncEnabled() {
        return getSettings().getBoolean(SETT_VSYNC_ENABLED, true);
    }

    public void setVsyncEnabled(boolean vsyncEnabled) {
        getSettings().putBoolean(SETT_VSYNC_ENABLED, vsyncEnabled);
        getSettings().flush();
    }

    // msaa samples
    public int getMSAA() {
        return getSettings().getInteger(SETT_MSAA_SAMPLES);
    }

    public void setMSAA(int msaa) {
        getSettings().putInteger(SETT_MSAA_SAMPLES, msaa);
        getSettings().flush();
    }

    // resolution
    public int getResX() {
        return getSettings().getInteger(SETT_RESOLUTION_X);
    }

    public void setResX(int resX) {
        getSettings().putInteger(SETT_RESOLUTION_X, resX);
        getSettings().flush();
    }

    public int getResY() {
        return getSettings().getInteger(SETT_RESOLUTION_Y);
    }

    public void setResY(int resY) {
        getSettings().putInteger(SETT_RESOLUTION_Y, resY);
        getSettings().flush();
    }


}
