package com.mygdx.game.screen;

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
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.GipGameProject;
import com.mygdx.game.SessionManager;
import com.mygdx.game.screen.settings.SettingsScreen;

import java.util.Random;


// class where all game logic goes

public class GameScreen implements Screen {

    final private GipGameProject game;

    // variable for tracking elapsed time
    private float elapsed_time;


    // stage for drawing actors
    private final Stage stage;

    // group for adding actors
    private Group gameScreenGroup;
    private Group forGroundGroup;
    private Group backgroundGroup;
    private final ImageTextButton exhaustpile;


    // currency & health value
    private final Label healthVirus;
    private final Label moneyValue;
    private final Label sessionTimer;

    // group for UI so they can be shown and hidden
    private final Group pauseGroup;
    private boolean pausescreen; // decides whether pause screen should be shown
    private final SettingsScreen settingsScreen;

    // turnmanager (later sessionmanager) ☻

    private final SessionManager sessionManager;

    public GameScreen(final GipGameProject game) {
        this.game = game;


        // game stage
        stage = new Stage(new ScreenViewport());
        backgroundGroup = new Group();
        stage.addActor(backgroundGroup);

        Gdx.input.setInputProcessor(stage); // allows stage to take input


        // groups determine which UI should be shown on screen
        gameScreenGroup = new Group();
        forGroundGroup = new Group();

        // game background
        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("other/game-ui-2.atlas"));

        TextureRegion gameBackgroundRegion = atlas.findRegion("guidelines");
        Image background = new Image(new TextureRegionDrawable(gameBackgroundRegion));
        background.setPosition(0, 0);
        background.setSize(stage.getWidth(), stage.getHeight());
        backgroundGroup.addActor(background);


        // game UI
        game.textureAtlas = new TextureAtlas("skin/game-ui.atlas");
        game.skin = new Skin(Gdx.files.internal("skin/game-ui.json"));
        game.skin.addRegions(new TextureAtlas("skin/game-ui.atlas"));


        // groups determine which UI should be shown on screen
        gameScreenGroup = new Group();


        // hudbar texture region
        TextureRegion hudbarRegion = atlas.findRegion("hud-bar");
        final Image hudbar = new Image(new TextureRegionDrawable(hudbarRegion));
        hudbar.setPosition(0, stage.getHeight() - hudbar.getHeight());
        backgroundGroup.addActor(hudbar);
        // initialize sessionmanager
        sessionManager = new SessionManager(game, stage, gameScreenGroup, forGroundGroup, this);







        stage.addActor(gameScreenGroup); // adds group to stage

        // money
        int money = 3; // sets money

        //healthVirus = new Label(String.valueOf((int) virusManager.getPlayer().gethealth()), new Label.LabelStyle(game.font, Color.RED));
        healthVirus = new Label(String.valueOf(sessionManager.getTurnManager().getCardManager().getVirusManager().getPlayer().getHealth()), new Label.LabelStyle(game.font, Color.RED));
        // icons health & money texture regions
        Image healthIcon = new Image(new TextureRegionDrawable(atlas.findRegion("health")));
        healthIcon.setPosition(healthVirus.getX() + healthVirus.getWidth() + 10,
                stage.getHeight() - healthIcon.getHeight() + healthIcon.getHeight() / 4 - 30);
        healthVirus.setPosition(100, stage.getHeight() - healthIcon.getHeight() / 2 - 30);

        backgroundGroup.addActor(healthVirus);
        backgroundGroup.addActor(healthIcon);

        // money
        moneyValue = new Label(money + "\t MB", new Label.LabelStyle(game.font, Color.GOLD));
        Image moneyIcon = new Image(new TextureRegionDrawable(atlas.findRegion("data")));
        moneyValue.setPosition(healthVirus.getX() + healthVirus.getWidth() + 50 + 10, stage.getHeight() - healthIcon.getHeight() / 2 - 30);
        moneyIcon.setPosition(moneyValue.getX() + moneyValue.getWidth() + 10,
                stage.getHeight() - moneyIcon.getHeight() + moneyIcon.getHeight() / 4 - 30);

        backgroundGroup.addActor(moneyIcon);
        backgroundGroup.addActor(moneyValue); // adds actor to stage

        // session timer
        sessionTimer = new Label("00:" + (int) elapsed_time, new Label.LabelStyle(game.font, Color.WHITE));
        sessionTimer.setPosition(sessionManager.getMap().getMapButton().getX()-100, sessionManager.getMap().getMapButton().getY());

        backgroundGroup.addActor(sessionTimer);



        // disable pausescreen
        pausescreen = false;

        // actor group
        pauseGroup = new Group();
        settingsScreen = new SettingsScreen(game, stage);

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
        TextureRegion pausescreenBackground = game.skin.getRegion("pausescreen-background"); // sets region for pause screen background
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
                sessionManager.getMap().getMapScreenGroup().setVisible(false);
                sessionManager.getMap().setShowMap(false);
                sessionManager.getMap().setPreviousState(true);
                return true;
            }
        });

        quit.addListener(new InputListener() { // detects click and quits to main menu
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {}
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new TitleScreen(game));
                sessionManager.getSaveManager().save();
                dispose();
                return true;
            }
        });

        // adding pausescreen buttons to actor group
        pauseScreenWindow.add(resume).pad(20).row();
        pauseScreenWindow.add(settings).pad(20).row();
        pauseScreenWindow.add(quit).pad(20).row();
        //pauseScreenWindow.setVisible(false); // makes pausescreen invisible by default
        pauseGroup.setVisible(false);

        // adding group to stage
        stage.addActor(pauseGroup);

        final Button deck = new Button(game.skin, "deck");
        deck.setPosition(sessionManager.getMap().getMapButton().getX() + sessionManager.getMap().getMapButton().getWidth() + 5, sessionManager.getMap().getMapButton().getY());

        deck.addListener(new InputListener() {
            boolean showDeck = false;
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (showDeck) {
                    showDeck = false;
                    sessionManager.getTurnManager().getCardManager().getTableGroup().getChild(0).setVisible(false);
                    sessionManager.getTurnManager().getCardManager().getDeckScreenGroup().setVisible(false);
                    gameScreenGroup.setVisible(true);

                } else {
                    if (!sessionManager.getMap().getMapScreenGroup().isVisible()) {
                        showDeck = true;
                        sessionManager.getTurnManager().getCardManager().getTableGroup().getChild(0).setVisible(true);
                        sessionManager.getTurnManager().getCardManager().getDeckScreenGroup().setVisible(true);
                        gameScreenGroup.setVisible(false);
                    }
                }
                return true;
            }
        });
        stage.addActor(deck);

        game.skin = new Skin(Gdx.files.internal("skin/game-ui.json"));
        game.skin.addRegions(new TextureAtlas("skin/game-ui.atlas"));

        Button drawpile = new Button(game.skin, "drawpile");
        drawpile.setPosition(0 ,0);
        gameScreenGroup.addActor(drawpile);
        drawpile.addListener(new InputListener() {
            boolean showDeck = false;
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (!sessionManager.getMap().getMapScreenGroup().isVisible()) {
                    showDeck = true;
                    sessionManager.getTurnManager().getCardManager().getTableGroup().getChild(1).setVisible(true);
                    sessionManager.getTurnManager().getCardManager().getDeckScreenGroup().setVisible(true);
                    gameScreenGroup.setVisible(false);
                }

                return true;
            }
        });

        gameScreenGroup.addActor(drawpile);

        final Button discardpile = new Button(game.skin, "discardpile");
        System.out.println("Width " + discardpile.getWidth() + " Height " + discardpile.getHeight());
        discardpile.setPosition(stage.getWidth()-discardpile.getWidth(), 0);
        gameScreenGroup.addActor(discardpile);

        discardpile.addListener(new InputListener() {
            boolean showDeck = false;
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (!sessionManager.getMap().getMapScreenGroup().isVisible()) {
                    showDeck = true;
                    sessionManager.getTurnManager().getCardManager().getTableGroup().getChild(2).setVisible(true);
                    sessionManager.getTurnManager().getCardManager().getDeckScreenGroup().setVisible(true);
                    gameScreenGroup.setVisible(false);
                }

                return true;
            }
        });
        exhaustpile = new ImageTextButton(String.valueOf(sessionManager.getTurnManager().getCardManager().getExhaustPile().size), game.skin);
        exhaustpile.setPosition(stage.getWidth()-exhaustpile.getWidth() ,discardpile.getY()+discardpile.getHeight());
        gameScreenGroup.addActor(exhaustpile);
        exhaustpile.addListener(new InputListener() {
            boolean showDeck = false;
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (!sessionManager.getMap().getMapScreenGroup().isVisible()) {
                    showDeck = true;
                    sessionManager.getTurnManager().getCardManager().getTableGroup().getChild(3).setVisible(true);
                    sessionManager.getTurnManager().getCardManager().getDeckScreenGroup().setVisible(true);
                    gameScreenGroup.setVisible(false);
                }

                return true;
            }
        });

        gameScreenGroup.addActor(exhaustpile);
        stage.addActor(forGroundGroup);
        Random random = new Random();

    }

    @Override
    public void render(float delta) {
        // clear screen
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // delta = the time in seconds since last render
        elapsed_time += delta;

        sessionManager.getTurnManager().getCardManager().setElapsed_time(elapsed_time);

        int mins = (int) elapsed_time/60;
        int secs = (int) elapsed_time%60;

        if (secs < 10) {
            sessionTimer.setText(mins + ":0" + secs);
        } else {
            sessionTimer.setText(mins + ":" + secs);
        }


        exhaustpile.setText(String.valueOf(sessionManager.getTurnManager().getCardManager().getExhaustPile().size));
        moneyValue.setText(sessionManager.getTurnManager().getCardManager().getVirusManager().getPlayer().getSTARTING_MONEY());


        game.batch.begin();


        healthVirus.setText(sessionManager.getTurnManager().getCardManager().getVirusManager().getPlayer().getHealth());
        game.batch.end();

        sessionManager.getTurnManager().getCardManager().getMonsterManager().drawMonster();
        sessionManager.getTurnManager().getCardManager().getVirusManager().drawVirus();


        game.batch.begin();
        stage.act(delta);
        stage.draw(); // renders actors on screen



        game.batch.end();

        sessionManager.getTurnManager().getCardManager().renderHand();

        // renders settingsscreen if enabled
        if (settingsScreen.getSettingsGroup().isVisible()) {
            settingsScreen.render(delta, gameScreenGroup);
        }


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



    public GipGameProject getGame() {
        return game;
    }

    public SessionManager getSessionManager() {
        return sessionManager;
    }

    public Stage getStage() {
        return stage;
    }

    public Group getGameScreenGroup() {
        return gameScreenGroup;
    }

    public Group getPauseGroup() {
        return pauseGroup;
    }

    public boolean isPausescreen() {
        return pausescreen;
    }

    public SettingsScreen getSettingsScreen() {
        return settingsScreen;
    }

    public Group getBackgroundGroup() {
        return backgroundGroup;
    }
}
