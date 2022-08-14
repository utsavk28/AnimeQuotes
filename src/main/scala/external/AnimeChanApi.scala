package external

import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpRequest, HttpResponse}
import akka.http.scaladsl.unmarshalling.Unmarshal
import model._
import util.JsonSupport

import scala.concurrent.duration._
import scala.concurrent.{Await, Future}

object AnimeChanApi extends API with JsonSupport {
  override val endpoint = "https://animechan.vercel.app/api"

  def getRandomAnimeQuote(): AnimeQuote = {
    val responseFuture: Future[HttpResponse] = Http().singleRequest(HttpRequest(uri = s"$endpoint/random"))
    val ftRes = Await.result(responseFuture, 2.second)
    val resFuture = Unmarshal(ftRes.entity).to[AnimeQuote]
    val animeQuote = Await.result(resFuture, 2.second)
    animeQuote
  }

  def getAllAnime(): Anime = {
    val responseFuture: Future[HttpResponse] = Http().singleRequest(HttpRequest(uri = s"$endpoint/available/anime"))
    val ftRes = Await.result(responseFuture, 2.second)
    val resFuture = Unmarshal(ftRes.entity).to[Anime]
    val anime = Await.result(resFuture, 2.second)
    println(anime)
    anime
  }

  def getAnimeQuoteByAnimeTitle(animeTitle: String): AnimeQuote = {
    val responseFuture: Future[HttpResponse] = Http().singleRequest(HttpRequest(uri = s"$endpoint/quotes/anime?title=$animeTitle"))
    val ftRes = Await.result(responseFuture, 2.second)
    val resFuture = Unmarshal(ftRes.entity).to[AnimeQuote]
    val animeQuote = Await.result(resFuture, 2.second)
    println(animeQuote)
    animeQuote
  }

  def getAnimeQuoteByCharacterName(characterName: String): AnimeQuote = {
    val responseFuture: Future[HttpResponse] = Http().singleRequest(HttpRequest(uri = s"$endpoint/quotes/character?name=$characterName"))
    val ftRes = Await.result(responseFuture, 2.second)
    val resFuture = Unmarshal(ftRes.entity).to[AnimeQuote]
    val animeQuote = Await.result(resFuture, 2.second)
    println(animeQuote)
    animeQuote
  }

}
