package com.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.game.GameLogic.Button;

public class MenuState extends State {

    Button button;
    Stage stage;
    Dialog dialog;
    final SelectBox<String> selectBox;

    public MenuState(GameStateManager gsm) {
        super(gsm);
        stage=new Stage();

        Skin skin=new Skin(Gdx.files.internal("skin/glassy-ui.json"));

        dialog=new Dialog("Setting",skin);
        dialog.setSize(200,200);
        dialog.setPosition(Gdx.graphics.getWidth()/2-100,Gdx.graphics.getHeight()/2-100);

        selectBox=new SelectBox<String>(skin);
        selectBox.setItems("XYZ","ABC","PQR","LMN");

        dialog.getContentTable().defaults().pad(10);
        dialog.getContentTable().add(selectBox);

        stage.addActor(dialog);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void handleInput() {

    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void render(SpriteBatch sb) {
        stage.draw();
    }
}
