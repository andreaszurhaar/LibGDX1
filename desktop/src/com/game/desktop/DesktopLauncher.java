package com.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.game.CopsAndRobbers;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = CopsAndRobbers.WIDTH;
		config.height = CopsAndRobbers.HEIGHT;
		config.title = CopsAndRobbers.TITLE;
		config.x = 100;
		config.y = 30;
		new LwjglApplication(new CopsAndRobbers(), config);
	}
}
