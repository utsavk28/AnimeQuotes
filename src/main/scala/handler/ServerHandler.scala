package handler

import external.AnimeChanApi
import model.AnimeQuote

object ServerHandler {
  var favoriteQuotes: Array[AnimeQuote] = Array()

  def getRandomAnimeQuotes(): AnimeQuote = {
    AnimeChanApi.getRandomAnimeQuote()
  }

  def getFavoriteAnimeQuotes(): Array[AnimeQuote] = {
    favoriteQuotes
  }

  def addAnimeQuotesToFavorite(animeQuote: AnimeQuote): String = {
    if (favoriteQuotes.contains(animeQuote)) {
      "AnimeQuote Already In"
    }
    else {
      favoriteQuotes = favoriteQuotes :+ animeQuote
      "AnimeQuote Added Successfully"
    }
  }

  def deleteAnimeQuotesToFavorite(animeQuote: AnimeQuote): String = {
    if (favoriteQuotes.contains(animeQuote)) {
      favoriteQuotes = favoriteQuotes.filter(_.quote != animeQuote.quote)
      "AnimeQuote Removed Successfully"
    }
    else {
      "AnimeQuote Not Found"
    }
  }
}
