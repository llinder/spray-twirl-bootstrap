package com.example

import akka.actor.Actor
import spray.routing._
import directives.LogEntry
import spray.http._
import MediaTypes._
import akka.event.Logging
import java.io.File
import shapeless._


// we don't implement our route structure directly in the service actor because
// we want to be able to test it independently, without having to spin up an actor
class MyServiceActor extends Actor with MyService {

  // the HttpService trait defines only one abstract member, which
  // connects the services environment to the enclosing actor or test
  def actorRefFactory = context

  // this actor only runs our route, but you could add
  // other things here, like request stream processing
  // or timeout handling
  def receive = runRoute(myRoute)
}


// this trait defines our service behavior independently from the service actor
trait MyService extends HttpService with StaticFilesDirective {

  def showPath(req: HttpRequest) = LogEntry("Method = %s, Path = %s" format(req.method, req.uri), Logging.InfoLevel)

  val myRoute =
    logRequest(showPath _) {
      get {
        getFromResource("bootstrap") ~
        path("") {
          respondWithMediaType(`text/html`) {
            // XML is marshalled to `text/xml` by default, so we simply override here
            complete {
              <html>
                <body>
                  <h1>Say hello to
                    <i>spray-routing</i>
                    on
                    <i>spray-can</i>
                    !</h1>
                </body>
              </html>
            }
          }
        } ~
          path("index") {
            respondWithMediaType(`text/html`) {
              complete(html.index().toString)
            }
          } ~
          path("file") {
            getFromResource("application.conf")
          }
      }
    }
}

trait StaticFilesDirective {
  import StaticFilesDirective._

  def withExtensions(extensions: List[String]) = (f: File) => extensions.contains(f.ext)

//  val resourceDirectory = new File(".").find("bootstrap")
//  println("ResourceDir: "+resourceDirectory)

//  val staticFiles = resourceDirectory.allFiles(withExtensions(List("html", "css", "png","js")))
//  println("Static files:")
//  println(staticFiles.mkString("\n"))
//
//  import spray.routing.directives.BasicDirectives._
//
//  def filename: Directive[String :: HNil] = extract(_.request.uri)
//
//  def staticFile: Directive0 = filename.require(staticFiles.contains(_))
}

object StaticFilesDirective {

  implicit class Dir(val file: File) extends AnyVal {
    def files: List[File] = if (file.isFile) List(file) else file.listFiles.toList

    def allFiles(includeDirectories: Boolean): List[File] = {
      def allFilesAcc(acc: List[File], f: File): List[File] = {
        if (f.isFile) acc :+ f
        else {
          var acc2 = acc
          for {
            subfile <- f.files
          } yield acc2 = allFilesAcc(acc2, subfile)
          acc2
        }
      }
      allFilesAcc(List(), file)
    }

    def allFiles(p: File => Boolean, includeDirectories: Boolean = false): List[File] = allFiles(includeDirectories).filter(p)

    def find(fn: String): File = allFiles(_.getName == fn).headOption match {
      case Some(file) => file
      case None => throw new RuntimeException("Cannot find %s file" format fn)
    }
  }

  implicit class FileOps(val file: File) extends AnyVal {
    def startsWith(prefix: String): Boolean = file.getName.startsWith(prefix)

    def endsWith(suffix: String): Boolean = file.getName.endsWith(suffix)

    def ext: String = file.getName.split('.').last
  }

}
