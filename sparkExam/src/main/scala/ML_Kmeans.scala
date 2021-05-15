import org.apache.spark.ml.clustering.KMeans
import org.apache.spark.ml.feature.{OneHotEncoder, StandardScaler, VectorAssembler}
import org.apache.spark.ml.{Pipeline, PipelineModel}
import org.apache.spark.sql.SparkSession

object ML_Kmeans {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().getOrCreate()
    val rawdata = spark.read.format("csv").option("header", true).load("file:///root/jars/customs_sale.csv")
    val data1 = rawdata.select(
      rawdata("Channel").cast("Double"),
      rawdata("Region").cast("Double"),
      rawdata("Fresh").cast("Double"),
      rawdata("Milk").cast("Double"),
      rawdata("Grocery").cast("Double"),
      rawdata("Frozen").cast("Double"),
      rawdata("Detergents_Paper").cast("Double"),
      rawdata("Delicassen").cast("Double")).cache()
    //
    val datahotl = new OneHotEncoder().setInputCol("Channel").setOutputCol("Channelvector").setDropLast(false)
    val datahot2 = new OneHotEncoder().setInputCol("Region").setOutputCol("Regionvector").setDropLast(false)
    val featuresArray = Array("Channelvector", "Regionvector", "Fresh", "Milk", "Grocery", "Frozen", "Detergents_Paper", "Delicassen")
    val vecDF = new VectorAssembler().setInputCols(featuresArray).setOutputCol("features")
    val scaledDF = new StandardScaler().setInputCol("features").setOutputCol("scaledFeatures").setWithStd(true).setWithMean(false)
    val kmeans = new KMeans().setFeaturesCol("scaledFeatures").setK(4).setSeed(123)

    val pipeline1 = new Pipeline().setStages(Array(datahotl, datahot2, vecDF, scaledDF))
    val data2 = pipeline1.fit(data1).transform(data1)
    //训练模型
    val model = kmeans.fit(data2)
    val results = model.transform(data2)
    //评估模型
    val WSSSE = model.computeCost(data2)
    println(s"Within Set Sum of Squared Errors=$WSSSE")
    //显示聚类结果
    println("Cluster Centers:")
    model.clusterCenters.foreach(println)
    results.collect().foreach(row => {
      println(row(10) + " is predicted as cluster" + row(11))
    })
  }
}
