/*    */ package survivalgame;
/*    */ 
/*    */ import java.awt.Color;
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
/*    */ public class TextField
/*    */ {
/*    */   int x;
/*    */   int y;
/*    */   int width;
/*    */   int height;
/*    */   Color backGroundColor;
/*    */   Color textColor;
/*    */   String text;
/*    */   boolean enabled;
/*    */   boolean displayed;
/*    */   boolean selected;
/* 31 */   int dispcount = 0;
/*    */   
/* 33 */   public TextField(int x, int y, int width, int height, Color backGroundColor, String text) { this.x = x;
/* 34 */     this.y = y;
/* 35 */     this.width = width;
/* 36 */     this.height = height;
/* 37 */     this.backGroundColor = backGroundColor;
/* 38 */     this.textColor = Color.black;
/* 39 */     this.text = text;
/* 40 */     this.enabled = true;
/* 41 */     this.displayed = true;
/* 42 */     this.selected = true;
/*    */   }
/*    */   
/* 45 */   public void draw(Graphics g, int scrx, int scry) { if (this.displayed) {
/* 46 */       int x = this.x * scrx / 1280;int y = this.y * scry / 720;
/* 47 */       int width = this.width * scrx / 1280;
/* 48 */       int height = this.height * scry / 720;
/* 49 */       g.setColor(this.backGroundColor);
/* 50 */       g.fillRect(x, y, width, height);
/* 51 */       g.setColor(Color.black);
/* 52 */       g.drawRect(x, y, width, height);
/* 53 */       g.drawRect(x + 2, y + 2, width - 4, height - 4);
/* 54 */       g.setColor(this.textColor);
/*    */       
/*    */ 
/* 57 */       Font myfont = new Font("Arial", 0, 16 * scrx / 1280);
/* 58 */       g.setFont(myfont);
/* 59 */       g.drawString(this.text, x + 4, y + height / 2 + 8);
/* 60 */       Graphics2D g2 = (Graphics2D)g;
/* 61 */       FontRenderContext frc = g2.getFontRenderContext();
/*    */       
/* 63 */       GlyphVector gv = myfont.createGlyphVector(frc, this.text);
/* 64 */       Rectangle2D box = gv.getVisualBounds();
/*    */       
/* 66 */       if (this.selected) {
/* 67 */         this.dispcount += 1;
/* 68 */         if (this.dispcount >= 25) g.drawLine((int)(x + 8 + box.getWidth()), y + 6 * scry / 720, (int)(x + 8 + box.getWidth()), y + height - 6 * scry / 720);
/* 69 */         if (this.dispcount == 50) this.dispcount = 0;
/*    */       }
/*    */     }
/*    */   }
/*    */   
/* 74 */   public void write(char string) { if (this.text.length() < 10) this.text += string;
/*    */   }
/*    */   
/* 77 */   public void delete() { if (this.text.length() > 0) this.text = this.text.substring(0, this.text.length() - 1);
/*    */   }
/*    */ }


/* Location:              D:\Dropbox\Dropbox\Game Development\SurvivalGame v0.977\SurvivalGame.jar!\survivalgame\TextField.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */