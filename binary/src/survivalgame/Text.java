/*    */ package survivalgame;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import java.awt.Font;
/*    */ import java.awt.Graphics;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Text
/*    */ {
/*    */   String s;
/*    */   int x;
/*    */   int y;
/*    */   Color color;
/* 20 */   int size = 16;
/* 21 */   private int timer = 0;
/* 22 */   int maxtimer = 310;
/*    */   
/* 24 */   public Text(String s, int x, int y, Color color, int size) { this.s = s;
/* 25 */     this.x = x;
/* 26 */     this.y = y;
/* 27 */     this.color = color;
/* 28 */     this.size = size;
/*    */   }
/*    */   
/*    */   public void draw(Graphics g, int scrx, int scry) {
/* 32 */     if (this.timer <= this.maxtimer * 0.65D) { g.setColor(this.color);
/* 33 */     } else { double por = (this.timer - this.maxtimer * 0.65D) / (this.maxtimer * 0.35D);
/* 34 */       int dis = (int)(255.0D - 255.0D * por);g.setColor(new Color(this.color.getRed(), this.color.getGreen(), this.color.getBlue(), dis));
/*    */     }
/* 36 */     g.setFont(new Font("Arial", 0, this.size * scrx / 1280));
/* 37 */     g.drawString(this.s, this.x * scrx / 1280, this.y * scry / 720);
/*    */   }
/*    */   
/* 40 */   public boolean tick() { this.timer += 1;
/* 41 */     return this.timer >= this.maxtimer;
/*    */   }
/*    */   
/* 44 */   public void shift() { this.y = ((int)(this.y - this.size * 1.15D)); }
/*    */ }


/* Location:              D:\Dropbox\Dropbox\Game Development\SurvivalGame v0.977\SurvivalGame.jar!\survivalgame\Text.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */