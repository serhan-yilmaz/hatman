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
/*    */ public class Witch
/*    */   extends Block
/*    */ {
/*    */   public short[] aroadx2;
/*    */   public short[] aroady2;
/*    */   public short[] aroadk2;
/* 19 */   public int curroad = 0;
/* 20 */   public int aroadl2 = 0;
/* 21 */   public int status = 0;
/* 22 */   public int charge = -1;
/* 23 */   public int laserx = 0;
/* 24 */   public int lasery = 0;
/*    */   
/*    */   public Witch(int x, int y, Color color, double speed) {
/* 27 */     super(x, y, color, 20);
/* 28 */     this.x = x;
/* 29 */     this.y = y;
/* 30 */     this.color = color;
/* 31 */     this.speed = speed;
/* 32 */     this.status = 0;
/* 33 */     this.type = 3;
/* 34 */     this.maxtimer = -1;
/* 35 */     this.aroadx2 = new short[200000];
/* 36 */     this.aroady2 = new short[200000];
/* 37 */     this.aroadk2 = new short[200000];
/*    */   }
/*    */   
/*    */   public void draw(Graphics g) {
/* 41 */     super.draw(g);
/* 42 */     g.setColor(this.color);
/* 43 */     g.drawOval(this.x - 140, this.y - 140, 280, 280);
/* 44 */     if (this.status == 2) {
/* 45 */       g.drawOval(this.x - 258, this.y - 258, 516, 516);
/* 46 */       g.drawOval(this.x - 260, this.y - 260, 520, 520);
/*    */     }
/* 48 */     if ((this.charge > 0) && (this.charge < 55)) {
/* 49 */       g.setColor(new Color(120 + this.charge * 2, 50, 35, (int)(101.0D + this.charge * 1.6D)));
/* 50 */       g.fillOval(this.x - 138, this.y - 138, 276, 276);
/*    */     }
/* 52 */     if (this.charge >= 55) {
/* 53 */       g.setColor(new Color(250, 50, 35, 194));
/* 54 */       g.fillOval(this.x - 193, this.y - 193, 386, 386);
/*    */     }
/* 56 */     g.drawImage(witch_img, this.x - 24, this.y - 42, 50, 50, null);
/*    */   }
/*    */   
/*    */   public void drawMini(Graphics g) {
/* 60 */     super.drawMini(g);
/* 61 */     g.setColor(this.color);
/* 62 */     g.drawOval(roundMini(this.x - 140), roundMini(this.y - 140), roundMini(280), roundMini(280));
/* 63 */     if (this.status == 2) {
/* 64 */       g.drawOval(roundMini(this.x - 258), roundMini(this.y - 258), roundMini(516), roundMini(516));
/* 65 */       g.drawOval(roundMini(this.x - 260), roundMini(this.y - 260), roundMini(520), roundMini(520));
/*    */     }
/* 67 */     if ((this.charge > 0) && (this.charge < 55)) {
/* 68 */       g.setColor(new Color(120 + this.charge * 2, 50, 35, (int)(101.0D + this.charge * 1.6D)));
/* 69 */       g.drawOval(roundMini(this.x - 138), roundMini(this.y - 138), roundMini(276), roundMini(276));
/*    */     }
/* 71 */     if (this.charge >= 55) {
/* 72 */       g.setColor(new Color(250, 50, 35, 194));
/* 73 */       g.drawOval(roundMini(this.x - 193), roundMini(this.y - 193), roundMini(386), roundMini(386));
/*    */     }
/* 75 */     g.drawImage(witch_img, roundMini(this.x - 24), roundMini(this.y - 42), roundMini(50), roundMini(50), null);
/*    */   }
/*    */ }


/* Location:              D:\Dropbox\Dropbox\Game Development\SurvivalGame v0.977\SurvivalGame.jar!\survivalgame\blocks\Witch.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */