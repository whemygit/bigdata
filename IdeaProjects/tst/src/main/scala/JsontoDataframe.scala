/**
  * Created by root on 17-7-31.
  */

import org.apache.spark.sql.SparkSession
import org.apache.log4j.{Level,Logger}

object JsontoDataframe {
  def main(args: Array[String]): Unit = {
    Logger.getLogger("org.apache.spark").setLevel(Level.WARN)
    Logger.getLogger("org.eclipse.jetty.server").setLevel(Level.OFF)
    val spark=SparkSession.builder().master("local").getOrCreate()
    import spark.implicits._
    val df=spark.read.json("file:///usr/local/spark/examples/src/main/resources/people.json")
    df.show()
    df.printSchema()
    df.select(df("name"),df("age")+1).show()
    df.filter(df("age")>20).show()
    df.groupBy("age").count().show()
    df.sort(df("age").desc).show()
    df.sort(df("age").desc,df("name").asc).show()
    df.select(df("name").as("username"),df("age")).show()
  }
}
