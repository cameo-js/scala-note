package fpis

abstract class Fruit (
  val name: String,
  val color: String
)
object Fruits {
  object Apple extends Fruit("apple","red")
  object Orange extends Fruit("orange","orange")
  object Pear extends Fruit("pear","yellowish")
  val menu = List(Apple, Orange, Pear)
}

object Main extends App {
//  import fpis.Fruit
//  import fpis._
//  import fpis.Fruits._
//  import fpis.Fruits.{Pear, Apple}
//  import fpis.Fruits.{Apple => McIntosh}
//  import fpis.Fruits.{Apple => McIntosh, _}
//  import fpis.Fruits.{Pear => _, _}


  def showFruit(fruit: Fruit) = {
    import fruit._
    println(name + "s are" + color)
  }

  import java.util.regex
  val pat = regex.Pattern.compile("a*b")


}