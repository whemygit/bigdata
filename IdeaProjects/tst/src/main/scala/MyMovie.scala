/**
  * Created by root on 17-7-28.
  */
import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.mllib.recommendation.ALS
import org.apache.log4j.{Level,Logger}

object MyMovie {
  def main(args: Array[String]): Unit = {
    Logger.getLogger("org.apache.spark").setLevel(Level.WARN)
    Logger.getLogger("org.eclipse.jetty.server").setLevel(Level.OFF)

    val inputfile="file:///root/bigdata/IdeaProjects/MyMllib/data/ml-100k/u.data"
    val sc=new SparkContext("local[2]","Movie Lens App")
    val rawData=sc.textFile(inputfile)
    val rawRatings=rawData.map(_.split("\t").take(3))
    println(rawRatings.first())
    rawRatings.first().foreach(println)

  }

}
