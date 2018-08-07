package io.github.manamiproject.manami.gui.events

import com.google.common.eventbus.Subscribe
import io.github.manamiproject.manami.cache.offlinedatabase.OfflineDatabaseUpdatedSuccessfullyEvent
import io.github.manamiproject.manami.common.LoggerDelegate
import io.github.manamiproject.manami.core.events.FileSavedStatusChangedEvent
import io.github.manamiproject.manami.core.events.OpenedFileChangedEvent
import io.github.manamiproject.manami.gui.controller.AnimeListTabController
import io.github.manamiproject.manami.gui.controller.MainController
import io.github.manamiproject.manami.gui.controller.WatchListTabController
import io.github.manamiproject.manami.gui.views.SplashScreenView
import io.github.manamiproject.manami.persistence.events.AnimeListChangedEvent
import io.github.manamiproject.manami.persistence.events.FilterListChangedEvent
import io.github.manamiproject.manami.persistence.events.WatchListChangedEvent
import tornadofx.Controller
import tornadofx.runLater

object EventDispatcher: Controller() {

    val logger by LoggerDelegate()

    private val splashScreenView: SplashScreenView by inject()
    private val mainController: MainController by inject()
    private val animeListTabController: AnimeListTabController by inject()
    private val watchListTabController: WatchListTabController by inject()

    @Subscribe
    fun offlineDatabaseSuccessfullyUpdated(obj: OfflineDatabaseUpdatedSuccessfullyEvent) = splashScreenView.replaceWithMainView()

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
}