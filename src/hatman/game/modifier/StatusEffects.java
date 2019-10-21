/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hatman.game.modifier;

import hatman.game.ResourceManager;
import java.util.ArrayList;
import sygfx.ScaledGraphics;
import sygfx.util.Anchor;

/**
 *
 * @author Serhan Yilmaz <github.com/serhan-yilmaz>
 */
public class StatusEffects {
    private ArrayList<Modifier> modifiers = new ArrayList<>();

    private Slow water_slow = new Slow(0.1, ResourceManager.ice_icon);
    private Slow wave_slow = new Slow(0.4, ResourceManager.wave_icon);
    private Stun stun = new Stun(ResourceManager.stun_icon);
    private Burn flame = new Burn(ResourceManager.flame_icon);

    public StatusEffects(){
        modifiers.add(water_slow);
        modifiers.add(wave_slow);
        modifiers.add(stun);
        modifiers.add(flame);
    }
    
    public void extinguishFlame(double amount){
        flame.adjust(-1*amount);
    }
    
    public void inflictFlame(double amount){
        flame.adjust(amount);
    }
    
    public void refreshStun(double duration){
        stun.refresh(duration);
    }
    
    public void refreshWaterSlow(double duration){
        water_slow.refresh(duration);
    }
    
    public void refreshWaveSlow(double duration){
        wave_slow.refresh(duration);
    }
    
    public void draw(ScaledGraphics g){
        g.setAnchor(Anchor.CENTER);
        int x = 172;
        int y = 67;
        for(Modifier m: modifiers){
            int nIcon = m.draw(g, x, y);
            x += 30 * nIcon;
        }
    }
    
    public void reset(){
        for(Modifier m: modifiers){
            m.reset();
        }
    }
    
    public void cycle(){
        for(Modifier m: modifiers){
            m.cycle();
        }
    }
    
    public double getMovementModifier(){
        return water_slow.getMovementModifier()
               * wave_slow.getMovementModifier()
               * stun.getMovementModifier();
    }
    
    public double getDamage(){
        return flame.getDamage();
    }
    
}
