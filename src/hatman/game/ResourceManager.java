/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hatman.game;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Serhan Yilmaz <github.com/serhan-yilmaz>
 */
public class ResourceManager {
    public final static BufferedImage flower[] = new BufferedImage[10];
    
    public static BufferedImage wave_icon;
    public static BufferedImage ice_icon;
    public static BufferedImage stun_icon;
    public static BufferedImage flame_icon;
    public static BufferedImage mine_visual;
    public static BufferedImage mine_visual_triggered;
    public static BufferedImage witch_hat;
    public static BufferedImage witch_sparkle;
    
    public static void initialize(){
        for (int i = 0; i < 3; i++) {
            flower[i] = readImage("/resources/images/flowers/flower" + i + ".png");
        }
        wave_icon = readImage("/resources/images/modifiers/wave_icon.png");
        ice_icon = readImage("/resources/images/modifiers/ice_icon.gif");
        stun_icon = readImage("/resources/images/modifiers/stun.png");
        flame_icon = readImage("/resources/images/modifiers/flame_icon.gif");
        mine_visual = readImage("/resources/images/mine_icon.png");
        mine_visual_triggered = readImage("/resources/images/mine_icon2.png");
        witch_hat = readImage("/resources/images/witchhat.png");
        witch_sparkle =  readImage("/resources/images/sparkles/sparkle.png");
    }
    
    public static BufferedImage readImage(String path){
        BufferedImage img = null;
        try {
             img = ImageIO.read(ResourceManager.class.getResourceAsStream(path));
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
        return img;
    }
    
}
