/*     */ package survivalgame.blocks;
/*     */ 
/*     */ import java.awt.AlphaComposite;
/*     */ import java.awt.Color;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.image.BufferedImage;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Hatman
/*     */   extends Block
/*     */ {
/*     */   public short[] aroadx2;
/*     */   public short[] aroady2;
/*     */   public short[] aroadk2;
/*  23 */   public int curroad = 0;
/*  24 */   public int aroadl2 = 0;
/*  25 */   public int anim1 = 0;
/*  26 */   public int anim2 = 0;
/*  27 */   public int animsize = 0;
/*  28 */   public double mine1 = 0.0D;
/*  29 */   public double mine2 = 0.0D;
/*  30 */   public double mine3 = 0.0D;
/*  31 */   public int charge = -1;
/*  32 */   public int status = 0;
/*  33 */   public int stat = 0;
/*  34 */   public int stat2 = 0;
/*  35 */   public int laserx = 0;
/*  36 */   public int lasery = 0;
/*     */   
/*     */   public Hatman(int x, int y, Color color, int radius) {
/*  39 */     super(x, y, color, radius);
/*  40 */     this.maxtimer = -1;
/*  41 */     this.type = 1;
/*  42 */     this.charge = -1;
/*     */   }
/*     */   
/*     */   public void draw(Graphics g)
/*     */   {
/*  47 */     if (this.charge == 17) {
/*  48 */       this.anim2 += 1;
/*  49 */       if (this.anim2 >= 2) {
/*  50 */         this.anim2 = 0;
/*  51 */         this.anim1 += 1;
/*  52 */         if (this.anim1 > 14) this.anim1 = 0;
/*     */       }
/*  54 */       g.drawImage(fire[this.anim1], this.x - this.animsize / 2, this.y - this.animsize / 9 - this.animsize / 2, this.animsize, this.animsize, null);
/*  55 */       return;
/*     */     }
/*  57 */     g.setColor(this.color);
/*  58 */     if (this.charge == 15) {
/*  59 */       g.setColor(new Color(139, 137, 137));
/*     */     }
/*  61 */     if (this.charge != 5) {
                int rad = (int) this.radius;
/*  62 */       g.fillOval(this.x - rad, this.y - rad, 2 * rad, 2 * rad);
/*     */     }
/*  64 */     if (this.charge != -1)
/*     */     {
/*  66 */       if ((this.status == 3) || (this.status == 6) || (this.status == 7)) this.stat2 = 0;
/*  67 */       if ((this.status == 1) || (this.status == 4) || (this.status == 5)) { this.stat2 = 1;
/*     */       }
/*  69 */       if ((this.status == 0) || (this.status == 4) || (this.status == 6)) this.stat = 0;
/*  70 */       if ((this.status == 2) || (this.status == 5) || (this.status == 7)) { this.stat = 1;
/*     */       }
/*  72 */       if ((this.charge != 8) && (this.charge != 1)) {
/*  73 */         if (((this.charge != 6) && (this.charge != 3) && (this.charge != 0) && (this.charge != 7) && (this.charge != 10) && (this.charge != 14) && (this.charge != 15) && (this.charge != 16) && (this.charge != 18)) || ((this.stat == 1) && (this.charge != 15)) || ((this.stat == 0) && (this.charge == 15)))
/*  74 */           g.drawImage(survivalgame.DropObject.dropimages[this.charge], this.x + survivalgame.DropObject.img_param[this.charge][0], this.y + survivalgame.DropObject.img_param[this.charge][1], survivalgame.DropObject.img_param[this.charge][2], survivalgame.DropObject.img_param[this.charge][3], null); else
/*  75 */           g.drawImage(survivalgame.DropObject.dropimages[this.charge], this.x + survivalgame.DropObject.img_param[this.charge][0] + survivalgame.DropObject.img_param[this.charge][2] + survivalgame.DropObject.img_param[this.charge][4], this.y + survivalgame.DropObject.img_param[this.charge][1], -1 * survivalgame.DropObject.img_param[this.charge][2], survivalgame.DropObject.img_param[this.charge][3], null);
/*     */       }
/*  77 */       if (this.charge == 8) {
/*  78 */         if (this.stat2 == 0)
/*  79 */           g.drawImage(survivalgame.DropObject.dropimages[this.charge], this.x + survivalgame.DropObject.img_param[this.charge][0], this.y + survivalgame.DropObject.img_param[this.charge][1] + survivalgame.DropObject.img_param[this.charge][4], survivalgame.DropObject.img_param[this.charge][2], -1 * survivalgame.DropObject.img_param[this.charge][3], null); else
/*  80 */           g.drawImage(survivalgame.DropObject.dropimages[this.charge], this.x + survivalgame.DropObject.img_param[this.charge][0], this.y + survivalgame.DropObject.img_param[this.charge][1], survivalgame.DropObject.img_param[this.charge][2], survivalgame.DropObject.img_param[this.charge][3], null);
/*     */       }
/*  82 */       if (this.charge == 1) {
/*  83 */         BufferedImage abcd = new BufferedImage(survivalgame.DropObject.dropimages[this.charge].getWidth(), survivalgame.DropObject.dropimages[this.charge].getHeight(), 2);
/*  84 */         Graphics2D g3 = abcd.createGraphics();
/*  85 */         g3.drawImage(survivalgame.DropObject.dropimages[this.charge], 0, 0, survivalgame.DropObject.dropimages[this.charge].getWidth(), survivalgame.DropObject.dropimages[this.charge].getHeight(), 0, 0, survivalgame.DropObject.dropimages[this.charge].getWidth(), survivalgame.DropObject.dropimages[this.charge].getHeight(), null);
/*  86 */         if (this.lasery == 0) { if ((this.laserx < 255) && (this.laserx >= 0)) g3.setColor(new Color(30, this.laserx / 2, this.laserx, 100));
/*  87 */         } else if ((this.laserx >= 0) && (this.laserx < 247)) g3.setColor(new Color(30, (247 - this.laserx) / 2, 247 - this.laserx, 100));
/*  88 */         if ((this.laserx < 0) || (this.laserx >= 247)) g3.setColor(new Color(30, 0, 0, 100));
/*  89 */         g3.setComposite(AlphaComposite.SrcOver);
/*  90 */         g3.fillRect(0, 0, survivalgame.DropObject.dropimages[this.charge].getWidth(), survivalgame.DropObject.dropimages[this.charge].getHeight());
/*  91 */         g3.setComposite(AlphaComposite.DstIn);
/*  92 */         g3.drawImage(survivalgame.DropObject.dropimages[this.charge], 0, 0, survivalgame.DropObject.dropimages[this.charge].getWidth(), survivalgame.DropObject.dropimages[this.charge].getHeight(), 0, 0, survivalgame.DropObject.dropimages[this.charge].getWidth(), survivalgame.DropObject.dropimages[this.charge].getHeight(), null);
/*  93 */         g.drawImage(abcd, this.x + survivalgame.DropObject.img_param[this.charge][0], this.y + survivalgame.DropObject.img_param[this.charge][1], survivalgame.DropObject.img_param[this.charge][2], survivalgame.DropObject.img_param[this.charge][3], null);
/*     */       }
/*  95 */       if (this.charge == 9) {
/*  96 */         g.setColor(Color.red);
/*  97 */         g.drawLine(this.x, this.y - 9, this.laserx, this.lasery);
/*     */       }
/*  99 */       if ((this.charge == 6) && (this.remy > 0.0D)) {
/* 100 */         g.setColor(new Color(255, 255, 0, 100));
/* 101 */         if (this.stat == 1) {
/* 102 */           g.fillArc(this.x + 20 - (int)this.mine1, this.y - 10 - (int)this.mine1, (int)this.mine1 * 2, (int)this.mine1 * 2, (int)this.mine2, (int)this.mine3);
/*     */         }
/*     */         else {
/* 105 */           g.fillArc(this.x - 20 - (int)this.mine1, this.y - 10 - (int)this.mine1, (int)this.mine1 * 2, (int)this.mine1 * 2, (int)this.mine2, (int)this.mine3);
/*     */         }
/*     */       }
/* 108 */       if (this.charge == 18) {
/* 109 */         g.setColor(Color.black);
/*     */         int my;
/* 111 */         int mx;
                  if (this.stat != 0) {
/* 112 */           mx = this.x + 5;
/* 113 */           my = this.y - 35;
/*     */         } else {
/* 115 */           mx = this.x - 5;
/* 116 */           my = this.y - 35;
/*     */         }
/* 118 */         int tx = (int)Math.round(7.0D * Math.cos(3.141592653589793D * (this.anim2 % 1500) / 750.0D - 1.5707963267948966D));
/* 119 */         int ty = (int)Math.round(7.0D * Math.sin(3.141592653589793D * (this.anim2 % 1500) / 750.0D - 1.5707963267948966D));
/* 120 */         if (this.stat == 0) tx *= -1;
/* 121 */         g.drawLine(mx, my, mx + tx, my + ty);
/* 122 */         tx = (int)Math.round(5.0D * Math.cos(3.141592653589793D * this.anim2 / 9000.0D - 1.5707963267948966D));
/* 123 */         ty = (int)Math.round(5.0D * Math.sin(3.141592653589793D * this.anim2 / 9000.0D - 1.5707963267948966D));
/* 124 */         if (this.stat == 0) tx *= -1;
/* 125 */         g.drawLine(mx, my, mx + tx, my + ty);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void drawMini(Graphics g) {
/* 131 */     if (this.charge == 17) {
/* 132 */       g.drawImage(fire[this.anim1], roundMini(this.x - this.animsize / 2), roundMini(this.y - this.animsize / 9 - this.animsize / 2), 1 + roundMini(this.animsize), 1 + roundMini(this.animsize), null);
/* 133 */       return;
/*     */     }
/* 135 */     g.setColor(this.color);
/* 136 */     if (this.charge == 15) {
/* 137 */       g.setColor(new Color(139, 137, 137));
/*     */     }
/* 139 */     if (this.charge != 5) {
                int rad = (int) this.radius;
/* 140 */       g.fillOval(roundMini(this.x - rad), roundMini(this.y - rad), roundMini(2 * rad), roundMini(2 * rad));
/*     */     }
/* 142 */     if (this.charge != -1) {
/* 143 */       if ((this.status == 3) || (this.status == 6) || (this.status == 7)) this.stat2 = 0;
/* 144 */       if ((this.status == 1) || (this.status == 4) || (this.status == 5)) { this.stat2 = 1;
/*     */       }
/* 146 */       if ((this.status == 0) || (this.status == 4) || (this.status == 6)) this.stat = 0;
/* 147 */       if ((this.status == 2) || (this.status == 5) || (this.status == 7)) { this.stat = 1;
/*     */       }
/* 149 */       if ((this.charge != 8) && (this.charge != 1)) {
/* 150 */         if (((this.charge != 6) && (this.charge != 3) && (this.charge != 0) && (this.charge != 7) && (this.charge != 10) && (this.charge != 14) && (this.charge != 15) && (this.charge != 16) && (this.charge != 18)) || ((this.stat == 1) && (this.charge != 15)) || ((this.stat == 0) && (this.charge == 15)))
/* 151 */           g.drawImage(survivalgame.DropObject.dropimages[this.charge], roundMini(this.x + survivalgame.DropObject.img_param[this.charge][0]), roundMini(this.y + survivalgame.DropObject.img_param[this.charge][1]), roundMini(survivalgame.DropObject.img_param[this.charge][2]), roundMini(survivalgame.DropObject.img_param[this.charge][3]), null); else
/* 152 */           g.drawImage(survivalgame.DropObject.dropimages[this.charge], roundMini(this.x + survivalgame.DropObject.img_param[this.charge][0] + survivalgame.DropObject.img_param[this.charge][2] + survivalgame.DropObject.img_param[this.charge][4]), roundMini(this.y + survivalgame.DropObject.img_param[this.charge][1]), roundMini(-1 * survivalgame.DropObject.img_param[this.charge][2]), roundMini(survivalgame.DropObject.img_param[this.charge][3]), null);
/*     */       }
/* 154 */       if (this.charge == 8) {
/* 155 */         if (this.stat2 == 0)
/* 156 */           g.drawImage(survivalgame.DropObject.dropimages[this.charge], roundMini(this.x + survivalgame.DropObject.img_param[this.charge][0]), roundMini(this.y + survivalgame.DropObject.img_param[this.charge][1] + survivalgame.DropObject.img_param[this.charge][4]), roundMini(survivalgame.DropObject.img_param[this.charge][2]), roundMini(-1 * survivalgame.DropObject.img_param[this.charge][3]), null); else
/* 157 */           g.drawImage(survivalgame.DropObject.dropimages[this.charge], roundMini(this.x + survivalgame.DropObject.img_param[this.charge][0]), roundMini(this.y + survivalgame.DropObject.img_param[this.charge][1]), roundMini(survivalgame.DropObject.img_param[this.charge][2]), roundMini(survivalgame.DropObject.img_param[this.charge][3]), null);
/*     */       }
/* 159 */       if (this.charge == 1) {
/* 160 */         BufferedImage abcd = new BufferedImage(survivalgame.DropObject.dropimages[this.charge].getWidth(), survivalgame.DropObject.dropimages[this.charge].getHeight(), 2);
/* 161 */         Graphics2D g3 = abcd.createGraphics();
/* 162 */         g3.drawImage(survivalgame.DropObject.dropimages[this.charge], 0, 0, survivalgame.DropObject.dropimages[this.charge].getWidth(), survivalgame.DropObject.dropimages[this.charge].getHeight(), 0, 0, survivalgame.DropObject.dropimages[this.charge].getWidth(), survivalgame.DropObject.dropimages[this.charge].getHeight(), null);
/* 163 */         if (this.lasery == 0) { if ((this.laserx < 255) && (this.laserx >= 0)) g3.setColor(new Color(30, this.laserx / 2, this.laserx, 100));
/* 164 */         } else if ((this.laserx >= 0) && (this.laserx < 247)) g3.setColor(new Color(30, (247 - this.laserx) / 2, 247 - this.laserx, 100));
/* 165 */         if ((this.laserx < 0) || (this.laserx >= 247)) g3.setColor(new Color(30, 0, 0, 100));
/* 166 */         g3.setComposite(AlphaComposite.SrcOver);
/* 167 */         g3.fillRect(0, 0, survivalgame.DropObject.dropimages[this.charge].getWidth(), survivalgame.DropObject.dropimages[this.charge].getHeight());
/* 168 */         g3.setComposite(AlphaComposite.DstIn);
/* 169 */         g3.drawImage(survivalgame.DropObject.dropimages[this.charge], 0, 0, survivalgame.DropObject.dropimages[this.charge].getWidth(), survivalgame.DropObject.dropimages[this.charge].getHeight(), 0, 0, survivalgame.DropObject.dropimages[this.charge].getWidth(), survivalgame.DropObject.dropimages[this.charge].getHeight(), null);
/* 170 */         g.drawImage(abcd, roundMini(this.x + survivalgame.DropObject.img_param[this.charge][0]), roundMini(this.y + survivalgame.DropObject.img_param[this.charge][1]), roundMini(survivalgame.DropObject.img_param[this.charge][2]), roundMini(survivalgame.DropObject.img_param[this.charge][3]), null);
/*     */       }
/* 172 */       if (this.charge == 9) {
/* 173 */         g.setColor(Color.red);
/* 174 */         g.drawLine(roundMini(this.x), roundMini(this.y - 9), roundMini(this.laserx), roundMini(this.lasery));
/*     */       }
/* 176 */       if ((this.charge == 6) && (this.remy > 0.0D)) {
/* 177 */         g.setColor(new Color(255, 255, 0, 100));
/* 178 */         if (this.stat == 1) {
/* 179 */           g.fillArc(roundMini(this.x + 20 - (int)this.mine1), roundMini(this.y - 10 - (int)this.mine1), roundMini((int)this.mine1 * 2), roundMini((int)this.mine1 * 2), (int)this.mine2, (int)this.mine3);
/*     */         }
/*     */         else {
/* 182 */           g.fillArc(roundMini(this.x - 20 - (int)this.mine1), roundMini(this.y - 10 - (int)this.mine1), roundMini((int)this.mine1 * 2), roundMini((int)this.mine1 * 2), (int)this.mine2, (int)this.mine3);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Dropbox\Dropbox\Game Development\SurvivalGame v0.977\SurvivalGame.jar!\survivalgame\blocks\Hatman.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */