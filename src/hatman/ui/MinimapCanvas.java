/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hatman.ui;

import hatman.game.GameEnvironment;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Rectangle;
import sygfx.Scale;
import sygfx.ScaledGraphics;
import sygfx.ui.Container;
import sygfx.util.Anchor;

/**
 *
 * @author Serhan Yilmaz <github.com/serhan-yilmaz>
 */
public class MinimapCanvas extends Container{
    private GameEnvironment game;
    private Scale positionScale;
    private Scale minimapScale;
    private int windowWidth;
    private int windowHeight;
    
    public MinimapCanvas(int width, int height, int scaling){
        super(width/scaling, height/scaling);
        windowWidth = width;
        windowHeight = height;
        this.position.x = (scaling - 1) * width / scaling;
        this.position.y = (scaling - 1) * height / scaling;
        positionScale = new Scale(this.position.x, this.position.y, 1, 1);
        minimapScale = new Scale(0, 0, 1d /scaling, 1d/scaling);
    }
    
    @Override
    public void paintComponent(ScaledGraphics sg){
        super.paintComponent(sg);
//        sg.setClip((Rectangle) null);
        sg = new ScaledGraphics(sg, positionScale);
        ScaledGraphics sg1 = new ScaledGraphics(sg, minimapScale);
        ScaledGraphics sg2 = new ScaledGraphics(sg, minimapScale.scale(0.5, 0.5));
        game.drawGameGraphics(sg2);
        ScaledGraphics sg3 = new ScaledGraphics(sg2, game.getScale().inverse());
        sg3.setStroke(new BasicStroke(2));
        sg3.setColor(Color.red);
        sg3.setAnchor(Anchor.NORTHWEST);
        sg3.drawRect(0, 0, windowWidth, windowHeight);
        sg3.setStroke(new BasicStroke(1));
        sg1.setColor(Color.black);
        sg1.setAnchor(Anchor.NORTHWEST);
        sg1.drawRect(0, 0, windowWidth - 1, windowHeight - 1);
    }
    
    public Scale getPositionScaling(){
        return positionScale;
    }
    
    public Scale getMinimapScaling(){
        return minimapScale.scale(0.5, 0.5);
    }
    
    public void setGameEnvironment(GameEnvironment game){
        this.game = game;
    }
}
