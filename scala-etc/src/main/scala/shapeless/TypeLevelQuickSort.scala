package shapeless

import shapeless.TypeLevelQuickSort.hlist.{HList, HNil}

/**
  * Created by cameo on 2017. 7. 14..
  */
object TypeLevelQuickSort {
  sealed trait Nat

  final class _0 extends Nat

  final class Succ[P <: Nat]() extends Nat

  type _1 = Succ[_0]
  type _2 = Succ[_1]
  type _3 = Succ[_2]
  type _4 = Succ[_3]
  type _5 = Succ[_4]


  trait Sum[A <: Nat, B <: Nat] {type Out <: Nat}


  val three = Sum[_1, _2]

  object Sum {
    type Aux[A <: Nat,B <: Nat,O <: Nat] = Sum[A, B]{type Out = O}
    def apply[A <: Nat, B <: Nat](implicit sum: Sum[A, B]): Aux[A, B, sum.Out] = sum

    implicit def zSum[A <: Nat]: Aux[_0, A, A] = new Sum[_0, A]{type Out = A}

    implicit def sum2[A <: Nat, B <: Nat](implicit sum: Sum[A, Succ[B]]): Aux[Succ[A], B, sum.Out] = new Sum[Succ[A], B]{type Out = sum.Out}

  }

  trait LT[A <: Nat, B <: Nat]

  object LT {
    def apply[A <: Nat, B <: Nat](implicit lt: LT[A, B]): LT[A, B] = lt

    implicit def zLt[A <: Nat]: LT[_0, A] = new LT[_0, A]{}

    implicit def lt2[A <: Nat, B <: Nat](implicit lt: LT[A, B]): LT[Succ[A], Succ[B]] = new LT[Succ[A], Succ[B]]{}
  }

  trait LTEq[A <: Nat, B <: Nat]

  object LTEq {
//    type <=[A <: Nat] = LTEq[A <:]
    def apply[A <: Nat, B <: Nat](implicit lteq: LTEq[A, B]): LTEq[A, B] = lteq

    implicit def zLteq[A <: Nat]: LT[_0, A] = new LT[_0, A]{}
//    implicit def zLteq: LT[_0, _0] = new LT[_0, _0]{}
    implicit def lteq2[A <: Nat, B <: Nat](implicit lt: LTEq[A, B]): LTEq[Succ[A], Succ[B]] = new LTEq[Succ[A], Succ[B]]{}
  }



  object list {
    sealed abstract class List[+A]
    case object Nil extends List[Nothing]
    // head와 tail의 내부 타입이 같아야 한다.
    final case class ::[B](head: B, tail: List[B]) extends List[B]
  }

  // type level의 list는 HList
  object hlist {
    sealed trait HList
    final class ::[+H, +T <: HList] extends HList
    final class HNil extends HList
    // type level의 자연수의 list는
    // 여기서는 hlist를 쓸수 밖에 없다.
    // 왜냐면 그냥 list를 사용하면 타입 정보가 다 날라갈것이다.
    type NS = _1 :: _0 :: _3 :: _2 :: HNil
  }

  trait LTEqs[H <: HList, A <: Nat] {
    type Out <: HList
  }
  object LTEqs {
    type Aux[H <: HList,A <: Nat,O <: HList] = LTEqs[H, A]{type Out = O}

//    def apply[A <: Nat, B <: Nat](implicit sum: Sum[A, B]): Aux[A, B, sum.Out] = sum
    def apply[H <: HList, A <: Nat](implicit lteq: LTEqs[H, A]): Aux[H, A, lteq.Out] = lteq

//    implicit def zLteqs[A <: HList]: Aux[HNil, A, HNil] = new LTEqs[] {}
  }



}
