package Snake;

import utilities.GDV5;
import utilities.Misc;

import java.awt.*;
import java.util.ArrayList;


public class GameRunner extends GDV5 {

    private int timer = 0;
    private final int unit = 50;
    private final int width = GDV5.getMaxWindowX() / unit;
    private final int height = GDV5.getMaxWindowY() / unit;
    private final int initial = 3;
    private final ArrayList<Snake> snake = new ArrayList<>();
    private final ArrayList<Snake> enemy = new ArrayList<>();
    private final Powerups powerup = new Powerups(100, 100, unit, unit);
    private final ArrayList<Apple> apples = new ArrayList<>();

    private Apple superApple;

    public GameRunner() {
        gameStart();
    }

    public void gameStart() {
        Splash.start();
        for (int i = 0; i < initial; i++) {
            snake.add(new Snake(200, GDV5.getMaxWindowY() / 2 + i * unit, 20, 20));
            enemy.add(new Snake(400, GDV5.getMaxWindowY() / 2 + i * unit + 100, 20, 20));
        }
        apples.add(new Apple((int) (Math.random() * width) * unit, (int) (Math.random() * height) * unit, unit, unit));
        superApple = new Apple((int) (Math.random() * width) * unit, (int) (Math.random() * height) * unit, unit, unit);

    }

    public void restart() {
        snake.clear();
        enemy.clear();
        apples.clear();
        gameStart();
    }
    public static void main(String[] args) {
        GameRunner game = new GameRunner();
        game.start();
    }

    @Override
    public void update() {
        Splash.setState();
        if (Splash.getNG()) {
            restart();
            Splash.nGFalse();
        }
        if (Splash.getState().equals("game")) {
            timer++;
            powerup.powerupLogic(timer);
            Snake.snakeLogic(timer, snake, enemy, apples, superApple, unit, powerup);
            levelUp();
        }

    }

    public void levelUp() {
        if (snake.size() % 5 == 0) {
            apples.add(new Apple((int) (Math.random() * width) * unit, (int) (Math.random() * height) * unit, unit, unit));
            snake.add(new Snake(snake.getLast().x, snake.getLast().y, width, height));

        }
    }



    @Override
    public void draw(Graphics2D win) {
        if (Splash.getState().equals("game")) {
            if (Powerups.getInDomain()) {
                win.drawImage(Misc.loadImage("InfiniteVoid.jpeg"), 0, 106, 1261, 787, null);
            } else {
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
            }
            FontMetrics metrics = win.getFontMetrics(win.getFont());
            win.setColor(Color.white);
            win.setFont(new Font("Comic Sans MS", Font.BOLD, 48));
            win.drawString("Score: " + (snake.size() - initial), 50, 50);
            for (Apple a : apples) {
                a.draw(win, true);
            }

            powerup.draw(win);
            if (snake.size() > 8) {
                superApple.draw(win, false);
            }
            for (Snake s : snake) {
                s.draw(win, snake.indexOf(s), unit, snake, false);
            }
            for (Snake s : enemy) {
                s.draw(win, enemy.indexOf(s), unit, enemy, true);
            }
        } else if (Splash.getState().equals("start")) {
            win.setColor(Color.white);
            win.setFont(new Font("Comic Sans MS", Font.BOLD, 48));
            FontMetrics metrics = win.getFontMetrics(win.getFont());
            win.drawString("Welcome to Snake!", ((GDV5.getMaxWindowX() - metrics.stringWidth("Welcome to Snake!")) / 2), 200);
            win.drawString("Press Enter to Start", ((GDV5.getMaxWindowX() - metrics.stringWidth("Press Enter to Start")) / 2), 300);
            win.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
            metrics = win.getFontMetrics(win.getFont());
            win.drawString("Use WASD to move", ((GDV5.getMaxWindowX() - metrics.stringWidth("Use WASD to move")) / 2), 400);
            win.drawString("Eat the apples to grow", ((GDV5.getMaxWindowX() - metrics.stringWidth("Eat the apples to grow")) / 2), 450);
            win.drawString("Press M for multiplayer", ((GDV5.getMaxWindowX() - metrics.stringWidth("Press M for multiplayer")) / 2), 500);
            win.drawString("Press J for music", ((GDV5.getMaxWindowX() - metrics.stringWidth("Press J for music")) / 2), 550);
            if (Splash.getMusic()) {
                win.drawString("Music: ON", ((GDV5.getMaxWindowX() - metrics.stringWidth("Music: ON")) / 2) - 100, 600);
            } else {
                win.drawString("Music: OFF", ((GDV5.getMaxWindowX() - metrics.stringWidth("Music: OFF")) / 2) - 100, 600);
            }
            if (Splash.getMultiplayer()) {
                win.drawString("Multiplayer: ON", ((GDV5.getMaxWindowX() - metrics.stringWidth("Multiplayer: ON")) / 2) + 100, 600);
            } else {
                win.drawString("Multiplayer: OFF", ((GDV5.getMaxWindowX() - metrics.stringWidth("Multiplayer: OFF")) / 2) + 100, 600);
            }
            win.drawString("By Jeff Wang", ((GDV5.getMaxWindowX() - metrics.stringWidth("By Jeff Wang")) / 2), 650);


        } else if (Splash.getState().equals("end")) {
            win.setColor(Color.white);
            win.setFont(new Font("Comic Sans MS", Font.BOLD, 48));
            FontMetrics metrics = win.getFontMetrics(win.getFont());
            win.drawString("Game Over", ((GDV5.getMaxWindowX() - metrics.stringWidth("Game Over")) / 2), 200);
            win.drawString("Press Enter to Restart", ((GDV5.getMaxWindowX() - metrics.stringWidth("Press Enter to Restart")) / 2), 300);
            win.drawString("Your score was " + (snake.size() - initial), ((GDV5.getMaxWindowX() - metrics.stringWidth("Your score was " + (snake.size() - initial))) / 2), 400);
        }
    }


}
