package fpis

import java.io.{File, PrintWriter}
import java.util.Date

import org.scalatest.FunSuite

/**
  * Created by cameo on 2017. 7. 13..
  */
class chapter9 extends FunSuite {
  // 흐름제어 추상화

  // 9.1 코드 중복 줄이기

//  // 요구사항#1 ".scala"로 끝나는 파일들 찾아주세요.
//  test("filesEnding"){
//    println("======")
//    FileMatcher.fileEnding("scala").foreach(a => println(a.getName))
//    println("======")
//  }
//
//  // 요구사항#2 "hoc"이 포함된 파일들 찾아주세요.
//  test("filesContaining"){
//    println("======")
//    FileMatcher.fileContaining("hoc").foreach(a => println(a.getName))
//    println("======")
//  }
//
//  // 요구사항#3 ".scala"로 끝나는 파일중에 Type 으로 시작하는 파일들 찾아주세요.
//  test("filesRegex"){
//    println("======")
//    FileMatcher.fileRegex("^A").foreach(a => println(a.getName))
//    println("======")
//  }


  object FileMatcher {
    private def filesHere = (new File("./scala-etc/src/main/scala/")).listFiles


    // 요구사항#1 ".scala"로 끝나는 파일들 찾아주세요.
    def fileEnding(query: String): Array[File] =
      for (file <- filesHere; if file.getName.endsWith(query))
        yield file


    // 요구사항#2 "hoc"이 포함된 파일들 찾아주세요.
    def fileContaining(query: String): Array[File] =
      for (file <- filesHere; if file.getName.contains(query))
        yield file


    // 요구사항#3 ".scala"로 끝나는 파일중에 Type 으로 시작하는 파일들 찾아주세요.
    def fileRegex(query: String): Array[File] =
      for (file <- filesHere; if file.getName.matches(query))
        yield file




    // QUIZ: 반복되는 부분과 반복되지 않는 부분은??




    // endsWith
    // contains
    // matches

    // file.getName.matches(query)
    // file.getName.{method}(query) X
    // string.method(string)
    // (string, string) => boolean







    def filesMatching(matcher: (String) => Boolean) = {
      for (file <- filesHere; if matcher(file.getName))
        yield file
    }






//    def fileEnding2(query: String) = filesMatching(
//      query, (filename, query) => filename.endsWith(query))
//
//    def fileContains2(query: String) = filesMatching(_.contains(query))
//
//
//    def fileRegex2(query: String) = filesMatching(query,
//      (filename, query) => filename.matches(query))




    // 여전히 코드 중복
    // QUIZ: query 를 클로저를 사용하여 중복제거
    // QUIZ: wildcard 를 활용


  }



  // 9.2 클라이언트 코드 단순하게 만들기
  val list = List(1,3,5,7)








  // QUIZ 짝수를 포함하는지 찾는 메소드를 구현하라
  def containsEven(nums: List[Int]): Boolean = nums.exists(_%2==0)


  // 9.3 커링
  test("currying"){
    def plainOldSum(x: Int, y: Int) = x + y
          val a = plainOldSum(1, 2)
          val b: Int => Int = plainOldSum(1, _)



    def curriedSum(x: Int)(y: Int) = x + y
          val d = curriedSum(1)(2)
          val e: (Int) => Int = curriedSum(1) // Int => Int
          val f: (Int) => Int = curriedSum(1)_
    // QUIZ: _ 를 활용해서 plainOldSum을 curriedSum 처럼 동작하게 만드시오



  }


  // 9.4 새로운 제어구조 작성
  test("제어구조"){

    def withPrintWriter(file: File, op: PrintWriter => Unit) = {
      val writer = new PrintWriter(file)
      try {
        op(writer) // xxxx 패턴
      } finally {
        writer.close()
      }
    }

    val file = new File("date.txt")
    withPrintWriter(file,writer => writer.println(new Date))



    println (
      "내장제어구문1"
    )

    println {
      "내장제어구문2"
    }

    // 어느것이 더 내장 제어 구조처럼 보이나?



    "Hello, world!".substring(7,9)
//    "Hello, world!".substring{7,9} //컴파일?



    // {} 를 사용하는건 파라미터가 1개인것만
    // QUIZ : withPrintWriter 를 {}를 사용하도록 하려면?


    def withPrintWriter2(file: File)(op: PrintWriter => Unit) = {
      val writer = new PrintWriter(file)
      try {
        op(writer)
      } finally {
        writer.close()
      }
    }

    withPrintWriter2(file){ writer =>
      writer.println(new Date)
    }
  }

  test("test"){
    def hello(): Int = {
      println("hello")
      1
    }

    def hi(s : Int): Int = {
      1
    }

    def hi2(s : => Int): Int = {
      1
    }

    hi(hello())
    //hello
    //res0: Int = 1

    println("====")

    hi2(hello()) //res1: Int = 1
  }

  // 9.5 이름에 의한 호출 파라미터
  // 관련 유명 문서 참고
  // https://jiyeonseo.gitbooks.io/daily/content/scala/thunk.html
}
