/**
  * Created by root on 17-8-3.
  */
object ScalaPrograming {
  def main(args: Array[String]): Unit = {
//    var capital=Map("US"->"Washington","France"->"Paris")
//    capital +=("Japan"->"Tokyo")
//    println(capital("France"))

    def factorial(x:BigInt):BigInt=if (x==0) 1 else x*factorial(x-1)
    println(factorial(30))
  }

}
