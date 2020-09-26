/*    */ package survivalgame.blocks;
/*    */ 
/*    */ import java.awt.Graphics;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Potion
/*    */   extends Block
/*    */ {
/*    */   public Potion(int x, int y, int type, int maxtimer)
/*    */   {
/* 16 */     super(x, y, null, 20);
/* 17 */     this.type = type;
/* 18 */     this.maxtimer = maxtimer;
/*    */   }
/*    */   
/*    */   public void draw(Graphics g) {
/* 22 */     if (this.type == 12) {
/* 23 */       g.drawImage(potion, this.x - 18, this.y - 18, 36, 36, null);
/*    */     }
/* 25 */     else if (this.type == 13) {
/* 26 */       g.drawImage(green_potion, this.x - 18, this.y - 18, 36, 36, null);
/*    */     }
/*    */   }
/*    */   
/*    */   public void drawMini(Graphics g) {
/* 31 */     if (this.type == 12) {
/* 32 */       g.drawImage(potion, roundMini(this.x - 40), roundMini(this.y - 40), 8, 8, null);
/*    */     }
/* 34 */     else if (this.type == 13) {
/* 35 */       g.drawImage(green_potion, roundMini(this.x - 40), roundMini(this.y - 40), 8, 8, null);
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\Dropbox\Dropbox\Game Development\SurvivalGame v0.977\SurvivalGame.jar!\survivalgame\blocks\Potion.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */