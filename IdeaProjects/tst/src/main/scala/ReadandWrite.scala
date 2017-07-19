/**
  * Created by root on 17-7-18.
  */
//wenbenwenjian
import org.apache.log4j.{Level,Logger}
import org.apache.spark.SparkConf
import org.apache.spark.SparkContext

//json
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.fasterxml.jackson.module.scala.experimental.ScalaObjectMapper
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.DeserializationFeature

object ReadandWrite {
  def main(args: Array[String]): Unit = {
    Logger.getLogger("org.apache.spark").setLevel(Level.WARN)
    Logger.getLogger("org.eclipse.jetty.server").setLevel(Level.OFF)
//    wenben
    val conf=new SparkConf().setAppName("ReadandWrite").setMaster("local[2]")
    val sc=new SparkContext(conf)
    val data=sc.textFile("file:///root/word.txt")
    data.foreach(println)

//    json
    case class Person(name:String,lovePandas:Boolean)


  }

}
