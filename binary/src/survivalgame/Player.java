/*    */ package survivalgame;
/*    */ 
/*    */ import java.awt.Font;
/*    */ import java.awt.Graphics;
/*    */ import java.awt.Graphics2D;
/*    */ import java.awt.font.FontRenderContext;
/*    */ import java.awt.font.GlyphVector;
/*    */ import java.awt.geom.Rectangle2D;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Player
/*    */ {
/*    */   double health;
/*    */   double mhealth;
/*    */   double speed;
/*    */   double regen;
/*    */   int gametime;
/* 25 */   short[] aroadx2 = new short[1000000];
/* 26 */   short[] aroady2 = new short[1000000];
/* 27 */   short[] aroadk2 = new short[1000000];
/*    */   int aroadl2;
/* 29 */   int finx = 0; int finy = 0;
/*    */   double stunned;
/*    */   double slowed;
           double laser_stun;
           double fountain_wave_slow;
           double fountain_slow;
/*    */   double modifier;
/*    */   double purple_potion_timer;
/*    */   double green_potion_timer;
/*    */   double burn;
/*    */   int score;
/*    */   boolean damaged;
/*    */   /*    */   /*    */   /*    */   
/* 39 */   public void regenerate() { 
            if (this.burn >= 0.5) {
                if(this.burn >= 1.0D){
                    this.burn *= 1.001D; 
                } else {
                    this.burn /= 1.001D;
                }
                if (this.burn > 5.0D) 
                    this.burn = 5.0D; 
                hitDamage(this.burn * 0.06D + this.burn * this.burn * 0.028D); 
            } else {
                this.burn = 0;
            }
/* 40 */     if (this.purple_potion_timer > 0.0D) this.health -= 25.0D * this.health / 40000.0D;
/* 41 */     if (this.green_potion_timer > 0.0D) this.health -= 25.0D * this.health / 30000.0D;
/* 42 */     if (this.green_potion_timer > 0.0D) if (this.health + this.regen <= this.mhealth) this.health += this.regen; else this.health = this.mhealth;
/* 43 */     if (this.health + this.regen <= this.mhealth) this.health += this.regen; else this.health = this.mhealth;
/*    */   }
/*    */   
/* 46 */   public void regenerate(double regen) { if (this.health + regen <= this.mhealth) this.health += regen; else this.health = this.mhealth;
/*    */   }
/*    */   /*    */   /*    */   /*    */   
/* 49 */   public void hitDamage(double damage) { 
            double newdamage = damage;
/* 50 */     if (this.purple_potion_timer > 0.0D) newdamage *= 0.5D;
/* 51 */     if (this.green_potion_timer > 0.0D) newdamage *= 1.5D;
/* 52 */     this.health -= newdamage;
/* 53 */     this.damaged = true;
/*    */   }
/*    */   
/*    */   public String getTimeString() {
/* 57 */     int[] ti = new int[5];
/* 58 */     ti[0] = (this.gametime % 50);ti[1] = ((this.gametime - ti[0]) / 50);ti[0] *= 2;
/* 59 */     ti[2] = (ti[1] % 60);ti[3] = ((ti[1] - ti[2]) / 60);
/* 60 */     String s = String.valueOf(ti[3]).concat(":").concat(String.valueOf(ti[2]).concat(".").concat(String.valueOf(ti[0])));
/* 61 */     return s;
/*    */   }
/*    */   
/*    */   public static String getTimeString(int gametime) {
/* 65 */     int[] ti = new int[5];
/* 66 */     ti[0] = (gametime % 50);ti[1] = ((gametime - ti[0]) / 50);ti[0] *= 2;
/* 67 */     ti[2] = (ti[1] % 60);ti[3] = ((ti[1] - ti[2]) / 60);
/* 68 */     ti[4] = (ti[3] / 60);
/* 69 */     String s = String.valueOf(ti[3]).concat(":").concat(String.valueOf(ti[2]).concat(".").concat(String.valueOf(ti[0])));
/*    */     
/* 71 */     if (gametime == 0) return "-";
/* 72 */     return s;
/*    */   }
/*    */   
/* 75 */   public static void drawString(Graphics g, String s, int fontsize, int x, int y, int scrx, int scry) { Font myfont = new Font("Arial", 0, fontsize * scrx / 1280);
/* 76 */     g.setFont(myfont);
/*    */     
/* 78 */     Graphics2D g2 = (Graphics2D)g;
/* 79 */     FontRenderContext frc = g2.getFontRenderContext();
/*    */     
/* 81 */     GlyphVector gv = myfont.createGlyphVector(frc, s);
/* 82 */     Rectangle2D box = gv.getVisualBounds();
/*    */     
/* 84 */     g.drawString(s, (int)(x * scrx / 1280 - box.getWidth() / 2.0D), (int)(y * scry / 720 + 0.0D * box.getHeight() / 2.0D));
/*    */   }
/*    */ }


/* Location:              D:\Dropbox\Dropbox\Game Development\SurvivalGame v0.977\SurvivalGame.jar!\survivalgame\Player.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */