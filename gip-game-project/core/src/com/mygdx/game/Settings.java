package com.mygdx.game;

import com.badlogic.gdx.Gdx;
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
    private boolean enabled;
    private Button exit;

    private final TextureRegion textureRegion;

    public Settings(final GipGameProject game, final Stage stage) {
        this.game = game;
        stageSettings = new Stage(new ScreenViewport());
        enabled = false;
        game.skin = new Skin(Gdx.files.internal("skin/ui-skin.json"));
        game.skin.addRegions(new TextureAtlas("skin/ui-skin.atlas"));
        exit = new Button(game.skin, "default");
        exit.setSize(39, 29);
        exit.setSize(game.skin.getRegion("X-0").getRegionWidth(), game.skin.getRegion("X-0").getRegionHeight());
        exit.setPosition((stageSettings.getWidth()-exit.getWidth())/2, (stageSettings.getHeight()-exit.getHeight())/2);

        textureRegion = new TextureRegion(game.skin.getRegion("settings-background"));
        exit.setPosition((stageSettings.getWidth()- textureRegion.getRegionWidth())/2+ textureRegion.getRegionWidth()-exit.getWidth()-2, (stageSettings.getHeight()- textureRegion.getRegionHeight())/2+ textureRegion.getRegionHeight()-exit.getHeight()-5);
        exit.addListener(new InputListener() {
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {}
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                enabled = false;
                Gdx.input.setInputProcessor(stage);
                return true;
            }
        });

        stageSettings.addActor(exit);
        exit.setName("exit");

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
        game.batch.draw(textureRegion, (stageSettings.getWidth()- textureRegion.getRegionWidth())/2, (stageSettings.getHeight()- textureRegion.getRegionHeight())/2);
        game.batch.end();
        game.batch.begin();
        stageSettings.act();
        stageSettings.draw();



        game.batch.end();

    }

    public void dispose() {
        stageSettings.dispose();
        textureRegion.getTexture().dispose();
    }

}
