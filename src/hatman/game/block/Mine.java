/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hatman.game.block;

import hatman.game.ResourceManager;
import java.awt.Color;
import java.awt.image.BufferedImage;
import sygfx.ScaledGraphics;
import sygfx.util.Anchor;

/**
 *
 * @author ahmet
 */
public class Mine extends TransientBlock{
    
    private static final int EXPLOSION_RADIUS = 200;
    private static final int EXPLOSION_COUNTDOWN_TIME = 40;
    private static final int EXPLOSION_GAP = 40;
    
    private boolean triggered;
    private boolean exploded;
    private int explosionCountdown;
    private int explosionGap;

    public Mine(double x, double y, double radius)
    {        
        super(x, y, 0, radius, 3000);
        this.triggered = false;
        this.exploded = false;
        this.explosionCountdown = EXPLOSION_COUNTDOWN_TIME;
        this.explosionGap = EXPLOSION_GAP;
    }
    
    public boolean isTargetExploded(Block target)
    {
        return explosionCountdown == 0 && isTargetReached(target, EXPLOSION_RADIUS);
    }
    
    public boolean isTriggered()
    {
        return triggered;
    }
    
    @Override
    public void draw(ScaledGraphics sg) {
        int x = (int) this.x;
        int y = (int) this.y;
        int radius = 25;
        int trigger_radius = (int) this.radius;
        int explosion_radius = EXPLOSION_RADIUS;
        
        sg.setAnchor(Anchor.CENTER);
        if(triggered){
            sg.drawImage(ResourceManager.mine_visual_triggered, 
                    x, y, 2*radius, 2*radius, null);
        } else {
            sg.drawImage(ResourceManager.mine_visual, 
                    x, y, 2*radius, 2*radius, null);
        }
        sg.setColor(Color.black);
        sg.drawOval(x, y, 2 * trigger_radius, 2 * trigger_radius);
        if(exploded){
            sg.setColor(new Color(255, 0, 0, 120));
            sg.fillOval(x, y, 2 * explosion_radius, 2 * explosion_radius);
        }
    }

    @Override
    public boolean cycle() {
        if(isExploded())
        {
           explosionGap--;
        }
        if(triggered)
        {
           explosionCountdown--;
           exploded = (explosionCountdown <= 0);
        }
        
        if(timer.cycle())
        {
            triggerMine();
        }
        return triggered && exploded && (explosionGap <= 0);
    }
    
    public void triggerMine()
    {
        triggered = true;
    }
    
    public boolean isExploded()
    {
        return exploded;
    }

    @Override
    public Mine clone() {
      return new Mine(x, y, radius);
    }
    
}
