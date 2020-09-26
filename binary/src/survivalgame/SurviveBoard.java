// Decompiled by DJ v3.12.12.98 Copyright 2014 Atanas Neshkov  Date: 2.6.2016 23:40:49
// Home Page: http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   SurviveBoard.java

package survivalgame;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.*;
import java.net.URL;
import java.util.Random;
import javax.sound.sampled.*;
import javax.swing.JPanel;
import javazoom.jlgui.basicplayer.BasicPlayer;
import javazoom.jlgui.basicplayer.BasicPlayerException;
import survivalgame.blocks.BlackBullet;
import survivalgame.blocks.Block;
import survivalgame.blocks.Hatman;
import survivalgame.blocks.Laser;
import survivalgame.blocks.Meteor;
import survivalgame.blocks.Mine;
import survivalgame.blocks.Potion;
import survivalgame.blocks.RedBall;
import survivalgame.blocks.Sparkle;
import survivalgame.blocks.Witch;

// Referenced classes of package survivalgame:
//            Text, Button, Slider, Dbcon, 
//            Map, Player, Statistics, DropList, 
//            Parameters, DropObject, TextField

public final class SurviveBoard extends JPanel
{

    public SurviveBoard()
    {
        soundpath = new String[20][2];
        basedb = new float[20];
        ran = new Random();
        redball = new RedBall[100];
        blackbullet = new BlackBullet[20];
        meteor = new Meteor[100];
        mine = new Mine[100];
        sparkle = new Sparkle[300];
        laser = new Laser[300];
        potion = new Potion[20];
        text = new Text[20];
        button = new Button[30];
        slider = new Slider[5];
        redballc = 0;
        blackbulletc = 0;
        meteorc = 0;
        minec = 0;
        sparklec = 0;
        laserc = 0;
        potionc = 0;
        sliderc = 0;
        buttonc = 0;
        textc = 0;
        tarx = -1;
        tary = -1;
        scrx = 1280;
        scry = 720;
        dab = new Dbcon();
        curroad = 0;
        weacount = 0;
        spawn3max = 35;
        gamestatus = -3;
        taunttimer = 0;
        tauntcounter = 0;
        tauntdirection = 1;
        gx = 0;
        gy = 0;
        gxe = 0;
        gye = 0;
        clickready = 0;
        mapmouse = false;
        metplay = 0;
        metran = 0;
        fps = 0;
        fps2 = 0;
        difficulty = 2;
        mastervolume = 70;
        musicvolume = 100;
        soundvolume = 100;
        hallofhatselect = 0;
        burstX = new int[100];
        burstY = new int[100];
        burstR = new int[100];
        showminimap = true;
        showtrace = true;
        stopped = false;
        resetdone = true;
        resetable = true;
        ismainsong = false;
        flowermap = new int[256][144];
        basedb[0] = 4F;
        basedb[1] = -5F;
        basedb[2] = -5F;
        basedb[3] = -3F;
        basedb[4] = -1F;
        basedb[5] = 6F;
        basedb[6] = 6F;
        basedb[7] = -1F;
        basedb[8] = -33F;
        soundpath[0][1] = "/sound/den.wav";
        soundpath[1][1] = "/sound/InfernalBirth1.wav";
        soundpath[2][1] = "/sound/cannon.wav";
        soundpath[3][1] = "/sound/bombexplode.wav";
        soundpath[4][1] = "/sound/witch.wav";
        soundpath[5][1] = "/sound/reward3c.wav";
        soundpath[6][1] = "/sound/deathlaugh2.wav";
        soundpath[7][1] = "/sound/witch_scream.wav";
        soundpath[8][1] = "/sound/fire.wav";
        soundpath[0][0] = "/sound/tetris.mp3";
        soundpath[1][0] = "/sound/InfernalBirth.mp3";
        map = new Map(512, 290);
        player = new Player();
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        off_Image = gd.getDefaultConfiguration().createCompatibleImage(2560, 1440, 1);
        g2 = off_Image.createGraphics();
        map_Image = gd.getDefaultConfiguration().createCompatibleImage(2560, 1440, 1);
        mg2 = map_Image.createGraphics();
        mini_Image = gd.getDefaultConfiguration().createCompatibleImage(256, 144, 1);
        ig2 = mini_Image.createGraphics();
        darker_map_Image = gd.getDefaultConfiguration().createCompatibleImage(2560, 1440, 1);
        dmg2 = darker_map_Image.createGraphics();
        hatman = new Hatman(350, 300, Color.orange, 20);
        mainsongtime = 0xf423f;
        reset();
    }

    public void darkenMap()
    {
        RescaleOp rescaleOp = new RescaleOp(0.7F, 5F, null);
        rescaleOp.filter(map_Image, darker_map_Image);
    }

    public void burstMap(int x, int y, int radius)
    {
        int xh = x / 5;
        int yh = y / 5;
        int rh = radius / 5;
        for(int i = -1 * rh; i <= rh; i++)
        {
            for(int j = -1 * rh; j <= rh; j++)
            {
                int xt = xh + i;
                int yt = yh + j;
                if(i * i + j * j <= rh * rh && xt >= 0 && yt >= 0 && xt < map.sizex && yt < map.sizey)
                    map.map[xt][yt] = false;
            }

        }

        int g = (new Color(232, 232, 232)).getRGB();
        int g2 = 0xffa6a6a6;
        for(int i = -1 * radius; i <= radius; i++)
        {
            for(int j = -1 * radius; j <= radius; j++)
            {
                if(i * i + j * j > radius * radius)
                    continue;
                int xt = x + i;
                int yt = y + j;
                if(xt < 0 || xt >= 2560 || yt < 0 || yt >= 1440)
                    continue;
                boolean d = true;
                for(int z = 0; z < burstc && d; z++)
                {
                    int p = (xt - burstX[z]) * (xt - burstX[z]) + (yt - burstY[z]) * (yt - burstY[z]);
                    if(p < burstR[z] * burstR[z])
                        d = false;
                }

                int red = 238;
                if(!d)
                    red = map_Image.getRGB(xt, yt) >> 16 & 0xff;
                double ratio = ((double)(i * i + j * j) + 0.0D) / (double)(radius * radius);
                int s = (int)((((double)(60 + ran.nextInt(8)) + (double)(72 + ran.nextInt(16)) * Math.sqrt(ratio) + (double)(98 + ran.nextInt(8)) * ratio) * (double)red) / 238D);
                if(s > red)
                    s = red;
                int rgb = 0x10000 * s + 256 * s + s;
                int s2 = (int)((((double)(60 + ran.nextInt(8)) + (double)(72 + ran.nextInt(16)) * Math.sqrt(ratio) + (double)(98 + ran.nextInt(8)) * ratio) * (double)red) / 340D);
                if(s2 > (red * 238) / 340)
                    s2 = (red * 238) / 340;
                int rgb2 = 0x10000 * s2 + 256 * s2 + s2;
                map_Image.setRGB(xt, yt, rgb);
                darker_map_Image.setRGB(xt, yt, rgb2);
            }

        }

        burstX[burstc] = x;
        burstY[burstc] = y;
        burstR[burstc++] = radius;
    }

    public void scorchMap(int x, int y, int radius)
    {
        int Brgb = Color.black.getRGB();
        int Grgb = (new Color(20, 120, 45)).getRGB();
        int Srgb = (new Color(238, 238, 238)).getRGB();
        int BBrgb = (new Color(0, 100, 130)).getRGB();
        int SSrgb = (new Color(230, 230, 230)).getRGB();
        for(int i = -1 * radius; i <= radius; i++)
        {
            for(int j = -1 * radius; j <= radius; j++)
            {
                if(i * i + j * j > radius * radius)
                    continue;
                int xt = x + i;
                int yt = y + j;
                if(xt < 0 || xt >= 2560 || yt < 0 || yt >= 1440)
                    continue;
                if(map_Image.getRGB(xt, yt) == Grgb)
                {
                    map_Image.setRGB(xt, yt, Brgb);
                    darker_map_Image.setRGB(xt, yt, Brgb);
                    continue;
                }
                if(map_Image.getRGB(xt, yt) == Srgb || map_Image.getRGB(xt, yt) == BBrgb || map_Image.getRGB(xt, yt) == SSrgb)
                    continue;
                boolean d = true;
                for(int z = 0; z < burstc && d; z++)
                {
                    int p = (xt - burstX[z]) * (xt - burstX[z]) + (yt - burstY[z]) * (yt - burstY[z]);
                    if(p < burstR[z] * burstR[z])
                        d = false;
                }

                if(d)
                {
                    map_Image.setRGB(xt, yt, Brgb);
                    darker_map_Image.setRGB(xt, yt, Brgb);
                }
            }

        }

    }

    public void bufferMap()
    {
        for(int i = 0; i < 256; i++)
        {
            for(int j = 0; j < 144; j++)
                flowermap[i][j] = 0;

        }

        mg2.setColor(new Color(238, 238, 238));
        mg2.fillRect(0, 0, 2560, 1440);
        mg2.setColor(new Color(0, 100, 130));
        int rgbcol = (new Color(0, 100, 130)).getRGB();
        int rgbcol2 = (new Color(20, 120, 45)).getRGB();
        int rgbcol3 = (new Color(220, 10, 15)).getRGB();
        int rgbcol4 = (new Color(238, 238, 238)).getRGB();
        for(int i = 0; i < 512; i++)
        {
            for(int j = 0; j < 288; j++)
            {
                int s = 5;
                int s2 = 5;
                double myran = Math.random();
                if(myran < 0.13D)
                    s = 9;
                else
                if(myran < 0.41999999999999998D)
                    s = 7;
                else
                if(myran < 0.5D)
                    s = 11;
                myran = Math.random();
                if(myran < 0.13D)
                    s2 = 9;
                else
                if(myran < 0.41999999999999998D)
                    s2 = 7;
                else
                if(myran < 0.5D)
                    s2 = 11;
                if(map.map[i][j])
                    mg2.fillRect(i * 5 + (5 - s) / 2, j * 5 + (5 - s2) / 2, s, s2);
            }

        }

        for(int i = 10; i < 2557; i++)
        {
            for(int j = 10; j < 1430; j++)
            {
                if(Math.random() >= 0.38D)
                    continue;
                boolean f = false;
                for(int m = 0; m < 3 && !f; m++)
                {
                    for(int n = 0; n < 3 && !f; n++)
                        if(map_Image.getRGB((i + m) - 1, (j + n) - 1) == rgbcol || map_Image.getRGB((i + m) - 1, (j + n) - 1) == rgbcol2)
                            f = true;

                }

                if(f)
                    map_Image.setRGB(i, j, rgbcol2);
            }

        }

        for(int i = 50; i < 2500; i++)
        {
            for(int j = 50; j < 1400; j++)
            {
                if(map_Image.getRGB(i, j) != rgbcol2 || Math.random() >= 0.0030000000000000001D)
                    continue;
                int redsit = 0;
                int po = 5;
                for(int m = 0; m < po; m++)
                {
                    for(int n = 0; n < po; n++)
                    {
                        if(map_Image.getRGB((i + m) - (po - 1) / 2, (j + n) - (po - 1) / 2) == rgbcol2)
                        {
                            redsit++;
                            continue;
                        }
                        if(map_Image.getRGB((i + m) - (po - 1) / 2, (j + n) - (po - 1) / 2) == rgbcol)
                            redsit += 2;
                    }

                }

                if(Math.random() >= (double)(40 + redsit) * 0.0040000000000000001D)
                    continue;
                int id = i / 10;
                int jd = j / 10;
                po = 5;
                redsit = 0;
                for(int m = 0; m < po; m++)
                {
                    for(int n = 0; n < po; n++)
                        if(flowermap[(id + m) - (po - 1) / 2][(jd + n) - (po - 1) / 2] != 0)
                            redsit++;

                }

                if(redsit != 0)
                    continue;
                int s = 23 + ran.nextInt(14);
                int fp = ran.nextInt(100);
                int mf = 0;
                if(fp < 60)
                    mf = 1;
                else
                if(fp < 70)
                    mf = 2;
                switch(mf)
                {
                case 0: // '\0'
                    s = 23 + ran.nextInt(14);
                    break;

                case 1: // '\001'
                    s = 19 + ran.nextInt(11);
                    break;

                case 2: // '\002'
                    s = 22 + ran.nextInt(12);
                    break;
                }
                mg2.drawImage(flower[mf], i, j, s, s, this);
                flowermap[id][jd] = 1;
            }

        }

    }

    public boolean readfile()
    {
        try
        {
            FileInputStream fileIn = new FileInputStream("player.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            statistic = (Statistics)in.readObject();
            in.close();
            fileIn.close();
        }
        catch(IOException | ClassNotFoundException i)
        {
            statistic = new Statistics();
            statistic.initialize();
            i.printStackTrace();
            return false;
        }
        return true;
    }

    public void enrageWitch()
    {
        witch.charge = 1;
        witch.lasery = 250;
        witch.color = Color.red;
        playSound(7, true);
        potion[potionc++] = new Potion(witch.x, witch.y, 12 + ran.nextInt(2), 3000);
    }

    public void deleteBlock(int i, int x)
    {
        switch(x)
        {
        case 1: // '\001'
            redball[i] = redball[--redballc];
            break;

        case 2: // '\002'
            blackbullet[i] = blackbullet[--blackbulletc];
            break;

        case 3: // '\003'
            meteor[i] = meteor[--meteorc];
            break;

        case 4: // '\004'
            mine[i] = mine[--minec];
            break;

        case 5: // '\005'
            potion[i] = potion[--potionc];
            break;

        case 6: // '\006'
            laser[i] = laser[--laserc];
            break;

        case 7: // '\007'
            sparkle[i] = sparkle[--sparklec];
            break;

        default:
            System.out.println("An error has occurred : deletion unsuccessful!");
            break;
        }
    }

    public void unlockHat(int hatno)
    {
        if(!statistic.hatacquired[hatno])
        {
            playSound(5, true);
            droplist.addObject(hatno);
            droplist.current = droplist.dropobjectc - 1;
            hatman.charge = hatno;
            statistic.hatacquired[hatno] = true;
            switch(hatno)
            {
            case 0: // '\0'
                addText("Hat Unlocked : ");
                addText("- A gift from the magician -");
                break;

            case 1: // '\001'
                addText("Special Hat Unlocked : ");
                addText("- Dancing with the flow -");
                break;

            case 2: // '\002'
                addText("Special Hat Unlocked : ");
                addText("- A Witch Wannabe -");
                break;

            case 3: // '\003'
                addText("Hat Unlocked : ");
                addText("- Baby Boy -");
                break;

            case 4: // '\004'
                addText("Hat Unlocked : ");
                addText("- A REAL Gentleman -");
                break;

            case 5: // '\005'
                addText("Special Hat Unlocked : ");
                addText("- Death Thirsty -");
                break;

            case 6: // '\006'
                addText("Special Hat Unlocked : ");
                addText("- Mine Maniac -");
                break;

            case 7: // '\007'
                addText("Hat Unlocked : ");
                addText("- Captain O-Captain -");
                break;

            case 8: // '\b'
                addText("Special Hat Unlocked : ");
                addText("- Space-Time! -");
                break;

            case 9: // '\t'
                addText("Special Hat Unlocked : ");
                addText("- LaserMan -");
                break;

            case 10: // '\n'
                addText("Hat Unlocked : ");
                addText("- Number 100 -");
                break;

            case 11: // '\013'
                addText("Hat Unlocked : ");
                addText("- Number 10 -");
                break;

            case 12: // '\f'
                addText("Special Hat Unlocked : ");
                addText("- Bronze CHEF -");
                break;

            case 13: // '\r'
                addText("Special Hat Unlocked : ");
                addText("- Silver CHEF -");
                break;

            case 14: // '\016'
                addText("Special Hat Unlocked : ");
                addText("- GOLDEN CHEF -");
                break;

            case 15: // '\017'
                addText("Special Hat Unlocked : ");
                addText("- Return of the BAT! -");
                break;

            case 16: // '\020'
                addText("Hat Unlocked : ");
                addText("- Robin the Good -");
                break;

            case 17: // '\021'
                addText("Special Hat Unlocked : ");
                addText("- The Fire -");
                break;

            case 18: // '\022'
                addText("Special Hat Unlocked : ");
                addText("- Unwavering Time -");
                break;
            }
        }
    }

    public void getDatas()
    {
        statistic.settings[0] = mastervolume;
        statistic.settings[1] = musicvolume;
        statistic.settings[2] = soundvolume;
        statistic.settings[3] = showminimap ? 1 : 0;
        statistic.settings[4] = showtrace ? 1 : 0;
        statistic.settings[5] = droplist.dropobject[droplist.current].imagenum;
        statistic.name[0] = textfield.text;
    }

    public void write2file(){
      getDatas();
              try
      {
        try (FileOutputStream fileOut = new FileOutputStream("player.ser")) {
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(statistic);
            out.close();
        }
         System.out.printf("Serialized data is saved in player.ser");
      }catch(IOException i)
      {
          i.printStackTrace();
      }
        
        
    }

    private void adjustStatistics()
    {
        statistic.avg[difficulty] = (statistic.avg[difficulty] * (double)statistic.numofplay[difficulty] + (double)player.gametime) / (double)(statistic.numofplay[difficulty] + 1);
        statistic.numofplay[difficulty]++;
        if(statistic.min[difficulty] == 0.0D || statistic.min[difficulty] > (double)player.gametime)
            statistic.min[difficulty] = player.gametime;
        if(statistic.max[difficulty] < (double)player.gametime)
            statistic.max[difficulty] = player.gametime;
        statistic.avg[3] = (statistic.avg[3] * (double)statistic.numofplay[3] + (double)player.gametime) / (double)(statistic.numofplay[3] + 1);
        statistic.numofplay[3]++;
        if(statistic.min[3] == 0.0D || statistic.min[3] > (double)player.gametime)
            statistic.min[3] = player.gametime;
        if(statistic.max[3] < (double)player.gametime)
            statistic.max[3] = player.gametime;
    }

    public void setTarget(int tarx, int tary)
    {
        this.tarx = (tarx * 1280) / scrx;
        this.tary = (tary * 720) / scry;
    }

    public Dimension getPreferredSize()
    {
        return new Dimension(1280, 720);
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        fps++;
        if(player.purple_potion_timer <= 0.0D && player.green_potion_timer <= 0.0D){
//            g2.drawImage(map_Image, 0, 0, 1280, 720, this);
//            g2.drawImage(map_Image, Math.max(gx - 20, 0), Math.max(gy - 20, 0), Math.min(1280 + gx + 20, 2560), Math.min(720 + gy + 20, 1440), this);
            g2.drawImage(map_Image, Math.max(gx - 20, 0), Math.max(gy - 20, 0), Math.min(1280 + gx + 20, 2560), Math.min(720 + gy + 20, 1440), Math.max(gx - 20, 0), Math.max(gy - 20, 0), Math.min(1280 + gx + 20, 2560), Math.min(720 + gy + 20, 1440), this);
        } else
            g2.drawImage(darker_map_Image, Math.max(gx - 20, 0), Math.max(gy - 20, 0), Math.min(1280 + gx + 20, 2560), Math.min(720 + gy + 20, 1440), Math.max(gx - 20, 0), Math.max(gy - 20, 0), Math.min(1280 + gx + 20, 2560), Math.min(720 + gy + 20, 1440), this);
        ig2.drawImage(map_Image, 0, 0, 256, 144, 0, 0, 2560, 1440, this);
        ig2.setColor(Color.magenta);
        ig2.fillOval(5, 1, 10, 10);
        ig2.fillOval(231, 1, 10, 10);
        ig2.fillOval(5, 133, 10, 10);
        ig2.fillOval(231, 133, 10, 10);
        g2.setColor(Color.magenta);
        g2.fillOval(50, 10, 100, 100);
        g2.fillOval(2310, 10, 100, 100);
        g2.fillOval(50, 1330, 100, 100);
        g2.fillOval(2310, 1330, 100, 100);
        g2.setColor(new Color(255, 255, 38, 200));
        if(showtrace)
        {
            for(int i = 0; i < player.aroadl2; i++)
            {
                if(map.map[map.aroadx2[i]][map.aroady2[i]])
                    System.out.println("error");
                g2.fillRect(player.aroadx2[i] * 5, player.aroady2[i] * 5, 5, 5);
            }

        }
        g2.setColor(new Color(200, 0, 0));
        g2.fillRect(player.finx * 5, player.finy * 5, 5, 5);
        for(int i = 0; i < sparklec; i++)
            if(sparkle[i] != null)
            {
                sparkle[i].draw(g2);
                sparkle[i].drawMini(ig2);
            }

        for(int i = 0; i < minec; i++)
            if(mine[i] != null)
            {
                mine[i].draw(g2);
                mine[i].drawMini(ig2);
            }

        for(int i = 0; i < potionc; i++)
            if(potion[i] != null)
            {
                potion[i].draw(g2);
                potion[i].drawMini(ig2);
            }

        witch.draw(g2);
        witch.drawMini(ig2);
        for(int i = 0; i < meteorc; i++)
            if(meteor[i] != null)
            {
                meteor[i].draw(g2);
                meteor[i].drawMini(ig2);
            }

        hatman.draw(g2);
        hatman.drawMini(ig2);
        for(int i = 0; i < laserc; i++)
            if(laser[i] != null)
            {
                laser[i].draw(g2);
                laser[i].drawMini(ig2);
            }

        for(int i = 0; i < blackbulletc; i++)
            if(blackbullet[i] != null)
            {
                blackbullet[i].draw(g2);
                blackbullet[i].drawMini(ig2);
            }

        for(int i = 0; i < redballc; i++)
            if(redball[i] != null)
            {
                redball[i].draw(g2);
                redball[i].drawMini(ig2);
            }

        
        Color outerCircleColor;
        if(tauntdirection == -1){
            int tColor = 0;
            if(taunttimer >= 20){
                tColor = (int) taunttimer - 20;
            }
            outerCircleColor = new Color(30, tColor / 2, tColor);
//            if(taunttimer >= 227){
//                int tColor = 120 + 6 * (247 - taunttimer);
//                outerCircleColor = new Color(30, tColor/2, tColor);
//            } else {
//            }
        } else {
            if((tauntcounter + taunttimer) >= 227){
                int tColor = (int) (3.5 * (tauntcounter + taunttimer - 227));
                outerCircleColor = new Color(30, tColor/2, tColor);
            } else {
                outerCircleColor = new Color(30, 0, 0);
            }
        }
        
        ig2.setColor(outerCircleColor);
        int shift = 2;
        ig2.drawOval(78+shift, 22+shift, 100-shift*2, 100-shift*2);
        ig2.setColor(new Color(30, 100, 220, 80));
        int iTime = (int) taunttimer;
        ig2.drawOval(128 - (iTime * 2) / 10, 72 - (iTime * 2) / 10, (2 * (iTime * 2)) / 10, (2 * (iTime * 2)) / 10);
        iTime = (int) taunttimer - 6;
        ig2.drawOval(128 - (iTime * 2) / 10, 72 - (iTime * 2) / 10, (2 * (iTime * 2)) / 10, (2 * (iTime * 2)) / 10);
        ig2.setColor(new Color(30, 100, 220, 20));
        iTime = (int) taunttimer;
        ig2.fillOval(128 - iTime * 2 / 10, 72 - iTime * 2 / 10, 2 * (iTime * 2) / 10, 2 * (iTime * 2) / 10);
        

        shift = 20;
        g2.setColor(outerCircleColor);
        g2.drawOval(780+shift, 220+shift, 1000-shift*2, 1000-shift*2);
//        g2.setColor(new Color(30, taunttimer / 2, taunttimer));
        g2.setColor(new Color(30, 100, 220, 60));
        iTime = (int) taunttimer;
        g2.drawOval(1280 - iTime * 2, 720 - iTime * 2, 2 * (iTime * 2), 2 * (iTime * 2));
        
        g2.setColor(new Color(30, 100, 220, 8));
        iTime = (int) taunttimer - 8;
        g2.fillOval(1280 - (iTime * 2), 720 - (iTime * 2), (2 * (iTime * 2)), (2 * (iTime * 2)));
        g2.setColor(new Color(30, 100, 220, 16));
        iTime = (int) taunttimer;
        g2.fillOval(1280 - iTime * 2, 720 - iTime * 2, 2 * (iTime * 2), 2 * (iTime * 2));
        
//        System.out.println(new Dimension(scrx, scry));
        g.drawImage(off_Image, 0, 0, scrx, scry, gx, gy, 1280 + gx, 720 + gy, this);
        if(showminimap)
        {
            g.drawImage(mini_Image, (1026 * scrx) / 1280, (576 * scry) / 720, scrx + (1026 * scrx) / 1280, scry + (576 * scry) / 720, 0, 0, 1280, 720, this);
            g.setColor(Color.black);
            g.drawRect((1026 * scrx) / 1280 + (gx * scrx) / 12800, (576 * scry) / 720 + (gy * scry) / 7200, scrx / 10, scry / 10);
            g.drawRect((1026 * scrx) / 1280, (576 * scry) / 720, scrx / 5, scry / 5);
        }
        g.setColor(Color.black);
        g.setFont(new Font("Arial", 0, (16 * scrx) / 1280));
        g.drawString("Health : ", (45 * scrx) / 1280, (40 * scry) / 720);
        g.drawString("Status Effects : ", (45 * scrx) / 1280, (75 * scry) / 720);
        g.drawString((new StringBuilder()).append("Time : ").append(player.getTimeString()).toString(), (1080 * scrx) / 1280, (40 * scry) / 720);
        g.drawString((new StringBuilder()).append("Fps : ").append(fps2).toString(), (1080 * scrx) / 1280, (70 * scry) / 720);
        g.setColor(Color.green);
        double port = player.health / player.mhealth;
        g.fillRect((160 * scrx) / 1280, (20 * scry) / 720, ((int)(250D * port) * scrx) / 1280, (30 * scry) / 720);
        g.setColor(Color.red);
        g.fillRect(((int)(160D + 250D * port) * scrx) / 1280, (20 * scry) / 720, ((int)(250D - 250D * port) * scrx) / 1280, (30 * scry) / 720);
        g.setColor(Color.black);
        g.drawString(String.valueOf((int)player.health), (256 * scrx) / 1280, (40 * scry) / 720);
        int numer = 0;
        if(player.purple_potion_timer > 0.0D)
        {
            g.drawImage(Block.potion, ((160 + (numer % 15) * 30) * scrx) / 1280, ((55 + (numer / 15) * 30) * scry) / 720, (25 * scrx) / 1280, (25 * scry) / 720, this);
            numer++;
        }
        if(player.green_potion_timer > 0.0D)
        {
            g.drawImage(Block.green_potion, ((160 + (numer % 15) * 30) * scrx) / 1280, ((55 + (numer / 15) * 30) * scry) / 720, (25 * scrx) / 1280, (25 * scry) / 720, this);
            numer++;
        }
        
        if(player.stunned > 0.0D || player.laser_stun > 6)
        {
            g.drawImage(Block.stun, ((160 + (numer % 15) * 30) * scrx) / 1280, ((55 + (numer / 15) * 30) * scry) / 720, (25 * scrx) / 1280, (25 * scry) / 720, this);
            numer++;
        }
        
        if(player.slowed > 0.0D)
        {
            g.drawImage(Block.ice_icon, ((160 + (numer % 15) * 30) * scrx) / 1280, ((55 + (numer / 15) * 30) * scry) / 720, (25 * scrx) / 1280, (25 * scry) / 720, this);
            numer++;
        }
        
        if(player.fountain_slow > 0.0D)
        {
            g.drawImage(Block.ice_icon, ((160 + (numer % 15) * 30) * scrx) / 1280, ((55 + (numer / 15) * 30) * scry) / 720, (25 * scrx) / 1280, (25 * scry) / 720, this);
            numer++;
        }
        
        if(player.fountain_wave_slow > 0.0D)
        {
            g.drawImage(Block.wave_icon, ((160 + (numer % 15) * 30) * scrx) / 1280, ((55 + (numer / 15) * 30) * scry) / 720, (25 * scrx) / 1280, (25 * scry) / 720, this);
            numer++;
        }
        
        if(player.modifier < 1.0D)
        {
            g.drawImage(Block.witch_slow, ((160 + (numer % 15) * 30) * scrx) / 1280, ((55 + (numer / 15) * 30) * scry) / 720, (25 * scrx) / 1280, (25 * scry) / 720, this);
            numer++;
        }
        for(int i = 0; i < (int)player.burn; i++)
        {
            g.drawImage(Block.fire[0], ((160 + (numer % 15) * 30) * scrx) / 1280, ((55 + (numer / 15) * 30) * scry) / 720, (25 * scrx) / 1280, (25 * scry) / 720, this);
            numer++;
        }

        for(int i = 0; i < textc; i++)
            text[i].draw(g, scrx, scry);

        if(spawn4 > 1272 && spawn4 % 60 >= 30)
        {
            g.setFont(new Font("Arial", 0, (28 * scrx) / 1280));
            g.setColor(Color.black);
            g.drawString("METEOR SHOWER ", (535 * scrx) / 1280, (45 * scry) / 720);
        }
        if(gamestatus == 1 || gamestatus == 2)
        {
            g.setColor(Color.black);
            g.setFont(new Font("Arial", 0, (64 * scrx) / 1280));
            g.drawString("Game Over ", (495 * scrx) / 1280, (310 * scry) / 720);
            g.setFont(new Font("Arial", 0, (28 * scrx) / 1280));
            g.drawString((new StringBuilder()).append("Score : ").append(player.score).toString(), (570 * scrx) / 1280, (340 * scry) / 720);
            g.setFont(new Font("Arial", 0, (24 * scrx) / 1280));
            g.drawString("Press R to Restart ", (560 * scrx) / 1280, (370 * scry) / 720);
            g.drawString("Press ESC to Return to the Main Menu ", (455 * scrx) / 1280, (400 * scry) / 720);
        }
        if(gamestatus == 2)
        {
            g.setColor(new Color(90, 52, 14));
            g.fillRect((390 * scrx) / 1280, (220 * scry) / 720, (500 * scrx) / 1280, (300 * scry) / 720);
            g.setColor(Color.black);
            g.drawRect((390 * scrx) / 1280, (220 * scry) / 720, (500 * scrx) / 1280, (300 * scry) / 720);
            g.setFont(new Font("Arial", 0, (24 * scrx) / 1280));
            g.setColor(Color.orange);
            g.drawString((new StringBuilder()).append("You made a HighScore!  #").append(myposition + 1).toString(), (492 * scrx) / 1280, (270 * scry) / 720);
            g.setColor(Color.black);
            g.drawString("Enter your name : ", (465 * scrx) / 1280, (330 * scry) / 720);
            textfield.draw(g, scrx, scry);
        }
        if(gamestatus == -2 || gamestatus == -1 || gamestatus <= -4)
        {
            g.setColor(Color.gray);
            g.fillRect(0, 0, scrx, scry);
        }
        if(gamestatus >= 3)
        {
            g.setColor(Color.gray);
            g.fillRect((430 * scrx) / 1280, (160 * scry) / 720, (420 * scrx) / 1280, (395 * scry) / 720);
            g.setColor(Color.black);
            g.drawRect((430 * scrx) / 1280, (160 * scry) / 720, (420 * scrx) / 1280, (395 * scry) / 720);
        }
        if(gamestatus == 3 || gamestatus == 7)
        {
            g.setColor(Color.black);
            g.setFont(new Font("Arial", 0, (34 * scrx) / 1280));
            g.drawString("Paused", (585 * scrx) / 1280, (225 * scry) / 720);
        }
        if(gamestatus == -1)
        {
            g.setColor(Color.black);
            g.setFont(new Font("Arial", 0, (20 * scrx) / 1280));
            for(int i = 0; i < dab.count; i++)
                g.drawString((new StringBuilder()).append(String.valueOf(i + 1)).append(" - ").append(dab.name[i]).append("  ").append(String.valueOf(dab.score[i])).toString(), ((70 + 290 * (i / 25)) * scrx) / 1280, ((70 + (i % 25) * 25) * scry) / 720);

        }
        if(gamestatus == -2)
        {
            g.setColor(Color.green);
            g.setFont(new Font("Arial", 0, (42 * scrx) / 1280));
            g.drawString("Survival Game", (500 * scrx) / 1280, (175 * scry) / 720);
            g.setColor(Color.black);
            g.setFont(new Font("Arial", 0, (24 * scrx) / 1280));
            g.drawString("Difficulty", (600 * scrx) / 1280, (535 * scry) / 720);
            g.setColor(Color.orange);
            g.setFont(new Font("Arial", 0, (20 * scrx) / 1280));
            g.drawString("by Night[owl]", (585 * scrx) / 1280, (210 * scry) / 720);
        }
        if(gamestatus == -5 || gamestatus == 5)
        {
            g.setColor(Color.black);
            g.setFont(new Font("Arial", 0, (22 * scrx) / 1280));
            g.drawString("Master Volume : ", (500 * scrx) / 1280, (230 * scry) / 720);
            g.drawString("Music Volume : ", (508 * scrx) / 1280, (280 * scry) / 720);
            g.drawString("Sound Volume : ", (503 * scrx) / 1280, (330 * scry) / 720);
        }
        if(gamestatus == -6 || gamestatus == 6)
        {
            g.setColor(Color.black);
            g.setFont(new Font("Arial", 0, (22 * scrx) / 1280));
            g.drawString("Show Minimap : ", (500 * scrx) / 1280, (290 * scry) / 720);
            g.drawString("Show Trace : ", (524 * scrx) / 1280, (335 * scry) / 720);
            g.drawString("Screen Pan : ", (528 * scrx) / 1280, (245 * scry) / 720);
            g.drawString("Pan Speed : ", (535 * scrx) / 1280, (200 * scry) / 720);
            g.drawString("Pause on Minimize : ", (490 * scrx) / 1280, (380 * scry) / 720);
            g.drawString("Select Hat : ", (540 * scrx) / 1280, (425 * scry) / 720);
            g.drawString("Hat name : ", (480 * scrx) / 1280, (460 * scry) / 720);
            g.setFont(new Font("Arial", 0, (18 * scrx) / 1280));
            int yaxis = 460;
            switch(droplist.dropobject[droplist.current].imagenum)
            {
            case -1: 
                g.drawString("No Hat", (590 * scrx) / 1280, (yaxis * scry) / 720);
                break;

            case 0: // '\0'
                g.drawString("A gift from the magician", (590 * scrx) / 1280, (yaxis * scry) / 720);
                break;

            case 1: // '\001'
                g.drawString("Dancing with the flow", (590 * scrx) / 1280, (yaxis * scry) / 720);
                break;

            case 2: // '\002'
                g.drawString("A Witch Wannabe", (590 * scrx) / 1280, (yaxis * scry) / 720);
                break;

            case 3: // '\003'
                g.drawString("Baby Boy", (590 * scrx) / 1280, (yaxis * scry) / 720);
                break;

            case 4: // '\004'
                g.drawString("A REAL Gentleman", (590 * scrx) / 1280, (yaxis * scry) / 720);
                break;

            case 5: // '\005'
                g.drawString("Death Thirsty", (590 * scrx) / 1280, (yaxis * scry) / 720);
                break;

            case 6: // '\006'
                g.drawString("Mine Maniac", (590 * scrx) / 1280, (yaxis * scry) / 720);
                break;

            case 7: // '\007'
                g.drawString("Captain O-Captain", (590 * scrx) / 1280, (yaxis * scry) / 720);
                break;

            case 8: // '\b'
                g.drawString("Space-Time!", (590 * scrx) / 1280, (yaxis * scry) / 720);
                break;

            case 9: // '\t'
                g.drawString("LaserMan", (590 * scrx) / 1280, (yaxis * scry) / 720);
                break;

            case 10: // '\n'
                g.drawString("Number 100", (590 * scrx) / 1280, (yaxis * scry) / 720);
                break;

            case 11: // '\013'
                g.drawString("Number 10", (590 * scrx) / 1280, (yaxis * scry) / 720);
                break;

            case 12: // '\f'
                g.drawString("Bronze CHEF", (590 * scrx) / 1280, (yaxis * scry) / 720);
                break;

            case 13: // '\r'
                g.drawString("Silver CHEF", (590 * scrx) / 1280, (yaxis * scry) / 720);
                break;

            case 14: // '\016'
                g.drawString("GOLDEN CHEF", (590 * scrx) / 1280, (yaxis * scry) / 720);
                break;

            case 15: // '\017'
                g.drawString("Return of the BAT!", (590 * scrx) / 1280, (yaxis * scry) / 720);
                break;

            case 16: // '\020'
                g.drawString("Robin the Good", (590 * scrx) / 1280, (yaxis * scry) / 720);
                break;

            case 17: // '\021'
                g.drawString("The Fire", (590 * scrx) / 1280, (yaxis * scry) / 720);
                break;

            case 18: // '\022'
                g.drawString("Unwavering Time", (590 * scrx) / 1280, (yaxis * scry) / 720);
                break;
            }
        }
        if(gamestatus == -7)
        {
            g.setColor(Color.black);
            g.setFont(new Font("Arial", 0, (28 * scrx) / 1280));
            g.drawString("Easy", (370 * scrx) / 1280, (80 * scry) / 720);
            g.drawString("Medium", (555 * scrx) / 1280, (80 * scry) / 720);
            g.drawString("Hard", (770 * scrx) / 1280, (80 * scry) / 720);
            g.drawString("Total", (965 * scrx) / 1280, (80 * scry) / 720);
            g.drawString("Minimum", (150 * scrx) / 1280, (160 * scry) / 720);
            g.drawString("Average", (150 * scrx) / 1280, (260 * scry) / 720);
            g.drawString("Maximum", (150 * scrx) / 1280, (360 * scry) / 720);
            g.drawString("Total Time", (150 * scrx) / 1280, (460 * scry) / 720);
            g.drawString("Total Plays", (150 * scrx) / 1280, (560 * scry) / 720);
            for(int i = 0; i < 5; i++)
            {
                for(int j = 0; j < 6; j++)
                {
                    if(j != 5)
                        g.drawLine(((300 + 200 * i) * scrx) / 1280, ((105 + 100 * j) * scry) / 720, ((300 + 200 * i) * scrx) / 1280, ((105 + 100 * j + 100) * scry) / 720);
                    if(i != 4)
                        g.drawLine(((300 + 200 * i) * scrx) / 1280, ((105 + 100 * j) * scry) / 720, ((300 + 200 * i + 200) * scrx) / 1280, ((105 + 100 * j) * scry) / 720);
                }

            }

            for(int i = 0; i < 4; i++)
                Player.drawString(g, Player.getTimeString((int)statistic.min[i]), 28, 400 + 200 * i, 160, scrx, scry);

            for(int i = 0; i < 4; i++)
                Player.drawString(g, Player.getTimeString((int)statistic.avg[i]), 28, 400 + 200 * i, 260, scrx, scry);

            for(int i = 0; i < 4; i++)
                Player.drawString(g, Player.getTimeString((int)statistic.max[i]), 28, 400 + 200 * i, 360, scrx, scry);

            for(int i = 0; i < 4; i++)
                Player.drawString(g, String.valueOf(statistic.numofplay[i]), 28, 400 + 200 * i, 560, scrx, scry);

            for(int i = 0; i < 4; i++)
                Player.drawString(g, Player.getTimeString((int)(statistic.avg[i] * (double)statistic.numofplay[i])), 28, 400 + 200 * i, 460, scrx, scry);

        }
        if(gamestatus == -9)
        {
            g.setFont(new Font("Arial", 0, (28 * scrx) / 1280));
            g.setColor(Color.black);
            for(int i = 0; i < 19; i++)
            {
                int x = ((150 + (i % 9) * 105) * scrx) / 1280;
                int y = ((100 + (i / 9) * 100) * scry) / 720;
                int width = (80 * scrx) / 1280;
                int height = (80 * scry) / 720;
                g.setColor(new Color(218, 218, 218));
                g.fillRect(x, y, width, height);
                g.setColor(Color.black);
                g.drawRect(x, y, width, height);
                g.drawImage(DropObject.dropimages[i], x + (5 * scrx) / 1280, y + (5 * scry) / 720, width - (10 * scrx) / 1280, height - (10 * scry) / 720, null);
                if(!statistic.hatacquired[i])
                {
                    g.setColor(new Color(30, 30, 30, 250));
                    g.fillRect(x, y, width, height);
                }
                if(i == hallofhatselect)
                {
                    g.setColor(Color.RED.darker());
                    g.drawRect(x + (4 * scrx) / 1280, y + (4 * scry) / 720, width - (8 * scrx) / 1280, height - (8 * scry) / 720);
                }
                g.setColor(Color.orange);
                if(i == 1 || i == 2 || i == 5 || i == 6 || i == 8 || i == 9 || i == 12 || i == 13 || i == 14 || i == 15 || i == 17 || i == 18)
                    g.drawString("*", (x + width) - (19 * scrx) / 1280, y + (25 * scry) / 720);
            }

            g.setColor(Color.black);
            g.setFont(new Font("Arial", 0, (28 * scrx) / 1280));
            g.drawString("Hat Name :", (150 * scrx) / 1280, (450 * scry) / 720);
            g.setFont(new Font("Arial", 0, (48 * scrx) / 1280));
            g.drawString("Hall of Hats", (500 * scrx) / 1280, (66 * scry) / 720);
            g.drawImage(DropObject.dropimages[4], (700 * scrx) / 1280, (24 * scry) / 720, (35 * scrx) / 1280, (53 * scry) / 1280, this);
            g.setColor(Color.black);
            g.setFont(new Font("Arial", 0, (22 * scrx) / 1280));
            switch(hallofhatselect)
            {
            case 0: // '\0'
                g.drawString("A gift from the magician", (320 * scrx) / 1280, (450 * scry) / 720);
                break;

            case 1: // '\001'
                g.drawString("Dancing with the flow", (320 * scrx) / 1280, (450 * scry) / 720);
                break;

            case 2: // '\002'
                g.drawString("A Witch Wannabe", (320 * scrx) / 1280, (450 * scry) / 720);
                break;

            case 3: // '\003'
                g.drawString("Baby Boy", (320 * scrx) / 1280, (450 * scry) / 720);
                break;

            case 4: // '\004'
                g.drawString("A REAL Gentleman", (320 * scrx) / 1280, (450 * scry) / 720);
                break;

            case 5: // '\005'
                g.drawString("Death Thirsty", (320 * scrx) / 1280, (450 * scry) / 720);
                break;

            case 6: // '\006'
                g.drawString("Mine Maniac", (320 * scrx) / 1280, (450 * scry) / 720);
                break;

            case 7: // '\007'
                g.drawString("Captain O-Captain", (320 * scrx) / 1280, (450 * scry) / 720);
                break;

            case 8: // '\b'
                g.drawString("Space-Time!", (320 * scrx) / 1280, (450 * scry) / 720);
                break;

            case 9: // '\t'
                g.drawString("LaserMan", (320 * scrx) / 1280, (450 * scry) / 720);
                break;

            case 10: // '\n'
                g.drawString("Number 100", (320 * scrx) / 1280, (450 * scry) / 720);
                break;

            case 11: // '\013'
                g.drawString("Number 10", (320 * scrx) / 1280, (450 * scry) / 720);
                break;

            case 12: // '\f'
                g.drawString("Bronze CHEF", (320 * scrx) / 1280, (450 * scry) / 720);
                break;

            case 13: // '\r'
                g.drawString("Silver CHEF", (320 * scrx) / 1280, (450 * scry) / 720);
                break;

            case 14: // '\016'
                g.drawString("GOLDEN CHEF", (320 * scrx) / 1280, (450 * scry) / 720);
                break;

            case 15: // '\017'
                g.drawString("Return of the BAT!", (320 * scrx) / 1280, (450 * scry) / 720);
                break;

            case 16: // '\020'
                g.drawString("Robin the Good", (320 * scrx) / 1280, (450 * scry) / 720);
                break;

            case 17: // '\021'
                g.drawString("The Fire", (320 * scrx) / 1280, (450 * scry) / 720);
                break;

            case 18: // '\022'
                g.drawString("Unwavering Time", (320 * scrx) / 1280, (450 * scry) / 720);
                break;
            }
            g.setFont(new Font("Arial", 0, (28 * scrx) / 1280));
            g.drawString("Hat Unlock Hint :", (150 * scrx) / 1280, (525 * scry) / 720);
            g.setFont(new Font("Arial", 0, (20 * scrx) / 1280));
            switch(hallofhatselect)
            {
            case 0: // '\0'
                g.drawString("It is obviously a gift.", (380 * scrx) / 1280, (525 * scry) / 720);
                break;

            case 1: // '\001'
                g.drawString("Go where the flow calls you.", (380 * scrx) / 1280, (525 * scry) / 720);
                break;

            case 2: // '\002'
                g.drawString("Hug the Witch. She needs it.", (380 * scrx) / 1280, (525 * scry) / 720);
                break;

            case 3: // '\003'
                g.drawString("Survive for a minute.", (380 * scrx) / 1280, (525 * scry) / 720);
                break;

            case 4: // '\004'
                g.drawString("Survive for two minutes.", (380 * scrx) / 1280, (525 * scry) / 720);
                break;

            case 5: // '\005'
                g.drawString("The number of the Horsemen of the Apocalypse is 4 which is where the death awaits you.", (380 * scrx) / 1280, (525 * scry) / 720);
                break;

            case 6: // '\006'
                g.drawString("You ONLY want to be with MINES.", (380 * scrx) / 1280, (525 * scry) / 720);
                break;

            case 7: // '\007'
                g.drawString("Survive for three minutes.", (380 * scrx) / 1280, (525 * scry) / 720);
                break;

            case 8: // '\b'
                g.drawString("Maybe you don't need to go to the space to get it at all.", (380 * scrx) / 1280, (525 * scry) / 720);
                break;

            case 9: // '\t'
                g.drawString("Lasers don't love you but you sure do love Lasers.", (380 * scrx) / 1280, (525 * scry) / 720);
                break;

            case 10: // '\n'
                g.drawString("Get in the first 100 in the Highscores.", (380 * scrx) / 1280, (525 * scry) / 720);
                break;

            case 11: // '\013'
                g.drawString("Get in the first 10 in the Highscores.", (380 * scrx) / 1280, (525 * scry) / 720);
                break;

            case 12: // '\f'
                g.drawString("Become third in the Highscores.", (380 * scrx) / 1280, (525 * scry) / 720);
                break;

            case 13: // '\r'
                g.drawString("Become second in the Highscores.", (380 * scrx) / 1280, (525 * scry) / 720);
                break;

            case 14: // '\016'
                g.drawString("Become FIRST in the Highscores.", (380 * scrx) / 1280, (525 * scry) / 720);
                break;

            case 15: // '\017'
                g.drawString("Survive for FOUR minutes.", (380 * scrx) / 1280, (525 * scry) / 720);
                break;

            case 16: // '\020'
                g.drawString("You are against evil, except when evil benefits you. What else to say?", (380 * scrx) / 1280, (525 * scry) / 720);
                break;

            case 17: // '\021'
                g.drawString("Fire calls for Fire.", (380 * scrx) / 1280, (525 * scry) / 720);
                break;

            case 18: // '\022'
                g.drawString("Just don't get interrupted for enough time...", (380 * scrx) / 1280, (525 * scry) / 720);
                break;
            }
            g.setFont(new Font("Arial", 0, (24 * scrx) / 1280));
            g.drawString("* means that the Hat has special properties.", (150 * scrx) / 1280, (585 * scry) / 720);
        }
        for(int i = 0; i < buttonc; i++)
            button[i].draw(g, scrx, scry);

        if(gamestatus == 7)
        {
            g.setColor(new Color(64, 36, 11));
            g.fillRect((420 * scrx) / 1280, (300 * scry) / 720, (440 * scrx) / 1280, (210 * scry) / 720);
            g.setColor(Color.black);
            g.drawRect((420 * scrx) / 1280, (300 * scry) / 720, (440 * scrx) / 1280, (210 * scry) / 720);
            g.setColor(Color.white);
            g.setFont(new Font("Arial", 0, (20 * scrx) / 1280));
            g.drawString("Are you sure you want to leave?", (510 * scrx) / 1280, (350 * scry) / 720);
            g.drawString("You will lose all your progress.", (515 * scrx) / 1280, (380 * scry) / 720);
            button[17].draw(g, scrx, scry);
            button[18].draw(g, scrx, scry);
        }
        for(int i = 0; i < sliderc; i++)
            slider[i].draw(g, scrx, scry);

        droplist.draw(g, scrx, scry);
        if(gamestatus == -3)
        {
            g.setColor(Color.black);
            g.fillRect(0, 0, scrx, scry);
            g.setColor(Color.white);
            g.setFont(new Font("Arial", 0, (70 * scrx) / 1280));
            g.drawString("Loading...", (480 * scrx) / 1280, (345 * scry) / 720);
        }
    }

    public void playSound(int soundno,boolean iswav){
        if(iswav)playSound(soundno,iswav,0f);else playSound(soundno,iswav,1f);
    }
    public void playSound(int soundno,boolean iswav,float dbinc){
        if(iswav){
        AudioInputStream inputStream = null;
        try {
            float soundvol;
            double soundmas=soundvolume*mastervolume*0.0001;
            if(soundmas==0)soundvol=-200f;else soundvol=(float)( 20*Math.log10(soundmas));
            
            Clip clip = AudioSystem.getClip();
            InputStream instream=this.getClass().getResource(soundpath[soundno][1]).openStream();
            if(instream!=null){
            InputStream bufferedIn = new BufferedInputStream(instream);
            inputStream = AudioSystem.getAudioInputStream(bufferedIn); 
            clip.open(inputStream);
            FloatControl gainControl = 
            (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(basedb[soundno]+dbinc+soundvol); // Reduce volume by 10 decibels.
            clip.start();
            
            inputStream.close();
     //       System.out.println("dut");
            }
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            //ex.printStackTrace();
        }
        }else{
             

            InputStream instream=null;
        try {
            instream = this.getClass().getResource(soundpath[soundno][0]).openStream();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
            if(instream!=null){
                InputStream bufferedIn = new BufferedInputStream(instream);
                BasicPlayer mp3player = new BasicPlayer();
                try {
                    mp3player.open(bufferedIn);
                    mp3player.play();
                    if(ismainsong)mainsong=mp3player;
                    mp3player.setGain(dbinc);
                } catch (BasicPlayerException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public void addText(String s, int x, int y)
    {
        text[textc++] = new Text(s, x, y, Color.black, 16);
    }

    public void addText(String s)
    {
        for(int i = 0; i < textc; i++)
            text[i].shift();

        text[textc++] = new Text(s, 80, 540, Color.black, 20);
    }

    private void doTimeLogic()
    {
        if(player.gametime == 370)
        {
            spawn2 = 224;
            addText("Cannons have started to fire at you from all 4 sides!");
        }
        if(player.gametime == 500)
            unlockHat(0);
        if(player.gametime == 690)
        {
            player.speed = 1.05D;
            addText("Your speed has increased!");
        }
        if(player.gametime == 850)
        {
            spawn7 = 0;
            addText("Land Mines are being planted in the area!");
        }
        if(player.gametime == 1420)
        {
            playSound(4, true);
            int dx = witch.x - hatman.x;
            int dy = witch.y - hatman.y;
            witch.status = 1;
            addText("The Witch is searching for you. Be careful!");
            if(dx * dx + dy * dy <= 19600)
                unlockHat(2);
        }
        if(player.gametime == 2450)
        {
            spawn4 = 0;
            spawn3 = 0;
            addText("Meteors are active now!");
        }
        if(player.gametime == 2850)
        {
            player.speed = 1.1000000000000001D;
            addText("Your speed has increased!");
        }
        if(player.gametime == 3000)
        {
            unlockHat(3);
            if(!player.damaged)
                unlockHat(18);
        }
        if(player.gametime == 3750)
        {
            Parameters.red_ball_speed = 4.0899999999999999D;
            addText("Red Balls are faster now!");
        }
        if(player.gametime == 4500)
        {
            player.mhealth *= 1.5D;
            player.health = player.mhealth;
            addText("You survived for one and a half minutes. Keep up!");
            addText("You are healed and your maximum health has increased.");
        }
        
        if(player.gametime == 5050)
        {
            Parameters.mine_upgraded = true;
            addText("The Land Mines have been upgraded!");
            addText("New mines will deal more damage and slow you now!");
        }
        
        if(player.gametime == 5400)
        {
            player.speed = 1.1499999999999999D;
            addText("Your speed has increased!");
        }

        if(player.gametime == 5820)
        {
            witch.speed = 0.505D;
            witch.status = 2;
            addText("The Witch has gotten angry!");
            addText("She moves faster and can now slow you when you are close!");
        }
        
        if(player.gametime == 6000)
            unlockHat(4);
        
        if(player.gametime == 6350)
        {
            player.speed = 1.2D;
            addText("Your speed has increased!");
        }
        
        if(player.gametime == 6850)
        {
            spawn5 = 0;
            addText("They are attacking with LASERs now!");
        }
        
        if(player.gametime == 7550)
        {
            Parameters.black_bullet_damage = 435;
            Parameters.black_bullet_speed = 19.050000000000001D;
            Parameters.black_bullet_radius = 30;
            Parameters.black_bullet_frequency = 307;
            addText("The Cannons have been upgraded!");
            addText("They are bigger, faster, stronger and fired more frequently now!");
        }
        
        if(player.gametime == 8300)
        {
            Parameters.red_ball_speed = 4.6399999999999997D;
            addText("Red Balls are even faster now!");
        }
        
        if(player.gametime == 8650)
        {
            player.speed = 1.25D;
            addText("Your speed has increased!");
        }
        
        if(player.gametime == 9000)
        {
            player.mhealth *= 1.3333333333333333D;
            player.health = player.mhealth;
            addText("WOW! You have survived for 3 minutes now. I'm Impressed!");
            addText("You are healed and your maximum health has increased.");
            unlockHat(7);
        }
        
        if(player.gametime == 9700)
        {
            Parameters.red_ball_period = Parameters.RED_BALL_UP3_SPAWN_PERIOD;
            Parameters.red_ball_radius = Parameters.RED_BALL_UP3_RADIUS;
            Parameters.red_ball_speed = Parameters.RED_BALL_UP3_SPEED;
            Parameters.red_ball_merge = true;
            addText("The red balls have been upgraded!");
            addText("They are faster, more frequent and can merge now!");
        }
        
        if(player.gametime == 10100)
        {
            player.speed = 1.3D;
            addText("Your speed has increased!");
        }
        
        if(player.gametime == 12000)
            unlockHat(15);
    }

    public void reset()
    {
        if(resetable)
        {
            resetable = false;
            resetdone = false;
            boolean idid = true;
            if(gamestatus == -3)
                idid = false;
            gamestatus = -3;
            map.render(5.0000000000000002E-005D);
            bufferMap();
            darkenMap();
            player.speed = 1.0D;
            speed_bulk = 0.0D;
            switch(difficulty)
            {
            case 0: // '\0'
                player.mhealth = 2000D;
                game_speed = 0.90000000000000002D;
                break;

            case 1: // '\001'
                player.mhealth = 1500D;
                game_speed = 0.94999999999999996D;
                break;

            case 2: // '\002'
                player.mhealth = 1000D;
                game_speed = 1.0D;
                break;
            }
            player.health = player.mhealth;
            player.regen = 0.080000000000000002D + 0.040000000000000001D * (double)(2 - difficulty);
            hatman.color = Color.orange;
            hatman.animsize = 40;
            laserc = 0;
            redballc = 0;
            meteorc = 0;
            blackbulletc = 0;
            potionc = 0;
            sparklec = 0;
            minec = 0;
            spawn = 0;
            spawn2 = 0xfffe7961;
            spawn3 = 0xfffe7961;
            spawn4 = 0xfffe7961;
            spawn5 = 0xfffe7961;
            spawn6 = 0;
            spawn7 = 0xfffe7961;
            player.gametime = 0;
            taunttimer = 0;
            tauntcounter = 0;
            tauntdirection = 1;
            witch = new Witch(200, 800, new Color(128, 0, 128), 0.38500000000000001D);
            int tempxy[][] = new int[2][2];
            tempxy[0][0] = hatman.x;
            tempxy[0][0] = hatman.y;
            tempxy[1][0] = witch.x;
            tempxy[1][1] = witch.y;
            for(int i = 0; i < 2; i++)
            {
                int sx = tempxy[i][0] / 5;
                int sy = tempxy[i][0] / 5;
                for(int j = 0; j < 9; j++)
                {
                    for(int k = 0; k < 9; k++)
                        if((sx + j) - 4 >= 0 && (sx + j) - 4 < 512 && (sy + k) - 4 >= 0 && (sy + k) - 4 < 290)
                            map.map[(sx + j) - 4][(sy + k) - 4] = false;

                }

            }

            burstc = 0;
            hatman.remx = 0.0D;
            player.aroadl2 = 0;
            player.stunned = 0.0D;
            player.modifier = 1.0D;
            player.slowed = 0.0D;
            player.purple_potion_timer = 0.0D;
            player.green_potion_timer = 0.0D;
            player.burn = 0.0D;
            minemaniac = true;
            halvetime = false;
            fire_count = 0;
            meteorcrash = 0;
            weacount = 0;
            potion_count = 0;
            player.score = 0;
            laserdamage = 0.0D;
            resetdone = true;
            Parameters.initialize();
            if(idid)
                gamestatus = 0;
        }
    }

    public void turn()
    {
        if(resetdone)
            resetable = true;
        if(gamestatus == 0 && hatman.charge == 18)
        {
            hatman.anim1++;
            if(hatman.anim1 >= 2)
            {
                hatman.anim1 = 0;
                hatman.anim2++;
                if(hatman.anim2 >= 18000)
                    hatman.anim2 = 0;
            }
        }
        speed_bulk += game_speed;
        boolean dontdo = false;
        if(speed_bulk < 1.0D)
            dontdo = true;
        if(!dontdo)
            speed_bulk--;
        if(player.green_potion_timer > 0.0D && !dontdo)
        {
            halvetime = !halvetime;
            if(halvetime)
                dontdo = true;
        }
        if(!dontdo)
        {
            if(hatman.charge == 17 && gamestatus >= 0)
            {
                if(firesound == null)
                {
                    playSound(8, true);
                } else
                {
                    firesound.loop(-1);
                    double soundmas = (double)(soundvolume * mastervolume) * 0.0001D;
                    if(firesound.isControlSupported(javax.sound.sampled.FloatControl.Type.MASTER_GAIN))
                    {
                        float soundvol;
                        if(soundmas == 0.0D)
                            soundvol = -200F;
                        else
                            soundvol = (float)(20D * Math.log10(soundmas));
                        FloatControl gainControl = (FloatControl)firesound.getControl(javax.sound.sampled.FloatControl.Type.MASTER_GAIN);
                        float res = basedb[8] + soundvol + (float)(hatman.animsize / 2);
                        if(res > -80F)
                        {
                            if(res < 6F)
                                gainControl.setValue(res);
                            else
                                gainControl.setValue(6F);
                        } else
                        {
                            gainControl.setValue(-80F);
                        }
                    }
                    if(!firesound.isActive())
                        firesound.start();
                }
            } else
            if(player.burn >= 1.0D && gamestatus >= 0)
            {
                if(firesound == null)
                {
                    playSound(8, true);
                } else
                {
                    firesound.loop(-1);
                    double soundmas = (double)(soundvolume * mastervolume) * 0.0001D;
                    if(firesound.isControlSupported(javax.sound.sampled.FloatControl.Type.MASTER_GAIN))
                    {
                        float soundvol;
                        if(soundmas == 0.0D)
                            soundvol = -200F;
                        else
                            soundvol = (float)(20D * Math.log10(soundmas));
                        FloatControl gainControl = (FloatControl)firesound.getControl(javax.sound.sampled.FloatControl.Type.MASTER_GAIN);
                        float res = basedb[8] + soundvol + 25F + (float)(5D * player.burn);
                        if(res > -80F)
                        {
                            if(res < 6F)
                                gainControl.setValue(res);
                            else
                                gainControl.setValue(6F);
                        } else
                        {
                            gainControl.setValue(-80F);
                        }
                    }
                    if(!firesound.isActive())
                        firesound.start();
                }
            } else
            if(firesound != null && firesound.isActive())
                firesound.stop();
            for(int i = 0; i < textc; i++)
                if(text[i].tick())
                    text[i] = text[--textc];

            if(clickready > 0)
                clickready--;
            if(gxe == 1)
                gx += (64 * statistic.settings[7]) / 100;
            if(gxe == 2)
                gx -= (64 * statistic.settings[7]) / 100;
            if(gx > 1280)
                gx = 1280;
            if(gx < 0)
                gx = 0;
            if(gye == 1)
                gy += (48 * statistic.settings[7]) / 100;
            if(gye == 2)
                gy -= (48 * statistic.settings[7]) / 100;
            if(gy > 720)
                gy = 720;
            if(gy < 0)
                gy = 0;
            if(gamestatus == 0)
            {
                doTimeLogic();
                taunttimer += 0.5 * tauntdirection;
                int dx = hatman.x - 1280;
                int dy = hatman.y - 720;
//                if(taunttimer % 30 != 0);
                if(dx * dx + dy * dy <= 4 * taunttimer * taunttimer){
                    player.health += player.mhealth * 4.5e-4;
                    if(player.burn >= 0.5){
                        player.burn -= 0.02;
                    } else {
                        player.burn = 0;
                    }
                }
                
                double tolerance = 8;
                if(dx * dx + dy * dy >= 4 * (taunttimer - tolerance) * (taunttimer - tolerance)){
                    if(dx * dx + dy * dy <= 4 * (taunttimer) * (taunttimer)){
                        player.fountain_wave_slow = 10;
                    }
                } else {
                    player.fountain_slow = 5;
                }
                
                if(taunttimer <= 50){
                    taunttimer = 50;
//                    if(tauntcounter >= 0){
//                        tauntcounter++;
//                    }
                    tauntcounter++;
                    if(tauntcounter >= 50){
                        tauntdirection = 1;
                        tauntcounter = 0;
                    } else {
                        tauntdirection = 0;
                    }
                }
                
                if(taunttimer <= 50){
                    if(dx * dx + dy * dy <= 50)
                        unlockHat(1);
                }
                
                if(taunttimer >= 247)
                {
                    taunttimer = 247;
                    tauntcounter++;
                    if(tauntcounter >= 50){
                        tauntdirection = -1;
                        tauntcounter = 0;
                    } else {
                        tauntdirection = 0;
                    }
//                    taunttimer = 0;
//                    if(dx * dx + dy * dy <= 0x3d090)
//                    {
//                        tarx = 0;
//                        taunttimer = 0;
//                        map.reset();
//                        map.finx = 256;
//                        map.finy = 144;
//                        player.finx = map.finx;
//                        player.finy = map.finy;
//                        map.stx = hatman.x / 5;
//                        map.sty = hatman.y / 5;
//                        long startTime = System.nanoTime();
//                        map.solve2(0);
//                        System.out.println("Time passed : " + ((System.nanoTime() - startTime) / 1000));
//                        player.hitDamage(26D);
//                        minemaniac = false;
//                        player.burn = 0.0D;
//                        if(dx * dx + dy * dy <= 25)
//                            unlockHat(1);
//                        if(map.retrace(hatman.x / 5, hatman.y / 5))
//                        {
//                            curroad = 0;
//                            player.aroadl2 = map.aroadl2;
//                            for(int i = 0; i < map.aroadl2; i++)
//                            {
//                                player.aroadk2[i] = map.aroadk2[i];
//                                player.aroadx2[i] = map.aroadx2[i];
//                                player.aroady2[i] = map.aroady2[i];
//                            }
//
//                        }
//                    }
                }
                player.regenerate();
                if(player.purple_potion_timer > 0.0D)
                    player.purple_potion_timer--;
                if(player.green_potion_timer > 0.0D)
                    player.green_potion_timer--;
                if(player.purple_potion_timer == 1.0D || player.green_potion_timer == 1.0D)
                    hatman.color = Color.orange;
                player.gametime++;
                if(tarx != -1)
                    if(map.ready)
                    {
                        for(int i = 0; i < weacount + 1; i++)
                        {
                            if(player.stunned > 0.0D)
                                player.stunned--;
                            if(player.stunned < 0.0D)
                                player.stunned = 0.0D;
                            if(player.slowed > 0.0D)
                                player.slowed--;
                            if(player.laser_stun > 0.0D)
                                player.laser_stun--;
                            if(player.slowed < 0.0D)
                                player.slowed = 0.0D;
                            if(player.fountain_slow > 0.0D)
                                player.fountain_slow--;
                            if(player.fountain_slow < 0.0D)
                                player.fountain_slow = 0.0D;
                            if(player.fountain_wave_slow > 0.0D)
                                player.fountain_wave_slow--;
                            if(player.fountain_wave_slow < 0.0D)
                                player.fountain_wave_slow = 0.0D;
                            if(player.purple_potion_timer > 0){
                                player.slowed = 0;
                                player.fountain_slow = 0;
                                player.fountain_wave_slow = 0;
//                                player.modifier = 1;
                            }
                            double pspeed = player.speed * player.modifier;
                            if(player.slowed != 0.0D)
                                pspeed *= 0.80;
                            if(player.fountain_slow != 0.0D)
                                pspeed *= 0.90;
                            if(player.fountain_wave_slow != 0.0D)
                                pspeed *= 0.70;
//                            if(player.purple_potion_timer > 0.0D)
//                                pspeed *= 0.95;
                            if(player.green_potion_timer > 0.0D)
                                pspeed *= 1.5D;
                            if(player.laser_stun > 7)
                                pspeed *= 8 / player.laser_stun;
                            if(player.stunned == 0.0D)
                                hatman.remx += pspeed;
                            for(boolean f = true; f;)
                                if(curroad < player.aroadl2)
                                {
                                    double tempa = 1.0D;
                                    if(player.aroadk2[curroad] > 3 && player.aroadk2[curroad] < 8)
                                        tempa = 1.4139999999999999D;
                                    if(player.aroadk2[curroad] >= 8 && player.aroadk2[curroad] < 16)
                                        tempa = 2.2360000000000002D;
                                    if(player.aroadk2[curroad] >= 16 && player.aroadk2[curroad] < 24)
                                        tempa = 3.6059999999999999D;
                                    if(hatman.remx >= tempa)
                                    {
                                        int mydir = player.aroadk2[curroad];
                                        hatman.status = mydir;
                                        if(hatman.remy > 0.0D)
                                            hatman.remy--;
                                        hatman.remx -= tempa;
                                        hatman.x = player.aroadx2[curroad] * 5;
                                        hatman.y = player.aroady2[curroad] * 5;
                                        curroad++;
                                    } else
                                    {
                                        f = false;
                                    }
                                } else
                                {
                                    tarx = -1;
                                    f = false;
                                }

                        }

                        weacount = 0;
                    } else
                    {
                        weacount++;
                    }
                if(droplist.dropobject[droplist.current].imagenum == 2 && ran.nextDouble() < 0.34999999999999998D)
                    sparkle[sparklec++] = new Sparkle((hatman.x - 10) + ran.nextInt(20), (hatman.y - 10) + ran.nextInt(20), 8 + ran.nextInt(4), 12 + ran.nextInt(8), 80, null, 8);
                if(droplist.dropobject[droplist.current].imagenum == 8)
                {
                    int mer = -20;
                    if(hatman.stat2 == 1)
                        mer = 20;
                    if(ran.nextDouble() < 0.5D)
                        sparkle[sparklec++] = new Sparkle((hatman.x - 10) + ran.nextInt(20), (hatman.y - 10) + ran.nextInt(20) + mer, 11 + ran.nextInt(8), 18 + ran.nextInt(12), 36, null, 9);
                }
                if(droplist.dropobject[droplist.current].imagenum == 12 && ran.nextDouble() < 0.5D)
                    sparkle[sparklec++] = new Sparkle((hatman.x - 10) + ran.nextInt(20), (hatman.y - 10) + ran.nextInt(20), 4 + ran.nextInt(7), 8 + ran.nextInt(8), 65, new Color(195 + ran.nextInt(20), 117 + ran.nextInt(20), 40 + ran.nextInt(20)), 10);
                if(droplist.dropobject[droplist.current].imagenum == 13 && ran.nextDouble() < 0.40000000000000002D)
                    sparkle[sparklec++] = new Sparkle((hatman.x - 40) + ran.nextInt(80), (hatman.y - 40) + ran.nextInt(80), 12 + ran.nextInt(7), 24 + ran.nextInt(16), 75, new Color(175 + ran.nextInt(30), 175 + ran.nextInt(30), 175 + ran.nextInt(30), 160), 10);
                if(droplist.dropobject[droplist.current].imagenum == 14)
                {
                    for(int i = 0; i < 4; i++)
                    {
                        int colran = ran.nextInt(2);
                        Color rancol = null;
                        switch(colran)
                        {
                        case 0: // '\0'
                            rancol = new Color(230 + ran.nextInt(20), 205 + ran.nextInt(20), 15 + ran.nextInt(20));
                            break;

                        case 1: // '\001'
                            rancol = new Color(235 + ran.nextInt(20), 225 + ran.nextInt(20), 170 + ran.nextInt(20));
                            break;

                        case 2: // '\002'
                            rancol = new Color(190 + ran.nextInt(20), 170 + ran.nextInt(20), 60 + ran.nextInt(20));
                            break;
                        }
                        if(ran.nextDouble() < 0.90000000000000002D)
                            sparkle[sparklec++] = new Sparkle((hatman.x - 10) + ran.nextInt(20), (hatman.y - 10) + ran.nextInt(20), 3 + ran.nextInt(2), 7 + ran.nextInt(6), 36, rancol, 10);
                    }

                }
                if(droplist.dropobject[droplist.current].imagenum == 15 && ran.nextDouble() < 0.5D)
                    sparkle[sparklec++] = new Sparkle((hatman.x - 10) + ran.nextInt(20), (hatman.y - 10) + ran.nextInt(20), 10 + ran.nextInt(4), 10 + ran.nextInt(8), 80, null, 11);
                if(witch.status != 0 && ran.nextDouble() < 0.17000000000000001D)
                    sparkle[sparklec++] = new Sparkle((witch.x - 10) + ran.nextInt(20), (witch.y - 10) + ran.nextInt(20), 12 + ran.nextInt(12), 18 + ran.nextInt(8), 80, null, 8);
                if(droplist.dropobject[droplist.current].imagenum == 9)
                {
                    boolean f = true;
                    int cx = hatman.x / 5;
                    int cy = -2 + hatman.y / 5;
                    do
                    {
                        if(!f)
                            break;
                        switch(hatman.status)
                        {
                        case 0: // '\0'
                            cx--;
                            break;

                        case 1: // '\001'
                            cy--;
                            break;

                        case 2: // '\002'
                            cx++;
                            break;

                        case 3: // '\003'
                            cy++;
                            break;

                        case 4: // '\004'
                            cx--;
                            cy--;
                            break;

                        case 5: // '\005'
                            cx++;
                            cy--;
                            break;

                        case 6: // '\006'
                            cx--;
                            cy++;
                            break;

                        case 7: // '\007'
                            cx++;
                            cy++;
                            break;
                        }
                        if(cx >= 0 && cx < 512 && cy >= 0 && cy < 290)
                        {
                            if(map.map[cx][cy])
                                f = false;
                        } else
                        {
                            f = false;
                        }
                    } while(true);
                    switch(hatman.status)
                    {
                    case 0: // '\0'
                        cx++;
                        break;

                    case 1: // '\001'
                        cy++;
                        break;

                    case 2: // '\002'
                        cx--;
                        break;

                    case 3: // '\003'
                        cy--;
                        break;

                    case 4: // '\004'
                        cx++;
                        cy++;
                        break;

                    case 5: // '\005'
                        cx--;
                        cy++;
                        break;

                    case 6: // '\006'
                        cx++;
                        cy--;
                        break;

                    case 7: // '\007'
                        cx--;
                        cy--;
                        break;
                    }
                    hatman.laserx = cx * 5;
                    hatman.lasery = cy * 5;
                }
                if(droplist.dropobject[droplist.current].imagenum == 1)
                {
                    hatman.laserx = (int) taunttimer;
                    if(taunttimer == 0)
                        hatman.lasery = hatman.lasery != 0 ? 0 : 1;
                }
                spawn++;
                spawn2++;
                spawn3++;
                spawn4++;
                spawn5++;
                spawn7++;
                if(spawn >= Parameters.red_ball_period)
                {
                    spawn = 0;
                    redball[redballc++] = new RedBall(100, 60, Color.red, Parameters.red_ball_radius, Parameters.red_ball_speed);
                    redball[redballc++] = new RedBall(2360, 60, Color.red, Parameters.red_ball_radius, Parameters.red_ball_speed);
                    redball[redballc++] = new RedBall(100, 1380, Color.red, Parameters.red_ball_radius, Parameters.red_ball_speed);
                    redball[redballc++] = new RedBall(2360, 1380, Color.red, Parameters.red_ball_radius, Parameters.red_ball_speed);
                }
                if(spawn2 >= Parameters.black_bullet_frequency)
                {
                    spawn2 = 0;
                    playSound(2, true);
                    dx = hatman.x - 100;
                    dy = hatman.y - 60;
                    double ang = Math.atan2(dy, dx);
                    blackbullet[blackbulletc++] = new BlackBullet(100, 60, Color.darkGray, Parameters.black_bullet_radius, ang, Parameters.black_bullet_speed);
                    dx = hatman.x - 2360;
                    dy = hatman.y - 60;
                    ang = Math.atan2(dy, dx);
                    blackbullet[blackbulletc++] = new BlackBullet(2360, 60, Color.darkGray, Parameters.black_bullet_radius, ang, Parameters.black_bullet_speed);
                    dx = hatman.x - 100;
                    dy = hatman.y - 1380;
                    ang = Math.atan2(dy, dx);
                    blackbullet[blackbulletc++] = new BlackBullet(100, 1380, Color.darkGray, Parameters.black_bullet_radius, ang, Parameters.black_bullet_speed);
                    dx = hatman.x - 2360;
                    dy = hatman.y - 1380;
                    ang = Math.atan2(dy, dx);
                    blackbullet[blackbulletc++] = new BlackBullet(2360, 1380, Color.darkGray, Parameters.black_bullet_radius, ang, Parameters.black_bullet_speed);
                }
                if(spawn4 < 1422)
                {
                    spawn3max = 63;
                } else
                {
                    spawn3max = 6;
                    if(spawn4 == 1872)
                    {
                        ran = new Random();
                        spawn4 = 0;
                    }
                }
                if(spawn3 >= spawn3max)
                {
                    if(spawn3max == 6)
                        metplay++;
                    else
                        metplay = 0;
                    if(metplay >= 3 + metran)
                    {
                        metran = ran.nextInt(7);
                        metplay = 0;
                    }
                    spawn3 = 0;
                    if(spawn3max == 63)
                        meteor[meteorc++] = new Meteor(ran.nextInt(2560), ran.nextInt(1440), new Color(ran.nextInt(255), ran.nextInt(255), ran.nextInt(255)), 93, 5);
                    else
                        meteor[meteorc++] = new Meteor(ran.nextInt(2560), ran.nextInt(1440), new Color(ran.nextInt(255), ran.nextInt(255), ran.nextInt(255)), 88, 4);
                    if(metplay == 0)
                    {
                        dx = (gx + 640) - meteor[meteorc - 1].x;
                        dy = (((gy + 360) - meteor[meteorc - 1].y) * 1280) / 720;
                        double abc = Math.sqrt(dx * dx + dy * dy);
                        if(abc > 350D)
                            playSound(1, true, (float)((34D * (3620D - abc)) / 3270D - 34D));
                        else
                            playSound(1, true);
                    }
                }
                if(spawn5 > 0 && spawn5 <= 15)
                    if(spawn6 < 25)
                    {
                        laser[laserc++] = new Laser(100, 1380, Color.red, Math.atan2(-2260D, 1320D), 10.4D, true);
                        laser[laserc++] = new Laser(2360, 60, Color.red, Math.atan2(2260D, -1320D), 10.4D, true);
                    } else
                    {
                        laser[laserc++] = new Laser(2360, 1380, Color.blue, Math.atan2(-2260D, -1320D), 10.4D, false);
                        laser[laserc++] = new Laser(100, 60, Color.blue, Math.atan2(2260D, 1320D), 10.4D, false);
                    }
                if(spawn5 == 54)
                {
                    spawn6++;
                    spawn5 = 0;
                    if(spawn6 == 50)
                        spawn6 = 0;
                }
                if(spawn7 == 176)
                {
                    spawn7 = 0;
                    int myx = 0;
                    int myy = 0;
                    for(boolean myf = true; myf;)
                    {
                        myx = ran.nextInt(2560);
                        myy = ran.nextInt(1440);
                        myf = false;
                        int mynx = myx / 5;
                        int myny = myy / 5;
                        int i = 0;
                        while(i < 9 && !myf) 
                        {
                            for(int j = 0; j < 9; j++)
                                if((mynx + i) - 4 >= 0 && (mynx + i) - 4 < 512 && (myny + j) - 4 >= 0 && (myny + j) - 4 < 288)
                                {
                                    if(map.map[(mynx + i) - 4][(myny + j) - 4])
                                        myf = true;
                                } else
                                {
                                    myf = true;
                                }

                            i++;
                        }
                    }

                    int mineradi = 48;
                    if(Parameters.mine_upgraded)
                        mineradi = 54;
                    mine[minec++] = new Mine(myx, myy, new Color(150, 75, 0), mineradi, Parameters.mine_upgraded);
                }
                for(int i = 0; i < meteorc; i++)
                    if(meteor[i].tick())
                        meteor[i] = meteor[--meteorc];

                for(int i = 0; i < redballc; i++)
                    if(redball[i].tick())
                        redball[i] = redball[--redballc];

                for(int i = 0; i < blackbulletc; i++)
                    if(blackbullet[i].tick())
                        blackbullet[i] = blackbullet[--blackbulletc];

                for(int i = 0; i < laserc; i++)
                    if(laser[i].tick())
                        laser[i] = laser[--laserc];

                for(int i = 0; i < potionc; i++)
                    if(potion[i].tick())
                        potion[i] = potion[--potionc];

                for(int i = 0; i < minec; i++)
                    if(mine[i].tick())
                        mine[i] = mine[--minec];

                for(int i = 0; i < sparklec; i++)
                    if(sparkle[i].tick())
                        sparkle[i] = sparkle[--sparklec];

                for(int i = 0; i < redballc; i++)
                {
                    if(redball[i] == null)
                        continue;
                    dx = hatman.x - redball[i].x;
                    dy = hatman.y - redball[i].y;
                    double te = Math.sqrt(dx * dx + dy * dy) / redball[i].speed;
                    if(te >= 0.20000000000000001D)
                        redball[i].move((double)dx / te, (double)dy / te);
                    if(dx * dx + dy * dy > redball[i].radius * redball[i].radius){
                        for(int j = 0; j < i && Parameters.red_ball_merge; j++){
                            double dx2 = redball[j].x - redball[i].x;
                            dx2 = dx2 * dx2;
                            double dy2 = redball[j].y - redball[i].y;
                            dy2 = dy2 * dy2;
                            double radj2 = redball[j].radius * redball[j].radius;
                            double radi2 = redball[i].radius * redball[i].radius;
                            if((dx2 + dy2) <= Math.max(radj2, radi2)){
                                double time = redball[i].maxtimer - redball[i].timer;
                                redball[j].increaseMass(redball[i].radius, time);
                                redball[i] = redball[--redballc];
                                redball[redballc] = null;
                                if(i != redballc){
                                    i--;
                                }
                                break;
                            }
                        }
                        continue;
                    }
                    double damage = redball[i].damage;
                    redball[i] = redball[--redballc];
                    redball[redballc] = null;
                    if(i != redballc){
                        i--;
                    }
                    player.hitDamage(damage);
                    hatman.animsize += 3;
                    if(player.burn == 0.0D && ran.nextInt(100) < 12)
                        player.burn = 1.0D;
                    if(player.burn >= 1.0D)
                    {
                        player.burn *= 1.1499999999999999D;
                        if(player.burn >= 5D)
                            player.burn = 5.0D;
                    }
                    minemaniac = false;
                }

                for(int i = 0; i < blackbulletc; i++)
                {
                    if(blackbullet[i] == null)
                        continue;
                    blackbullet[i].move();
                    dx = hatman.x - blackbullet[i].x;
                    dy = hatman.y - blackbullet[i].y;
                    if(dx * dx + dy * dy <= blackbullet[i].radius * blackbullet[i].radius)
                    {
                        deleteBlock(i, 2);
                        player.hitDamage(Parameters.black_bullet_damage);
                        minemaniac = false;
                        continue;
                    }
                    dx = witch.x - blackbullet[i].x;
                    dy = witch.y - blackbullet[i].y;
                    if(dx * dx + dy * dy <= blackbullet[i].radius * blackbullet[i].radius && witch.status != 0 && witch.charge <= 0)
                    {
                        deleteBlock(i, 2);
                        enrageWitch();
                    }
                }

                if(witch.status > 0)
                {
                    witch.timer++;
                    if(witch.timer >= 50 && map.ready)
                    {
                        witch.timer = 0;
                        map.finx = hatman.x / 5;
                        map.finy = hatman.y / 5;
                        map.reset();
                        map.stx = witch.x / 5;
                        map.sty = witch.y / 5;
                        long startTime = System.nanoTime();
                        map.solve5();
                        System.out.println("Time passed Solve5 : " + ((System.nanoTime() - startTime) / 1000));
                        if(map.retrace(map.stx, map.sty))
                        {
                            witch.curroad = 0;
                            witch.aroadl2 = map.aroadl2;
                            for(int j = 0; j < witch.aroadl2; j++)
                            {
                                witch.aroadx2[j] = map.aroadx2[j];
                                witch.aroady2[j] = map.aroady2[j];
                                witch.aroadk2[j] = map.aroadk2[j];
                            }

                        }
                    }
                    if(witch.laserx > 0)
                        witch.laserx--;
                    if(witch.lasery > 0)
                        witch.lasery--;
                    if(witch.lasery == 1)
                        witch.color = new Color(128, 0, 128);
                    if(witch.laserx <= 0 && witch.curroad < witch.aroadl2 && witch.charge < 1)
                        witch.remx += witch.speed;
                    if(witch.laserx <= 0 && witch.lasery > 0 && witch.curroad < witch.aroadl2 && witch.charge < 1)
                        witch.remx += witch.speed;
                    for(boolean f = true; f;)
                        if(witch.curroad < witch.aroadl2)
                        {
                            double tempa = 1.0D;
                            if(witch.aroadk2[witch.curroad] > 3 && witch.aroadk2[witch.curroad] < 8)
                                tempa = 1.4139999999999999D;
                            if(witch.aroadk2[witch.curroad] >= 8)
                                tempa = 2.2360000000000002D;
                            if(witch.remx >= tempa)
                            {
                                witch.remx -= tempa;
                                dx = witch.x - witch.aroadx2[witch.curroad] * 5;
                                dy = witch.y - witch.aroady2[witch.curroad] * 5;
                                if(dx * dx + dy * dy >= 900)
                                {
                                    witch.timer = 1000;
                                    System.out.println("An error has occurred type:1");
                                    System.out.println((new StringBuilder()).append("cur:").append(witch.curroad).toString());
                                    System.out.println((new StringBuilder()).append("aroadl2:").append(witch.aroadl2).toString());
                                } else
                                {
                                    witch.x = witch.aroadx2[witch.curroad] * 5;
                                    witch.y = witch.aroady2[witch.curroad] * 5;
                                    witch.curroad++;
                                }
                            } else
                            {
                                f = false;
                            }
                        } else
                        {
                            f = false;
                        }

                    dx = witch.x - hatman.x;
                    dy = witch.y - hatman.y;
                    if(dx * dx + dy * dy <= 19881)
                    {
                        if(witch.charge == 0)
                        {
                            witch.charge = 1;
                            playSound(4, true);
                        }
                    } else
                    if(witch.charge == -1)
                        witch.charge = 0;
                    if(witch.charge > 0)
                        witch.charge++;
                    if(witch.charge < 55 && dx * dx + dy * dy <= 141 * 141)
                    {
//                        minemaniac = false;
                        player.hitDamage(1.25D + (double)witch.charge * 0.043D);
                        if(ran.nextInt(100) < 15 && player.burn == 0.0D)
                            player.burn = 1.0D;
                        if(player.burn >= 1.0D)
                        {
                            player.burn += 0.015;
                            if(player.burn > 5D)
                                player.burn = 5D;
                        }
                    }
                    if(witch.charge > 55 && witch.charge < 95 && dx * dx + dy * dy <= 184 * 184)
                    {
//                        minemaniac = false;
                        player.hitDamage(3.85D);
                        if(ran.nextInt(100) < 15 && player.burn == 0.0D)
                            player.burn = 1.0D;
                        if(player.burn >= 1.0D)
                        {
                            player.burn += 0.025D;
                            if(player.burn > 5D)
                                player.burn = 5D;
                        }
                    }
                    if(witch.charge == 55)
                    {
                        if(witch.lasery > 0)
                            burstMap(witch.x, witch.y, 193);
                        scorchMap(witch.x, witch.y, 193);
                        if(dx * dx + dy * dy <= 193 * 193)
                        {
//                            minemaniac = false;
                            player.hitDamage(307D);
                            if(player.burn <= 1){
                                player.burn = 1;
                            } else {
                                player.burn += 0.5;
                            }
//                            player.burn++;
                            if(player.burn > 5D)
                                player.burn = 5D;
                            fire_count++;
                            hatman.animsize += 5;
                            if(fire_count >= 1 && player.burn >= 5)
                                unlockHat(17);
                        }
                    }
                    if(witch.charge == 95)
                        witch.charge = -1;
                    if(witch.status == 2)
                        if(dx * dx + dy * dy <= 0x10810)
                            player.modifier = 0.85;
                        else
                            player.modifier = 1.0D;
                }
                
                for(int i = 0; i < meteorc; i++)
                {
                    if(meteor[i] == null || meteor[i].timer != 70)
                        continue;
                    dx = meteor[i].x - hatman.x;
                    dy = meteor[i].y - hatman.y;
                    if(dx * dx + dy * dy <= 10000 && meteor[i].type == 4 || dx * dx + dy * dy <= 19600 && meteor[i].type == 5)
                    {
                        player.stunned = meteor[i].maxtimer - 65;
                        meteorcrash++;
                        if(meteorcrash == 3)
                            unlockHat(8);
                    }
                    dx = witch.x - meteor[i].x;
                    dy = witch.y - meteor[i].y;
                    if(dx * dx + dy * dy <= 10000 && meteor[i].type == 4 || dx * dx + dy * dy <= 19600 && meteor[i].type == 5)
                        witch.laserx = meteor[i].maxtimer - 65;
                }

                for(int i = 0; i < laserc; i++)
                {
                    if(laser[i] == null)
                        continue;
                    laser[i].move();
                    dx = hatman.x - laser[i].x;
                    dy = hatman.y - laser[i].y;
                    double dr = Math.sqrt(dx * dx + dy * dy);
                    double alfa = 2D * Math.acos(dr / (double)(2 * laser[i].radius));
                    double por = (alfa - Math.sin(alfa)) / 6.2831853071795862D;
                    if(dr >= (double)(laser[i].radius * 2))
                        continue;
//                    minemaniac = false;
                    double damage = 10D;
                    if(laser[i].burns){
                        damage = 50D;
                        if(player.burn <= 1) {
                            if(ran.nextInt(1000) < 600 * por)
                                player.burn = 1;
                        } else {
                           player.burn += 0.4 * por;
                           if(player.burn > 5){
                               player.burn = 5;
                           }
                        }
                    } else {
                        if(player.laser_stun <= 35){
                            player.laser_stun += 5 * por;
                        }
                    }
                    player.hitDamage(damage * por);
                    laserdamage += damage * por;
                    if(laserdamage >= 750D)
                        unlockHat(9);
                }

                for(int i = 0; i < minec; i++)
                {
                    if(mine[i] == null)
                        continue;
                    dx = hatman.x - mine[i].x;
                    dy = hatman.y - mine[i].y;
                    if(dx * dx + dy * dy <= mine[i].radius * mine[i].radius && mine[i].charge == -1)
                        mine[i].charge = 0;
                    int dx2 = mine[i].x - witch.x;
                    int dy2 = mine[i].y - witch.y;
                    if(dx2 * dx2 + dy2 * dy2 <= 37249 && mine[i].charge == -1 && witch.charge >= 55)
                        mine[i].charge = 0;
                    int ddx;
                    int ddy;
                    if(mine[i].charge == 0)
                    {
                        ddx = (gx + 640) - mine[i].x;
                        ddy = (((gy + 360) - mine[i].y) * 1280) / 720;
                        double abc = Math.sqrt(ddx * ddx + ddy * ddy);
                        if(abc > 350D)
                            playSound(3, true, (float)((50D * (3620D - abc)) / 3270D - 50D));
                        else
                            playSound(3, true);
                    }
                    ddx = dx;
                    ddy = dy - 10;
                    if(hatman.stat == 1)
                        ddx += 20;
                    else
                        ddx -= 20;
                    if(ddx * ddx + ddy * ddy <= 0x12769 && (ddx > 0 && hatman.stat == 0 || ddx < 0 && hatman.stat == 1))
                    {
                        double te = (ddx * ddx + ddy * ddy) - mine[i].radius * mine[i].radius;
                        if(te > 0.0D)
                        {
                            double n = Math.sqrt(te);
                            double ang1 = Math.atan2(ddy, -1 * ddx);
                            double ang2 = Math.atan2(mine[i].radius, n);
                            if(hatman.remy <= 0.0D || hatman.mine1 > n)
                            {
                                hatman.mine1 = n;
                                hatman.mine2 = ((ang1 - ang2) * 180D) / 3.1415926535897931D;
                                hatman.mine3 = (ang2 * 2D * 180D) / 3.1415926535897931D;
                            }
                        }
                        hatman.remy = 1.0D;
                    }
                    if(mine[i].charge >= 0)
                        mine[i].charge++;
                    if(mine[i].charge == 25)
                    {
                        int myrad = 130;
                        if(mine[i].isupgraded)
                            myrad = 150;
                        scorchMap(mine[i].x, mine[i].y, myrad);
                        if(dx * dx + dy * dy <= myrad * myrad)
                        {
                            player.hitDamage(185D);
                            hatman.animsize += 2;
                            if(mine[i].isupgraded)
                            {
                                player.slowed = 75D;
                                player.hitDamage(60D);
                            } else
                            if(ran.nextInt(50) < 25)
                            {
                                player.burn++;
                                if(player.burn > 5D)
                                    player.burn = 5D;
                            }
                        }
                        if(dx2 * dx2 + dy2 * dy2 <= myrad * myrad && witch.charge <= 0 && witch.status != 0)
                            enrageWitch();
                        if(dx * dx + dy * dy <= mine[i].radius * mine[i].radius)
                        {
                            hatman.animsize++;
                            player.hitDamage(180D);
                            if(mine[i].isupgraded)
                            {
                                player.hitDamage(60D);
                                player.slowed = 75D;
                            } else
                            if(ran.nextInt(50) < 40)
                            {
                                player.burn++;
                                if(player.burn > 5D)
                                    player.burn = 5D;
                            }
                        }
                        for(int j = 0; j < minec; j++)
                        {
                            if(i == j)
                                continue;
                            dx2 = mine[i].x - mine[j].x;
                            dy2 = mine[i].y - mine[j].y;
                            if(Math.sqrt(dx2 * dx2 + dy2 * dy2) <= (double)myrad && mine[j].charge == -1)
                                mine[j].charge = 0;
                        }

                    }
                    if(mine[i].charge == 50)
                        mine[i].timer = mine[i].maxtimer;
                    if(mine[i].charge == -1 && (double)mine[i].timer >= (double)mine[i].maxtimer * 0.94999999999999996D)
                        mine[i].charge = 0;
                }

                for(int i = 0; i < sparklec; i++)
                {
                    if(sparkle[i] == null)
                        continue;
                    sparkle[i].status++;
                    if(sparkle[i].status > sparkle[i].charge * 2)
                        sparkle[i].status = 0;
                }

                for(int i = 0; i < potionc; i++)
                {
                    if(potion[i] == null)
                        continue;
                    if(potion[i].type == 12)
                    {
                        dx = hatman.x - potion[i].x;
                        dy = hatman.y - potion[i].y;
                        if(dx * dx + dy * dy > 1600)
                            continue;
                        hatman.color = new Color(102, 10, 102);
//                        player.regenerate(50D + (25D * player.mhealth) / 100D);
                        player.purple_potion_timer = 400D;
                        potion_count++;
                        if(potion_count == 2)
                            unlockHat(16);
                        deleteBlock(i, 5);
                        continue;
                    }
                    if(potion[i].type != 13)
                        continue;
                    dx = hatman.x - potion[i].x;
                    dy = hatman.y - potion[i].y;
                    if(dx * dx + dy * dy > 1600)
                        continue;
                    hatman.color = new Color(5, 194, 5);
                    player.green_potion_timer = 300D;
                    potion_count++;
                    if(potion_count == 3)
                        unlockHat(16);
                    deleteBlock(i, 5);
                }

                if(player.health <= 0.0D)
                {
                    if(droplist.dropobject[droplist.current].imagenum == 5)
                        playSound(6, true);
                    gamestatus = -3;
                    boolean f = false;
                    dx = hatman.x - 100;
                    dy = hatman.y - 60;
                    if(dx * dx + dy * dy <= 2500)
                        f = true;
                    dx = hatman.x - 2360;
                    dy = hatman.y - 60;
                    if(dx * dx + dy * dy <= 2500)
                        f = true;
                    dx = hatman.x - 2360;
                    dy = hatman.y - 1380;
                    if(dx * dx + dy * dy <= 2500)
                        f = true;
                    dx = hatman.x - 100;
                    dy = hatman.y - 1380;
                    if(dx * dx + dy * dy <= 2500)
                        f = true;
                    if(f)
                        unlockHat(5);
                    if(minemaniac)
                        unlockHat(6);
                    adjustStatistics();
                    player.score = (int)((double)(4 * player.gametime) * (0.5D + (double)difficulty * 0.25D));
                    Thread abc = new Thread() {

                        public void run()
                        {
                            if(dab.connected)
                                dab.getScores();
                            gamestatus = 1;
                            if(dab.connected)
                                if(dab.count == 100)
                                {
                                    if(player.score > dab.score[dab.count - 1])
                                    {
                                        unlockHat(10);
                                        for(int j = 7; j < 9; j++)
                                            button[j].displayed = true;

                                        gamestatus = 2;
                                        myposition = dab.getMyPosition(player.score);
                                        if(myposition <= 9)
                                            unlockHat(11);
                                        if(myposition <= 2)
                                            unlockHat(12);
                                        if(myposition <= 1)
                                            unlockHat(13);
                                        if(myposition <= 0)
                                            unlockHat(14);
                                    }
                                } else
                                {
                                    unlockHat(10);
                                    for(int j = 7; j < 9; j++)
                                        button[j].displayed = true;

                                    gamestatus = 2;
                                    myposition = dab.getMyPosition(player.score);
                                    if(myposition <= 10)
                                        unlockHat(11);
                                    if(myposition <= 2)
                                        unlockHat(12);
                                    if(myposition <= 1)
                                        unlockHat(13);
                                    if(myposition <= 0)
                                        unlockHat(14);
                                }
                        }

                        final SurviveBoard this$0;

            
            {
                this.this$0 = SurviveBoard.this;
                //super();
            }
                    }
;
                    abc.start();
                }
            }
        }
//        repaint();
    }

    Statistics statistic;
    String soundpath[][];
    float basedb[];
    Random ran;
    RedBall redball[];
    BlackBullet blackbullet[];
    Meteor meteor[];
    Mine mine[];
    Sparkle sparkle[];
    Laser laser[];
    Potion potion[];
    Witch witch;
    Hatman hatman;
    Text text[];
    Button button[];
    Slider slider[];
    DropList droplist;
    TextField textfield;
    int redballc;
    int blackbulletc;
    int tauntdirection;
    int meteorc;
    int minec;
    int sparklec;
    int laserc;
    int potionc;
    int sliderc;
    int buttonc;
    int textc;
    int tarx;
    int tary;
    int scrx;
    int scry;
    int mainsongtime;
    BasicPlayer mainsong;
    Dbcon dab;
    BufferedImage off_Image;
    BufferedImage map_Image;
    BufferedImage darker_map_Image;
    BufferedImage mini_Image;
    Graphics2D g2;
    Graphics2D mg2;
    Graphics2D dmg2;
    Graphics2D ig2;
    int curroad;
    int weacount;
    int spawn;
    int spawn2;
    int spawn3;
    int spawn4;
    int spawn5;
    int spawn6;
    int spawn7;
    int spawn3max;
    Player player;
    int gamestatus;
    double taunttimer;
    double tauntcounter;
    int gx;
    int gy;
    int gxe;
    int gye;
    int clickready;
    boolean mapmouse;
    int metplay;
    int metran;
    int fps;
    int fps2;
    int difficulty;
    int paramreturn;
    int mastervolume;
    int musicvolume;
    int soundvolume;
    int myposition;
    int meteorcrash;
    int hallofhatselect;
    int potion_count;
    int fire_count;
    double game_speed;
    double speed_bulk;
    int burstX[];
    int burstY[];
    int burstR[];
    int burstc;
    Clip firesound;
    double laserdamage;
    boolean showminimap;
    boolean showtrace;
    boolean stopped;
    boolean minemaniac;
    boolean resetdone;
    boolean resetable;
    boolean ismainsong;
    boolean halvetime;
    Map map;
    static BufferedImage flower[] = new BufferedImage[10];
    int flowermap[][];

}