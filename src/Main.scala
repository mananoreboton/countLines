import java.io.File

import scala.io.Source

object Main extends App {

  def getFileTree(f: File): Stream[File] = f #:: Option(f.listFiles()).toStream.flatten.flatMap(getFileTree)
  def getLines(file: File) = Source.fromFile(file).getLines().toStream
    .filter(s => !s.trim().isEmpty)
    .filter(s => !s.trim().startsWith("//"))
    .filter(s => !s.trim().startsWith("/*"))
    .filter(s => !s.trim().startsWith("import "))
    .filter(s => !s.trim().startsWith("public "))
    .filter(s => !s.trim().startsWith("private "))
    .filter(s => !s.trim().startsWith("protected "))
    .count(_ => true)

  val result = getFileTree(new File("/Users/mebu/StudioProjects/mobile-app-android-newer"))
      .filter(f => !f.isDirectory)
      .filter(f => !f.getAbsolutePath.contains("libnmap"))
      .filter(f => f.getName.contains(".java"))
      .filter(f => !f.getName.contains("Binding"))
      .filter(f => !f.getName.contains("Test"))
      .filter(f => !f.getName.contains("Proxy"))
      .filter(f => f.getName.length > 7)
      .map(f => (f.getName, getLines(f)))
      .sortBy(_._2)
      .foreach(println)
      //.count(_ => true)

  println(result)
}
