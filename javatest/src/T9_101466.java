import java.util.HashSet;
import java.util.Set;

public class T9_101466 {
  static int wall[][]=new int[10000][10000];//墙
  static int n,m;
  static Set<String> se=new HashSet<String>();
  static int count;

  public static void main(String[] args) {
    M();
  }
  public static Set M() {
    for (int i = 0; i < wall.length; i++) {
      for (int j = 0; j < wall[0].length; j++) {
        wall[i][j]=-1;
      }
    }
    n=3;m=4;
    dfs(1,1);
    System.out.println(se.size());
//    System.out.println(count);
    return se;
  }
  static boolean check(){
    for(int i=1;i<n;i++){
      for(int j=1;j<m;j++){
        if((wall[i][j]+wall[i][j+1]+wall[i+1][j]+wall[i+1][j+1])%4==0){//保证正方形不同色
          return false;
        }
      }
    }
    return true;
  }
  static void dfs(int x,int y){
    if(x==n+1){//贴完了
      if(check()){
        String s = "";
        for(int i=1;i<=n;i++){
          for(int j=1;j<=m;j++){
            s+=wall[i][j];
          }
        }
        se.add(s);
        count++;
      }
      return;
    }
    if(wall[x][y]==-1){//这个地方空着
      if(wall[x][y+1]==-1&&y+1<=m){//可以横着贴
        for(int color=1;color<3;color++){//选一种颜色
          wall[x][y]=wall[x][y+1]=color;
          if(y+2<=m)dfs(x,y+2);//贴完之后还可以继续往后贴
          else dfs(x+1,1);//贴完之后要到下一排开始贴
          wall[x][y]=wall[x][y+1]=-1;
        }
      }
      if(wall[x+1][y]==-1&&x+1<=n){//可以竖着贴
        for(int color=1;color<3;color++){//选一种颜色
          wall[x][y]=wall[x+1][y]=color;
          if(y+1<=m)dfs(x,y+1);//贴完之后还可以继续往后贴
          else dfs(x+1,1);//贴完之后要到下一排开始贴
          wall[x][y]=wall[x+1][y]=-1;

        }
      }
    }else{//有砖
      if(y==m)dfs(x+1,1);
      else dfs(x,y+1);
    }
  }
}
