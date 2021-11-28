package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class Settings {


    final GipGameProject game;
    private Stage stageSettings;
    private Stage previousStage;
    private boolean enabled;
    private boolean isInputSettings;


    private TextureRegion settingsTextureRegion;
    private TextureRegion inputTextureRegion;

    public Settings(final GipGameProject game, final Stage stage) {
        this.game = game;
        previousStage = stage;
        stageSettings = new Stage(new ScreenViewport());
        enabled = false;
        game.skin = new Skin(Gdx.files.internal("skin/settings-ui.json"));
        game.skin.addRegions(new TextureAtlas("skin/settings-ui.atlas"));

        settingsTextureRegion = new TextureRegion(game.skin.getRegion("settings"));
        inputTextureRegion= new TextureRegion(game.skin.getRegion("input-settings"));

        isInputSettings = false;

        final Button settings = new Button(game.skin);
        settings.setPosition((stageSettings.getWidth()-settingsTextureRegion.getRegionWidth())/2+30, (stageSettings.getHeight()-settingsTextureRegion.getRegionHeight())/2+802);
        settings.setChecked(true);


        final Button inputSettings = new Button(game.skin, "settings-input");
        inputSettings.setPosition(settings.getX()+settings.getWidth()+30, settings.getY());

        settings.addListener(new InputListener() {
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                settings.setChecked(true);
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                settings.setChecked(true);
                inputSettings.setChecked(false);
                isInputSettings = false;
                return true;
            }
        });

        inputSettings.addListener(new InputListener() {
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                inputSettings.setChecked(true);
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                inputSettings.setChecked(true);
                settings.setChecked(false);
                isInputSettings = true;
                return true;
            }
        });





        stageSettings.addActor(settings);
        stageSettings.addActor(inputSettings);


    }


    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Stage getStageSettings() {
        return stageSettings;
    }

    public void render(float delta) {
        game.batch.begin();
        if (isInputSettings) {
            game.batch.draw(inputTextureRegion, (stageSettings.getWidth()-inputTextureRegion.getRegionWidth())/2, (stageSettings.getHeight()- inputTextureRegion.getRegionHeight())/2);

        } else {
            game.batch.draw(settingsTextureRegion, (stageSettings.getWidth()-settingsTextureRegion.getRegionWidth())/2, (stageSettings.getHeight()- settingsTextureRegion.getRegionHeight())/2);

        }
        game.batch.end();
        game.batch.begin();
        stageSettings.act();
        stageSettings.draw();
        game.batch.end();

        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            enabled = false;
            Gdx.input.setInputProcessor(previousStage);
            dispose();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.F10)) {
            enabled = false;
            Gdx.input.setInputProcessor(previousStage);
            dispose();
        }


    }

    public void dispose() {
        stageSettings.dispose();
        settingsTextureRegion.getTexture().dispose();
        inputTextureRegion.getTexture().dispose();
    }

}
