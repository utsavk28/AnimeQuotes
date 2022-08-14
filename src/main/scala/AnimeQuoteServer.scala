import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import handler.ServerHandler
import model.AnimeQuote
import util.JsonSupport

import scala.concurrent.ExecutionContextExecutor
import scala.util.{Failure, Success}

object AnimeQuoteServer extends App with JsonSupport {
  def run(): Unit = {
    implicit val system: ActorSystem[Nothing] = ActorSystem(Behaviors.empty, "AnimeQuoteWebServer")
    implicit val executionContext: ExecutionContextExecutor = system.executionContext
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
