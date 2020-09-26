/*    */ package survivalgame;
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
/*    */ public class Slider
/*    */ {
/*    */   int x;
/*    */   int y;
/*    */   int width;
/*    */   int height;
/*    */   Color backGroundColor;
/*    */   Color sliderColor;
/*    */   int position;
/*    */   int maxposition;
/*    */   boolean enabled;
/*    */   boolean displayed;
/*    */   boolean selected;
/*    */   
/*    */   public Slider(int x, int y, int width, int height, Color backGroundColor, boolean displayed, int position)
/*    */   {
/* 28 */     this.x = x;
/* 29 */     this.y = y;
/* 30 */     this.width = width;
/* 31 */     this.height = height;
/* 32 */     this.backGroundColor = backGroundColor;
/* 33 */     this.sliderColor = Color.black;
/* 34 */     this.position = position;
/* 35 */     this.maxposition = 100;
/* 36 */     this.displayed = displayed;
/* 37 */     this.enabled = true;
/* 38 */     this.selected = false;
/*    */   }
/*    */   
/* 41 */   public void draw(Graphics g, int scrx, int scry) { if (this.displayed) {
/* 42 */       int x = this.x * scrx / 1280;int y = this.y * scry / 720;
/* 43 */       int width = this.width * scrx / 1280;
/* 44 */       int height = this.height * scry / 720;
/* 45 */       g.setColor(this.backGroundColor);
/* 46 */       g.fillRect(x, y, width, height);
/* 47 */       g.setColor(Color.black);
/* 48 */       g.drawRect(x, y, width, height);
/* 49 */       g.drawRect(x + 2, y + 2, width - 4, height - 4);
/* 50 */       g.setColor(this.sliderColor);
/* 51 */       g.drawLine(x + 8 * scrx / 1280, y + height / 2, x + width - 8 * scrx / 1280, y + height / 2);
/* 52 */       int[] polyx = new int[3];int[] polyy = new int[3];
/* 53 */       polyx[0] = (x + 8 * scrx / 1280 + this.position * (width - 16 * scrx / 1280) / this.maxposition);polyy[0] = (y + height / 2);
/* 54 */       polyx[1] = ((int)(polyx[0] + 15.0D * Math.cos(1.046150353645401D) * scrx / 1280.0D));polyy[1] = ((int)(polyy[0] - 15.0D * Math.sin(1.046150353645401D) * scry / 720.0D));
/* 55 */       polyx[2] = ((int)(polyx[0] - 15.0D * Math.cos(1.046150353645401D) * scrx / 1280.0D));polyy[2] = ((int)(polyy[0] - 15.0D * Math.sin(1.046150353645401D) * scry / 720.0D));
/* 56 */       for (int i = 0; i < 11; i++) {
/* 57 */         g.drawLine(x + i * (width - 16 * scrx / 1280) / 10 + 8 * scrx / 1280, y + 12 * scry / 720, x + i * (width - 16 * scrx / 1280) / 10 + 8 * scrx / 1280, y + height - 12 * scry / 720);
/*    */       }
/* 59 */       g.setColor(Color.gray);
/* 60 */       g.fillPolygon(polyx, polyy, 3);
/* 61 */       g.setColor(Color.black);
/* 62 */       g.drawPolygon(polyx, polyy, 3);
/*    */     }
/*    */   }
/*    */   
/* 66 */   public void setSliderPosition(int mx, int my, int scrx, int scry) { if ((this.enabled) && (this.displayed) && 
/* 67 */       (mx >= this.x) && (mx <= this.x + this.width) && (my >= this.y) && (my <= this.y + this.height)) {
/* 68 */       this.selected = true;
/* 69 */       double portion = (0.0D + mx - this.x - 8.0D) / (this.width - 16);
/* 70 */       this.position = ((int)(portion * this.maxposition)); if (this.position < 0) this.position = 0; if (this.position > this.maxposition) this.position = this.maxposition;
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\Dropbox\Dropbox\Game Development\SurvivalGame v0.977\SurvivalGame.jar!\survivalgame\Slider.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */