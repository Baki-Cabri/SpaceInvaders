package Model;

import View.GameScreen;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GameState  {

    private Canvas canvas;
    private ArrayList<SGameState> states = new ArrayList<SGameState>();
    private byte selectState =0;

    public GameState(Canvas canvas) {
        SGameState game = new GameScreen();
        states.add(game);
        this.canvas = canvas;
    }


    public void draw(Graphics2D g) {
        states.get(selectState).draw(g);
    }

    public void update(double delta) {
        states.get(selectState).update(delta);
    }

    public void setState(byte state) {
        for(int i = 0; i <canvas.getKeyListeners().length; i++){
            canvas.removeKeyListener(canvas.getKeyListeners()[state]);
        }
        selectState = state;
        states.get(selectState).init(canvas);
    }
    public byte getStates(){
        return selectState;
    }



}
