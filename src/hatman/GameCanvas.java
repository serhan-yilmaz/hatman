/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hatman;

import sygfx.ScaledGraphics;
import sygfx.ui.Container;

/**
 *
 * @author Serhan
 */
public class GameCanvas extends Container {
    private GameEnvironment game;
    
    public GameCanvas(int width, int height){
        super(width, height);
    }
    
    @Override
    public void paintComponent(ScaledGraphics sg){
        super.paintComponent(sg);
        sg = new ScaledGraphics(sg, game.getScale());
        game.drawGameGraphics(sg);
    }
    
    public void setGameEnvironment(GameEnvironment game){
        this.game = game;
    }
    
}
