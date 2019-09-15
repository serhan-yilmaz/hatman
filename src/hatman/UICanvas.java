/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hatman;

import java.awt.Color;
import java.awt.Font;
import sygfx.ScaledGraphics;
import sygfx.ui.Container;

/**
 *
 * @author Serhan
 */
public class UICanvas extends Container{
    private GameEnvironment game;
    
    public UICanvas(int width, int height){
        super(width, height);
    }
    
    @Override
    public void paintComponent(ScaledGraphics g){
        super.paintComponent(g);
        g.setColor(Color.red);
        g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 24));
        g.drawString("Cost : " + game.getHatman().getEstimatedPathCost(), 100, 40);
        g.drawString("Fps : " + game.getFPS(), 1100, 40);
    }
    
    @Override
    public boolean isClickable(){
        return false;
    }
    
    public void setGameEnvironment(GameEnvironment game){
        this.game = game;
    }
    
}
