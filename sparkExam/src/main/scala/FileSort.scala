import org.apache.spark.{HashPartitioner, SparkConf, SparkContext}

object FileSort {
  def main(args: Array[String]): Unit = {
    System.setProperty("hadoop.home.dir", "C:\\winutil\\")
    val sc = new SparkContext(new SparkConf().setAppName("FileSort").setMaster("local"))
    val lines = sc.textFile("D:\\sparkdata\\filesort",3)
    var index = 0
    lines.filter(_.trim.length>0)
        .map(x=>(x.toInt,""))
        .partitionBy(new HashPartitioner(1))
        .sortByKey()
        .map(x=>{
          index+=1
          (index,x._1)
        })
        .saveAsTextFile("D:\\sparkdata\\filesort\\result")
  }
}
