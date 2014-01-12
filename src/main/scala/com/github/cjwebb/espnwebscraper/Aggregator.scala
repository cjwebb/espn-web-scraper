package com.github.cjwebb.espnwebscraper

import akka.actor.Actor

/**
 * Actor that receives the results of scraping.
 * Just adds all the things to a list, so shouldn't do anything risky.
 */
class Aggregator extends Actor {
  def receive = ???
}
