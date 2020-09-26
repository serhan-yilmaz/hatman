/*    */ package survivalgame;
/*    */ 
/*    */ 
/*    */ public class Parameters
/*    */ {
           public static final int BLACK_BULLET_UP0_DAMAGE = 385;
           public static final int BLACK_BULLET_UP0_FREQUENCY = 352;
           public static final int BLACK_BULLET_UP0_RADIUS = 20;
           public static final double BLACK_BULLET_UP0_SPEED = 15.3D;
            
/*    */   public static final int BLACK_BULLET_UP1_DAMAGE = 435;
/*    */   public static final int BLACK_BULLET_UP1_FREQUENCY = 307;
/*    */   public static final int BLACK_BULLET_UP1_RADIUS = 30;
/*    */   public static final double BLACK_BULLET_UP1_SPEED = 19.05D;

           public static final int RED_BALL_UP0_RADIUS = 10;
           public static final int RED_BALL_UP0_SPAWN_PERIOD = 300;
           public static final double RED_BALL_UP0_SPEED = 3.52D;
           
/*    */   public static final double RED_BALL_UP1_SPEED = 4.09D;

/*    */   public static final double RED_BALL_UP2_SPEED = 4.64D;

           public static final int RED_BALL_UP3_SPAWN_PERIOD = 210;
           public static final int RED_BALL_UP3_RADIUS = 10;
           public static final double RED_BALL_UP3_SPEED = 4.84D;
/*    */   
/*    */   public static int black_bullet_damage;
/*    */   public static int black_bullet_frequency;
/*    */   public static int black_bullet_radius;
/*    */   public static double black_bullet_speed;
/*    */   public static double red_ball_speed;
           public static int red_ball_radius;
           public static int red_ball_period;
/*    */   public static boolean mine_upgraded;
           public static boolean red_ball_merge;
/*    */ 
/*    */   public static void initialize()
/*    */   {
/* 33 */     black_bullet_damage = BLACK_BULLET_UP0_DAMAGE;
/* 34 */     black_bullet_frequency = BLACK_BULLET_UP0_FREQUENCY;
/* 35 */     black_bullet_radius = BLACK_BULLET_UP0_RADIUS;
/* 36 */     black_bullet_speed = BLACK_BULLET_UP0_SPEED;
/*    */     
/* 38 */     red_ball_speed = RED_BALL_UP0_SPEED;
             red_ball_radius = RED_BALL_UP0_RADIUS;
             red_ball_period = RED_BALL_UP0_SPAWN_PERIOD;
/*    */     
/* 40 */     mine_upgraded = false;
             red_ball_merge = false;
/*    */   }
/*    */ }


/* Location:              D:\Dropbox\Dropbox\Game Development\SurvivalGame v0.977\SurvivalGame.jar!\survivalgame\Parameters.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */