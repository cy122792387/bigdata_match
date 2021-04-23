import org.apache.spark.{SparkConf, SparkContext}

object MaxMin {
  def main(args: Array[String]): Unit = {
    val sc = new SparkContext(new SparkConf().setMaster("local").setAppName("TOPN"))
    val lines = sc.textFile("D:\\360安全云盘同步版\\教学\\大数据比赛\\sparkdata\\maxmin")
    sc.setLogLevel("ERROR")
    val result = lines.filter(_.trim.length > 0)
        .map(line => ("key", line.trim.toInt))
        .groupByKey()
        .map(x => {
          var min = Integer.MAX_VALUE
          var max = Integer.MIN_VALUE
          for (num <- x._2) {
            if (num > max) max = num
            if (num < min) min = num
          }
          (max, min)
        })
    result.collect()
        .foreach(x => {
          println("max = " + x._1)
          println("min =" + x._2)
        })
  }
}