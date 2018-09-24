package io.github.manamiproject.manami.gui.views.filterlist

import io.github.manamiproject.manami.gui.controller.FilterListTabController
import javafx.scene.control.Tab
import javafx.scene.control.TabPane
import tornadofx.View

class FilterListTabView : View() {

    override val root = TabPane()

    private val filterListTabController: FilterListTabController by inject()
    private val filterListTable: FilterListTable = find(FilterListTable::class)


    init {
        filterListTable.entries.addAll(filterListTabController.filterListEntries)
        filterListTabController.filterListEntries.bind(filterListTable.entries)
    }

    val tab = Tab("Filter List").apply {
        content = filterListTable.root
    }
}