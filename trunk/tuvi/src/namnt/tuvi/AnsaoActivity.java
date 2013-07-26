package namnt.tuvi;


import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Date;

import com.polites.android.GestureImageView;

import namnt.tuvi.utils.StarConst;
import namnt.tuvi.utils.Tcung;
import namnt.tuvi.utils.Utils;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.FloatMath;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AbsoluteLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class AnsaoActivity extends Activity implements StarConst{
	public static final String INTENT_NAME = "name";
	public static final String INTENT_NGAY = "ngay";
	public static final String INTENT_THANG = "thang";
	public static final String INTENT_NAM = "nam";
	public static final String INTENT_GIO = "gio";
	public static final String INTENT_CAN = "can";
	public static final String INTENT_GIOITINH = "gioitinh";
	
	Tcung[] cung = new Tcung[12];
	TextView[] TieuHanStr;
	int width;
	int height;
	int gio;
	int ngay;
	int thang;
	int nam;
	int can;
	int namnu;
	int amduong;
	int manh;
	int than;
	int cuc;
	int tuan;
	int triet;
	int[] sao;
	Date ToDate;
	TextView TuoiAmDuong;
	TextView TuoiMenh;
	TextView TuoiCuc;
	TextView SaoChuMenh;
	TextView SaoChuThan;
	TextView ThangDuong;
	TextView NamDuong;
	TextView NgayDuong;
	
	AbsoluteLayout mainview;
	 static final int NONE = 0;
	 static final int DRAG = 1;
	 static final int ZOOM = 2;
	 int mode = NONE;
	 PointF start = new PointF();
	 PointF mid = new PointF();

	 float oldDist = 1f;
	 PointF oldDistPoint = new PointF();

	 public static String TAG = "ZOOM";
	 GestureImageView img_ansao;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		int gio_ = getIntent().getExtras().getInt(INTENT_GIO);
		int ngay_ = getIntent().getExtras().getInt(INTENT_NGAY);
		int thang_ = getIntent().getExtras().getInt(INTENT_THANG);
		int nam_ = getIntent().getExtras().getInt(INTENT_NAM);
		int can_ = getIntent().getExtras().getInt(INTENT_CAN);
		int namnu_ = getIntent().getExtras().getInt(INTENT_GIOITINH);
		
		setContentView(R.layout.activity_ansao);
		LayoutInflater inflater = LayoutInflater.from(this);
		mainview = (AbsoluteLayout) inflater.inflate(R.layout.layout_ansao, null);
		if (mainview == null){
			Log.v("test", "null");
			return;
		}
		img_ansao = (GestureImageView)findViewById(R.id.id_image_ansao);
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);

		width = metrics.widthPixels; 
		//nexus 4 760
		// galaxy S 480
		height = metrics.heightPixels;
		//nexus 4 1176
		// galaxy S 800
		Utils.setDimensionTcung(width/4, height/4);
		this.cung = new Tcung[12];
	    this.TieuHanStr = new TextView[12];
	    for (int i = 0; i < 12; i++)
	    {
	    	cung[i] = new Tcung(this.getApplicationContext());
	    	TieuHanStr[i] = new TextView(this.getApplicationContext());
	    }
	    this.sao = new int[120];
	    TuoiAmDuong = new TextView(this.getApplicationContext());
		TuoiMenh = new TextView(this.getApplicationContext());
		TuoiCuc = new TextView(this.getApplicationContext());
		SaoChuMenh = new TextView(this.getApplicationContext());
		SaoChuThan = new TextView(this.getApplicationContext());
		ThangDuong = new TextView(this.getApplicationContext());
		NamDuong = new TextView(this.getApplicationContext());
		NgayDuong = new TextView(this.getApplicationContext());
	    Create(gio_,ngay_,thang_,nam_,can_,namnu_);
		Log.v("test", "width " + width + " height " + height);
		int[] dx = { 2, 1, 0, 0, 0, 0, 1, 2, 3, 3, 3, 3 };
		int[] dy = { 3, 3, 3, 2, 1, 0, 0, 0, 0, 1, 2, 3 };
		TextView txt = new TextView(this.getApplicationContext());
		AbsoluteLayout.LayoutParams lp1 = new AbsoluteLayout.LayoutParams(width/4, height/4, width/4 * 2, height/4 * 2);
		txt.setLayoutParams(lp1);
		txt.setTextSize(getResources().getDimensionPixelSize(R.dimen.text_size_cung));
		txt.setTextColor(Color.BLUE);
		txt.setText("test");
		mainview.addView(txt);
		
		for (int i = 0; i < 12; i++)
	    {
		
		//Log.v("test", "item width " + width/4 + " item height " + height/4 + " item x " + width/4 * dx[i] + " item y " + height/4 * dy[i]);
		AbsoluteLayout.LayoutParams lp = new AbsoluteLayout.LayoutParams(width/4, height/4, width/4 * dx[i], height/4 * dy[i]);
		cung[i].setLayoutParams(lp);
		mainview.addView(cung[i]);
		
		//lp = new AbsoluteLayout.LayoutParams(width/4, height/4, width/4 * dx[i], height/4 * dy[i]);
	
	    }
		Log.v("test", "mainview width " + mainview.getWidth() + " mainview height " + mainview.getHeight());
//		mainview.setOnTouchListener(new OnTouchListener() {
//			   @Override
//			   public boolean onTouch(View v, MotionEvent event) {
//			    Log.d(TAG, "mode=DRAG");
//			    switch (event.getAction() & MotionEvent.ACTION_MASK) {
//			    case MotionEvent.ACTION_DOWN:
//			     start.set(event.getX(), event.getY());
//			     Log.d(TAG, "mode=DRAG");
//			     mode = DRAG;
//
//			     break;
//			    case MotionEvent.ACTION_POINTER_DOWN:
//			     oldDist = spacing(event);
//			     oldDistPoint = spacingPoint(event);
//			     Log.d(TAG, "oldDist=" + oldDist);
//			     if (oldDist > 10f) {
//			      midPoint(mid, event);
//			      mode = ZOOM;
//			      Log.d(TAG, "mode=ZOOM");
//			     }
//			     System.out.println("current time :" + System.currentTimeMillis());
//			     break;// return !gestureDetector.onTouchEvent(event);
//			    case MotionEvent.ACTION_UP:
//			    case MotionEvent.ACTION_POINTER_UP:
//			     Log.d(TAG, "mode=NONE");
//			     mode = NONE;
//			     break;
//			    case MotionEvent.ACTION_MOVE:
//			     if (mode == DRAG) {
//
//			     } else if (mode == ZOOM) {
//			      PointF newDist = spacingPoint(event);
//			      float newD = spacing(event);
//			      Log.e(TAG, "newDist=" + newDist);
//			      float[] old = new float[9];
//			      float[] newm = new float[9];
//			      Log.e(TAG, "x=" + old[0] + ":&:" + old[2]);
//			      Log.e(TAG, "y=" + old[4] + ":&:" + old[5]);
//			      float scale = newD / oldDist;
//			      float scalex = newDist.x / oldDistPoint.x;
//			      float scaley = newDist.y / oldDistPoint.y;
//			      zoom(scale, scale, start);
//			     }
//			     break;
//			    }
//			    return true;
//			   }
//			  });
		//zoom(2f,2f,new PointF(0,0));  
		//Bitmap bmp = getBitmapFromView(mainview);
		mainview.setDrawingCacheEnabled(true);
	      // this is the important code :)  
	      // Without it the view will have a dimension of 0,0 and the bitmap will be null          

		mainview.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED), 
	            MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
		Log.v("test", "mainview.getMeasuredWidth() " + mainview.getMeasuredWidth() + " mainview.getMeasuredHeight() " + mainview.getMeasuredHeight());
		mainview.layout(0, 0, mainview.getMeasuredWidth(), mainview.getMeasuredHeight()); 
		
		//mainview.layout(0, 0, width*4, height*4);
		mainview.buildDrawingCache(true);
	    Bitmap b = Bitmap.createBitmap(mainview.getDrawingCache());
	    mainview.setDrawingCacheEnabled(false); 
	    img_ansao.setImageBitmap(b);
		saveImageToExternalStorage(b);
		
	}
	public void zoom(Float scaleX,Float scaleY,PointF pivot){
		mainview.setPivotX(pivot.x);
		mainview.setPivotY(pivot.y);  
		mainview.setScaleX(scaleX);
		mainview.setScaleY(scaleY);  
		 } 
	public Bitmap getBitmapFromView(View view) {
	    Bitmap returnedBitmap = Bitmap.createBitmap(width, height,Bitmap.Config.ARGB_8888);
	    Canvas canvas = new Canvas(returnedBitmap);
	    Drawable bgDrawable =view.getBackground();
	    if (bgDrawable!=null) 
	        bgDrawable.draw(canvas);
	    else 
	        canvas.drawColor(Color.WHITE);
	    view.draw(canvas);
	    return returnedBitmap;
	}
	public void saveImageToExternalStorage(Bitmap image) {
		//image=scaleCenterCrop(image,200,200);
		String fullPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/";
		try
		{
		File dir = new File(fullPath);
		if (!dir.exists()) {
		dir.mkdirs();
		}
		OutputStream fOut = null;
		File file = new File(fullPath, "photo1.png");

		if(file.exists())
		file.delete();

		file.createNewFile();
		fOut = new FileOutputStream(file);
		// 100 means no compression, the lower you go, the stronger the compression
		image.compress(Bitmap.CompressFormat.PNG, 100, fOut);
		fOut.flush();
		fOut.close();
		}
		catch (Exception e)
		{
		Log.e("saveToExternalStorage()", e.getMessage());
		}
		}
	private float spacing(MotionEvent event) {
		  // ...
		  float x = event.getX(0) - event.getX(1);
		  float y = event.getY(0) - event.getY(1);
		  return FloatMath.sqrt(x * x + y * y);
		 }

		 private PointF spacingPoint(MotionEvent event) {
		  PointF f = new PointF();
		  f.x = event.getX(0) - event.getX(1);
		  f.y = event.getY(0) - event.getY(1);
		  return f;
		 }

		 /**
		  * the mid point of the first two fingers
		  */
		 private void midPoint(PointF point, MotionEvent event) {
		  // ...
		  float x = event.getX(0) + event.getX(1);
		  float y = event.getY(0) + event.getY(1);
		  point.set(x / 2, y / 2);
		 }
		 /**
		  * Ham tao
		  * @param gio_ gio sinh
		  * @param ngay_ ngay sinh am lich 
		  * @param thang_ thang sinh am lich
		  * @param nam_ nam sinh am lich
		  * @param can_ can
		  * @param namnu_ nam hay nu
		  */
	public void Create(int gio_, int ngay_, int thang_, int nam_, int can_, int namnu_)
	  {
	    this.gio = gio_;
	    this.ngay = ngay_;
	    this.thang = thang_;
	    this.nam = nam_;
	    this.can = can_;
	   // Log.v("test", "android gio " + gio_ + " ngay " + ngay_ + " thang " + thang_ + " nam " + nam_ + " can " + can_);
	    if (this.nam % 2 != this.can % 2) this.can = (this.can % 10 + 1);

	    this.namnu = namnu_;
	    this.amduong = ((this.can - 1) % 2);
	    this.manh = ((this.thang + 14 - this.gio) % 12);
	    this.than = ((this.thang + this.gio) % 12);

	    int[][] cucarr = { { 2, 6, 3, 5, 4, 6 }, { 6, 5, 4, 3, 2, 5 }, { 5, 3, 2, 4, 6, 3 }, { 3, 4, 6, 2, 5, 4 }, { 4, 2, 5, 6, 3, 2 } };

	    this.cuc = cucarr[((this.can - 1) % 5)][(this.manh / 2)];

	    for (int i = 0; i < 12; i++)
	    {
	      int j = (i + 12 - this.manh) % 12;
	      if (this.amduong != this.namnu - 1) j = (12 - i + this.manh) % 12;

	      String St = String.valueOf(this.cuc + j * 10);
	      if (i == this.than)
	        this.cung[i].Setname(String.valueOf(StarConst.strcung[((this.manh + 12 - i) % 12)]).concat(String.valueOf("  (THA^N)")), i, St);
	      else this.cung[i].Setname(StarConst.strcung[((this.manh + 12 - i) % 12)], i, St);

	      int[] tieu_han = { 10, 7, 4, 1 };
	      if (this.namnu == 1) this.TieuHanStr[i].setText(StarConst.strnam[((11 - tieu_han[((this.nam - 1) % 4)] + this.nam + i) % 12)]); else
	        this.TieuHanStr[i].setText(StarConst.strnam[((11 + tieu_han[((this.nam - 1) % 4)] + this.nam - i) % 12)]);
	    }
	    this.triet = ((4 - (this.can - 1) % 5) * 2);
	    this.tuan = ((this.nam + 22 - this.can) % 12);

	    this.cung[this.triet].Settriet();
	    this.cung[(this.triet + 1)].Settriet();
	    this.cung[this.tuan].Settuan();
	    this.cung[(this.tuan + 1)].Settuan();

	    int[] tuviarr = { 10, 7, 12, 5, 2, 3 };
	    int tu_vi = ((this.ngay - 1) / this.cuc + tuviarr[((this.ngay - 1) % this.cuc + 6 - this.cuc)] + 11) % 12;
	    Add(0, tu_vi);
	    Add(1, (tu_vi + 11) % 12);
	    Add(2, (tu_vi + 9) % 12);
	    Add(3, (tu_vi + 8) % 12);
	    Add(4, (tu_vi + 7) % 12);
	    Add(5, (tu_vi + 4) % 12);

	    int thien_phu = (16 - tu_vi) % 12;
	    for (int i = 0; i < 7; i++)
	      Add(6 + i, (thien_phu + i) % 12);
	    Add(13, (thien_phu + 10) % 12);
	    Add(14, (this.thang + 3) % 12);
	    Add(15, (23 - this.thang) % 12);
	    Add(16, (this.sao[14] + this.ngay + 11) % 12);
	    Add(17, (121 + this.sao[15] - this.ngay) % 12);
	    Add(19, (this.gio + 3) % 12);
	    Add(18, (23 - this.gio) % 12);

	    Add(20, (this.sao[18] + this.ngay + 10) % 12);
	    Add(21, (122 + this.sao[19] - this.ngay) % 12);
	    Add(22, (5 + this.gio) % 12);
	    Add(23, (1 + this.gio) % 12);
	    Add(24, (this.nam + 4 - 1) % 12);
	    Add(25, (23 - this.nam) % 12);
	    Add(26, (23 - this.nam) % 12);

	    int[] thien_khoi = { 1, 0, 11, 11, 1, 0, 6, 6, 3, 3 };
	    int[] thien_viet = { 7, 8, 9, 9, 7, 8, 2, 2, 5, 5 };
	    Add(27, thien_khoi[(this.can - 1)]);
	    Add(28, thien_viet[(this.can - 1)]);

	    int[] thien_quan = { 7, 4, 5, 2, 3, 9, 11, 9, 10, 6 };
	    int[] thien_phuc = { 9, 8, 0, 11, 3, 2, 6, 5, 6, 5 };
	    Add(29, thien_quan[(this.can - 1)]);
	    Add(30, thien_phuc[(this.can - 1)]);

	    Add(32, (this.nam + 5 - 1) % 12);
	    Add(31, (this.nam + 9 - 1) % 12);

	    int[] dao_hoa = { 9, 6, 3, 0 };
	    Add(33, dao_hoa[((this.nam - 1) % 4)]);
	    Add(34, (16 - this.nam) % 12);
	    Add(35, (22 - this.nam) % 12);
	    Add(36, (this.manh + this.nam + 11) % 12);
	    Add(37, (this.than + this.nam - 1) % 12);
	    Add(38, (this.manh + 5) % 12);
	    Add(39, (this.manh + 7) % 12);
	    Add(40, (19 - this.nam) % 12);
	    Add(41, (5 + this.nam) % 12);
	    Add(42, 4);
	    Add(43, 10);
	    int[] co_than = { 2, 2, 5, 5, 5, 8, 8, 8, 11, 11, 11, 2 };

	    int[] qua_tu = { 10, 10, 1, 1, 1, 4, 4, 4, 7, 7, 7, 10 };

	    Add(44, co_than[(this.nam - 1)]);
	    Add(45, qua_tu[(this.nam - 1)]);
	    Add(46, (9 + this.thang - 1) % 12);
	    Add(47, (1 + this.thang - 1) % 12);
	    Add(48, (1 + this.thang - 1) % 12);
	    int[] thien_ma = { 2, 11, 8, 5 };
	    Add(49, thien_ma[((this.nam - 1) % 4)]);
	    int[] hoa_cai = { 4, 1, 10, 7 };
	    Add(50, hoa_cai[((this.nam - 1) % 4)]);
	    int[] pha_toai = { 5, 1, 9 };
	    Add(51, pha_toai[((this.nam - 1) % 3)]);
	    int[] kiep_sat = { 5, 2, 11, 8 };
	    Add(52, kiep_sat[((this.nam - 1) % 4)]);
	    int[] thien_tru = { 5, 6, 0, 5, 6, 8, 2, 6, 9, 10 };
	    Add(54, thien_tru[(this.can - 1)]);
	    int[] luu_nien = { 5, 6, 8, 9, 8, 9, 11, 0, 9, 3 };
	    Add(55, luu_nien[(this.can - 1)]);
	    int[] luu_ha = { 9, 10, 7, 8, 0, 6, 3, 4, 11, 2 };
	    Add(56, luu_ha[(this.can - 1)]);
	    Add(57, (8 + this.thang - 1) % 12);
	    Add(58, (7 + this.thang - 1) % 12);

	    int linh_tinh = 3;
	    if ((this.nam - 1) % 4 != 2) linh_tinh = 10;
	    if (this.namnu - 1 == this.amduong)
	      Add(60, (13 - this.gio + linh_tinh) % 12);
	    else Add(60, (this.gio - 1 + linh_tinh) % 12);

	    int[] hoa_tinh = { 2, 3, 1, 9 };
	    if (this.namnu - 1 == this.amduong)
	      Add(59, (this.gio - 1 + hoa_tinh[((this.nam - 1) % 4)]) % 12);
	    else Add(59, (13 - this.gio + hoa_tinh[((this.nam - 1) % 4)]) % 12);

	    for (int i = 0; i < 12; i++) Add(61 + i, (this.nam - 1 + i) % 12);
	    Add(73, (this.nam + 10) % 12);
	    Add(74, this.nam % 12);
	    Add(53, (this.nam + 23 - this.thang + this.gio) % 12);
	    int[] loc_ton = { 2, 3, 5, 6, 5, 6, 8, 9, 11, 0 };
	    Add(75, loc_ton[(this.can - 1)]);
	    for (int i = 0; i < 12; i++)
	      if (this.amduong == this.namnu - 1)
	        Add(76 + i, (loc_ton[(this.can - 1)] + i) % 12);
	      else Add(76 + i, (loc_ton[(this.can - 1)] + 12 - i) % 12);
	    Add(88, (this.sao[75] + 1) % 12);
	    Add(89, (this.sao[75] + 11) % 12);

	    int[] trang_sinh = { 8, 11, 5, 8, 2 };
	    for (int i = 0; i < 12; i++) {
	      if (this.amduong == this.namnu - 1)
	        Add(90 + i, (trang_sinh[(this.cuc - 2)] + i) % 12);
	      else Add(90 + i, (trang_sinh[(this.cuc - 2)] + 12 - i) % 12);
	    }
	    Add(102, (10 + this.gio) % 12);
	    Add(103, (24 - this.gio) % 12);

	    int[] hoa_loc = { 5, 1, 4, 7, 8, 3, 2, 9, 11, 13 };

	    int[] hoa_quyen = { 13, 11, 1, 4, 7, 8, 3, 2, 0, 9 };

	    int[] hoa_khoa = { 3, 0, 18, 1, 15, 11, 7, 19, 14, 7 };

	    int[] hoa_ky = { 2, 7, 5, 9, 1, 19, 4, 18, 3, 8 };

	    Add(104, this.sao[hoa_loc[(this.can - 1)]]);
	    Add(105, this.sao[hoa_quyen[(this.can - 1)]]);
	    Add(106, this.sao[hoa_khoa[(this.can - 1)]]);
	    Add(107, this.sao[hoa_ky[(this.can - 1)]]);

	    int[] duong_phu = { 7, 8, 10, 11, 10, 11, 1, 2, 4, 5 };
	    int[] quoc_an = { 10, 11, 1, 2, 1, 2, 4, 5, 7, 8 };
	    Add(108, duong_phu[(this.can - 1)]);
	    Add(109, quoc_an[(this.can - 1)]);

	    this.TuoiAmDuong.setText(String.valueOf(String.valueOf(String.valueOf("Tuoi: ").concat(String.valueOf(StarConst.stramduong[this.amduong]))).concat(String.valueOf(" "))).concat(String.valueOf(StarConst.strnamnu[(this.namnu - 1)])));
	    this.TuoiMenh.setText(String.valueOf("Menh: ").concat(String.valueOf(StarConst.strmenh[((this.nam - 1) / 2 * 5 + (this.can - 1) / 2)])));
	    this.TuoiCuc.setText(String.valueOf("Cuc: ").concat(String.valueOf(StarConst.strcuc[(this.cuc - 2)])));

	    int[] sao_menh = { 8, 9, 75, 19, 5, 3, 13, 3, 5, 19, 75, 9 };

	    int[] sao_than = { 60, 10, 11, 4, 18, 1, 59, 10, 11, 4, 18, 1 };

	    String s = StarConst.strsao[sao_menh[(this.nam - 1)]];
	    if ((s.charAt(0) == '+') || (s.charAt(0) == '-'))
	      s = s.substring(1);
	    this.SaoChuMenh.setText(String.valueOf("Sao chu menh: ").concat(String.valueOf(s)));
	    s = StarConst.strsao[sao_than[(this.nam - 1)]];
	    if ((s.charAt(0) == '+') || (s.charAt(0) == '-'))
	      s = s.substring(1);
	    this.SaoChuThan.setText(String.valueOf("Sao chu thân: ").concat(String.valueOf(s)));
	  }

	  void Add(int s, int c)
	  {
	    this.sao[s] = c;
	    this.cung[c].Add(s);
	  }
	  void UpdateDuong()
	  {
	    this.NgayDuong.setText(String.valueOf(this.ToDate.getDate()));
	    this.ThangDuong.setText(String.valueOf(this.ToDate.getMonth() + 1));
	    this.NamDuong.setText(String.valueOf(this.ToDate.getYear() + 1900));
	  }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.ansao, menu);
		return true;
		
	}

}
