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
/*    */ public class Meteor
/*    */   extends Block
/*    */ {
/*    */   public Meteor(int x, int y, Color color, int maxtimer, int type)
/*    */   {
/* 18 */     super(x, y, color, 8);
/* 19 */     this.maxtimer = maxtimer;
/* 20 */     this.type = type;
/*    */   }
/*    */   
/*    */   public void draw(Graphics g) {
/* 24 */     g.setColor(Color.black);
                int rad = (int) this.radius;
/* 25 */     g.fillOval(this.x - rad, this.y - rad, 2 * rad, 2 * rad);
/* 26 */     if (this.type == 4) {
/* 27 */       g.setColor(this.color);
/* 28 */       g.drawOval(this.x - 100, this.y - 100, 200, 200);
/* 29 */       if (this.timer < 65) {
/* 30 */         g.drawOval((int)(this.x - this.timer * 1.4D), (int)(this.y - this.timer * 1.4D), (int)(2 * this.timer * 1.4D), (int)(2 * this.timer * 1.4D));
/*    */       } else {
/* 32 */         g.drawOval((int)(this.x - 91.0D), (int)(this.y - 91.0D), 182, 182);
/* 33 */         g.setColor(new Color(this.color.getRed(), this.color.getBlue(), this.color.getGreen(), 140));
/* 34 */         g.fillOval(this.x - 100, this.y - 100, 200, 200);
/*    */       }
/*    */     }
/* 37 */     else if (this.type == 5) {
/* 38 */       g.setColor(this.color);
/* 39 */       g.drawOval(this.x - 140, this.y - 140, 280, 280);
/* 40 */       if (this.timer < 65) {
/* 41 */         g.drawOval((int)(this.x - this.timer * 2.15D), (int)(this.y - this.timer * 2.15D), (int)(2 * this.timer * 2.15D), (int)(2 * this.timer * 2.15D));
/*    */       } else {
/* 43 */         g.drawOval((int)(this.x - 139.75D), (int)(this.y - 139.75D), 279, 279);
/* 44 */         g.setColor(new Color(this.color.getRed(), this.color.getBlue(), this.color.getGreen(), 140));
/* 45 */         g.fillOval(this.x - 140, this.y - 140, 280, 280);
/*    */       }
/*    */     }
/*    */   }
/*    */   
/*    */   public void drawMini(Graphics g) {
/* 51 */     g.setColor(Color.black);
                int rad = (int) this.radius;
/* 52 */     g.fillOval(roundMini(this.x - rad), roundMini(this.y - rad), roundMini(2 * rad), roundMini(2 * rad));
/* 53 */     if (this.type == 4) {
/* 54 */       g.setColor(this.color);
/* 55 */       g.drawOval(roundMini(this.x - 100), roundMini(this.y - 100), roundMini(200), roundMini(200));
/* 56 */       if (this.timer < 65) {
/* 57 */         g.drawOval(roundMini((int)(this.x - this.timer * 1.4D)), roundMini((int)(this.y - this.timer * 1.4D)), roundMini((int)(2 * this.timer * 1.4D)), roundMini((int)(2 * this.timer * 1.4D)));
/*    */       } else {
/* 59 */         g.drawOval(roundMini((int)(this.x - 91.0D)), roundMini((int)(this.y - 91.0D)), roundMini(182), roundMini(182));
/* 60 */         g.setColor(new Color(this.color.getRed(), this.color.getBlue(), this.color.getGreen(), 140));
/* 61 */         g.fillOval(roundMini(this.x - 100), roundMini(this.y - 100), roundMini(200), roundMini(200));
/*    */       }
/*    */     }
/* 64 */     else if (this.type == 5) {
/* 65 */       g.setColor(this.color);
/* 66 */       g.drawOval(roundMini(this.x - 140), roundMini(this.y - 140), roundMini(280), roundMini(280));
/* 67 */       if (this.timer < 65) {
/* 68 */         g.drawOval(roundMini((int)(this.x - this.timer * 2.15D)), roundMini((int)(this.y - this.timer * 2.15D)), roundMini((int)(2 * this.timer * 2.15D)), roundMini((int)(2 * this.timer * 2.15D)));
/*    */       } else {
/* 70 */         g.drawOval(roundMini((int)(this.x - 139.75D)), roundMini((int)(this.y - 139.75D)), roundMini(279), roundMini(279));
/* 71 */         g.setColor(new Color(this.color.getRed(), this.color.getBlue(), this.color.getGreen(), 140));
/* 72 */         g.fillOval(roundMini(this.x - 140), roundMini(this.y - 140), roundMini(280), roundMini(280));
/*    */       }
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\Dropbox\Dropbox\Game Development\SurvivalGame v0.977\SurvivalGame.jar!\survivalgame\blocks\Meteor.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */