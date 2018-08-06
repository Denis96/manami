package io.github.manamiproject.manami.gui.controller

import io.github.manamiproject.manami.core.Manami
import io.github.manamiproject.manami.entities.WatchListEntry
import javafx.collections.FXCollections.observableArrayList
import tornadofx.Controller

class WatchListTabController : Controller() {

    private val manami = Manami
    val watchListEntries = observableArrayList<WatchListEntry>()

    fun updateEntries() {
        watchListEntries.clear()
        watchListEntries.addAll(manami.fetchWatchList()) //FIXME: probably too expensive on huge lists
    }
}