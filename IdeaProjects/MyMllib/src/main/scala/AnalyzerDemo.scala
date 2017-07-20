/**
  * Created by root on 17-7-20.
  */
import org.apache.spark._
import AnalyzeTools.anaylyzerWords
import org.apache.spark.rdd.RDD
import org.apache.log4j.{Level,Logger}

object AnalyzerDemo {
  //分词排序后取出词频最高的前10个
  def main(args: Array[String]): Unit = {
    Logger.getLogger("org.apache.spark").setLevel(Level.WARN)
    Logger.getLogger("org.eclipse.jetty.server").setLevel(Level.OFF)
    val conf = new SparkConf().setAppName("my app").setMaster("local")
    val sc = new SparkContext(conf)
    val data=sc.textFile("file:///root/word1.txt").map(x => {
      val list=anaylyzerWords(x) //分词处理
      list.toString.replace("[", "").replace("]", "").split(",")
    }).flatMap(x => x.toList).map(x => (x.trim(),1)).reduceByKey(_+_).top(10)(Ord.reverse).foreach(println)
  }

  //分词排序
  object Ord extends Ordering[(String,Int)]{
    def compare(a:(String,Int), b:(String,Int))=a._2 compare (b._2)
  }
}
