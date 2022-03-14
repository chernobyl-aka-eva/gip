package com.mygdx.game.monster;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
import com.mygdx.game.GipGameProject;

public class Monster extends Actor {

    private int id;

    private GipGameProject game;

    //position and dimension
    private int positionX = 1480;
    // characterics
    //name
    private String name;
    //health
    private int health;
    //block
    private int block;
    //texture region
    private Animation<TextureRegion> monsterIdleAnimation;
    //stage
    Stage stage;
    private Group gameScreenGroup;

    private final Label monsterName;
    private ProgressBar monsterHealthBar;
    private Actor nameAreaMonster;

    private Label blockLabel;
    private boolean blockActive;
    private Image blockImage;
    private ProgressBar monsterBlockBar;

    private float elapsed_time;


    public Monster(final GipGameProject game, int id, String name, int health, int block, Stage stage, Group gameScreenGroup) {
        this.id = id;
        this.name = name;
        this.health = health;
        this.block = block;
        this.game = game;
        this.stage = stage;
        this.gameScreenGroup = gameScreenGroup;

        game.skin = new Skin(Gdx.files.internal("skin/game-ui.json"));
        game.skin.addRegions(new TextureAtlas("skin/game-ui.atlas"));

        elapsed_time = 0f;
        // Animation
        TextureAtlas monsterIdleSet = new TextureAtlas(Gdx.files.internal("animation/enemyidle.atlas"));
        monsterIdleAnimation = new Animation<TextureRegion>(1 / 10f, monsterIdleSet.findRegions("enemyidle"));
        monsterIdleAnimation.setFrameDuration(3 / 10f);

        nameAreaMonster = new Actor();

        nameAreaMonster.setSize(monsterIdleAnimation.getKeyFrame(0).getRegionWidth() * 6,
                monsterIdleAnimation.getKeyFrame(0).getRegionHeight() * 6);
        nameAreaMonster.setPosition(positionX, stage.getHeight() - 700);

        // Monster Name
        monsterName = new Label(name, new Label.LabelStyle(game.font, Color.WHITE));
        float x = nameAreaMonster.getX() + (nameAreaMonster.getWidth() - monsterName.getWidth()) / 2;
        float y = nameAreaMonster.getY() - 70;
        monsterName.setPosition(x, y);
        monsterName.setVisible(false);

        initMonster();

        // Health Bar
        monsterHealthBar = new ProgressBar(0, 100, 1, false, game.skin, "red-knob");
        monsterHealthBar.setValue(health);
        monsterHealthBar.setPosition(positionX, stage.getHeight() - 730);

        monsterBlockBar = new ProgressBar(0, 100, 1, false, game.skin, "blue-knob");
        monsterBlockBar.setValue(health);
        monsterBlockBar.setPosition(monsterHealthBar.getX(), monsterHealthBar.getY());

        // Block Bar
        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("skin/game-ui.atlas"));
        //TextureRegion textureRegion = game.textureAtlas.findRegion("block");
        TextureRegion textureRegion = atlas.findRegion("block");
        blockImage = new Image(new TextureRegionDrawable(textureRegion));
        blockImage.setPosition(monsterHealthBar.getX() - blockImage.getWidth() + 3, monsterHealthBar.getY() - (blockImage.getHeight() / 2));
        //blockImage.setPosition(500,500);

        blockLabel = new Label(String.valueOf(block), game.skin);
        blockLabel.setPosition(blockImage.getX(), blockImage.getY());
        //Color color = Color.rgba8888(57.0F, 45.0F, 63.0F, 1.0F);
        //color.set(57, 45, 63, 1);
        //System.out.println("Color: " + color.r + ", " + color.g + ", " + color.b + ", " + color.a);
        blockLabel.setColor(Color.PURPLE);
        blockActive = false;

    }

    public void initMonster() {

        nameAreaMonster.addListener(new ClickListener() {
            final GipGameProject gameProject = game; // detects if hovering over enemy
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                if (pointer == -1) {
                    monsterName.setVisible(true);
                    gameProject.log.debug("hovering monster " + monsterIdleAnimation.getKeyFrame(0).getRegionWidth() + " " + monsterIdleAnimation.getKeyFrame(0).getRegionHeight());
                }
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                if (pointer == -1) {
                    monsterName.setVisible(false);
                    gameProject.log.debug("not hovering monster");
                }
            }
        });
        gameScreenGroup.addActor(nameAreaMonster);
    }

    //rip setvisible 05/03/2022 - 05/03/2022 🥺

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        if (visible) {
            if (blockActive) {
                monsterBlockBar.setVisible(true);
                blockLabel.setVisible(true);
                blockImage.setVisible(true);
            }
            nameAreaMonster.setVisible(true);
            monsterHealthBar.setVisible(true);
        } else {
            if (blockActive) {
                monsterBlockBar.setVisible(false);
                blockLabel.setVisible(false);
                blockImage.setVisible(false);
            }
            monsterName.setVisible(false);
            nameAreaMonster.setVisible(false);
            monsterHealthBar.setVisible(false);
        }

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        monsterHealthBar.setValue(health);
        if (blockActive) {
            monsterBlockBar.setValue(health);
            blockLabel.setText(String.valueOf(block));
        }
        blockLabel.setText(block);
        if (this.isVisible()){
            monsterHealthBar.setValue(health);

            elapsed_time += Gdx.graphics.getDeltaTime();
            TextureRegion currentFrameMonster = monsterIdleAnimation.getKeyFrame(elapsed_time, true);
            game.batch.draw(currentFrameMonster,
                    1480,
                    Gdx.graphics.getHeight() - 700,
                    currentFrameMonster.getRegionWidth() * 6,
                    currentFrameMonster.getRegionHeight() * 6);

            if (monsterName.isVisible()) {
                monsterName.draw(batch, parentAlpha);
            }

            if (!blockActive) {
                monsterHealthBar.draw(batch, parentAlpha);
            } else {
                monsterBlockBar.draw(batch, parentAlpha);
                blockImage.draw(batch, parentAlpha);
                blockLabel.draw(batch, parentAlpha);
            }
        }

    }



    @Override
    public void act(float delta) {
        super.act(delta);
        if (this.isVisible()) {
            monsterHealthBar.act(delta);

            nameAreaMonster.act(delta);
            if (monsterName.isVisible()) {
                monsterName.act(delta);
            }
        }

    }

    // getters and setters


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public GipGameProject getGame() {
        return game;
    }

    public void setGame(GipGameProject game) {
        this.game = game;
    }

    public int getBlock() {
        return block;
    }

    public void setBlock(int block) {
        this.block = block;
    }

    public Group getGameScreenGroup() {
        return gameScreenGroup;
    }

    public void setGameScreenGroup(Group gameScreenGroup) {
        this.gameScreenGroup = gameScreenGroup;
    }

    public float getElapsed_time() {
        return elapsed_time;
    }

    public void setElapsed_time(float elapsed_time) {
        this.elapsed_time = elapsed_time;
    }

    public int getPositionX() {
        return positionX;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public int gethealth() {
        return health;
    }

    public void sethealth(int health) {
        this.health = health;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
        monsterHealthBar.setValue(health);
    }

    public int getblock() {
        return block;
    }

    public void setblock(int block) {
        this.block = block;
        if (block > 0) {
            setBlockActive(true);
        } else {
            block = 0;
            setBlockActive(false);
        }
    }

    public Animation<TextureRegion> getMonsterIdleAnimation() {
        return monsterIdleAnimation;
    }

    public void setMonsterIdleAnimation(Animation<TextureRegion> monsterIdleAnimation) {
        this.monsterIdleAnimation = monsterIdleAnimation;
    }

    public Label getMonsterName() {
        return monsterName;
    }

    public ProgressBar getMonsterHealthBar() {
        return monsterHealthBar;
    }

    public void setMonsterHealthBar(ProgressBar monsterHealthBar) {
        this.monsterHealthBar = monsterHealthBar;
    }

    public Actor getNameAreaMonster() {
        return nameAreaMonster;
    }

    public void setNameAreaMonster(Actor nameAreaMonster) {
        this.nameAreaMonster = nameAreaMonster;
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

    public ProgressBar getMonsterBlockBar() {
        return monsterBlockBar;
    }

    public void setMonsterBlockBar(ProgressBar monsterBlockBar) {
        this.monsterBlockBar = monsterBlockBar;
    }
}


