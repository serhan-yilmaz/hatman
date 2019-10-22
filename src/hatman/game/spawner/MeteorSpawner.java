/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hatman.game.spawner;

import hatman.game.block.Meteor;
import java.awt.Color;

/**
 *
 * @author Serhan Yilmaz <github.com/serhan-yilmaz>
 */
public class MeteorSpawner extends Spawner{
    private final Meteor product;
    
    public MeteorSpawner(int x, int y, Meteor product) {
        this(x, y, product, 600);
    }
    
    public MeteorSpawner(int x, int y, Meteor product, double spawn_period) {
        super(x, y, spawn_period);
        this.product = product;
    }
    
    @Override
    public void spawn() {
        int x = (int) (Math.random() * position.x);
        int y = (int) (Math.random() * position.y);
        int r = (int) (Math.random() * 255);
        int g = (int) (Math.random() * 255);
        int b = (int) (Math.random() * 255);
//        int alpha = (int) (Math.random() * 20) + 40;
        int radius = (int) ((0.9 + Math.random() * 0.1) * product.getRadius());
        double stunDuration = 30 * radius / product.getRadius();
        
        Meteor m = product.clone();
        m.setX(x);
        m.setY(y);
//        m.setColor(Color.red);
        m.setColor(new Color(r, g, b, 200));
        m.setRadius(radius);
        m.setStunDuration(stunDuration);
        gameElements.addMeteor(m);
    }
}
