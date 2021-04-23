import org.apache.spark.{SparkConf, SparkContext}

object MoveRate {
  def main(args: Array[String]): Unit = {
    val sc = new SparkContext(new SparkConf().setAppName("MoveRate").setMaster("local"))
    val movies = sc.textFile("D:\\sparkdata\\movies.csv")
    val ratings0 = sc.textFile("D:\\sparkdata\\ratings.csv")
    val ratings=ratings0.map(line=>{
      val fields=line.split(",")
      (fields(1).toInt,fields(2).toDouble)
    })
    val movie_avg_rate = ratings.map(x=>(x._1,(x._2,1)))
        .reduceByKey((x,y)=> (x._1+y._1,x._2+y._2))
        .map(x=>(x._1,x._2._1/x._2._2))
        .filter(_._2>4)
        .collect()
        .foreach(print)
    //TODO
  }
}
/*movies.csv
movieId	title	            genres
1	    Toy Story (1995)	Adventure|Animation|Children|Comedy|Fantasy*/

/*ratings.csv
userId	movieId	rating	timestamp
    1	    296  	5	    1147880044*/
