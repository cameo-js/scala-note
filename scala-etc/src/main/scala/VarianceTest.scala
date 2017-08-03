/**
  * Created by cameo on 2017. 7. 12..
  */
trait A
trait B extends A
trait C extends B


object VarianceTest extends App{

  val aa: A = new A{}
  val bb: A = new B{}
  val cc: A = new C{}
  val dd: B = new C{}

//  val ddd: C = new B{}

  // 변성이란?
  // [불가산] 가변성, 다양성; 변동, 변화
  // differences between two or more similar things // http://www.macmillandictionary.com/dictionary/british/variance


  trait Martin[+T] // Martin 은 타입 파라미터 T 에 대해 공변적이다.

//  val martin1: Martin[A] = new Martin[A]{}
//  val martin2: Martin[A] = new Martin[B]{}
//  val martin3: Martin[A] = new Martin[C]{}
//  val martin4: Martin[B] = new Martin[C]{}
//  val martin5: Martin[C] = new Martin[B]{}

  // 공변적으로 바꿔보자
  // 반공변적으로도 바꿔보자



//  class Cell[T](init: T){
//    private[this] var current = init
//    def get = current
//    def set[T](x: T) = { current = x }
//  }


//  val c1 = new Cell[String]("old")
//  val c2: Cell[String] = c1
//  c2.set("new")
//  val s: String = c1.get
//  println(s"정답은 $s")
  // QUIZ : s 는 워가 출력 될까? old:치,마,에 new:로,타

  // QUIZ : 공변적이라고 가정( Cell[+T] )하면 아래 s는 뭐가 출력될까? 컴파일은 될까?
//  val c1 = new Cell[A]("old")
//  val c2: Cell[Any] = c1
//  c2.set(1)
//  val s: Any = c1.get
//  1



  trait Covariant[+T, +R] {
    def apply[W >: T](t: W): R
  }

  trait Contravariant[-T, -R] {
    def apply[A <: R](t: T): A
  }


  def a = new Function[B, B] {
    override def apply(v1: B): B = ???
  }

//  val a1: Function1[A, A] = a
////
//  val a2: Function1[B, B] = a
////
//  val a3: Function1[C, C] = a
////
//  val a4: Function1[C, A] = a
////
//  val a5: Function1[A, C] = a

  def b = new Covariant[B, B] {
    override def apply[W >: B](t: W): B = ???
  }

//  val b1: Covariant[A, A] = b
////
//  val b2: Covariant[B, B] = b
////
//  val b3: Covariant[C, C] = b
////
//  val b4: Covariant[C, A] = b
////
//  val b5: Covariant[A, C] = b

  def c = new Contravariant[B, B] {
    override def apply[A <: B](t: B): A = ???
  }

//  val c1: Contravariant[A, A] = c
////
//  val c2: Contravariant[B, B] = c
////
//  val c3: Contravariant[C, C] = c
////
//  val c4: Contravariant[C, A] = c
////
//  val c5: Contravariant[A, C] = c
}
