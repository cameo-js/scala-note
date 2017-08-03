import java.io.File

/**
  * Created by cameo on 2017. 7. 13..
  */
object FileMatcher {
  private def filesHere = (new File(".")).listFiles

  def fileEnding(query: String): Array[File] =
    for (file <- filesHere; if file.getName.endsWith(query))
      yield file
}

object Main extends App {
  FileMatcher.fileEnding("9").foreach(a => println(a.getName))
}
