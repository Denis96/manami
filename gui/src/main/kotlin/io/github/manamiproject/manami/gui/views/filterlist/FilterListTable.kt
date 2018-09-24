package io.github.manamiproject.manami.gui.views.filterlist

import io.github.manamiproject.manami.entities.FilterListEntry
import io.github.manamiproject.manami.entities.Title
import io.github.manamiproject.manami.gui.components.HyperlinkBuilder
import io.github.manamiproject.manami.gui.components.Icons
import io.github.manamiproject.manami.gui.controller.FilterListTabController
import io.github.manamiproject.manami.gui.controller.WatchListTabController
import javafx.beans.property.SimpleListProperty
import javafx.geometry.Pos.CENTER
import javafx.scene.control.TableColumn
import javafx.scene.text.Text
import tornadofx.*

private const val SPACER = 25.0

class FilterListTable : Fragment() {

    var entries = SimpleListProperty<FilterListEntry>(observableList())

    private val filterListTabController: FilterListTabController by inject()
    private var titleColumn: TableColumn<FilterListEntry, Title>? = null

    init {
        entries.value.onChange {
            entries.value
                    .map(FilterListEntry::title)
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

            column("Thumbnail", FilterListEntry::thumbnail).cellFormat {
                graphic = imageview(this.rowItem.thumbnail.toString())
            }

            column("Title", FilterListEntry::title).apply {
                titleColumn = this
            }.cellFormat { title ->
                rowItem.infoLink.url?.let {
                    graphic = HyperlinkBuilder.buildHyperlinkFrom(title, it)
                    alignment = CENTER
                }
            }


            column("Actions", FilterListEntry::infoLink).cellFormat {
                val filterListEntry = rowItem

                graphic = hbox(spacing = 5, alignment = CENTER) {
                    button("", Icons.createIconDelete()).action {
                        entries.remove(filterListEntry)
                        filterListTabController.removeFromFilterList(filterListEntry)
                    }
                }
            }
        }
    }.apply {
        fitToParentSize()
    }
}