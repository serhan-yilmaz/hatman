/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hatman.game.modifier;

import java.awt.image.BufferedImage;
import sygfx.ScaledGraphics;

/**
 *
 * @author Serhan Yilmaz <github.com/serhan-yilmaz>
 */
public class Burn implements Modifier{
    private double burn = 0;
    private final BufferedImage img;
    
    public Burn(BufferedImage img) {
        this.img = img;
    }
    
    public void adjust(double amount){
        burn += amount;
        if(burn > 5){
            burn = 5;
        }
        if(burn < 0){
            burn = 0;
        }
    }
    
    @Override
    public int draw(ScaledGraphics g, int x, int y){
        int n = 0;
        for(int i = 1; i <= burn; i++){
            g.drawImage(img, x + 30 * n, y, 25, 25, null);
            n++;
        }
        return n;
    }
    
    @Override
    public void reset(){
        burn = 0;
    }
    
    @Override
    public void cycle(){
        if(burn < 1){
            burn -= 1e-4;
            burn /= 1.001;
        } else {
            burn += 1e-4;
            burn *= 1.001;
        }
        if(burn > 5){
            burn = 5;
        }
        if(burn < 0){
            burn = 0;
        }
    }
    
    public double getDamage(){
        return burn * 0.06 + burn * burn * 0.028;
    }
}
