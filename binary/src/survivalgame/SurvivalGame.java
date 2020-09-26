/*     */ package survivalgame;
/*     */ 
/*     */ import java.awt.Desktop;
/*     */ import java.awt.Desktop.Action;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URI;
/*     */ import java.net.URISyntaxException;
/*     */ import java.net.URL;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
/*     */ import javax.imageio.ImageIO;
/*     */ import javax.swing.JOptionPane;
/*     */ import javax.swing.SwingUtilities;
/*     */ import javazoom.jlgui.basicplayer.BasicPlayer;
/*     */ import javazoom.jlgui.basicplayer.BasicPlayerException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SurvivalGame
/*     */ {
/*     */   public static void openWebpage(URI uri)
/*     */   {
/*  40 */     Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
/*  41 */     if ((desktop != null) && (desktop.isSupported(Desktop.Action.BROWSE))) {
/*     */       try {
/*  43 */         desktop.browse(uri);
/*     */       } catch (Exception e) {
/*  45 */         e.printStackTrace();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public static void openWebpage(URL url) {
/*     */     try {
/*  52 */       openWebpage(url.toURI());
/*     */     } catch (URISyntaxException e) {
/*  54 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void main(String[] args) throws InterruptedException
/*     */   {
/*  63 */     System.out.println("Current Java Version : ");System.out.println(System.getProperty("java.version"));
/*  64 */     if ((!System.getProperty("java.version").startsWith("1.7")) && (!System.getProperty("java.version").startsWith("1.8")) && (!System.getProperty("java.version").startsWith("1.9")))
/*     */     {
/*     */ 
/*  67 */       Object[] options = { "Yes", "Quit", "Start anyway" };
/*     */       
/*     */ 
/*     */ 
/*  71 */       int n = JOptionPane.showOptionDialog(null, "Your java seems to be outdated.\nDo you want to update it?\nStarting with an outdated version may cause anomalities.", "Current Java version : " + 
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*  76 */         System.getProperty("java.version") + "  Required > 1.7.0_01", 1, 2, null, options, options[0]);
/*     */       
/*  78 */       if (n == 0) {
/*     */         try {
/*  80 */           openWebpage(new URL("http://www.oracle.com/technetwork/java/javase/downloads/jre7-downloads-1880261.html"));
/*     */         }
/*     */         catch (MalformedURLException localMalformedURLException) {}
/*     */         
/*     */ 
/*  85 */         return; }
/*  86 */       if (n != 2) return;
/*     */     }
/*     */     try {
/*  89 */       survivalgame.blocks.Block.witch_img = ImageIO.read(SurvivalGame.class.getResourceAsStream("/images/witchhat.png"));
/*  90 */       survivalgame.blocks.Block.sparkle = ImageIO.read(SurvivalGame.class.getResourceAsStream("/images/sparkle2.png"));
/*  91 */       survivalgame.blocks.Block.explosion = ImageIO.read(SurvivalGame.class.getResourceAsStream("/images/explosion.png"));
/*  92 */       survivalgame.blocks.Block.mineicon = ImageIO.read(SurvivalGame.class.getResourceAsStream("/images/mine_icon.png"));
/*  93 */       survivalgame.blocks.Block.mineicon2 = ImageIO.read(SurvivalGame.class.getResourceAsStream("/images/mine_icon2.png"));
/*  94 */       survivalgame.blocks.Block.bat_sparkle = ImageIO.read(SurvivalGame.class.getResourceAsStream("/images/bat_sparkle.png"));
/*  95 */       survivalgame.blocks.Block.potion = ImageIO.read(SurvivalGame.class.getResourceAsStream("/images/potion.png"));
/*  96 */       survivalgame.blocks.Block.green_potion = ImageIO.read(SurvivalGame.class.getResourceAsStream("/images/green_potion.png"));
/*  97 */       survivalgame.blocks.Block.stun = ImageIO.read(SurvivalGame.class.getResourceAsStream("/images/stun.png"));
/*  98 */       survivalgame.blocks.Block.ice_icon = ImageIO.read(SurvivalGame.class.getResourceAsStream("/images/ice_icon.gif"));
/*  99 */       survivalgame.blocks.Block.witch_slow = ImageIO.read(SurvivalGame.class.getResourceAsStream("/images/witch_slow.png"));
/* 100 */       survivalgame.blocks.Block.wave_icon = ImageIO.read(SurvivalGame.class.getResourceAsStream("/images/wave_icon.png"));

for (int i = 0; i < 3; i++) {
/* 101 */         SurviveBoard.flower[i] = ImageIO.read(SurvivalGame.class.getResourceAsStream("/images/flower" + i + ".png"));
/*     */       }
/* 103 */       survivalgame.blocks.Block.fire = new BufferedImage[15];
/* 104 */       for (int i = 0; i < 15; i++) { survivalgame.blocks.Block.fire[i] = ImageIO.read(SurvivalGame.class.getResourceAsStream("/images/fireanimation/frame_" + i + ".gif"));
/*     */       }
/*     */       
/* 107 */       Map.mymap = ImageIO.read(SurvivalGame.class.getResourceAsStream("/images/map.png"));
/* 108 */       DropObject.dropimages[0] = ImageIO.read(SurvivalGame.class.getResourceAsStream("/images/hat0.png"));
/* 109 */       DropObject.img_param[0][0] = -27;DropObject.img_param[0][1] = -48;DropObject.img_param[0][2] = 48;DropObject.img_param[0][3] = 48;DropObject.img_param[0][4] = 7;
/* 110 */       DropObject.dropimages[1] = ImageIO.read(SurvivalGame.class.getResourceAsStream("/images/hat1.png"));
/* 111 */       DropObject.img_param[1][0] = -28;DropObject.img_param[1][1] = -40;DropObject.img_param[1][2] = 56;DropObject.img_param[1][3] = 50;
/* 112 */       DropObject.dropimages[2] = ImageIO.read(SurvivalGame.class.getResourceAsStream("/images/hat2.png"));
/* 113 */       DropObject.img_param[2][0] = -28;DropObject.img_param[2][1] = -60;DropObject.img_param[2][2] = 56;DropObject.img_param[2][3] = 60;
/* 114 */       DropObject.dropimages[3] = ImageIO.read(SurvivalGame.class.getResourceAsStream("/images/hat3.png"));
/* 115 */       DropObject.img_param[3][0] = -22;DropObject.img_param[3][1] = -44;DropObject.img_param[3][2] = 50;DropObject.img_param[3][3] = 54;DropObject.img_param[3][4] = -4;
/* 116 */       DropObject.dropimages[4] = ImageIO.read(SurvivalGame.class.getResourceAsStream("/images/hat4.png"));
/* 117 */       DropObject.img_param[4][0] = -25;DropObject.img_param[4][1] = -36;DropObject.img_param[4][2] = 50;DropObject.img_param[4][3] = 50;
/* 118 */       DropObject.dropimages[5] = ImageIO.read(SurvivalGame.class.getResourceAsStream("/images/hat5.png"));
/*     */       /*     */ 
/* 123 */       DropObject.img_param[5][0] = -49;DropObject.img_param[5][1] = -65;DropObject.img_param[5][2] = 100;DropObject.img_param[5][3] = 100;
/* 124 */       DropObject.dropimages[6] = ImageIO.read(SurvivalGame.class.getResourceAsStream("/images/hat6.png"));
/* 125 */       DropObject.img_param[6][0] = -27;DropObject.img_param[6][1] = -42;DropObject.img_param[6][2] = 58;DropObject.img_param[6][3] = 58;DropObject.img_param[6][4] = -2;
/* 126 */       DropObject.dropimages[7] = ImageIO.read(SurvivalGame.class.getResourceAsStream("/images/hat7.png"));
/* 127 */       DropObject.img_param[7][0] = -23;DropObject.img_param[7][1] = -40;DropObject.img_param[7][2] = 52;DropObject.img_param[7][3] = 52;DropObject.img_param[7][4] = -4;
/* 128 */       DropObject.dropimages[8] = ImageIO.read(SurvivalGame.class.getResourceAsStream("/images/hat8.png"));
/*     */       
/* 130 */       DropObject.img_param[8][0] = -47;DropObject.img_param[8][1] = -61;DropObject.img_param[8][2] = 100;DropObject.img_param[8][3] = 100;DropObject.img_param[8][4] = 125;
/* 131 */       DropObject.dropimages[9] = ImageIO.read(SurvivalGame.class.getResourceAsStream("/images/hat9.png"));
/* 132 */       DropObject.img_param[9][0] = -21;DropObject.img_param[9][1] = -18;DropObject.img_param[9][2] = 42;DropObject.img_param[9][3] = 24;DropObject.img_param[9][4] = -4;
/* 133 */       DropObject.dropimages[10] = ImageIO.read(SurvivalGame.class.getResourceAsStream("/images/hat10.png"));
/*     */       
/* 135 */       DropObject.img_param[10][0] = 24;DropObject.img_param[10][1] = -44;DropObject.img_param[10][2] = -56;DropObject.img_param[10][3] = 48;DropObject.img_param[10][4] = 7;
/* 136 */       DropObject.dropimages[11] = ImageIO.read(SurvivalGame.class.getResourceAsStream("/images/hat11.png"));
/* 137 */       DropObject.img_param[11][0] = -23;DropObject.img_param[11][1] = -52;DropObject.img_param[11][2] = 46;DropObject.img_param[11][3] = 80;DropObject.img_param[11][4] = 7;
/* 138 */       DropObject.dropimages[12] = ImageIO.read(SurvivalGame.class.getResourceAsStream("/images/hat12.png"));
/* 139 */       DropObject.img_param[12][0] = -31;DropObject.img_param[12][1] = -44;DropObject.img_param[12][2] = 64;DropObject.img_param[12][3] = 60;DropObject.img_param[12][4] = 7;
/* 140 */       DropObject.dropimages[13] = ImageIO.read(SurvivalGame.class.getResourceAsStream("/images/hat13.png"));
/* 141 */       DropObject.img_param[13][0] = -33;DropObject.img_param[13][1] = -62;DropObject.img_param[13][2] = 65;DropObject.img_param[13][3] = 65;DropObject.img_param[13][4] = 7;
/* 142 */       DropObject.dropimages[14] = ImageIO.read(SurvivalGame.class.getResourceAsStream("/images/hat14.png"));
/* 143 */       DropObject.img_param[14][0] = 24;DropObject.img_param[14][1] = -62;DropObject.img_param[14][2] = -72;DropObject.img_param[14][3] = 60;DropObject.img_param[14][4] = 25;
/* 144 */       DropObject.dropimages[15] = ImageIO.read(SurvivalGame.class.getResourceAsStream("/images/hat15.png"));
/* 145 */       DropObject.img_param[15][0] = 26;DropObject.img_param[15][1] = -52;DropObject.img_param[15][2] = -60;DropObject.img_param[15][3] = 60;DropObject.img_param[15][4] = 8;
/* 146 */       DropObject.dropimages[16] = ImageIO.read(SurvivalGame.class.getResourceAsStream("/images/hat16.png"));
/* 147 */       DropObject.img_param[16][0] = 26;DropObject.img_param[16][1] = -44;DropObject.img_param[16][2] = -52;DropObject.img_param[16][3] = 46;DropObject.img_param[16][4] = 2;
/* 148 */       DropObject.dropimages[17] = survivalgame.blocks.Block.fire[0];
/* 149 */       DropObject.img_param[17][0] = 26;DropObject.img_param[17][1] = -44;DropObject.img_param[17][2] = -52;DropObject.img_param[17][3] = 46;DropObject.img_param[17][4] = 2;
/* 150 */       DropObject.dropimages[18] = ImageIO.read(SurvivalGame.class.getResourceAsStream("/images/hat18.png"));
/* 151 */       DropObject.img_param[18][0] = -30;DropObject.img_param[18][1] = -64;DropObject.img_param[18][2] = 70;DropObject.img_param[18][3] = 70;DropObject.img_param[18][4] = -9;
/*     */     }
/*     */     catch (IOException localIOException) {}
/*     */     
/*     */ 
/*     */ 
/* 157 */     GUI x = new GUI();
/* 158 */     x.ready2go = false;
/* 159 */     SwingUtilities.invokeLater(new Runnable()
/*     */     {
/*     */       public void run() {
/* 162 */         x.createAndShowGUI();
/*     */         
/*     */ 
/* 165 */         x.ready2go = true;
/*     */       }
/*     */     });
/*     */     Thread.sleep(250);
/* 169 */     if (x.a.dab.initialize() == 1) {
/* 170 */       x.a.dab.getScores();
/*     */     } else {
/* 172 */       x.a.button[21].enabled = false;
/*     */     }
/* 174 */     if (x.a.readfile()) { 
                x.giveDatas();
/*     */     }
/*     */     
/* 177 */     x.a.gamestatus = -2;
/*     */     
/* 179 */     int realtime = 0;

        ScheduledExecutorService scheduler = 
                Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(x.a::turn, 20, 20, TimeUnit.MILLISECONDS);


/* 198 */     long as = System.nanoTime();
/* 199 */     float playing = -1.0F;
/* 200 */     int gc_counter = 0;
/*     */     for (;;) {
/*     */       try {
/* 203 */         Thread.sleep(20L);
/* 204 */         gc_counter++;
/* 205 */         if (gc_counter >= 250) {
/* 206 */           gc_counter = 0;
/* 207 */           System.gc();
/*     */         }
/*     */       } catch (InterruptedException localInterruptedException) {}
/* 210 */       if (x.ready2go) {
///* 211 */         x.a.turn();
/* 212 */         realtime++;
/* 213 */         if (realtime % 500 == 0) x.a.write2file();
/* 214 */         long bs = System.nanoTime();
/* 215 */         if (x.a.mainsongtime < 5650) { x.a.mainsongtime += 1; } else { x.a.mainsongtime = 0;x.a.ismainsong = true;x.a.playSound(0, false);playing = -1.0F;x.a.ismainsong = false; }
/* 216 */         float current = x.a.mastervolume * x.a.musicvolume * 1.0E-4F;
/* 217 */         if (current != playing) {
/*     */           try {
/* 219 */             x.a.mainsong.setGain(current);playing = current;
/*     */           } catch (BasicPlayerException ex) {
/* 221 */             System.out.println(ex.getMessage());
/*     */           }
/*     */         }
/*     */         
/* 225 */         if (realtime % 250 == 0) {
/* 226 */           long heapsize = Runtime.getRuntime().totalMemory();
/* 227 */           System.out.println("heapsize is::" + heapsize);
/*     */         }
/*     */         
/* 230 */         if (bs > as + 1000000000L) { as = bs;x.a.fps2 = x.a.fps;x.a.fps = 0;System.out.println("FPS : " + x.a.fps2);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Dropbox\Dropbox\Game Development\SurvivalGame v0.977\SurvivalGame.jar!\survivalgame\SurvivalGame.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */