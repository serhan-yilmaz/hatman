/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hatman.game;

import hatman.util.FpsCounter;
import hatman.game.block.Hatman;
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
import sygfx.Scale;
import sygfx.ScaledGraphics;
import sygfx.util.Anchor;

/**
 *
 * @author Serhan Yilmaz <github.com/serhan-yilmaz>
 */
public class GameEnvironment {
    private final Dimension size;
    private BufferedImage mapImage;
    private Map map;
    private Hatman hatman = new Hatman(250, 250, 6);
    private AtomicBoolean busyFlag = new AtomicBoolean(false);
    private Scale gameScale = Scale.UNITY;
    private FpsCounter fpsCounter = new FpsCounter(50);
    private Scale mapImageScale = Scale.UNITY.scale(2, 2);
    private SpeedCalculator speedCalculator = new SpeedCalculator(hatman);
    
    public GameEnvironment(int width, int height){
        this.size = new Dimension(width, height);
        initialize();
    }
 
    private void initialize(){
        map = new Map(getMapWidth(), getMapHeight());
        GraphicsDevice gd = GraphicsEnvironment.
                getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int mapImageWidth = (int) mapImageScale.icX(getMapWidth());
        int mapImageHeight = (int) mapImageScale.icX(getMapHeight());
        System.out.println("MapImage : " + mapImageWidth + " - " + mapImageHeight);
        mapImage = gd.getDefaultConfiguration().
                createCompatibleImage(mapImageWidth, mapImageHeight, 1);
        Graphics g = mapImage.createGraphics();
        g.setColor(Color.white);
        g.fillRect(0, 0, mapImage.getWidth(), mapImage.getHeight());
        try {
            BufferedImage img = ImageIO.read(Map.class.
                    getResourceAsStream("/images/obstacleMap.png"));
            g.drawImage(img, 0, 0, mapImage.getWidth(),  
                    mapImage.getHeight(), null);
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }
    
    public void drawGameGraphics(ScaledGraphics sg){
        fpsCounter.count();
//        sg = new ScaledGraphics(sg, gameScale);
        sg.setAnchor(Anchor.NORTHWEST);
        ScaledGraphics sg2 = new ScaledGraphics(sg, mapImageScale);
        sg2.setAnchor(Anchor.NORTHWEST);
        sg2.drawImage(mapImage, 0, 0, null);
        sg.setAnchor(Anchor.CENTER);
        sg.setColor(Color.blue);
//        sg.fillOval(endX, endY, 20, 20);
        hatman.draw(sg);
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
    
    public BufferedImage getMapImage(){
        return mapImage;
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
        hatman.cycle();
        speedCalculator.cyle();
//        System.out.println("FPS : " + fpsCounter.getFps());
    }
    
    public int getFPS(){
        return fpsCounter.getFPS()/2;
    }
    
}
