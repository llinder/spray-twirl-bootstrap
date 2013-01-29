package com.example

import spray.can.server.SprayCanHttpServerApp
import akka.actor.Props

trait WebApp extends SprayCanHttpServerApp {

  // create and start our service actor
  val service = system.actorOf(Props[MyServiceActor], "my-service")

  // create a new HttpServer using our handler tell it where to bind to
  newHttpServer(service) ! Bind(interface = "localhost", port = 8080)
}

object Boot extends App with WebApp {

}