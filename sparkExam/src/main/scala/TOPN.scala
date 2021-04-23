import org.apache.spark.{SparkConf, SparkContext}

object TOPN {
  def main(args: Array[String]): Unit = {
    val sc = new SparkContext(new SparkConf().setMaster("local").setAppName("TOPN"))
    val lines = sc.textFile("D:\\360安全云盘同步版\\教学\\大数据比赛\\sparkdata\\topn")
    var num = 0;
    val result = lines.filter(line => (line.trim.length > 0) && line.split(",").length == 4)
        .map(x => (x.split(",")(2).toInt, ""))
        .sortByKey(false)
        .keys
        .take(5)
    result.foreach(x => {
      num = num + 1;
      println(num + "\t" + x)
    })
  }
}
