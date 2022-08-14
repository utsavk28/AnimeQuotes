package handler

import external.AnimeChanApi
import model.AnimeQuote
import database.Database
object ServerHandler {

  def getRandomAnimeQuotes(): AnimeQuote = {
    AnimeChanApi.getRandomAnimeQuote()
  }

  def getFavoriteAnimeQuotes(): Array[AnimeQuote] = {
    Database.favoriteQuotes
  }

  def addAnimeQuotesToFavorite(animeQuote: AnimeQuote): String = {
    if (Database.favoriteQuotes.contains(animeQuote)) {
      "AnimeQuote Already In"
    }
    else {
      Database.favoriteQuotes = Database.favoriteQuotes :+ animeQuote
      "AnimeQuote Added Successfully"
    }
  }

  def deleteAnimeQuotesToFavorite(animeQuote: AnimeQuote): String = {
    if (Database.favoriteQuotes.contains(animeQuote)) {
      Database.favoriteQuotes = Database.favoriteQuotes.filter(_.quote != animeQuote.quote)
      "AnimeQuote Removed Successfully"
    }
    else {
      "AnimeQuote Not Found"
    }
  }
}
