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
/*    */ public class BlackBullet
/*    */   extends Block
/*    */ {
/*    */   public BlackBullet(int x, int y, Color color, int radius, double angle, double speed)
/*    */   {
/* 17 */     super(x, y, color, radius);
/* 18 */     this.angle = angle;
/* 19 */     this.speed = speed;
/* 20 */     this.maxtimer = 900;
/* 21 */     this.type = 2;
/*    */   }
/*    */   
/*    */   public void move() {
/* 25 */     this.remx += this.speed * Math.cos(this.angle);this.remy += this.speed * Math.sin(this.angle);
/* 26 */     this.x += (int)this.remx;this.y += (int)this.remy;
/* 27 */     this.remx -= (int)this.remx;this.remy -= (int)this.remy;
/*    */   }
/*    */   
/*    */   public void drawMini(Graphics g) {
/* 31 */     if ((this.x >= 0) && (this.y >= 0)) super.drawMini(g);
/*    */   }
/*    */ }


/* Location:              D:\Dropbox\Dropbox\Game Development\SurvivalGame v0.977\SurvivalGame.jar!\survivalgame\blocks\BlackBullet.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */