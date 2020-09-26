package survivalgame;

import java.awt.Color;
import java.awt.Graphics;

public class DropList
{
  static final int MAX_ITEMS = 25;
  static final int MAX_HAT = 19;
  DropObject[] dropobject = new DropObject[25];
  int dropobjectc = 0;
  int x;
  int y;
  int width;
  int height;
  boolean displayed;
  boolean enabled;
  boolean selected;
  int current = 0;
  
  public DropList(int x, int y, int width, int height)
  {
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
    this.displayed = false;
    this.enabled = true;
    this.selected = false;
  }
  
  public void draw(Graphics g, int scrx, int scry)
  {
    if ((this.displayed) && (this.enabled))
    {
      if (this.selected)
      {
        for (int i = 0; i < this.dropobjectc; i++) {
          this.dropobject[i].draw(g, scrx, scry);
        }
        g.drawRect((this.dropobject[this.current].x + 2) * scrx / 1280, (this.dropobject[this.current].y + 2) * scry / 720, (this.dropobject[this.current].width - 4) * scrx / 1280, (this.dropobject[this.current].height - 4) * scry / 720);
      }
      else
      {
        this.dropobject[this.current].draw(g, scrx, scry, this.x, this.y);g.drawRect((this.x + 2) * scrx / 1280, (this.y + 2) * scry / 720, (this.width - 4) * scrx / 1280, (this.height - 4) * scry / 720);
      }
      g.setColor(Color.black);
    }
  }
  
  public boolean isClicked(int mx, int my)
  {
    if ((this.enabled) && (this.displayed))
    {
      if (this.selected) {
        for (int i = 0; i < this.dropobjectc; i++) {
          if (mx >= this.dropobject[i].x) {
            if ((((mx < this.dropobject[i].x + this.width ? 1 : 0) & (my >= this.dropobject[i].y ? 1 : 0)) != 0) && (my < this.dropobject[i].y + this.height)) {
              return true;
            }
          }
        }
      } else {
        return (mx >= this.x) && (mx < this.x + this.width) && (my >= this.y) && (my < this.y + this.height);
      }
    }
    else {
      return false;
    }
    return false;
  }
  
  public void setClicked(int mx, int my)
  {
    if (this.selected) {
      for (int i = 0; i < this.dropobjectc; i++) {
        if (mx >= this.dropobject[i].x) {
          if ((((mx < this.dropobject[i].x + this.width ? 1 : 0) & (my >= this.dropobject[i].y ? 1 : 0)) != 0) && (my < this.dropobject[i].y + this.height))
          {
            this.selected = false;this.current = i;
          }
        }
      }
    } else {
      this.selected = true;
    }
  }
  
  public void addObject(int imagenum)
  {
    if (this.dropobjectc < 25) {
      this.dropobject[(this.dropobjectc++)] = new DropObject(this.x + this.width * ((this.dropobjectc - 1) / 5), this.y + this.height * ((this.dropobjectc - 1) % 5), this.width, this.height, imagenum);
    }
  }
}
