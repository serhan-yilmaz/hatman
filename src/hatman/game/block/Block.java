/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hatman.game.block;

import java.awt.Point;
import sygfx.ScaledGraphics;

/**
 *
 * @author Serhan Yilmaz <github.com/serhan-yilmaz>
 */
public abstract class Block {
    protected double radius;
    protected double speed;
    protected double x;
    protected double y;
    
    public Block(double x, double y, double speed, double radius){
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.radius = radius;
        if(speed < 0){
            throw new IllegalArgumentException("Unsupported Parameter <Speed> : " + speed);
        }
        if(radius < 0){	
            throw new IllegalArgumentException("Unsupported Parameter <Radius> : " + radius);	
        }
    }
    
    protected double moveThrough(double x, double y, double speed){
        double remain = speed;
        if(this.x == x && this.y == y){
            return remain;
        }
        double dx = x - this.x;
        double dy = y - this.y;
        double d = Math.sqrt(dx * dx + dy * dy);
        if(remain >= d){
            this.x = x;
            this.y = y;
            return remain - d;
        }
        this.x += dx * remain / d;
        this.y += dy * remain / d;
        return 0;
    }

    /**
     * @return the x
     */
    public int getX() {
        return (int) Math.round(x);
    }

    /**
     * @param x the x to set
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * @return the y
     */
    public int getY() {
        return (int) Math.round(y);
    }

    /**
     * @param y the y to set
     */
    public void setY(double y) {
        this.y = y;
    }
    
    public Point getPosition(){
        return new Point(getX(), getY());
    }
    
    public double getRadius(){	
        return radius;	
    }	

    public double getSpeed(){	
        return speed;	
    }	

    
    public abstract void draw(ScaledGraphics sg);
    
    public abstract boolean cycle();
    
    protected double getDistanceTo(Block target){
        return getDistanceTo(target.x, target.y);
    }
    
    protected double getDistanceTo(int x, int y){
        return getDistanceTo((double) x, (double) y);	
    }	

    protected double getDistanceTo(double x, double y){
        double dx = this.x - x;
        double dy = this.y - y;
        return Math.sqrt(dx * dx + dy * dy);
    }
    
    public boolean isTargetReached(Block target){
        return isTargetReached(target, radius);
    }
    
    public boolean isTargetReached(Block target, double radius){
        return getDistanceTo(target.x, target.y) < (radius);
    }
 
    @Override
    public abstract Block clone();
    
}
