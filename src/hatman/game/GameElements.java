/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hatman.game;

import hatman.game.block.Block;
import hatman.game.block.RedBall;
import hatman.game.spawner.Spawner;
import hatman.util.UnorderedArrayList;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import sygfx.ScaledGraphics;

/**
 *
 * @author Serhan Yilmaz <github.com/serhan-yilmaz>
 */
public class GameElements {
    private final UnorderedArrayList<RedBall> redballs;
    private final UnorderedArrayList<Spawner> spawners;
    
    public GameElements(){
        redballs = new UnorderedArrayList<>();
        spawners = new UnorderedArrayList<>();
    }
    
    public void reset(){
        redballs.clear();
        spawners.clear();
    }
    
    public void addSpawner(Spawner s){
        s.setGameElements(this);
        spawners.add(s);
    }
    
    public void addRedball(RedBall redball){
        redballs.add(redball);
    }
    
    public UnorderedArrayList<RedBall> getRedballs(){
        return redballs;
    }
    
    public void cycle(){
        Iterator<RedBall> iterator = redballs.iterator();
        while(iterator.hasNext()){
            Block bo = iterator.next();
            if(bo.cycle()){
                iterator.remove();
            }
        }
        
        for(Spawner s: spawners){
            s.cycle();
        }
    }
    
    public void draw(ScaledGraphics g){
        for(RedBall r: redballs){
            r.draw(g);
        }
        for (Spawner s : spawners) {
            s.draw(g);
        }
    }
    
}
