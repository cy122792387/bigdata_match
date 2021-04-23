public class T5_hutong {
  public static void main(String[] args) {
    int sum = 0;//所有人，包括小明，门牌号之和
    int count = 0;//总户数
    for(int i = 2;i < 100;i++) {//起始门牌号不确定，枚举所以可能的情况
      sum=0;
      count=0;
      for(int j = i;j < 100;j++) {
        count++;
        sum = sum + j;
        if(sum - count + 1 == 100 && count - 1 >= i)
          System.out.println("i = "+i+", j = "+j+", count = "+(count-1));
      }
    }

  }
}
