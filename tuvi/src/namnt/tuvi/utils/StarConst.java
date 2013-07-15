package namnt.tuvi.utils;

import android.graphics.Color;



interface StarConst
{
  public static final int thuy = 2;
  public static final int moc = 3;
  public static final int kim = 4;
  public static final int tho = 5;
  public static final int hoa = 6;
  public static final int chuot = 0;
  public static final int suu = 1;
  public static final int dan = 2;
  public static final int mao = 3;
  public static final int thin = 4;
  public static final int ran = 5;
  public static final int ngo = 6;
  public static final int mui = 7;
  public static final int khi = 8;
  public static final int dau = 9;
  public static final int tuat = 10;
  public static final int hoi = 11;
  public static final int tuvi = 0;
  public static final int thienco = 1;
  public static final int thaiduong = 2;
  public static final int vukhuc = 3;
  public static final int thiendong = 4;
  public static final int liemtrinh = 5;
  public static final int thienphu = 6;
  public static final int thaiam = 7;
  public static final int thamlang = 8;
  public static final int cumon = 9;
  public static final int thientuong = 10;
  public static final int thienluong = 11;
  public static final int thatsat = 12;
  public static final int phaquan = 13;
  public static final int taphu = 14;
  public static final int huubat = 15;
  public static final int tamthai = 16;
  public static final int battoa = 17;
  public static final int vanxuong = 18;
  public static final int vankhuc = 19;
  public static final int anquang = 20;
  public static final int thienquy = 21;
  public static final int thaiphu = 22;
  public static final int phongcao = 23;
  public static final int longtri = 24;
  public static final int phuongcac = 25;
  public static final int giaithan = 26;
  public static final int thienkhoi = 27;
  public static final int thienviet = 28;
  public static final int thienquan = 29;
  public static final int thienphuc = 30;
  public static final int thienduc = 31;
  public static final int nguyetduc = 32;
  public static final int daohoa = 33;
  public static final int hongloan = 34;
  public static final int thienhy = 35;
  public static final int thientai = 36;
  public static final int thientho = 37;
  public static final int thienthuong = 38;
  public static final int thiensu = 39;
  public static final int thienkhoc = 40;
  public static final int thienhu = 41;
  public static final int thienla = 42;
  public static final int diavong = 43;
  public static final int cothan = 44;
  public static final int quatu = 45;
  public static final int thienhinh = 46;
  public static final int thieny = 47;
  public static final int thienrieu = 48;
  public static final int thienma = 49;
  public static final int hoacai = 50;
  public static final int phatoai = 51;
  public static final int kiepsat = 52;
  public static final int dauquan = 53;
  public static final int thientru = 54;
  public static final int luunien = 55;
  public static final int luuha = 56;
  public static final int thiengiai = 57;
  public static final int diagiai = 58;
  public static final int hoatinh = 59;
  public static final int linhtinh = 60;
  public static final int thaitue = 61;
  public static final int quansach = 73;
  public static final int thienkhong = 74;
  public static final int locton = 75;
  public static final int bacsi = 76;
  public static final int kinhduong = 88;
  public static final int dala = 89;
  public static final int trangsinh = 90;
  public static final int diakiep = 102;
  public static final int diakhong = 103;
  public static final int hoaloc = 104;
  public static final int hoaquyen = 105;
  public static final int hoakhoa = 106;
  public static final int hoaky = 107;
  public static final int duongphu = 108;
  public static final int quocan = 109;
  public static final int hd = 1;
  public static final int bh = 2;
  public static final int dd = 3;
  public static final int vd = 4;
  public static final int md = 5;
  public static final String[] strnam = { "TĂ­", "Suu", "DĂ n", "Mao", "ThĂ¬n", "Ti", "Ngo", "Mui", "ThĂ¢n", "Dau", "TuĂ¡t", "Hoi" };

  public static final String[] strcan = { "Giap", "Ă�t", "BĂ­nh", "Dinh", "Mau", "Ky", "Canh", "TĂ¢n", "NhĂ¢m", "QuĂ­" };

  public static final String[] stramduong = { "Duong", "Ă‚m" };
  public static final String[] strnamnu = { "Nam", "Nu" };
  public static final String[] strcung = { "Menh", "Huynh De", "Phu The", "Tu Duc", "Tai Bach", "Tat Ach", "Thien Di", "No Boc", "Quan Loc", "Dien Trach", "Phuc Duc", "Phu Mau" };

  public static final String[] strcuc = { "Thuy Nhi Cuc", "Moc Tam Cuc", "Kim Tu Cuc", "Tho Ngu CuĂ¯c", "Hoa Luc cuc" };

  public static final String[] strsao = { "Tu vi", "Thien co", "Thai duong", "Vu khuc", "Thien dong", "Liem Trinh", "Thien phu", "Thai Ă¢m", "Tham lang", "Cu mon", "Thien tuong", "Thien luong", "ThĂ¡t sat", "Pha QuĂ¢n", "+Ta phu", "+Huu bat", "+Tam thai", "+Bat toa", "+Van xuong", "+Van khuc", "+Ă‚n quang", "+Thien quy", "+Thai phu", "+Phong cao", "+Long trĂ¬", "+Phuong cat", "+Giai than", "+Thien khoi", "+Thien viet", "+Thien quan", "+Thien Phuc", "+Thien duc", "+Nguyet duc", "+Dao hoa", "+Hong loan", "+Thien hy", "+Thien tai", "+Thien tho", "-Thien thuong", "-Thien su", "-Thien khoc", "-Thien hu", "-Thien la", "-Dia vĂµng", "-Co thĂ n", "-Qua tu", "-Thien hĂ¬nh", "+Thien y", "-Thien rieu", "+Thien ma", "+Hoa cai", "-Pha toai", "-Kiep sat", "-Dau quĂ¢n", "+Thien tru", "+Van tinh", "-Luu ha", "+Thien giai", "+Dia giai", "-Hoa tinh", "-Linh tinh", "-Thai tue", "+Thieu duong", "-Tang mon", "+Thieu Ă¢m", "-Quan phu", "-Tu phu", "-Tue pha", "+Long duc", "-Bach Ho", "+Phuc duc", "-Dieu khach", "-Truc phu", "+Quan sach", "-Thien khong", "+Loc ton", "+Bac si", "+Luc si", "+Thanh long", "-Tieu hao", "-Tuong quĂ¢n", "+Tau thu", "-Phi liem", "+Hy than", "-Benh phu", "-Dai hao", "-Phuc binh", "-Quan phu", "-KĂ¬nh duong", "-Da la", "+Trang sinh", "-Moc duc", "-Quan dai", "+LĂ¢m quan", "+De vuong", "-Suy", "-Benh", "-Tu", "-Mo", "-Tuyet", "-Thai", "+Duong", "-Dia kiep", "-Dia khong", "+Hoa loc", "+Hoa quyen", "+Hoa khoa", "-Hoa Ki", "+Duong phu", "+Quoc An" };

  public static final int[][] dialoi = { { 2, 3, 5, 2, 4, 5, 5, 3, 5, 2, 4, 2 }, { 3, 3, 1, 5, 5, 4, 3, 3, 4, 5, 5, 1 }, { 1, 3, 4, 4, 4, 5, 5, 3, 1, 1, 1, 1 }, { 4, 5, 4, 3, 5, 1, 4, 5, 4, 3, 5, 1 }, { 4, 1, 5, 3, 1, 3, 1, 1, 5, 1, 1, 3 }, { 4, 3, 4, 1, 5, 1, 5, 3, 4, 1, 5, 1 }, { 5, 2, 5, 2, 4, 3, 5, 3, 5, 2, 4, 3 }, { 4, 3, 1, 1, 1, 1, 1, 3, 4, 5, 5, 5 }, { 1, 5, 3, 1, 4, 1, 1, 5, 3, 1, 4, 1 }, { 4, 1, 4, 5, 1, 1, 4, 1, 3, 5, 1, 3 }, { 4, 3, 5, 1, 4, 3, 4, 3, 5, 1, 4, 3 }, { 4, 3, 4, 4, 5, 1, 5, 3, 4, 1, 5, 1 }, { 5, 3, 5, 1, 1, 4, 5, 3, 5, 1, 1, 4 }, { 5, 4, 1, 1, 3, 1, 5, 4, 1, 1, 3, 1 } };

  public static final int[] nguhanhsao = { 5, 3, 6, 4, 2, 6, 5, 2, 2, 2, 2, 3, 4, 2, 5, 5, 2, 3, 4, 2, 3, 5, 4, 5, 2, 5, 3, 6, 6, 6, 6, 6, 6, 3, 2, 2, 5, 5, 5, 2, 2, 2, 5, 5, 5, 5, 6, 2, 2, 6, 4, 6, 6, 6, 5, 6, 2, 6, 5, 6, 6, 6, 6, 3, 2, 6, 4, 6, 2, 4, 5, 6, 4, 7, 6, 5, 2, 6, 2, 6, 3, 4, 6, 6, 5, 6, 6, 6, 4, 4, 2, 2, 4, 4, 4, 2, 6, 2, 5, 5, 5, 3, 6, 6, 3, 2, 2, 2, 3, 5 };

  public static final String[] strmenh = { "Hai trung kim", "Gian ha thuy", "TĂ­ch lich hoa", "BĂ­ch thuong tho", "Tang do moc", "Dai khe thuy", "Lo trung hoa", "Thanh dau tho", "Tung bach moc", "Kim bach kim", "Phu dang hoa", "Sa trung tho", "Dai lĂ¢m moc", "Bach lap kim", "Truong luu thuy", "Sa trung kim", "Thien ha thuy", "Thien thuong hoa", "Lo bang tho", "Duong lieu moc", "Tuyen trung thuy", "Son ha hoa", "Dai dich tho", "Thach luu moc", "Kiem phong kim", "Son dau hoa", "Oc thuong tho", "BĂ¬nh dia moc", "Thoa xuyen kim", "Dai hai thuy" };

  public static final int[] nguhanhcung = { 2, 5, 3, 3, 5, 6, 6, 5, 4, 4, 5, 2 };
  public static final int[] nguhanhcolor = { Color.BLACK, Color.GREEN, Color.WHITE, Color.YELLOW, Color.RED, Color.BLACK };
}