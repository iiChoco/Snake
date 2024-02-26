package Snake;

import utilities.GDV5;
import utilities.Misc;

import static java.awt.event.KeyEvent.VK_ENTER;

public class Splash {
    private static String state = "";
    private static boolean newGame = false;
    private static boolean music = false;
    private static boolean multiplayer = false;

    public static void setState() {
        boolean enter = GDV5.KeysPressed[VK_ENTER];
        boolean m = GDV5.KeysPressed[77];
        boolean j = GDV5.KeysPressed[74];
        if (enter && state.equals("start")) {
            state = "game";
            if (music) {
                Misc.playSound("blue.wav");
            }
        }
        if (m && state.equals("start")) {
            multiplayer = true;
        }
        if (j && state.equals("start")) {
            music = true;
        }
        if (enter && state.equals("end")) {
            newGame = true;
        }

    }

    public static void start() {
        state = "start";
    }

    public static void end() {
        state = "end";
    }

    public static boolean getNG() {
        return newGame;
    }

    public static void nGFalse() {
        newGame = false;
    }

    public static boolean getMusic() {
        return music;
    }

    public static boolean getMultiplayer() {
        return multiplayer;
    }

    public static String getState() {
        return state;
    }
}
