package io.github.manamiproject.manami.gui.controller

import io.github.manamiproject.manami.core.Manami
import io.github.manamiproject.manami.entities.Anime
import javafx.collections.FXCollections.observableArrayList
import tornadofx.Controller

class AnimeListTabController : Controller() {

    private val manami = Manami
    val animeList = observableArrayList<Anime>()

    fun updateAnimeEntries() {
        animeList.clear()
        animeList.addAll(manami.fetchAnimeList()) //FIXME: probably too expensive on huge lists
    }
}