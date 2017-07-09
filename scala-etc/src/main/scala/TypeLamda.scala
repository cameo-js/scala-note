import scala.language.higherKinds
/**
  * Created by cameo on 2017. 6. 19..
  */
trait Functor[A, F[_]] {
  def map[B](x: F[A])(f: A => B): F[B]
}

object TypeLamdaMain extends App {

  implicit def OptionIsFunctor[A]: Functor[A, Option] = new Functor[A, Option] {
    override def map[B](x: Option[A])(f: (A) => B): Option[B] = x map f
  }

  def Tuple2FunctorTest[X, A](x: Tuple2[X, A]) = {
    type Alias[A] = Tuple2[X, A]
    new Functor[A, Alias] {
      override def map[B](x: Alias[A])(f: A => B): Alias[B] = (x._1, f(x._2))
    }
  }

//  implicit def Tuple2IsFunctor[X, A]: Functor[A, ({ type λ[A] = Tuple2[X, A]})#λ] =
//    new Functor[A, ({ type Alias[A] = Tuple2[X, A]})#Alias] {
//      override def map[B](x: (X, A))(f: (A) => B): (X, B) = (x._1, f(x._2))
//    }

  implicit def Tuple2IsFunctor[X, A]: Functor[A, Tuple2[X, ?]] =
    new Functor[A, Tuple2[X, ?]] {
      override def map[B](x: (X, A))(f: (A) => B): (X, B) = (x._1, f(x._2))
    }

  val a: (String, Int) = implicitly[Functor[Int, ({ type Alias[A] = Tuple2[String, A]})#Alias]].map(("a", 1))(_ + 1)

  println(a)

  implicitly[Functor[Int, Option]].map(Option(5))(_ + 1)
}

