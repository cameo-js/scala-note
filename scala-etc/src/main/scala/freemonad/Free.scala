package freemonad

object MyFree {

  sealed trait Interact[A]
  case class Ask(prompt: String) extends Interact[String]
  case class Tell(msg: String) extends Interact[Unit]

  trait Monad[M[_]] {
    def pure[A](a: A): M[A]
    def flatMap[A,B](fa: M[A])(f: A => M[B]): M[B]
  }
  object Monad {
    def apply[A[_]: Monad]:Monad[A] = implicitly(Monad[A])
  }

//  Monad[Option]()

  sealed trait ~>[F[_], G[_]]{
    def apply[A](a: F[A]): G[A]
  }

  sealed trait Free[F[_],A]{
    def flatMap[C](g: A => Free[F, C]): Free[F, C]
  }

  case class Return[F[_], A](a: A) extends Free[F, A] {
    def flatMap[C](g: (A) => Free[F, C]): Free[F, C] = g(a)
  }

  case class Bind[F[_], A, B](fa: F[A], f: A => Free[F, B]) extends Free[F, B] {
    def flatMap[C](g: B => Free[F, C]): Free[F, C] = Bind[F, A, C](fa, f(_).flatMap(g))
  }
}
