package fpis

import org.scalatest.WordSpec

/**
  * Created by cameo on 2017. 7. 5..
  */
class chapter7 extends WordSpec{
  "chapter7" should {
    "if expression" in {
      //전통적인
      val status = true
      var value = ""
      if (status) value = "true" else value = "false"

      val filename = if (true) "true"
    }
  }
}
