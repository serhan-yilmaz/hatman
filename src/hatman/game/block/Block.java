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
    
    protected double speed;
    protected double x;
    protected double y;
    
    public Block(double x, double y, double speed){
        this.x = x;
        this.y = y;
        this.speed = speed;
        if(speed < 0){
            throw new IllegalArgumentException("Unsupported Parameter <Speed> : " + speed);
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
    protected void setX(double x) {
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
    protected void setY(double y) {
        this.y = y;
    }
    
    public Point getPosition(){
        return new Point(getX(), getY());
    }
    
    public abstract void draw(ScaledGraphics sg);
    
    public abstract void cycle();
    
    protected double getDistanceTo(int x, int y){
        double dx = this.x - x;
        double dy = this.y - y;
        return Math.sqrt(dx * dx + dy * dy);
    }
    
}
