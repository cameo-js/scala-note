package week5

/**
  * Created by cameo on 2017. 7. 19..
  */
object Msort extends App {

  def msort[T](list: List[T])(implicit ord: Ordering[T]): List[T] = {
    val x = list.length / 2
    if (x == 0) list
    else {
      def merge(xs: List[T], ys: List[T]): List[T] = (xs, ys) match {
        case (Nil, ys) => ys
        case (xs, Nil) => xs
        case (x :: xs1, y :: ys1) =>
          if (ord.lt(x, y)) x :: merge(xs1, ys)
          else y :: merge(xs, ys1)
      }
      val (xs, ys) = list splitAt x
      merge(msort(xs), msort(ys))
    }
  }

  println(msort(List(1,-3,6,3,7)))

}
