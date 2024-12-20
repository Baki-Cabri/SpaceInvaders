package Model;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class SpriteAnimation {

    private ArrayList<BufferedImage> sprites = new ArrayList<BufferedImage>();
    private byte currentSprite;

    private int animationSpeed;
    private double xPos, yPos;
    private boolean loop = false;
    private boolean play = false;
    private boolean destroyAnimation = false;
    private Timer timer;

    public SpriteAnimation(double xPos, double yPos, int animationSpeed) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.animationSpeed = animationSpeed;

        timer = new Timer();
    }

    public boolean isDestroyAfterAnimation() {
        return destroyAnimation;
    }

    public void setDestroyAnimation(boolean destroyAnimation) {
        this.destroyAnimation = destroyAnimation;
    }

    public double getxPos() {
        return xPos;
    }

    public void setxPos(double xPos) {
        this.xPos = xPos;
    }

    public double getyPos() {
        return yPos;
    }
    public void setyPos(double yPos) {
        this.yPos = yPos;
    }


    public void draw(Graphics2D g) {
        g.drawImage(sprites.get(currentSprite), (int) getxPos(), (int) getxPos(), null);
    }
    public void update(double delta) {
        if(isSpriteAnimeDestroyed()) return;
        if (loop && !play) {loopAnimation();}
        if (play && !loop) {playAnimation();}
    }
    public void stopAnimation() {
        loop = false;
        play = false;
    }
    public void resetAnimation() {
        loop = false;
        play = false;
        currentSprite = 0;
    }

    private void loopAnimation() {
        if(timer.timerEvent(animationSpeed) && currentSprite != sprites.size()-1 ) {
            currentSprite++;
        }else if (timer.timerEvent(animationSpeed) && currentSprite == sprites.size()-1) {
            currentSprite = 0;
        }
    }

    private void playAnimation() {
        if(timer.timerEvent(animationSpeed) && currentSprite != sprites.size()-1 && !isSpriteAnimeDestroyed()) {
            play = false;
        }else if (timer.timerEvent(animationSpeed) && currentSprite == sprites.size()-1 && isDestroyAfterAnimation()) {
            sprites = null;
        }else if (timer.timerEvent(animationSpeed) && currentSprite == sprites.size()-1) {
            currentSprite++;
        }
    }

    public boolean isSpriteAnimeDestroyed(){
        if((sprites == null)) return true;
        return false;
    }


    public void addSpriteAnimation(String filePath, int xPos, int yPos, int width, int height) {
        BufferedImage spriteMap = null;
        try {
            spriteMap = ImageIO.read(new File(filePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        sprites.add(spriteMap.getSubimage(xPos, yPos, width, height));
    }

    public void PlayerAnimation(boolean play, boolean destroyAnimation) {
        this.play = play;
        this.destroyAnimation = destroyAnimation;
    }



}
