package com.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.game.AI.Astar.Astar;
import com.game.AI.GuardCirclePatrolling;
import com.game.AI.GuardPatrolling;
import com.game.GameLogic.Button;
import com.game.Readers.FileHandler;
import com.game.Readers.SpriteReader;

import java.io.IOException;

public class GameOverState extends State {

    Button button;
    Stage stage;
    public BitmapFont font;
    private TextureRegion background;
    private Skin skin;
    private Skin skin2;
    private TextButton restart;
    private TextButton gameov;
    GameStateManager gamestatemanager;
    public float deltaTime;
    public String str;

    public GameOverState(final GameStateManager gsm, float deltaTime) {
        super(gsm);
        gamestatemanager = gsm;
        stage=new Stage();
        SpriteReader reader = new SpriteReader();
        this.deltaTime = deltaTime;

        try {
            background  = reader.getImage(58,292,26,28);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Create a font
        font = new BitmapFont();
        skin = new Skin();
        skin.add("default", font);
        skin2 = new Skin();
        skin2.add("default", font);

        //Create a texture
        Pixmap pixmap = new Pixmap(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/10, Pixmap.Format.RGB888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        skin.add("background",new Texture(pixmap));
        skin2.add("background",new Texture(pixmap));
        
      //Create a button style
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.newDrawable("background", Color.ORANGE);
        textButtonStyle.down = skin.newDrawable("background", Color.BLACK);
        textButtonStyle.checked = skin.newDrawable("background", Color.BLACK);
        textButtonStyle.over = skin.newDrawable("background", Color.RED);
        textButtonStyle.font = skin.getFont("default");
        skin.add("default", textButtonStyle);

      //Create a 2nd button style
        TextButton.TextButtonStyle textButtonStyle2 = new TextButton.TextButtonStyle();
        textButtonStyle2.up = skin2.newDrawable("background", Color.BLACK);
        textButtonStyle2.down = skin2.newDrawable("background", Color.BLACK);
        textButtonStyle2.checked = skin2.newDrawable("background", Color.BLACK);
        textButtonStyle2.over = skin2.newDrawable("background", Color.BLACK);
        textButtonStyle2.font = skin2.getFont("default");
        skin2.add("default", textButtonStyle2);

        restart = new TextButton("Try Again", skin); // Use the initialized skin
        restart.setPosition(Gdx.graphics.getWidth()/4 , 4* Gdx.graphics.getHeight()/7);
        restart.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
            	gamestatemanager.push(new MenuState(gsm));
            
            }
        });
        
        gameov = new TextButton("GameOver", skin2); // Use the initialized skin
        gameov.setPosition(Gdx.graphics.getWidth()/4 , 6* Gdx.graphics.getHeight()/7);
        
        stage.addActor(restart);
        stage.addActor(gameov);

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
    	stage.getBatch().begin();
        stage.getBatch().draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        sb.begin();
        str = Float.toString(deltaTime);
        font.draw(sb, str, 400, 300);
        font.draw(sb, "TIME", 350, 300);
        sb.end();
        stage.getBatch().end();
        stage.act();
        stage.draw();
    }
}
