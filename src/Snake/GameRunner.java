package Snake;

import utilities.GDV5;

import java.awt.*;
import java.util.ArrayList;


public class GameRunner extends GDV5 {

    private int timer = 0;
    private final int unit = 50;
    private final int width = GDV5.getMaxWindowX() / unit;
    private final int height = GDV5.getMaxWindowY() / unit;
    private final int initial = 3;
    private final ArrayList<Snake> snake = new ArrayList<>();
    private final Apple apple;


    public GameRunner() {
        for (int i = 0; i < initial; i++) {
            snake.add(new Snake(200, GDV5.getMaxWindowY() / 2 + i * unit, 20, 20));
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
        snake.getFirst().turn(snake, 0, unit);
        snake.getFirst().colDet(snake, apple);
        if (timer % 10 == 0 && !snake.getFirst().getTurn()) {
            for (Snake s : snake) {
                s.move(snake, snake.indexOf(s), unit);
                s.setTurn(false);
            }
        }
        snake.getFirst().setTurn(false);
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
        apple.draw(win);
        for (Snake s : snake) {
            s.draw(win, snake.indexOf(s), unit, snake);
        }
    }


}
