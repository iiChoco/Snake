package Snake;

import utilities.GDV5;
import utilities.Misc;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Snake extends Rectangle {

    private final Map<String, Image> images;
    private int dir = 0; // 0 = up, 1 = down, 2 = left, 3 = right
    private int lX, lY;
    private boolean turn = false;

    public Snake(int x, int y, int width, int height) {
        super(x, y, width, height);
        images = new HashMap<>();
        preloadImages();
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

    public void turn(ArrayList<Snake> s, int ind, int unit) {
        if (GDV5.KeysPressed[KeyEvent.VK_W] && dir != 1 && dir != 0 && !turn) {
            dir = 0;
            for (Snake snake : s) {
                //snake.move(s, ind, unit);
                snake.setTurn(true);
            }
        } else if (GDV5.KeysPressed[KeyEvent.VK_S] && dir != 0 && dir != 1) {
            dir = 1;
            for (Snake snake : s) {
                //snake.move(s, ind, unit);
                snake.setTurn(true);
            }
        } else if (GDV5.KeysPressed[KeyEvent.VK_A] && dir != 3 && dir != 2) {
            dir = 2;
            for (Snake snake : s) {
                //snake.move(s, ind, unit);
                snake.setTurn(true);
            }
        } else if (GDV5.KeysPressed[KeyEvent.VK_D] && dir != 2 && dir != 3) {
            dir = 3;
            for (Snake snake : s) {
                //snake.move(s, ind, unit);
                snake.setTurn(true);
            }

        }
    }

    public void colDet(ArrayList<Snake> s, Apple a) {
        Snake head = s.getFirst();
        if (head.intersects(a)) {
            s.add(new Snake(s.get(s.size() - 1).x, s.get(s.size() - 1).y, width, height));
            a.relocate();
        }

    }

    public boolean getTurn() {
        return turn;
    }

    public void setTurn(boolean t) {
        turn = t;
    }


    public void preloadImages() {
        String[] parts = {"g_up", "g_down", "g_right", "g_left", "head_up", "head_down", "head_left", "head_right", "tail_right", "tail_left", "tail_down", "tail_up", "body_bottomleft", "body_bottomright", "body_topleft", "body_topright", "body_horizontal", "body_vertical"};
        for (String part : parts) {
            images.put(part, Misc.loadImage(part + ".png"));
        }
    }

    public Image determineHead() {
        switch (dir) {
            case 0:
                return images.get("g_up");
            case 1:
                return images.get("g_down");
            case 2:
                return images.get("g_left");
            case 3:
                return images.get("g_right");
        }
        return null;
    }

    public Image determineBody(ArrayList<Snake> s, int ind, int unit) {
        Snake current = s.get(ind);
        Snake next = s.get(ind + 1);
        Snake previous = s.get(ind - 1);
        if (current.getY() == next.getY() && current.getY() == previous.getY()) {
            return images.get("body_horizontal");
        }
        if (current.x == next.x && current.x == previous.x) {
            return images.get("body_vertical");
        }
        int deltaXNext = (int) (next.getX() - current.getX());
        int deltaYNext = (int) (next.getY() - current.getY());
        int deltaXPrev = (int) (previous.getX() - current.getX());
        int deltaYPrev = (int) (previous.getY() - current.getY());

        if ((deltaXNext == unit && deltaYPrev == unit) || (deltaXPrev == unit && deltaYNext == unit)) {
            return images.get("body_bottomright");
        }
        if ((deltaXNext == -unit && deltaYPrev == unit) || (deltaXPrev == -unit && deltaYNext == unit)) {
            return images.get("body_bottomleft");
        }
        if ((deltaXNext == unit && deltaYPrev == -unit) || (deltaXPrev == unit && deltaYNext == -unit)) {
            return images.get("body_topright");
        }
        if ((deltaXNext == -unit && deltaYPrev == -unit) || (deltaXPrev == -unit && deltaYNext == -unit)) {
            return images.get("body_topleft");
        }
        return null;
    }

    public Image determineTail(ArrayList<Snake> s, int ind, int unit) {
        Snake current = s.get(ind);
        Snake previous = s.get(ind - 1);
        if (current.x - previous.x == unit) {
            return images.get("tail_right");
        }
        if (current.x - previous.x == -unit) {
            return images.get("tail_left");
        }
        if (current.getY() - previous.getY() == unit) {
            return images.get("tail_down");
        }
        if (current.getY() - previous.getY() == -unit) {
            return images.get("tail_up");
        }
        return null;
    }

    public void draw(Graphics2D pb, int ind, int unit, ArrayList<Snake> s) {
        Image img = null;
        if (ind == 0) {
            img = determineHead();
        } else if (ind != s.size() - 1) {
            img = determineBody(s, ind, unit);
        } else {
            img = determineTail(s, ind, unit);
        }
        pb.drawImage(img, x, y, unit, unit, null);
    }

}