package namnt.tuvi.utils;

import namnt.tuvi.R;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.Log;
import android.view.View;

//Mot so ky hieu:
//    (M)=Mieu dia
//     (V) = Vuong dia
//     (D)=Dac dia
//     (B)=Bi`nh ho`a
//     (H)=Ham dia 	 
//Cac mau sau day tuong trung cho:
//    Trang : Kim
//     Xanh: Moc
//     Den: Thuy
//     Do: Hoa
//     Va`ng: Tho

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
		
	Paint mypaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    //g.drawColor(StarConst.nguhanhcolor[(StarConst.nguhanhcung[this.chi] - 2)]);
    mypaint.setColor(StarConst.nguhanhcolor[(StarConst.nguhanhcung[this.chi] - 2)]);
    mypaint.setStyle(Style.STROKE);
    mypaint.setTextSize(getResources().getDimensionPixelSize(R.dimen.text_size_cung));
    Log.v("test", "width " + (Utils.width_cung - Utils.offset_right_cung) + " height " + (Utils.height_cung - Utils.offset_bottom_cung));
    Rect r = new Rect(Utils.offset_left_cung, Utils.offset_top_cung, Utils.width_cung - Utils.offset_right_cung, Utils.height_cung - Utils.offset_bottom_cung);
    g.drawRect(r,mypaint);
    //r.set(Utils.offset_left_cung - 2, Utils.offset_top_cung - 2, Utils.width_cung - Utils.offset_right_cung - 2, Utils.height_cung - Utils.offset_bottom_cung -2);
   // g.drawRect(r,mypaint);
    mypaint.setStyle(Style.FILL);
    mypaint.setTypeface(Typeface.DEFAULT_BOLD);
   // mypaint.setAntiAlias(true);
    mypaint.setColor(Color.BLUE);
    g.drawText(this.name, getResources().getDimensionPixelSize(R.dimen.x_name_cung), getResources().getDimensionPixelSize(R.dimen.y_name_cung), mypaint);
    if ((this.tuan) && (this.triet)) {
    	g.drawText("TuanTriet", getResources().getDimensionPixelSize(R.dimen.x_tuantriet_cung), getResources().getDimensionPixelSize(R.dimen.y_tuantriet_cung), mypaint);
    } else {
      if (this.tuan) g.drawText("Tuan", getResources().getDimensionPixelSize(R.dimen.x_tuanortriet_cung), getResources().getDimensionPixelSize(R.dimen.y_tuanortriet_cung), mypaint);
      if (this.triet) g.drawText("Triet", getResources().getDimensionPixelSize(R.dimen.x_tuanortriet_cung), getResources().getDimensionPixelSize(R.dimen.y_tuanortriet_cung), mypaint);
    }

    int i = 0;
    int x = 6;
    String[] dstr = { "", "(H) ", "(B) ", "(D) ", "(V) ", "(M) " };
    while (this.sao[i] < 14)
    {
      mypaint.setColor(StarConst.nguhanhcolor[(StarConst.nguhanhsao[this.sao[i]] - 2)]);
      g.drawText(String.valueOf(dstr[StarConst.dialoi[this.sao[i]][this.chi]]).concat(String.valueOf(StarConst.strsao[this.sao[i]])), getResources().getDimensionPixelSize(R.dimen.x_part2_cung), getResources().getDimensionPixelSize(R.dimen.y_part2_cung) + i * getResources().getDimensionPixelSize(R.dimen.y_part2_step_cung), mypaint);
     
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

    int y = getResources().getDimensionPixelSize(R.dimen.y_part3_cung);//55;
    j = 1;
    for (i = 0; i < this.numsao; i++) {
      if (StarConst.strsao[this.sao[i]].charAt(0) != '+')
        continue;
      mypaint.setColor(StarConst.nguhanhcolor[(StarConst.nguhanhsao[this.sao[i]] - 2)]);
      g.drawText(StarConst.strsao[this.sao[i]], x, y,mypaint);
      if (j < max) { y +=  getResources().getDimensionPixelSize(R.dimen.y_part3_step_cung); j++; } else {
        x += getResources().getDimensionPixelSize(R.dimen.x_part3_colum2_cung); y = getResources().getDimensionPixelSize(R.dimen.y_part3_cung); j = 0;
      }
    }

    for (i = 0; i < this.numsao; i++) {
      if (StarConst.strsao[this.sao[i]].charAt(0) != '-')
        continue;
      mypaint.setColor(StarConst.nguhanhcolor[(StarConst.nguhanhsao[this.sao[i]] - 2)]);
      g.drawText(StarConst.strsao[this.sao[i]], x, y, mypaint);
      if (j < 8) { y += getResources().getDimensionPixelSize(R.dimen.y_part3_step_cung); j++; } else {
    	  x += getResources().getDimensionPixelSize(R.dimen.x_part3_colum2_cung); y = getResources().getDimensionPixelSize(R.dimen.y_part3_cung); j = 0;
      }
    }
    mypaint.setARGB(255, 220, 220, 220);
    g.drawText(this.hanstr, getResources().getDimensionPixelSize(R.dimen.x_part4_cung), getResources().getDimensionPixelSize(R.dimen.y_part4_cung), mypaint);
  }

  public void Setname(String s, int c, String st)
  {
    this.chi = c;
    this.name = s;
    this.numsao = 0;
    this.tuan = false;
    this.triet = false;
    this.hanstr = st;
  }

  public void Settuan() {
    this.tuan = true;
  }

  public void Settriet() {
    this.triet = true;
  }

  public void Add(int s) {
    this.sao[this.numsao] = s;
    this.numsao += 1;
    invalidate();
  }
}