package namnt.tuvi.utils;

class DuonglichQuaAmlich
{
  int ngay;
  int thang;
  int can;
  int chi;
  boolean valid;

  int ff(int nam, int start)
  {
    int[] tab = { 363, 7498, 374, 14997, 2947, 3221, 366, 6446, 378, 10861, 2439, 2733, 369, 5546, 380, 11621, 1418, 3749, 372, 7498, 3457, 5706, 365, 3223, 376, 10542, 2950, 1367, 368, 2742, 378, 5548, 1416, 5842, 371, 5797, 3966, 13899, 363, 5707, 375, 13463, 2948, 5275, 367, 2395, 377, 10966, 2439, 2921, 369, 6994, 380, 13989, 1417, 7461, 373, 14923, 3458, 6733, 365, 5293, 376, 10589, 2950, 1389, 368, 3434, 378, 6994, 1927, 7570, 371, 7469, 3966, 6733, 363, 2638, 374, 5294, 3460, 5302, 367, 2741, 377, 5546, 2438, 5802, 370, 3731, 380, 11558, 1418, 5415, 373, 10839, 3971, 2651, 365, 5338, 376, 10933, 2949, 2901, 368, 5962, 379, 13973, 1928, 6805, 371, 5419, 4481, 5419, 364, 2653, 374, 5466, 3459, 5482, 367, 2901, 377, 6986, 2438, 7498, 369, 6805, 381, 6443, 1930, 6446, 373, 2669, 3970, 2742, 365, 5548, 376, 11685, 2949, 3749, 367, 3402, 379, 11414, 2441, 5271, 372, 2359, 4481, 2391, 364, 2742, 374, 5812, 3459, 5844, 366, 5797, 378, 13899, 2439, 6731, 370, 5271, 381, 10583, 1419, 2395, 373, 4826, 3970, 2922, 364, 6996, 376, 15141, 2949, 7461, 368, 6731, 379, 13467, 1929, 5293, 372, 2397, 4481, 2733, 363, 5546, 375, 7509, 2947, 3474, 366, 7462, 377, 6733 };

    nam--;
    switch (start) { case 1:
      return tab[(nam * 2)] & 0x1F;
    case 3:
      return (tab[(nam * 2)] & 0x1E0) >> 5;
    case 5:
      return (tab[(nam * 2)] & 0x1E00) >> 9;
    case 2:
    case 4: } start -= 6;
    int mask = 1 << start;
    return (tab[(nam * 2 + 1)] & mask) >> start;
  }

  DuonglichQuaAmlich(int N, int T, int nam)
  {
    int[] maxday = { 0, 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

    if ((T > 12) || (T < 0) || (N < 0) || (N > maxday[T])) this.valid = false;
    if ((T == 2) && (N == 29) && (nam % 4 != 0)) this.valid = false;
    int C = ff(nam, 1);
    int TA = ff(nam, 3);
    int P = 6;
    int W = ff(nam, P);
    int S = 30 - C + W;
    int U = T;
    if (T > 8) U = T + 1;
    int I = U / 2;
    int NA;
    if (T <= 2) { NA = 31 * I + N;
    } else {
      NA = 30 * T + I + N - 32;
      if (nam % 4 == 0) NA++;
    }
    if (NA <= S) {
      NA = C + NA - 1;
      nam--;
    } else {
      do {
        NA -= S;
        P++;
        TA++;
        W = ff(nam, P);
        S = 29 + W;
      }
      while (NA > S);
      if (TA < 13) { nam--; } else {
        TA -= 12;
        int V = ff(nam, 5);
        if ((V != 0) && (TA > V) && (
          (TA > V + 1) || (NA <= 15))) TA--;
      }
    }
    this.chi = (nam % 12);
    nam += 6;
    this.can = (nam % 10);
    this.ngay = NA;
    this.thang = TA;
  }

  int getngay() {
    return this.ngay;
  }

  int getthang() {
    return this.thang;
  }

  int getcan() {
    return this.can;
  }

  int getchi() {
    return this.chi;
  }
}