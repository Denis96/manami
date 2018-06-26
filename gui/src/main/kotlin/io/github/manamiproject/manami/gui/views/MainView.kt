package io.github.manamiproject.manami.gui.views

import io.github.manamiproject.manami.common.isValidFile
import io.github.manamiproject.manami.core.Manami
import io.github.manamiproject.manami.entities.Anime
import io.github.manamiproject.manami.entities.FilterListEntry
import io.github.manamiproject.manami.entities.WatchListEntry
import io.github.manamiproject.manami.gui.components.FileChoosers
import io.github.manamiproject.manami.gui.components.Icons.createIconBranchFork
import io.github.manamiproject.manami.gui.components.Icons.createIconClipboardCheck
import io.github.manamiproject.manami.gui.components.Icons.createIconExit
import io.github.manamiproject.manami.gui.components.Icons.createIconExport
import io.github.manamiproject.manami.gui.components.Icons.createIconFile
import io.github.manamiproject.manami.gui.components.Icons.createIconFilterList
import io.github.manamiproject.manami.gui.components.Icons.createIconFolderOpen
import io.github.manamiproject.manami.gui.components.Icons.createIconImport
import io.github.manamiproject.manami.gui.components.Icons.createIconPlus
import io.github.manamiproject.manami.gui.components.Icons.createIconQuestion
import io.github.manamiproject.manami.gui.components.Icons.createIconRedo
import io.github.manamiproject.manami.gui.components.Icons.createIconSave
import io.github.manamiproject.manami.gui.components.Icons.createIconTags
import io.github.manamiproject.manami.gui.components.Icons.createIconThumbsUp
import io.github.manamiproject.manami.gui.components.Icons.createIconUndo
import io.github.manamiproject.manami.gui.components.Icons.createIconWatchList
import io.github.manamiproject.manami.gui.views.UnsavedChangesDialogView.DialogDecision.*
import io.github.manamiproject.manami.gui.views.animelist.AnimeListTabView
import javafx.application.Platform
import javafx.event.EventHandler
import javafx.scene.Parent
import javafx.scene.control.Button
import javafx.scene.control.MenuItem
import javafx.scene.control.TabPane
import javafx.scene.control.TextField
import javafx.stage.Stage
import org.controlsfx.control.textfield.AutoCompletionBinding
import org.controlsfx.control.textfield.TextFields
import tornadofx.View
import tornadofx.runLater
import java.nio.file.Path
import java.nio.file.Paths

private const val APPLICATION_NAME = "Manami"
private const val FILE_SUFFIX_XML = ".xml"
private const val DIRTY_FLAG = "*"

class MainView : View() {

    override val root: Parent by fxml()

    private val animeListTabView: AnimeListTabView by inject()

    private val tabPane: TabPane by fxid()
    private val miNewList: MenuItem by fxid()
    private val miNewEntry: MenuItem by fxid()
    private val miOpen: MenuItem by fxid()
    private val miImport: MenuItem by fxid()
    private val miCheckList: MenuItem by fxid()
    private val miSave: MenuItem by fxid()
    private val miSaveAs: MenuItem by fxid()
    private val miExit: MenuItem by fxid()
    private val miRedo: MenuItem by fxid()
    private val miUndo: MenuItem by fxid()
    private val miExport: MenuItem by fxid()
    private val miRelatedAnime: MenuItem by fxid()
    private val miRecommendations: MenuItem by fxid()
    private val miAnimeList: MenuItem by fxid()
    private val miWatchList: MenuItem by fxid()
    private val miFilterList: MenuItem by fxid()
    private val miTagList: MenuItem by fxid()
    private val miAbout: MenuItem by fxid()
    private val txtSearchString: TextField by fxid()
    private val btnSearch: Button by fxid()

    private val manami = Manami
    private var autocompletionBinding: AutoCompletionBinding<String> = TextFields.bindAutoCompletion(txtSearchString, setOf())

    init {
        title = APPLICATION_NAME
        initMenuItemGlyphs()
    }

    override fun onDock() {
        this.currentStage?.let {
            it.isMaximized = true

            runLater {
                initNativeCloseButton(it)
            }
        }
    }

    private fun initNativeCloseButton(stage: Stage) {
        Platform.setImplicitExit(false)
        stage.onCloseRequest = EventHandler {
            manami.exit()
        }
    }

    private fun initMenuItemGlyphs() {
        miNewList.graphic = createIconFile()
        miNewEntry.graphic = createIconPlus()
        miOpen.graphic = createIconFolderOpen()
        miSave.graphic = createIconSave()
        miImport.graphic = createIconImport()
        miExport.graphic = createIconExport()
        miExit.graphic = createIconExit()
        miUndo.graphic = createIconUndo()
        miRedo.graphic = createIconRedo()
        miRecommendations.graphic = createIconThumbsUp()
        miRelatedAnime.graphic = createIconBranchFork()
        miTagList.graphic = createIconTags()
        miCheckList.graphic = createIconClipboardCheck()
        miFilterList.graphic = createIconFilterList()
        miWatchList.graphic = createIconWatchList()
        miAbout.graphic = createIconQuestion()
    }

    fun exit() {
        checkFileSavedContext {
            manami.exit()
        }
    }

    fun deleteEntry() {}

    fun newList() {
        checkFileSavedContext {
            manami.newList()
            //TODO: clear everything
        }
    }

    fun showNewEntry() = find(NewEntryView::class).openModal()

    fun open() {
        FileChoosers.showOpenFileDialog(primaryStage)?.let {
            if(it.isValidFile()) {
                checkFileSavedContext {
                    manami.open(it)
                    //TODO: clear everything
                }
            }
        }
    }

    fun importFile() {
        FileChoosers.showImportFileDialog(primaryStage)?.let {
            checkFileSavedContext {
                manami.importFile(it)
            }
        }
    }

    fun export() {
        FileChoosers.showExportDialog(primaryStage)?.let {
            manami.export(it)
        }
    }

    fun save() {
        if(manami.getConfigFile().isValidFile()) {
            manami.save()
        } else {
            saveAs()
        }
    }

    fun saveAs() {
        FileChoosers.showSaveAsFileDialog(primaryStage)?.let {
            val file: Path = it

            if (it.endsWith(FILE_SUFFIX_XML) || it.endsWith(FILE_SUFFIX_XML.toUpperCase())) {
                Paths.get("${it.fileName}$FILE_SUFFIX_XML")
            }

            manami.saveAs(file)
        }
    }

    fun undo() = manami.undo()

    fun redo() = manami.redo()

    fun showRecommendationsTab() {}

    fun showRelatedAnimeTab() {}

    fun showTagListTab() {}

    fun showCheckListTab() {}

    fun showAnimeListTab() = tabPane.tabs.addAll(animeListTabView.tab)

    fun showWatchListTab() {}

    fun showFilterTab() {}

    fun showAbout() = AboutView.showAbout()

    fun updateFileNameInStageTitle() {
        when(manami.getConfigFile().isValidFile()) {
            true -> runLater { title = "$APPLICATION_NAME - ${manami.getConfigFile().fileName}" }
            false -> runLater { title = APPLICATION_NAME }
        }
    }

    fun animeListChanged() {
        updateMenuItemsForImportAndExport()
        updateMenuItemsForAdditionalLists()
        updateAutocompletionEntries()
    }

    fun watchListChanged() {
        updateMenuItemsForImportAndExport()
        updateAutocompletionEntries()
    }

    fun filterListChanged() {
        updateMenuItemsForImportAndExport()
        updateAutocompletionEntries()
    }

    fun fileSavedStatusChanged() {
        updateDirtyFlagInStageTitle()
        updateMenuItemsForSaving()
        updateMenuItemsForUndoAndRedo()
    }

    private fun updateDirtyFlagInStageTitle() {
        if(!manami.isFileUnsaved() && title.endsWith(DIRTY_FLAG)) {
            runLater {
                title = title.substring(0, title.length-1)
            }
        } else if(manami.isFileUnsaved() && !title.endsWith(DIRTY_FLAG)) {
            runLater {
                title = "$title*"
            }
        }
    }

    private fun checkFileSavedContext(command: () -> Unit) {
        var dialogDecision = NO

        if(manami.isFileUnsaved()) {
            dialogDecision = UnsavedChangesDialogView.showUnsavedChangesDialog()
        }

        if(dialogDecision == YES) {
            save()
        }

        if(dialogDecision != CANCEL) {
            runAsync {
                command()
            }
        }
    }

    private fun disableSaveMenuItem(value: Boolean) {
        runLater {
            miSave.isDisable = value
            miSaveAs.isDisable = value
        }
    }

    private fun disableImportMenuItem(value: Boolean) {
        runLater {
            miImport.isDisable = value
        }
    }

    private fun disableExportMenuItem(value: Boolean) {
        runLater {
            miExport.isDisable = value
        }
    }

    private fun disableCheckListMenuItem(value: Boolean) {
        runLater {
            miCheckList.isDisable = value
        }
    }

    private fun disableRelatedAnimeMenuItem(value: Boolean) {
        runLater {
            miRelatedAnime.isDisable = value
        }
    }

    private fun disableRecommendationsMenuItem(value: Boolean) {
        runLater {
            miRecommendations.isDisable = value
        }
    }

    private fun disableUndoMenuItem(value: Boolean) {
        runLater {
            miUndo.isDisable = value
        }
    }

    private fun disableRedoMenuItem(value: Boolean) {
        runLater {
            miRedo.isDisable = value
        }
    }

    private fun updateMenuItemsForSaving() {
        when(manami.isFileUnsaved()) {
            true -> disableSaveMenuItem(false)
            false -> disableSaveMenuItem(true)
        }
    }

    private fun updateMenuItemsForImportAndExport() {
        val animeListIsNotEmpty = manami.fetchAnimeList().isNotEmpty()
        val watchListIsNotEmpty = manami.fetchWatchList().isNotEmpty()
        val filterListIsNotEmpty = manami.fetchFilterList().isNotEmpty()

        when(animeListIsNotEmpty || watchListIsNotEmpty || filterListIsNotEmpty) {
            true -> {
                disableImportMenuItem(true)
                disableExportMenuItem(false)
            }
            false -> {
                disableImportMenuItem(false)
                disableExportMenuItem(true)
            }
        }
    }

    private fun updateMenuItemsForAdditionalLists() {
        when(manami.fetchAnimeList().isNotEmpty()) {
            true -> {
                disableCheckListMenuItem(false)
                disableRelatedAnimeMenuItem(false)
                disableRecommendationsMenuItem(false)
            }
            false -> {
                disableCheckListMenuItem(true)
                disableRelatedAnimeMenuItem(true)
                disableRecommendationsMenuItem(true)
            }
        }
    }

    private fun updateMenuItemsForUndoAndRedo() {
        when(manami.doneCommandsExist()) {
            true -> disableUndoMenuItem(false)
            false -> disableUndoMenuItem(true)
        }

        when(manami.undoneCommandsExist()) {
            true -> disableRedoMenuItem(false)
            false -> disableRedoMenuItem(true)
        }
    }

    private fun updateAutocompletionEntries() {
        val animeListTitles = manami.fetchAnimeList().map(Anime::title).toSet()
        val watchListTitles = manami.fetchWatchList().map(WatchListEntry::title).toSet()
        val filterListTitles = manami.fetchFilterList().map(FilterListEntry::title).toSet()

        val allTitles = animeListTitles.union(watchListTitles).union(filterListTitles)

        autocompletionBinding.dispose()
        autocompletionBinding = TextFields.bindAutoCompletion(txtSearchString, allTitles)
    }
}