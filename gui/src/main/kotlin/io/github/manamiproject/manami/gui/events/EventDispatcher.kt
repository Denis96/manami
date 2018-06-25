package io.github.manamiproject.manami.gui.events

import com.google.common.eventbus.Subscribe
import io.github.manamiproject.manami.cache.offlinedatabase.OfflineDatabaseUpdatedSuccessfullyEvent
import io.github.manamiproject.manami.common.isValidFile
import io.github.manamiproject.manami.core.Manami
import io.github.manamiproject.manami.core.events.FileSavedStatusChangedEvent
import io.github.manamiproject.manami.core.events.OpenedFileChangedEvent
import io.github.manamiproject.manami.gui.views.MainView
import io.github.manamiproject.manami.gui.views.SplashScreenView
import io.github.manamiproject.manami.gui.views.animelist.AnimeListTabView
import io.github.manamiproject.manami.persistence.events.AnimeListChangedEvent
import tornadofx.Controller

object EventDispatcher: Controller() {

    private val manami = Manami;
    private val splashScreenView: SplashScreenView by inject()
    private val mainView: MainView by inject()
    private val animeList: AnimeListTabView by inject()

    @Subscribe
    fun offlineDatabaseSuccessfullyUpdated(obj: OfflineDatabaseUpdatedSuccessfullyEvent) = splashScreenView.replaceWithMainView()

    @Subscribe
    fun openFileChanged(obj: OpenedFileChangedEvent) {
        mainView.updateFileNameInStageTitle()

        when(manami.getConfigFile().isValidFile()) {
            true -> mainView.disableImportButton(true)
            false -> mainView.disableImportButton(false)
        }
    }

    @Subscribe
    fun animeListChanged(obj: AnimeListChangedEvent) = animeList.updateAnimeEntries()

    @Subscribe
    fun fileChanged(obj: FileSavedStatusChangedEvent) {
        mainView.updateDirtyFlagInStageTitle()

        when(manami.isFileUnsaved()) {
            true -> mainView.disableSaveButton(true)
            false -> mainView.disableSaveButton(false)
        }
    }
}