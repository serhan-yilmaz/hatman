/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hatman.mapsolver;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import javax.imageio.ImageIO;
import hatman.mapsolver.FibonacciHeap.Entry;

/**
 *
 * @author Serhan Yilmaz <github.com/serhan-yilmaz>
 */
public class Map {
    public static final int QUANTIZATION_FACTOR = 5;
    private final int xSize;
    private final int ySize;
    private final int WIDTH;
    private final int HEIGHT;
    
    private FibonacciHeap<Node> fh;
    private final ArrayList<Node> moves = new ArrayList<>();
    private final boolean[][] obstacle;
    private final boolean[][] passable;
    private final boolean[][] closedSet;
    private final Node[][] fScore;
    private final Node[][] cameFrom;
    private final double[][] gScore;
    
    private long total = 0;
    private int num = 0;
    
    public Map(int width, int height){
        int x = quantize(width) + 1;
        int y = quantize(height) + 1;
        obstacle = new boolean[x][];
        passable = new boolean[x][];
        closedSet = new boolean[x][];
        gScore = new double[x][];
        fScore = new Node[x][];
        cameFrom = new Node[x][];
        for(int i = 0; i < x; i++){
            obstacle[i] = new boolean[y];
            passable[i] = new boolean[y];
            closedSet[i] = new boolean[y];
            gScore[i] = new double[y];
            fScore[i] = new Node[y];
            cameFrom[i] = new Node[y];
            for(int j = 0; j < y; j++){
                fScore[i][j] = new Node(i,j);
            }
        }
        xSize = x;
        ySize = y;
        WIDTH = width;
        HEIGHT = height;
//        ArrayList<Node> basicMoves = new ArrayList<>();
//        int r = 5;
//        for(int i = r; i > 1; i--){
//            basicMoves.add(new Node(i,1));
//            for(int j = 2; j < i; j++){
//                if(i % j != 0){
//                    basicMoves.add(new Node(i,j));
//                }
//            }
//        }
        /*
        basicMoves.add(new Node(5,1));
        basicMoves.add(new Node(5,2));
        basicMoves.add(new Node(5,3));
        basicMoves.add(new Node(5,4));
        basicMoves.add(new Node(4,1));
        basicMoves.add(new Node(4,3));
        basicMoves.add(new Node(3,1));
        basicMoves.add(new Node(3,2));
        basicMoves.add(new Node(2,1));
        */
//        for(Node n : basicMoves){
//            for(int i1 = 0; i1 < 2; i1++){
//                int a = n.getX();
//                int b = n.getY();
//                if(i1 == 1){
//                    b = n.getX();
//                    a = n.getY();
//                }
//                for(int i2 = 0; i2 < 2; i2++){
//                    if(i2 == 1){
//                        a *= -1;
//                    }
//                    for(int i3 = 0; i3 < 2; i3++){
//                        if(i3 == 1){
//                            b *= -1;
//                        }
//                        moves.add(new Node(a,b));
//                    }
//                }
//            }
//        }
        moves.add(new Node(1,0));
        moves.add(new Node(-1,0));
        moves.add(new Node(0,1));
        moves.add(new Node(0,-1));
        
        moves.add(new Node(1,1));
        moves.add(new Node(1,-1));
        moves.add(new Node(-1,1));
        moves.add(new Node(-1,-1));
        
        for(Node n : moves){
            n.setCost(getDistance(n.getX(),n.getY()));
        }
        
        try {
            render();
        } catch (IOException ex) {
            randomize();
        }
    }
    
    public boolean hasObstableAt(int x, int y){
        return this.obstacle[x][y];
    }
    
    private int quantize(int n){
        return n / QUANTIZATION_FACTOR;
    }
    
    private int dequantize(int n){
        return n * QUANTIZATION_FACTOR + QUANTIZATION_FACTOR / 2;
    }
    
    private void randomize(){
        for(int i = 0; i < obstacle.length; i++){
            for(int j = 0; j < obstacle[i].length; j++){
                if(Math.random() < 0.11){
                    obstacle[i][j] = true;
                } else {
                    obstacle[i][j] = false;
                }
            }
        }
    }
    
    private void render() throws IOException{
        BufferedImage img = ImageIO.read(Map.class.getResourceAsStream("/resources/images/obstacleMap.png"));
        double radius = 20.0 / QUANTIZATION_FACTOR;
        int r = (int) Math.ceil(radius);
        for(int i1 = 0; i1 < img.getWidth(); i1++){
            for(int j1 = 0; j1 < img.getHeight(); j1++){
                int xp = i1 * WIDTH / img.getWidth();
                int yp = j1 * HEIGHT / img.getHeight();
                int x = quantize(xp);
                int y = quantize(yp);
                if(!obstacle[x][y]){
                    if(img.getRGB(i1, j1) != Color.white.getRGB()){
                        obstacle[x][y] = true;
                        for(int i = -1 * r; i <= r; i++){
                           for(int j = -1 * r; j <= r; j++){ 
                               if(getDistance(i,j) <= radius){
                                   int x1 = i + x;
                                   int y1 = j + y;
                                   if(x1 >= 0 && y1 >= 0 && x1 < xSize && y1 < ySize){
                                       passable[x1][y1] = true;
                                   }
                               }
                           }
                        }
                    }
                }
            }
        }
    }
    
    public boolean isOccupied(int x, int y){
        return obstacle[quantize(x)][quantize(y)];
    }
    
    public boolean isPassable(int x, int y){
        return !passable[quantize(x)][quantize(y)];
    }
    
    private boolean isTargetReached(int x, int y, int endX, int endY){
        return x == endX && y == endY;
        //return getDistanceTo(x,y,endX,endY) <= quantize(10);
    }
    
    public Path solveLee(int startX, int startY, int endX, int endY){
        int stx = quantize(startX);
        int sty = quantize(startY);
        int endx = quantize(endX);
        int endy = quantize(endY);
        if(obstacle[stx][sty] || obstacle[endx][endy])
            return null;
        
        int iteration = 0;
        long startTime = System.nanoTime();
        prepare();
        LinkedList<Node> list = new LinkedList<>();
        LinkedList<Node> list2 = new LinkedList<>();
        list.add(new Node(stx,sty));
        while(!list.isEmpty()){
            for(Node current : list){
                if(isTargetReached(current.getX(),current.getY(),endx,endy)){
                    System.out.println(iteration);
                    reportTime(startTime);
                    return constructPath(current, startX, startY, endX, endY);
                }
                closedSet[current.getX()][current.getY()] = true;
                for(Node n : moves){
                    iteration++;
                    int nx = n.getX() + current.getX();
                    int ny = n.getY() + current.getY();
                    if(nx >= 0 && nx < xSize && ny >= 0 && ny < ySize){
                        if(closedSet[nx][ny]){
                            continue;
                        }
                        if(passable[nx][ny]){
                            continue;
                        }
                        if(!checkInnerObstacles(current,n)){
                            continue;
                        }
                        closedSet[nx][ny] = true;
                        list2.add(new Node(nx,ny));
                        cameFrom[nx][ny] = current;
                    }
                }
            }
            list.clear();
            list.addAll(list2);
            list2.clear();
        }
        return null;
    }
    
    public Path solve(int startX, int startY, int endX, int endY){
        return solveAStar(startX,startY,endX,endY);
    }
    
    private Path solveAStar(int startX, int startY, int endX, int endY){
        int stx = quantize(startX);
        int sty = quantize(startY);
        int endx = quantize(endX);
        int endy = quantize(endY);
        if(obstacle[stx][sty] || obstacle[endx][endy])
            return null;
        
        long startTime = System.nanoTime();
        prepare();
        addOpenList(stx,sty,getHeuristicScore(stx,sty,endx,endy));
        gScore[stx][sty] = 0;
        
        long iteration = 0;
        while(!fh.isEmpty()){
            Node current = fh.dequeueMin().getValue();
            if(isTargetReached(current.getX(),current.getY(),endx,endy)){
                
                //System.out.println("Iteration : " + iteration);
                Path path = constructPath(current, startX, startY, endX, endY);
                reportTime(startTime);
                return path;
            }
            closedSet[current.getX()][current.getY()] = true;
            for(Node n : moves){
                iteration++;
                int nx = n.getX() + current.getX();
                int ny = n.getY() + current.getY();
                if(nx >= 0 && nx < xSize && ny >= 0 && ny < ySize){
                    if(closedSet[nx][ny]){
                        continue;
                    }
                    if(passable[nx][ny]){
                        continue;
                    }
                    if(!checkInnerObstacles(current,n)){
                        continue;
                    }
                    double tentativeGScore = gScore[current.getX()][current.getY()] + n.getCost();
                    if(fScore[nx][ny].getPointer() == null){
                        addOpenList(nx,ny,tentativeGScore + getHeuristicScore(nx,ny,endx,endy));
                    } else {
                        if(tentativeGScore > gScore[nx][ny]){
                            continue;
                        }
                        fh.decreaseKey(fScore[nx][ny].getPointer(), tentativeGScore + getHeuristicScore(nx,ny,endx,endy));
                    }
                    gScore[nx][ny] = tentativeGScore;
                    cameFrom[nx][ny] = current;
                }
            }
        }
        reportTime(startTime);
        return null;
    }
    
    private boolean isInLineOfSight(Node current, Node previous){
        int x0 = quantize(previous.getX());
        int y0 = quantize(previous.getY());
        int x1 = quantize(current.getX());
        int y1= quantize(current.getY());
        int dx = x1 - x0;
        int dy = y1 - y0;
        int sx = 1;
        int sy = 1;
        int f = 0;
        if(dx < 0){
            dx *= -1;
            sx = -1;
        }
        if(dy < 0){
            dy *= -1;
            sy = -1;
        }
        int sxp = (sx - 1) / 2;
        int syp = (sy - 1) / 2;
        if(dx >= dy){
            while(x0 != x1){
                f = f + dy;
                if(f >= dx){
                    if(passable[x0 + sxp][y0 + syp]){
                        return false;
                    }
                    y0 += sy;
                    f = f - dx;
                }
                if(f != 0 && passable[x0 + sxp][y0 + syp]){
                    return false;
                }
                if(dy == 0 && passable[x0 + sxp][y0] && passable[x0 + sxp][y0 - 1]){
                    return false;
                }
                x0 += sx;
            }
        } else {
            while(y0 != y1){
                f = f + dx;
                if(f >= dy){
                    if(passable[x0 + sxp][y0 + syp]){
                        return false;
                    }
                    x0 += sx;
                    f = f - dy;
                }
                if(f != 0 && passable[x0 + sxp][y0 + syp]){
                    return false;
                }
                if(dx == 0 && passable[x0][y0 + syp] && passable[x0 - 1][y0 + syp]){
                    return false;
                }
                y0 += sy;
            }
        }
        return true;
    }
    
    
    private boolean checkInnerObstacles(Node current, Node move){
        boolean b = false;
        double t = Math.floor(1 + QUANTIZATION_FACTOR * move.getCost() / 28);
        for(int i = 1; i < t; i++){
            int dx = (int) Math.round(i * move.getX() / t);
            int dy = (int) Math.round(i * move.getY() / t);
            int nx2 = dx + current.getX();
            int ny2 = dy + current.getY();
            if(passable[nx2][ny2]){
                b = true;
                break;
            }
        }
        return !b;
    }
    
    private void reportTime(long startTime){
        num++;
        long dif = System.nanoTime() - startTime;
        total += dif;
//        System.out.println("Time passed : " + (dif / 1000) + " us");
        //System.out.println("Average : " + getAverageRunTime() + " us" + ", in " + num + " trials");
    }
    
    private void addOpenList(int x, int y, double priority){
        Entry<Node> st = fh.enqueue(fScore[x][y], priority);
        fScore[x][y].setPointer(st);
    }
    private double getDistance(int x, int y){
        return Math.sqrt(x * x + y * y);
    }
    private double getDistanceTo(int x, int y, int endX, int endY){
        double dx = x - endX;
        double dy = y - endY;
        return Math.sqrt(dx * dx + dy * dy);
    }
    private double getHeuristicScore(int x, int y, int endX, int endY){
        return getDistanceTo(x,y,endX,endY);
    }
    private void prepare(){
        fh = new FibonacciHeap<>();
        for(int i = 0; i < xSize; i++){
            for(int j = 0; j < ySize; j++){
                closedSet[i][j] = false;
                fScore[i][j].setPointer(null);
                gScore[i][j] = -1;
                cameFrom[i][j] = null;
            }
        }
    }
    
    private Path constructPath(Node current, int startX, int startY, int endX, int endY){
        ArrayList<Node> temp = new ArrayList<>();
        Path path = new Path(endX, endY);
        Node next = current;
        Node previous = null;
        while(next != null){
            Node n = new Node(dequantize(next.getX()) , dequantize(next.getY()));
            temp.add(n);
            next = cameFrom[next.getX()][next.getY()];
        }
        previous = new Node(startX, startY);
        previous.setCost(0d);
//        path.append(previous);
        for(int i = temp.size() - 2; i >= 1; i--){
            Node n = temp.get(i);
            double cost;
//            cost = getDistanceTo(n.getX(), n.getY(), previous.getX(), previous.getY());
//            n.setCost(cost);
            Node parent = previous.getParent();
            if(parent != null && isInLineOfSight(parent, n)){
                cost = getDistanceTo(n.getX(), n.getY(), parent.getX(), parent.getY());
                n.setCost(cost);
                n.setParent(parent);
                previous = n;
            } else {
                cost = getDistanceTo(n.getX(), n.getY(), previous.getX(), previous.getY());
                n.setCost(cost);
                n.setParent(previous);
                path.append(previous);
                previous = n;
            }
//            path.append(n);
        }
        Node parent = previous.getParent();
        Node end = new Node(endX, endY);
        if(parent != null && isInLineOfSight(parent, end)){
            double cost = getDistanceTo(end.getX(), end.getY(), parent.getX(), parent.getY());
            end.setCost(cost);
            end.setParent(parent);
            path.append(end);
        } else {
            double cost = getDistanceTo(end.getX(), end.getY(), previous.getX(), previous.getY());
            end.setCost(cost);
            end.setParent(previous);
            path.append(previous);
            path.append(end);
        }
        
//        end.setCost(getDistanceTo(end.getX(), end.getY(), previous.getX(), previous.getY()));
//        path.append(end);
        return path;
    }
    
    private long getAverageRunTime(){
        /*if(num == 0)
            return 0;*/
        return total / (1000 * num);
    }
}
