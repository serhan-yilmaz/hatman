/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hatman.game.block;

import java.awt.Color;
import sygfx.ScaledGraphics;
import sygfx.util.Anchor;

/**
 *
 * @author Serhan Yilmaz <github.com/serhan-yilmaz>
 */
public class Visual extends Block{

    public Visual(double x, double y, double radius) {
        super(x, y, 0, radius);
    }

    @Override
    public void draw(ScaledGraphics sg) {
        int x = (int) this.x;
        int y = (int) this.y;
        int radius = (int) this.radius;
        sg.setAnchor(Anchor.CENTER);
        sg.setColor(Color.MAGENTA);
        sg.fillOval(x, y, 2 * radius, 2 * radius);
    }

    @Override
    public boolean cycle() {
        return false;
    }

    @Override
    public Block clone() {
        return new Visual(x, y, radius);
    }
    
}
