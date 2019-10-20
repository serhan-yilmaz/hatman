/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hatman.ui;

import hatman.game.GameEnvironment;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import sygfx.Layer;
import sygfx.Scale;
import sygfx.ScaledGraphics;
import sygfx.ui.Container;

/**
 *
 * @author Serhan Yilmaz <github.com/serhan-yilmaz>
 */
public class GameCanvas extends Container {
    private GameEnvironment game;
    private Layer layer;
    
    public GameCanvas(int width, int height){
        super(width, height);
        layer = new Layer(Scale.UNITY, width, height, BufferedImage.TYPE_INT_RGB);
//        System.out.println(new Dimension(width, height));
    }
    
    @Override
    public void paintComponent(ScaledGraphics sg){
        super.paintComponent(sg);
        ScaledGraphics sg1 = layer.createScaledGraphics(Scale.UNITY);
        ScaledGraphics sg2 = new ScaledGraphics(sg1, game.getScale());
        game.countfps();
//        sg1.setClip(0, 0, this.getWidth(), this.getHeight());
//        sg = new ScaledGraphics(sg, game.getScale());
        game.drawGameGraphics(sg2);
//        sg1.setColor(Color.white);
//        sg2.fillRect(0, 0, getWidth(), getHeight());
        layer.commit(sg);
    }
    
    public void setGameEnvironment(GameEnvironment game){
        this.game = game;
    }
    
}
