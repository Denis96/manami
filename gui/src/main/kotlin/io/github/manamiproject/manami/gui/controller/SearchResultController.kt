package io.github.manamiproject.manami.gui.controller

import io.github.manamiproject.manami.entities.WatchListEntry
import javafx.beans.property.SimpleListProperty
import tornadofx.Controller
import tornadofx.observableList

class SearchResultController : Controller() {

    val watchListSearchResults = SimpleListProperty<WatchListEntry>(observableList())
}