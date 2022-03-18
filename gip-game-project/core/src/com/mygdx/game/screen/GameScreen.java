package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.GipGameProject;
import com.mygdx.game.SessionManager;
import com.mygdx.game.effect.Effect;
import com.mygdx.game.item.Item;


// class where all game logic goes

public class GameScreen implements Screen {

    final private GipGameProject game;

    // variable for tracking elapsed time
    private float elapsed_time;

    private Image gameBackground; // background texture region
    private Image hudbar; // hud bar texture region
    private Image mapBackground; // map texture region

    private boolean showMap; // determines whether map should be shown
    private boolean previousState = true; // previous state of map button

    private Table table;
    private Table drawTable;
    private Table discardTable;

    // virus
    private Array<Effect> effects = new Array<>(); // buffs and debuffs arraylist
    private Array<Image> icons = new Array<>();
    private Array<Image> iconsEnem = new Array<>();

    private Array<Item> items= new Array<>(); // items arraylist
    private Array<Image> itemIcons = new Array<>();

    // enemy
    private final Array<Effect> effectsEnemy = new Array<>(); // buffs and debuffs arraylist


    // effects
    //private final Effect strength = new Effect("strength", 1, 1, 5); // strength buff
    //private final Effect dexterity = new Effect("dexterity", 1, 1, 5); // dexterity buff

    // item
    private final Item dataDisk = new Item("data-disk", "common", "Gain 100 megabytes after every combat");

    // stage for drawing actors
    private final Stage stage;

    // group for adding actors
    private Group gameScreenGroup;
    private Group mapScreenGroup;
    private Group backgroundGroup;
    private ImageTextButton exhaustpile;

    // icons health & money texture regions
    private final Image healthIcon;
    private final Image moneyIcon;

    // currency & health value
    private final Label healthVirus;
    private final Label moneyValue;

    private int money;
    private final Label sessionTimer;

    // group for UI so they can be shown and hidden
    private final Group pauseGroup;
    private final TextureRegion pausescreenBackground;
    private boolean pausescreen; // decides whether pause screen should be shown
    private final Settings settingsScreen;

    // turnmanager (later sessionmanager) ☻
    // private TurnManager turnManager;

    private final SessionManager sessionManager;

    public GameScreen(final GipGameProject game) {
        this.game = game;

        // adding effects (later this will be added by playing cards or having certain items)
        //effects.add(strength);
        //effects.add(dexterity);

        //effectsEnemy.add(dexterity);
        //effectsEnemy.add(strength);

        // adding items
        items.add(dataDisk);

        // game stage
        stage = new Stage(new ScreenViewport());
        backgroundGroup = new Group();
        stage.addActor(backgroundGroup);

        Gdx.input.setInputProcessor(stage); // allows stage to take input

        // groups determine which UI should be shown on screen
        gameScreenGroup = new Group();
        mapScreenGroup = new Group();

        // initialize turnmanager
        // turnManager = new TurnManager(game, stage, gameScreenGroup);


        // game background
        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("other/game-ui-2.atlas"));

        TextureRegion gameBackgroundRegion = atlas.findRegion("guidelines");
        Image background = new Image(new TextureRegionDrawable(gameBackgroundRegion));
        background.setPosition(0, 0);
        background.setSize(stage.getWidth(), stage.getHeight());
        backgroundGroup.addActor(background);

        // map texture region
        TextureRegion mapBackgroundRegion = atlas.findRegion("map-background");
        Image mapBackground = new Image(new TextureRegionDrawable(mapBackgroundRegion)) {
            @Override
            public void draw(Batch batch, float parentAlpha) {
                if (showMap) {
                    super.draw(batch, parentAlpha);
                }
            }
        };
        mapBackground.setPosition(0, 0);
        backgroundGroup.addActor(mapBackground);

        // hudbar texture region
        TextureRegion hudbarRegion = atlas.findRegion("hud-bar");
        final Image hudbar = new Image(new TextureRegionDrawable(hudbarRegion));
        hudbar.setPosition(0, stage.getHeight() - hudbar.getHeight());
        backgroundGroup.addActor(hudbar);

        // effect icons
        for (int i = 0; i < effects.size; i++) { // adds names of regions to textureregion arraylist (virus)
            //TextureRegion iconRegion = atlas.findRegion(effects.get(i).getEffectName());
            //Image effectIcon = new Image(new TextureRegionDrawable(iconRegion));
           // icons.add(effectIcon);
           // backgroundGroup.addActor(effectIcon);
        }


        for (int i = 0; i < effectsEnemy.size; i++) { // adds names of regions to textureregion arraylist (enemy)
            //TextureRegion iconRegion = atlas.findRegion(effects.get(i).getEffectName());
            //Image effectIcon = new Image(new TextureRegionDrawable(iconRegion));
           // iconsEnemy.add(effectIcon);
           // backgroundGroup.addActor(effectIcon);
        }

        // item icons
        for (int i = 0; i < items.size; i++) { // adds names of regions to textureregion arraylist (items)
          //  TextureRegion iconRegion = atlas.findRegion(items.get(i).getItemName());
          //  Image itemIcon = new Image(new TextureRegionDrawable(iconRegion));
           // itemIcons.add(itemIcon);
         //   backgroundGroup.addActor(itemIcon);
        }


        // groups determine which UI should be shown on screen
        gameScreenGroup = new Group();
        mapScreenGroup = new Group();

        // initialize sessionmanager
        sessionManager = new SessionManager(game, stage, gameScreenGroup);

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
                if (!settingsScreen.getSettingsGroup().isVisible() && !pausescreen) {
                    if (!sessionManager.getTurnManager().getCardManager().getTableGroup().getChild(0).isVisible() && !sessionManager.getTurnManager().getCardManager().getTableGroup().getChild(1).isVisible() && !sessionManager.getTurnManager().getCardManager().getTableGroup().getChild(2).isVisible() && !sessionManager.getTurnManager().getCardManager().getTableGroup().getChild(3).isVisible()){
                        if (previousState) {
                            showMap = true;
                            previousState = false;
                            gameScreenGroup.setVisible(false);
                            mapScreenGroup.setVisible(true);
                        } else {
                            showMap = false;
                            previousState = true;
                            gameScreenGroup.setVisible(true);
                            mapScreenGroup.setVisible(false);
                        }
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
                //sessionManager.getTurnManager().getCardManager().getMonsterManager().setVisible(true);
                //sessionManager.getTurnManager().getCardManager().getVirusManager().setVisible(true);
                return true;
            }
        });

        mapScreenGroup.addActor(mapReturn); // adds return button to group
        stage.addActor(mapScreenGroup); // adds group to stage

        stage.addActor(gameScreenGroup); // adds group to stage

        // money
        int money = 3; // sets money

        // imports texture atlas and sets text for health and money


        // health

        //healthVirus = new Label(String.valueOf((int) virusManager.getPlayer().gethealth()), new Label.LabelStyle(game.font, Color.RED));
        healthVirus = new Label(String.valueOf(sessionManager.getTurnManager().getCardManager().getVirusManager().getPlayer().getHealth()), new Label.LabelStyle(game.font, Color.RED));
        healthIcon = new Image(new TextureRegionDrawable(atlas.findRegion("health")));
        healthIcon.setPosition(healthVirus.getX() + healthVirus.getWidth() + 10,
                stage.getHeight() - healthIcon.getHeight() + healthIcon.getHeight() / 4 - 30);
        healthVirus.setPosition(100, stage.getHeight() - healthIcon.getHeight() / 2 - 30);

        backgroundGroup.addActor(healthVirus);
        backgroundGroup.addActor(healthIcon);

        // money
        moneyValue = new Label(money + "\t MB", new Label.LabelStyle(game.font, Color.GOLD));
        moneyIcon = new Image(new TextureRegionDrawable(atlas.findRegion("data")));
        moneyValue.setPosition(healthVirus.getX() + healthVirus.getWidth() + 50 + 10, stage.getHeight() - healthIcon.getHeight() / 2 - 30);
        moneyIcon.setPosition(moneyValue.getX() + moneyValue.getWidth() + 10,
                stage.getHeight() - moneyIcon.getHeight() + moneyIcon.getHeight() / 4 - 30);

        backgroundGroup.addActor(moneyIcon);
        backgroundGroup.addActor(moneyValue); // adds actor to stage

        // session timer
        sessionTimer = new Label("00:" + (int) elapsed_time, new Label.LabelStyle(game.font, Color.WHITE));
        sessionTimer.setPosition(map.getX()-100, map.getY());

        backgroundGroup.addActor(sessionTimer);



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
        pauseScreenWindow.add(resume).pad(20).row();
        pauseScreenWindow.add(settings).pad(20).row();
        pauseScreenWindow.add(quit).pad(20).row();
        //pauseScreenWindow.setVisible(false); // makes pausescreen invisible by default
        pauseGroup.setVisible(false);

        // adding group to stage
        stage.addActor(pauseGroup);

        final Button deck = new Button(game.skin, "deck");
        deck.setPosition(map.getX() + map.getWidth() + 5, map.getY());

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
                    if (!mapScreenGroup.isVisible()) {
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
                if (!mapScreenGroup.isVisible()) {
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
        //discardpile.setScale(0.17F);
        //discardpile.setSize(discardpile.getWidth()*discardpile.getScaleX(), discardpile.getHeight()*discardpile.getScaleY());
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
                if (!mapScreenGroup.isVisible()) {
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
                if (!mapScreenGroup.isVisible()) {
                    showDeck = true;
                    sessionManager.getTurnManager().getCardManager().getTableGroup().getChild(3).setVisible(true);
                    sessionManager.getTurnManager().getCardManager().getDeckScreenGroup().setVisible(true);
                    gameScreenGroup.setVisible(false);
                }

                return true;
            }
        });

        gameScreenGroup.addActor(exhaustpile);
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
}
