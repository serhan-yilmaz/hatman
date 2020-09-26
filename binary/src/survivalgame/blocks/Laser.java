/*    */ package survivalgame.blocks;
/*    */ 
/*    */ import java.awt.Color;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Laser
/*    */   extends Block
/*    */ {
/*    */   public Laser(int x, int y, Color color, double angle, double speed, boolean burns)
/*    */   {
/* 17 */     super(x, y, color, 20);
/* 18 */     this.angle = angle;
/* 19 */     this.speed = speed;
/* 20 */     this.type = 6;
/* 21 */     this.maxtimer = 200;
             this.burns = burns;
/*    */   }
            public final boolean burns;
/*    */ }
         
          


/* Location:              D:\Dropbox\Dropbox\Game Development\SurvivalGame v0.977\SurvivalGame.jar!\survivalgame\blocks\Laser.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */