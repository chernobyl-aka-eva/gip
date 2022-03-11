package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.effect.Effect;
import com.mygdx.game.item.Item;

import java.util.ArrayList;

// class where all game logic goes

public class GameScreen implements Screen {

    final GipGameProject game;

    // variable for tracking elapsed time
    private float elapsed_time;


    private TextureRegion gameBackground; // background texture region
    private TextureRegion hudbar; // hud bar texture region
    private TextureRegion mapBackground; // map texture region

    private boolean showMap; // determines whether map should be shown
    private boolean previousState = true; // previous state of map button

    private final Table table;

    // effects texture regions
    private ArrayList<TextureRegion> icons = new ArrayList<>();
    private ArrayList<TextureRegion> iconsEnemy = new ArrayList<>();

    // items texture region
    private ArrayList<TextureRegion> relicIcons = new ArrayList<>();

    // virus
    private ArrayList<Effect> effects = new ArrayList<>(); // buffs and debuffs arraylist

    private ArrayList<Item> items = new ArrayList<>(); // items arraylist

    // enemy
    private ArrayList<Effect> effectsEnemy = new ArrayList<>(); // buffs and debuffs arraylist


    // effects
    private Effect strength = new Effect("strength", 1, 1, 5); // strength buff
    private Effect dexterity = new Effect("dexterity", 1, 1, 5); // dexterity buff

    // item
    private Item dataDisk = new Item("data-disk", "common", "Gain 100 megabytes after every combat");

    // stage for drawing actors
    private Stage stage;

    // group for adding actors
    private final Group gameScreenGroup;
    private final Group mapScreenGroup;

    // icons health & money texture regions
    private final TextureRegion healthIcon;
    private final TextureRegion moneyIcon;

    // currency & health value
    private Label healthVirus;
    private Label moneyValue;

    private int money;
    private Label sessionTimer;

    // group for UI so they can be shown and hidden
    private final Group pauseGroup;
    private final TextureRegion pausescreenBackground;
    private boolean pausescreen; // decides whether pause screen should be shown
    private final Settings settingsScreen;

    // turnmanager (later sessionmanager) ☻
    private TurnManager turnManager;

    public GameScreen(final GipGameProject game) {
        this.game = game;

        // adding effects (later this will be added by playing cards or having certain items)
        effects.add(strength);
        effects.add(dexterity);

        effectsEnemy.add(dexterity);
        effectsEnemy.add(strength);

        // adding relics
        items.add(dataDisk);

        // game stage
        stage = new Stage(new ScreenViewport());

        Gdx.input.setInputProcessor(stage); // allows stage to take input

        // groups determine which UI should be shown on screen
        gameScreenGroup = new Group();
        mapScreenGroup = new Group();

        // initialize turnmanager
        turnManager = new TurnManager(game, stage, gameScreenGroup);


        // game background
        game.textureAtlas = new TextureAtlas("other/game-ui-2.atlas");
        gameBackground = new TextureRegion();
        gameBackground = game.textureAtlas.findRegion("guidelines");

        // hudbar texture region
        hudbar = game.textureAtlas.findRegion("hud-bar");

        // map texture region
        mapBackground = game.textureAtlas.findRegion("map-background");

        // effect icons
        for (int i = 0; i < effects.size(); i++) { // adds names of regions to textureregion arraylist (virus)
            icons.add(game.textureAtlas.findRegion(effects.get(i).getEffectName()));
        }

        for (int i = 0; i < effectsEnemy.size(); i++) { // adds names of regions to textureregion arraylist (enemy)
            iconsEnemy.add(game.textureAtlas.findRegion(effectsEnemy.get(i).getEffectName()));
        }

        // relic icons
        for (int i = 0; i < items.size(); i++) { // adds names of regions to textureregion arraylist (items)
            relicIcons.add(game.textureAtlas.findRegion(items.get(i).getRelicName()));
        }

        // game UI
        game.textureAtlas = new TextureAtlas("skin/game-ui.atlas");
        game.skin = new Skin(Gdx.files.internal("skin/game-ui.json"));
        game.skin.addRegions(new TextureAtlas("skin/game-ui.atlas"));

        Button map = new Button(game.skin); // map button
        map.setPosition(stage.getWidth() - 215, stage.getHeight() - map.getHeight() - 5); // sets position for map button

        map.addListener(new InputListener() { // enables the map button to toggle and enables/disables game/map groups
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {}
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (!settingsScreen.getSettingsGroup().isVisible() && !pausescreen && !table.isVisible()) {
                    if (previousState) {
                        showMap = true;
                        previousState = false;
                        gameScreenGroup.setVisible(false);
                        mapScreenGroup.setVisible(true);
                        turnManager.getCardManager().getMonsterManager().setVisible(false);
                        turnManager.getCardManager().getVirusManager().setVisible(false);
                    } else {
                        showMap = false;
                        previousState = true;
                        gameScreenGroup.setVisible(true);
                        mapScreenGroup.setVisible(false);
                        turnManager.getCardManager().getMonsterManager().setVisible(true);
                        turnManager.getCardManager().getVirusManager().setVisible(true);
                    }
                }
                return true;
            }
        });

        // adds actor to stage
        stage.addActor(map);

        Button mapReturn = new Button(game.skin, "return"); // return button in map screen
        mapReturn.setPosition(0, stage.getHeight() - 870);
        mapScreenGroup.setVisible(false); // makes map invisible by default
        mapReturn.addListener(new InputListener() { // allows going back to game screen from map
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {}
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                showMap = false;
                previousState = true;
                mapScreenGroup.setVisible(false);
                gameScreenGroup.setVisible(true);
                turnManager.getCardManager().getMonsterManager().setVisible(true);
                turnManager.getCardManager().getVirusManager().setVisible(true);
                return true;
            }
        });

        mapScreenGroup.addActor(mapReturn); // adds return button to group
        stage.addActor(mapScreenGroup); // adds group to stage

        stage.addActor(gameScreenGroup); // adds group to stage

        // money
        int money = 3; // sets money

        // imports texture atlas and sets text for health and money
        game.textureAtlas = new TextureAtlas(Gdx.files.internal("other/game-ui-2.atlas"));

        // health
        //healthVirus = new Label(String.valueOf((int) virusManager.getPlayer().getMaxHealth()), new Label.LabelStyle(game.font, Color.RED));
        healthVirus = new Label(String.valueOf((int) turnManager.getCardManager().getVirusManager().getPlayer().getMaxHealth()), new Label.LabelStyle(game.font, Color.RED));
        healthIcon = game.textureAtlas.findRegion("health");
        healthVirus.setPosition(100, stage.getHeight() - healthIcon.getRegionHeight() / 2 - 30);

        // money
        moneyValue = new Label(String.valueOf(money) + "\t MB", new Label.LabelStyle(game.font, Color.GOLD));
        moneyIcon = game.textureAtlas.findRegion("data");
        moneyValue.setPosition(healthVirus.getX() + healthVirus.getWidth() + 50 + 10, stage.getHeight() - healthIcon.getRegionHeight() / 2 - 30);

        stage.addActor(healthVirus); // adds actor to stage
        stage.addActor(moneyValue); // adds actor to stage

        // session timer
        sessionTimer = new Label("00:" + String.valueOf((int)elapsed_time), new Label.LabelStyle(game.font, Color.WHITE));
        sessionTimer.setPosition(map.getX()-100, map.getY());

        stage.addActor(sessionTimer);



        // disable pausescreen
        pausescreen = false;

        // actor group
        pauseGroup = new Group();
        settingsScreen = new Settings(game, stage);

        // pause button
        game.skin = new Skin(Gdx.files.internal("skin/game-ui.json")); // imports skin (json file)
        game.skin.addRegions(new TextureAtlas("skin/game-ui.atlas")); // adds regions

        final Button pause = new Button(game.skin, "pause");
        pause.setPosition(stage.getWidth() - 50,
                stage.getHeight() - pause.getHeight() / 2 - 30);

        pause.addListener(new InputListener() { // detects click and shows pause screen
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {}
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (!settingsScreen.getSettingsGroup().isVisible()) { // checks if settings isn't open
                    pausescreen = true;
                    pauseGroup.setVisible(true);
                }
                return true;
            }
        });
        stage.addActor(pause); // adds actor to stage

        // pause menu
        pausescreenBackground = game.skin.getRegion("pausescreen-background"); // sets region for pause screen background
        Window pauseScreenWindow = new Window("",game.skin);
        pauseScreenWindow.setPosition((stage.getWidth() - pausescreenBackground.getRegionWidth()) / 2,
                (stage.getHeight() - pausescreenBackground.getRegionHeight()) / 2);
        pauseScreenWindow.setSize(pausescreenBackground.getRegionWidth(), pausescreenBackground.getRegionHeight());
        pauseGroup.addActor(pauseScreenWindow);

        pauseScreenWindow.addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == Input.Keys.ESCAPE) {
                    pausescreen = false;
                    pauseGroup.setVisible(false);
                }
                return super.keyDown(event, keycode);
            }
        });


        Button resume = new TextButton("Resume", game.skin, "pausescreen-button"); // pause screen button
        resume.setPosition(
                (stage.getWidth() - pausescreenBackground.getRegionWidth()) / 2 + (pausescreenBackground.getRegionWidth() - resume.getWidth()) / 2,
                ((stage.getHeight() - pausescreenBackground.getRegionHeight()) / 2 - 50) + pausescreenBackground.getRegionHeight() - resume.getHeight());

        final Button settings = new TextButton("Settings", game.skin, "pausescreen-button"); // settings button in pause menu
        settings.setPosition(resume.getX(), resume.getY() - resume.getHeight() - 35); // sets position of settings button

        Button quit = new TextButton("Main Menu", game.skin, "pausescreen-button"); // quit button in pause menu
        quit.setPosition(settings.getX(), settings.getY() - resume.getHeight() - 35); // sets position of quit button

        // pause menu button listeners
        resume.addListener(new InputListener() { // detects click and closes pause screen
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {}
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                pausescreen = false;
                pauseGroup.setVisible(false);
                return true;
            }
        });


        settings.addListener(new InputListener() { // detects click and opens settings screen
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {}
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                pausescreen = false;
                pauseGroup.setVisible(false);
                gameScreenGroup.setVisible(false);
                settingsScreen.getSettingsGroup().setVisible(true);
                //turnManager.getCardManager().getMonsterManager().setVisible(false);
                //turnManager.getCardManager().getVirusManager().setVisible(false);
                mapScreenGroup.setVisible(false);
                showMap = false;
                previousState = true;
                return true;
            }
        });

        quit.addListener(new InputListener() { // detects click and quits to main menu
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {}
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new TitleScreen(game));
                dispose();
                return true;
            }
        });

        // adding pausescreen buttons to actor group
        pauseGroup.addActor(resume);
        pauseGroup.addActor(settings);
        pauseGroup.addActor(quit);
        pauseGroup.setVisible(false); // makes pausescreen invisible by default

        // adding group to stage
        stage.addActor(pauseGroup);

        game.skin = new Skin(Gdx.files.internal("skin/game-ui.json"));
        game.skin.addRegions(new TextureAtlas("skin/game-ui.atlas"));

        final Button deck = new Button(game.skin, "deck");
        deck.setPosition(map.getX() + map.getWidth() + 5, map.getY());

        final Group deckScreenGroup = new Group();
        table = new Table(game.skin);


        deck.addListener(new InputListener() {
            boolean showDeck = false;
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (showDeck) {
                    showDeck = false;
                    table.setVisible(false);
                    deckScreenGroup.setVisible(false);
                    gameScreenGroup.setVisible(true);

                } else {
                    if (!mapScreenGroup.isVisible()) {
                        showDeck = true;
                        table.setVisible(true);
                        deckScreenGroup.setVisible(true);
                        gameScreenGroup.setVisible(false);
                    }
                }
                return true;
            }
        });

        stage.addActor(deck);


        game.skin = new Skin(Gdx.files.internal("skin/game-ui.json"));
        game.skin.addRegions(new TextureAtlas("skin/game-ui.atlas"));

        //setup deckscreen scollwindow
        table.setVisible(false);
        table.setBounds(0,0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        table.setSize(stage.getWidth(), stage.getHeight()-60);
        table.setOrigin(Align.center);
        table.align(Align.center);
        //table.debug();
        Table displayDeck = turnManager.getCardManager().getdisplayDeck();
        displayDeck.setSize(stage.getWidth(), stage.getHeight());
        displayDeck.setOrigin(Align.center);
        displayDeck.align(Align.center);
        displayDeck.setBounds(displayDeck.getX(), displayDeck.getY(), stage.getWidth(), stage.getHeight()-60);
        ScrollPane scrollPane = new ScrollPane(displayDeck, game.skin);
        scrollPane.setSize(stage.getWidth(), stage.getHeight());
        scrollPane.setOrigin(Align.center);
        scrollPane.validate();
        table.add("Deck").row();
        table.add(scrollPane);

        stage.addActor(table);
        deckScreenGroup.addActor(table);


        Button deckReturn = new Button(game.skin, "return");
        deckReturn.setPosition(stage.getWidth()-deckReturn.getWidth(), 100);
        deckReturn.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {}

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                deckScreenGroup.setVisible(false);
                table.setVisible(false);
                gameScreenGroup.setVisible(true);
                return true;
            }
        });

        deckScreenGroup.setVisible(false);
        deckScreenGroup.addActor(deckReturn);
        stage.addActor(deckScreenGroup);

    }

    @Override
    public void render(float delta) {
        // clear screen
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // delta = the time in seconds since last render
        elapsed_time += delta;

        turnManager.getCardManager().setElapsed_time(elapsed_time);

        int mins = (int) elapsed_time/60;
        int secs = (int) elapsed_time%60;

        if (secs < 10) {
            sessionTimer.setText(mins + ":0" + secs);
        } else {
            sessionTimer.setText(mins + ":" + secs);
        }



        moneyValue.setText(money);


        game.batch.begin();

        // draw background
        game.batch.draw(gameBackground,
                0,
                0,
                1920,
                1080);

        // renders buffs and debuffs virus
        if (effects.size() > 0) {
            int padding = 0;
            for (int i = 0; i < effects.size(); i++) {
                game.batch.draw(icons.get(i), 400 + padding, 1080 - 805);
                padding += 25;
            }
        }

        // renders buffs and debuffs enemy
        if (effectsEnemy.size() > 0) {
            int padding = 0;
            for (int i = 0; i < effectsEnemy.size(); i++) {
                game.batch.draw(iconsEnemy.get(i), 1480 + padding, 1080 - 805);
                padding += 25;
            }
        }

        // renders items
        if (items.size() > 0) {
            int padding = 0;
            for (int i = 0; i < items.size(); i++) {
                game.batch.draw(relicIcons.get(i), 25, stage.getHeight() - hudbar.getRegionHeight() * 2 + 3);
                padding += 25;
            }
        }

        // shows map if enabled
        if (showMap) {
            game.batch.draw(mapBackground, 0, 0, 1920, 1080);
        }

        // draw hud-bar
        game.batch.draw(hudbar, 0, stage.getHeight() - hudbar.getRegionHeight());

        // renders health and money icon
        game.batch.draw(
                healthIcon,
                healthVirus.getX() + healthVirus.getWidth() + 10,
                stage.getHeight() - healthIcon.getRegionHeight() + healthIcon.getRegionHeight() / 4 - 30);
        game.batch.draw(
                moneyIcon,
                moneyValue.getX() + moneyValue.getWidth() + 10,
                stage.getHeight() - moneyIcon.getRegionHeight() + moneyIcon.getRegionHeight() / 4 - 30);

        // shows pausescreen if enabled
        /*
        if (pausescreen) {
            game.batch.draw(
                    pausescreenBackground,
                    (stage.getWidth() - pausescreenBackground.getRegionWidth()) / 2,
                    (stage.getHeight() - pausescreenBackground.getRegionHeight()) / 2);
        }

         */

        game.batch.end();

        turnManager.drawMonster();
        turnManager.drawVirus();


        game.batch.begin();
        stage.act(delta);
        stage.draw(); // renders actors on screen



        game.batch.end();

        // renders settingsscreen if enabled
        if (settingsScreen.getSettingsGroup().isVisible()) {
            settingsScreen.render(delta, gameScreenGroup);
        }

        turnManager.getCardManager().renderHand();



    }


    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void show() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
        settingsScreen.dispose();
    }
}
