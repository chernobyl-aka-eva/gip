package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.GipGameProject;

public class Settings {

    final GipGameProject game;

    // stage the same as last screen
    private final Stage stageSettings;
    private boolean isInputSettings;
    private final Group settingsGroup;

    // settings backgrounds
    private final TextureRegion settingsTextureRegion;
    private final TextureRegion inputTextureRegion;
    private Image settingsBackground;
    private Image inputBackground;

    public Settings(final GipGameProject game, final Stage stage) {

        this.game = game;

        stageSettings = stage;
        settingsGroup = new Group(); // actor group
        settingsGroup.setVisible(false); // prevents settings showing up without clicking

        Skin skin = new Skin(Gdx.files.internal("skin/settings-ui.json"));
        TextureAtlas atlas = new TextureAtlas("skin/settings-ui.atlas");

        settingsTextureRegion = new TextureRegion(atlas.findRegion("settings"));
        inputTextureRegion = new TextureRegion(atlas.findRegion("input-settings"));
        settingsBackground = new Image(new TextureRegionDrawable(settingsTextureRegion));
        settingsBackground.setPosition((stageSettings.getWidth() - settingsBackground.getWidth()) / 2, (stageSettings.getHeight() - settingsBackground.getHeight()) / 2);
        settingsBackground.setVisible(true);

        inputBackground = new Image(new TextureRegionDrawable(inputTextureRegion));
        inputBackground.setPosition((stageSettings.getWidth() - inputBackground.getWidth()) / 2, (stageSettings.getHeight() - inputBackground.getHeight()) / 2);
        inputBackground.setVisible(false);

        isInputSettings = false; // shows settings instead of input settings

        final Button settings = new Button(skin); // settings button
        settings.setPosition(
                (stageSettings.getWidth() - settingsBackground.getWidth() / 2 + 30),
                (stageSettings.getHeight() - settingsBackground.getHeight()) / 2 + 802);
        settings.setChecked(true); // enables toggling


        final Button inputSettings = new Button(skin, "settings-input"); // input button
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
                setInputSettings(false);
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
                setInputSettings(true);
                return true;
            }
        });

        // adds buttons to actor group
        settingsGroup.addActor(settingsBackground);
        settingsGroup.addActor(inputBackground);
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

    public boolean isInputSettings() {

        return isInputSettings;
    }

    public void setInputSettings(boolean inputSettings) {
        isInputSettings = inputSettings;
        settingsBackground.setVisible(!inputSettings);
        inputBackground.setVisible(inputSettings);
    }

    public void dispose() {
        settingsTextureRegion.getTexture().dispose();
        inputTextureRegion.getTexture().dispose();

    }

}
