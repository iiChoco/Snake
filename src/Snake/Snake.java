package Snake;

import utilities.Misc;

import java.awt.*;
import java.util.ArrayList;
import java.util.Map;

public class Snake extends Rectangle {

    private Map<String, Image> images;
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

    public void preloadImages() {
        String[] parts = {"tail_right", "tail_left", "tail_down", "tail_up", "body_bottomleft", "body_bottomright", "body_topleft", "body_topright", "body_horizontal", "body_vertical"};
        for (String part : parts) {
            images.put(part, Misc.loadImage(part + ".png"));
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