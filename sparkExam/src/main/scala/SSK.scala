import org.apache.spark.{SparkConf, SparkContext}

object SSK {
  def main(args: Array[String]): Unit = {
    val sc = new SparkContext(new SparkConf().setAppName("FileSort").setMaster("local"))
    val lines = sc.textFile("D:\\sparkdata\\secondarySort.txt")
    lines.filter(_.trim.length>0)
        .map(x=>(new SecondarySortKey(x.split(" ")(0).toInt,x.split(" ")(1).toInt),x))
        .sortByKey()
        .values
        .collect()
        .foreach(println)
  }
}
class SecondarySortKey(var first:Int,val second:Int) extends Ordered[SecondarySortKey] with Serializable {
  override def compare(that: SecondarySortKey): Int = {
    if(this.first-that.first!=0){
      this.first-that.first
    }else{
      this.second-that.second
    }
  }
}