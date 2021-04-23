import org.apache.spark.{SparkConf, SparkContext}

import scala.util.parsing.json.JSON

object Test {
  val config = new SparkConf().setAppName("wc").setMaster("local")
  val sc = new SparkContext(config)

  def main(args: Array[String]): Unit = {
    testJson()

  }

  def testJson(): Unit = {
    val jsonStrs = sc.textFile("D:\\people.json")
    val result = jsonStrs.map(s => JSON.parseFull(s))
    result.foreach(r => r match {
      case Some(map: Map[String, Any]) => println(map)
      case None => println("parsint failed")
      case other => println("unknown struct" + other)
    })

  }

  def keyAverage(): Unit = {
    val rdd = sc.parallelize(Array(("spark", 2), ("hadoop", 6), ("hadoop", 4), ("spark", 6)))
        .mapValues((_, 1))
        .reduceByKey((x, y) => (x._1 + y._1, x._2 + y._2))
        .mapValues(x => x._1 / x._2)
    rdd.foreach(println)
  }

  def wordCount(): Unit = {
    sc.setLogLevel("WARN")
    val rdd = sc.textFile("D:\\1.txt")
        .flatMap(_.split(" "))
        .map((_, 1))
        .reduceByKey(_ + _)
    rdd.foreach(println)
  }
}

