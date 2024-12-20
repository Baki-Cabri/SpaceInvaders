package View;

import Model.Player;
import Model.SGameState;

import java.awt.*;

public class GameScreen implements SGameState {
    private Player player;

    public GameScreen() {
        player = new Player(150,50,50,50);
    }

    @Override
    public void update(double delta) {
        player.update(delta);
    }

    @Override
    public void draw(Graphics2D g) {
        player.draw(g);
    }

    @Override
    public void init(Canvas canvas) {
        canvas.addKeyListener(player);
    }
}
