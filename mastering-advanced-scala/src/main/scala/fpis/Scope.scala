package fpis

package chapter13 {
  private [fpis] class Scope {
    protected [chapter13] def useStarChart() = {}

    class Test {
      private[Scope] val distance = 100
    }
    private[this] var speed = 200
  }
}
package launch {
  import chapter13._
  object Vehicle {
    private[launch] val guide = new Scope

  }
}
