package com.github.cjwebb.espnwebscraper

import akka.actor.{Props, Actor, ActorSystem}
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import scala.collection.JavaConverters._
import java.util.regex.Pattern
import scala.util.matching.Regex
import org.joda.time.DateTime

object ESPNWebScraper extends App {
  val base = "http://espnfc.com/results/_/league/eng.1/english-premier-league?cc=5739"

  val actorSystem = ActorSystem("EspnWebScraper")
  val scraper = actorSystem.actorOf(Props[Scraper])

  Thread.sleep(100)

  val result = scraper ! FixturesURL(base)


  //val thing = scraper ! MatchReportURL("http://espnfc.com/uk/en/report/367398/report.html?soccernet=true&cc=5739")

  Thread.sleep(1000)
  actorSystem.shutdown()
}

case class FixturesURL(href: String)
case class MatchReportURL(href: String) // not used yet

class Scraper extends Actor {

  val NumberRegex = """(\d+)""".r

  def receive = {
    case FixturesURL(href) => {
      val doc = getDocument(href)
      println(getReportURLs(doc))
    }
    case MatchReportURL(href) => {
      val doc = getDocument(href)
      getMatchStats(doc)
    }
  }

  def getDocument(href: String) =
    Jsoup.connect(href).userAgent("com.github.cjwebb").timeout(3000).get()

  def getReportURLs(document: Document): List[MatchReportURL] = {
    val elements = document.getElementById("my-teams-table").getElementsByClass("mod-content")
    val links = elements.select("td[align=center] a[href]").iterator().asScala.map(_.attr("href")).toList

    links map (MatchReportURL(_))
  }

  def getMatchStats(document: Document) = {
    def getScore = {
      val scoreTime = document.getElementsByClass("score-time").select(".score").text()
      val scoreList = scoreTime.trim.split('-').map(_.trim)
      (scoreList(0), scoreList(1))
    }

    def getDate = {
      val scriptNumbers = document.getElementsByClass("match-details").select("script").first().html()
      NumberRegex.findFirstIn(scriptNumbers) map { dateMillis =>
        val dt = new DateTime(dateMillis.toLong)
        dt.toString("YYYY-MM-dd HH:mm:ss")
      } getOrElse(throw new IllegalArgumentException("missing date: not properly parsed"))
    }

    // for some reason ESPN list home and away team the wrong way round
    val matchUp = document.getElementsByClass("matchup")
    val awayTeam = matchUp.select(".team").select(".home")
    val homeTeam = matchUp.select(".team").select(".away")

    val score = getScore

    MatchStats(
      homeTeam.select(".team-name a").text(),
      awayTeam.select(".team-name a").text(),
      getDate,
      score._1.toInt,
      score._2.toInt
    )
  }

}
