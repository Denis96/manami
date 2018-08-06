package io.github.manamiproject.manami.gui.views.searchresults

import io.github.manamiproject.manami.core.Manami
import io.github.manamiproject.manami.entities.Title
import io.github.manamiproject.manami.entities.WatchListEntry
import io.github.manamiproject.manami.gui.components.HyperlinkBuilder
import io.github.manamiproject.manami.gui.components.Icons
import javafx.collections.FXCollections
import javafx.geometry.Pos
import javafx.scene.control.Tab
import javafx.scene.control.TabPane
import javafx.scene.control.TableColumn
import tornadofx.*

class SearchResultView : View() {
    override val root = TabPane()

    private val manami = Manami
    private val watchListEntries = FXCollections.observableArrayList<WatchListEntry>()
    private var titleColumn: TableColumn<WatchListEntry, Title>? = null

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
                tableview(watchListEntries) {
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
                                runAsync {
                                    manami.filterAnime(watchListEntry)
                                }
                            }
                            button("", Icons.createIconDelete()).action {
                                runAsync {
                                    manami.removeFromWatchList(watchListEntry)
                                }
                            }
                        }
                    }
                }
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
/*
    fun updateEntries() {
        watchListEntries.clear()
        watchListEntries.addAll(manami.fetchWatchList()) //FIXME: probably too expensive on huge lists
        resizeTitleColumn()
    }

    private fun resizeTitleColumn() {
        watchListEntries
                .map(WatchListEntry::title)
                .map { title -> Pair(title, title.length) }
                .maxBy { titleLengthPair -> titleLengthPair.second }
                ?.let { titleLengthPair ->
                    val width = Text(titleLengthPair.first).layoutBounds.width + SPACER
                    titleColumn?.prefWidth = width
                }
    }
}*/