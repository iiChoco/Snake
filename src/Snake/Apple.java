package Snake;

import utilities.Misc;

import java.awt.*;

public class Apple extends Rectangle {

    public Apple(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    public void relocate() {
        this.x = (int) (Math.random() * 20) * 50;
        this.y = (int) (Math.random() * 20) * 50;
    }

    public void draw(Graphics2D win) {
        win.drawImage(Misc.loadImage("SASAN.png"), this.x, this.y, 50, 50, null);
    }


}