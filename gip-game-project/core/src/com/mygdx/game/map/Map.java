package com.mygdx.game.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.save.SavedState;
import com.mygdx.game.screen.GameScreen;

public class Map extends Table {
    private boolean showMap; // determines whether map should be shown
    private boolean previousState = true; // previous state of map button
    private Group mapScreenGroup;
    private TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("other/map-ui.atlas"));
    private Button mapButton;
    private int currentEvent = 0;

    private SavedState savedState;

    private Table mapTable;
    private ScrollPane scrollPane;
    private MapBackground mapBackground;

    public Map(final GameScreen gameScreen, SavedState savedState) {
        setBounds(0, 0, gameScreen.getStage().getWidth(), gameScreen.getStage().getHeight()-60);
        align(Align.center);
        //debug();
        setVisible(false);
        setShowMap(false);

        this.savedState = savedState;



        mapTable = new Table();
        mapTable.setBounds(0,2, gameScreen.getStage().getWidth(), gameScreen.getStage().getHeight()-62);

        // map texture region
        TextureRegion mapBackgroundRegion = atlas.findRegion("map-background");
        mapBackground = new MapBackground(mapBackgroundRegion, savedState);
        mapBackground.setPosition(0, 0);
        mapTable.add(mapBackground).size(mapBackground.getWidth(), mapBackground.getHeight());



        mapScreenGroup = new Group();


        // game UI
        gameScreen.getGame().textureAtlas = new TextureAtlas("skin/game-ui.atlas");
        gameScreen.getGame().skin = new Skin(Gdx.files.internal("skin/game-ui.json"));
        gameScreen.getGame().skin.addRegions(new TextureAtlas("skin/game-ui.atlas"));

        mapButton = new Button(gameScreen.getGame().skin); // map button
        mapButton.setPosition(gameScreen.getStage().getWidth() - 215, gameScreen.getStage().getHeight() - mapButton.getHeight() - 5); // sets position for map button

        mapButton.addListener(new InputListener() { // enables the map button to toggle and enables/disables game/map groups
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {}
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (!gameScreen.getSettingsScreen().getSettingsGroup().isVisible() && !gameScreen.isPausescreen()) {
                    if (!gameScreen.getSessionManager().getEventManager().getCardManager().getTableGroup().getChild(0).isVisible() && !gameScreen.getSessionManager().getEventManager().getCardManager().getTableGroup().getChild(1).isVisible() && !gameScreen.getSessionManager().getEventManager().getCardManager().getTableGroup().getChild(2).isVisible() && !gameScreen.getSessionManager().getEventManager().getCardManager().getTableGroup().getChild(3).isVisible()){
                        if (previousState) {
                            setShowMap(true);
                            previousState = false;
                            gameScreen.getGameScreenGroup().setVisible(false);
                            mapScreenGroup.setVisible(true);
                        } else {
                            setShowMap(false);
                            previousState = true;
                            gameScreen.getGameScreenGroup().setVisible(true);
                            mapScreenGroup.setVisible(false);
                        }
                    }
                }
                return true;
            }
        });

        // adds actor to stage
        gameScreen.getStage().addActor(mapButton);

        Button mapReturn = new Button(gameScreen.getGame().skin, "return"); // return button in map screen
        mapReturn.setPosition(0, gameScreen.getStage().getHeight() - 870);
        mapScreenGroup.setVisible(false); // makes map invisible by default
        mapReturn.addListener(new InputListener() { // allows going back to game screen from map
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {}
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Map.this.setShowMap(false);
                previousState = true;
                mapScreenGroup.setVisible(false);
                gameScreen.getGameScreenGroup().setVisible(true);
                return true;
            }
        });

        mapScreenGroup.addActor(mapReturn); // adds return button to group

        Table legend = new Table();
        TextureRegion region = atlas.findRegion("legenda");
        legend.setBackground(new TextureRegionDrawable(region));
        legend.setClip(true);
        legend.setOrigin(Align.center);
        //legend.scaleBy(0.5F);
        legend.setSize(region.getRegionWidth()*legend.getScaleX(), region.getRegionHeight()*legend.getScaleY());
        legend.setPosition(gameScreen.getStage().getWidth()-legend.getWidth(), legend.getHeight());

        Image monster = new Image(new TextureRegionDrawable(atlas.findRegion("monster")));
        legend.add(monster);
        legend.add(new Label("Monster", gameScreen.getGame().skin)).row();

        Image elite = new Image(new TextureRegionDrawable(atlas.findRegion("elite")));
        legend.add(elite);
        legend.add(new Label("Elite", gameScreen.getGame().skin)).row();

        Image file = new Image(new TextureRegionDrawable(atlas.findRegion("file")));
        legend.add(file);
        legend.add(new Label("File", gameScreen.getGame().skin)).row();

        Image random = new Image(new TextureRegionDrawable(atlas.findRegion("random")));
        legend.add(random);
        legend.add(new Label("Random", gameScreen.getGame().skin)).row();

        Image rest = new Image(new TextureRegionDrawable(atlas.findRegion("rest")));
        legend.add(rest);
        legend.add(new Label("Rest", gameScreen.getGame().skin)).row();

        Image shop = new Image(new TextureRegionDrawable(atlas.findRegion("shop")));
        legend.add(shop);
        legend.add(new Label("Shop", gameScreen.getGame().skin)).row();


        mapScreenGroup.addActor(legend);

        //mapTable.add(mapScreenGroup);
        scrollPane = new ScrollPane(mapTable, gameScreen.getGame().skin);
        scrollPane.layout();
        scrollPane.scrollTo(0, 0, 0, 0);
        scrollPane.setFlickScroll(false);
        scrollPane.setVariableSizeKnobs(false);
        add(scrollPane).fill().expand();
        scrollPane.addListener(new InputListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                super.enter(event, x, y, pointer, fromActor);
                if (pointer == -1) {
                    gameScreen.getStage().setScrollFocus(scrollPane);
                }
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                super.exit(event, x, y, pointer, toActor);
                if (pointer == -1) {
                    gameScreen.getStage().setScrollFocus(null);
                }
            }
        });
        gameScreen.getMapScreenGroup().addActor(this);
        gameScreen.getMapScreenGroup().addActor(mapScreenGroup); // adds group to stage


        if (savedState != null) {
            currentEvent = savedState.getSavedMap().getCurrentEventId();
        }
    }

    public void dispose() {
        mapBackground.dispose();
        atlas.dispose();

    }

    public MapBackground getMapBackground() {
        return mapBackground;
    }

    public void setMapBackground(MapBackground mapBackground) {
        this.mapBackground = mapBackground;
    }

    public boolean isShowMap() {
        return showMap;
    }

    public void setShowMap(boolean showMap) {
        this.showMap = showMap;
        setVisible(showMap);
    }

    public int getCurrentEvent() {
        return mapBackground.getCurrentEvent();
    }

    public void setCurrentEvent(int currentEvent) {
        mapBackground.setCurrentEvent(currentEvent);
        this.currentEvent = mapBackground.getCurrentEvent();
    }

    public boolean isPreviousState() {
        return previousState;
    }

    public void setPreviousState(boolean previousState) {
        this.previousState = previousState;
    }

    public Group getMapScreenGroup() {
        return mapScreenGroup;
    }

    public void setMapScreenGroup(Group mapScreenGroup) {
        this.mapScreenGroup = mapScreenGroup;
    }

    public TextureAtlas getAtlas() {
        return atlas;
    }

    public void setAtlas(TextureAtlas atlas) {
        this.atlas = atlas;
    }

    public Button getMapButton() {
        return mapButton;
    }

    public void setMapButton(Button mapButton) {
        this.mapButton = mapButton;
    }

    public Table getMapTable() {
        return mapTable;
    }

    public void setMapTable(Table mapTable) {
        this.mapTable = mapTable;
    }
}
