/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hatman.game.util;

import hatman.game.block.Block;
import java.awt.Point;

/**
 *
 * @author Serhan Yilmaz <github.com/serhan-yilmaz>
 */
public class SpeedCalculator {
    private long timeStart;
    private Point startPosition;
    private final Block block;
    
    public SpeedCalculator(Block block){
        this.block = block;
        reset();
    }
    
    public final void reset(){
        startPosition = block.getPosition();
        timeStart = System.nanoTime();
    }
    
    public void cyle(){
        long time = System.nanoTime();
        if(time - timeStart >= 1e9){
            double distance = computeDistance(block.getPosition());
            System.out.println("Disposition: " + Math.round(distance));
            reset();
        }
    }
    
    private double computeDistance(Point p){
        double dx = startPosition.x - p.x;
        double dy = startPosition.y - p.y;
        double distance = Math.sqrt(dx * dx + dy * dy);
        return distance;
    }
            
            
    
}
