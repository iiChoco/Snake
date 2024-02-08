package utilities;

import javax.sound.sampled.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Misc {
    //draw hitbox [debug purposes]
    public static <T extends Rectangle> void drawHitbox(Graphics2D win, T obj, int sp) {
        Color tc = win.getColor();
        win.setColor(Color.yellow);
        win.drawRect((int) obj.getX() - sp, (int) obj.getY() - sp, obj.width + 2 * sp, obj.height + 2 * sp);
        win.setColor(tc);
    }

    public static <T extends Rectangle> void drawHitbox(Graphics2D win, T obj, int sp, Color cl) {
        Color tc = win.getColor();
        win.setColor(cl);
        win.drawRect((int) obj.getX() - sp, (int) obj.getY() - sp, obj.width + 2 * sp, obj.height + 2 * sp);
        win.setColor(tc);
    }

    //check for collision between two objects
    public static <T extends Rectangle, U extends Rectangle> boolean checkHitbox(T obj1, U obj2, int error) {
        return (new Rectangle((int) obj1.getX() - error, (int) obj1.getY() - error, obj1.width + error, obj1.height + error)).intersects((new Rectangle((int) obj2.getX() - error, (int) obj2.getY() - error, obj2.width + error, obj2.height + error)));
    }

    public static void playSound(String soundFile) {
        File f = new File("src/resources/" + soundFile);
        AudioInputStream audioIn;
        try {
            audioIn = AudioSystem.getAudioInputStream(f.toURI().toURL());
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    //loads image
    public static Image loadImage(String imgFile) {
        Image img = null;
        try {
            img = Toolkit.getDefaultToolkit().getImage(new File("src/resources/" + imgFile).toURI().toURL());
        } catch (IOException e) {
            System.out.println("couldnt load image");
        }
        return img;
    }

    //add custom font
    public static Font addFont(String fontFile) {
        Font newFont = null;
        try {
            newFont = Font.createFont(Font.TRUETYPE_FONT, new File(fontFile)).deriveFont(30f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(newFont);
        } catch (IOException | FontFormatException e) {
            System.out.println("couldnt load font");
        }
        return newFont;
    }
}
