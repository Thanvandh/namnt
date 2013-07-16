package namnt.tuvi;

import namnt.tuvi.utils.StarConst;
import namnt.tuvi.utils.Tcung;
import android.app.Activity;
import android.os.Bundle;


public class tuvi extends Activity implements StarConst
{
  String name;
  int ngay;
  int thang;
  int nam;
  int can;
  int gio;
  int namnu;
  int amduong;
  int manh;
  int than;
  int cuc;
  int tuan;
  int triet;
  Tcung[] cung;
  int[] sao;
  Date ToDate;
  Label phaistr;
  Label tenstr;
  TextField ten;
  Choice phai_;
  Label ngaystr;
  Label giostr;
  Choice gio_;
  Choice ngay_;
  Label thangstr;
  Choice thang_;
  Label namstr;
  Choice nam_;
  Choice can_;
  Label amlichstr;
  Label TuoiAmDuong;
  Label TuoiMenh;
  Label TuoiCuc;
  Label SaoChuMenh;
  Label SaoChuThan;
  Button BDDate;
  Button BIDate;
  Button BDMonth;
  TextField ThangDuong;
  Button BIMonth;
  Button BIYear;
  TextField NamDuong;
  Button BDYear;
  Button AmSangDuong;
  TextField NgayDuong;
  Label[] TieuHanStr;



  @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tuvi);
		init();
		
	}
  public void init()
  {
    //setBackground(new Color(160, 160, 160));

    this.sao = new int[120];
    this.cung = new Tcung[12];

    this.TieuHanStr = new Label[12];

    int[] dx = { 2, 1, 0, 0, 0, 0, 1, 2, 3, 3, 3, 3 };
    int[] dy = { 3, 3, 3, 2, 1, 0, 0, 0, 0, 1, 2, 3 };

    for (int i = 0; i < 12; i++)
    {
      this.cung[i] = new Tcung();
      this.cung[i].reshape(dx[i] * 200, dy[i] * 160, 200, 160);
      add(this.cung[i]);

      this.TieuHanStr[i] = new Label("Mao");
      this.TieuHanStr[i].reshape(dx[i] * 118 + 200, dy[i] * 100 + 162, 44, 20);
      add(this.TieuHanStr[i]);
    }

    this.tenstr = new Label("ten");
    this.tenstr.reshape(240, 190, 40, 20);
    add(this.tenstr);
    this.ten = new TextField();
    this.ten.reshape(290, 190, 124, 20);
    add(this.ten);
    this.phaistr = new Label("Phai");
    this.phaistr.reshape(420, 190, 40, 20);
    add(this.phaistr);
    this.phai_ = new Choice();
    for (i = 0; i < 2; i++)
      this.phai_.addItem(StarConst.strnamnu[i]);
    add(this.phai_);
    this.phai_.reshape(470, 190, 60, 21);
    this.phai_.select(0);
    this.ngaystr = new Label("ngay");
    this.ngaystr.reshape(240, 220, 40, 20);
    add(this.ngaystr);
    this.ngay_ = new Choice();
    for (i = 1; i <= 30; i++)
      this.ngay_.addItem(String.valueOf(i));
    add(this.ngay_);
    this.ngay_.reshape(290, 220, 50, 21);
    this.ngay_.select(0);
    this.thangstr = new Label("thang");
    this.thangstr.reshape(340, 220, 50, 20);
    add(this.thangstr);
    this.thang_ = new Choice();
    for (i = 1; i <= 12; i++)
      this.thang_.addItem(String.valueOf(i));
    add(this.thang_);
    this.thang_.reshape(390, 220, 50, 21);
    this.thang_.select(0);
    this.namstr = new Label("nam");
    this.namstr.reshape(240, 250, 50, 20);
    add(this.namstr);
    this.nam_ = new Choice();
    for (i = 0; i < 12; i++)
      this.nam_.addItem(StarConst.strnam[i]);
    add(this.nam_);
    this.nam_.reshape(370, 250, 50, 21);
    this.nam_.select(0);
    this.can_ = new Choice();
    for (i = 0; i < 10; i++)
      this.can_.addItem(StarConst.strcan[i]);
    add(this.can_);
    this.can_.reshape(290, 250, 70, 21);
    this.can_.select(0);
    this.amlichstr = new Label("(âm lich)");
    this.amlichstr.reshape(440, 250, 90, 20);
    add(this.amlichstr);

    this.giostr = new Label("gio");
    this.giostr.reshape(240, 280, 40, 20);
    add(this.giostr);
    this.gio_ = new Choice();
    for (i = 0; i < 12; i++)
      this.gio_.addItem(String.valueOf(String.valueOf(String.valueOf(String.valueOf(String.valueOf(StarConst.strnam[i]).concat(String.valueOf(" ("))).concat(String.valueOf(String.valueOf(i * 2)))).concat(String.valueOf(":00 - "))).concat(String.valueOf(String.valueOf(i * 2 + 1)))).concat(String.valueOf(":59)")));
    add(this.gio_);
    this.gio_.select(0);
    this.gio_.reshape(290, 280, 100, 20);

    this.TuoiAmDuong = new Label("Tuoi:");
    this.TuoiAmDuong.reshape(240, 310, 300, 20);
    add(this.TuoiAmDuong);

    this.TuoiMenh = new Label("Menh:");
    this.TuoiMenh.reshape(240, 330, 190, 20);
    add(this.TuoiMenh);

    this.TuoiCuc = new Label("Cuc: ");
    this.TuoiCuc.reshape(240, 350, 190, 20);
    add(this.TuoiCuc);

    this.SaoChuMenh = new Label("Sao Chu Menh: ");
    this.SaoChuMenh.reshape(240, 370, 190, 20);
    add(this.SaoChuMenh);

    this.SaoChuThan = new Label("Sao Chu Thân:");
    this.SaoChuThan.reshape(240, 390, 190, 20);
    add(this.SaoChuThan);

    this.BDDate = new Button("<");
    this.BDDate.reshape(220, 430, 14, 20);
    add(this.BDDate);
    this.NgayDuong = new TextField();
    this.NgayDuong.reshape(237, 430, 20, 21);
    add(this.NgayDuong);
    this.BIDate = new Button(">");
    this.BIDate.reshape(260, 430, 14, 20);
    add(this.BIDate);
    this.BDMonth = new Button("<");
    this.BDMonth.reshape(280, 430, 14, 20);
    add(this.BDMonth);
    this.ThangDuong = new TextField();
    this.ThangDuong.reshape(297, 430, 20, 21);
    add(this.ThangDuong);
    this.BIMonth = new Button(">");
    this.BIMonth.reshape(320, 430, 14, 20);
    add(this.BIMonth);
    this.BDYear = new Button("<");
    this.BDYear.reshape(340, 430, 14, 20);
    add(this.BDYear);
    this.NamDuong = new TextField();
    this.NamDuong.reshape(357, 430, 40, 21);
    add(this.NamDuong);
    this.BIYear = new Button(">");
    this.BIYear.reshape(400, 430, 14, 20);
    add(this.BIYear);
    this.AmSangDuong = new Button("Duong Lich qua Âm Lich");
    this.AmSangDuong.reshape(420, 430, 160, 21);
    add(this.AmSangDuong);

    this.NgayDuong.setEditable(false);
    this.ThangDuong.setEditable(false);
    this.NamDuong.setEditable(false);

    this.ToDate = new Date();
    UpdateDuong();

    Create(this.gio_.getSelectedIndex() + 1, this.ngay_.getSelectedIndex() + 1, this.thang_.getSelectedIndex() + 1, this.nam_.getSelectedIndex() + 1, this.can_.getSelectedIndex() + 1, this.phai_.getSelectedIndex() + 1);
  }

  public boolean action(Event e, Object o)
  {
    if ((e.target instanceof Button))
    {
      if (e.target == this.AmSangDuong)
      {
        DuonglichQuaAmlich temp = new DuonglichQuaAmlich(this.ToDate.getDate(), this.ToDate.getMonth() + 1, this.ToDate.getYear());

        this.ngay_.select(temp.getngay() - 1);
        this.thang_.select(temp.getthang() - 1);
        this.nam_.select(temp.getchi());
        this.can_.select(temp.getcan());
      }
      else {
        if ((e.target == this.BDDate) && (this.ToDate.getDate() > 1)) this.ToDate.setDate(this.ToDate.getDate() - 1);
        if ((e.target == this.BIDate) && (this.ToDate.getDate() < 31)) this.ToDate.setDate(this.ToDate.getDate() + 1);
        if ((e.target == this.BDMonth) && (this.ToDate.getMonth() > 0)) this.ToDate.setMonth(this.ToDate.getMonth() - 1);
        if ((e.target == this.BIMonth) && (this.ToDate.getMonth() < 11)) this.ToDate.setMonth(this.ToDate.getMonth() + 1);
        if ((e.target == this.BDYear) && (this.ToDate.getYear() > 1)) this.ToDate.setYear(this.ToDate.getYear() - 1);
        if ((e.target == this.BIYear) && (this.ToDate.getYear() < 99)) this.ToDate.setYear(this.ToDate.getYear() + 1);
        UpdateDuong();
        return true;
      }

    }

    Create(this.gio_.getSelectedIndex() + 1, this.ngay_.getSelectedIndex() + 1, this.thang_.getSelectedIndex() + 1, this.nam_.getSelectedIndex() + 1, this.can_.getSelectedIndex() + 1, this.phai_.getSelectedIndex() + 1);

    return true;
  }

  public void destroy()
  {
  }

  public void start()
  {
  }

  public void stop()
  {
  }

  synchronized void Create(int gio_, int ngay_, int thang_, int nam_, int can_, int namnu_)
  {
    this.gio = gio_;
    this.ngay = ngay_;
    this.thang = thang_;
    this.nam = nam_;
    this.can = can_;

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
    for (i = 0; i < 7; i++)
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

    for (i = 0; i < 12; i++) Add(61 + i, (this.nam - 1 + i) % 12);
    Add(73, (this.nam + 10) % 12);
    Add(74, this.nam % 12);
    Add(53, (this.nam + 23 - this.thang + this.gio) % 12);
    int[] loc_ton = { 2, 3, 5, 6, 5, 6, 8, 9, 11, 0 };
    Add(75, loc_ton[(this.can - 1)]);
    for (i = 0; i < 12; i++)
      if (this.amduong == this.namnu - 1)
        Add(76 + i, (loc_ton[(this.can - 1)] + i) % 12);
      else Add(76 + i, (loc_ton[(this.can - 1)] + 12 - i) % 12);
    Add(88, (this.sao[75] + 1) % 12);
    Add(89, (this.sao[75] + 11) % 12);

    int[] trang_sinh = { 8, 11, 5, 8, 2 };
    for (i = 0; i < 12; i++) {
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
}