package io.github.manamiproject.manami.gui.controller

import io.github.manamiproject.manami.core.Manami
import io.github.manamiproject.manami.entities.WatchListEntry
import javafx.beans.property.SimpleListProperty
import tornadofx.Controller
import tornadofx.observableList

class WatchListTabController : Controller() {

    private val manami = Manami
    val watchListEntries = SimpleListProperty<WatchListEntry>(observableList())

    fun updateEntries() {
        watchListEntries.clear()
        watchListEntries.addAll(manami.fetchWatchList()) //FIXME: probably too expensive on huge lists
    }

    fun filterAnime(watchListEntry: WatchListEntry) {
        runAsync {
            manami.filterAnime(watchListEntry)
        }
    }

    fun removeFromWatchList(watchListEntry: WatchListEntry) {
        runAsync {
            manami.removeFromWatchList(watchListEntry)
        }
    }
}