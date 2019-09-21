/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hatman.game;

import hatman.mapsolver.Map;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import sygfx.Scale;
import sygfx.ScaledGraphics;
import sygfx.util.Anchor;

/**
 *
 * @author Serhan Yilmaz <github.com/serhan-yilmaz>
 */
public class GameMapVisuals {
    private final Dimension size;
    private final Map map;
    
    private int flowermap[][];
    private BufferedImage mapImage;
    private Scale mapImageScale = Scale.UNITY.scale(1, 1);
//    private Scale mapImageScale = Scale.UNITY.scale(2, 2);
    private ScaledGraphics mg2;
//    private Graphics2D mg2;
    
    public GameMapVisuals(Dimension size, Map map){
        this.size = size;
        this.map = map;
        initialize();
    }
    
    private void initialize(){
        GraphicsDevice gd = GraphicsEnvironment.
                getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int mapImageWidth = (int) mapImageScale.icX(size.width);
        int mapImageHeight = (int) mapImageScale.icX(size.height);
        System.out.println("MapImage : " + mapImageWidth + " - " + mapImageHeight);
        mapImage = gd.getDefaultConfiguration().
                createCompatibleImage(mapImageWidth, mapImageHeight, 1);
        Graphics g = mapImage.createGraphics();
        g.setColor(Color.white);
        g.fillRect(0, 0, mapImage.getWidth(), mapImage.getHeight());
        
        
        try {
            BufferedImage img = ImageIO.read(GameMapVisuals.class.
                    getResourceAsStream("/resources/images/obstacleMap.png"));
            g.drawImage(img, 0, 0, mapImage.getWidth(),  
                    mapImage.getHeight(), null);
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
        
        mg2 = new ScaledGraphics(mapImage.createGraphics(), mapImageScale.inverse());
//        mg2 = mapImage.createGraphics();
        
        flowermap = new int[mapImage.getWidth()][mapImage.getHeight()];
        
        bufferMap();
    }
    
    public void draw(ScaledGraphics sg){
        ScaledGraphics sg2 = new ScaledGraphics(sg, mapImageScale);
        sg2.setAnchor(Anchor.NORTHWEST);
        sg2.drawImage(mapImage, 0, 0, null);
    }

    public void bufferMap()
    {
        mg2.setAnchor(Anchor.NORTHWEST);
        Random ran = new Random();
        
        for(int i = 0; i < mapImage.getWidth(); i++)
        {
            for(int j = 0; j < mapImage.getHeight(); j++)
                flowermap[i][j] = 0;
        }
        mg2.setColor(new Color(238, 238, 238));
        mg2.fillRect(0, 0, mapImage.getWidth(), mapImage.getHeight());
        mg2.setColor(new Color(0, 100, 130));
        
        
        int rgbcol = (new Color(0, 100, 130)).getRGB();
        int rgbcol2 = (new Color(20, 120, 45)).getRGB();
        int rgbcol3 = (new Color(220, 10, 15)).getRGB();
        int rgbcol4 = (new Color(238, 238, 238)).getRGB();
        
        
        int qf = Map.QUANTIZATION_FACTOR;
        int width = mapImage.getWidth();
        int height = mapImage.getHeight();
        
        for(int i = 0; i < width/qf; i++)
        {
            for(int j = 0; j < height/qf; j++)
            {
                int s = 5;
                int s2 = 5;
                double myran = Math.random();
                if(myran < 0.13D)
                    s = 9;
                else
                if(myran < 0.41999999999999998D)
                    s = 7;
                else
                if(myran < 0.5D)
                    s = 11;
                
                myran = Math.random();
                if(myran < 0.13D)
                    s2 = 9;
                else
                if(myran < 0.41999999999999998D)
                    s2 = 7;
                else
                if(myran < 0.5D)
                    s2 = 11;
                s = s * qf / 5;
                s2 = s2 * qf / 5;
                if(map.hasObstableAt(i, j))
                    mg2.fillRect(i * qf + (qf - s) / 2, j * qf + (qf - s2) / 2, s, s2);
            }
        }
        
        for(int i = 10; i < 2557; i++)
        {
            for(int j = 10; j < 1430; j++)
            {
                if(Math.random() >= 0.38D)
                    continue;
                boolean f = false;
                for(int m = 0; m < 3 && !f; m++)
                {
                    for(int n = 0; n < 3 && !f; n++)
                        if(getRGB((i + m) - 1, (j + n) - 1) == rgbcol 
                                || getRGB((i + m) - 1, (j + n) - 1) == rgbcol2)
                            f = true;
                }

                if(f){
                    setRGB(i, j, rgbcol2);
                }
            }
        }

        for(int i = 50; i < 2500; i++)
        {
            for(int j = 50; j < 1400; j++)
            {
                if(getRGB(i, j) != rgbcol2 || Math.random() >= 0.0030000000000000001D)
                    continue;
                int redsit = 0;
                int po = 5;
                for(int m = 0; m < po; m++)
                {
                    for(int n = 0; n < po; n++)
                    {
                        if(getRGB((i + m) - (po - 1) / 2, (j + n) - (po - 1) / 2) == rgbcol2)
                        {
                            redsit++;
                            continue;
                        }
                        if(getRGB((i + m) - (po - 1) / 2, (j + n) - (po - 1) / 2) == rgbcol)
                            redsit += 2;
                    }
                }

                if(Math.random() >= (double)(40 + redsit) * 0.0040000000000000001D)
                    continue;
                int id = i / 10;
                int jd = j / 10;
                po = 5;
                redsit = 0;
                for(int m = 0; m < po; m++)
                {
                    for(int n = 0; n < po; n++)
                        if(flowermap[(id + m) - (po - 1) / 2][(jd + n) - (po - 1) / 2] != 0)
                            redsit++;

                }

                if(redsit != 0)
                    continue;
                int s = 23 + ran.nextInt(14);
                int fp = ran.nextInt(100);
                int mf = 0;
                if(fp < 60)
                    mf = 1;
                else
                if(fp < 70)
                    mf = 2;
                switch(mf)
                {
                case 0: // '\0'
                    s = 23 + ran.nextInt(14);
                    break;

                case 1: // '\001'
                    s = 19 + ran.nextInt(11);
                    break;

                case 2: // '\002'
                    s = 22 + ran.nextInt(12);
                    break;
                }
                mg2.drawImage(ResourceManager.flower[mf], i, j, s, s, null);
                flowermap[id][jd] = 1;
            }
        }
    }
    
    private int getRGB(int i, int j){
        int x = (int) (i / mapImageScale.getXScaling());
        int y = (int) (j / mapImageScale.getYScaling());
        return mapImage.getRGB(x, y);
    }
    
    private void setRGB(int i, int j, int color){
        int x = (int) (i / mapImageScale.getXScaling());
        int y = (int) (j / mapImageScale.getYScaling());
        mapImage.setRGB(x, y, color);
    }
    
    
}
