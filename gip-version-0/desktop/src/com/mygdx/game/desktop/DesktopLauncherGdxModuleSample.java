package com.mygdx.game.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.GdxModuleInfoSample;
import com.mygdx.game.GipVersion0;

public class DesktopLauncherGdxModuleSample {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration(); //allows to specify various configuration settings

		System.out.println("app= " + Gdx.app);
		System.out.println("input= " + Gdx.input);

		new LwjglApplication(new GdxModuleInfoSample(), config);

		System.out.println("after app= " + Gdx.app);
		System.out.println("after input= " + Gdx.input);
	}
}
