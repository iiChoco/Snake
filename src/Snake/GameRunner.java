package Snake;

import utilities.GDV5;

import java.awt.*;
import java.util.ArrayList;


public class GameRunner extends GDV5 {

    private int timer = 0;
    private final int initial = 3;
    private final ArrayList<Snake> snake = new ArrayList<>();


    public GameRunner() {
        for (int i = 0; i < initial; i++) {
            snake.add(new Snake(200 - i * 20, 200, 20, 20));
        }
    }

    public static void main(String[] args) {
        GameRunner snake = new GameRunner();
        snake.start();
    }

    @Override
    public void update() {
        timer++;
        if (timer % 10 == 0) {
            for (Snake s : snake) {
                s.move(snake, snake.indexOf(s), 20);
            }
        }
    }



    @Override
    public void draw(Graphics2D win) {

    }


}
