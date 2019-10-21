/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hatman.game;

import hatman.game.modifier.StatusEffects;
import java.awt.Color;
import java.awt.Font;
import sygfx.ScaledGraphics;
import sygfx.util.Anchor;

/**
 *
 * @author Serhan Yilmaz <github.com/serhan-yilmaz>
 */
public class Player {
    private StatusEffects modifiers;
    
    private double health;
    private double max_health;
    private double regen = 0.08;
    
    public Player(StatusEffects modifiers){
        this.modifiers = modifiers;
        reset();
    }
    
    public final void reset(){
        max_health = 1000;
        health = max_health;
    }
    
    public void cycle(){
        regenerate();
        if(health > max_health){
            health = max_health;
        }
    }
    
    public void regenerate(){
        health += regen;
    }
    
    public void heal(double d){
        health += d;
    }
    
    public void damage(double d){
        health -= d;
    }
    
    public void draw(ScaledGraphics g){
        double ratio = health / max_health;
        g.setAnchor(Anchor.NORTHWEST);
        if(ratio > 0){
            g.setColor(Color.green);
            g.fillRect(160, 20, (int) (250D * ratio), 30);
        }
        g.setColor(Color.red);
        g.fillRect((int)(160 + 250 * ratio), 20, (int)(250D - 250D * ratio), 30);
        g.setFont(new Font("Arial", Font.PLAIN, 16));
        g.setColor(Color.black);
        g.setAnchor(Anchor.WEST);
        g.drawString("Health : ", 45, 35);
        g.drawString("Status Effects : ", 45, 70);
        g.setAnchor(Anchor.CENTER);
        g.drawString("" + (int) health, 285, 35);
    }
    
    public double getHealth(){
        return health;
    }
    
}
