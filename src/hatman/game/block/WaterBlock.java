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
public class WaterBlock extends Block{
    private final static int MAXIMUM_RADIUS = 500;
    private final static int MINIMUM_RADIUS = 100;
    private final static int OUTER_RADIUS_SHIFT = 20;
    private final static int DEEP_WATER_LENGTH = 16;
    private final static int PAUSE_TIME = 50;
    
    private final static int STATUS_EXPANDING_OUTWARD = 1;
    private final static int STATUS_EXPANDING_INWARD = 2;
    private final static int STATUS_PAUSED_OUTER = 3;
    private final static int STATUS_PAUSED_INNER = 4;
    
    private int status = STATUS_EXPANDING_OUTWARD;
    private int counter = 0;
    
    public WaterBlock(double x, double y, double speed, double radius) {
        super(x, y, speed, radius);
    }

    @Override
    public void draw(ScaledGraphics sg) {
        int x = (int) this.x;
        int y = (int) this.y;
        int radius = (int) this.radius;
        int radius_inner = radius - DEEP_WATER_LENGTH;
        int radius_outer = MAXIMUM_RADIUS - OUTER_RADIUS_SHIFT;
        sg.setAnchor(Anchor.CENTER);
        sg.setColor(Color.black);
        sg.drawOval(x, y, 2 * radius_outer, 2 * radius_outer);
        sg.setColor(new Color(30, 100, 220, 60));
        sg.drawOval(x, y, 2 * radius, 2 * radius);
        sg.setColor(new Color(30, 100, 220, 8));
        sg.fillOval(x, y, 2 * radius_inner, 2 * radius_inner);
        sg.setColor(new Color(30, 100, 220, 16));
        sg.fillOval(x, y, 2 * radius, 2 * radius);
    }

    @Override
    public boolean cycle() {
        switch(status){
            case STATUS_EXPANDING_OUTWARD:
                radius += speed;
                if(radius >= MAXIMUM_RADIUS){
                    radius = MAXIMUM_RADIUS;
                    status = STATUS_PAUSED_OUTER;
                    counter = PAUSE_TIME;
                }
                break;
            case STATUS_EXPANDING_INWARD:
                radius -= speed;
                if(radius <= MINIMUM_RADIUS){
                    radius = MINIMUM_RADIUS;
                    status = STATUS_PAUSED_INNER;
                    counter = PAUSE_TIME;
                }
                break;
            case STATUS_PAUSED_OUTER:
                counter--;
                if(counter <= 0)
                    status = STATUS_EXPANDING_INWARD;
                break;
            case STATUS_PAUSED_INNER:
                counter--;
                if(counter <= 0)
                    status = STATUS_EXPANDING_OUTWARD;
                break;
            default:
                throw new RuntimeException("Invalid status parameter.");
        }
        return false;
    }

    @Override
    public WaterBlock clone() {
        return new WaterBlock(x, y, speed, radius);
    }
}
