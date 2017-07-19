/**
  * Created by root on 17-7-14.
  */
import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.SparkConf
import org.apache.log4j.{Level,Logger}
import org.apache.spark.mllib.linalg.distributed._

import org.apache.spark.mllib.recommendation.ALS
import org.apache.spark.mllib.recommendation.MatrixFactorizationModel
import org.apache.spark.mllib.recommendation.Rating


object UserBased {
  def main(args: Array[String]): Unit = {
    //屏蔽日志
    Logger.getLogger("org.apache.spark").setLevel(Level.WARN)
    Logger.getLogger("org.eclipse.jetty.server").setLevel(Level.OFF)
    val inputFile="file:///usr/local/spark/data/mllib/als/test.data"
    val conf=new SparkConf().setAppName("UserBased").setMaster("local[2]")
    val sc=new SparkContext(conf)
    val data=sc.textFile(inputFile)
    val ratings=data.map(_.split(',')match {case Array(user,item,rate)=>
    Rating(user.toInt,item.toInt,rate.toDouble)})

    val rank=10
    val numIterations=10
    val model=ALS.train(ratings,rank,numIterations,0.01)

    val userProducts=ratings.map({case Rating(user,product,rate)=>(user,product)})
    val predictions=
      model.predict(userProducts).map({case Rating(user,product,rate)=>
        ((user,product),rate)})
    val ratesAndPreds=ratings.map({ case Rating(user, product, rate) =>
      ((user, product), rate)}).join(predictions)
    val MSE=ratesAndPreds.map({ case ((user, product), (r1, r2)) =>
      val err = (r1 - r2)
      err*err}).mean()
    println("Mean Squared Error = " + MSE)
  }
}
