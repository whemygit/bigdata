/**
  * Created by root on 17-8-8.
  */
package spark.mllib


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
import org.apache.spark.ml.classification.{NaiveBayes, NaiveBayesModel}
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator
import org.apache.log4j.{Level, Logger}

object NewsClassifySecond {
   case class RawDataRecord(category: String, text: String)

  def main(args: Array[String]): Unit = {
    Logger.getLogger("org.apache.spark").setLevel(Level.WARN)
    Logger.getLogger("org.eclipse.jetty.server").setLevel(Level.OFF)
    val conf=new SparkConf().setMaster("local").setAppName("NewsClassifyTest")
    val sc=new SparkContext(conf)

    val spark = SparkSession.builder.master("local").appName("Spark SQL basic example").config("spark.some.config.option", "some-value").getOrCreate()
    // For implicit conversions like converting RDDs to DataFrames
    import spark.implicits._

//    val datas=sc.textFile("/root/train_corpus/1caijing")
//    println(datas.first())
//    val ss=datas.first().split(",")(0)
//    println(ss)

    //分数据
    val Array(srcDF,testDF) = sc.textFile("/root/train_corpus").map {
      x =>
        val data = x.split(",")
        RawDataRecord(data(0),data(1))
    }.toDF().randomSplit(Array(0.9,0.1))

    //分词
    val tokenizer = new Tokenizer().setInputCol("text").setOutputCol("words")
    val wordsData = tokenizer.transform(srcDF)
//    wordsData.show(false)
    val testtokenizer = new Tokenizer().setInputCol("text").setOutputCol("words")
    val testwordsData = testtokenizer.transform(testDF)

    //文档词频
    val hashingTF =
      new HashingTF().setInputCol("words").setOutputCol("rawFeatures").setNumFeatures(500000)
    val featurizedData = hashingTF.transform(wordsData)

    val testhashingTF =
      new HashingTF().setInputCol("words").setOutputCol("rawFeatures").setNumFeatures(500000)
    val testfeaturizedData = testhashingTF.transform(testwordsData)

    //逆文档词频
    val idf = new IDF().setInputCol("rawFeatures").setOutputCol("features")
    val idfModel = idf.fit(featurizedData)
    val rescaledData = idfModel.transform(featurizedData)

    val testidf = new IDF().setInputCol("rawFeatures").setOutputCol("features")
    val testidfModel = testidf.fit(testfeaturizedData)
    val testrescaledData = testidfModel.transform(testfeaturizedData)
//    rescaledData.show(false)
    //转换成贝叶斯的输入格式
    val trainDataRdd = rescaledData.select($"category",$"features").map {
      case Row(label: String, features:Vector) =>
        LabeledPoint(label.toDouble, Vectors.dense(features.toArray))
    }

    val testtrainDataRdd = testrescaledData.select($"category",$"features").map {
      case Row(label: String, features:Vector) =>
        LabeledPoint(label.toDouble, Vectors.dense(features.toArray))
    }

    val model =new NaiveBayes().fit(trainDataRdd)

    val predictions = model.transform(testtrainDataRdd)
    println("predictln out:");
    predictions.show(1);
    val testResult=predictions.select("label","prediction")
    println("myresult:")
    testResult.show(1)
////    model.write.overwrite().save("resoult")
//
//
//    //模型评估
//    val evaluator = new MulticlassClassificationEvaluator()
//      .setLabelCol("label")
//      .setPredictionCol("prediction")
//      .setMetricName("accuracy")
//    val accuracy = evaluator.evaluate(predictions)
//    println("accuracy out :")
//    println("Accuracy:"+accuracy)


//////    模型再利用
//    model.save("target/tmp/NewsClassifyTrainModel")
//    val sameModel=NaiveBayesModel.load("target/tmp/NewsClassifyTrainModel")
//
//    val predictions_loadmodel = sameModel.transform(testtrainDataRdd)
////    println("predictln out:");
////    predictions.show();
//    val testResult_loadmodel=predictions_loadmodel.select("label","prediction")
////    println("myresult_loadmodel:")
////    testResult_loadmodel.show();
//
////
////
//    //模型评估
//    val evaluator_loadmodel = new MulticlassClassificationEvaluator()
//      .setLabelCol("label")
//      .setPredictionCol("prediction")
//      .setMetricName("accuracy")
//    val accuracy_loadmodel = evaluator_loadmodel.evaluate(predictions_loadmodel)
//    println("accuracy out :")
//    println("Accuracy:"+accuracy_loadmodel)

    //分類数据
//    val dataPredict = sc.textFile("/root/jieguo").map {
//      x =>
//        val data = x.split(",")
//        RawDataRecord(data(0),data(1))
//    }.toDF()
//    dataPredict.show()
//
//    //分词
//    val tokenizerPredict = new Tokenizer().setInputCol("text").setOutputCol("words")
//    val wordsDataPredict = tokenizerPredict.transform(dataPredict)
//    wordsDataPredict.show(false)
//
//    //文档词频
//    val hashingTFPredict =
//      new HashingTF().setInputCol("words").setOutputCol("rawFeatures").setNumFeatures(500000)
//    val featurizedDataPredict = hashingTFPredict.transform(wordsData)
//    featurizedDataPredict.show(false)
//
//
//    //逆文档词频
//    val rescaledDataPredict = idfModel.transform(featurizedDataPredict)
//    rescaledDataPredict.show()
//
//    //转换成贝叶斯的输入格式
//    val predictDataRdd = rescaledDataPredict.select($"category",$"features").map {
//      case Row(label: String, features:Vector) =>
//        LabeledPoint(label.toDouble, Vectors.dense(features.toArray))}

//    val predictionsPredict = model.transform(predictDataRdd)
//    predictionsPredict.show()
  }
}
