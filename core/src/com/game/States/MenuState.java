package com.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.game.GameLogic.Button;

public class MenuState extends State {

    Button button;
    Stage stage;
    public BitmapFont font;
    private Texture background;
    private Skin skin;
    private TextButton start;
    private SelectBox bots;
    Dialog dialog;
    GameStateManager gamestatemanager;

    public MenuState(GameStateManager gsm) {
        super(gsm);
        gamestatemanager = gsm;
        stage=new Stage();
        
        background = new Texture(Gdx.files.internal("desert.png"));

        //Create a font
        font = new BitmapFont();
        skin = new Skin();
        skin.add("default", font);

        //Create a texture
        Pixmap pixmap = new Pixmap(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/10, Pixmap.Format.RGB888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        skin.add("background",new Texture(pixmap));
        
        //Create a button style
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.newDrawable("background", Color.ORANGE);
        textButtonStyle.down = skin.newDrawable("background", Color.BLACK);
        textButtonStyle.checked = skin.newDrawable("background", Color.BLACK);
        textButtonStyle.over = skin.newDrawable("background", Color.RED);
        textButtonStyle.font = skin.getFont("default");
        skin.add("default", textButtonStyle);
        
        //Scroll Pane style
        ScrollPane.ScrollPaneStyle scrollPaneStyle = new ScrollPane.ScrollPaneStyle();
        scrollPaneStyle.background = skin.newDrawable("background", Color.WHITE);
        skin.add("default",scrollPaneStyle);
        
        //Create a SelectBox style
        SelectBox.SelectBoxStyle boxStyle = new SelectBox.SelectBoxStyle();
        boxStyle.font = skin.getFont("default");
        boxStyle.background = skin.newDrawable("background", Color.WHITE);
        boxStyle.fontColor = Color.BLACK;
        ScrollPane pane = new ScrollPane(bots, skin);
        boxStyle.scrollStyle = pane.getStyle();
        boxStyle.listStyle = new List.ListStyle();
        boxStyle.listStyle.font = skin.getFont("default");
        boxStyle.listStyle.fontColorSelected = Color.BLACK;
        boxStyle.listStyle.fontColorUnselected = Color.BLUE;
        boxStyle.listStyle.selection = skin.newDrawable("background", Color.LIGHT_GRAY);
        skin.add("default", boxStyle);

        
        String[] arrayBots = new String[4];
        arrayBots[0] = "None";
        arrayBots[1] = "Bot1";
        arrayBots[2] = "Bot2";
        arrayBots[3] = "Bot3";
        
        float selectBoxHeight = Gdx.graphics.getHeight()/25;
        float selectBoxOffset = Gdx.graphics.getHeight()/200;

        
        bots = new SelectBox(skin);
        bots.setItems(arrayBots);
        bots.setPosition(Gdx.graphics.getWidth()/4 + 9*Gdx.graphics.getWidth()/22, 5* Gdx.graphics.getHeight()/7-selectBoxOffset);
        bots.setSize(2*Gdx.graphics.getWidth()/22,selectBoxHeight);
        
        start = new TextButton("Start", skin); // Use the initialized skin
        start.setPosition(Gdx.graphics.getWidth()/4 , 6* Gdx.graphics.getHeight()/7);
        start.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                gamestatemanager.push(new MapState(gamestatemanager));
            }
        });
        
        stage.addActor(bots);
        stage.addActor(start);

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
        stage.getBatch().end();
        stage.act();
        stage.draw();
    }
}
