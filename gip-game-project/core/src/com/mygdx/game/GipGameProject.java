package com.mygdx.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.screen.TitleScreen;

public class GipGameProject extends Game {

    // Created by Eva-Veronica Szeredi & Philip Scott ☺

    public SpriteBatch batch;

    // used for text see https://github.com/libgdx/libgdx/wiki/Gdx-freetype
    // https://www.dafont.com/pc-senior.font already included
    // https://www.dafont.com/corrupted-file.font needs to be yet included (title) ? -maybe picture instead
    public FreeTypeFontGenerator generator;
    public FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    public BitmapFont font;

    // loading public skin for all buttons
    public Skin skin;

    // graphics
    public TextureAtlas textureAtlas;

    // public logger
    public Logger log;

	public Viewport viewport;
	public Camera camera;


    public Sound sound;
    public Music music;

    @Override
    public void create() {
        generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/pcsenior.ttf"));
        batch = new SpriteBatch();
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        font = generator.generateFont(parameter);

        log = new Logger(this.getClass().getName(), Logger.DEBUG);

        Gdx.app.setLogLevel(Application.LOG_DEBUG);

		camera = new OrthographicCamera();

		viewport = new StretchViewport(1920, 1080, camera);
        this.setScreen(new TitleScreen(this));

        sound =  Gdx.audio.newSound(Gdx.files.internal("sound/click_sound_1.mp3"));
        music = Gdx.audio.newMusic(Gdx.files.internal("music/mediathreat.ogg"));
        music.setLooping(true);


        skin = new Skin(Gdx.files.internal("skin/game-ui.json"));
        skin.addRegions(new TextureAtlas("skin/game-ui.atlas"));


    }

    @Override
    public void render() {
        super.render(); // important !
        batch.setProjectionMatrix(camera.combined);
        camera.update();
    }

    @Override
    public void dispose() {
        batch.dispose();
        generator.dispose();
        skin.getAtlas().dispose();
        skin.dispose();
        textureAtlas.dispose();
    }

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height, true);
	}

    public Camera getCamera() {
        return camera;
    }

    public Sound getSound() {
        return sound;
    }

    public void setSound(Sound sound) {
        this.sound = sound;
    }

    public Music getMusic() {
        return music;
    }

    public void setMusic(Music music) {
        this.music = music;
    }
}
