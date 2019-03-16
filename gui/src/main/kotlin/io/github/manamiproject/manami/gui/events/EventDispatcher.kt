package io.github.manamiproject.manami.gui.events

import com.google.common.eventbus.Subscribe
import io.github.manamiproject.manami.common.LoggerDelegate
import io.github.manamiproject.manami.core.events.FileSavedStatusChangedEvent
import io.github.manamiproject.manami.core.events.OpenedFileChangedEvent
import io.github.manamiproject.manami.core.events.SearchResultEvent
import io.github.manamiproject.manami.gui.controller.AnimeListTabController
import io.github.manamiproject.manami.gui.controller.MainController
import io.github.manamiproject.manami.gui.controller.SearchResultController
import io.github.manamiproject.manami.gui.controller.WatchListTabController
import io.github.manamiproject.manami.persistence.events.AnimeListChangedEvent
import io.github.manamiproject.manami.persistence.events.FilterListChangedEvent
import io.github.manamiproject.manami.persistence.events.WatchListChangedEvent
import tornadofx.Controller
import tornadofx.runLater

object EventDispatcher: Controller() {

    val logger by LoggerDelegate()

    private val mainController: MainController by inject()
    private val animeListTabController: AnimeListTabController by inject()
    private val watchListTabController: WatchListTabController by inject()
    private val searchResultController: SearchResultController by inject()

    @Subscribe
    fun openFileChanged(obj: OpenedFileChangedEvent) {
        runLater {
            mainController.updateFileNameInStageTitle()
        }
    }

    @Subscribe
    fun animeListChanged(obj: AnimeListChangedEvent) {
        runLater {
            animeListTabController.updateAnimeEntries()
            mainController.animeListChanged()
        }
    }

    @Subscribe
    fun watchListChanged(obj: WatchListChangedEvent) {
        runLater {
            watchListTabController.updateEntries()
            mainController.watchListChanged()
        }
    }

    @Subscribe
    fun filterListChanged(obj: FilterListChangedEvent) {
        runLater {
            mainController.filterListChanged()
        }
    }

    @Subscribe
    fun fileChanged(obj: FileSavedStatusChangedEvent) {
        runLater {
            mainController.fileSavedStatusChanged()
        }
    }

    @Subscribe
    fun searchResult(obj: SearchResultEvent) {
        runLater {
            searchResultController.watchListSearchResults.value.clear()
            searchResultController.watchListSearchResults.value.addAll(obj.getWatchListSearchResultList())

            searchResultController.filterListSearchResults.value.clear()
            searchResultController.filterListSearchResults.value.addAll(obj.getFilterListSearchResultList())
        }
    }
}