/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hatman.mapsolver;

import java.util.Comparator;

/**
 *
 * @author Serhan Yilmaz <github.com/serhan-yilmaz>
 */
public class NodeComparator implements Comparator<Node> {

    @Override
    public int compare(Node o1, Node o2) {
        int c1 = Integer.compare(o1.getX(), o2.getX());
        if(c1 != 0){
            return c1;
        }
        return Integer.compare(o1.getY(), o2.getY());
    }
    
}
