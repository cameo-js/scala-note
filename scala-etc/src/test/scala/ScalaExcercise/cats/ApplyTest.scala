package ScalaExcercise.cats

import cats._
import cats.implicits._
import org.scalatest.FunSuite

/**
  * Created by cameo on 2017. 7. 7..
  */
class ApplyTest extends FunSuite{
  val intToString: Int ⇒ String = _.toString
  val double: Int ⇒ Int = _ * 2
  val addTwo: Int ⇒ Int = _ + 2
  val addArity2 = (a: Int, b: Int) ⇒ a + b
  val addArity3 = (a: Int, b: Int, c: Int) ⇒ a + b + c

  test("map"){
    println(Apply[Option].map(Some(1))(intToString))
    println(Apply[Option].map(Some(1))(double))
    println(Apply[Option].map(None)(addTwo))
  }
  test("compose"){
    val listOpt = Apply[List] compose Apply[Option]
    val plusOne = (x: Int) ⇒ x + 1
    println(listOpt.ap(List(Some(plusOne)))(List(Some(1), None, Some(3))))
  }
  test("|@|"){
    val option2 = Option(1) |@| Option(2)
    println(option2.apWith(Some(addArity2)))
    println(option2.map(addArity2))
  }
}
