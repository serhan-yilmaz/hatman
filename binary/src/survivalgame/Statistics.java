/*    */ package survivalgame;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Statistics
/*    */   implements Serializable
/*    */ {
/*    */   double[] avg;
/*    */   double[] min;
/*    */   double[] max;
/*    */   int[] numofplay;
/*    */   boolean[] hatacquired;
/*    */   int[] settings;
/*    */   String[] name;
/*    */   
/*    */   public void Statistics() {}
/*    */   
/*    */   public void initialize()
/*    */   {
/* 25 */     this.avg = new double[10];
/* 26 */     this.min = new double[10];
/* 27 */     this.max = new double[10];
/* 28 */     this.numofplay = new int[10];
/* 29 */     this.hatacquired = new boolean[30];
/* 30 */     this.name = new String[10];
/*    */     
/* 32 */     for (int i = 0; i < 30; i++) this.hatacquired[i] = false;
/* 33 */     this.settings = new int[10];
/* 34 */     this.settings[0] = 70;
/* 35 */     this.settings[1] = 100;
/* 36 */     this.settings[2] = 100;
/* 37 */     this.settings[3] = 1;
/* 38 */     this.settings[4] = 1;
/* 39 */     this.settings[5] = -1;
/* 40 */     this.settings[6] = 1;
/* 41 */     this.settings[7] = 25;
/* 42 */     this.settings[8] = 1;
/* 43 */     this.settings[9] = 2;
/*    */   }
/*    */ }


/* Location:              D:\Dropbox\Dropbox\Game Development\SurvivalGame v0.977\SurvivalGame.jar!\survivalgame\Statistics.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */