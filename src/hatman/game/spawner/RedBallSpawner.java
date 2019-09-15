/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hatman.game.spawner;

import hatman.game.block.RedBall;

/**
 *
 * @author Serhan Yilmaz <github.com/serhan-yilmaz>
 */
public class RedBallSpawner extends Spawner{
    private final RedBall product;
    
    public RedBallSpawner(int x, int y, RedBall product) {
        this(x, y, product, 600);
    }
    
    public RedBallSpawner(int x, int y, RedBall product, double spawn_period) {
        super(x, y, spawn_period);
        this.product = product;
    }
    
    @Override
    public void spawn() {
        RedBall r = product.clone();
        r.setX(position.x);
        r.setY(position.y);
        gameElements.addRedball(r);
    }
}
