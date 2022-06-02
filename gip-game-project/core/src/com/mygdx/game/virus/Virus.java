package com.mygdx.game.virus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.GipGameProject;
import com.mygdx.game.effect.EffectManager;
import com.mygdx.game.item.ItemManager;
import com.mygdx.game.save.SavedState;

public class Virus extends Actor {

    private GipGameProject game;

    private Array<Integer> startingDeck;

    private String name; // name
    private int health; // health
    private int block; // block
    private int money;


    private final int STARTING_HP = 75; // starting HP
    private final int MAX_HP = 200; // maximum health the player can have
    private final int STARTING_MONEY = 99; // MB data (money)
    private final int HAND_SIZE = 10; // maximum amount of cards the player can hold

    private int energy = 3; // how much energy you get every turn
    private EnergyManager energyManager;
    private Animation<TextureRegion> idleAnimation; // idle character animation

    private int amountToDraw = 6;

    private Label blockLabel;
    private boolean blockActive;
    private Image blockImage;
    private ProgressBar virusBlockBar;
    // items
    // potions

    private float positionX; // position and dimension
    private float positionY;

    // stage
    Stage stage;
    private Group gameScreenGroup;

    private final Label virusName;
    private ProgressBar virusHealthBar;
    private Actor nameAreaVirus;

    private EffectManager effectManager;
    private ItemManager itemManager;
    private float elapsed_time;

    private SavedState savedState;

    // constructor
    public Virus(GipGameProject game, String name, int health, int block, Stage stage, Group gameScreenGroup, SavedState savedState) {
        this.name = name;
        this.health = health;
        this.block = block;
        this.game = game;
        this.stage = stage;
        this.gameScreenGroup = gameScreenGroup;
        if (savedState == null) {
            this.money = STARTING_MONEY;
        } else {
            this.money = savedState.getMoney();
        }
        this.savedState = savedState;
        game.skin = new Skin(Gdx.files.internal("skin/game-ui.json"));
        game.skin.addRegions(new TextureAtlas("skin/game-ui.atlas"));

        positionX = 400;
        positionY = stage.getHeight() - 700;

        elapsed_time = 0f;
        // Animation
        TextureAtlas idleSet = new TextureAtlas(Gdx.files.internal("animation/idle.atlas"));
        idleAnimation = new Animation<TextureRegion>(1 / 10f, idleSet.findRegions("idle"));
        idleAnimation.setFrameDuration(3 / 10f);

        nameAreaVirus = new Actor();

        nameAreaVirus.setSize(idleAnimation.getKeyFrame(0).getRegionWidth() * 6, // sets size of area to check
                idleAnimation.getKeyFrame(0).getRegionHeight() * 6);
        nameAreaVirus.setPosition(positionX, stage.getHeight() - 700); // sets position of checking area

        // Virus Name
        virusName = new Label(name, new Label.LabelStyle(game.font, Color.WHITE));
        float x = nameAreaVirus.getX() + (nameAreaVirus.getWidth() - virusName.getWidth()) / 2;
        float y = nameAreaVirus.getY() - 70;
        virusName.setPosition(x, y);
        virusName.setVisible(false);

        initVirus();

        // Health Bar
        virusHealthBar = new ProgressBar(0, 100, 1, false, game.skin, "red-knob");
        virusHealthBar.setValue(health);
        virusHealthBar.setPosition(positionX, stage.getHeight() - 730);

        virusBlockBar = new ProgressBar(0, 100, 1, false, game.skin, "blue-knob");
        virusBlockBar.setValue(health);
        virusBlockBar.setPosition(virusHealthBar.getX(), virusHealthBar.getY());

        this.effectManager = new EffectManager(this, game, stage);

        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("skin/game-ui.atlas"));
        //TextureRegion textureRegion = game.textureAtlas.findRegion("block");
        TextureRegion textureRegion = atlas.findRegion("block");
        blockImage = new Image(new TextureRegionDrawable(textureRegion));
        blockImage.setPosition(virusHealthBar.getX() - blockImage.getWidth() + 3, virusHealthBar.getY() - (blockImage.getHeight() / 2));
        //blockImage.setPosition(500,500);

        blockLabel = new Label(String.valueOf(block), game.skin);
        blockLabel.setPosition(blockImage.getX(), blockImage.getY());
        //Color color = Color.rgba8888(57.0F, 45.0F, 63.0F, 1.0F);
        //color.set(57, 45, 63, 1);
        //System.out.println("Color: " + color.r + ", " + color.g + ", " + color.b + ", " + color.a);
        blockLabel.setColor(Color.PURPLE);
        blockActive = false;

        setBlock(block);

        // Energy
        FreeTypeFontGenerator generator = game.generator;
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = game.parameter;
        parameter.size = 50;
        BitmapFont font;
        font = generator.generateFont(parameter);

        energyManager = new EnergyManager(game, stage, energy, font);
        gameScreenGroup.addActor(energyManager);

        // Starting Deck
        startingDeck = new Array<>();
        startingDeck.add(6);
        //startingDeck.add(0);
        //startingDeck.add(0);
        //startingDeck.add(0);
        startingDeck.add(0);
        //startingDeck.add(1);
        //startingDeck.add(1);
        //startingDeck.add(1);
        startingDeck.add(1);
        startingDeck.add(2);
        startingDeck.add(3);
        startingDeck.add(4);
        startingDeck.add(5);
        startingDeck.add(7);
        startingDeck.add(8);
        startingDeck.add(9);
        startingDeck.add(11);
        startingDeck.add(12);
        startingDeck.add(13);
        startingDeck.add(14);
        startingDeck.add(15);
        startingDeck.add(17);
        startingDeck.add(20);
        startingDeck.add(21);
        startingDeck.add(22);
        startingDeck.add(23);
        startingDeck.add(24);
        startingDeck.add(25);
        startingDeck.add(26);
        startingDeck.add(27);
        startingDeck.add(28);


        // Items
        Group itemGroup = new Group();
        itemManager = new ItemManager(itemGroup, stage, game);
        if (savedState == null) {
            itemManager.addItem(0);
            itemManager.addItem(0);
            itemManager.addItem(0);
            itemManager.addItem(0);
            itemManager.addItem(0);
            itemManager.addItem(0);
            itemManager.addItem(0);
            itemManager.addItem(0);
            itemManager.addItem(0);
            itemManager.addItem(0);
            stage.addActor(itemGroup);
        } else {
            for (Integer savedItemIndex : savedState.getSavedItemIndexes()) {
                itemManager.addItem(savedItemIndex);
            }
        }
    }

    public void initVirus() {
        nameAreaVirus.addListener(new ClickListener() { // detects if hovering over player
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                if (pointer == -1) {
                    virusName.setVisible(true);
                    game.log.debug("hovering player " + idleAnimation.getKeyFrame(0).getRegionWidth() + " " + idleAnimation.getKeyFrame(0).getRegionHeight());
                }
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                if (pointer == -1) {
                    virusName.setVisible(false);
                    game.log.debug("not hovering player ");
                }
            }
        });
        gameScreenGroup.addActor(nameAreaVirus);
    }

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        if (visible) {
            if (blockActive) {
                virusBlockBar.setVisible(true);
                blockLabel.setVisible(true);
                blockImage.setVisible(true);
            }
            nameAreaVirus.setVisible(true);
            virusHealthBar.setVisible(true);
        } else {
            if (blockActive) {
                virusBlockBar.setVisible(false);
                blockLabel.setVisible(false);
                blockImage.setVisible(false);
            }
            virusName.setVisible(false);
            nameAreaVirus.setVisible(false);
            virusHealthBar.setVisible(false);
        }
    }

    public void addEffect(int id) {
        effectManager.addEffect(id);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        virusHealthBar.setValue(health);
        if (blockActive) {
            virusBlockBar.setValue(health);
            blockLabel.setText(String.valueOf(block));
        }
        blockLabel.setText(block);
        if (this.isVisible()) {
            elapsed_time += Gdx.graphics.getDeltaTime();
            TextureRegion currentFrame = idleAnimation.getKeyFrame(elapsed_time, true);
            game.batch.draw(currentFrame,
                    positionX,
                    positionY,
                    currentFrame.getRegionWidth() * 6,
                    currentFrame.getRegionHeight() * 6);

            if (virusName.isVisible()) {
                virusName.draw(batch, parentAlpha);
            }
            if (!blockActive) {
                virusHealthBar.draw(batch, parentAlpha);
            } else {
                virusBlockBar.draw(batch, parentAlpha);
                blockImage.draw(batch, parentAlpha);
                blockLabel.draw(batch, parentAlpha);
            }
        }

    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (this.isVisible()) {
            virusHealthBar.act(delta);

            nameAreaVirus.act(delta);
            if (virusName.isVisible()) {
                virusName.act(delta);
            }
        }

    }

    public void dispose() {

    }

    // getters and setters


    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getAmountToDraw() {
        return amountToDraw;
    }

    public void setAmountToDraw(int amountToDraw) {
        this.amountToDraw = amountToDraw;
    }

    public ItemManager getItemManager() {
        return itemManager;
    }

    public void setItemManager(ItemManager itemManager) {
        this.itemManager = itemManager;
    }

    public EffectManager getEffectManager() {
        return effectManager;
    }

    public void setEffectManager(EffectManager effectManager) {
        this.effectManager = effectManager;
    }

    public GipGameProject getGame() {
        return game;
    }

    public void setGame(GipGameProject game) {
        this.game = game;
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public EnergyManager getEnergyManager() {
        return energyManager;
    }

    public void setEnergyManager(EnergyManager energyManager) {
        this.energyManager = energyManager;
    }

    public Animation<TextureRegion> getIdleAnimation() {
        return idleAnimation;
    }

    public void setIdleAnimation(Animation<TextureRegion> idleAnimation) {
        this.idleAnimation = idleAnimation;
    }

    public Array<Integer> getStartingDeck() {
        return startingDeck;
    }

    public void setStartingDeck(Array<Integer> startingDeck) {
        this.startingDeck = startingDeck;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getBlock() {
        return block;
    }

    public void setBlock(int block) {
        this.block = block;
        if (block > 0) {
            setBlockActive(true);
        } else {
            block = 0;
            setBlockActive(false);
        }
    }

    public int getSTARTING_HP() {
        return STARTING_HP;
    }

    public int getMAX_HP() {
        return MAX_HP;
    }

    public int getSTARTING_MONEY() {
        return STARTING_MONEY;
    }

    public int getHAND_SIZE() {
        return HAND_SIZE;
    }

    public Label getBlockLabel() {
        return blockLabel;
    }

    public void setBlockLabel(Label blockLabel) {
        this.blockLabel = blockLabel;
    }

    public boolean isBlockActive() {
        return blockActive;
    }

    public void setBlockActive(boolean blockActive) {
        this.blockActive = blockActive;
    }

    public Image getBlockImage() {
        return blockImage;
    }

    public void setBlockImage(Image blockImage) {
        this.blockImage = blockImage;
    }

    public ProgressBar getVirusBlockBar() {
        return virusBlockBar;
    }

    public void setVirusBlockBar(ProgressBar virusBlockBar) {
        this.virusBlockBar = virusBlockBar;
    }

    public float getPositionX() {
        return positionX;
    }

    public void setPositionX(float positionX) {
        this.positionX = positionX;
    }

    public float getPositionY() {
        return positionY;
    }

    public void setPositionY(float positionY) {
        this.positionY = positionY;
    }

    @Override
    public Stage getStage() {
        return stage;
    }

    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Group getGameScreenGroup() {
        return gameScreenGroup;
    }

    public void setGameScreenGroup(Group gameScreenGroup) {
        this.gameScreenGroup = gameScreenGroup;
    }

    public Label getVirusName() {
        return virusName;
    }

    public ProgressBar getVirusHealthBar() {
        return virusHealthBar;
    }

    public void setVirusHealthBar(ProgressBar virusHealthBar) {
        this.virusHealthBar = virusHealthBar;
    }

    public Actor getNameAreaVirus() {
        return nameAreaVirus;
    }

    public void setNameAreaVirus(Actor nameAreaVirus) {
        this.nameAreaVirus = nameAreaVirus;
    }

    public float getElapsed_time() {
        return elapsed_time;
    }

    public void setElapsed_time(float elapsed_time) {
        this.elapsed_time = elapsed_time;
    }
}


