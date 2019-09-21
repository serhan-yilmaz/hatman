/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hatman.game;

import hatman.game.block.BlackBullet;
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
    private final UnorderedArrayList<BlackBullet> blackbullets;
    private final UnorderedArrayList<Spawner> spawners;
    private final UnorderedArrayList<Block> visuals;
    
    public GameElements(){
        redballs = new UnorderedArrayList<>();
        spawners = new UnorderedArrayList<>();
        visuals = new UnorderedArrayList<>();
        blackbullets = new UnorderedArrayList<>();
    }
    
    public void reset(){
        redballs.clear();
        spawners.clear();
        blackbullets.clear();
    }
    
    public void addVisuals(Block b){
        visuals.add(b);
    }
    
    public void addSpawner(Spawner s){
        s.setGameElements(this);
        spawners.add(s);
    }
    
    public void addRedball(RedBall redball){
        redballs.add(redball);
    }
    
    public void addBlackBullet(BlackBullet blackbullet){
        blackbullets.add(blackbullet);
    }
    
    public UnorderedArrayList<RedBall> getRedballs(){
        return redballs;
    }
    
    public UnorderedArrayList<BlackBullet> getBlackBullets(){
        return blackbullets;
    }
    
    public void cycle(){
        Iterator<RedBall> iteratorRB = redballs.iterator();
        while(iteratorRB.hasNext()){
            Block bo = iteratorRB.next();
            if(bo.cycle()){
                iteratorRB.remove();
            }
        }
        
        Iterator<BlackBullet> iteratorBB = blackbullets.iterator();
        while(iteratorBB.hasNext()){
            Block bo = iteratorBB.next();
            if(bo.cycle()){
                iteratorBB.remove();
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
        for (Block b : visuals) {
            b.draw(g);
        }
        
        for (Block b : blackbullets) {
            b.draw(g);
        }
    }
    
}
