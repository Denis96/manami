package io.github.manamiproject.manami.gui.views.watchlist

import io.github.manamiproject.manami.gui.controller.WatchListTabController
import javafx.scene.control.Tab
import javafx.scene.control.TabPane
import tornadofx.View


class WatchListTabView : View() {

    override val root = TabPane()

    private val watchListTabController: WatchListTabController by inject()
    private val watchListTable: WatchListTable = find(WatchListTable::class)


    init {
        watchListTable.entries.addAll(watchListTabController.watchListEntries)
        watchListTabController.watchListEntries.bind(watchListTable.entries)
    }

    val tab = Tab("Watch List").apply {
        content = watchListTable.root
    }
}