import org.scalatest.FunSuite

/**
  * Created by cameo on 2017. 7. 9..
  */
class AdHocPolymorphismTest extends FunSuite {

  trait AppendableForImplicitConversion[A] {
    def append(a: A): A
  }
  class IntAppendableForImplicitConversion(i: Int) extends AppendableForImplicitConversion[Int]{
    override def append(a: Int): Int = i + a
  }
  class StringAppendableForImplicitConversion(s: String) extends AppendableForImplicitConversion[String]{
    override def append(a: String): String = s.concat(a)
  }

  implicit def intToAppendable(i: Int) = new IntAppendableForImplicitConversion(i)
  implicit def stringToAppendable(s: String) = new StringAppendableForImplicitConversion(s)

  def appendItemForImplicitConversion[A](a: A, b: A)(implicit ev: A => AppendableForImplicitConversion[A]) = a append b

  test("ad-hoc polyporphism for implicit conversion"){

    val res1 = appendItemForImplicitConversion(1,2)
    val res2 = appendItemForImplicitConversion("1","2")

    println(res1)
    println(res2)
  }


  trait AppendableForTypeClass[A] {
    def append(a: A, b: A): A
  }
  object AppendableForTypeClass {

    implicit val appendableInt = new AppendableForTypeClass[Int] {
      override def append(a: Int, b: Int) = a + b
    }

    implicit val appendableString = new AppendableForTypeClass[String] {
      override def append(a: String, b: String): String = a.concat(b)
    }

  }

  def appendItemForTypeClass[A](a: A, b: A)(implicit ev: AppendableForTypeClass[A]) = ev.append(a, b)

  test("ad-hoc polyporphism for type class"){
    val res1 = appendItemForTypeClass(1, 2)
    val res2 = appendItemForTypeClass("1", "2")

    println(res1)
    println(res2)
  }

  def appendItemForViewBound[A <% AppendableForImplicitConversion[A]](a: A, b: A) = a append b

  test("test view bound"){
    val res1 = appendItemForViewBound(1,2)
    val res2 = appendItemForViewBound("1","2")

    println(res1)
    println(res2)
  }

  def appendItemForContextBound[A : AppendableForTypeClass](a: A, b: A) = implicitly[AppendableForTypeClass[A]].append(a, b)

  test("test context bound"){
    val res1 = appendItemForContextBound(1,2)
    val res2 = appendItemForContextBound("1","2")

    println(res1)
    println(res2)
  }
}
