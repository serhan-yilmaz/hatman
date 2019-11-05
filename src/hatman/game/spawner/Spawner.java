/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hatman.game.spawner;

import hatman.game.GameElements;
import hatman.util.Timer;
import java.awt.Point;
import java.util.ArrayList;
import sygfx.ScaledGraphics;

/**
 *
 * @author Serhan Yilmaz <github.com/serhan-yilmaz>
 */
public class Spawner {
    protected final ArrayList<Spawner> children = new ArrayList<>();
    protected Point position;
    protected GameElements gameElements;
    private Timer timer;
    
    public Spawner(){
        this(0, 0);
    }
    
    public Spawner(int x, int y){
        this(x, y, 600);
    }
    
    public Spawner(double spawn_period){
        this(0, 0, spawn_period);
    }
    
    public Spawner(int x, int y, double spawn_period){
        this(new Point(x, y), spawn_period);
    }
    
    public Spawner(Point p, double spawn_period){
        this.position = p;
        this.timer = new Timer(spawn_period);
    }
    
    public void setSpawnPeriod(double period){
        timer.setPeriod(period);
        for(Spawner s: children){
            s.setSpawnPeriod(period);
        }
    }
    
    public void setGameElements(GameElements ge){
        gameElements = ge;
        for(Spawner s: children){
            s.setGameElements(ge);
        }
    }
    
    public void addSpawner(Spawner s){
        s.setGameElements(gameElements);
        children.add(s);
    }
    
    public void cycle(){
        if(timer.cycle()){
            spawn();
        }
        for(Spawner s: children){
            s.cycle();
        }
    }
    
    public void draw(ScaledGraphics sg){
        for(Spawner s: children){
            s.draw(sg);
        }
    }
    
    public void spawn(){
        
    }
    
    public void setPosition(int x, int y){
        this.position = new Point(x, y);
    }
    
    
//    public Block spawn(){
//        Block copy = product.clone();
//        copy.setX(position.x);
//        copy.setY(position.y);
//        return copy;
//    }
    
}
