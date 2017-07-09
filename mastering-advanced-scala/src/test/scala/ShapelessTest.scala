import org.scalatest.FunSuite

/**
  * Created by cameo on 2017. 6. 23..
  */
//trait Converter[A] {
//  type B
//  def convert(a: A): B
//}
//
//implicit val intConverter = new Converter[Int] {
//  override type B = String
//  override def convert(a: Int): B = (a * 1000).toString
//}
//
//// reutrn 타입은 Converter에 의존성이 있는데 외부로 노출할 필요가 있는가?
//def foo[A](a: A)(implicit cv: Converter[A]): cv.B = cv.convert(a)

trait Converter[A] {
  type B
  def convert(a: A): B
}
object Converter{
  type Aux[A0, B0] = Converter[A0] { type B = B0 }
}

trait Encoder[F] {
  type T
  def encode(a: F): T
}


class ShapelessTest extends FunSuite {

  implicit val intConverter = new Converter[Int] {
    type B = String
    override def convert(a: Int): String = (a * 1000).toString
  }

  implicit val stringEncoder = new Encoder[String] {
    type T = Double
    override def encode(a: String): Double = a.toDouble
  }

  //      // 안됨요
  def foo[A,B](a: A)(implicit cv: Converter.Aux[A,B],enc: Encoder[B]): enc.T = enc.encode(cv.convert(a))
  //
  val a: Double = foo[Int,String](10)
  //      println(a)

  test("test"){
    println(a)
  }
}
