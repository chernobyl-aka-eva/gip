package com.mygdx.game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

class GameScreen implements Screen{
    //screen
    private Camera camera;
    private Viewport viewport;

    //graphics
    private SpriteBatch batch;
    private TextureAtlas textureAtlas;
    // private Texture background;
    private TextureRegion[] backgrounds;
    private float backgroundHeight; //height of background in World units

    private TextureRegion mainMenuTextureRegion, guidelineTextureRegion; //add more later

    //timing
    //private int backgroundOffset;
    private float[] backgroundOffsets = {0,0,0,0};
    private float backgroundMaxScrollingSpeed;

    //world parameters;
    private final int WORLD_WIDTH = 1920;
    private final int WORLD_HEIGHT = 1080;

    //game objects


    GameScreen(){
        camera = new OrthographicCamera();
        viewport = new StretchViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);

        //set up texture atlas
        textureAtlas = new TextureAtlas("backgrounds.atlas");


        //background = new Texture("Binary_text_flow.gif");
        //backgroundOffset = 0;

        backgrounds = new TextureRegion[4];
        backgrounds[0] = textureAtlas.findRegion("binary-bg-0");
        backgrounds[1] = textureAtlas.findRegion("binary-bg-1");
        backgrounds[2] = textureAtlas.findRegion("binary-bg-2");
        backgrounds[3] = textureAtlas.findRegion("binary-bg-3");

        backgroundMaxScrollingSpeed = (float)WORLD_HEIGHT / 4;

        batch = new SpriteBatch();
    }

    @Override
    public void render(float deltaTime) {
        batch.begin();
        // scrolling background
        renderBackground(deltaTime);
        batch.end();
    }

    private void renderBackground(float deltaTime) {
        backgroundOffsets[0] += deltaTime * backgroundMaxScrollingSpeed/8;
        backgroundOffsets[1] += deltaTime * backgroundMaxScrollingSpeed/4;
        backgroundOffsets[2] += deltaTime * backgroundMaxScrollingSpeed/2;
        backgroundOffsets[3] += deltaTime * backgroundMaxScrollingSpeed;

        for (int layer = 0; layer < backgroundOffsets.length; layer++){
            if (backgroundOffsets[layer] > WORLD_HEIGHT) {
                backgroundOffsets[layer] = 0;
            }
            batch.draw(backgrounds[layer],
                    0, -backgroundOffsets[layer],
                    WORLD_WIDTH, WORLD_HEIGHT);
            batch.draw(backgrounds[layer], 0, -backgroundOffsets[layer] + WORLD_HEIGHT,
                    WORLD_WIDTH, WORLD_HEIGHT);
        }
    }



    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        batch.setProjectionMatrix(camera.combined);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    @Override
    public void show() {

    }
}

