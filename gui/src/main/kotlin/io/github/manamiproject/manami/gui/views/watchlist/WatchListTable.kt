package io.github.manamiproject.manami.gui.views.watchlist

import io.github.manamiproject.manami.entities.Title
import io.github.manamiproject.manami.entities.WatchListEntry
import io.github.manamiproject.manami.gui.components.HyperlinkBuilder
import io.github.manamiproject.manami.gui.components.Icons
import io.github.manamiproject.manami.gui.controller.WatchListTabController
import javafx.beans.property.SimpleListProperty
import javafx.geometry.Pos
import javafx.scene.control.TableColumn
import javafx.scene.text.Text
import tornadofx.*

private const val SPACER = 25.0

class WatchListTable : Fragment() {

    private val watchListTabController: WatchListTabController by inject()
    private var titleColumn: TableColumn<WatchListEntry, Title>? = null

    var entries = SimpleListProperty<WatchListEntry>(observableList())

    init {
        entries.value.onChange {
            entries.value
                    .map(WatchListEntry::title)
                    .map { title -> Pair(title, title.length) }
                    .maxBy { titleLengthPair -> titleLengthPair.second }
                    ?.let { titleLengthPair ->
                        val width = Text(titleLengthPair.first).layoutBounds.width + SPACER
                        titleColumn?.prefWidth = width
                    }
        }
    }

    override val root = anchorpane {
        tableview(entries.value) {
            fitToParentSize()

            column("Thumbnail", WatchListEntry::thumbnail).cellFormat {
                graphic = imageview(this.rowItem.thumbnail.toString())
            }

            column("Title", WatchListEntry::title).apply {
                titleColumn = this
            }.cellFormat { title ->
                rowItem.infoLink.url?.let {
                    graphic = HyperlinkBuilder.buildHyperlinkFrom(title, it)
                }
            }


            column("Actions", WatchListEntry::infoLink).cellFormat {
                val watchListEntry = rowItem

                graphic = hbox(spacing = 5, alignment = Pos.CENTER) {
                    button("", Icons.createIconFilterList()).action {
                        watchListTabController.filterAnime(watchListEntry)
                        entries.remove(watchListEntry)
                    }
                    button("", Icons.createIconDelete()).action {
                        watchListTabController.removeFromWatchList(watchListEntry)
                        entries.remove(watchListEntry)
                    }
                }
            }
        }
    }.apply {
        fitToParentSize()
    }
}