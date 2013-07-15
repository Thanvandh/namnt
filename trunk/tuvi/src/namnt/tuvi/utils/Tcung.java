package namnt.tuvi.utils;

import android.graphics.Canvas;
import android.graphics.Color;



class Tcung extends Canvas
  implements StarConst
{
  String name;
  String hanstr;
  int chi;
  int numsao;
  int[] sao = new int[100];
  boolean tuan;
  boolean triet;
  Color mycolor = new Color();
  public void paint(Graphics g)
  {
    g.setColor(StarConst.nguhanhcolor[(StarConst.nguhanhcung[this.chi] - 2)]);
    g.drawRect(4, 4, 192, 152);
    g.drawRect(3, 3, 194, 154);
    g.setColor(Color.blue);
    g.drawString(this.name, 10, 15);
    if ((this.tuan) && (this.triet)) {
      g.drawString("TuanTriet", 120, 15);
    } else {
      if (this.tuan) g.drawString("Tuan", 150, 15);
      if (this.triet) g.drawString("Triet", 150, 15);
    }

    int i = 0;
    int x = 6;
    String[] dstr = { "", "(H) ", "(B) ", "(D) ", "(V) ", "(M) " };
    while (this.sao[i] < 14)
    {
      g.setColor(StarConst.nguhanhcolor[(StarConst.nguhanhsao[this.sao[i]] - 2)]);
      g.drawString(String.valueOf(dstr[StarConst.dialoi[this.sao[i]][this.chi]]).concat(String.valueOf(StarConst.strsao[this.sao[i]])), 60, 28 + i * 13);
      i++;
    }

    int j = 0;
    int max = 0;
    for (i = 0; i < this.numsao; i++)
    {
      if (StarConst.strsao[this.sao[i]].charAt(0) == '+') max++;
      if (StarConst.strsao[this.sao[i]].charAt(0) != '-') continue; j++;
    }
    if ((j > 8) || (max > 8)) max = 8;

    int y = 55;
    j = 1;
    for (i = 0; i < this.numsao; i++) {
      if (StarConst.strsao[this.sao[i]].charAt(0) != '+')
        continue;
      g.setColor(StarConst.nguhanhcolor[(StarConst.nguhanhsao[this.sao[i]] - 2)]);
      g.drawString(StarConst.strsao[this.sao[i]], x, y);
      if (j < max) { y += 12; j++; } else {
        x += 100; y = 55; j = 0;
      }
    }

    for (i = 0; i < this.numsao; i++) {
      if (StarConst.strsao[this.sao[i]].charAt(0) != '-')
        continue;
      g.setColor(StarConst.nguhanhcolor[(StarConst.nguhanhsao[this.sao[i]] - 2)]);
      g.drawString(StarConst.strsao[this.sao[i]], x, y);
      if (j < 8) { y += 12; j++; } else {
        x += 100; y = 55; j = 0;
      }
    }
    g.setColor(new Color(220, 220, 220));
    g.drawString(this.hanstr, 90, 152);
  }

  void Setname(String s, int c, String st)
  {
    this.chi = c;
    this.name = s;
    this.numsao = 0;
    this.tuan = false;
    this.triet = false;
    this.hanstr = st;
  }

  void Settuan() {
    this.tuan = true;
  }

  void Settriet() {
    this.triet = true;
  }

  void Add(int s) {
    this.sao[this.numsao] = s;
    this.numsao += 1;
    repaint();
  }
}