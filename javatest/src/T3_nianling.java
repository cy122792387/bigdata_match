public class T3_nianling {
  public static void main(String[] args) {
    int x,y;
    int count = 0;
    for(int i=27;i<=100;i++) {
      x = i/10;    //十位数
      y = i%10;    //个位数
      if(i - (x + 10*y) == 27) {
//        System.out.println(i);
        count++;
      }
    }
    System.out.println(count);
  }
}
