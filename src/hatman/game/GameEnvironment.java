/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hatman.game;

import hatman.game.block.BlackBullet;
import hatman.game.block.Block;
import hatman.util.FpsCounter;
import hatman.game.block.Hatman;
import hatman.game.block.Meteor;
import hatman.game.block.Mine;
import hatman.game.block.RedBall;
import hatman.game.block.Sparkle;
import hatman.game.block.Visual;
import hatman.game.block.WaterBlock;
import hatman.game.block.Witch;
import hatman.game.modifier.StatusEffects;
import hatman.game.spawner.BlackBulletSpawner;
import hatman.game.spawner.MeteorSpawner;
import hatman.game.spawner.MineSpawner;
import hatman.game.spawner.RedBallSpawner;
import hatman.game.spawner.Spawner;
import hatman.game.util.SpeedCalculator;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.imageio.ImageIO;
import hatman.mapsolver.Map;
import hatman.mapsolver.Path;
import hatman.util.UnorderedArrayList;
import java.awt.Font;
import java.awt.Point;
import java.util.Iterator;
import sygfx.Scale;
import sygfx.ScaledGraphics;
import sygfx.util.Anchor;

/**
 *
 * @author Serhan Yilmaz <github.com/serhan-yilmaz>
 */
public class GameEnvironment {
    private final Dimension size;
    private Map map;
    private GameMapVisuals mapVisuals;
    private StatusEffects statusEffects;
    private Hatman hatman;
    private Scale gameScale = Scale.UNITY;
    private FpsCounter fpsCounter = new FpsCounter(50);
    private SpeedCalculator speedCalculator;
    private GameElements gameElements = new GameElements();
    private Player player;
    private boolean paused = false;
    private boolean gameover = false;
    private int gametime = 0;
    private WaterBlock water;
    private Witch witch;
    
    public GameEnvironment(int width, int height){
        this.size = new Dimension(width, height);
        initialize();
    }
 
    private void initialize(){
        statusEffects = new StatusEffects();
        hatman = new Hatman(250, 250, 5, 20, statusEffects);
        player = new Player(statusEffects);
        water = new WaterBlock(1280, 720, 1, 250);
        map = new Map(getMapWidth(), getMapHeight());
        mapVisuals = new GameMapVisuals(this.size, map);
        reset();
    }
    
    public void reset(){
        speedCalculator = new SpeedCalculator(hatman);
        gameElements.reset();
        statusEffects.reset();
        player.reset();
        
        witch = new Witch(200, 800, 1.25, 20, hatman, map, player);
        
        RedBall prototype = new RedBall(0, 0, 3.52, 10, hatman);
        Spawner redballspawner = new Spawner();
        redballspawner.addSpawner(new RedBallSpawner (100, 60, prototype));
        redballspawner.addSpawner(new RedBallSpawner (2360, 60, prototype));
        redballspawner.addSpawner(new RedBallSpawner (100, 1380, prototype));
        redballspawner.addSpawner(new RedBallSpawner (2360, 1380, prototype));
        redballspawner.setSpawnPeriod(300);
        
        BlackBullet bb = new BlackBullet(0, 0, 15.3, 20, 0);
        Spawner bbspawner = new Spawner();
        bbspawner.addSpawner(new BlackBulletSpawner(100, 60, bb, hatman));
        bbspawner.addSpawner(new BlackBulletSpawner(2360, 60, bb, hatman));
        bbspawner.addSpawner(new BlackBulletSpawner(100, 1380, bb, hatman));
        bbspawner.addSpawner(new BlackBulletSpawner(2360, 1380, bb, hatman));
        bbspawner.setSpawnPeriod(352);
        
        Mine mn = new Mine(0, 0, 60);
        Spawner mnspawner = new Spawner();
        mnspawner.addSpawner(new MineSpawner(size.width, size.height, mn));
        mnspawner.setSpawnPeriod(200);
        
        Meteor meteor = new Meteor(0, 0, 140, null);
        Spawner meteorSpawner = new MeteorSpawner(size.width, size.height, meteor);
        meteorSpawner.setSpawnPeriod(63);
        
        gameElements.addVisuals(new Visual(100, 60, 50));
        gameElements.addVisuals(new Visual(2360, 60, 50));
        gameElements.addVisuals(new Visual(100, 1380, 50));
        gameElements.addVisuals(new Visual(2360, 1380, 50));
        
        gametime = 0;
        gameElements.addSpawner(redballspawner);
        gameElements.addSpawner(bbspawner);
        gameElements.addSpawner(mnspawner);
        gameElements.addSpawner(meteorSpawner);
        gameover = false;
        System.gc();
    }
    
    public void countfps(){
        fpsCounter.count();
    }
    
    public void drawGameGraphics(ScaledGraphics sg){
        mapVisuals.draw(sg);
        gameElements.drawVisuals(sg);
        water.draw(sg);
        witch.draw(sg);
        hatman.draw(sg);
        gameElements.draw(sg);
    }
    
    public void drawUIGraphics(ScaledGraphics g){
        player.draw(g);
        statusEffects.draw(g);
        
        String timeString = (new StringBuilder()).append("Time : ")
                .append(getTimeString(gametime)).toString();
        g.setAnchor(Anchor.WEST);
        g.setColor(Color.black);
        g.setFont(new Font("Arial", Font.PLAIN, 16));
        g.drawString(timeString, 1080, 35);
        g.drawString("Fps : " + getFPS(), 1080, 65);
        if(gameover)
        {
            g.setAnchor(Anchor.CENTER);
            g.setColor(Color.black);
            g.setFont(new Font("Arial", 0, 64));
            g.drawString("Game Over ", 640, 310);
            g.setFont(new Font("Arial", 0, 28));
            String scoretext = (new StringBuilder()).append("Score : ").append(getScore()).toString();
            g.drawString(scoretext, 640, 355);
            g.setFont(new Font("Arial", 0, 24));
            g.drawString("Press R to Restart ", 640, 385);
        }
    }
    
    public void moveHatman(int x, int y){
        if(map.reserve()){
            Path path = map.solve(hatman.getX(), hatman.getY(), x, y);
            if(path != null){
                path.poll();
                hatman.move(path);
            }
            map.release();
        }
    }
    
    public Scale getScale(){
        return gameScale;
    }
    
    public void setScale(Scale scale){
        this.gameScale = scale;
    }
    
    public int getMapWidth(){
        return size.width;
    }
    
    public int getMapHeight(){
        return size.height;
    }
    
    public Hatman getHatman(){
        return hatman;
    }
    
    public void cycle(){
        fpsCounter.cycle();
        
        if(paused || gameover){
            return;
        }
        gametime++;
        
        hatman.cycle();
        water.cycle();
        witch.cycle();
//        speedCalculator.cycle();
        gameElements.cycle();
        statusEffects.cycle();
        
        Sparkle sparkle = witch.spawnSparkle();
        if(sparkle != null){
            gameElements.addVisuals(sparkle);
        }
        
        Iterator<RedBall> iterator = gameElements.getRedballs().iterator();
        while(iterator.hasNext()){
            RedBall r = iterator.next();
            if(r.isTargetReached()){
                statusEffects.inflictFlame(0.3);
                player.damage(355);
                iterator.remove();
            }
        }
        
        Iterator<BlackBullet> itBB = gameElements.getBlackBullets().iterator();
        while(itBB.hasNext()){
            BlackBullet b = itBB.next();
            if(b.isTargetReached(hatman)){
                player.damage(385);
                statusEffects.refreshStun(8);
                itBB.remove();
            }
        }
        
        for(Mine m: gameElements.getMines()){
            if(m.isTargetReached(hatman)){
                m.triggerMine();
            }
            if(m.isTargetReached(witch)){
                m.triggerMine();
            }
            if(m.isTargetExploded(hatman)){
                player.damage(200);
                statusEffects.inflictFlame(0.9);
            }
            if(m.isExploded()){
                for(Mine m2: gameElements.getMines()){
                    if(m != m2 && m.isTargetExploded(m2)){
                        m2.triggerMine();
                    }
                }
            }
        }
        
        for(Meteor m: gameElements.getMeteors()){
            if(m.isTargetStunned(hatman)){
                statusEffects.refreshStun(m.getStunDuration());
            }
        }
        
        if(water.isBlockInside(hatman)){
            player.healPercentage(4.5e-4);
            statusEffects.refreshWaterSlow(10);
            statusEffects.extinguishFlame(0.02);
        }
        
        if(water.isBlockInWave(hatman)){
            statusEffects.refreshWaveSlow(10);
        }
        
        player.cycle();
        
        if(player.getHealth() <= 0){
            gameover = true;
        }
    }
    
    public int getFPS(){
        return fpsCounter.getFPS();
    }
    
    public Player getPlayer(){
        return player;
    }
    
    public static String getTimeString(int gametime) {
        int[] ti = new int[5];
        ti[0] = (gametime % 50);
        ti[1] = ((gametime - ti[0]) / 50);
        ti[0] *= 2;
        ti[2] = (ti[1] % 60);
        ti[3] = ((ti[1] - ti[2]) / 60);
        ti[4] = (ti[3] / 60);
        String s = String.valueOf(ti[3]).concat(":")
                .concat(String.valueOf(ti[2]).concat(".")
                .concat(String.valueOf(ti[0])));
        if (gametime == 0) return "-";
        return s;
    }
    
    public int getScore(){
        int difficulty = 0;
        return (int)((double)(4 * gametime) * (0.5D + (double)difficulty * 0.25D));
    }
    
    public Point getHatmanPosition(){
        return hatman.getPosition();
    }
    
    
}
