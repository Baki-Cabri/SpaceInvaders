package Model;

import java.awt.*;

public interface SGameState {
    public void update( double delta);
    public void draw(Graphics2D g);
    public void init(Canvas canvas);
}
