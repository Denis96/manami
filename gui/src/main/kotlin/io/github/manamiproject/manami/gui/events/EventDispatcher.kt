package io.github.manamiproject.manami.gui.events

import com.google.common.eventbus.Subscribe
import io.github.manamiproject.manami.cache.offlinedatabase.OfflineDatabaseUpdatedSuccessfullyEvent
import io.github.manamiproject.manami.core.events.FileSavedStatusChangedEvent
import io.github.manamiproject.manami.core.events.OpenedFileChangedEvent
import io.github.manamiproject.manami.gui.controller.AnimeListTabController
import io.github.manamiproject.manami.gui.controller.MainController
import io.github.manamiproject.manami.gui.controller.WatchListTabController
import io.github.manamiproject.manami.gui.views.MainView
import io.github.manamiproject.manami.gui.views.SplashScreenView
import io.github.manamiproject.manami.persistence.events.AnimeListChangedEvent
import io.github.manamiproject.manami.persistence.events.FilterListChangedEvent
import io.github.manamiproject.manami.persistence.events.WatchListChangedEvent
import tornadofx.Controller

object EventDispatcher: Controller() {

    private val splashScreenView: SplashScreenView by inject()
    private val mainController: MainController by inject()
    private val animeListTabController: AnimeListTabController by inject()
    private val watchListTabController: WatchListTabController by inject()

    @Subscribe
    fun offlineDatabaseSuccessfullyUpdated(obj: OfflineDatabaseUpdatedSuccessfullyEvent) = splashScreenView.replaceWithMainView()

    @Subscribe
    fun openFileChanged(obj: OpenedFileChangedEvent) = mainController.updateFileNameInStageTitle()

    @Subscribe
    fun animeListChanged(obj: AnimeListChangedEvent) {
        animeListTabController.updateAnimeEntries()
        mainController.animeListChanged()
    }

    @Subscribe
    fun watchListChanged(obj: WatchListChangedEvent) {
        watchListTabController.updateEntries()
        mainController.watchListChanged()
    }

    @Subscribe
    fun filterListChanged(obj: FilterListChangedEvent) = mainController.filterListChanged()

    @Subscribe
    fun fileChanged(obj: FileSavedStatusChangedEvent) = mainController.fileSavedStatusChanged()
}