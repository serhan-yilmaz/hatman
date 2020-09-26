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
/*    */ public class RedBall
/*    */   extends Block
/*    */ {
/*    */   public RedBall(int x, int y, Color color, int radius, double speed)
/*    */   {
/* 17 */     super(x, y, color, radius);
/* 18 */     this.speed = speed;
/* 19 */     this.maxtimer = 3000;
/* 20 */     this.type = 1;
             this.initial_radius = radius;
             this.initial_speed = speed;
             this.initial_damage = 405D;
             this.damage = this.initial_damage;
/*    */   }
/*    */   
/*    */   public void drawMini(Graphics g) {
/* 24 */     g.setColor(this.color);
            int rad = (int) this.radius;
/* 25 */     g.fillOval(roundMini(this.x - rad), roundMini(this.y - rad), 1 + roundMini(2 * rad), 1 + roundMini(2 * rad));
/*    */   }

           public void increaseMass(double r, double time){
               double rad2 = this.radius * this.radius;
               this.radius = Math.sqrt(rad2 + r*r);
               this.maxtimer += time * 0.05;
//               if(this.maxtimer - this.timer > 0.9 * time){
//                   this.maxtimer += time * 0.1;
//               } else {
////                   this.timer = 0;
////                   this.maxtimer = (int) time + this.timer;
//               }
               updateMass();
           }
           
           public void updateMass(){
               double ratio = Math.sqrt(this.radius / this.initial_radius);
               this.damage = this.initial_damage * ratio * ratio;
               this.speed = this.initial_speed / ratio;
           }
           
           @Override
           public boolean tick(){
               if(this.radius > this.initial_radius){
                double rad2 = this.radius * this.radius;
                double k = 2.5e-3;
//                double rad = this.radius;
                this.radius = Math.sqrt(rad2 - k * this.radius);
//                double r1 = 0.1;
//                double dRad = this.radius - this.initial_radius;
//                this.radius = Math.sqrt(rad2 - k * dRad - r1 * r1);
                if(this.radius < this.initial_radius){
                    this.radius = initial_radius;
                }
                updateMass();
                this.timer--;
               }
               return super.tick();
           }
           
           public double damage;
           double initial_damage;
           double initial_speed;
           double initial_radius;
/*    */ }


/* Location:              D:\Dropbox\Dropbox\Game Development\SurvivalGame v0.977\SurvivalGame.jar!\survivalgame\blocks\RedBall.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */