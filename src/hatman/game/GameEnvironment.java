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
import hatman.game.block.RedBall;
import hatman.game.block.Visual;
import hatman.game.block.WaterBlock;
import hatman.game.spawner.BlackBulletSpawner;
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
import java.awt.Font;
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
    private Hatman hatman;
    private AtomicBoolean busyFlag = new AtomicBoolean(false);
    private Scale gameScale = Scale.UNITY;
    private FpsCounter fpsCounter = new FpsCounter(50);
    private SpeedCalculator speedCalculator;
    private GameElements gameElements = new GameElements();
    private Player player = new Player();
    private boolean gameover = false;
    private int gametime = 0;
    private WaterBlock water;
    
    public GameEnvironment(int width, int height){
        this.size = new Dimension(width, height);
        initialize();
    }
 
    private void initialize(){
        hatman = new Hatman(250, 250, 6, 20);
        water = new WaterBlock(1280, 720, 1, 250);
        map = new Map(getMapWidth(), getMapHeight());
        mapVisuals = new GameMapVisuals(this.size, map);
        reset();
    }
    
    public void reset(){
        speedCalculator = new SpeedCalculator(hatman);
        gameElements.reset();
        
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
        
        gameElements.addVisuals(new Visual(100, 60, 50));
        gameElements.addVisuals(new Visual(2360, 60, 50));
        gameElements.addVisuals(new Visual(100, 1380, 50));
        gameElements.addVisuals(new Visual(2360, 1380, 50));
        
        gametime = 0;
        gameElements.addSpawner(redballspawner);
        gameElements.addSpawner(bbspawner);
        player = new Player();
        gameover = false;
        System.gc();
    }
    
    public void countfps(){
        fpsCounter.count();
    }
    
    public void drawGameGraphics(ScaledGraphics sg){
//        fpsCounter.count();
        
        mapVisuals.draw(sg);
        gameElements.drawVisuals(sg);
        water.draw(sg);
        hatman.draw(sg);
        gameElements.draw(sg);
    }
    
    public void drawUIGraphics(ScaledGraphics g){
        player.draw(g);
        
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
        if(busyFlag.compareAndSet(false, true)){
            Path path = map.solve(hatman.getX(), hatman.getY(), x, y);
            if(path != null){
                path.poll();
                hatman.move(path);
            }
            busyFlag.set(false);
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
        
        if(gameover){
            return;
        }
        gametime++;
        
        hatman.cycle();
        water.cycle();
//        speedCalculator.cycle();
        gameElements.cycle();
        
        Iterator<RedBall> iterator = gameElements.getRedballs().iterator();
        while(iterator.hasNext()){
            RedBall r = iterator.next();
            if(r.isTargetReached()){
                player.damage(405);
                iterator.remove();
            }
        }
        
        Iterator<BlackBullet> itBB = gameElements.getBlackBullets().iterator();
        while(itBB.hasNext()){
            BlackBullet b = itBB.next();
            if(b.isTargetReached(hatman)){
                player.damage(385);
                itBB.remove();
            }
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
    
}
