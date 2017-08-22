/**
  * Created by root on 17-7-21.
  */
import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.log4j.{Level,Logger}

object ScalaApp {
  def main(args: Array[String]): Unit = {
    Logger.getLogger("org.apache.spark").setLevel(Level.WARN)
    Logger.getLogger("org.eclipse.jetty.server").setLevel(Level.OFF)
    val sc=new SparkContext("local[2]","First Spark App")
    val data=sc.textFile("file:///root/UserPurch.csv")
    println(data)
//    result:file://root/UserPurch.csv MapPartitionsRDD[1] at textFile at ScalaApp.scala:13
    data.cache()
    data.foreach(println)
//    result: Jill,Samsung Galaxy cover,8.95
//    Bob,ipad cover,5.49
//    John,iphone cover,9.9
//    John,Headphones,5.49
//    Jack,iphone cover,9.9
    val data1=data.map(line=>line.split(","))
//    println(data1)
//    result:MapPartitionsRDD[2] at map at ScalaApp.scala:23
//    data1.foreach(println)
//    result:[Ljava.lang.String;@392d11d0
//      [Ljava.lang.String;@38a38a23
//    [Ljava.lang.String;@378a0bf
//    [Ljava.lang.String;@29c386ed
//    [Ljava.lang.String;@48e3001f
    val data2=data1.map(purchaseRecord=>(purchaseRecord(0),purchaseRecord(1),purchaseRecord(2)))
    data2.cache()
//    println(data2)
//    result:MapPartitionsRDD[3] at map at ScalaApp.scala:32
//    data2.foreach(println)
//    result:(John,iphone cover,9.9)
//    (John,Headphones,5.49)
//    (Jack,iphone cover,9.9)
//    (Jill,Samsung Galaxy cover,8.95)
//    (Bob,ipad cover,5.49)
//    购买次数
    val numPurchases=data2.count()
    println(numPurchases)
//    多少个不同的用户购买过商品
    val uniqueUsers=data2.map{case (user,product,price)=>user}.distinct().count()
    println(uniqueUsers)
//    总收入
    val totalReven=data2.map{case (user,product,price)=>price.toDouble}.sum()
    println(totalReven)
//    最畅销
    val productsByPopularity=data2
    .map{case (user,product,price)=>(product,1)}
    .reduceByKey(_+_)
//    result:ShuffledRDD[10] at reduceByKey at ScalaApp.scala:54
    .collect()
//      result:[Lscala.Tuple2;@3fe46690
    .sortBy(-_._2)
//    println(productsByPopularity)
//    result:[Lscala.Tuple2;@496a31da
    productsByPopularity.foreach(println)
    val mostPopular=productsByPopularity(0)
    println("Most popular product: %s with %d purchases".format(mostPopular._1,mostPopular._2))
  }

}
