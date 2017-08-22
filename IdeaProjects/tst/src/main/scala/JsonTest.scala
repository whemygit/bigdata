/**
  * Created by root on 17-7-31.
  */
import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.SparkConf
import scala.util.parsing.json.JSON
import org.apache.log4j.{Level,Logger}

object JsonTest {
  def main(args: Array[String]): Unit = {
    Logger.getLogger("org.apache.spark").setLevel(Level.WARN)
    Logger.getLogger("org.eclipse.jetty.server").setLevel(Level.OFF)
    val inputFile =  "file:///usr/local/spark/examples/src/main/resources/people.json"
    val conf=new SparkConf().setAppName("JsonTestApp").setMaster("local[2]")
    val sc=new SparkContext(conf)
    val jsonStrs=sc.textFile(inputFile)
    jsonStrs.foreach(println)
    val result=jsonStrs.map(s=>JSON.parseFull(s))
    result.foreach({r=>r match {
      case Some(map: Map[string,Any])=>println(map)
      case None=>println("Parsing failed")
      case other=>println("Unknown data structure: "+ other)
    }})
    result.foreach(println)

  }

}
