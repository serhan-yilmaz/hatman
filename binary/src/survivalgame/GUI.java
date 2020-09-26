package survivalgame;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.PrintStream;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import survivalgame.blocks.Hatman;

public class GUI
{
  protected JFrame f;
  protected final SurviveBoard a = new SurviveBoard();
  private PaintThread paintThread = new PaintThread(a);
  static GraphicsDevice device = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0];
  protected boolean ready2go;
  
  protected void initializeButtons()
  {
    this.a.button[(this.a.buttonc++)] = new Button(560, 250, 160, 40, new Color(150, 75, 0), "Start");
    this.a.button[(this.a.buttonc++)] = new Button(380, 550, 160, 40, new Color(150, 75, 0), "Easy");
    this.a.button[(this.a.buttonc++)] = new Button(560, 550, 160, 40, new Color(150, 75, 0), "Medium");
    this.a.button[this.a.buttonc] = new Button(740, 550, 160, 40, new Color(150, 75, 0), "Hard");this.a.button[(this.a.buttonc++)].selected = true;
    this.a.button[(this.a.buttonc++)] = new Button(560, 300, 160, 40, new Color(150, 75, 0), "Profile");
    this.a.button[(this.a.buttonc++)] = new Button(560, 350, 160, 40, new Color(150, 75, 0), "Settings");
    this.a.button[(this.a.buttonc++)] = new Button(480, 620, 320, 40, new Color(150, 75, 0), "Return to the Main Menu", false);
    this.a.button[(this.a.buttonc++)] = new Button(470, 415, 160, 40, new Color(150, 75, 0), "OK", false);
    this.a.button[(this.a.buttonc++)] = new Button(650, 415, 160, 40, new Color(150, 75, 0), "Cancel", false);
    this.a.button[(this.a.buttonc++)] = new Button(560, 250, 160, 40, new Color(150, 75, 0), "Game Settings", false);
    this.a.button[(this.a.buttonc++)] = new Button(560, 300, 160, 40, new Color(150, 75, 0), "Sound Settings", false);
    this.a.button[(this.a.buttonc++)] = new Button(560, 485, 160, 40, new Color(150, 75, 0), "Return", false);
    
    this.a.button[(this.a.buttonc++)] = new Button(690, 260, 60, 40, new Color(150, 75, 0), "Yes", "No", false);
    this.a.button[(this.a.buttonc++)] = new Button(690, 305, 60, 40, new Color(150, 75, 0), "Yes", "No", false);
    
    this.a.button[(this.a.buttonc++)] = new Button(560, 350, 160, 40, new Color(150, 75, 0), "Continue", false);
    this.a.button[(this.a.buttonc++)] = new Button(560, 400, 160, 40, new Color(150, 75, 0), "Main Menu", false);
    this.a.button[(this.a.buttonc++)] = new Button(560, 450, 160, 40, new Color(150, 75, 0), "Quit", false);
    
    this.a.button[(this.a.buttonc++)] = new Button(470, 415, 160, 40, new Color(150, 75, 0), "Yes", false);
    this.a.button[(this.a.buttonc++)] = new Button(650, 415, 160, 40, new Color(150, 75, 0), "No", false);
    
    this.a.button[(this.a.buttonc++)] = new Button(560, 450, 160, 40, new Color(150, 75, 0), "Quit");
    
    this.a.button[(this.a.buttonc++)] = new Button(560, 400, 160, 40, new Color(150, 75, 0), "Tutorial");this.a.button[(this.a.buttonc - 1)].enabled = false;
    
    this.a.button[(this.a.buttonc++)] = new Button(560, 250, 160, 40, new Color(150, 75, 0), "High Scores", false);
    
    this.a.button[(this.a.buttonc++)] = new Button(560, 300, 160, 40, new Color(150, 75, 0), "Statistics", false);
    
    this.a.button[(this.a.buttonc++)] = new Button(560, 350, 160, 40, new Color(150, 75, 0), "Hall of Hats", false);
    
    this.a.button[(this.a.buttonc++)] = new Button(480, 620, 320, 40, new Color(150, 75, 0), "Return to the Profile", false);
    
    this.a.button[(this.a.buttonc++)] = new Button(690, 215, 80, 40, new Color(150, 75, 0), "Mouse", "Keyboard", false);
    
    this.a.button[(this.a.buttonc++)] = new Button(690, 350, 60, 40, new Color(150, 75, 0), "Yes", "No", false);
    
    this.a.slider[(this.a.sliderc++)] = new Slider(660, 200, 160, 40, new Color(100, 50, 0), false, 70);
    this.a.slider[(this.a.sliderc++)] = new Slider(660, 250, 160, 40, new Color(100, 50, 0), false, 100);
    this.a.slider[(this.a.sliderc++)] = new Slider(660, 300, 160, 40, new Color(100, 50, 0), false, 100);
    this.a.slider[(this.a.sliderc++)] = new Slider(660, 170, 160, 40, new Color(100, 50, 0), false, 100);
    
    this.a.textfield = new TextField(460, 350, 360, 40, new Color(245, 245, 245), "");
    
    this.a.droplist = new DropList(690, 395, 60, 40);this.a.droplist.addObject(-1);
  }
  
  private void exit()
  {
    this.a.write2file();
    System.exit(0);
  }
  
  private void return2Menu()
  {
    this.a.droplist.displayed = false;this.a.gamestatus = -2;
    for (int j = 6; j < this.a.buttonc; j++) {
      this.a.button[j].displayed = false;
    }
    for (int j = 0; j < 6; j++) {
      this.a.button[j].displayed = true;
    }
    this.a.button[19].displayed = true;this.a.button[20].displayed = true;
  }
  
  protected void giveDatas()
  {
    for (int i = 0; i < 3; i++) {
      this.a.slider[i].position = this.a.statistic.settings[i];
    }
    this.a.slider[3].position = this.a.statistic.settings[7];
    this.a.button[12].value = (this.a.statistic.settings[3] == 1);
    this.a.button[13].value = (this.a.statistic.settings[4] == 1);
    this.a.button[25].value = (this.a.statistic.settings[6] == 1);
    this.a.button[26].value = (this.a.statistic.settings[8] == 1);
    this.a.mastervolume = this.a.slider[0].position;
    this.a.musicvolume = this.a.slider[1].position;
    this.a.soundvolume = this.a.slider[2].position;
    this.a.showminimap = (this.a.statistic.settings[3] == 1);
    this.a.showtrace = (this.a.statistic.settings[4] == 1);
    this.a.difficulty = this.a.statistic.settings[9];
    for (int i = 1; i <= 3; i++) {
      this.a.button[i].selected = false;
    }
    this.a.button[(this.a.difficulty + 1)].selected = true;
    if (this.a.statistic.name[0] != null) {
      this.a.textfield.text = this.a.statistic.name[0];
    }
    for (int i = 0; (i < 30) && (i < 19); i++) {
      if (this.a.statistic.hatacquired[i]) {
        this.a.droplist.addObject(i);
      }
    }
    for (int i = 0; (i < this.a.droplist.dropobjectc) && (i <= 19); i++) {
      if (this.a.droplist.dropobject[i].imagenum == this.a.statistic.settings[5])
      {
        this.a.droplist.current = i;this.a.hatman.charge = this.a.statistic.settings[5];
      }
    }
  }
  
  protected void createAndShowGUI()
  {
    System.out.println("Created GUI on EDT? " + 
      SwingUtilities.isEventDispatchThread());
    this.f = new JFrame("Survival Game Project");
    this.f.setDefaultCloseOperation(3);
    this.f.setSize(800, 800);
    this.f.setResizable(false);
    this.f.setUndecorated(true);
    this.f.setExtendedState(6);
    if (device.isFullScreenSupported()) {
      device.setFullScreenWindow(this.f);
    }
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    int width = (int)screenSize.getWidth();
    int height = (int)screenSize.getHeight();
    this.f.setPreferredSize(new Dimension(width, height));
    initializeButtons();
    this.f.addComponentListener(new ComponentAdapter()
    {
      public void componentResized(ComponentEvent ce)
      {
        GUI.this.a.scrx = GUI.this.f.getContentPane().getWidth();
        GUI.this.a.scry = GUI.this.f.getContentPane().getHeight();
      }
    });
    this.f.addFocusListener(new FocusListener()
    {
      public void focusGained(FocusEvent fe)
      {
        if ((GUI.this.a.gamestatus == 3) && (GUI.this.a.stopped) && 
          (GUI.this.a.statistic.settings[8] == 1))
        {
          GUI.this.a.stopped = false;
          GUI.this.a.gamestatus = 0;GUI.this.a.button[9].displayed = false;GUI.this.a.button[10].displayed = false;
          GUI.this.a.button[14].displayed = false;GUI.this.a.button[15].displayed = false;GUI.this.a.button[16].displayed = false;
        }
      }
      
      public void focusLost(FocusEvent fe)
      {
        if ((GUI.this.a.gamestatus == 0) && 
          (GUI.this.a.statistic.settings[8] == 1))
        {
          GUI.this.a.stopped = true;
          GUI.this.a.gamestatus = 3;GUI.this.a.button[9].displayed = true;GUI.this.a.button[10].displayed = true;
          GUI.this.a.button[14].displayed = true;GUI.this.a.button[15].displayed = true;GUI.this.a.button[16].displayed = true;
        }
      }
    });
    this.f.addKeyListener(new KeyListener()
    {
      public void keyTyped(KeyEvent ke) {}
      
      public void keyPressed(KeyEvent ke)
      {
        char s = ke.getKeyChar();
        int ins = s;
        if (((s == 'r') || (s == 'R')) && (
          (GUI.this.a.gamestatus == 0) || (GUI.this.a.gamestatus == 1))) {
          GUI.this.a.reset();
        }
        if (((s == 'h') || (s == 'H')) && 
          (GUI.this.a.gamestatus == -8))
        {
          GUI.this.a.gamestatus = -9;GUI.this.a.button[6].displayed = false;GUI.this.a.button[24].displayed = true;
          for (int j = 0; j < 6; j++) {
            GUI.this.a.button[j].displayed = false;
          }
          for (int j = 18; j < 24; j++) {
            GUI.this.a.button[j].displayed = false;
          }
        }
        if ((GUI.this.a.gamestatus >= 0) && (GUI.this.a.gamestatus != 2) && 
          (GUI.this.a.statistic.settings[6] == 0))
        {
          if ((s == 'w') || (s == 'W') || (ke.getKeyCode() == 38)) {
            GUI.this.a.gye = 2;
          }
          if ((s == 'd') || (s == 'D') || (ke.getKeyCode() == 39)) {
            GUI.this.a.gxe = 1;
          }
          if ((s == 's') || (s == 'S') || (ke.getKeyCode() == 40)) {
            GUI.this.a.gye = 1;
          }
          if ((s == 'a') || (s == 'A') || (ke.getKeyCode() == 37)) {
            GUI.this.a.gxe = 2;
          }
        }
        if (GUI.this.a.gamestatus == 2)
        {
          if ((!ke.isActionKey()) && (((ins >= 32) && (ins <= 126)) || (ins == 305) || (ins == 287) || (ins == 257) || (ins == 351) || (ins == 246) || (ins == 231))) {
            GUI.this.a.textfield.write(s);
          }
          if (ke.getKeyCode() == 8) {
            GUI.this.a.textfield.delete();
          }
        }
        if (ke.getKeyCode() == 27)
        {
          if (GUI.this.a.gamestatus == 0)
          {
            GUI.this.a.gamestatus = 3;GUI.this.a.button[9].displayed = true;GUI.this.a.button[10].displayed = true;
            GUI.this.a.button[14].displayed = true;GUI.this.a.button[15].displayed = true;GUI.this.a.button[16].displayed = true;
          }
          else if (GUI.this.a.gamestatus == 3)
          {
            GUI.this.a.gamestatus = 0;GUI.this.a.button[9].displayed = false;GUI.this.a.button[10].displayed = false;
            GUI.this.a.button[14].displayed = false;GUI.this.a.button[15].displayed = false;GUI.this.a.button[16].displayed = false;
          }
          if ((GUI.this.a.gamestatus == 5) || (GUI.this.a.gamestatus == 6) || (GUI.this.a.gamestatus == -5) || (GUI.this.a.gamestatus == -6))
          {
            for (int j = 11; j < GUI.this.a.buttonc; j++) {
              GUI.this.a.button[j].displayed = false;
            }
            for (int j = 0; j < 4; j++) {
              GUI.this.a.slider[j].displayed = false;
            }
            switch (GUI.this.a.paramreturn)
            {
            case 0: 
            case 1: 
              GUI.this.a.gamestatus = -4;GUI.this.a.button[6].displayed = true;
              for (int j = 9; j < 11; j++) {
                GUI.this.a.button[j].displayed = true;
              }
              break;
            case 2: 
            case 3: 
              GUI.this.a.gamestatus = 3;
              for (int j = 9; j < 11; j++) {
                GUI.this.a.button[j].displayed = true;
              }
              for (int j = 14; j < 17; j++) {
                GUI.this.a.button[j].displayed = true;
              }
            }
            GUI.this.a.droplist.displayed = false;
          }
          else
          {
            if ((GUI.this.a.gamestatus == -4) || (GUI.this.a.gamestatus == -8)) {
              GUI.this.return2Menu();
            }
            if ((GUI.this.a.gamestatus == -7) || (GUI.this.a.gamestatus == -1) || (GUI.this.a.gamestatus == -9))
            {
              GUI.this.a.gamestatus = -8;
              for (int j = 0; j < GUI.this.a.buttonc; j++) {
                GUI.this.a.button[j].displayed = false;
              }
              GUI.this.a.button[6].displayed = true;
              for (int j = 21; j < 24; j++) {
                GUI.this.a.button[j].displayed = true;
              }
            }
            if (GUI.this.a.gamestatus == 1) {
              GUI.this.return2Menu();
            }
          }
        }
      }
      
      public void keyReleased(KeyEvent ke)
      {
        char s = ke.getKeyChar();
        if (((GUI.this.a.gxe != 0) || (GUI.this.a.gye != 0)) && 
          (GUI.this.a.statistic.settings[6] == 0))
        {
          if (((s == 'w') || (s == 'W') || (ke.getKeyCode() == 38)) && (GUI.this.a.gye == 2)) {
            GUI.this.a.gye = 0;
          }
          if (((s == 'd') || (s == 'D') || (ke.getKeyCode() == 39)) && (GUI.this.a.gxe == 1)) {
            GUI.this.a.gxe = 0;
          }
          if (((s == 's') || (s == 'S') || (ke.getKeyCode() == 40)) && (GUI.this.a.gye == 1)) {
            GUI.this.a.gye = 0;
          }
          if (((s == 'a') || (s == 'A') || (ke.getKeyCode() == 37)) && (GUI.this.a.gxe == 2)) {
            GUI.this.a.gxe = 0;
          }
        }
      }
    });
    this.f.addMouseMotionListener(new MouseMotionListener()
    {
      public void mouseDragged(MouseEvent me)
      {
        int ax = GUI.this.f.getContentPane().getWidth() - GUI.this.f.getWidth();
        int ay = GUI.this.f.getContentPane().getHeight() - GUI.this.f.getHeight();
        int bx = me.getX() + ax / 2;int by = me.getY() + ay / 2;bx = bx * 1280 / GUI.this.a.scrx;by = by * 720 / GUI.this.a.scry;
        if ((GUI.this.a.mapmouse) && (GUI.this.a.showminimap)) {
          if ((bx >= 1026) && (by >= 576))
          {
            GUI.this.a.gx = ((bx - 1090) * 10);
            GUI.this.a.gy = ((by - 612) * 10);
          }
        }
        for (int i = 0; i < GUI.this.a.sliderc; i++) {
          if (GUI.this.a.slider[i].selected)
          {
            GUI.this.a.slider[i].setSliderPosition(bx, by, GUI.this.a.scrx, GUI.this.a.scry);
            if (i == 0) {
              GUI.this.a.mastervolume = GUI.this.a.slider[i].position;
            }
            if (i == 1) {
              GUI.this.a.musicvolume = GUI.this.a.slider[i].position;
            }
            if (i == 2) {
              GUI.this.a.soundvolume = GUI.this.a.slider[i].position;
            }
            if (i == 3) {
              GUI.this.a.statistic.settings[7] = GUI.this.a.slider[i].position;
            }
          }
        }
      }
      
      public void mouseMoved(MouseEvent me)
      {
        if (GUI.this.a.gamestatus >= 0)
        {
          if (GUI.this.a.statistic.settings[6] == 1)
          {
            if (me.getX() >= GUI.this.a.scrx - 20) {
              GUI.this.a.gxe = 1;
            } else if (me.getX() <= 20) {
              GUI.this.a.gxe = 2;
            } else {
              GUI.this.a.gxe = 0;
            }
            if (me.getY() >= GUI.this.a.scry - 20) {
              GUI.this.a.gye = 1;
            } else if (me.getY() <= 20) {
              GUI.this.a.gye = 2;
            } else {
              GUI.this.a.gye = 0;
            }
          }
        }
        else
        {
          GUI.this.a.gxe = 0;GUI.this.a.gye = 0;
        }
      }
    });
    this.f.addMouseListener(new MouseListener()
    {
      public void mouseClicked(MouseEvent me) {}
      
      public void mousePressed(MouseEvent me)
      {
        if ((me.getButton() == 3) && (GUI.this.a.gamestatus != -3) && 
          (GUI.this.a.clickready == 0))
        {
          GUI.this.a.clickready = 6;
          int ax = GUI.this.f.getContentPane().getWidth() - GUI.this.f.getWidth();
          int ay = GUI.this.f.getContentPane().getHeight() - GUI.this.f.getHeight();
          GUI.this.a.setTarget(me.getX() + ax, me.getY() + ay);
          GUI.this.a.map.finx = (((me.getX() + ax / 2) * 1280 / GUI.this.a.scrx + GUI.this.a.gx) / 5);GUI.this.a.map.finy = (((me.getY() + ay / 2) * 720 / GUI.this.a.scry + GUI.this.a.gy) / 5);
          GUI.this.a.player.finx = GUI.this.a.map.finx;GUI.this.a.player.finy = GUI.this.a.map.finy;
          if (GUI.this.a.map.ready)
          {
            long as = System.nanoTime();
            GUI.this.a.map.reset();
            long bs = System.nanoTime();System.out.println("reset : " + (bs - as) / 1000L);
            as = System.nanoTime();
            
            GUI.this.a.map.stx = (GUI.this.a.hatman.x / 5);
            GUI.this.a.map.sty = (GUI.this.a.hatman.y / 5);
            
            GUI.this.a.map.solve5();
            
            bs = System.nanoTime();System.out.println("Time : " + (bs - as) / 1000L + " us");
            as = System.nanoTime();
            if (GUI.this.a.map.retrace(GUI.this.a.hatman.x / 5, GUI.this.a.hatman.y / 5))
            {
              GUI.this.a.curroad = 0;
              GUI.this.a.player.aroadl2 = GUI.this.a.map.aroadl2;
              for (int i = 0; i < GUI.this.a.player.aroadl2; i++)
              {
                GUI.this.a.player.aroadk2[i] = GUI.this.a.map.aroadk2[i];GUI.this.a.player.aroadx2[i] = GUI.this.a.map.aroadx2[i];GUI.this.a.player.aroady2[i] = GUI.this.a.map.aroady2[i];
              }
            }
          }
        }
        if (me.getButton() == 1)
        {
          int ax = GUI.this.f.getContentPane().getWidth() - GUI.this.f.getWidth();
          int ay = GUI.this.f.getContentPane().getHeight() - GUI.this.f.getHeight();
          int bx = me.getX() + ax / 2;int by = me.getY() + ay / 2;bx = bx * 1280 / GUI.this.a.scrx;by = by * 720 / GUI.this.a.scry;
          if (GUI.this.a.droplist.isClicked(bx, by))
          {
            GUI.this.a.droplist.setClicked(bx, by);GUI.this.a.hatman.charge = GUI.this.a.droplist.dropobject[GUI.this.a.droplist.current].imagenum;return;
          }
          GUI.this.a.droplist.selected = false;
          for (int i = 0; i < GUI.this.a.sliderc; i++)
          {
            GUI.this.a.slider[i].setSliderPosition(bx, by, GUI.this.a.scrx, GUI.this.a.scry);
            if (GUI.this.a.slider[i].selected)
            {
              if (i == 0) {
                GUI.this.a.mastervolume = GUI.this.a.slider[i].position;
              }
              if (i == 1) {
                GUI.this.a.musicvolume = GUI.this.a.slider[i].position;
              }
              if (i == 2) {
                GUI.this.a.soundvolume = GUI.this.a.slider[i].position;
              }
              if (i == 3) {
                GUI.this.a.statistic.settings[7] = GUI.this.a.slider[i].position;
              }
            }
          }
          if (GUI.this.a.gamestatus == -9) {
            for (int i = 0; i < 19; i++)
            {
              int x = 150 + i % 9 * 105;int y = 100 + i / 9 * 100;
              int width = 80;int height = 80;
              if (bx >= x) {
                if ((((bx < x + width ? 1 : 0) & (by >= y ? 1 : 0)) != 0) && (by < y + height)) {
                  GUI.this.a.hallofhatselect = i;
                }
              }
            }
          }
          for (int i = 0; i < GUI.this.a.buttonc; i++)
          {
            if (GUI.this.a.button[i].istogglebutton)
            {
              GUI.this.a.button[i].toggle(bx, by);
              if (GUI.this.a.button[i].isClicked(bx, by))
              {
                if (i == 12)
                {
                  GUI.this.a.mapmouse = false;GUI.this.a.showminimap = (!GUI.this.a.showminimap);
                }
                if (i == 13) {
                  GUI.this.a.showtrace = (!GUI.this.a.showtrace);
                }
                if (i == 25) {
                  GUI.this.a.statistic.settings[6] = (GUI.this.a.statistic.settings[6] == 1 ? 0 : 1);
                }
                if (i == 26) {
                  GUI.this.a.statistic.settings[8] = (GUI.this.a.statistic.settings[8] == 1 ? 0 : 1);
                }
              }
            }
            if (GUI.this.a.button[i].isClicked(bx, by))
            {
              if ((i > 0) && (i < 4))
              {
                GUI.this.a.difficulty = (i - 1);GUI.this.a.statistic.settings[9] = (i - 1);
              }
              if ((GUI.this.a.gamestatus != 7) || (i == 17) || (i == 18)) {
                switch (i)
                {
                case 0: 
                  GUI.this.a.button[0].selected = true; break;
                case 1: 
                  GUI.this.a.button[1].selected = true;GUI.this.a.button[2].selected = false;GUI.this.a.button[3].selected = false; break;
                case 2: 
                  GUI.this.a.button[1].selected = false;GUI.this.a.button[2].selected = true;GUI.this.a.button[3].selected = false; break;
                case 3: 
                  GUI.this.a.button[1].selected = false;GUI.this.a.button[2].selected = false;GUI.this.a.button[3].selected = true; break;
                case 4: 
                  GUI.this.a.button[4].selected = true; break;
                case 5: 
                  GUI.this.a.button[5].selected = true; break;
                case 6: 
                  GUI.this.a.button[6].selected = true; break;
                case 7: 
                  GUI.this.a.button[i].selected = true; break;
                case 8: 
                  GUI.this.a.button[i].selected = true; break;
                case 9: 
                  GUI.this.a.button[i].selected = true; break;
                case 10: 
                  GUI.this.a.button[i].selected = true; break;
                case 11: 
                  GUI.this.a.button[i].selected = true; break;
                case 14: 
                  GUI.this.a.button[i].selected = true; break;
                case 15: 
                  GUI.this.a.button[i].selected = true; break;
                case 16: 
                  GUI.this.a.button[i].selected = true; break;
                case 17: 
                  GUI.this.a.button[i].selected = true; break;
                case 18: 
                  GUI.this.a.button[i].selected = true; break;
                case 19: 
                  GUI.this.a.button[i].selected = true; break;
                case 20: 
                  GUI.this.a.button[i].selected = true; break;
                case 21: 
                  GUI.this.a.button[i].selected = true; break;
                case 22: 
                  GUI.this.a.button[i].selected = true; break;
                case 23: 
                  GUI.this.a.button[i].selected = true; break;
                case 24: 
                  GUI.this.a.button[i].selected = true;
                }
              }
            }
          }
          if (((GUI.this.a.gamestatus == 0) || (GUI.this.a.gamestatus == 1) || (GUI.this.a.gamestatus >= 3)) && 
            (bx >= 1026) && (by >= 576) && (GUI.this.a.showminimap))
          {
            GUI.this.a.mapmouse = true;
            GUI.this.a.gx = ((bx - 1090) * 10);
            GUI.this.a.gy = ((by - 612) * 10);
          }
        }
      }
      
      public void mouseReleased(MouseEvent me)
      {
        if ((me.getButton() == 1) && (GUI.this.a.gamestatus != -3))
        {
          int ax = GUI.this.f.getContentPane().getWidth() - GUI.this.f.getWidth();
          int ay = GUI.this.f.getContentPane().getHeight() - GUI.this.f.getHeight();
          int bx = me.getX() + ax / 2;int by = me.getY() + ay / 2;bx = bx * 1280 / GUI.this.a.scrx;by = by * 720 / GUI.this.a.scry;
          for (int i = 0; i < GUI.this.a.sliderc; i++) {
            GUI.this.a.slider[i].selected = false;
          }
          for (int i = 0; i < GUI.this.a.buttonc; i++)
          {
            if ((GUI.this.a.button[i].isClicked(bx, by)) && (GUI.this.a.button[i].selected) && ((GUI.this.a.gamestatus != 7) || (i == 17) || (i == 18))) {
              switch (i)
              {
              case 0: 
                GUI.this.a.reset();
                for (int j = 0; j < 6; j++) {
                  GUI.this.a.button[j].displayed = false;
                }
                GUI.this.a.button[19].displayed = false;GUI.this.a.button[20].displayed = false; break;
              case 21: 
                GUI.this.a.gamestatus = -1;GUI.this.a.dab.getScores();GUI.this.a.button[6].displayed = false;GUI.this.a.button[24].displayed = true;
                for (int j = 0; j < 6; j++) {
                  GUI.this.a.button[j].displayed = false;
                }
                for (int j = 18; j < 24; j++) {
                  GUI.this.a.button[j].displayed = false;
                }
                break;
              case 6: 
                GUI.this.return2Menu(); break;
              case 7: 
                GUI.this.a.gamestatus = 1;
                if (GUI.this.a.textfield.text.length() != 0) {
                  GUI.this.a.dab.addScore(GUI.this.a.player.score, GUI.this.a.textfield.text);
                }
                for (int j = 7; j < 9; j++) {
                  GUI.this.a.button[j].displayed = false;
                }
                break;
              case 8: 
                GUI.this.a.gamestatus = 1;
                for (int j = 7; j < 9; j++) {
                  GUI.this.a.button[j].displayed = false;
                }
                GUI.this.a.button[19].displayed = false; break;
              case 5: 
                GUI.this.a.gamestatus = -4;
                for (int j = 0; j < 6; j++) {
                  GUI.this.a.button[j].displayed = false;
                }
                GUI.this.a.button[6].displayed = true;GUI.this.a.button[19].displayed = false;GUI.this.a.button[20].displayed = false;
                for (int j = 9; j < 11; j++) {
                  GUI.this.a.button[j].displayed = true;
                }
                break;
              case 9: 
                if (GUI.this.a.gamestatus < 0)
                {
                  GUI.this.a.gamestatus = -6;GUI.this.a.paramreturn = 1;
                }
                else
                {
                  GUI.this.a.gamestatus = 6;GUI.this.a.paramreturn = 2;
                }
                for (int j = 0; j < GUI.this.a.buttonc; j++) {
                  GUI.this.a.button[j].displayed = false;
                }
                for (int j = 11; j < 14; j++) {
                  GUI.this.a.button[j].displayed = true;
                }
                GUI.this.a.button[25].displayed = true;GUI.this.a.button[26].displayed = true;GUI.this.a.slider[3].displayed = true;GUI.this.a.droplist.displayed = true; break;
              case 10: 
                if (GUI.this.a.gamestatus < 0)
                {
                  GUI.this.a.gamestatus = -5;GUI.this.a.paramreturn = 0;
                }
                else
                {
                  GUI.this.a.gamestatus = 5;GUI.this.a.paramreturn = 3;
                }
                for (int j = 0; j < 3; j++) {
                  GUI.this.a.slider[j].displayed = true;
                }
                for (int j = 0; j < GUI.this.a.buttonc; j++) {
                  GUI.this.a.button[j].displayed = false;
                }
                GUI.this.a.button[11].displayed = true; break;
              case 11: 
                for (int j = 11; j < GUI.this.a.buttonc; j++) {
                  GUI.this.a.button[j].displayed = false;
                }
                for (int j = 0; j < 4; j++) {
                  GUI.this.a.slider[j].displayed = false;
                }
                GUI.this.a.droplist.displayed = false;
                switch (GUI.this.a.paramreturn)
                {
                case 0: 
                case 1: 
                  GUI.this.a.gamestatus = -4;GUI.this.a.button[6].displayed = true;
                  for (int j = 9; j < 11; j++) {
                    GUI.this.a.button[j].displayed = true;
                  }
                  break;
                case 2: 
                case 3: 
                  GUI.this.a.gamestatus = 3;
                  for (int j = 9; j < 11; j++) {
                    GUI.this.a.button[j].displayed = true;
                  }
                  for (int j = 14; j < 17; j++) {
                    GUI.this.a.button[j].displayed = true;
                  }
                }
                break;
              case 14: 
                GUI.this.a.gamestatus = 0;
                for (int j = 0; j < GUI.this.a.buttonc; j++) {
                  GUI.this.a.button[j].displayed = false;
                }
                break;
              case 15: 
                GUI.this.a.paramreturn = 5;GUI.this.a.gamestatus = 7;GUI.this.a.button[17].displayed = true;GUI.this.a.button[18].displayed = true; break;
              case 16: 
                GUI.this.a.paramreturn = 4;GUI.this.a.gamestatus = 7;GUI.this.a.button[17].displayed = true;GUI.this.a.button[18].displayed = true; break;
              case 17: 
                for (int j = 0; j < GUI.this.a.buttonc; j++) {
                  GUI.this.a.button[j].displayed = false;
                }
                switch (GUI.this.a.paramreturn)
                {
                case 4: 
                  GUI.this.exit(); break;
                case 5: 
                  GUI.this.return2Menu();
                }
                break;
              case 18: 
                for (int j = 0; j < GUI.this.a.buttonc; j++) {
                  GUI.this.a.button[j].displayed = false;
                }
                switch (GUI.this.a.paramreturn)
                {
                case 4: 
                case 5: 
                  GUI.this.a.gamestatus = 3;
                  for (int j = 9; j < 11; j++) {
                    GUI.this.a.button[j].displayed = true;
                  }
                  for (int j = 14; j < 17; j++) {
                    GUI.this.a.button[j].displayed = true;
                  }
                }
                break;
              case 19: 
                GUI.this.exit(); break;
              case 22: 
                GUI.this.a.gamestatus = -7;GUI.this.a.button[6].displayed = false;GUI.this.a.button[24].displayed = true;
                for (int j = 0; j < 6; j++) {
                  GUI.this.a.button[j].displayed = false;
                }
                for (int j = 18; j < 24; j++) {
                  GUI.this.a.button[j].displayed = false;
                }
                break;
              case 4: 
                GUI.this.a.gamestatus = -8;GUI.this.a.button[24].displayed = false;
                for (int j = 0; j < 21; j++) {
                  GUI.this.a.button[j].displayed = false;
                }
                GUI.this.a.button[6].displayed = true;
                for (int j = 21; j < 24; j++) {
                  GUI.this.a.button[j].displayed = true;
                }
                break;
              case 24: 
                GUI.this.a.gamestatus = -8;
                for (int j = 0; j < GUI.this.a.buttonc; j++) {
                  GUI.this.a.button[j].displayed = false;
                }
                GUI.this.a.button[6].displayed = true;
                for (int j = 21; j < 24; j++) {
                  GUI.this.a.button[j].displayed = true;
                }
                break;
              case 23: 
                GUI.this.a.gamestatus = -9;GUI.this.a.button[6].displayed = false;GUI.this.a.button[24].displayed = true;
                for (int j = 0; j < 6; j++) {
                  GUI.this.a.button[j].displayed = false;
                }
                for (int j = 18; j < 24; j++) {
                  GUI.this.a.button[j].displayed = false;
                }
              }
            }
            switch (i)
            {
            case 0: 
              GUI.this.a.button[i].selected = false; break;
            case 4: 
              GUI.this.a.button[i].selected = false; break;
            case 5: 
              GUI.this.a.button[i].selected = false; break;
            case 6: 
              GUI.this.a.button[i].selected = false; break;
            case 7: 
              GUI.this.a.button[i].selected = false; break;
            case 8: 
              GUI.this.a.button[i].selected = false; break;
            case 9: 
              GUI.this.a.button[i].selected = false; break;
            case 10: 
              GUI.this.a.button[i].selected = false; break;
            case 11: 
              GUI.this.a.button[i].selected = false; break;
            case 14: 
              GUI.this.a.button[i].selected = false; break;
            case 15: 
              GUI.this.a.button[i].selected = false; break;
            case 16: 
              GUI.this.a.button[i].selected = false; break;
            case 17: 
              GUI.this.a.button[i].selected = false; break;
            case 18: 
              GUI.this.a.button[i].selected = false; break;
            case 19: 
              GUI.this.a.button[i].selected = false; break;
            case 20: 
              GUI.this.a.button[i].selected = false; break;
            case 21: 
              GUI.this.a.button[i].selected = false; break;
            case 22: 
              GUI.this.a.button[i].selected = false; break;
            case 23: 
              GUI.this.a.button[i].selected = false; break;
            case 24: 
              GUI.this.a.button[i].selected = false;
            }
          }
          GUI.this.a.mapmouse = false;
        }
      }
      
      public void mouseEntered(MouseEvent me) {}
      
      public void mouseExited(MouseEvent me) {}
    });
    this.f.add(this.a);
    this.f.pack();
    this.f.setVisible(true);
    this.f.setLocationRelativeTo(null);
    paintThread.setRepaintPeriod(20);
    paintThread.start();
  }
}
