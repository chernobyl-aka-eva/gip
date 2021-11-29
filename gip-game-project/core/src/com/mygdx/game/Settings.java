package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Settings {

    final GipGameProject game;

    // stage the same as last screen
    private Stage stageSettings;
    private boolean isInputSettings;
    private Group settingsGroup;

    // settings backgrounds
    private TextureRegion settingsTextureRegion;
    private TextureRegion inputTextureRegion;

    public Settings(final GipGameProject game, final Stage stage) {

        this.game = game;

        stageSettings = stage;
        settingsGroup = new Group(); // actor group
        settingsGroup.setVisible(false); // prevents settings showing up without clicking
        game.skin = new Skin(Gdx.files.internal("skin/settings-ui.json")); // skin
        game.skin.addRegions(new TextureAtlas("skin/settings-ui.atlas")); // texture atlas (skin)

        settingsTextureRegion = new TextureRegion(game.skin.getRegion("settings"));
        inputTextureRegion = new TextureRegion(game.skin.getRegion("input-settings"));

        isInputSettings = false; // shows settings instead of input settings

        final Button settings = new Button(game.skin); // settings button
        settings.setPosition(
                (stageSettings.getWidth() - settingsTextureRegion.getRegionWidth()) / 2 + 30,
                (stageSettings.getHeight() - settingsTextureRegion.getRegionHeight()) / 2 + 802);
        settings.setChecked(true); // enables toggling


        final Button inputSettings = new Button(game.skin, "settings-input"); // input button
        inputSettings.setPosition(settings.getX() + settings.getWidth() + 30,
                settings.getY());

        settings.addListener(new InputListener() { // settings input listener (detects clicking)
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) { // makes sure only 1 button is checked
                settings.setChecked(true);
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) { // listens to input
                settings.setChecked(true);
                inputSettings.setChecked(false);
                isInputSettings = false;
                return true;
            }
        });

        inputSettings.addListener(new InputListener() { // input input listener (detects clicking)
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                inputSettings.setChecked(true);
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                inputSettings.setChecked(true);
                settings.setChecked(false);
                isInputSettings = true;
                return true;
            }
        });

        // adds buttons to actor group
        settingsGroup.addActor(settings);
        settingsGroup.addActor(inputSettings);
        stageSettings.addActor(settingsGroup); // adding actor group to stage


    }

    public Group getSettingsGroup() {
        return settingsGroup;
    } // getter

    public Stage getStageSettings() {
        return stageSettings;
    } // getter

    public void render(float delta, Group previousGroup) {

        game.batch.begin();
        // displays correct settings screen
        if (isInputSettings) {
            game.batch.draw(inputTextureRegion, (stageSettings.getWidth() - inputTextureRegion.getRegionWidth()) / 2, (stageSettings.getHeight() - inputTextureRegion.getRegionHeight()) / 2);

        } else {
            game.batch.draw(settingsTextureRegion, (stageSettings.getWidth() - settingsTextureRegion.getRegionWidth()) / 2, (stageSettings.getHeight() - settingsTextureRegion.getRegionHeight()) / 2);

        }
        game.batch.end();
        game.batch.begin();
        stageSettings.act(); // draws actors
        stageSettings.draw();
        game.batch.end();

        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) { // detects ESC key to close settings
            settingsGroup.setVisible(false);
            previousGroup.setVisible(true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.F10)) { // detects F10 key to close settings (later save)
            settingsGroup.setVisible(false);
            previousGroup.setVisible(true);
        }


    }

    public void dispose() {
        settingsTextureRegion.getTexture().dispose();
        inputTextureRegion.getTexture().dispose();

    }

}
