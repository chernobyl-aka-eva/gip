package com.mygdx.game.screen.settings;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.GipGameProject;

import static java.lang.Integer.parseInt;

public class SettingsScreen {

    final GipGameProject game;

    // stage the same as last screen
    private final Stage stageSettings;
    private final Group settingsGroup;

    // settings backgrounds
    private final TextureRegion settingsTextureRegion;
    private final TextureRegion inputTextureRegion;
    private final Image settingsBackground;
    private final Image inputBackground;

    private final Settings settings;
    private final Table soundsSettingsTable;
    private final Table videoSettingsTable;
    private final Table inputScrollPaneTable;
    private final ScrollPane inputScrollPane;
    private final Table inputSettingsTable;
    private final Slider volumeMusicSlider;
    private final Slider soundMusicSlider;
    private final CheckBox musicCheckbox;
    private final CheckBox soundEffectsCheckbox;


    private final Array<String> resolutionsArray;

    private final KeyboardController keyboardController;

    private final Array<Label> keyLabels = new Array<>(23);

    public SettingsScreen(final GipGameProject game, final Stage stage) {

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

        final Button settingsButton = new Button(skin); // settings button
        settingsButton.setPosition(
                (stageSettings.getWidth() - settingsBackground.getWidth() / 2 + 30),
                (stageSettings.getHeight() - settingsBackground.getHeight()) / 2 + 802);
        settingsButton.setChecked(true); // enables toggling


        final Button inputSettings = new Button(skin, "settings-input"); // input button
        inputSettings.setPosition(settingsButton.getX() + settingsButton.getWidth() + 30,
                settingsButton.getY());

        settingsButton.addListener(new InputListener() { // settings input listener (detects clicking)
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) { // makes sure only 1 button is checked
                settingsButton.setChecked(true);
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) { // listens to input
                settingsButton.setChecked(true);
                inputSettings.setChecked(false);
                setInputSettings(false);
                soundsSettingsTable.setVisible(true);
                videoSettingsTable.setVisible(true);
                inputScrollPaneTable.setVisible(false);
                if (settings.isSoundEffectsEnabled()){
                    game.getSound().play(settings.getSoundVolume());
                }

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
                settingsButton.setChecked(false);
                setInputSettings(true);
                soundsSettingsTable.setVisible(false);
                videoSettingsTable.setVisible(false);
                inputScrollPaneTable.setVisible(true);
                if (settings.isSoundEffectsEnabled()){
                    game.getSound().play(settings.getSoundVolume());
                }
                return true;
            }
        });

        // add buttons to actor group
        settingsGroup.addActor(settingsBackground);
        settingsGroup.addActor(inputBackground);
        settingsGroup.addActor(settingsButton);
        settingsGroup.addActor(inputSettings);
        stageSettings.addActor(settingsGroup); // adding actor group to stage

        soundsSettingsTable = new Table();
        soundsSettingsTable.setFillParent(true);
        //soundsSettingsTable.setDebug(true);
        stage.addActor(soundsSettingsTable);

        videoSettingsTable = new Table();
        videoSettingsTable.setFillParent(true);
        //videoSettingsTable.setDebug(true);
        stage.addActor(videoSettingsTable);

        inputScrollPaneTable = new Table();
        inputScrollPaneTable.setVisible(true);
        //inputScrollPaneTable.setDebug(true);
        inputScrollPaneTable.setBounds(settingsBackground.getX() + 25, settingsBackground.getY() + 160, 1580, 615);
        inputScrollPaneTable.setSize(1580, 615);

        inputSettingsTable = new Table();
        //inputSettingsTable.setDebug(true);

        settings = new Settings(game);


        // music volume
        volumeMusicSlider = new Slider(0f, 1f, 0.1f, false, skin);
        volumeMusicSlider.setValue(settings.getMusicVolume());
        String musicVolume = String.valueOf(settings.getSettings().getFloat(String.valueOf(settings.getMusicVolume())));
        game.log.debug("MUSIC VOLUME: " + musicVolume);
        volumeMusicSlider.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                settings.setMusicVolume(volumeMusicSlider.getValue());
                //settings.setMusicVolume(volumeMusicSlider.getValue());
                game.log.debug(String.valueOf(volumeMusicSlider.getValue()));
                return false;
            }
        });

        // sound volume
        soundMusicSlider = new Slider(0f, 1f, 0.1f, false, skin);
        soundMusicSlider.setValue(settings.getSoundVolume());
        String soundVolume = String.valueOf(settings.getSettings().getFloat(String.valueOf(settings.getSoundVolume())));
        game.log.debug("SOUND VOLUME: " + soundVolume);
        soundMusicSlider.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                settings.setSoundVolume(soundMusicSlider.getValue());

                return false;
            }
        });

        // music on/off
        musicCheckbox = new CheckBox(null, skin);
        musicCheckbox.setChecked(settings.isMusicEnabled());
        musicCheckbox.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                boolean enabled = musicCheckbox.isChecked();
                settings.setMusicEnabled(enabled);
                game.log.debug(String.valueOf(musicCheckbox.isChecked()));
                return false;
            }
        });

        // sound on/off
        soundEffectsCheckbox = new CheckBox(null, skin);
        soundEffectsCheckbox.setChecked(settings.isSoundEffectsEnabled());
        soundEffectsCheckbox.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                boolean enabled = soundEffectsCheckbox.isChecked();
                settings.setSoundEffectsEnabled(enabled);
                game.log.debug(String.valueOf(soundEffectsCheckbox.isChecked()));
                return false;
            }
        });

        Label volumeMusicLabel = new Label("Music Volume", skin);
        Label volumeSoundLabel = new Label("Sound Volume", skin);
        Label musicOnOffLabel = new Label("Music", skin);
        Label soundOnOffLabel = new Label("Sound Effect", skin);

        soundsSettingsTable.row().pad(10, 0, 0, 10);
        soundsSettingsTable.add(volumeMusicLabel).left();
        soundsSettingsTable.add(volumeMusicSlider);
        soundsSettingsTable.row().pad(10, 0, 0, 10);
        soundsSettingsTable.add(musicOnOffLabel).left();
        soundsSettingsTable.add(musicCheckbox);
        soundsSettingsTable.row().pad(10, 0, 0, 10);
        soundsSettingsTable.add(volumeSoundLabel).left();
        soundsSettingsTable.add(soundMusicSlider);
        soundsSettingsTable.row().pad(10, 0, 0, 10);
        soundsSettingsTable.add(soundOnOffLabel).left();
        soundsSettingsTable.add(soundEffectsCheckbox);

        soundsSettingsTable.setPosition(settingsBackground.getX() + settingsBackground.getWidth() - 450, settingsBackground.getY() + settingsBackground.getHeight() - 200);
        settingsGroup.addActor(soundsSettingsTable);

        // fullscreen on/off
        final CheckBox fullscreenCheckBox = new CheckBox(null, skin);
        fullscreenCheckBox.setChecked(settings.isFullscreenEnabled());
        fullscreenCheckBox.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                boolean enabled = fullscreenCheckBox.isChecked();
                settings.setFullscreenEnabled(enabled);
                game.log.debug(String.valueOf(fullscreenCheckBox.isChecked()));
                game.log.debug("enabled fullscreen " + settings.isFullscreenEnabled());
                return false;
            }
        });

        final SelectBox<String> resolutions = new SelectBox<>(skin);
        Gdx.graphics.getDisplayModes();
        Graphics.DisplayMode[] displayModes;
        displayModes = Gdx.graphics.getDisplayModes();
        resolutionsArray = new Array<>();
        for (Graphics.DisplayMode displayMode : displayModes) {
            String screen = displayMode.width + "x" + displayMode.height;
            if (!resolutionsArray.contains(screen, false)) {
                resolutionsArray.add(screen);
            }
        }
        resolutions.setItems(resolutionsArray);
        resolutions.setSelected(settings.getResX() + "x" + settings.getResY());
        resolutions.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                String i = resolutionsArray.get(resolutions.getSelectedIndex());
                String[] split = i.split("x");
                int x = parseInt(split[0]);
                int y = parseInt(split[1]);
                settings.setResX(x);
                settings.setResY(y);
                return false;
            }
        });

        Label fullscreenLabel = new Label("Fullscreen", skin);
        Label vSyncLabel = new Label("Vsync", skin);
        Label resolutionLabel = new Label("Resolution", skin);

        //videoSettingsTable.row().pad(10, 0, 0, 10);
        videoSettingsTable.add(fullscreenLabel).left();
        videoSettingsTable.add(fullscreenCheckBox);
        videoSettingsTable.row().pad(10, 0, 0, 10);
        videoSettingsTable.add(resolutionLabel).left();
        videoSettingsTable.add(resolutions);
        videoSettingsTable.setPosition(settingsBackground.getX() + 400, settingsBackground.getY() + settingsBackground.getHeight() - 180);
        settingsGroup.addActor(videoSettingsTable);

        // keyboard controller
        keyboardController = new KeyboardController();

        // adding labels and text buttons to settings table
        addInput(0, "Confirm Card");
        addInput(1, "Cancel/Exit");
        addInput(2, "Map");
        addInput(3, "View Deck");
        addInput(4, "Draw Pile");
        addInput(5, "Discard Pile");
        addInput(6, "Exhaust Pile");
        addInput(7, "End Turn");
        addInput(8, "Peek");
        addInput(9, "Up");
        addInput(10, "Down");
        addInput(11, "Left");
        addInput(12, "Right");
        addInput(13, "Card 1");
        addInput(14, "Card 2");
        addInput(15, "Card 3");
        addInput(16, "Card 4");
        addInput(17, "Card 5");
        addInput(18, "Card 6");
        addInput(19, "Card 7");
        addInput(20, "Card 8");
        addInput(21, "Card 9");
        addInput(22, "Card 0");
        addInput(23, "Release Card");

        //scroll pane
        inputScrollPane = new ScrollPane(inputSettingsTable, skin, "inputScrollPane");

        //inputScrollPane.setOrigin(Align.center);
        inputScrollPane.addListener(new InputListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                super.enter(event, x, y, pointer, fromActor);
                if (pointer == -1) {
                    stage.setScrollFocus(inputScrollPane);
                }
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                super.exit(event, x, y, pointer, toActor);
                if (pointer == -1) {
                    stage.setScrollFocus(null);
                }
            }
        });

        inputScrollPane.setFlickScroll(false);
        inputScrollPane.setVariableSizeKnobs(false);
        inputSettingsTable.center().top().align(Align.center);
        inputScrollPaneTable.add(inputScrollPane).fill().expand();

        settingsGroup.addActor(inputScrollPaneTable);
        inputScrollPaneTable.setVisible(false);

    }

    public void addInput(final int keyId, String labelName) {
        Skin skin = new Skin(Gdx.files.internal("skin/settings-ui.json"));
        final TextButton inputTextButton = new TextButton(Input.Keys.toString(keyboardController.getKey(keyId)), skin);
        keyLabels.add(inputTextButton.getLabel());
        inputTextButton.addListener(new ChangeListener() {
            int key;

            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.input.setInputProcessor(new InputAdapter() {
                    @Override
                    public boolean keyDown(int keycode) {
                        key = keycode;
                        keyboardController.changeKey(keyId, keycode);
                        game.log.debug("test " + keyboardController.getKey(keyId));
                        return true;
                    }

                    @Override
                    public boolean keyUp(int keycode) {
                        inputTextButton.setChecked(false);
                        Gdx.input.setInputProcessor(settingsGroup.getStage());
                        game.log.debug("up");
                        inputTextButton.setText(Input.Keys.toString(keyboardController.getKey(keyId)));
                        return true;
                    }
                });
            }
        });

        Label cardLabel = new Label(labelName + ". . . . .", skin);

        inputSettingsTable.row();
        inputSettingsTable.add(cardLabel).padRight(1000).left();
        inputSettingsTable.add(inputTextButton).right();

        keyboardController.initKeyArray();
    }

    public void refreshInput() {
        for (int k = 0; k < keyLabels.size; k++) {
            keyLabels.get(k).setText(Input.Keys.toString(keyboardController.getKeyArray()[k]));
        }
    }

    public void refreshVariables() {
        for (int v = 0; v <= 5; v++) {
            switch (v) {
                case 0:
                    settings.setFullscreenEnabled(settings.isFullscreenEnabled());
                    break;
                case 1:
                    settings.changeResolution();
                    break;
                case 2:
                    settings.setMusicVolume(settings.getMusicVolume());
                    break;
                case 3:
                    settings.setMusicEnabled(settings.isMusicEnabled());
                    break;
                case 4:
                    settings.setSoundVolume(settings.getSoundVolume());
                    break;
                case 5:
                    settings.setSoundEffectsEnabled(settings.isSoundEffectsEnabled());
                    break;
            }
        }
    }

    public Group getSettingsGroup() {
        return settingsGroup;
    } // getter

    public void render(float delta, Group previousGroup) {
        game.batch.setProjectionMatrix(game.getCamera().combined); //Important

        game.batch.begin();
        game.batch.end();
        game.batch.begin();
        stageSettings.act(); // draws actors
        stageSettings.draw();
        game.batch.end();

        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) { // detects ESC key to close settings
            // resets settings since no save
            settingsGroup.setVisible(false);
            previousGroup.setVisible(true);
            refreshInput();
            refreshVariables();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.F10)) { // detects F10 key to close settings and save
            settingsGroup.setVisible(false);
            keyboardController.saveInputSettings();
            settings.saveSettings();
            settings.changeResolution();
            previousGroup.setVisible(true);
        }


    }

    public void setInputSettings(boolean inputSettings) {
        settingsBackground.setVisible(!inputSettings);
        inputBackground.setVisible(inputSettings);
    }

    public void dispose() {
        settingsTextureRegion.getTexture().dispose();
        inputTextureRegion.getTexture().dispose();

    }


}
