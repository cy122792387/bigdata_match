import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class T6_ssxl {
  static int m = 30;//行
  static int n = 50;//列
  static char[][] ch = new char[m][n];//读文件 放到数组里面

  public static void main(String[] args) throws FileNotFoundException {
    Scanner sc = new Scanner(new File("D:\\inc.txt"));
    for (int i = 0; i < m; i++) {
      String tStr = sc.next();
      for (int j = 0; j < n; j++) {
        ch[i][j] = tStr.charAt(j);
      }
    }//读文件
    List<String> s=new LinkedList<>();//存结果
    char []a=new char[3];//临时存储，便于比较是否递增
    for(int i=0;i<m;i++){
      for(int j=0;j<n;j++){// chi[i][j] 代表所有的位置上的字母
        if(ch[i][j]=='y'||ch[i][j]=='z');
        //向右查看
        for(int k=j+1;k<n;k++){
          if(ch[i][k]=='z') continue;
          for(int l=k+1;l<n;l++){
            if(ch[i][j]-ch[i][k]<0&&ch[i][k]-ch[i][l]<0){
              a[0]=ch[i][j];
              a[1]=ch[i][k];
              a[2]=ch[i][l];
              String tmp= new String(a);
              s.add(tmp);
            }
          }
        }
        //向下查看
        for(int k=i+1;k<m;k++){
          if(ch[k][j]=='z') continue;
          for(int l=k+1;l<m;l++){
            if(ch[i][j]-ch[k][j]<0&&ch[k][j]-ch[l][j]<0){
              a[0]=ch[i][j];
              a[1]=ch[k][j];
              a[2]=ch[l][j];
              String tmp= new String(a);
              s.add(tmp);
            }
          }
        }
        //向右下查看
        for(int k1=i+1,k2=j+1;k1<m&&k2<n;k1++,k2++){
          if(ch[k1][k2]=='z') continue;
          for(int l1=k1+1,l2=k2+1;l1<m&&l2<n;l1++,l2++){
            if(ch[i][j]-ch[k1][k2]<0&&ch[k1][k2]-ch[l1][l2]<0){
              a[0]=ch[i][j];
              a[1]=ch[k1][k2];
              a[2]=ch[l1][l2];
              String tmp= new String(a);
              s.add(tmp);
            }
          }
        }
        //向右上查看
        for(int k1=i-1,k2=j+1;k1>=0&&k2<n;k1--,k2++){
          if(ch[k1][k2]=='z') continue;
          for(int l1=k1-1,l2=k2+1;l1>=0&&l2<n;l1--,l2++){
            if(ch[i][j]-ch[k1][k2]<0&&ch[k1][k2]-ch[l1][l2]<0){
              a[0]=ch[i][j];
              a[1]=ch[k1][k2];
              a[2]=ch[l1][l2];
              String tmp= new String(a);
              s.add(tmp);
            }
          }
        }
        //向左下查看
        for(int k1=i+1,k2=j-1;k1<m&&k2>=0;k1++,k2--){
          if(ch[k1][k2]=='z') continue;
          for(int l1=k1+1,l2=k2-1;l1<m&&l2>=0;l1++,l2--){
            if(ch[i][j]-ch[k1][k2]<0&&ch[k1][k2]-ch[l1][l2]<0){
              a[0]=ch[i][j];
              a[1]=ch[k1][k2];
              a[2]=ch[l1][l2];
              String tmp= new String(a);
              s.add(tmp);
            }
          }
        }
      }
    }
//    for (String str:s) {
//      System.out.println(str);
//    }
    System.out.println(s.size());
  }
}
