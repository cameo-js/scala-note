// 13 패키지와 임포트

//package com.typeclass
//class Chapter13
//
//package com.typeclass{
//  class Chapter13
//}
//
//package com {
//  package typeclass {
//    class Chapter13
//  }
//}



// 관련 코드에 간결하게 접근하기
// 같은 패키지 안에 있는 코드에 접근할때 전체경로 X
package com {
  package typeclass {
    class Chapter13{
      // fpis.StarMap x
      val map = new StarMap
    }
    class StarMap
  }
  class Ship {
    // fpis.Chapter13 x
    val chapter13 = new typeclass.Chapter13
  }
  package hello {
    class World {
      // fpis.Ship x
      def addShip() { new Ship }
    }
  }
}
// 해당 파일을 컴파일 했을때 경로를 그려봅시다!

// 13.2 관련 코드에 간결하게 접근하기 bobsrockets.scala

// 13.3 임포트 Fruit.scala

// 13.4 암시적 임포트
// 스칼라는 몇 가지 임포트를 항상 추가
// import java.lang._
// import scala._
// import Predef._

// StringBuilder


// 13.5 접근 수식자
// vs JAVA


// protected ProtectedTest.java

// 보호 스코프

// 동반객체
// http://www.jesperdj.com/2016/01/08/scala-access-modifiers-and-qualifiers-in-detail/

// 13.6 패키지 객체
// package.scala
