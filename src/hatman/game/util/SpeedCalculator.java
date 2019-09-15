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
    private int numCycles;
    
    public SpeedCalculator(Block block){
        this.block = block;
        reset();
    }
    
    public final void reset(){
        startPosition = block.getPosition();
        timeStart = System.nanoTime();
        numCycles = 0;
    }
    
    public void cycle(){
        long time = System.nanoTime();
        numCycles++;
        if(time - timeStart >= 1e9){
            double distance = computeDistance(block.getPosition());
            System.out.println("Disposition: " + Math.round(distance) + 
                    ", Number of Cycles: " + numCycles);
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













