public class T8_sijie {
  static int N = 20;
  static int[] a = new int[N];
  static int tmp, ans;
  static boolean[] st = new boolean[N];// 一次递归结束之前，数字不能重复

  public static void main(String[] args) {
    dfs(0);
    System.out.println(ans);
  }
  static void dfs(int u) {
    if(u == 3) tmp = 1 + a[0] + a[1] + a[2];                            // 四行
    if(u == 7 && tmp != a[3] + a[4] + a[5] + a[6]) return;
    if(u == 11 && tmp != a[7] + a[8] + a[9] + a[10]) return;
    if(u == 15 && tmp != a[11] + a[12] + a[13] + a[14]) return;

    if(u == 12 && tmp != 1 + a[3] + a[7] + a[11]) return;               // 四列
    if(u == 13 && tmp != a[0] + a[4] + a[8] + a[12]) return;
    if(u == 14 && tmp != a[1] + a[5] + a[9] + a[13]) return;
    if(u == 15 && tmp != a[2] + a[6] + a[10] + a[14]) return;

    if(u == 15 && tmp != 1 + a[4] + a[9] + a[14]) return;               // 两条对角线
    if(u == 12 && tmp != a[2] + a[5] + a[8] + a[11]) return;

    if(u == 15) ans ++;

    for (int i = 2; i <= 16; i ++) {
      if(!st[i]) {
        a[u] = i;
        st[i] = true;
        dfs(u + 1);
        st[i] = false;
      }
    }
  }

}
