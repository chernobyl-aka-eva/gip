package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.GipVersion0;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration(); //allows to specify various configuration settings
		config.title = "gip-version-0"; //naam van applicatie
		config.useGL30 = false; //openGL settings
		config.width = 1920;
		config.height = 1080;
		new LwjglApplication(new GipVersion0(), config);
	}
}
