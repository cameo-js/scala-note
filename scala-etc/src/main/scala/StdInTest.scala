object StdInTest {
  def main(args: Array[String]) {
    val inputs: Iterator[String] = io.Source.stdin.getLines
    val n = inputs.next.toInt
    val map = makeMap(inputs.take(n))
    for (ln <- inputs) println(get(map)(ln))
  }
  def makeMap(n: Iterator[String]):Map[String, String] = {
    n.foldLeft(Map[String,String]())((acc, item)=> {
      val temp = item.split(" ")
      acc ++ Map(temp(0) -> temp(1))
    })
  }
  def get(map: Map[String, String])(key: String): String = {
    if (map.isDefinedAt(key)) {
      s"${key}=${map(key)}"
    } else {
      "Not found"
    }
  }
}
