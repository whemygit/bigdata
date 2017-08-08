/**
  * Created by root on 17-7-17.
  */
import org.apache.spark.{SparkConf,SparkContext}
import org.apache.spark.mllib.classification.{NaiveBayes,NaiveBayesModel}
import org.apache.spark.mllib.util.MLUtils
import org.apache.log4j.{Level,Logger}

object NaiveBayesExample {
  def main(args: Array[String]): Unit = {
    Logger.getLogger("org.apache.spark").setLevel(Level.WARN)
    Logger.getLogger("org.eclipse.jetty.server").setLevel(Level.OFF)
    val conf=new SparkConf().setMaster("local").setAppName("NaiveBayesExample")
    val sc=new SparkContext(conf)
//    val inputFile =  "/usr/local/spark/data/mllib/sample_libsvm_data.txt"
//    val conf = new SparkConf().setAppName("NaiveBayesExample").setMaster("local[2]")
//    val sc = new SparkContext(conf)

    val data=MLUtils.loadLibSVMFile(sc,"data/mllib/sample_libsvm_data.txt")
//    val data=sc.textFile("file://root/IdeaProjects/data/mllib/sample_libsvm_data.txt")
    data.take(1).foreach(println)

    val Array(training,test)=data.randomSplit(Array(0.6,0.4))
    training.take(1).foreach(println)
    val model=NaiveBayes.train(training,lambda = 1.0,modelType = "multinomial")
    val predictionAndLabel=test.map(p=>(model.predict(p.features),p.label))
    val accuracy=1.0*predictionAndLabel.filter(x=>x._1==x._2).count()/test.count()
    println("1: "+accuracy)
//    println(data.first())
//    println(data.first().features)

    model.save(sc,"target/tmp/myNaiveBayesModel")
//    println("a: "+model)
    val sameModel=NaiveBayesModel.load(sc,"target/tmp/myNaiveBayesModel")
//    println("b: "+sameModel)
    val predictionAndLabel1=test.map(p=>(sameModel.predict(p.features),p.label))
    val accuracy1=1.0*predictionAndLabel1.filter(x=>x._1==x._2).count()/test.count()
    println("2:"+accuracy1)
  }
}
