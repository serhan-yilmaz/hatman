/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hatman.mapsolver;

import hatman.mapsolver.FibonacciHeap.Entry;

/**
 *
 * @author Serhan Yilmaz <github.com/serhan-yilmaz>
 */
public class Node {
    private final int x;
    private final int y;
    private Entry<Node> pointer;
    private Double cost;
    private Node parent = null;
    
    public Node(int x, int y){
        this.x = x;
        this.y = y;
    }

    /**
     * @return the x
     */
    public int getX() {
        return x;
    }

    /**
     * @return the y
     */
    public int getY() {
        return y;
    }

    /**
     * @return the pointer
     */
    protected Entry<Node> getPointer() {
        return pointer;
    }

    /**
     * @param pointer the pointer to set
     */
    protected void setPointer(Entry<Node> pointer) {
        this.pointer = pointer;
    }

    /**
     * @return the cost
     */
    public Double getCost() {
        return cost;
    }

    /**
     * @param cost the cost to set
     */
    protected void setCost(Double cost) {
        this.cost = cost;
    }

    /**
     * @return the parent
     */
    public Node getParent() {
        return parent;
    }

    /**
     * @param parent the parent to set
     */
    public void setParent(Node parent) {
        this.parent = parent;
    }
}
