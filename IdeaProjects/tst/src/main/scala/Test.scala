/**
  * Created by root on 17-7-14.
  */
import java.io._


class Point(xc: Int, yc: Int) {
  var x: Int = xc
  var y: Int = yc

  def move(dx: Int, dy: Int) {
    x = x + dx
    y = y + dy
    println ("x 的坐标点: " + x);
    println ("y 的坐标点: " + y);
  }
}

object Test {
  def main(args: Array[String]): Unit = {
    val pt=new Point(10,20)

    pt.move(10,10)
  }
}
