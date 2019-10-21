/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hatman.game;

import hatman.game.block.BlackBullet;
import hatman.game.block.Block;
import hatman.game.block.Mine;
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
    private final UnorderedArrayList<Mine> mines;
    
    public GameElements(){
        redballs = new UnorderedArrayList<>();
        spawners = new UnorderedArrayList<>();
        visuals = new UnorderedArrayList<>();
        blackbullets = new UnorderedArrayList<>();
        mines = new UnorderedArrayList<>();
    }
    
    public void reset(){
        redballs.clear();
        blackbullets.clear();
        visuals.clear();
        spawners.clear();
        mines.clear();
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
    
    public void addMine(Mine mine)
    {
        mines.add(mine);
    }
    
    public UnorderedArrayList<RedBall> getRedballs(){
        return redballs;
    }
    
    public UnorderedArrayList<BlackBullet> getBlackBullets(){
        return blackbullets;
    }
    
    public UnorderedArrayList<Mine> getMine() {
        return mines;
    }
    
    public UnorderedArrayList<Mine> getMines()
    {
        return mines;
    }
    
    private void cycleBlocks(UnorderedArrayList<? extends Block> list){
        Iterator<? extends Block> iterator = list.iterator();
        while(iterator.hasNext()){
            Block bo = iterator.next();
            if(bo.cycle()){
                iterator.remove();
            }
        }
    }
    
    public void cycle(){
        cycleBlocks(redballs);
        cycleBlocks(blackbullets);
        cycleBlocks(visuals);
        cycleBlocks(mines);
        
        for(Spawner s: spawners){
            s.cycle();
        }
    }
    
    private void drawBlocks(ScaledGraphics g, 
            UnorderedArrayList<? extends Block> list){
        for(Block b: list){
            b.draw(g);
        }
    }
    
    public void drawVisuals(ScaledGraphics g){
        drawBlocks(g, visuals);
    }
    
    public void draw(ScaledGraphics g){
        drawBlocks(g, redballs);
        drawBlocks(g, blackbullets);
        drawBlocks(g, mines);
        
        for (Spawner s : spawners) {
            s.draw(g);
        }
    }

 
    
}
