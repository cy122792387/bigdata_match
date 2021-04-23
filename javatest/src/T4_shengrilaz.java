public class T4_shengrilaz {
  public static void main(String[] args) {
    int i = 0;
    for (i = 1; i < 100; i++)//从1枚举到100,开始吹蜡烛的年龄
    {
      int sum = 0;
      int temp = i;//从几岁开始过生日
      while (sum < 236)//一共过了几次生日不定，所以循环次数不定
      {
        sum += temp;
        temp++;
      }//从开始举办party吹的蜡烛，以后一直一个等差数列
      if (sum == 236)
      {
        System.out.println(i);
        break;
      }
    }
  }
}
