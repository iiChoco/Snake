package Snake;

import utilities.Misc;

import java.awt.*;
import java.util.ArrayList;

public class Powerups extends Rectangle {
    private String type;
    private final String[] powerups = {"domain", "speed", "slow", "levelUp", "levelDown", "none"};
    private static boolean inDomain = false;
    private int timer = 2;
    private boolean timerOn = false;

    public Powerups(int x, int y, int width, int height) {
        super(x, y, width, height);
        type = "domain";
    }

    public void powerupLogic(int time) {
        spawnPowerup(time);
        if (timerOn) {
            timer++;
        }
        if (timer % 1440 == 0 && inDomain) {
            inDomain = false;
            timerStop();
        }
    }

    public void powerUp(ArrayList<Apple> a) {
        switch (type) {
            case "domain":
                Misc.playSound("domain.wav");
                inDomain = true;
                System.out.println(inDomain);
                timerStart();
                break;
            case "speed":
                Snake.setDelay(5);
                break;
            case "slow":
                Snake.setDelay(20);
                break;
            case "levelUp":
                a.add(new Apple((int) (Math.random() * 20) * 50, (int) (Math.random() * 20) * 50, 50, 50));
                break;
            case "levelDown":
                a.removeLast();
                break;
        }
    }

    public void timerStart() {
        timerOn = true;
    }

    public void timerStop() {
        timerOn = false;
        timer = 0;
    }

    public static boolean getInDomain() {
        return inDomain;
    }

    public void setLoc(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void spawnPowerup(int time) {
        if (time % 600 == 0) {
            this.x = (int) (Math.random() * 20) * 50;
            this.y = (int) (Math.random() * 20) * 50;
            this.type = powerups[(int) (Math.random() * powerups.length)];
        }
    }

    public void draw(Graphics2D win) {
        win.setColor(Color.magenta);
        win.fill(this);
    }
}
