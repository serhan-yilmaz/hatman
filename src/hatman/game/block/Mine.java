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
 * @author ahmet
 */
public class Mine extends TransientBlock{

    private final int DAMAGE_RADIUS = 200;
    private final int EXPLOSION_COUNTDOWN_TIME = 50;
    private final int EXPLOSION_GAP = 20;
    private final Block target;
    private boolean triggered;
    private boolean exploded;
    private int explosionCountdown;
    private int explosionGap;

    public Mine(double x, double y, double radius, Block target)
    {        
        super(x, y, 0, radius, 3000);
        this.target = target;
        this.triggered = false;
        this.exploded = false;
        this.explosionCountdown = EXPLOSION_COUNTDOWN_TIME;
        this.explosionGap = EXPLOSION_GAP;
    }
    
    public boolean isTargetExploded()
    {
        return explosionCountdown == 0 && isTargetReached(target);
    }
    public boolean isMineBlown()
    {
        if(triggered)
        {
            return true;
        }
        
        return false;
    }
    
    @Override
    public void draw(ScaledGraphics sg) {
        //.drawImage(img, DAMAGE_RADIUS, DAMAGE_RADIUS, io);
        int x = (int) this.x;
        int y = (int) this.y;
        int radius = 20;
        int trigger_radius = (int) this.radius;
        sg.setAnchor(Anchor.CENTER);
        sg.setColor(Color.gray);
        sg.fillOval(x, y, 2 * radius, 2 * radius);
        
        sg.setColor(Color.black);
        sg.drawOval(x, y, 2 * trigger_radius, 2 * trigger_radius);
    }

    @Override
    public boolean cycle() {
        if(isTargetReached(target))
        {
            triggerMine();
            
        }
        if(isExploded())
        {
           explosionGap = explosionGap -1;
        }
        if(triggered)
        {
           explosionCountdown = explosionCountdown - 1; 
           exploded = (explosionCountdown<=0);
        }
        if(timer.cycle())
        {
            triggerMine();
        }
        return triggered&&exploded&&(explosionGap == 0);
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
      return new Mine(x, y, radius, target);
    }
    
}
