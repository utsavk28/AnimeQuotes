package model

case class Anime(anime:Array[String])
case class AnimeQuote(anime: String, character: String, quote: String)
case class AnimeQuotes(data:Array[AnimeQuote])

