import java.util.HashSet;
import java.util.Set;

public class T9_CiZhuanYangShi {//瓷砖样式
  static int row = 3;
  static int rank = 10;
  static int count = 0;
  static int[][] arr = new int[row + 2][rank + 2];    //--------------①
  static Set<String> set=new HashSet<>();
  public static void main(String[] args) {
    int i = 0;
    int j = 0;
    for (i = 1; i <= row; i++) {    //-------------②
      for (j = 1; j <= rank; j++) {
        arr[i][j] = -1;
      }
    }//初始化，-1
    dfs(1, 1);
//    Set<String> set2= T9_101466.M();
    System.out.println(set.size());
//    System.out.println(set2.size());
//    for (String s1:set) {
//      if (!set2.contains(s1)){
//        System.out.println(s1);
//      }
//    }
  }


  static int Judge(int x, int y) {    //每一块砖的左上、右上、左下、右下四个2*2方格
    if (arr[x][y] == arr[x - 1][y] && arr[x][y] == arr[x - 1][y - 1] && arr[x][y] == arr[x][y - 1]) {    //左上
      return 0;
    }
    if (arr[x][y] == arr[x - 1][y] && arr[x][y] == arr[x - 1][y + 1] && arr[x][y] == arr[x][y + 1]) {    //右上
      return 0;
    }
    if (arr[x][y] == arr[x][y - 1] && arr[x][y] == arr[x + 1][y - 1] && arr[x][y] == arr[x + 1][y]) {    //左下
      return 0;
    }
    if (arr[x][y] == arr[x][y + 1] && arr[x][y] == arr[x + 1][y] && arr[x][y] == arr[x + 1][y + 1]) {    //右下
      return 0;
    }
    return 1;
  }
  static  boolean check(){
    for(int i=1;i<row;i++){
      for(int j=1;j<rank;j++){
        if((arr[i][j]+arr[i][j+1]+arr[i+1][j]+arr[i+1][j+1])%4==0){//保证正方形不同色
          return false;
        }
      }
    }
    return true;
  }
  public static void printarr() {
    for (int i = 1; i < row + 1; i++) {
      for (int j = 1; j < rank + 1; j++) {
        System.out.print(arr[i][j]);
      }
    }
    System.out.println();
  }
  public static void add(){
    StringBuffer stringBuffer = new StringBuffer();
    for (int i = 1; i <= row; i++) {
      for (int j = 1; j <= rank; j++) {
        stringBuffer.append(arr[i][j]);
      }
    }
    set.add(stringBuffer.toString());

  }
  public static void dfs(int x, int y) {
    if (x == row && y == rank + 1) {//贴完了，计数+1
      count++;
      if(check())
      add();
      return;
    }
    if (y == rank + 1) {//换行了
      dfs(x + 1, 1);
      return;
    }
    if (arr[x][y] == -1) {//当前这个x y 没有贴
      if (arr[x][y + 1] == -1) {    //    横铺1
        arr[x][y] = 1;
        arr[x][y + 1] = 1;
        if (Judge(x, y) == 1) {
          dfs(x, y + 1);
        }
        arr[x][y] = -1;
        arr[x][y + 1] = -1;
      }
      if (arr[x][y + 1] == -1) {    //    横铺2
        arr[x][y] = 2;
        arr[x][y + 1] = 2;
        if (Judge(x, y) == 1) {
          dfs(x, y + 1);
        }
        arr[x][y] = -1;
        arr[x][y + 1] = -1;
      }
      if (arr[x + 1][y] == -1) {    //    竖铺2
        arr[x][y] = 2;
        arr[x + 1][y] = 2;
        if (Judge(x, y) == 1) {
          dfs(x, y + 1);
        }
        arr[x][y] = -1;
        arr[x + 1][y] = -1;
      }
      if (arr[x + 1][y] == -1) {    //    竖铺1
        if(x==1&&y==1){
          System.out.println();
        }
        arr[x][y] = 1;
        arr[x + 1][y] = 1;
        if (Judge(x, y) == 1) {
          dfs(x, y + 1);
        }
        arr[x][y] = -1;
        arr[x + 1][y] = -1;
      }

    } else {
      dfs(x, y + 1);
    }
  }

}
