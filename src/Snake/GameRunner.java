package Snake;

import utilities.GDV5;

import java.awt.*;
import java.util.ArrayList;

public class GameRunner extends GDV5 {
    private static final ArrayList<Snake> snakes = new ArrayList<>();
    private final int unit = 50;
    private final int width = GDV5.getMaxWindowX() / unit;
    private final int height = GDV5.getMaxWindowY() / unit;
    private int timer = 0;
    private final int initial = 2;
    private final SnakeHead head;

    private final Apple apple;


    public GameRunner() {
        head = new SnakeHead(GDV5.getMaxWindowX() / 2, GDV5.getMaxWindowY() / 2, unit, unit);
        for (int i = 0; i < initial; i++) {
            Snake s = new Snake(head.getHeadX(), head.getHeadY() + (i + 1) * unit, unit, unit);
            snakes.add(s);
        }
        apple = new Apple((int) (Math.random() * width) * unit, (int) (Math.random() * height) * unit, unit, unit);
    }

    public static void main(String[] args) {
        GameRunner snake = new GameRunner();
        snake.start();
    }

    @Override
    public void update() {
        timer++;
        head.headLogic(snakes, apple);
        if (timer % 60 == 0) {
            head.move(unit);
            head.setMoving(false);
            for (int i = 0; i < snakes.size(); i++) {
                snakes.get(i).move(head, snakes, i, unit);
            }


        }

    }

    public int getUnit() {
        return unit;
    }

    @Override
    public void draw(Graphics2D win) {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if ((i + j) % 2 == 0) {
                    win.setColor(new Color(179, 214, 101));
                } else {
                    win.setColor(new Color(172, 208, 94));
                }
                win.fillRect(i * unit, j * unit, unit, unit);
            }

        }


        head.draw(win);
        apple.draw(win);
        for (int i = 0; i < snakes.size(); i++) {
            snakes.get(i).draw(win, head, snakes, i, unit);
        }
    }


}
