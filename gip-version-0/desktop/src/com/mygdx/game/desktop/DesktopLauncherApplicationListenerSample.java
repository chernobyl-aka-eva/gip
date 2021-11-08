package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.ApplicationListenerSample;
import com.mygdx.game.GipVersion0;

public class DesktopLauncherApplicationListenerSample {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration(); //allows to specify various configuration settings
		new LwjglApplication(new ApplicationListenerSample(), cfg);
	}
}
