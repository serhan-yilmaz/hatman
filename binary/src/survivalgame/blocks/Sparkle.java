/*    */ package survivalgame.blocks;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import java.awt.Graphics;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Sparkle
/*    */   extends Block
/*    */ {
/* 16 */   public int charge = -1;
/* 17 */   public int status = 0;
/*    */   
/* 19 */   public Sparkle(int x, int y, int radius, int charge, int maxtimer, Color color, int type) { super(x, y, color, radius);
/* 20 */     this.charge = charge;
/* 21 */     this.maxtimer = maxtimer;
/* 22 */     this.type = type;
/*    */   }
/*    */   
/*    */   public void draw(Graphics g)
/*    */   {
/* 27 */     if (this.status < this.charge) {
                int rad = (int) this.radius;
/* 28 */       if (this.type == 8) {
/* 29 */         g.drawImage(sparkle, this.x - rad, this.y - rad, 2 * rad, 2 * rad, null);
/*    */       }
/* 31 */       else if (this.type == 11) {
/* 32 */         g.drawImage(bat_sparkle, this.x - rad, this.y - rad, 2 * rad, 2 * rad, null);
/*    */       }
/* 34 */       else if (this.type == 9) {
/* 35 */         g.drawImage(explosion, this.x - rad, this.y - rad, 2 * rad, 2 * rad, null);
/*    */       }
/* 37 */       else if (this.type == 10) {
/* 38 */         g.setColor(this.color);
/* 39 */         g.fillOval(this.x - rad, this.y - rad, 2 * rad, 2 * rad);
/*    */       }
/*    */     }
/*    */   }
/*    */   
/*    */   public void drawMini(Graphics g)
/*    */   {
/* 46 */     if (this.status < this.charge) {
                int rad = (int) this.radius;
/* 47 */       if (this.type == 8) {
/* 48 */         g.drawImage(sparkle, roundMini(this.x - rad), roundMini(this.y - rad), roundMini(2 * rad), roundMini(2 * rad), null);
/*    */       }
/* 50 */       else if (this.type == 11) {
/* 51 */         g.setColor(Color.black);
/* 52 */         g.fillOval(roundMini(this.x - rad), roundMini(this.y - rad), roundMini(2 * rad), roundMini(2 * rad));
/*    */       }
/* 54 */       else if (this.type == 9) {
/* 55 */         g.drawImage(explosion, roundMini(this.x - rad), roundMini(this.y - rad), roundMini(2 * rad), roundMini(2 * rad), null);
/*    */       }
/* 57 */       else if (this.type == 10) {
/* 58 */         g.setColor(this.color);
/* 59 */         g.fillOval(roundMini(this.x - rad), roundMini(this.y - rad), 1 + roundMini(2 * rad), 1 + roundMini(2 * rad));
/*    */       }
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\Dropbox\Dropbox\Game Development\SurvivalGame v0.977\SurvivalGame.jar!\survivalgame\blocks\Sparkle.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */