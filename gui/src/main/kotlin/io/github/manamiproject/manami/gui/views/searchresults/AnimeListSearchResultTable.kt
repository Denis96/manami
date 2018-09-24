package io.github.manamiproject.manami.gui.views.searchresults

import io.github.manamiproject.manami.entities.Anime
import io.github.manamiproject.manami.entities.Title
import io.github.manamiproject.manami.gui.components.HyperlinkBuilder
import javafx.beans.property.SimpleListProperty
import javafx.geometry.Pos.CENTER
import javafx.scene.control.TableColumn
import tornadofx.*

class AnimeListSearchResultTable : Fragment() {

    var entries = SimpleListProperty<Anime>(observableList())

    private var titleColumn: TableColumn<Anime, Title>? = null


    override val root = anchorpane {
        tableview(entries.value) { //TODO: create binding
            fitToParentSize()

            column("Thumbnail", Anime::thumbnail).cellFormat {
                graphic = imageview(this.rowItem.thumbnail.toString())
            }

            column("Title", Anime::title).apply {
                titleColumn = this
            }.cellFormat { title ->
                rowItem.infoLink.url?.let {
                    graphic = HyperlinkBuilder.buildHyperlinkFrom(title, it)
                    alignment = CENTER
                }
            }
        }
    }
}