/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hatman.ui;

import hatman.game.GameEnvironment;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import sygfx.Scale;
import sygfx.ui.JCanvas;
import sygfx.ui.Window;
import sygfx.ui.layout.FlowLayout;

/**
 *
 * @author Serhan Yilmaz <github.com/serhan-yilmaz>
 */
public class CanvasDesigner {
    
    public static void design(JCanvas canvas, 
            GameEnvironment game, GameController controller){
        PanningController panning = controller.getPanningController();
        canvas.addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                Scale s = canvas.getScale();
                double x = s.icX(e.getX());
                double y = s.icY(e.getY());
                panning.adjustMapScale(x, y, e.getWheelRotation());
            }
        });
        canvas.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                char s = e.getKeyChar();
                if(s == 'k'){
                    game.getPlayer().damage(450);
                }
                if(s == 'r'){
                    game.reset();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
        Window window = canvas.getWindow();
        window.setLayout(new FlowLayout());
        
        GameCanvas gameCanvas = 
                new GameCanvas(window.getWidth(), window.getHeight());
        MinimapCanvas minimapCanvas = 
                new MinimapCanvas(window.getWidth(), window.getHeight());
        UICanvas uicanvas = 
                new UICanvas(window.getWidth(), window.getHeight());
        
        gameCanvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e){
                Scale scale = game.getScale().inverse();
                int x = scale.cX(e.getX());
                int y = scale.cY(e.getY());
                game.moveHatman(x, y);  
            }
        });
        gameCanvas.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                panning.setMousePosition(e.getX(), e.getY());
            }
        });

        gameCanvas.setGameEnvironment(game);
        minimapCanvas.setGameEnvironment(game);
        uicanvas.setGameEnvironment(game);
        
        window.add(gameCanvas);
        window.add(minimapCanvas);
        window.add(uicanvas);
    }
    
}
