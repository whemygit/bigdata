/**
  * Created by root on 17-7-17.
  */
import org.apache.spark.sql.SparkSession
import org.apache.spark.ml.feature.{HashingTF,IDF,Tokenizer}
import org.apache.log4j.{Level,Logger}


object TFIDFXiada {
  def main(args: Array[String]): Unit = {
    Logger.getLogger("org.apache.spark").setLevel(Level.WARN)
    Logger.getLogger("org.eclipse.jetty.server").setLevel(Level.OFF)
    val spark=SparkSession.builder().master("local[6]").appName("TFIDFXiada").getOrCreate()
    import spark.implicits._

    val sentencesData=spark.createDataFrame(Seq(
      (0,"I heard about Spark and I love Spark"),
      (0,"I wish Java could use case classes"),
      (1,"Logistic regression models are neat")
    )).toDF("label","sentence")

    val tokenizer=new Tokenizer().setInputCol("sentence").setOutputCol("words")

    val wordsData=tokenizer.transform(sentencesData)
    wordsData.show(false)

    val hashingTF=new HashingTF().setInputCol("words").setOutputCol("rawFeatures").setNumFeatures(2000)
    val featurizedData=hashingTF.transform(wordsData)

    featurizedData.select("rawFeatures").show(false)

    val idf=new IDF().setInputCol("rawFeatures").setOutputCol("features")
    val iDFModel=idf.fit(featurizedData)
    val rescaledData=iDFModel.transform(featurizedData)
    rescaledData.select("features","label").take(3).foreach(println)

  }

}
