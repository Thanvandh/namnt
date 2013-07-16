package namnt.tuvi.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;



public class Tcung extends View
  implements StarConst
{
  public Tcung(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

String name;
  String hanstr;
  int chi;
  int numsao;
  int[] sao = new int[100];
  boolean tuan;
  boolean triet;
  @Override
	protected void onDraw(Canvas g) {
		
	Paint mypaint = new Paint();
    //g.drawColor(StarConst.nguhanhcolor[(StarConst.nguhanhcung[this.chi] - 2)]);
    mypaint.setColor(StarConst.nguhanhcolor[(StarConst.nguhanhcung[this.chi] - 2)]);
    Rect r = new Rect(4, 4, 192, 152);
    g.drawRect(r,mypaint);
    r.set(3, 3, 194, 154);
    g.drawRect(r,mypaint);
    mypaint.setColor(Color.BLUE);
    g.drawText(this.name, 10, 15, mypaint);
    if ((this.tuan) && (this.triet)) {
    	g.drawText("TuanTriet", 120, 15, mypaint);
    } else {
      if (this.tuan) g.drawText("Tuan", 150, 15, mypaint);
      if (this.triet) g.drawText("Triet", 150, 15, mypaint);
    }

    int i = 0;
    int x = 6;
    String[] dstr = { "", "(H) ", "(B) ", "(D) ", "(V) ", "(M) " };
    while (this.sao[i] < 14)
    {
      mypaint.setColor(StarConst.nguhanhcolor[(StarConst.nguhanhsao[this.sao[i]] - 2)]);
      g.drawText(String.valueOf(dstr[StarConst.dialoi[this.sao[i]][this.chi]]).concat(String.valueOf(StarConst.strsao[this.sao[i]])), 60, 28 + i * 13, mypaint);
     
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
      mypaint.setColor(StarConst.nguhanhcolor[(StarConst.nguhanhsao[this.sao[i]] - 2)]);
      g.drawText(StarConst.strsao[this.sao[i]], x, y,mypaint);
      if (j < max) { y += 12; j++; } else {
        x += 100; y = 55; j = 0;
      }
    }

    for (i = 0; i < this.numsao; i++) {
      if (StarConst.strsao[this.sao[i]].charAt(0) != '-')
        continue;
      mypaint.setColor(StarConst.nguhanhcolor[(StarConst.nguhanhsao[this.sao[i]] - 2)]);
      g.drawText(StarConst.strsao[this.sao[i]], x, y, mypaint);
      if (j < 8) { y += 12; j++; } else {
        x += 100; y = 55; j = 0;
      }
    }
    mypaint.setARGB(255, 220, 220, 220);
    g.drawText(this.hanstr, 90, 152, mypaint);
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

  public void Add(int s) {
    this.sao[this.numsao] = s;
    this.numsao += 1;
    invalidate();
  }
}