/**
  * Created by root on 17-8-14.
  */

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession
import org.apache.spark.SparkContext
import org.apache.spark.ml.feature.Tokenizer
import org.apache.spark.ml.feature.HashingTF
import org.apache.spark.sql.Row
import org.apache.spark.ml.linalg.Vector
import org.apache.spark.ml.linalg.Vectors
import org.apache.spark.ml.feature.LabeledPoint
import org.apache.spark.ml.feature.IDFModel
import org.apache.spark.ml.classification.NaiveBayesModel
import org.apache.log4j.{Level, Logger}
import spark.mllib.NewsClassifyBayes.RawDataRecord

object BayesPredictPythonData {

  def main(args: Array[String]): Unit = {
    Logger.getLogger("org.apache.spark").setLevel(Level.WARN)
    Logger.getLogger("org.eclipse.jetty.server").setLevel(Level.OFF)
    val conf=new SparkConf().setMaster("local").setAppName("NewsClassifyTest")
    val sc=new SparkContext(conf)

    val spark = SparkSession.builder.master("local").appName("NavieBayesTest").config("spark.some.config.option", "some-value").getOrCreate()
    // For implicit conversions like converting RDDs to DataFrames
    import spark.implicits._

    //分類数据
    val dataPredict = sc.textFile("/root/predict_data").map {
      x =>
        val data = x.split(",")
        RawDataRecord(data(0),data(1))
    }.toDF()

    //分词
    val tokenizer = new Tokenizer().setInputCol("text").setOutputCol("words")
    val wordsData = tokenizer.transform(dataPredict)
//    wordsData.show(1)

    //文档词频
    val hashingTF =
      new HashingTF().setInputCol("words").setOutputCol("rawFeatures").setNumFeatures(500000)
    val featurizedData = hashingTF.transform(wordsData)
//    featurizedData.show(1)


    //逆文档词频
    val loadidfModel=IDFModel.load("target/tmp/NewsClassifyidfModelResult")

    val rescaledData = loadidfModel.transform(featurizedData)
//    rescaledData.show()


//    rescaledData.show(false)
    //转换成贝叶斯的输入格式
    val predictDataRdd = rescaledData.select($"category",$"features").map {
      case Row(label: String, features:Vector) =>
        LabeledPoint(label.toDouble, Vectors.dense(features.toArray))
    }

    val nbModel=NaiveBayesModel.load("target/tmp/NewsClassifyBayesModelResult")

    val predictions = nbModel.transform(predictDataRdd)

//    println("predictln out:");
//    predictions.show();
    val predictResult=predictions.select("label","prediction")
    println("myresult:")
    predictResult.show()

    predictResult.rdd.saveAsTextFile("file:///root/predictresult/writeback")

//    predictResult.write.json("file:///root/predictresult/result")

  }
}
