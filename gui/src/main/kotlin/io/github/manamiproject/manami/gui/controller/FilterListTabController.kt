package io.github.manamiproject.manami.gui.controller

import io.github.manamiproject.manami.core.Manami
import io.github.manamiproject.manami.entities.FilterListEntry
import javafx.beans.property.SimpleListProperty
import tornadofx.Controller
import tornadofx.observableList

class FilterListTabController : Controller() {

    private val manami = Manami
    val filterListEntries = SimpleListProperty<FilterListEntry>(observableList())

    fun updateEntries() {
        filterListEntries.clear()
        filterListEntries.addAll(manami.fetchFilterList()) //FIXME: probably too expensive on huge lists
    }

    fun filterAnime(filterListEntry: FilterListEntry) {
        runAsync {
            manami.filterAnime(filterListEntry)
        }
    }

    fun removeFromFilterList(filterListEntry: FilterListEntry) {
        runAsync {
            manami.removeFromFilterList(filterListEntry)
        }
    }
}