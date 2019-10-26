/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hatman.ui;

import hatman.game.GameEnvironment;
import java.awt.Dimension;
import java.awt.Point;
import sygfx.Scale;

/**
 *
 * @author Serhan Yilmaz <github.com/serhan-yilmaz>
 */
public class PanningController {
    private final Dimension resolution;
    private GameEnvironment game;
    private int mouseX = -1;
    private int mouseY = -1;
    private double panningSpeed = 1.3;
    private double panningRatio = 0.05;
    private boolean panningShift = true;
    private boolean cameraLock = true;
    
    public PanningController(Dimension resolution, GameEnvironment game){
        this.resolution = resolution;
        this.game = game;
    }
    
    public void setPanningShift(boolean b){
        panningShift = b;
    }
    
    public void setPanningSpeed(double speed){
        this.panningSpeed = speed;
    }
    
    public void setPanningRatio(double ratio){
        this.panningRatio = ratio;
    }
    
    public void cycle(){
        if(cameraLock){
            setPanning(game.getHatmanPosition());
            return;
        }
        if(!panningShift){
            return;
        }
        double r = panningRatio;
        double ri = 1 - r;
        if(mouseX < resolution.width * r){
            shiftCanvasScale(resolution.width / 100 * panningSpeed, 0);
        }
        if(mouseX > resolution.width * ri){
            shiftCanvasScale(-resolution.width / 100 * panningSpeed, 0);
        }
        if(mouseY < resolution.height * r){
            shiftCanvasScale(0, resolution.height / 100 * panningSpeed);
        }
        if(mouseY > resolution.height * ri){
            shiftCanvasScale(0, -resolution.height / 100 * panningSpeed);
        }
    }
    
    public void toggleCameraLock(boolean b){
        this.cameraLock = !this.cameraLock;
    }
    
    public void setCameraLock(boolean b){
        this.cameraLock = b;
    }
    
    public void setPanning(Point p){
        int x = p.x - resolution.width/2;
        int y = p.y - resolution.height/2;
        this.setPanning(x, y, new Dimension(1, 1));
    }
    
    public void setPanning(double x, double y){
        this.setPanning(x, y, resolution);
    }
    
    public void setPanning(double x, double y, Dimension resolution){
        Scale scale = game.getScale();
        scale = scale.setXShift(x, resolution.width);
        scale = scale.setYShift(y, resolution.height);
        game.setScale(scale);
        correctCanvasShift();
    }
    
    public void adjustMapScale(double x, double y, int a){
        double ratio = 0.15;
        double cx = x + (resolution.width / 2.0 - x) * ratio;
        double cy = y + (resolution.width / 2.0 - y) * ratio;
        double b = Math.pow(1 - ratio, a);
        Scale s = game.getScale();
        double xp = s.icX(x);
        double yp = s.icY(y);
        Scale s2 = Scale.create(xp, yp, cx, cy, 
                s.getXScaling() * b, s.getYScaling() * b);
        game.setScale(s2);
        correctCanvasScale();
        correctCanvasShift();
    }
    
    public void setMousePosition(int x, int y){
        this.mouseX = x;
        this.mouseY = y;
    }
    
    private void shiftCanvasScale(double xShift, double yShift){
        Scale scale = game.getScale();
        scale = scale.shift(xShift / scale.getXScaling(), 
                yShift / scale.getYScaling());
        game.setScale(scale);
        correctCanvasShift();
    }
    
    private void correctCanvasScale(){
        Scale scale = game.getScale();
        if(scale.getXScaling() < 0.5){
            scale = scale.setXScale(0.5);
        }
        if(scale.getYScaling() < 0.5){
            scale = scale.setYScale(0.5);
        }
        game.setScale(scale);
    }
    
    private void correctCanvasShift(){
        Scale scale = game.getScale();
        int width = game.getMapWidth();
        int height = game.getMapHeight();
        double c = scale.icX(0);
        if(c < 0){
            scale = scale.setXShift(0,0);
        }
        c = scale.icX(resolution.width);
        if(c > width){
            scale = scale.setXShift(width, resolution.width);
        }
        c = scale.icY(0);
        if(c < 0){
            scale = scale.setYShift(0,0);
        }
        c = scale.icY(resolution.height);
        if(c > height){
            scale = scale.setYShift(height, resolution.height);
        }
        game.setScale(scale);
    }
    
}
