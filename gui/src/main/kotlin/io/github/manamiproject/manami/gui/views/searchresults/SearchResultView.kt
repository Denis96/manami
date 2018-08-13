package io.github.manamiproject.manami.gui.views.searchresults

import io.github.manamiproject.manami.gui.views.watchlist.WatchListTable
import javafx.scene.control.Tab
import javafx.scene.control.TabPane
import tornadofx.View
import tornadofx.pane
import tornadofx.titledpane
import tornadofx.vbox

class SearchResultView : View() {
    override val root = TabPane()

    private val watchListTableView: WatchListTable = find(WatchListTable::class)


    val tab = Tab("Search results").apply {
        content = vbox {
            titledpane {
                text = "Anime list"
                pane {
                    title = "WOOHOO"
                }
            }
            titledpane {
                text = "Watch list"
                content = watchListTableView.root
            }
            titledpane {
                text = "Filter list"
                pane {
                    title = "WOOHOO"
                }
            }
        }
    }
}