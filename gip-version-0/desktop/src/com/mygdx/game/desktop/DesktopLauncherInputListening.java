package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.InputListeningSample;
import com.mygdx.game.InputPollingSample;

public class DesktopLauncherInputListening {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration(); //allows to specify various configuration settings
		new LwjglApplication(new InputListeningSample(), config);
	}
}
