/**
  * Created by root on 17-8-8.
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
import org.apache.spark.ml.feature.IDF
import org.apache.spark.ml.classification.NaiveBayesModel
import org.apache.log4j.{Level, Logger}
import spark.mllib.NewsClassifySecond.RawDataRecord

object BayesPredict {

  def main(args: Array[String]): Unit = {
        Logger.getLogger("org.apache.spark").setLevel(Level.WARN)
    Logger.getLogger("org.eclipse.jetty.server").setLevel(Level.OFF)
    val conf=new SparkConf().setMaster("local").setAppName("NewsClassifyTest")
    val sc=new SparkContext(conf)

    val spark = SparkSession.builder.master("local").appName("NavieBayesTest").config("spark.some.config.option", "some-value").getOrCreate()
    // For implicit conversions like converting RDDs to DataFrames
    import spark.implicits._

    //分類数据
    val dataPredict = sc.textFile("/root/jieguo").map {
      x =>
        val data = x.split(",")
        RawDataRecord(data(0),data(1))
    }.toDF()

    //分词
    val tokenizer = new Tokenizer().setInputCol("text").setOutputCol("words")
    val wordsData = tokenizer.transform(dataPredict)
//    wordsData.show(false)

    //文档词频
    val hashingTF =
      new HashingTF().setInputCol("words").setOutputCol("rawFeatures").setNumFeatures(100)
    val featurizedData = hashingTF.transform(wordsData)


    //逆文档词频
    val idf = new IDF().setInputCol("rawFeatures").setOutputCol("features")
    val idfModel = idf.fit(featurizedData)
    val rescaledData = idfModel.transform(featurizedData)


//    rescaledData.show(false)
    //转换成贝叶斯的输入格式
    val trainDataRdd = rescaledData.select($"category",$"features").map {
      case Row(label: String, features:Vector) =>
        LabeledPoint(label.toDouble, Vectors.dense(features.toArray))
    }

    val sameModel=NaiveBayesModel.load("target/tmp/NaiveBayesModelTest")

    val predictions = sameModel.transform(trainDataRdd)

    println("predictln out:");
    predictions.show();
    val testResult=predictions.select("label","prediction")
    println("myresult:")
    testResult.show(1)
  }
}
