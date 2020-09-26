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
/*    */ public class Mine
/*    */   extends Block
/*    */ {
/*    */   public boolean isupgraded;
/* 17 */   public int charge = -1;
/*    */   
/* 19 */   public Mine(int x, int y, Color color, int radius, boolean isupgraded) { super(x, y, color, radius);
/* 20 */     this.maxtimer = 2580;
/* 21 */     this.type = 7;
/* 22 */     this.isupgraded = isupgraded;
/*    */   }
/*    */   
/*    */   public void draw(Graphics g) {
/* 26 */     if (!this.isupgraded) {
/* 27 */       g.setColor(this.color);
/*    */     } else {
/* 29 */       g.setColor(new Color(90, 155, 185));
/*    */     }
            int rad = (int) this.radius;
/* 31 */     g.drawOval(this.x - rad, this.y - rad, 2 * rad, 2 * rad);
/*    */     
/* 33 */     if (this.charge == -1) {
/* 34 */       g.drawImage(mineicon, this.x - 16, this.y - 16, 40, 40, null);
/*    */     } else {
/* 36 */       g.drawImage(mineicon2, this.x - 16, this.y - 16, 40, 40, null);
/*    */     }
/* 38 */     if (this.charge >= 25) {
/* 39 */       if (!this.isupgraded) {
/* 40 */         g.setColor(new Color(250, 50, 35, 194));
/* 41 */         g.fillOval(this.x - 130, this.y - 130, 260, 260);
/*    */       }
/*    */       else {
/* 44 */         g.setColor(new Color(90, 155, 185, 194));
/* 45 */         g.fillOval(this.x - 150, this.y - 150, 300, 300);
/*    */       }
/*    */     }
/*    */   }
/*    */   
/*    */   public void drawMini(Graphics g) {
/* 51 */     if (!this.isupgraded) {
/* 52 */       g.setColor(this.color);
/*    */     } else {
/* 54 */       g.setColor(new Color(90, 155, 185));
/*    */     }
            int rad = (int) this.radius;
/* 56 */     g.drawOval(roundMini(this.x - rad), roundMini(this.y - rad), roundMini(2 * rad), roundMini(2 * rad));
/*    */     
/* 58 */     if (this.charge == -1) {
/* 59 */       g.drawImage(mineicon, roundMini(this.x - 16), roundMini(this.y - 16), roundMini(40), roundMini(40), null);
/*    */     } else {
/* 61 */       g.drawImage(mineicon, roundMini(this.x - 16), roundMini(this.y - 16), roundMini(40), roundMini(40), null);
/*    */     }
/* 63 */     if (this.charge >= 25) {
/* 64 */       if (!this.isupgraded) {
/* 65 */         g.setColor(new Color(250, 50, 35, 194));
/* 66 */         g.fillOval(roundMini(this.x - 130), roundMini(this.y - 130), roundMini(260), roundMini(260));
/*    */       }
/*    */       else {
/* 69 */         g.setColor(new Color(90, 155, 185, 194));
/* 70 */         g.fillOval(roundMini(this.x - 150), roundMini(this.y - 150), roundMini(300), roundMini(300));
/*    */       }
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\Dropbox\Dropbox\Game Development\SurvivalGame v0.977\SurvivalGame.jar!\survivalgame\blocks\Mine.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */