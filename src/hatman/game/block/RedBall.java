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
public class RedBall extends TransientBlock{
    private final Block target;
    
    public RedBall(double x, double y, double speed, 
            double radius, Block target) {
        super(x, y, speed, radius, 3000);
        this.target = target;
    }

    @Override
    public void draw(ScaledGraphics sg) {
        int x = (int) this.x;
        int y = (int) this.y;
        int radius = (int) this.radius;
        sg.setAnchor(Anchor.CENTER);
        sg.setColor(Color.red);
        sg.fillOval(x, y, 2 * radius, 2 * radius);
    }

    @Override
    public boolean cycle() {
        moveThrough(target.x, target.y, this.speed);
//        if(isTargetReached()){
//            return true;
//        }
        return timer.cycle();
    }
    
    public boolean isTargetReached(){
        return isTargetReached(target);
    }

    @Override
    public RedBall clone() {
        return new RedBall(x, y, speed, radius, target);
    }
    
}
