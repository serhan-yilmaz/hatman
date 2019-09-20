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
    
    public static void initialize(){
        for (int i = 0; i < 3; i++) {
            flower[i] = readImage("/resources/images/flowers/flower" + i + ".png");
        }
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
