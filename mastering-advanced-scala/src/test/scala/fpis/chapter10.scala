package fpis

import org.scalatest.FunSuite

/**
  * Created by cameo on 2017. 7. 26..
  */
class chapter10 extends FunSuite{
/**
  * 10장 상속과 구성
  *
  * 구성 : 어떤 클래스가 다른 클래스의 *참조*를 갖는 것
  * 상속 : 슈퍼클래스/서브클래스 *관계*를 이룬다
  */


  abstract class Element { // abstract 추상클래스
    def contents: Array[String] // 구현이 없다.
  def demo(): Unit = println("element's demo")



    // 정의(definition)? 선언(declaration)?

    def height: Int = contents.length
    def width: Int = if (height == 0) 0 else contents(0).length
    // 파라미터 목록이 없음
    // def height()도 사용 가능하지만 왜 ()를 생략하는가?
    // 필드나 메소드 중 어느방식이든 클라이언트 코드는 영향 X (단일 접근 원칙)
    // 스칼라에서 필드와 메소드의 각각의 의미와 차이? 개선?
    import Element.elem

    def above(that: Element): Element = elem(this.contents ++ that.contents)
    def beside(that: Element): Element = elem(
      for (
        (line1, line2) <- this.contents zip that.contents
      ) yield line1 + line2
    )

    override def toString = contents mkString "\n"
  }

//   val element = new Element {
//     override def contents: Array[String] = ???
//   } // 컴파일?

  test("괄호"){
    def a() = println("a")
    def b = println("b")
    val c = println("c")
    // 아래 항목중 컴파일이 되는것은?
    a() // 1
    a   // 2
//    b() // 3
    b   // 4
    c   // 5
//    c() // 6
    // 출력결과는?

    // 인자를 받지 않고 부수 효과도 없는(리턴 타입이 Unit 이 아닌) 메소드는
    // 괄호를 아예 붙이지 않는 파라미터 없는 메소드로 정의할 것을 권장
  }


  // Element 를 상속한 서브클래스에서 추상 메서드인 contents를 구현
  // extends 절은 두가지 효과를 가진다. 두가지 효과란?
  class ArrayElement_(conts: Array[String]) extends Element {
    override val contents: Array[String] = conts // 메소드와 필드 오버라이드

    // 자바의 네임스페이스 : 필드, 메소드, 타입, 패키지
    // 스칼라의 네임스페이스 : 값(필드, 메소드, 패키지, 싱글톤 객체), 타입(클래스와 트레이트 이름)
  }
  // code smell? 어디가 중복이고? 어떻게 중복제거를 할까?



  // 파라미터 필드
  private class ArrayElement(
    val contents: Array[String]
  ) extends Element {
    override def demo: Unit = println("array's demo")
  }


  // 슈퍼클래스 생성자 호출
  private class LineElement(s: String) extends Element {
    override def height: Int = s.length
    override def width: Int = 1

    override def contents: Array[String] = Array(s)
  }


  private class UniformElement(
    ch: Char,
    override val width: Int,
    override val height: Int
  ) extends Element {
    private val line = ch.toString * width
    def contents: Array[String] = Array.fill(height)(line)
  }

  // 다형성
  val e1: Element = new ArrayElement(Array("hello", "world"))
  val ae: Element = new LineElement("hello")
  val e2: Element = ae
  val e3: Element = new UniformElement('x', 2, 3)


  test("invoke"){
    // 동적 바인딩
    // demo 를 추가해보자!
    def invokeDemo(e: Element): Unit = {
      e.demo()
    }
    invokeDemo(new ArrayElement(Array("")))
    invokeDemo(new LineElement(""))
    invokeDemo(new UniformElement('x', 2, 3))
  }

  // final도 있다.

  // 상속과 구성 사용
  // is-a 관계
  // ArrayElement 는 Element 이다. : 자연스러운가?
  // LineElement 는 Element 이다. : 자연스러운가?


  // above, beside, toString 구현


  // 팩토리 객체 정의
  object Element {
    def elem(contents: Array[String]): Element = new ArrayElement(contents)
    def elem(chr: Char, width: Int, height: Int): Element = new UniformElement(chr, width, height)
    def elem(contents: String): Element = new LineElement(contents)
  }
  // 팩토리 메소드 활용!

  // 각 클래스들 비공개로!

  // 높이와 너비 조절

  test("main"){
//    val space = elem(" ")
//    val corner = elem("+")
//    def spiral(nEdges: Int, direction: Int): Element = {
//      if (nEdges == 1)
//        elem("+")
//      else {
//        val sp = spiral(nEdges - 1, (direction + 3) % 4)
//        def verticalBar = elem('|', 1, sp.height)
//        def horizontalBar = elem('-', sp.width, 1)
//        if (direction == 0)
//          (corner beside horizontalBar) above (sp beside space)
//        else if (direction == 1)
//          (sp above space) beside (corner above verticalBar)
//        else if (direction == 2)
//          (space beside sp) above (horizontalBar beside corner)
//        else
//          (verticalBar above corner) beside (space above sp)
//      }
//    }
  }

}
