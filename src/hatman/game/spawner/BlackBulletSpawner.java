/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hatman.game.spawner;

import hatman.game.block.BlackBullet;
import hatman.game.block.Block;

/**
 *
 * @author Serhan Yilmaz <github.com/serhan-yilmaz>
 */
public class BlackBulletSpawner extends Spawner{
    private BlackBullet product;
    private Block target;
    
    public BlackBulletSpawner(int x, int y, double speed, 
            double radius, Block target) {
        this(x, y, speed, radius, target, 600);
    }
    
    public BlackBulletSpawner(int x, int y, double speed, 
            double radius, Block target, double spawn_period) {
        this(x, y, new BlackBullet(x, y, speed, radius, 0), 
                target, spawn_period);
    }
    
    public BlackBulletSpawner(int x, int y, BlackBullet product, Block target) {
        this(x, y, product, target, 600);
    }
    
    public BlackBulletSpawner(int x, int y, BlackBullet product, 
            Block target, double spawn_period) {
        super(x, y, spawn_period);
        this.target = target;
        this.product = product;
    }
    
    @Override
    public void spawn() {
        BlackBullet b = product.clone();
        b.setX(position.x);
        b.setY(position.y);
        b.setAngle(computeAngle(target));
        gameElements.addBlackBullet(b);
    }
    
    private double computeAngle(Block target){
        double dx = target.getX() - this.position.x;
        double dy = target.getY() - this.position.y;
        double ang = Math.atan2(dy, dx);
        return ang;
    }
    
}
