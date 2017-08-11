package freemonad

import java.util.Date

import cats.{Id, Monad, ~>}
import cats.implicits._
import cats.free.Free
import cats.free.Free.liftF

import scala.collection.mutable
import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration

sealed trait KVStoreA[A]
case class Put[T](key: String, value: T) extends KVStoreA[Unit]
case class Get[T](key: String) extends KVStoreA[Option[T]]
case class Delete(key: String) extends KVStoreA[Unit]
//case class Gets[T](keys: List[String]) extends KVStoreA[List[T]]

object CatsFree {
  type KVStore[A] = Free[KVStoreA, A]

  import cats.free.Free.liftF

  def put[T](key:String, value: T): KVStore[Unit] = liftF[KVStoreA, Unit](Put(key,value))
  def get[T](key:String): KVStore[Option[T]] = liftF[KVStoreA, Option[T]](Get(key))
  def delete[T](key:String): KVStore[Unit] = liftF[KVStoreA, Unit](Delete(key))

  def update[T](key:String, f: T => T): KVStore[Unit] = for {
    g <- get[T](key)
    _ <- g.map(v=>put[T](key, f(v))).getOrElse(cats.free.Free.pure())
  } yield ()


//  def gets[T](keys:List[String]): KVStore[List[T]] = keys.map(a => get[T](a))
//    liftF[KVStoreA, List[T]](Gets(key))



  def program: KVStore[Option[Int]] = for {
    _ <- put("wild-cats",2)
    _ <- update[Int]("wild-cats", _ + 12)
    _ <- put("tame-cats",5)
    n <- get[Int]("wild-cats")
    _ <- delete("tame-cats")
  } yield n

  type ID[A] = A

  def impureCompiler: KVStoreA ~> Id =
    new (KVStoreA ~> Id) {
      println("new transformer" + new Date)
      val kvs = mutable.Map.empty[String, Any]

      override def apply[A](fa: KVStoreA[A]): Id[A] = fa match {
        case Get(key) => {
          println(s"get($key)")
          kvs.get(key)
        }
        case Put(key, value) => {
          println(s"put($key, $value)")
          kvs.put(key,value)
          ()
        }
        case Delete(key) => {
          println(s"delete($key)")
          kvs.remove(key)
          ()
        }
      }
    }

  def impureCompiler1: KVStoreA ~> Future =
    new (KVStoreA ~> Future) {
      println("new transformer" + new Date)
      val kvs = mutable.Map.empty[String, Any]

      override def apply[A](fa: KVStoreA[A]): Future[A] = fa match {
        case Get(key) => {
          println(s"get($key)")
          Future(kvs.get(key))
        }
        case Put(key, value) => {
          println(s"put($key, $value)")
          kvs.put(key,value)
          Future(())
        }
        case Delete(key) => {
          println(s"delete($key)")
          kvs.remove(key)
          Future(())
        }
      }
    }

  def impureCompiler2[M[_] : Monad]: KVStoreA ~> M =
    new (KVStoreA ~> M) {
      println("new transformer" + new Date)
      val kvs = mutable.Map.empty[String, Any]

      override def apply[A](fa: KVStoreA[A]): M[A] = fa match {
        case Get(key) => {
          println(s"get($key)")
          Monad[M].pure(kvs.get(key).map(_.asInstanceOf[A]))
        }
        case Put(key, value) => {
          println(s"put($key, $value)")
          kvs.put(key,value)
          Monad[M].pure(())
        }
        case Delete(key) => {
          println(s"delete($key)")
          kvs.remove(key)
          Monad[M].pure(())
        }
      }
    }

  def main(args: Array[String]): Unit = {
//    println(program.foldMap(impureCompiler))
    val b: Future[Option[Int]] = program.foldMap(impureCompiler2[Future])
    val a = Await.result(b, Duration.Inf)
    println(a)
  }
}
