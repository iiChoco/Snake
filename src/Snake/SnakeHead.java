package Snake;

import utilities.GDV5;
import utilities.Misc;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class SnakeHead extends Rectangle {
    private int dir = 0;
    private int cX;
    private int cY;
    private boolean isMoving = false;

    public SnakeHead(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    public void headLogic(ArrayList<Snake> s, Apple apple) {
        turn();
        colDet(s, apple);
    }

    public void colDet(ArrayList<Snake> s, Apple a) {
        for (int i = 0; i < s.size(); i++) {
            if (this.intersects(s.get(i))) {
                System.out.println("Game Over");
                System.exit(0);
            }
        }
        if (this.intersects(a)) {
            s.add(new Snake(s.get(s.size() - 1).x, s.get(s.size() - 1).y, width, height));
            a.relocate();
        }

    }

    public void move(int unit) {
        isMoving = true;
        cY = this.y;
        cX = this.x;

        switch (dir) {
            case 0:
                this.y -= unit;
                break;
            case 1:
                this.y += unit;
                break;
            case 2:
                this.x -= unit;
                break;
            case 3:
                this.x += unit;
                break;
        }
        //System.out.println("Head x: " + this.x + " y: " + this.y);

    }

    public int getHeadX() {
        return x;
    }

    public int getHeadY() {
        return y;
    }

    public int getcX() {
        return cX;
    }

    public int getcY() {
        return cY;
    }

    public int getDir() {
        return dir;
    }

    public void setMoving(boolean b) {
        isMoving = b;
    }

    public void turn() {
        if (GDV5.KeysPressed[KeyEvent.VK_W] && dir != 1 && dir != 0) {
            dir = 0;
            if (!isMoving) {
                move(50);
            }


        }
        if (GDV5.KeysPressed[KeyEvent.VK_S] && dir != 0 && dir != 1) {
            dir = 1;
            if (!isMoving) {
                move(50);
            }

        }
        if (GDV5.KeysPressed[KeyEvent.VK_A] && dir != 3 && dir != 2) {
            dir = 2;
            if (!isMoving) {
                move(50);
            }

        }
        if (GDV5.KeysPressed[KeyEvent.VK_D] && dir != 2 && dir != 3) {
            dir = 3;
            if (!isMoving) {
                move(50);
            }
        }
    }

    public void draw(Graphics2D pb) {
        switch (dir) {
            case 0:
                pb.drawImage(Misc.loadImage("head_up.png"), x, y, width, height, null);
                break;
            case 1:
                pb.drawImage(Misc.loadImage("head_down.png"), x, y, width, height, null);
                break;
            case 2:
                pb.drawImage(Misc.loadImage("head_left.png"), x, y, width, height, null);
                break;
            case 3:
                pb.drawImage(Misc.loadImage("head_right.png"), x, y, width, height, null);
                break;
        }
    }
}
