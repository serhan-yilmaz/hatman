/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hatman;

import java.awt.BasicStroke;
import java.awt.Color;
import sygfx.Scale;
import sygfx.ScaledGraphics;
import sygfx.ui.Container;
import sygfx.util.Anchor;

/**
 *
 * @author Serhan
 */
public class MinimapCanvas extends Container{
    private GameEnvironment game;
    private Scale minimapScale;
    
    public MinimapCanvas(int width, int height){
        super(width, height);
        minimapScale = new Scale(width * 0.8, height * 0.8, 0.2, 0.2);
    }
    
    @Override
    public void paintComponent(ScaledGraphics sg){
        super.paintComponent(sg);
        ScaledGraphics sg1 = new ScaledGraphics(sg, minimapScale);
        sg1.setAnchor(Anchor.NORTHWEST);
        sg1.clipRect(position.x, position.y, size.width, size.height);
        ScaledGraphics sg2 = new ScaledGraphics(sg, minimapScale.scale(0.5, 0.5));
        game.drawGameGraphics(sg2);
        ScaledGraphics sg3 = new ScaledGraphics(sg2, game.getScale().inverse());
        sg3.setStroke(new BasicStroke(2));
        sg3.setColor(Color.red);
        sg3.setAnchor(Anchor.NORTHWEST);
        sg3.drawRect(0, 0, getWidth(), getHeight());
        sg3.setStroke(new BasicStroke(1));
        sg1.setColor(Color.black);
        sg1.setAnchor(Anchor.NORTHWEST);
        sg1.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
    }
    
    public void setGameEnvironment(GameEnvironment game){
        this.game = game;
    }
}
