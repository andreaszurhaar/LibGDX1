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

public class MenuState extends State {

    Button button;
    Stage stage;
    public BitmapFont font;
    private TextureRegion background;
    private Skin skin;
    private TextButton start;
    private SelectBox agentBots;
    private SelectBox guardBots;
    private SelectBox levelBox;
    Dialog dialog;
    GameStateManager gamestatemanager;
    private FileHandler levelReader;

    public MenuState(final GameStateManager gsm) {
        super(gsm);
        gamestatemanager = gsm;
        stage=new Stage();
        SpriteReader reader = new SpriteReader();
        levelReader = new FileHandler();

        try {
            background  = reader.getImage(58,292,26,28);
        } catch (IOException e) {
            e.printStackTrace();
        }

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
        ScrollPane agentPane = new ScrollPane(agentBots, skin);
        boxStyle.scrollStyle = agentPane.getStyle();
        boxStyle.listStyle = new List.ListStyle();
        boxStyle.listStyle.font = skin.getFont("default");
        boxStyle.listStyle.fontColorSelected = Color.BLACK;
        boxStyle.listStyle.fontColorUnselected = Color.BLUE;
        boxStyle.listStyle.selection = skin.newDrawable("background", Color.LIGHT_GRAY);
        skin.add("default", boxStyle);

       final String[] levels = new String[100] ;

        for(int i = 0; i < 100; i++){
            levels[i] = ""+i;
        }
        
        String[] arrayAgent = new String[3];
        arrayAgent[0] = "Basic";
        arrayAgent[1] = "Heuristic Closest AI";
        arrayAgent[2] = "Heuristic Random AI";

        String[] arrayGuard = new String[4];
        arrayGuard[0] = "Patrolling";
        arrayGuard[1] = "Circle patrolling";
        arrayGuard[2] = "Heatmap patrolling";
        arrayGuard[3] = "Random patrolling";
        
        float selectBoxHeight = Gdx.graphics.getHeight()/25;
        float selectBoxOffset = Gdx.graphics.getHeight()/200;

        
        agentBots = new SelectBox(skin);
        agentBots.setItems(arrayAgent);
        agentBots.setPosition(Gdx.graphics.getWidth()/4 + 9*Gdx.graphics.getWidth()/22, 5* Gdx.graphics.getHeight()/7-selectBoxOffset);
        agentBots.setSize(2*Gdx.graphics.getWidth()/22,selectBoxHeight);

        guardBots = new SelectBox(skin);
        guardBots.setItems(arrayGuard);
        guardBots.setPosition(Gdx.graphics.getWidth()/8 + 9*Gdx.graphics.getWidth()/22, 5* Gdx.graphics.getHeight()/7-selectBoxOffset);
        guardBots.setSize(2*Gdx.graphics.getWidth()/22,selectBoxHeight);

        levelBox = new SelectBox(skin);
        levelBox.setItems(levels);
        levelBox.setPosition(Gdx.graphics.getWidth()/2 + 7*Gdx.graphics.getWidth()/22, 6* Gdx.graphics.getHeight()/7+selectBoxOffset);
        levelBox.setSize(2*Gdx.graphics.getWidth()/22,selectBoxHeight);
        
        start = new TextButton("Start", skin); // Use the initialized skin
        start.setPosition(Gdx.graphics.getWidth()/4 , 6* Gdx.graphics.getHeight()/7);
        start.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                int levelInt = Integer.parseInt((String) levelBox.getSelected());
                String guardAI = (String) guardBots.getSelected();
                String intruderAI = (String) agentBots.getSelected();
                if(levelInt == 0 ) {
                   // gamestatemanager.push(new MapState(gamestatemanager, guardAI, intruderAI));
                    gamestatemanager.push(new TestState(gamestatemanager, levelReader.fileReader(9).get(1),levelReader.fileReader(9).get(0),levelReader.fileReader(9).get(2),guardAI, intruderAI,0));
                }
                else{
                    //gamestatemanager.push(new MainState(gsm,levelReader.fileReader(levelInt).get(1),levelReader.fileReader(levelInt).get(0),levelReader.fileReader(levelInt).get(2),new GuardCirclePatrolling(),new Astar()));
                    gamestatemanager.push(new MainState(gsm,levelReader.fileReader(levelInt).get(1),levelReader.fileReader(levelInt).get(0),levelReader.fileReader(levelInt).get(2),guardAI,intruderAI));
                }
            }
        });
        
        stage.addActor(guardBots);
        stage.addActor(agentBots);
        stage.addActor(levelBox);
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
