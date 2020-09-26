/*    */ package survivalgame.blocks;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import java.awt.Graphics;
/*    */ import java.awt.image.BufferedImage;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Block
/*    */ {
/*    */   public int x;
/*    */   public int y;
/*    */   public Color color;
/* 34 */   public double radius = 20;
/* 35 */   public double remx = 0.0D; public double remy = 0.0D;
/* 36 */   public int timer = 0;
/*    */   public double angle;
/*    */   public double speed;
/*    */   public int type;
/*    */   public int maxtimer;
/*    */   public static BufferedImage witch_img;
/*    */   public static BufferedImage sparkle;
/*    */   public static BufferedImage bat_sparkle;
/*    */   public static BufferedImage explosion;
/*    */   public static BufferedImage mineicon;
/*    */   public static BufferedImage mineicon2;
/*    */   public static BufferedImage potion;
/*    */   public static BufferedImage[] fire;
/*    */   public static BufferedImage green_potion;
/*    */   public static BufferedImage stun;
/*    */   public static BufferedImage ice_icon;
/*    */   public static BufferedImage witch_slow;
           public static BufferedImage wave_icon;
/*    */   
/*    */   public Block(int x, int y, Color color, int radius) {
/* 55 */     this.x = x;
/* 56 */     this.y = y;
/* 57 */     this.color = color;
/* 58 */     this.radius = radius;
/*    */   }
/*    */   
/*    */   public void draw(Graphics g) {
/* 62 */     g.setColor(this.color);
             int rad = (int) this.radius;
/* 63 */     g.fillOval(this.x - rad, this.y - rad, 2 * rad, 2 * rad);
/*    */   }
/*    */   
/*    */   public void drawMini(Graphics g) {
/* 67 */     g.setColor(this.color);
             int rad = (int) this.radius;
/* 68 */     g.fillOval(roundMini(this.x - rad), roundMini(this.y - rad), roundMini(2 * rad), roundMini(2 * rad));
/*    */   }
/*    */   
/* 71 */   public int roundMini(int num) { int result = (int)Math.round(num / 10.0D);
/* 72 */     if ((result <= 1) && (result >= 0)) return 1;
/* 73 */     if ((result >= -1) && (result <= 0)) return -1;
/* 74 */     return result;
/*    */   }
/*    */   
/* 77 */   public void move(double speedx, double speedy) { this.remx += speedx;this.remy += speedy;
/* 78 */     this.x += (int)this.remx;this.y += (int)this.remy;
/* 79 */     this.remx -= (int)this.remx;this.remy -= (int)this.remy;
/*    */   }
/*    */   
/* 82 */   public void move() { this.remx += this.speed * Math.cos(this.angle);this.remy += this.speed * Math.sin(this.angle);
/* 83 */     this.x += (int)this.remx;this.y += (int)this.remy;
/* 84 */     this.remx -= (int)this.remx;this.remy -= (int)this.remy;
/*    */   }
/*    */   
/* 87 */   public boolean tick() { if (this.maxtimer != -1) this.timer += 1;
/* 88 */     if (this.maxtimer != -1) return this.timer >= this.maxtimer; return false;
/*    */   }
/*    */ }


/* Location:              D:\Dropbox\Dropbox\Game Development\SurvivalGame v0.977\SurvivalGame.jar!\survivalgame\blocks\Block.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */