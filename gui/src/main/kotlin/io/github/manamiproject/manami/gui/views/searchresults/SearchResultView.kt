package io.github.manamiproject.manami.gui.views.searchresults

import io.github.manamiproject.manami.gui.controller.SearchResultController
import io.github.manamiproject.manami.gui.views.watchlist.WatchListTable
import javafx.scene.control.Tab
import javafx.scene.control.TabPane
import tornadofx.*

class SearchResultView : View() {
    override val root = TabPane()

    private val searchResultController: SearchResultController by inject()
    private val watchListTable: WatchListTable = find(WatchListTable::class)

    init {
        watchListTable.entries.addAll(searchResultController.watchListSearchResults)
        searchResultController.watchListSearchResults.bind(watchListTable.entries)
    }


    val tab = Tab("Search results").apply {
        content = vbox {
            titledpane {
                text = "Anime list"
                content = label("anime list search results")
            }
            titledpane {
                text = "Watch list"
                content = watchListTable.root
            }
            titledpane {
                text = "Filter list"
                content = label("filter list search results")
            }
        }
    }
}