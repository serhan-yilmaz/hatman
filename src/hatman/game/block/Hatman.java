/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hatman.game.block;

import sygfx.ScaledGraphics;
import java.awt.Color;
import hatman.mapsolver.Node;
import hatman.mapsolver.Path;
import sygfx.util.Anchor;

/**
 *
 * @author Serhan Yilmaz <github.com/serhan-yilmaz>
 */
public class Hatman extends ConcreteBlock{

    public Hatman(double x, double y, double speed, double radius) {
        super(x, y, speed, radius);
    }

    @Override
    public void draw(ScaledGraphics sg) {
        int x = this.getX();
        int y = this.getY();
        int radius = (int) this.radius;
        Path path = this.path;
        sg.setColor(Color.red);
        if(path != null){
            Node previous = new Node(x, y);
            for(Node n : path.getPath()){
                if(previous != null){
                    sg.fillOval(n.getX(), n.getY(), 6, 6);
                    sg.drawLine(previous.getX(), previous.getY(), n.getX(), n.getY());
                }
                previous = n;
            }
        }
        sg.setColor(Color.orange);
        sg.setAnchor(Anchor.CENTER);
        sg.fillOval(x, y, 2 * radius, 2 * radius);
    }
    
    public int getEstimatedPathCost(){
        if(path == null){
            return 0;
        }
        if(path.isEmpty()){
            return 0;
        }
        Node n = path.peek();
        double cost = path.getTotalCost() - n.getCost() + getDistanceTo(n.getX(), n.getY());
        return (int) Math.round(cost);
    }
    
}
