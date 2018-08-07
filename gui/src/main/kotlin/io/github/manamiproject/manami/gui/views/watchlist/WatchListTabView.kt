package io.github.manamiproject.manami.gui.views.watchlist

import io.github.manamiproject.manami.entities.Title
import io.github.manamiproject.manami.entities.WatchListEntry
import io.github.manamiproject.manami.gui.components.HyperlinkBuilder.buildHyperlinkFrom
import io.github.manamiproject.manami.gui.components.Icons.createIconDelete
import io.github.manamiproject.manami.gui.components.Icons.createIconFilterList
import io.github.manamiproject.manami.gui.controller.WatchListTabController
import javafx.geometry.Pos.CENTER
import javafx.scene.control.Tab
import javafx.scene.control.TabPane
import javafx.scene.control.TableColumn
import javafx.scene.text.Text
import tornadofx.*


private const val SPACER = 25.0

class WatchListTabView : View() {
    override val root = TabPane()

    private val watchListTabController: WatchListTabController by inject()
    private var titleColumn: TableColumn<WatchListEntry, Title>? = null

    init {
        watchListTabController.watchListEntries.onChange {
            watchListTabController.watchListEntries
                    .map(WatchListEntry::title)
                    .map { title -> Pair(title, title.length) }
                    .maxBy { titleLengthPair -> titleLengthPair.second }
                    ?.let { titleLengthPair ->
                        val width = Text(titleLengthPair.first).layoutBounds.width + SPACER
                        titleColumn?.prefWidth = width
                    }
        }
    }

    val tab = Tab("Watch List").apply {
        content = tableview(watchListTabController.watchListEntries) {
            column("Thumbnail", WatchListEntry::thumbnail).cellFormat {
                graphic = imageview(this.rowItem.thumbnail.toString())
            }

            column("Title", WatchListEntry::title).apply {
                titleColumn = this
            }.cellFormat { title ->
                rowItem.infoLink.url?.let {
                    graphic = buildHyperlinkFrom(title, it)
                }
            }


            column("Actions", WatchListEntry::infoLink).cellFormat {
                val watchListEntry = rowItem

                graphic = hbox(spacing = 5, alignment = CENTER) {
                    button("", createIconFilterList()).action {
                        watchListTabController.filterAnime(watchListEntry)
                    }
                    button("", createIconDelete()).action {
                        watchListTabController.removeFromWatchList(watchListEntry)
                    }
                }
            }
        }
    }
}