package io.github.manamiproject.manami.gui.views.searchresults

import io.github.manamiproject.manami.gui.controller.SearchResultController
import io.github.manamiproject.manami.gui.views.filterlist.FilterListTable
import io.github.manamiproject.manami.gui.views.watchlist.WatchListTable
import javafx.scene.control.Tab
import javafx.scene.control.TabPane
import tornadofx.*

class SearchResultView : View() {

    override val root = TabPane()

    private val searchResultController: SearchResultController by inject()
    private val animeListTable: AnimeListSearchResultTable = find(AnimeListSearchResultTable::class)
    private val watchListTable: WatchListTable = find(WatchListTable::class)
    private val filterListTable: FilterListTable = find(FilterListTable::class)

    init {
        watchListTable.entries.addAll(searchResultController.watchListSearchResults)
        searchResultController.watchListSearchResults.bind(watchListTable.entries)

        filterListTable.entries.addAll(searchResultController.filterListSearchResults)
        searchResultController.filterListSearchResults.bind(filterListTable.entries)
    }


    val tab = Tab("Search results").apply {
        content = vbox {
            titledpane {
                text = "Anime list"
                content = animeListTable.root
            }
            titledpane {
                text = "Watch list"
                content = watchListTable.root
            }
            titledpane {
                text = "Filter list"
                content = filterListTable.root
            }
        }
    }
}