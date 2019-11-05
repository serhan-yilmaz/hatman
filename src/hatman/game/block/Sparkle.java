/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hatman.game.block;

import hatman.util.Timer;
import java.awt.Color;
import java.awt.image.BufferedImage;
import sygfx.ScaledGraphics;
import sygfx.util.Anchor;

/**
 *
 * @author Serhan Yilmaz <github.com/serhan-yilmaz>
 */
public class Sparkle extends Visual{
    private Timer timer;
    private Timer sparkleTimer;
    private boolean visible = Math.random() < 0.5;
    private final BufferedImage img;
    
    public Sparkle(double x, double y, double radius, 
            double sparkle_period, double lifespan, BufferedImage img) {
        super(x, y, radius);
        timer = new Timer(lifespan);
        sparkleTimer = new Timer(sparkle_period);
        sparkleTimer.setRemainingTime(sparkle_period * (Math.random() * 0.5 + 0.25));
        this.img = img;
    }
    
    @Override
    public void draw(ScaledGraphics sg) {
        int x = (int) this.x;
        int y = (int) this.y;
        int radius = (int) this.radius;
        if(visible){
            sg.setAnchor(Anchor.CENTER);
            sg.drawImage(img, x, y, 2 * radius, 2 * radius, null);
        }
//        sg.setColor(Color.MAGENTA);
//        sg.fillOval(x, y, 2 * radius, 2 * radius);
    }
    
    @Override
    public boolean cycle() {
        super.cycle();
        if(sparkleTimer.cycle()){
            visible = !visible;
        }
        return timer.cycle();
    }
    
}
