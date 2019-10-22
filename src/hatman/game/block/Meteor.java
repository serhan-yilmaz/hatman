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
public class Meteor extends TransientBlock{
    private final static double FALLING_TIME = 65;
//    private final static double HIT_DELAY = 25;
    private final static int INNER_RADIUS = 8;
    private final static int OUTER_GAP = 1;
    
    private boolean stun = false;
    private boolean falling = true;
    private Color color;
    private double current_radius = 0;
    private double stun_duration = 25;
    
    public Meteor(double x, double y, double radius, Color color) {
        super(x, y, 0, radius, 250);
        this.color = color;
    }
    
    public double getStunDuration(){
        return this.stun_duration;
    }
    
    public void setStunDuration(double stun_duration){
        this.stun_duration = stun_duration;
    }
    
    public void setRadius(double radius){
        this.radius = radius;
    }
    
    public void setColor(Color color){
        this.color = color;
    }
    
    public boolean isTargetStunned(Block b){
        return stun & this.isTargetReached(b, radius);
    }

    @Override
    public void draw(ScaledGraphics sg) {
        int x = (int) this.x;
        int y = (int) this.y;
        int radius = (int) (this.radius - OUTER_GAP);
        int current_radius = (int) this.current_radius;
        
        sg.setAnchor(Anchor.CENTER);
        sg.setColor(Color.black);
        sg.fillOval(x, y, 2 * INNER_RADIUS, 2 * INNER_RADIUS);
        
        if(falling){
            sg.setColor(color);
            sg.drawOval(x, y, 2 * current_radius, 2 * current_radius);
        } else {
            sg.setColor(new Color(color.getRed(), 
                    color.getBlue(), color.getGreen(), 140));
            sg.fillOval(x, y, 2 * current_radius, 2 * current_radius);
        }
        
        sg.setColor(color);
        sg.drawOval(x, y, 2 * radius, 2 * radius);
    }

    @Override
    public boolean cycle() {
        if(falling){
            double falling_speed = (this.radius - OUTER_GAP)/ FALLING_TIME;
            this.current_radius += falling_speed;
            if(this.current_radius >= (radius - OUTER_GAP)){
                this.current_radius = radius - OUTER_GAP;
                falling = false;
                stun = true;
            }
        } else {
            if(stun){
                stun = false;
                timer.reset();
                timer.setPeriod(stun_duration + 1);
            }
        }
        return timer.cycle();
    }

    @Override
    public Meteor clone() {
        return new Meteor(x, y, radius, color);
    }
    
}
