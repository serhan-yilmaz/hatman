/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hatman.sound;
import java.io.File;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.util.Random;
import javazoom.jl.player.Player;
/**
 *
 * @author ahmet
 */
public class Sound implements Runnable{
    
    private String _filename;
    public Sound(String filename)
    {
        this._filename = filename;
    }
    
    public void run() {
      try {
            FileInputStream mpThree     = new FileInputStream(_filename);
            
            BufferedInputStream bis = new BufferedInputStream(mpThree);
            Player player = new Player(bis);
            player.play();
            player.close();
           }
        catch (Exception e) {
//            System.out.println("Problem playing file " + filename);
            System.out.println(e);
        }
    }
    
       public static void main(String[] args) {
        //(new Thread(new Sound("music.mp3"))).start();
       // (new Thread(new Sound("src\\resources\\sound\\tetris.mp3"))).start();
        
            (new Thread(new Sound("src\\resources\\sound\\tetris.mp3"))).start();
            
        Random rand = new Random();
        int n = -1;
        for(int i = 0; i<100; i++)
        {
            
            n = 1000 + rand.nextInt(3000);
            try
            {
                Thread.sleep(n);
                
            }
            catch(Exception e)
            {
                 System.out.println(e);   
            }
            (new Thread(new Sound("src\\resources\\sound\\Alert-02.mp3"))).start();
        }
     
       }
       
}
