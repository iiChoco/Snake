package Snake;

import utilities.Misc;

import java.awt.*;
import java.util.ArrayList;

public class Snake extends Rectangle {

    private final int dir = 0; // 0 = up, 1 = down, 2 = left, 3 = right
    private int lX, lY;

    public Snake(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    public void move(ArrayList<Snake> s, int ind, int unit) {
        lX = x;
        lY = y;
        if (ind == 0) {
            switch (dir) {
                case 0:
                    y -= unit;
                    break;
                case 1:
                    y += unit;
                    break;
                case 2:
                    x -= unit;
                    break;
                case 3:
                    x += unit;
                    break;
            }
        } else {
            x = s.get(ind - 1).lX;
            y = s.get(ind - 1).lY;
        }

    }


    public void draw(Graphics2D pb, int ind) {
        if (ind == 0) {
            switch (dir) {
                case 0:
                    pb.drawImage(Misc.loadImage("head_up.png"), x, y, width, height, null);
            }
        }
    }

}