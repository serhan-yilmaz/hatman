/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hatman.game.spawner;

import hatman.game.block.Mine;

/**
 *
 * @author ahmet
 */
public class MineSpawner extends Spawner{
    private final Mine product;
    
    public MineSpawner(int x, int y, Mine product) {
        this(x, y, product, 600);
    }
    
    public MineSpawner(int x, int y, Mine product, double spawn_period) {
        super(x, y, spawn_period);
        this.product = product;
    }
    
    @Override
    public void spawn() {
        int x = (int) (Math.random() * position.x);
        int y = (int) (Math.random() * position.y);
        Mine m = product.clone();
        m.setX(x);
        m.setY(y);
        gameElements.addMine(m);
    }
    
    
}
