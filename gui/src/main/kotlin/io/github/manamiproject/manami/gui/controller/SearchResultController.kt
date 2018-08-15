package io.github.manamiproject.manami.gui.controller

import io.github.manamiproject.manami.entities.WatchListEntry
import javafx.beans.property.SimpleListProperty
import javafx.collections.FXCollections.observableArrayList
import tornadofx.Controller

class SearchResultController : Controller() {

    val watchListSearchResults = SimpleListProperty<WatchListEntry>(observableArrayList())
}