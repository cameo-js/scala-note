package ScalaExcercise.cats

import cats._
import org.scalatest.WordSpec

/**
  * Created by cameo on 2017. 7. 6..
  */
class FunctorTest extends WordSpec{
  implicit val listFunctor: Functor[List] = new Functor[List] {
    def map[A, B](fa: List[A])(f: (A) => B): List[B] = fa map f
  }
  implicit val optionFunctor: Functor[Option] = new Functor[Option] {
    def map[A, B](fa: Option[A])(f: (A) => B): Option[B] = fa map f
  }

  implicit def functionFunctor[In]: Functor[Function1[In, ?]] = new Functor[Function1[In, ?]] {
    override def map[A, B](fa: Function[In, A])(f: Function1[A, B]): Function1[In, B] = fa andThen f
  }
  "cats" should {
    "functor" in {
      val a: Seq[Int] = Functor[List].map(List(1,2,3))(_ + 1)
      println(a)

      val b = Functor[Function1[String, ?]].map(_.toDouble + 1.0)(_.toInt)
      println(b("1"))

      val optionLength: Option[String] => Option[Int] = Functor[Option].lift(_.length)
      println(optionLength(Some("hello")))

      val source = List("Cats", "is", "awesome")
      val product = Functor[List].fproduct(source)(_.length).toMap

      val listOpt = Functor[List] compose Functor[Option]
    }
  }
}
