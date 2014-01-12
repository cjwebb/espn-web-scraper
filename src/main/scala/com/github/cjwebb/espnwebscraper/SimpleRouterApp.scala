package com.github.cjwebb.espnwebscraper

import akka.actor._
import akka.routing.FromConfig

object SimpleRouterSetup extends App {
  val system = ActorSystem("SimpleSystem")
  val simpleRouted = system.actorOf(Props[SimpleActor].withRouter(FromConfig()), name = "simpleRoutedActor")

  for (n <- 0 until 10)  simpleRouted ! Message("Hello, Akka #%d!".format(n))
}

case class Message(msg: String)

class SimpleActor extends Actor with ActorLogging {

  def receive = {
    case Message(msg) => log.info("Got a valid message: %s".format(msg))
    case default => log.error("Got a message I don't understand.")
  }
}