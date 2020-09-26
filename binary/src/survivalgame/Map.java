

package survivalgame;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.PrintStream;
import java.util.ArrayDeque;

// Referenced classes of package survivalgame:
//            FibonacciHeap

public final class Map
{

    public Map(int sizex, int sizey)
    {
        roadx = new short[0x7a120];
        roady = new short[0x7a120];
        roadk = new short[0x7a120];
        aroadx = new short[0x7a120];
        aroady = new short[0x7a120];
        aroadk = new short[0x7a120];
        aroadx2 = new short[0x7a120];
        aroady2 = new short[0x7a120];
        aroadk2 = new short[0x7a120];
        nodes = new ArrayDeque(10000);
        nodes2 = new ArrayDeque(10000);
        fh = new FibonacciHeap();
        r = 5;
        chg = 0;
        radial = 4;
        total = 0;
        ready = true;
        call = 0;
        roadl = -1;
        finx = 150;
        finy = 100;
        this.sizex = sizex;
        this.sizey = sizey;
        map = new boolean[sizex][sizey];
        mem1 = new short[sizex][sizey];
        mem2 = new int[sizex][sizey];
        mem3 = new FibonacciHeap.Entry[sizex][sizey];
        mem4 = new boolean[sizex][sizey];
        mem5 = new double[sizex][sizey];
        for(int i = 0; i < sizex; i++)
        {
            for(int j = 0; j < sizey; j++)
            {
                mem1[i][j] = -1;
                mem2[i][j] = -1;
                mem4[i][j] = false;
                mem5[i][j] = -1D;
            }

        }

        randomize(0.00069999999999999999D);
        stx = 234;
        sty = 201;
        mindist = -1D;
        System.out.println((new StringBuilder()).append("Turnx :").append(mem1[finx][finy]).toString());
        System.out.println((new StringBuilder()).append("Turny :").append(mem2[stx][sty]).toString());
    }

    public void reset()
    {
        roadl = -1;
        aroadl = 0;
        ready = false;
        call = 0;
        nodes.clear();
        nodes2.clear();
        fh = new FibonacciHeap();
        for(int i = 0; i < sizex; i++)
        {
            for(int j = 0; j < sizey; j++)
            {
                mem1[i][j] = -1;
                mem2[i][j] = -1;
                mem4[i][j] = false;
                mem3[i][j] = null;
                mem5[i][j] = -1D;
            }

        }

    }

    public void randomize(double portion)
    {
        for(int i = 0; i < sizex; i++)
        {
            for(int j = 0; j < sizey; j++)
                if(Math.random() <= portion)
                    map[i][j] = true;
                else
                    map[i][j] = false;

        }

        for(int i = 80; i < 84; i++)
        {
            for(int j = 40; j < 140; j++)
                if(Math.random() <= 0.98999999999999999D)
                    map[i][j] = true;

        }

        map[finx][finy] = false;
        map[20][20] = false;
    }

    public void render(double portion)
    {
        int blackrgb = Color.black.getRGB();
        for(int i = 0; i < sizex; i++)
        {
            for(int j = 0; j < sizey; j++)
            {
                if(mymap.getRGB(i, j) == blackrgb)
                {
                    map[i][j] = true;
                    continue;
                }
                if(Math.random() <= portion)
                    map[i][j] = true;
                else
                    map[i][j] = false;
            }

        }

    }

    public void solve2(int level)
    {
        boolean end = true;
        if(level == 0)
        {
            total = 0;
            roadx[0] = (short)finx;
            roady[0] = (short)finy;
            roadk[0] = 0;
            roadl = 1;
            aroadl = 0;
            mem1[finx][finy] = 0;
        } else
        {
            aroadl = 0;
            for(int j = 0; j < roadl && end; j++)
            {
                int x = roadx[j];
                int y = roady[j];
                boolean dd = true;
                for(int n = 0; n < 2 * radial - 1 && dd; n++)
                {
                    for(int m = 0; m < 2 * radial - 1 && dd; m++)
                        if(((x + n) - radial) + 1 >= 0 && ((x + n) - radial) + 1 < sizex && ((y + m) - radial) + 1 >= 0 && ((y + m) - radial) + 1 < sizey && map[((x + n) - radial) + 1][((y + m) - radial) + 1])
                            dd = false;

                }

                for(int i = 0; i < 8 && end && dd; i++)
                {
                    x = roadx[j];
                    y = roady[j];
                    switch(i)
                    {
                    case 0: // '\0'
                        x++;
                        break;

                    case 1: // '\001'
                        y++;
                        break;

                    case 2: // '\002'
                        x--;
                        break;

                    case 3: // '\003'
                        y--;
                        break;

                    case 4: // '\004'
                        x++;
                        y++;
                        break;

                    case 5: // '\005'
                        x--;
                        y++;
                        break;

                    case 6: // '\006'
                        x++;
                        y--;
                        break;

                    case 7: // '\007'
                        x--;
                        y--;
                        break;
                    }
                    if(x < 0 || y < 0 || x >= sizex || y >= sizey || map[x][y])
                        continue;
                    boolean d = true;
                    if(mem1[x][y] != -1 || !d)
                        continue;
                    mem1[x][y] = (short)level;
                    aroadx[aroadl] = (short)x;
                    aroady[aroadl] = (short)y;
                    aroadl++;
                    mem2[x][y] = (short)i;
                    if(x == stx && y == sty)
                        end = false;
                }
            }

            for(int i = 0; i < aroadl; i++)
            {
                roadx[i] = aroadx[i];
                roady[i] = aroady[i];
            }

            roadl = aroadl;
        }
        if(roadl != 0 && end)
            solve2(level + 1);
    }

    public synchronized void solve4(int level)
    {
        boolean end = true;
        if(level == 0)
        {
            nodes.add(Integer.valueOf(finx + finy * sizex));
            mem1[finx][finy] = -1;
            mem2[finx][finy] = -1;
            mem4[finx][finy] = true;
        } else
        {
            while(!nodes.isEmpty() && end) 
            {
                call++;
                int s = ((Integer)nodes.pop()).intValue();
                int x = s % sizex;
                int y = s / sizex;
                boolean dd = true;
                for(int n = 0; n < 2 * radial - 1 && dd; n++)
                {
                    for(int m = 0; m < 2 * radial - 1 && dd; m++)
                        if(((x + n) - radial) + 1 >= 0 && ((x + n) - radial) + 1 < sizex && ((y + m) - radial) + 1 >= 0 && ((y + m) - radial) + 1 < sizey && map[((x + n) - radial) + 1][((y + m) - radial) + 1])
                            dd = false;

                }

                int i = 0;
                while(i < 8 && end && dd) 
                {
                    int x2 = x;
                    int y2 = y;
                    switch(i)
                    {
                    case 0: // '\0'
                        x2++;
                        break;

                    case 1: // '\001'
                        y2++;
                        break;

                    case 2: // '\002'
                        x2--;
                        break;

                    case 3: // '\003'
                        y2--;
                        break;

                    case 4: // '\004'
                        x2++;
                        y2++;
                        break;

                    case 5: // '\005'
                        x2--;
                        y2++;
                        break;

                    case 6: // '\006'
                        x2++;
                        y2--;
                        break;

                    case 7: // '\007'
                        x2--;
                        y2--;
                        break;

                    case 8: // '\b'
                        x += 2;
                        y++;
                        break;

                    case 9: // '\t'
                        x += 2;
                        y--;
                        break;

                    case 10: // '\n'
                        x -= 2;
                        y++;
                        break;

                    case 11: // '\013'
                        x -= 2;
                        y--;
                        break;

                    case 12: // '\f'
                        x++;
                        y += 2;
                        break;

                    case 13: // '\r'
                        x++;
                        y -= 2;
                        break;

                    case 14: // '\016'
                        x--;
                        y += 2;
                        break;

                    case 15: // '\017'
                        x--;
                        y -= 2;
                        break;
                    }
                    if(x2 >= 0 && y2 >= 0 && x2 < sizex && y2 < sizey && !map[x2][y2])
                    {
                        boolean d = true;
                        if(!mem4[x2][y2])
                        {
                            mem1[x2][y2] = (short)level;
                            mem2[x2][y2] = (short)i;
                            mem4[x2][y2] = true;
                            nodes2.add(Integer.valueOf(x2 + y2 * sizex));
                            if(x2 == stx && y2 == sty)
                                end = false;
                        }
                    }
                    i++;
                }
            }
            ArrayDeque temp = nodes;
            nodes = nodes2;
            nodes2 = temp;
        }
        if(!nodes.isEmpty() && end)
        {
            solve4(level + 1);
        } else
        {
            System.out.println((new StringBuilder()).append("call : ").append(call).toString());
            call = 0;
        }
    }

    public synchronized void solve5()
    {
        boolean end = true;
        double dx = Math.abs(finx - stx);
        double dy = Math.abs(finy - sty);
        double h = Math.min(dx, dy) * 0.41399999999999998D + Math.max(dx, dy);
        mem3[finx][finy] = fh.enqueue(Integer.valueOf(finx + sizex * finy), h);
        mem2[finx][finy] = -1;
        mem5[finx][finy] = 0.0D;
       // System.out.println((new StringBuilder()).append("Finish x : ").append(finx).append(" y : ").append(finy).toString());
        //System.out.println((new StringBuilder()).append("Start x : ").append(stx).append(" y : ").append(sty).toString());
        int call = 0;
        int iteration = 0;
        while(!fh.isEmpty() && end) 
        {
            call++;
            FibonacciHeap.Entry en = fh.dequeueMin();
            int s = ((Integer)en.getValue()).intValue();
            int x = s % sizex;
            int y = s / sizex;
            if(x == stx && y == sty)
            {
                end = false;
                System.out.println("Found");
            }
            mem4[x][y] = true;
            boolean dd = true;
            for(int n = 0; n < 2 * radial - 1 && dd; n++)
            {
                for(int m = 0; m < 2 * radial - 1 && dd; m++)
                    if(((x + n) - radial) + 1 >= 0 && ((x + n) - radial) + 1 < sizex && ((y + m) - radial) + 1 >= 0 && ((y + m) - radial) + 1 < sizey && map[((x + n) - radial) + 1][((y + m) - radial) + 1])
                        dd = false;

            }

            int i = 0;
            while(i < 8 && end && dd) 
            {
                iteration++;
                int x2 = x;
                int y2 = y;
                switch(i)
                {
                case 0: // '\0'
                    x2++;
                    break;

                case 1: // '\001'
                    y2++;
                    break;

                case 2: // '\002'
                    x2--;
                    break;

                case 3: // '\003'
                    y2--;
                    break;

                case 4: // '\004'
                    x2++;
                    y2++;
                    break;

                case 5: // '\005'
                    x2--;
                    y2++;
                    break;

                case 6: // '\006'
                    x2++;
                    y2--;
                    break;

                case 7: // '\007'
                    x2--;
                    y2--;
                    break;

                case 8: // '\b'
                    x2 += 2;
                    y2++;
                    break;

                case 9: // '\t'
                    x2 += 2;
                    y2--;
                    break;

                case 10: // '\n'
                    x2 -= 2;
                    y2++;
                    break;

                case 11: // '\013'
                    x2 -= 2;
                    y2--;
                    break;

                case 12: // '\f'
                    x2++;
                    y2 += 2;
                    break;

                case 13: // '\r'
                    x2++;
                    y2 -= 2;
                    break;

                case 14: // '\016'
                    x2--;
                    y2 += 2;
                    break;

                case 15: // '\017'
                    x2--;
                    y2 -= 2;
                    break;

                case 16: // '\020'
                    x2 += 3;
                    y2 += 2;
                    break;

                case 17: // '\021'
                    x2 += 3;
                    y2 -= 2;
                    break;

                case 18: // '\022'
                    x2 -= 3;
                    y2 += 2;
                    break;

                case 19: // '\023'
                    x2 -= 3;
                    y2 -= 2;
                    break;

                case 20: // '\024'
                    x2 += 2;
                    y2 += 3;
                    break;

                case 21: // '\025'
                    x2 += 2;
                    y2 -= 3;
                    break;

                case 22: // '\026'
                    x2 -= 2;
                    y2 += 3;
                    break;

                case 23: // '\027'
                    x2 -= 2;
                    y2 -= 3;
                    break;
                }
                double kk = 1.0D;
                if(i >= 4 && i < 8)
                    kk = 1.415D;
                if(i >= 8 && i < 16)
                    kk = 2.2360000000000002D;
                if(i >= 16 && i < 24)
                    kk = 3.6059999999999999D;
                if(x2 >= 0 && y2 >= 0 && x2 < sizex && y2 < sizey && !map[x2][y2] && !mem4[x2][y2])
                {
                    dx = Math.abs(x2 - stx);
                    dy = Math.abs(y2 - sty);
                    h = Math.min(dx, dy) * 0.41399999999999998D + Math.max(dx, dy);
                    double pr = mem5[x][y] + kk + h;
                    if(mem3[x2][y2] != null)
                    {
                        if(mem3[x2][y2].getPriority() > pr)
                        {
                            fh.decreaseKey(mem3[x2][y2], pr);
                            mem2[x2][y2] = i;
                            mem5[x2][y2] = mem5[x][y] + kk;
                        }
                    } else
                    {
                        mem3[x2][y2] = fh.enqueue(Integer.valueOf(x2 + y2 * sizex), pr);
                        mem2[x2][y2] = i;
                        mem5[x2][y2] = mem5[x][y] + kk;
                    }
                }
                i++;
            }
        }
        System.out.println("Iteration : " + iteration);
       // System.out.println((new StringBuilder()).append("call : ").append(call).toString());
    }

    public void solve3(int level)
    {
        if(level == 0)
        {
            roadx[0] = (short)finx;
            roady[0] = (short)finy;
            roadk[0] = 0;
            roadl = 1;
            aroadl = 0;
            mem1[stx][sty] = 0;
        }
    }

    public synchronized boolean retrace(int stx, int sty)
    {
        boolean f = true;
        boolean d = true;
        int cx = stx;
        int cy = sty;
        if(mem2[cx][cy] != -1)
        {
            aroadl2 = 0;
        } else
        {
            f = false;
            d = false;
        }
        int iterate = 0;
        do
        {
            if(!f)
                break;
            if(cx >= 0 && cy >= 0 && cx < sizex && cy < sizey)
            {
                aroadx2[aroadl2] = (short)cx;
                aroady2[aroadl2] = (short)cy;
                aroadk2[aroadl2] = (short)mem2[cx][cy];
                aroadl2++;
                if(++iterate >= 0x249f0)
                {
                    f = false;
                    aroadl2 = 0;
                    System.out.println("THAT HAPPENED!!");
                }
                switch(mem2[cx][cy])
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

                case 8: // '\b'
                    cx -= 2;
                    cy--;
                    break;

                case 9: // '\t'
                    cx -= 2;
                    cy++;
                    break;

                case 10: // '\n'
                    cx += 2;
                    cy--;
                    break;

                case 11: // '\013'
                    cx += 2;
                    cy++;
                    break;

                case 12: // '\f'
                    cx--;
                    cy -= 2;
                    break;

                case 13: // '\r'
                    cx--;
                    cy += 2;
                    break;

                case 14: // '\016'
                    cx++;
                    cy -= 2;
                    break;

                case 15: // '\017'
                    cx++;
                    cy += 2;
                    break;

                case 16: // '\020'
                    cx -= 3;
                    cy -= 2;
                    break;

                case 17: // '\021'
                    cx -= 3;
                    cy += 2;
                    break;

                case 18: // '\022'
                    cx += 3;
                    cy -= 2;
                    break;

                case 19: // '\023'
                    cx += 3;
                    cy += 2;
                    break;

                case 20: // '\024'
                    cx -= 2;
                    cy -= 3;
                    break;

                case 21: // '\025'
                    cx -= 2;
                    cy += 3;
                    break;

                case 22: // '\026'
                    cx += 2;
                    cy -= 3;
                    break;

                case 23: // '\027'
                    cx += 2;
                    cy += 3;
                    break;
                }
                if(mem2[cx][cy] == -1)
                    f = false;
            } else
            {
                f = false;
            }
        } while(true);
        ready = true;
        return d;
    }

    boolean map[][];
    int sizex;
    int sizey;
    short roadx[];
    short roady[];
    short roadk[];
    short aroadx[];
    short aroady[];
    short aroadk[];
    short aroadx2[];
    short aroady2[];
    short aroadk2[];
    int roadl;
    int aroadl;
    int aroadl2;
    short mem1[][];
    int mem2[][];
    FibonacciHeap.Entry mem3[][];
    boolean mem4[][];
    double mem5[][];
    double mindist;
    ArrayDeque nodes;
    ArrayDeque nodes2;
    FibonacciHeap fh;
    int finx;
    int finy;
    int r;
    int chg;
    int radial;
    int stx;
    int sty;
    int total;
    boolean ready;
    protected static BufferedImage mymap;
    int call;
}