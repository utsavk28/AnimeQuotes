package util
import akka.http.scaladsl.server.Directives
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import model._
import spray.json.*

trait JsonSupport extends SprayJsonSupport with DefaultJsonProtocol {
  implicit val animeFormat: RootJsonFormat[Anime] = jsonFormat1(Anime)
  implicit val animeQuoteFormat: RootJsonFormat[AnimeQuote] = jsonFormat3(AnimeQuote)
  implicit val animeQuotesFormat: RootJsonFormat[AnimeQuotes] = jsonFormat1(AnimeQuotes)
}

