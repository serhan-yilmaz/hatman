/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hatman.game.block;

import java.awt.Color;
import sygfx.ScaledGraphics;
import sygfx.util.Anchor;

/**
 *
 * @author Serhan Yilmaz <github.com/serhan-yilmaz>
 */
public class BlackBullet extends TransientBlock{
    private double angle;
    
    public BlackBullet(double x, double y, double speed, 
            double radius, double angle) {
        super(x, y, speed, radius, 1000);
        this.angle = angle;
    }
    
    public void setAngle(double angle){
        this.angle = angle;
    }

    @Override
    public void draw(ScaledGraphics sg) {
        int x = (int) this.x;
        int y = (int) this.y;
        int radius = (int) this.radius;
        sg.setAnchor(Anchor.CENTER);
        sg.setColor(Color.black);
        sg.fillOval(x, y, 2 * radius, 2 * radius);
    }

    @Override
    public BlackBullet clone() {
        return new BlackBullet(x, y, speed, radius, angle);
    }

    @Override
    public boolean cycle() {
        this.x += this.speed * Math.cos(this.angle); 
        this.y += this.speed * Math.sin(this.angle);
        return timer.cycle();
    }
    
}
