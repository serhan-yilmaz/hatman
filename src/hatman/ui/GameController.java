/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hatman.ui;

import hatman.game.GameEnvironment;
import java.awt.Dimension;

/**
 *
 * @author Serhan Yilmaz <github.com/serhan-yilmaz>
 */
public class GameController {
    private final Dimension resolution;
    private GameEnvironment game;
    private PanningController panning;
    
    public GameController(int width, int height, GameEnvironment game){
        this.resolution = new Dimension(width, height);
        this.game = game;
        this.panning = new PanningController(resolution, game);
    }
    
    public PanningController getPanningController(){
        return panning;
    }
    
    public void cycle(){
        panning.cycle();
    }
    
    
}
