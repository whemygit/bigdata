/**
  * Created by root on 17-7-17.
  */
import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.mllib.feature.{HashingTF,IDF}
import org.apache.spark.mllib.linalg.Vector
import org.apache.spark.rdd.RDD
import org.apache.log4j.{Level,Logger}

object TFIDFExample {
  def main(args: Array[String]): Unit = {
    Logger.getLogger("org.apache.spark").setLevel(Level.WARN)
    Logger.getLogger("org.eclipse.jetty.server").setLevel(Level.OFF)
    val conf=new SparkConf().setAppName("TFIDFExample").setMaster("local")
    val sc=new SparkContext(conf)

    val documents: RDD[Seq[String]] = sc.textFile("data/mllib/kmeans_data.txt").map(_.split(" ").toSeq)
    val hashingTF=new HashingTF()
    val tf: RDD[Vector]=hashingTF.transform(documents)

    tf.cache()
    val idf=new IDF().fit(tf)
    val tfidf:RDD[Vector]=idf.transform(tf)

    val idfIgnore = new IDF(minDocFreq = 2).fit(tf)
    val tfidfIgnore: RDD[Vector] = idfIgnore.transform(tf)

    println("tfidf: ")
    tfidf.foreach(x=>println(x))

    println("tfidfIgnore: ")
    tfidfIgnore.foreach(x=>println(x))
    sc.stop()
  }

}
