/**
  * Created by root on 17-7-17.
  */
import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.mllib.feature.{Word2Vec,Word2VecModel}
import org.apache.log4j.{Level,Logger}

object Word2VecExample {
  def main(args: Array[String]): Unit = {
    Logger.getLogger("org.apache.spark").setLevel(Level.WARN)
    Logger.getLogger("org.eclipse.jetty.server").setLevel(Level.OFF)
    val conf=new SparkConf().setAppName("Word2VecExample").setMaster("local")
    val sc=new SparkContext(conf)

    val input=sc.textFile("data/mllib/sample_lda_data.txt").map(line=>line.split(" ").toSeq)
    val word2Vec=new Word2Vec()
    val model=word2Vec.fit(input)

    val synonyms=model.findSynonyms("1",5)
    for((synonym,cosineSimilarity)<-synonyms){
      println(s"$synonym $cosineSimilarity")
    }
    model.save(sc,"myModelPath")
    val sameModel = Word2VecModel.load(sc,"myModelPath")

    sc.stop()
  }

}
