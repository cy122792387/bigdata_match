public class T7_ts {
  static int[][] a = new int[10][10];//数组
  static int ans;//结果数
  static boolean[] st = new boolean[15];// 布尔值数组，避免重复

  static boolean judge(int x, int y) {
    if (x == 0) {
      if (y == 0 || a[x][y] > a[x][y - 1]) return true;
    }

    if ((y == 0 && a[x][y] > a[x - 1][y]) || (y != 0 && a[x][y] > a[x][y - 1] && a[x][y] > a[x - 1][y])) return true;

    return false;
  }

  static void dfs(int x, int y) {
    if (x == 2) ans++;// 每次填数组都会判断满足条件，只要填完了也判断完了，多了一种结果 x==2代表10个都填完了
    for (int i = 1; i <= 10; i++) {
      if (!st[i]) {//如果这个数组没有填
        a[x][y] = i;//填上
        if (judge(x, y)) {//如果成功
          st[i] = true;
          if (y == 4) dfs(x + 1, 0);//换行
          else dfs(x, y + 1);//每一次都去填右边格子
          st[i] = false;
        }
      }
    }
  }

  public static void main(String[] args) {
    dfs(0, 0);
    System.out.println(ans);
  }

}
