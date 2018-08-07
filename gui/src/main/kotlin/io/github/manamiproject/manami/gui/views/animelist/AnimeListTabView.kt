package io.github.manamiproject.manami.gui.views.animelist

import io.github.manamiproject.manami.entities.*
import io.github.manamiproject.manami.gui.controller.AnimeListTabController
import javafx.event.EventHandler
import javafx.scene.control.*
import tornadofx.*

class AnimeListTabView : View() {
    override val root = TabPane()

    private val animeListTabController: AnimeListTabController by inject()

    val tab = Tab("Anime List").apply {
        content = tableview(animeListTabController.animeList) {
            isEditable = true

            column("Title", Anime::title).apply {
                isEditable = true
                cellFactory = TitleTableCellCallback()
                comparator = Comparator { o1, o2 -> o1.compareTo(o2, ignoreCase = true) }
                onEditCommit = EventHandler {
                    animeListTabController.changeTitle(it.rowValue, it.newValue as Title)
                }
            }

            column("Type", Anime::type).apply {
                isEditable = true
                cellFactory = AnimeTypeTableCellCallback()
                comparator = Comparator { o1, o2 -> o1.value.compareTo(o2.value) }
                onEditCommit = EventHandler {
                    animeListTabController.changeType(it.rowValue, it.newValue as AnimeType)
                }
            }

            column("Episodes", Anime::episodes).apply {
                isEditable = true
                cellFactory = EpisodesTableCellCallback()
                comparator = Comparator { o1, o2 -> o1.compareTo(o2) }
                onEditCommit = EventHandler {
                    animeListTabController.changeEpisodes(it.rowValue, it.newValue)
                }
            }

            column("InfoLink", Anime::infoLink).apply {
                isEditable = true
                cellFactory = InfoLinkTableCellCallback()
                comparator = Comparator { o1, o2 -> o1.toString().compareTo(o2.toString()) }
                onEditCommit = EventHandler {
                    animeListTabController.changeInfoLink(it.rowValue, it.newValue)
                }
            }

            column("Location", Anime::location).apply {
                isEditable = true
                cellFactory = LocationTableCellCallback()
                comparator = Comparator { o1, o2 -> o1.compareTo(o2) }
                onEditCommit = EventHandler {
                    animeListTabController.changeLocation(it.rowValue, it.newValue)
                }
            }

            //cmiDeleteEntry.graphic = Icons.createIconDelete()
            //TODO: contextmenu entry for deleting an entry
        }
    }
}