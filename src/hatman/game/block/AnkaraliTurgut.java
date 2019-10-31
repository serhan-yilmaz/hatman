/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hatman.game.block;

import hatman.mapsolver.Map;
import hatman.mapsolver.Path;
import sygfx.ScaledGraphics;

/**
 *
 * @author ahmet
 */
public class AnkaraliTurgut extends ConcreteBlock{
    
    private ConcreteBlock target;
    private int remainingTime;
    private Map map; 

    public AnkaraliTurgut(double x, double y, double speed, double radius, ConcreteBlock target) {
        super(x, y, speed, radius);
        this.target = target;
    }
    private void chaseToTarget(){
        int xInt = (int) Math.round(x);
        int yInt = (int) Math.round(y);
        Path path = map.solve( xInt ,yInt ,target.getX(), target.getY());
        if(path != null){
            path.poll();
            move(path);
        }
    }
    @Override
    public void draw(ScaledGraphics sg) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean cycle() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Block clone() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
