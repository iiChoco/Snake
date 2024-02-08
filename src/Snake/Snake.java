package Snake;

import utilities.Misc;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Snake extends Rectangle {
    private final Map<String, Image> images;
    private int lX, lY;

    public Snake(int x, int y, int width, int height) {
        super(x, y, width, height);
        images = new HashMap<>();
        preloadImages();

    }

    public void preloadImages() {
        String[] parts = {"tail_right", "tail_left", "tail_down", "tail_up", "body_bottomleft", "body_bottomright", "body_topleft", "body_topright", "body_horizontal", "body_vertical"};
        for (String part : parts) {
            images.put(part, Misc.loadImage(part + ".png"));
        }
    }

    public Image determineNeck(ArrayList<Snake> s, SnakeHead previous, int ind, int unit) {
        if (s == null || s.size() <= ind + 1) return null;

        Snake neck = s.get(ind);
        Snake segmentAfterNeck = s.get(ind + 1);

        int deltaXHead = previous.x - neck.x;
        int deltaYHead = previous.y - neck.y;
        int deltaXAfterNeck = segmentAfterNeck.x - neck.x;
        int deltaYAfterNeck = segmentAfterNeck.y - neck.y;


        if (Math.abs(deltaXHead) == unit && deltaYAfterNeck == 0) {
            return images.get("body_horizontal");
        } else if (Math.abs(deltaYHead) == unit && deltaXAfterNeck == 0) {
            return images.get("body_vertical");
        }

        if (deltaYHead == unit) { // Head is above the neck
            if (deltaXAfterNeck == unit) { // Turning right
                return images.get("body_bottomright");
            } else if (deltaXAfterNeck == -unit) { // Turning left
                return images.get("body_bottomleft");
            }
        } else if (deltaYHead == -unit) { // Head is below the neck
            if (deltaXAfterNeck == unit) { // Turning right
                return images.get("body_topright");
            } else if (deltaXAfterNeck == -unit) { // Turning left
                return images.get("body_topleft");
            }
        }

        // Head is directly left or right of the neck
        if (deltaXHead == unit) { // Head is to the left of the neck
            if (deltaYAfterNeck == unit) { // Turning down
                return images.get("body_bottomright");
            } else if (deltaYAfterNeck == -unit) { // Turning up
                return images.get("body_topright");
            }
        } else if (deltaXHead == -unit) { // Head is to the right of the neck
            if (deltaYAfterNeck == unit) { // Turning down
                return images.get("body_bottomleft");
            } else if (deltaYAfterNeck == -unit) { // Turning up
                return images.get("body_topleft");
            }
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

        // Check corners
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

    /*
    public void move2(SnakeHead h, ArrayList<Snake> s, int ind, int unit) {
        if (ind == 0) {
            this.x = h.getcX();
            this.y = h.getcY();

        } else {
            this.x = s.get(ind - 1).x;
            this.y = s.get(ind - 1).y;
        }
    }
         */

    public void move(SnakeHead h, ArrayList<Snake> s, int ind, int unit) {
        if (ind == 0) {
            lX = x;
            lY = y;
            x = h.getcX();
            y = h.getcY();
        } else {
            lX = x;
            lY = y;
            x = s.get(ind - 1).lX;
            y = s.get(ind - 1).lY;
        }
    }

    public void draw(Graphics2D pb, SnakeHead h, ArrayList<Snake> s, int ind, int unit) {
        Image part = null;
        if (ind == 0) { // head
            part = determineNeck(s, h, ind, unit);
        } else if (ind == s.size() - 1) { // tail
            part = determineTail(s, ind, unit);
        } else { // body
            part = determineBody(s, ind, unit);
        }

        if (part != null) {
            System.out.println("Drawing part " + ind + " at " + s.get(ind).x + " " + s.get(ind).y);

            pb.drawImage(part, s.get(ind).x, s.get(ind).y, unit, unit, null);
        }

    }
    /*
    public void draw(Graphics2D win) {
        win.setColor(Color.GREEN);
        win.drawRect(this.x, this.y, this.width, this.height);
        win.fill(this);
    }

     */

    // body_bottomleft.png
    // body_bottomright.png
    // body_horizontal.png
    // body_vertical.png
    // body_topleft.png
    // body_topright.png
/*
    public void draw(Graphics2D win, SnakeHead h, ArrayList<Snake> s, int ind, int unit) {
        if (ind == s.size()-1) {
            if (s.get(ind).getX() - s.get(ind-1).getX() == unit) {
                win.drawImage(Misc.loadImage("tail_right.png"), s.get(ind).x, s.get(ind).y, unit, unit, null);
            } else if (s.get(ind).getX() - s.get(ind-1).getX() == -unit) {
                win.drawImage(Misc.loadImage("tail_left.png"), s.get(ind).x, s.get(ind).y, unit, unit, null);
            } else if (s.get(ind).getY() - s.get(ind-1).getY() == unit) {
                win.drawImage(Misc.loadImage("tail_down.png"), s.get(ind).x, s.get(ind).y, unit, unit, null);
            } else if (s.get(ind).getY() - s.get(ind-1).getY() == -unit) {
                win.drawImage(Misc.loadImage("tail_up.png"), s.get(ind).x, s.get(ind).y, unit, unit, null);
            }
        } else if (ind == 0) {
            if (s.get(ind).getX() - h.getHeadX() == -unit && s.get(ind).getY() - s.get(1).getY() == -unit) {
                win.drawImage(Misc.loadImage("body_bottomleft.png"), s.get(ind).x, s.get(ind).y, unit, unit, null);

            } else if (Math.abs(s.get(ind).getX() - h.getHeadX()) == unit) {
                win.drawImage(Misc.loadImage("body_horizontal.png"), s.get(ind).x, s.get(ind).y, unit, unit, null);
            } else if (Math.abs(s.get(ind).getY() - h.getHeadY()) == unit) {
                win.drawImage(Misc.loadImage("body_vertical.png"), s.get(ind).x, s.get(ind).y, unit, unit, null);
            }
        } else if (ind != 0){
            if (s.get(ind).getY() - s.get(ind+1).getY() == -unit && s.get(ind).getX() - s.get(ind-1).getX() == unit) {
                win.drawImage(Misc.loadImage("body_bottomleft.png"), s.get(ind).x, s.get(ind).y, unit, unit, null);
            }
            if (s.get(ind).getY() - s.get(ind+1).getY() == -unit && s.get(ind).unit - s.get(ind-1).getX() == -unit) {
                win.drawImage(Misc.loadImage("body_bottomright.png"), s.get(ind).x, s.get(ind).y, unit, unit, null);
            }
            if (s.get(ind).getX() - s.get(ind+1).getX() == -unit && s.get(ind).getY() - s.get(ind-1).getY() == -unit) {
                win.drawImage(Misc.loadImage("body_bottomright.png"), s.get(ind).x, s.get(ind).y, unit, unit, null);
            }
            if (s.get(ind).getX() - s.get(ind+1).getX() == unit && s.get(ind).getY() - s.get(ind-1).getY() == unit) {
                win.drawImage(Misc.loadImage("body_topleft.png"), s.get(ind).x, s.get(ind).y, unit, unit, null);
            }
            if (s.get(ind).getX() - s.get(ind+1).getX() == -unit && s.get(ind).getY() - s.get(ind-1).getY() == unit) {
                win.drawImage(Misc.loadImage("body_topleft.png"), s.get(ind).x, s.get(ind).y, unit, unit, null);
            }
            if (s.get(ind).getY() - s.get(ind+1).getY() == unit && s.get(ind).getX() - s.get(ind-1).getX() == -unit) {
                win.drawImage(Misc.loadImage("body_topright.png"), s.get(ind).x, s.get(ind).y, unit, unit, null);

            }
            /*
            if (s.get(ind).getY() - s.get(ind+1).getY() == 50 && s.get(ind).getX() - s.get(ind-1).getX() == 50) {
                win.drawImage(Misc.loadImage("body_topleft.png"), s.get(ind).x, s.get(ind).y, 50, 50, null);
                System.out.println("topleft");

            }

            if (Math.abs(s.get(ind).getY() - s.get(ind + 1).getY()) == unit && Math.abs(s.get(ind).getY() - s.get(ind-1).getY()) == unit) {
                win.drawImage(Misc.loadImage("body_vertical.png"), s.get(ind).x, s.get(ind).y, unit, unit, null);
            }
            if (Math.abs(s.get(ind).getX() - s.get(ind + 1).getX()) == unit && Math.abs(s.get(ind).getX() - s.get(ind-1).getX()) == unit) {
                win.drawImage(Misc.loadImage("body_horizontal.png"), s.get(ind).x, s.get(ind).y, unit, unit, null);
            }



        }



    }

 */
}
