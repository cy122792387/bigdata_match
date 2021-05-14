import org.apache.spark.{SparkConf, SparkContext}
import scala.util.control._

object C {

  def main(args: Array[String]): Unit = {
    val sc = new SparkContext(new SparkConf().setAppName("C"))
    //    step1(sc)

    while (true) {
    }
  }


}

def step4(sc: SparkContext): Unit = {
  val lines = sc.textFile("/root/test/C/hotelsparktask2/*")
  lines.filter(x => {
    val province = x.split(",")(3)
    val starLev = x.split(",")(7)
    var res = true
    if (province.contains("北京") || province.contains("上海") || province.contains("广东")
        || province.contains("四川") || province.contains("海南")) {
    } else {
      res = false
    }
    if (starLev.contains("四星") || starLev.contains("五星")) {
    } else {
      res = false
    }
    res
  }).map(x => {
        val fields = x.split(",")
        (fields(3), fields(11) + "," + fields(12))
      }).groupByKey().map(x => {
        val province = x._1
        val fields = x._2
        var count = 0
        var avgRate: Double = 0
        var avgComment = 0
        for (f <- fields) {
          count += 1
          val fs = f.split(",")
          if ("NULL".equals(fs(0)) || "".equals(fs(0))) {
            fs(0) = "0"
          }
          if ("NULL".equals(fs(1)) || "".equals(fs(1))) {
            fs(1) = "0"
          }
          avgRate = avgRate + fs(0).toDouble
          avgComment = avgComment + fs(1).toInt
        }
        val StrAvgRate = (avgRate / count).formatted("%.2f")
        province + "，" + count + "，" + StrAvgRate + "，" + avgComment
      }).collect().foreach(println)

}
def step3(sc: SparkContext): Unit = {
  val lines = sc.textFile("/root/test/C/hotelsparktask2/*")
  lines.map(x => (x.split(",")(4), x)).groupByKey().map(x => {
    val city = x._1
    val lines = x._2
    var province = ""
    var numOfHotel = lines.size
    var numOfRoom = 0
    for (line <- lines) {
      val fields = line.split(",")
      province = fields(3)
      if ("NULL".equals(fields(9))) {
        fields(9) = "0"
      }
      numOfRoom += fields(9).toInt
    }
    (numOfRoom, province + "," + city + "," + numOfHotel + "," + numOfRoom)
  }).sortByKey(false).map(_._2).take(10).foreach(println)
}
def step2(sc: SparkContext): Unit = {
  val lines = sc.textFile("/root/test/C/hotelsparktask1/*")
  val delRateAccount = sc.longAccumulator("delRateAccount");
  val delStarLevAccount = sc.longAccumulator("delStarLevAccount");
  val lines2 = lines.filter(x => {
    val fields = x.split(",")
    if ("NULL".equals(fields(11))) {
      fields(11) = "-1"
    }
    var rate = fields(11).toDouble
    var starLevel = fields(7)
    var res = true
    if (rate < 0 || rate > 10) {
      delRateAccount.add(1)
      res = false
    }
    if (!(starLevel.contains("二星") || starLevel.contains("三星")
        || starLevel.contains("四星") || starLevel.contains("五星"))) {
      delStarLevAccount.add(1)
      res = false
    }
    res
  }
  )
  val line2size = lines2.collect().size
  print("评分不合法数量：" + delRateAccount.value)
  print("星级不合法数量：" + delStarLevAccount.value)
  //累加器的输出需要在一次action操作之后，不能在2次action操作之后
  val lines3 = lines2.distinct()
  val lines3size = lines3.collect().size
  val duplicate = line2size - lines3size
  val total = lines.collect().size - lines3size
  print("冗余数据有：" + duplicate)
  print("删除总数据条数：" + total)
  lines3.saveAsTextFile("/root/test/C/hotelsparktask2")
}

def step1(sc: SparkContext): Unit = {
  val lines = sc.textFile("/root/test/C/input/*")
  lines.filter(x => {
    var first = false;
    if (x.startsWith("SEQ")) {
      first = true
    }
    val fields = x.split(",")
    var count = 0;
    for (f <- fields) {
      if ("NULL".equals(f) || "".equals(f)) {
        count += 1
      }
    }
    if (count > 3 || first == true) {
      false
    } else {
      true
    }
  }).saveAsTextFile("/root/test/C/hotelsparktask1")

}
