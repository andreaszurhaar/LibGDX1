package com.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.game.States.GameStateManager;
import com.game.States.MapState;

public class CopsAndRobbers extends ApplicationAdapter {

	public static final int WIDTH = 1000;
	public static final int HEIGHT = 600;
	public static final String TITLE = "CopsAndRobbers";
	public SpriteBatch sb;
	public GameStateManager gsm;


	@Override
	public void create () {
		sb = new SpriteBatch();
		gsm = new GameStateManager();
		gsm.push(new MapState(gsm));
		Gdx.gl.glClearColor(1,0,0,1);

	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.render(sb);


	}
	
	@Override
	public void dispose () {
		sb.dispose();

	}

}
