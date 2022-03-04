package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.cards.CardManager;
import com.mygdx.game.effect.Effect;
import com.mygdx.game.monster.Monster;
import com.mygdx.game.item.Item;
import com.mygdx.game.virus.Virus;

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

    // effects texture regions
    private ArrayList<TextureRegion> icons = new ArrayList<>();
    private ArrayList<TextureRegion> iconsEnemy = new ArrayList<>();

    // items texture region
    private ArrayList<TextureRegion> relicIcons = new ArrayList<>();

    // virus
    private ArrayList<Effect> effects = new ArrayList<>(); // buffs and debuffs arraylist
    private Virus eva = new Virus("Eva", 33, 0);
    private ArrayList<Item> items = new ArrayList<>(); // items arraylist

    // enemy
    private ArrayList<Effect> effectsEnemy = new ArrayList<>(); // buffs and debuffs arraylist
    private Monster philip = new Monster(1,"Philip", 100, 0);

    // effects
    private Effect strength = new Effect("strength", 1, 1, 5); // strength buff
    private Effect dexterity = new Effect("dexterity", 1, 1, 5); // dexterity buff

    // item
    private Item dataDisk = new Item("data-disk", "common", "Gain 100 megabytes after every combat");

    // animation object virus
    private Animation<TextureRegion> idleAnimation;

    // animation object enemy
    private Animation<TextureRegion> enemyIdleAnimation;

    // health bars
    private ProgressBar health;
    private ProgressBar healthEnemy;

    private float x;
    private float y;

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

    // group for UI so they can be shown and hidden
    private final Group pauseGroup;
    private final TextureRegion pausescreenBackground;
    private boolean pausescreen; // decides whether pause screen should be shown
    private final Settings settingsScreen;

    private CardManager cardManager;

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
                if (!settingsScreen.getSettingsGroup().isVisible() && !pausescreen) {
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
                return true;
            }
        });

        mapScreenGroup.addActor(mapReturn); // adds return button to group
        stage.addActor(mapScreenGroup); // adds group to stage

        // atlas for virus and enemy textures (animation)
        TextureAtlas idleSet = new TextureAtlas(Gdx.files.internal("animation/idle.atlas"));
        idleAnimation = new Animation<TextureRegion>(1 / 10f, idleSet.findRegions("idle"));
        idleAnimation.setFrameDuration(1 / 10f);

        TextureAtlas enemyIdleSet = new TextureAtlas(Gdx.files.internal("animation/enemyidle.atlas"));
        enemyIdleAnimation = new Animation<TextureRegion>(1 / 10f, enemyIdleSet.findRegions("enemyidle"));
        enemyIdleAnimation.setFrameDuration(1 / 10f);

        // ghost actor (detects mouse hover to show name)
        Actor nameAreaVirus = new Actor();

        nameAreaVirus.setSize(idleAnimation.getKeyFrame(0).getRegionWidth() * 6, // sets size of area to check
                idleAnimation.getKeyFrame(0).getRegionHeight() * 6);

        nameAreaVirus.setPosition(eva.getPositionX(), stage.getHeight() - 750); // sets position of checking area

        final Label nameVirus = new Label("Virus", new Label.LabelStyle(game.font, Color.WHITE)); // label to show name

        x = nameAreaVirus.getX() + (nameAreaVirus.getWidth() - nameVirus.getWidth()) / 2;
        y = nameAreaVirus.getY() - 70;
        nameVirus.setPosition(x, y); // sets position of label

        // adds actor to group
        gameScreenGroup.addActor(nameVirus);
        // player name invisible by default
        nameVirus.setVisible(false);

        nameAreaVirus.addListener(new ClickListener() { // detects if hovering over player
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                nameVirus.setVisible(true);
                game.log.debug("hover " + idleAnimation.getKeyFrame(0).getRegionWidth() + " " + idleAnimation.getKeyFrame(0).getRegionHeight());
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                nameVirus.setVisible(false);
                game.log.debug("not hover");
            }
        });

        stage.addActor(nameAreaVirus); // adds actor to stage

        Actor nameAreaEnemy = new Actor();

        nameAreaEnemy.setSize(enemyIdleAnimation.getKeyFrame(0).getRegionWidth() * 6,
                enemyIdleAnimation.getKeyFrame(0).getRegionHeight() * 6);
        nameAreaEnemy.setPosition(philip.getPositionX(), stage.getHeight() - 750);

        final Label nameEnemy = new Label("Monster", new Label.LabelStyle(game.font, Color.WHITE));

        x = nameAreaEnemy.getX() + (nameAreaEnemy.getWidth() - nameEnemy.getWidth()) / 2;
        y = nameAreaEnemy.getY() - 70;
        nameEnemy.setPosition(x, y);

        gameScreenGroup.addActor(nameEnemy);

        nameEnemy.setVisible(false);
        nameAreaEnemy.addListener(new ClickListener() { // detects if hovering over enemy
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                nameEnemy.setVisible(true);
                game.log.debug("hover " + enemyIdleAnimation.getKeyFrame(0).getRegionWidth() + " " + enemyIdleAnimation.getKeyFrame(0).getRegionHeight());
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                nameEnemy.setVisible(false);
                game.log.debug("nein hover");
            }
        });

        stage.addActor(nameAreaEnemy); // adds actor to stage

        // initializing health bar virus
        health = new ProgressBar(0, eva.getHealth(), 1, false, game.skin); // health bar virus
        health.setValue(eva.getHealth()); // sets current health
        health.setPosition(eva.getPositionX(), stage.getHeight() - 780); // sets position of health bar
        gameScreenGroup.addActor(health); // adds actor to group

        // initializing health bar enemy
        healthEnemy = new ProgressBar(0, 100, 1, false, game.skin); // health bar enemy
        healthEnemy.setValue(philip.getHealth()); // sets current health
        healthEnemy.setPosition(philip.getPositionX(), stage.getHeight() - 780); // sets position of health bar
        gameScreenGroup.addActor(healthEnemy); // adds actor to group

        stage.addActor(gameScreenGroup); // adds group to stage

        // money
        int money = 3; // sets money

        // imports texture atlas and sets text for health and money
        game.textureAtlas = new TextureAtlas(Gdx.files.internal("other/game-ui-2.atlas"));

        // health
        healthVirus = new Label(String.valueOf((int) health.getValue()), new Label.LabelStyle(game.font, Color.RED));
        healthIcon = game.textureAtlas.findRegion("health");
        healthVirus.setPosition(100, stage.getHeight() - healthIcon.getRegionHeight() / 2 - 30);

        // money
        moneyValue = new Label(String.valueOf(money) + "\t MB", new Label.LabelStyle(game.font, Color.GOLD));
        moneyIcon = game.textureAtlas.findRegion("data");
        moneyValue.setPosition(healthVirus.getX() + healthVirus.getWidth() + 50 + 10, stage.getHeight() - healthIcon.getRegionHeight() / 2 - 30);

        stage.addActor(healthVirus); // adds actor to stage
        stage.addActor(moneyValue); // adds actor to stage

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

        Button resume = new TextButton("Resume", game.skin, "pausescreen-button"); // pause screen button
        resume.setPosition(
                (stage.getWidth() - pausescreenBackground.getRegionWidth()) / 2 + (pausescreenBackground.getRegionWidth() - resume.getWidth()) / 2,
                ((stage.getHeight() - pausescreenBackground.getRegionHeight()) / 2 - 50) + pausescreenBackground.getRegionHeight() - resume.getHeight());

        final Button settings = new TextButton("Settings", game.skin, "pausescreen-button"); // settings button in pause menu
        settings.setPosition(resume.getX(), resume.getY() - resume.getHeight() - 35); // sets position of settings button

        Button quit = new TextButton("Main Menu", game.skin, "pausescreen-button"); // auit button in pause menu
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

        game.skin = new Skin(Gdx.files.internal("skin/game-ui001.json"));
        game.skin.addRegions(new TextureAtlas("skin/game-ui001.atlas"));
        final Table table = new Table(game.skin);










        final Button deck = new Button(game.skin, "deck");
        deck.setPosition(1500, 100);

        deck.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (table.isVisible()) {
                    table.setVisible(false);
                } else {
                    table.setVisible(true);
                }
                return true;
            }
        });

        gameScreenGroup.addActor(deck);



        cardManager = new CardManager(eva, philip, game, stage);
        cardManager.addCard(0);
        cardManager.addCard(1);
        cardManager.addCard(0);
        cardManager.addCard(1);
        cardManager.addCard(0);

        cardManager.addCard(0);
        cardManager.addCard(1);
        cardManager.addCard(0);
        cardManager.addCard(1);
        cardManager.addCard(0);
        cardManager.addCard(0);
        cardManager.addCard(1);
        cardManager.addCard(0);
        cardManager.addCard(1);
        cardManager.addCard(0);
        cardManager.addCard(0);
        cardManager.addCard(1);
        cardManager.addCard(0);
        cardManager.addCard(1);
        cardManager.addCard(0);
        //cardManager.drawcard(2);

        gameScreenGroup.addActor(cardManager.getHand());


        game.skin = new Skin(Gdx.files.internal("skin/game-ui001.json"));
        game.skin.addRegions(new TextureAtlas("skin/game-ui001.atlas"));

        //setup deckscreen scollwindow
        table.setVisible(false);
        table.setBounds(0,0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        table.setSize(stage.getWidth(), stage.getHeight());
        table.setOrigin(Align.center);
        table.align(Align.center);
        table.debug();
        Table displayDeck = cardManager.getdisplayDeck();
        displayDeck.setSize(stage.getWidth(), stage.getHeight()-50);
        displayDeck.setOrigin(Align.center);
        displayDeck.align(Align.center);
        ScrollPane scrollPane = new ScrollPane(displayDeck, game.skin);
        scrollPane.setSize(stage.getWidth(), stage.getHeight());
        scrollPane.setOrigin(Align.center);
        scrollPane.validate();
        table.add("Deck").row();
        table.add(scrollPane);

        stage.addActor(table);





        Table table1 = new Table();
        table1.setWidth(stage.getWidth());
        table1.align(Align.center|Align.top);
        table1.setPosition(0, Gdx.graphics.getHeight());
        Texture texture = new Texture(Gdx.files.internal("cards/uncommon_0_card.png"));
        Image image = new Image(texture);
        Image image1 = new Image(texture);
        Image image2 = new Image(texture);
        table1.add(image).size(image.getWidth(), image.getHeight());
        table1.add(image1);
        //stage.addActor(table1);
    }

    @Override
    public void render(float delta) {
        // clear screen
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // delta = the time in seconds since last render
        elapsed_time += delta;

        // getting the right frame for the animation
        TextureRegion currentFrame = idleAnimation.getKeyFrame(elapsed_time, true);
        TextureRegion currentFrameEnemy = enemyIdleAnimation.getKeyFrame(elapsed_time, true);

        game.batch.begin();

        // draw background
        game.batch.draw(gameBackground,
                0,
                0,
                1920,
                1080);

        // draw virus
        game.batch.draw(currentFrame,
                eva.getPositionX(),
                stage.getHeight() - 750,
                currentFrame.getRegionWidth() * 6,
                currentFrame.getRegionHeight() * 6);

        // draw enemy
        game.batch.draw(currentFrameEnemy,
                philip.getPositionX(),
                stage.getHeight() - 750,
                currentFrameEnemy.getRegionWidth() * 6,
                currentFrameEnemy.getRegionHeight() * 6);

        // renders buffs and debuffs virus
        if (effects.size() > 0) {
            int padding = 0;
            for (int i = 0; i < effects.size(); i++) {
                game.batch.draw(icons.get(i), eva.getPositionX() + padding, 1080 - 805);
                padding += 25;
            }
        }

        // renders buffs and debuffs enemy
        if (effectsEnemy.size() > 0) {
            int padding = 0;
            for (int i = 0; i < effectsEnemy.size(); i++) {
                game.batch.draw(iconsEnemy.get(i), philip.getPositionX() + padding, 1080 - 805);
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
        if (pausescreen) {
            game.batch.draw(
                    pausescreenBackground,
                    (stage.getWidth() - pausescreenBackground.getRegionWidth()) / 2,
                    (stage.getHeight() - pausescreenBackground.getRegionHeight()) / 2);
        }

        game.batch.end();
        game.batch.begin();
        stage.act(delta);
        stage.draw(); // renders actors on screen

        game.batch.end();

        // renders settingsscreen if enabled
        if (settingsScreen.getSettingsGroup().isVisible()) {
            settingsScreen.render(delta, gameScreenGroup);
        }

        cardManager.renderHand();


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
