
public class T1_shulie {
  public static void main(String[] args) {
    int n=20190324;
    int a[]=new int[n];
    a[0]=a[1]=a[2]=1;
    for(int i=3;i<n;i++) {
      a[i]=a[i-1]+a[i-2]+a[i-3];
      a[i]%=10000;
    }
    System.out.println(a[n-1]);
  }
}
