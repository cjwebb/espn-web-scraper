package com.github.cjwebb.espnwebscraper

case class MatchStats(homeTeam: String,
                      awayTeam: String,
                      date: String,
                      homeTeamScore: Int,
                      awayTeamScore: Int)

case class TeamStats(shots: Int,
                     shotsOnGoal: Int,
                     fouls: Int,
                     cornerKicks: Int,
                     offsides: Int,
                     possessionPercentage: Int,
                     yellowCards: Int,
                     redCards: Int,
                     saves: Int
                     )

case class Player(shirtNumber: Int, name: String)

case class Substitutions(playerOff: String, playerOn: String, time: Int)

case class YellowCards(name: String, time: Int)

case class RedCards(name: String, time: Int)
