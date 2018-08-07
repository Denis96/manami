package io.github.manamiproject.manami.gui.controller

import io.github.manamiproject.manami.core.Manami
import io.github.manamiproject.manami.entities.*
import javafx.collections.FXCollections.observableArrayList
import tornadofx.Controller

class AnimeListTabController : Controller() {

    private val manami = Manami
    val animeList = observableArrayList<Anime>()

    fun updateAnimeEntries() {
        animeList.clear()
        animeList.addAll(manami.fetchAnimeList()) //FIXME: probably too expensive on huge lists
    }

    fun changeTitle(anime: Anime, newTitle: Title) {
        runAsync {
            manami.changeTitle(anime, newTitle)
        }
    }

    fun changeType(anime: Anime, newType: AnimeType) {
        runAsync {
            manami.changeType(anime, newType)
        }
    }

    fun changeEpisodes(anime: Anime, newNumberOfEpisodes: Episodes) {
        runAsync {
            manami.changeEpisodes(anime, newNumberOfEpisodes)
        }
    }

    fun changeInfoLink(anime: Anime, newInfoLink: InfoLink) {
        runAsync {
            manami.changeInfoLink(anime, newInfoLink)
        }
    }

    fun changeLocation(anime: Anime, newLocation: Location) {
        runAsync {
            manami.changeLocation(anime, newLocation)
        }
    }
}