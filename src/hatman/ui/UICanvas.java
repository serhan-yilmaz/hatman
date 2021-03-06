/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hatman.ui;

import hatman.game.GameEnvironment;
import java.awt.Color;
import java.awt.Font;
import sygfx.ScaledGraphics;
import sygfx.ui.Container;

/**
 *
 * @author Serhan Yilmaz <github.com/serhan-yilmaz>
 */
public class UICanvas extends Container{
    private GameEnvironment game;
    
    public UICanvas(int width, int height){
        super(width, height);
    }
    
    @Override
    public void paintComponent(ScaledGraphics g){
        super.paintComponent(g);
        game.drawUIGraphics(g);
    }
    
    @Override
    public boolean isClickable(){
        return false;
    }
    
    public void setGameEnvironment(GameEnvironment game){
        this.game = game;
    }
    
    public GameEnvironment getGameEnvironment(){
        return game;
    }
    
}
