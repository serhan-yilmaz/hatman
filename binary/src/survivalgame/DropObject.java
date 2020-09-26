package survivalgame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class DropObject
{
  public static BufferedImage[] dropimages = new BufferedImage[30];
  public static int[][] img_param = new int[30][5];
  int x;
  int y;
  int width;
  int height;
  int imagenum;
  
  public DropObject(int x, int y, int width, int height, int imagenum)
  {
    this.x = x;this.y = y;this.width = width;this.height = height;this.imagenum = imagenum;
  }
  
  public void draw(Graphics g, int scrx, int scry)
  {
    int x = this.x * scrx / 1280;int y = this.y * scry / 720;
    int width = this.width * scrx / 1280;
    int height = this.height * scry / 720;
    g.setColor(new Color(218, 218, 218));
    
    g.fillRect(x, y, width, height);
    g.setColor(Color.black);
    g.drawRect(x, y, width, height);
    if (this.imagenum >= 0)
    {
      g.drawImage(dropimages[this.imagenum], x + 3, y + 3, width - 6, height - 6, null);
    }
    else
    {
      g.setFont(new Font("Arial", 0, 14 * scrx / 1280));
      g.drawString("No Hat", x + 10 * scrx / 1280, y + height / 2 + 4 * scry / 720);
    }
  }
  
  public void draw(Graphics g, int scrx, int scry, int myx, int myy)
  {
    int x = myx * scrx / 1280;int y = myy * scry / 720;
    int width = this.width * scrx / 1280;
    int height = this.height * scry / 720;
    g.setColor(new Color(218, 218, 218));
    
    g.fillRect(x, y, width, height);
    g.setColor(Color.black);
    g.drawRect(x, y, width, height);
    if (this.imagenum >= 0)
    {
      g.drawImage(dropimages[this.imagenum], x + 3, y + 3, width - 6, height - 6, null);
    }
    else
    {
      g.setFont(new Font("Arial", 0, 14 * scrx / 1280));
      g.drawString("No Hat", x + 10 * scrx / 1280, y + height / 2 + 4 * scry / 720);
    }
  }
}
