/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hatman;

import hatman.mapsolver.Node;
import hatman.mapsolver.Path;

/**
 *
 * @author Serhan
 */
public abstract class ConcreteBlock extends Block{
    protected Path path = null;
    
    public ConcreteBlock(double x, double y, double speed) {
        super(x, y, speed);
    }
    
    public final synchronized void move(Path p){
        this.path = p;
    }
    
    @Override
    public void cycle(){
        double remain = speed;
        if(path != null){
            while(remain > 0 && !path.isEmpty()){
                Node n = path.peek();
                remain = moveThrough(n.getX(), n.getY(), remain);
                if(n.getX() == x && n.getY() == y){
                    path.poll();
                }
            }
        }
    }
    
    public Path getPath(){
        return path;
    }
}
