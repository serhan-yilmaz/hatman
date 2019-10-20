/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hatman.ui;

import hatman.game.GameEnvironment;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import javax.swing.JFrame;
import sygfx.ui.JCanvas;

/**
 *
 * @author Serhan Yilmaz <github.com/serhan-yilmaz>
 */
public class GUI {
    private static GraphicsDevice device = GraphicsEnvironment
            .getLocalGraphicsEnvironment().getDefaultScreenDevice();
    private static final int RESOLUTION_WIDTH = 1280;
    private static final int RESOLUTION_HEIGHT = 720;
    private static final int GAME_WIDTH = RESOLUTION_WIDTH * 2;
    private static final int GAME_HEIGHT = RESOLUTION_HEIGHT * 2;
    
    private JFrame f = new JFrame("Hatman");
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
        
//        if (device.isFullScreenSupported()) {
//          device.setFullScreenWindow(this.f);
//        }
        
        f.pack();
//        f.setPreferredSize(new Dimension(RESOLUTION_WIDTH, RESOLUTION_HEIGHT));
//        f
        f.setExtendedState(JFrame.MAXIMIZED_BOTH);
        f.setLocationRelativeTo(null);
        repaintThread.setRepaintPeriod(20);
        repaintThread.start();
    }
    
    public void setVisible(){
        f.setVisible(true);
        jcanvas.setFocusable(true);
        jcanvas.requestFocus();
    }
    
    public void cycle(){
//        System.out.println(f.getPreferredSize());
        controller.cycle();
        game.cycle();
    }
}
