package Snake;

import utilities.GDV5;

import java.awt.*;
import java.util.ArrayList;


public class GameRunner extends GDV5 {

    private final ArrayList<Snake> snake = new ArrayList<>();


    public GameRunner() {

    }

    public static void main(String[] args) {
        GameRunner snake = new GameRunner();
        snake.start();
    }

    @Override
    public void update() {

    }



    @Override
    public void draw(Graphics2D win) {

    }


}
