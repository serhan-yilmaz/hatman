/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hatman;

import javax.swing.JFrame;
import sygfx.ui.JCanvas;

/**
 *
 * @author Serhan
 */
public class GUI {
//    private static GraphicsDevice device = GraphicsEnvironment
//            .getLocalGraphicsEnvironment().getDefaultScreenDevice();
    private static final int RESOLUTION_WIDTH = 1280;
    private static final int RESOLUTION_HEIGHT = 720;
    private static final int GAME_WIDTH = RESOLUTION_WIDTH * 2;
    private static final int GAME_HEIGHT = RESOLUTION_HEIGHT * 2;
    
    private JFrame f = new JFrame("MapSolver");
    private JCanvas jcanvas = new JCanvas(RESOLUTION_WIDTH, RESOLUTION_HEIGHT);
    private GameEnvironment game = new GameEnvironment(GAME_WIDTH, GAME_HEIGHT);
    private GameController controller = 
            new GameController(RESOLUTION_WIDTH, RESOLUTION_HEIGHT, game);
    
    private PaintThread repaintThread = new PaintThread(jcanvas);
    
    public GUI(){
        initialize();
    }
    
    private void initialize(){
        
        f.setUndecorated(true);
        f.setExtendedState(JFrame.MAXIMIZED_BOTH);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(jcanvas);
        CanvasDesigner.design(jcanvas, game, controller);
        f.pack();
        f.setLocationRelativeTo(null);
        repaintThread.setRepaintPeriod(20);
        repaintThread.start();
    }
    
    public void setVisible(){
        f.setVisible(true);
    }
    
    public void cycle(){
        controller.cycle();
        game.cycle();
    }
}
