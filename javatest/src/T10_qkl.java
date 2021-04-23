import java.util.Scanner;

public class T10_qkl {
  static int n;
  static int k;
  static int[][] chocolate;
  public static void main(String[] args) {
    Scanner scaner = new Scanner(System.in);
    n = scaner.nextInt();//从n块中切出k块小的
    k = scaner.nextInt();
    chocolate = new int[n][2];
    int max = 0;
    for (int i = 0; i < n; i++) {
      chocolate[i][0] = scaner.nextInt();
      chocolate[i][1] = scaner.nextInt();
      int cmin = Math.min(chocolate[i][0], chocolate[i][1]);
      max = max > cmin? max:cmin;//max代表最大能切多大 即所以长方形的最大短边
    }
    scaner.close();
    for (int i = max; i >= 1; i--) {
      if (solve(i)){//在现有情况下 ，想切的边长也确定，能切多少块
        System.out.println(i);
        break;
      }
    }//从大到小去找
/*    int l=1,r=max;
    int res=-1;
    while (l<=r){
      int m=(l+r)/2;
      if(solve(m)){
        res=m;
        l=m+1;
      }else {
        r=m-1;
      }
    }
    System.out.println(res);*/
  }
  public static boolean solve(int i){//i是你想要切的边长
    int cPiece = 0;
    for (int j = 0; j < n; j++)
      cPiece+=(chocolate[j][0]/i)*(chocolate[j][1]/i);//现在想切边长为i的正方形，这个试子算出了chocolate[j] 能切几块
    if(cPiece>=k) {//只有当切出的块数大于小朋友的个数才是满足要求的
      return true;
    }
    return  false;
  }
}
/*
样例输入：
    2 10
    6 5
    5 6

    样例输出：
    2*/
