/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hatman.game.modifier;

import sygfx.ScaledGraphics;

/**
 *
 * @author Serhan Yilmaz <github.com/serhan-yilmaz>
 */
public interface Modifier {
    public int draw(ScaledGraphics g, int x, int y);
    public void reset();
    public void cycle();
}
