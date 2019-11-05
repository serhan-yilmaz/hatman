/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hatman.game.spawner;

import hatman.game.block.Visual;

/**
 *
 * @author Serhan Yilmaz <github.com/serhan-yilmaz>
 */
public class VisualSpawner extends Spawner{
    private final Visual product;
    
    public VisualSpawner(int x, int y, Visual product) {
        this(x, y, product, 600);
    }
    
    public VisualSpawner(int x, int y, Visual product, double spawn_period) {
        super(x, y, spawn_period);
        this.product = product;
    }
    
    @Override
    public void spawn() {
        if(this.product != null){
            spawn(this.product);
        }
    }
    
    public void spawn(Visual product) {
        Visual r = product.clone();
        r.setX(position.x);
        r.setY(position.y);
        gameElements.addVisuals(r);
    }
}
