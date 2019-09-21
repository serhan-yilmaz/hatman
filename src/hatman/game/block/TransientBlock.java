/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hatman.game.block;

import hatman.util.Timer;

/**
 *
 * @author Serhan Yilmaz <github.com/serhan-yilmaz>
 */
public abstract class TransientBlock extends Block{
    protected Timer timer;
    
    public TransientBlock(double x, double y, double speed, 
            double radius, double lifespan) {
        super(x, y, speed, radius);
        timer = new Timer(lifespan);
    }
    
}
