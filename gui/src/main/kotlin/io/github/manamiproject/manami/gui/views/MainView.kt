package io.github.manamiproject.manami.gui.views

import io.github.manamiproject.manami.common.isValidFile
import io.github.manamiproject.manami.core.Manami
import io.github.manamiproject.manami.gui.components.FileChoosers.showExportDialog
import io.github.manamiproject.manami.gui.components.FileChoosers.showImportFileDialog
import io.github.manamiproject.manami.gui.components.FileChoosers.showOpenFileDialog
import io.github.manamiproject.manami.gui.components.FileChoosers.showSaveAsFileDialog
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
import io.github.manamiproject.manami.gui.controller.MainController
import io.github.manamiproject.manami.gui.views.UnsavedChangesDialogView.DialogDecision.*
import io.github.manamiproject.manami.gui.views.animelist.AnimeListTabView
import io.github.manamiproject.manami.gui.views.searchresults.SearchResultView
import io.github.manamiproject.manami.gui.views.watchlist.WatchListTabView
import javafx.application.Platform
import javafx.event.EventHandler
import javafx.scene.Parent
import javafx.scene.control.*
import javafx.stage.Stage
import org.controlsfx.control.textfield.TextFields
import tornadofx.View
import tornadofx.action
import java.nio.file.Path
import java.nio.file.Paths


private const val FILE_SUFFIX_XML = ".xml"

class MainView : View() {

    override val root: Parent by fxml()

    private val animeListTabView: AnimeListTabView by inject()
    private val watchListTabView: WatchListTabView by inject()
    private val searchResultView: SearchResultView by inject()
    private val mainController: MainController by inject()

    private val tabPane: TabPane by fxid()
    private val miNewList: MenuItem by fxid()
    private val miNewEntry: MenuItem by fxid()
    private val miOpen: MenuItem by fxid()
    private val miImport: MenuItem by fxid()
    private val miCheckList: MenuItem by fxid()
    private val miSave: MenuItem by fxid()
    private val miSaveAs: MenuItem by fxid()
    private val miExit: MenuItem by fxid()
    private val miUndo: MenuItem by fxid()
    private val miRedo: MenuItem by fxid()
    private val miExport: MenuItem by fxid()
    private val miRelatedAnime: MenuItem by fxid()
    private val miRecommendations: MenuItem by fxid()
    private val miAnimeList: MenuItem by fxid()
    private val miWatchList: MenuItem by fxid()
    private val miFilterList: MenuItem by fxid()
    private val miTagList: MenuItem by fxid()
    private val miInvalidateCache: MenuItem by fxid()
    private val miAbout: MenuItem by fxid()
    private val txtSearchString: TextField by fxid()
    private val btnSearch: Button by fxid()

    private val manami = Manami


    override fun onDock() {
        this.currentStage?.let {
            it.isMaximized = true

            initNativeCloseButton(it)
        }
    }

    init {
        TextFields.bindAutoCompletion(txtSearchString, mainController.autoCompleteTitles)
        initProperties()
        initMenuItemGlyphs()
        initSearchButton()
    }

    private fun initProperties() {
        miImport.disableProperty().bindBidirectional(mainController.disableImportMenuItemProperty)
        miCheckList.disableProperty().bindBidirectional(mainController.disableCheckListMenuItemProperty)
        miSave.disableProperty().bindBidirectional(mainController.disableSaveMenuItemProperty)
        miSaveAs.disableProperty().bindBidirectional(mainController.disableSaveAsMenuItemProperty)
        miUndo.disableProperty().bindBidirectional(mainController.disableUndoMenuItemProperty)
        miRedo.disableProperty().bindBidirectional(mainController.disableRedoMenuItemProperty)
        miExport.disableProperty().bindBidirectional(mainController.disableExportMenuItemProperty)
        miRelatedAnime.disableProperty().bindBidirectional(mainController.disableRelatedAnimeMenuItemProperty)
        miRecommendations.disableProperty().bindBidirectional(mainController.disableRecommendationsMenuItemProperty)
        titleProperty.bindBidirectional(mainController.titleProperty)
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

    private fun initSearchButton() {
        btnSearch.action {
            if (!tabPane.tabs.contains(searchResultView.tab)) {
                tabPane.tabs.addAll(searchResultView.tab)
            }

            tabPane.selectionModel.select(searchResultView.tab)
        }
    }


    private fun initNativeCloseButton(stage: Stage) {
        Platform.setImplicitExit(false)
        stage.onCloseRequest = EventHandler {
            manami.exit()
        }
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
        showOpenFileDialog(primaryStage)?.let {
            if(it.isValidFile()) {
                checkFileSavedContext {
                    manami.open(it)
                    //TODO: clear everything
                }
            }
        }
    }

    fun importFile() {
        showImportFileDialog(primaryStage)?.let {
            checkFileSavedContext {
                manami.importFile(it)
            }
        }
    }

    fun export() {
        showExportDialog(primaryStage)?.let {
            manami.export(it)
        }
    }

    fun save() {
        if(manami.getCurrentlyOpenedFile().isValidFile()) {
            manami.save()
        } else {
            saveAs()
        }
    }

    fun saveAs() {
        showSaveAsFileDialog(primaryStage)?.let {
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

    fun showAnimeListTab() {
        showTab(animeListTabView.tab)
    }

    fun showWatchListTab() {
        showTab(watchListTabView.tab)
    }

    fun showFilterTab() {}

    private fun showTab(tab: Tab) {
        if (!tabPane.tabs.contains(tab)) {
            tabPane.tabs.addAll(tab)
        }

        tabPane.selectionModel.select(tab)
    }

    fun invalidateCache() {
        manami.invalidateCache()
    }

    fun showAbout() = AboutView.showAbout()

    private fun checkFileSavedContext(command: () -> Unit) {
        var dialogDecision = NO

        if(manami.isFileUnsaved()) {
            dialogDecision = UnsavedChangesDialogView.showUnsavedChangesDialog()
        }

        if(dialogDecision == YES) {
            save()
        }

        if(dialogDecision != CANCEL) {
            command()
        }
    }
}