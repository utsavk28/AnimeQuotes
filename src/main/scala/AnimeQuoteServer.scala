import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives.*
import akka.stream.ActorMaterializer
import external.AnimeChanApi
import handler.ServerHandler
import model.AnimeQuote
import util.JsonSupport

import scala.concurrent.*
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}

object AnimeQuoteServer extends App with JsonSupport {
  implicit val system: ActorSystem = ActorSystem("AnimeQuoteWebServer")
  implicit val executor: ActorMaterializer.type = ActorMaterializer
  implicit val dispatcher: ExecutionContextExecutor = system.dispatcher
  val host = "localhost"
  val port = 9000

  val animeRoutes = pathPrefix("anime") {
    get {
      path("random") {
        complete(ServerHandler.getRandomAnimeQuotes())
      }
    }
  }
  val favoriteRoute = pathPrefix("favorite") {
    concat(
      get {
        path("all") {
          complete(ServerHandler.getFavoriteAnimeQuotes())
        }
      },
      post {
        path("add") {
          entity(as[AnimeQuote]) { animeQuote =>
            complete(ServerHandler.addAnimeQuotesToFavorite(animeQuote))
          }
        }
      },
      delete {
        path("delete") {
          entity(as[AnimeQuote]) { animeQuote =>
            complete(ServerHandler.deleteAnimeQuotesToFavorite(animeQuote))
          }
        }
      }
    )
  }

  val route = pathPrefix("api") {
    concat(animeRoutes, favoriteRoute)
  }


  def main(): Unit = {
    val binding = Http().newServerAt(host, port).bindFlow(route)
    binding.onComplete {
      case Success(_) =>
        println(s"Server is listening on http://$host:$port")
      case Failure(exception) =>
        println(s"Failure : $exception")
        system.terminate()
    }
  }
}
