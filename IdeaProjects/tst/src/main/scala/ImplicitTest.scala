/**
  * Created by root on 17-7-14.
  */
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.Dataset
import org.apache.spark.sql.Row
import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.DataFrameReader
import org.apache.spark.rdd.RDD
import org.apache.log4j.{Level,Logger}


object ImplicitTest {
  def main(args: Array[String]) {
//    val spark=SparkSession.builder().appName("Spark Sql basic example").getOrCreate()
    Logger.getLogger("org.apache.spark").setLevel(Level.WARN)
    Logger.getLogger("org.eclipse.jetty.server").setLevel(Level.OFF)
    val spark = SparkSession.builder.master("local").appName("Spark SQL basic example").config("spark.some.config.option", "some-value").getOrCreate()

    // For implicit conversions like converting RDDs to DataFrames
    import spark.implicits._

    val dataList: List[(Double, String, Double, Double, String, Double, Double, Double, Double)] = List(
      (0, "male", 37, 10, "no", 3, 18, 7, 4),
      (0, "female", 27, 4, "no", 4, 14, 6, 4),
      (0, "female", 32, 15, "yes", 1, 12, 1, 4),
      (0, "male", 57, 15, "yes", 5, 18, 6, 5),
      (0, "male", 22, 0.75, "no", 2, 17, 6, 3),
      (0, "female", 32, 1.5, "no", 2, 17, 5, 5),
      (0, "female", 22, 0.75, "no", 2, 12, 1, 3),
      (0, "male", 57, 15, "yes", 2, 14, 4, 4),
      (0, "female", 32, 15, "yes", 4, 16, 1, 2),
      (0, "male", 22, 1.5, "no", 4, 14, 4, 5))

    val data = dataList.toDF("affairs", "gender", "age", "yearsmarried", "children", "religiousness", "education", "occupation", "rating")
    println(data.count())



    data.show()

    data.printSchema()

    // 请务必保证jar包运行完成，退出spark，释放资源
    spark.stop

  }
}
