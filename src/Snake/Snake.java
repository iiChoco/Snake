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
    private static int delay = 10;

    public Snake(int x, int y, int width, int height) {
        super(x, y, width, height);
        images = new HashMap<>();
        preloadImages();
    }

    public static void setDelay(int d) {
        delay = d;
    }

    public static void snakeLogic(int time, ArrayList<Snake> snake, ArrayList<Snake> enemy, ArrayList<Apple> apple, Apple superApple, int unit, Powerups powerup) {
        snake.getFirst().turn(snake, false);
        if (Splash.getMultiplayer()) {
            enemy.getFirst().turn(enemy, true);
        }
        snake.getFirst().colDet(snake, enemy, apple, superApple, powerup, false);
        enemy.getFirst().colDet(enemy, snake, apple, superApple, powerup, true);
        if (time % delay == 0 && !snake.getFirst().getTurn()) {
            for (Snake s : snake) {
                s.move(snake, snake.indexOf(s), unit);
            }
            for (Snake s : enemy) {
                if (!Powerups.getInDomain()) {
                    s.enemyMove(enemy, apple, enemy.indexOf(s), unit);
                }
            }
        }
        snake.getFirst().setTurn(false);
        enemy.getFirst().setTurn(false);
    }
    public void move(ArrayList<Snake> s, int ind, int unit) {
        lX = x;
        lY = y;
        if (ind == 0) {
            if (x < 0) {
                x = GDV5.getMaxWindowX() - 50;
            } else if (x >= GDV5.getMaxWindowX()) {
                x = 0;
            } else if (y < 0) {
                y = GDV5.getMaxWindowY() - 50;
            } else if (y >= GDV5.getMaxWindowY()) {
                y = 0;
            } else {
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
            }
        } else {
            x = s.get(ind - 1).lX;
            y = s.get(ind - 1).lY;
        }
    }

    public void enemyMove(ArrayList<Snake> s, ArrayList<Apple> a, int ind, int unit) {
        int appleX = a.getFirst().getAX();
        int appleY = a.getFirst().getAY();
        lX = x;
        lY = y;
        if (!Splash.getMultiplayer()) {
            if (ind == 0) {
                if (x != appleX) {
                    if (x < appleX) {
                        x += unit;
                    } else {
                        x -= unit;
                    }
                } else if (y != appleY) {
                    if (y < appleY) {
                        y += unit;
                    } else {
                        y -= unit;
                    }
                }
            } else {
                x = s.get(ind - 1).lX;
                y = s.get(ind - 1).lY;
            }
        } else {
            if (ind == 0) {
                if (x <= 0) {
                    x = GDV5.getMaxWindowX() - 50;
                } else if (x >= GDV5.getMaxWindowX()) {
                    x = 0;
                } else if (y <= 0) {
                    y = GDV5.getMaxWindowY() - 50;
                } else if (y >= GDV5.getMaxWindowY()) {
                    y = 0;
                } else {
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
                }
            } else {
                x = s.get(ind - 1).lX;
                y = s.get(ind - 1).lY;
            }
        }
    }

    public void turn(ArrayList<Snake> s, boolean enemy) {
        if (!enemy) {
            if (GDV5.KeysPressed[KeyEvent.VK_W] && dir != 1 && dir != 0 && !turn) {
                dir = 0;
                for (Snake snake : s) {
                    //snake.move(s, ind, unit);
                    snake.setTurn(true);
                }
            } else if (GDV5.KeysPressed[KeyEvent.VK_S] && dir != 0 && dir != 1 && !turn) {
                dir = 1;
                for (Snake snake : s) {
                    //snake.move(s, ind, unit);
                    snake.setTurn(true);
                }
            } else if (GDV5.KeysPressed[KeyEvent.VK_A] && dir != 3 && dir != 2 && !turn) {
                dir = 2;
                for (Snake snake : s) {
                    //snake.move(s, ind, unit);
                    snake.setTurn(true);
                }
            } else if (GDV5.KeysPressed[KeyEvent.VK_D] && dir != 2 && dir != 3 && !turn) {
                dir = 3;
                for (Snake snake : s) {
                    //snake.move(s, ind, unit);
                    snake.setTurn(true);
                }
            }
        } else {
            if (GDV5.KeysPressed[KeyEvent.VK_UP] && dir != 1 && dir != 0 && !turn) {
                dir = 0;
                for (Snake snake : s) {
                    //snake.move(s, ind, unit);
                    snake.setTurn(true);
                }
            } else if (GDV5.KeysPressed[KeyEvent.VK_DOWN] && dir != 0 && dir != 1 && !turn) {
                dir = 1;
                for (Snake snake : s) {
                    //snake.move(s, ind, unit);
                    snake.setTurn(true);
                }
            } else if (GDV5.KeysPressed[KeyEvent.VK_LEFT] && dir != 3 && dir != 2 && !turn) {
                dir = 2;
                for (Snake snake : s) {
                    //snake.move(s, ind, unit);
                    snake.setTurn(true);
                }
            } else if (GDV5.KeysPressed[KeyEvent.VK_RIGHT] && dir != 2 && dir != 3 && !turn) {
                dir = 3;
                for (Snake snake : s) {
                    //snake.move(s, ind, unit);
                    snake.setTurn(true);
                }
            }
        }
    }

    public void colDet(ArrayList<Snake> s, ArrayList<Snake> e, ArrayList<Apple> apple, Apple sa, Powerups powerup, boolean enemy) {
        Snake head = s.getFirst();
        if (head.intersects(powerup)) {
            powerup.powerUp(apple);
            powerup.setLoc(-100, -100);
        }
        for (Apple a : apple) {
            if (head.intersects(a)) {
                s.add(new Snake(s.getLast().x, s.getLast().y, width, height));
                a.relocate();
                Misc.playSound("eat.wav");
            }
        }
        if (head.intersects(sa) && s.size() > 8) {
            s.add(new Snake(s.get(s.size() - 1).x, s.get(s.size() - 1).y, width, height));
            s.add(new Snake(s.get(s.size() - 1).x, s.get(s.size() - 1).y, width, height));
            s.add(new Snake(s.get(s.size() - 1).x, s.get(s.size() - 1).y, width, height));
            s.add(new Snake(s.get(s.size() - 1).x, s.get(s.size() - 1).y, width, height));
            s.add(new Snake(s.get(s.size() - 1).x, s.get(s.size() - 1).y, width, height));
            sa.relocate();
            Misc.playSound("eat.wav");
        }
        if (!enemy) {
            for (int i = 1; i < s.size(); i++) {
                if (head.intersects(s.get(i))) {
                    Splash.end();
                }
            }
        }
        for (Snake snake : e) {
            if (head.intersects(snake)) {
                Splash.end();
            }
        }
    }


    public boolean getTurn() {
        return turn;
    }

    public void setTurn(boolean t) {
        turn = t;
    }


    public void preloadImages() {
        String[] enemyParts = {"g_up", "g_down", "g_right", "g_left", "head_up", "head_down", "head_left", "head_right", "tail_right", "tail_left", "tail_down", "tail_up", "body_bottomleft", "body_bottomright", "body_topleft", "body_topright", "body_horizontal", "body_vertical"};
        String[] parts = {"g_up", "g_down", "g_right", "g_left", "head_up", "head_down", "head_left", "head_right", "tail_right", "tail_left", "tail_down", "tail_up", "body_bottomleft", "body_bottomright", "body_topleft", "body_topright", "body_horizontal", "body_vertical"};
        for (String part : parts) {
            images.put(part, Misc.loadImage(part + ".png"));
        }
        for (String part : enemyParts) {
            images.put("enemy" + part, Misc.loadImage("enemy" + part + ".png"));
        }
    }


    public Image determineHead(ArrayList<Snake> s, boolean enemy) {
        Snake head = s.getFirst();
        Snake next = s.get(1);
        if (!enemy) {
            if (head.y - next.y == -50) {
                return images.get("head_up");
            }
            if (head.y - next.y == 50) {
                return images.get("head_down");
            }
            if (head.x - next.x == -50) {
                return images.get("head_left");
            }
            if (head.x - next.x == 50) {
                return images.get("head_right");
            }
        } else {
            if (head.y - next.y == -50) {
                return images.get("enemyhead_up");
            }
            if (head.y - next.y == 50) {
                return images.get("enemyhead_down");
            }
            if (head.x - next.x == -50) {
                return images.get("enemyhead_left");
            }
            if (head.x - next.x == 50) {
                return images.get("enemyhead_right");
            }
        }
        return null;


    }

    public Image determineBody(ArrayList<Snake> s, int ind, int unit, boolean enemy) {
        Snake current = s.get(ind);
        Snake next = s.get(ind + 1);
        Snake previous = s.get(ind - 1);
        if (!enemy) {
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
        } else {
            if (current.getY() == next.getY() && current.getY() == previous.getY()) {
                return images.get("enemybody_horizontal");
            }
            if (current.x == next.x && current.x == previous.x) {
                return images.get("enemybody_vertical");
            }
            int deltaXNext = (int) (next.getX() - current.getX());
            int deltaYNext = (int) (next.getY() - current.getY());
            int deltaXPrev = (int) (previous.getX() - current.getX());
            int deltaYPrev = (int) (previous.getY() - current.getY());

            if ((deltaXNext == unit && deltaYPrev == unit) || (deltaXPrev == unit && deltaYNext == unit)) {
                return images.get("enemybody_bottomright");
            }
            if ((deltaXNext == -unit && deltaYPrev == unit) || (deltaXPrev == -unit && deltaYNext == unit)) {
                return images.get("enemybody_bottomleft");
            }
            if ((deltaXNext == unit && deltaYPrev == -unit) || (deltaXPrev == unit && deltaYNext == -unit)) {
                return images.get("enemybody_topright");
            }
            if ((deltaXNext == -unit && deltaYPrev == -unit) || (deltaXPrev == -unit && deltaYNext == -unit)) {
                return images.get("enemybody_topleft");
            }
        }
        return null;
    }

    public Image determineTail(ArrayList<Snake> s, int ind, int unit, boolean enemy) {
        Snake current = s.get(ind);
        Snake previous = s.get(ind - 1);
        if (!enemy) {
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
        } else {
            if (current.x - previous.x == unit) {
                return images.get("enemytail_right");
            }
            if (current.x - previous.x == -unit) {
                return images.get("enemytail_left");
            }
            if (current.getY() - previous.getY() == unit) {
                return images.get("enemytail_down");
            }
            if (current.getY() - previous.getY() == -unit) {
                return images.get("enemytail_up");
            }
        }
        return null;
    }

    public void draw(Graphics2D pb, int ind, int unit, ArrayList<Snake> s, boolean enemy) {
        Image img = null;

        if (ind == 0) {
            img = determineHead(s, enemy);
        } else if (ind != s.size() - 1) {
            img = determineBody(s, ind, unit, enemy);
        } else {
            img = determineTail(s, ind, unit, enemy);
        }

        pb.drawImage(img, x, y, unit, unit, null);
    }

}