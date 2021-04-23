public class T2_pingfang {
  public static void main(String[] args) {
    int []ffs=new int[2020];//数组用来记录每个数字
    ffs[0]=1;
    int n=2019;
    for (int i = 1; i <= n; i++) {//有哪些数字组成
      for (int j = n; j -i*i>=0 ; j--) {
        if(ffs[j-i*i]!=0) {
          ffs[j] += ffs[j - i * i];// 新的数字是i, 结果数字可能数量 = ffs[j - i * i] （1...i-1数字构成的 j - i * i 可能性的和）
//          System.out.println("当前把"+i+"的平方"+i*i+"考虑进来");
//          System.out.println("ffs["+j+"]="+ffs[j]);
          // 2019 f(2019)=f(2019-44*44) //f(2019-44*44) 个数是1-43 个数和
        }
      }
    }
//    for (int i = 0; i <= n; i++) {
      System.out.println(ffs[n]);
//    }
  }

}