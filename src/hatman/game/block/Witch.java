/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hatman.game.block;

import hatman.game.Player;
import hatman.game.ResourceManager;
import hatman.game.modifier.StatusEffects;
import hatman.mapsolver.Map;
import hatman.mapsolver.Path;
import hatman.util.Timer;
import java.awt.Color;
import sygfx.ScaledGraphics;
import sygfx.util.Anchor;

/**
 *
 * @author Serhan Yilmaz <github.com/serhan-yilmaz>
 */
public class Witch extends ConcreteBlock{
    private final static int TRIGGER_RADIUS = 141;
    private final static int EXPLOSION_RADIUS = 193;
    private final static int SLOW_RADIUS = 260;
    private final static int CHARGE_DURATİON = 51;
    private final static int EXPLOSION_DURATİON = 40;
    private final static int RAGE_STUN_DURATİON = 40;
    private final static int RAGE_DURATİON = 320;
    private final static double CHARGING_DAMAGE = 0.35;
    private final static double EXPLOSION_DAMAGE = 225;
    private final static double FLAME_DAMAGE = 1.65;
    private final static double EXPLOSION_BURN = 1;
    private final static double CHARGING_BURN = 0.015;
    private final static double FLAME_BURN = 0.03;
    private final static double RAGE_SPEED_MULTIPLER = 3;
    private final static double RAGE_CHARGE_MULTIPLER = 1.5;
    
    private final static int STATUS_IDLE = 0;
    private final static int STATUS_CHARGING = 1;
    private final static int STATUS_EXPLOSION = 2;
    private final static int STATUS_RAGING = 3;
    
    private final static Color EXPLOSION_COLOR = new Color(250, 50, 35, 194);
    
    private Hatman target;
    private Map map;
    private Player player;
    private StatusEffects statusEffects;
    private StatusEffects modifiers = new StatusEffects();
    private final Color color = new Color(128, 0, 128);
    private Timer timer = new Timer(50);
    private Timer charge_timer = new Timer(CHARGE_DURATİON);
    private Timer rage_timer = new Timer(RAGE_DURATİON);
    private int status = STATUS_IDLE;
    private boolean slow_enabled = false;
    private boolean raging = false;
    
    private double remaining_speed = 0;
    
    public Witch(double x, double y, double speed, double radius, 
            Hatman target, Map map, Player player) {
        super(x, y, speed, radius);
        this.target = target;
        this.map = map;
        this.player = player;
        this.statusEffects = player.getStatusEffects();
    }
    
    public Sparkle spawnSparkle(){
        if(status == STATUS_IDLE && Math.random() < 0.17){
            int x = (int) (this.x +  (Math.random() - 0.5) * 20);
            int y = (int) (this.y +  (Math.random() - 0.5) * 20);
            int radius = (int) (12 + Math.random() * 12);
            int sparkle_period = (int) (18 + Math.random() * 8);
            return new Sparkle(x, y, radius, sparkle_period, 80, ResourceManager.witch_sparkle);
        }
        return null;
    }

    @Override
    public void draw(ScaledGraphics sg) {
        int x = (int) this.x;
        int y = (int) this.y;
        int radius = (int) this.radius;
        sg.setAnchor(Anchor.CENTER);
        if(raging){
            sg.setColor(Color.red);
        } else {
            sg.setColor(color);
        }
        sg.fillOval(x, y, 2 * radius, 2 * radius);
//        sg.setColor(color);
        sg.drawOval(x, y, 2 * TRIGGER_RADIUS, 2 * TRIGGER_RADIUS);
        if(slow_enabled){
            sg.drawOval(x, y, 2 * SLOW_RADIUS - 4, 2 * SLOW_RADIUS - 4);
            sg.drawOval(x, y, 2 * SLOW_RADIUS, 2 * SLOW_RADIUS);
        }
        if(status == STATUS_CHARGING){
            double remain = charge_timer.getRemainingTime();
            double ratio = 1 - remain/CHARGE_DURATİON;
            int red = (int) (120 + ratio * 110);
            int alpha = (int) (101 + ratio * 88);
            sg.setColor(new Color(red, 50, 35, alpha));
            sg.fillOval(x, y, 2 * TRIGGER_RADIUS-2, 2 * TRIGGER_RADIUS-2);
        }
        if(status == STATUS_EXPLOSION){
            sg.setColor(EXPLOSION_COLOR);
            sg.fillOval(x, y, 2 * EXPLOSION_RADIUS, 2 * EXPLOSION_RADIUS);
        }
        sg.drawImage(ResourceManager.witch_hat, x + 1, y - 17, 50, 50, null);
    }

    @Override
    public boolean cycle() {
        modifiers.cycle();
        if(rage_timer.cycle()){
            raging = false;
        }
        if(timer.cycle()){
            if(map.reserve()){
                map.requestFullPath();
                Path path = map.solve((int) x, (int) y, (int) target.x, (int) target.y);
                if(path != null){
                    path.poll();
                    this.move(path);
                }
                map.release();
            }
        }
        if(slow_enabled){
            if(this.isTargetReached(target, SLOW_RADIUS)){
                statusEffects.refreshWitchSlow(2);
            }
        }
        switch(status){
            case STATUS_IDLE:
                if(this.isTargetReached(target, TRIGGER_RADIUS)){
                    status = STATUS_CHARGING;
                    charge_timer.reset();
                    charge_timer.setPeriod(CHARGE_DURATİON);
                } else {
                    double speed = this.speed * modifiers.getMovementModifier();
                    if(raging){
                        speed *= RAGE_SPEED_MULTIPLER;
                    }
                    remaining_speed += speed;
                    if(Math.random() < 0.4 + 0.2 * remaining_speed/this.speed){
                        remaining_speed = moveOnPath(this.path, remaining_speed);
                    }
                }
                break;
            case STATUS_CHARGING:
                double charge_speed = 1;
                if(raging){
                    charge_speed = RAGE_CHARGE_MULTIPLER;
                }
                if(charge_timer.cycle(charge_speed)){
                    status = STATUS_EXPLOSION;
                    charge_timer.reset();
                    charge_timer.setPeriod(EXPLOSION_DURATİON);
                    if(this.isTargetReached(target, EXPLOSION_RADIUS)){
                        player.damage(EXPLOSION_DAMAGE);
                        statusEffects.inflictFlame(EXPLOSION_BURN);
                    }
                } else {
                    if(this.isTargetReached(target, TRIGGER_RADIUS)){
                        player.damage(CHARGING_DAMAGE);
                        statusEffects.inflictFlame(CHARGING_BURN);
                    }
                }
                break;
            case STATUS_EXPLOSION:
                if(this.isTargetReached(target, EXPLOSION_RADIUS)){
                    player.damage(FLAME_DAMAGE);
                    statusEffects.inflictFlame(FLAME_BURN);
                }
                if(charge_timer.cycle()){
                    status = STATUS_IDLE;
                }
                break;
            case STATUS_RAGING:
                if(charge_timer.cycle()){
                    status = STATUS_IDLE;
                }
                break;
            default:
                throw new RuntimeException("Invalid status.");
        }
        return false;
    }
    
    public void setWitchSlow(boolean b){
        slow_enabled = b;
    }
    
    public void rageWitch(){
        raging = true;
        rage_timer.reset();
        status = STATUS_CHARGING;
        charge_timer.reset();
        charge_timer.setPeriod(CHARGE_DURATİON);
//        modifiers.refreshStun(RAGE_STUN_DURATİON);
    }

    @Override
    public Witch clone() {
        return new Witch(x, y, speed, radius, target, map, player);
    }
    
    public StatusEffects getModifiers(){
        return modifiers;
    }
    
}
