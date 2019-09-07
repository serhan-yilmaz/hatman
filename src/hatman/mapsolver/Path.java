/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hatman.mapsolver;

import java.awt.Point;
import java.util.LinkedList;

/**
 *
 * @author Serhan Yilmaz <github.com/serhan-yilmaz>
 */
public class Path {
    
    private Point endPoint;
    private LinkedList<Node> path = new LinkedList<>();
    private double totalCost = 0;
    
    public Path(int endX, int endY){
        endPoint = new Point(endX, endY);
    }
    
    protected void append(Node n){
        getPath().add(n);
        totalCost += n.getCost();
    }

    /**
     * @return the path
     */
    public LinkedList<Node> getPath() {
        return path;
    }

    /**
     * @return the totalCost
     */
    public double getTotalCost() {
        return totalCost;
    }
    
    public Node peek(){
        return path.peekFirst();
    }
    
    public Node poll(){
        Node n = path.pollFirst();
        if(n != null){
            totalCost -= n.getCost();
        }
        return n;
    }
    
    public boolean isEmpty(){
        return path.isEmpty();
    }

    /**
     * @return the endPoint
     */
    public Point getEndPoint() {
        return endPoint;
    }
    
}
