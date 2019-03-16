package io.github.manamiproject.manami.gui.views

import io.github.manamiproject.manami.common.extensions.fileExists
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
import javafx.collections.FXCollections.observableSet
import javafx.collections.SetChangeListener
import javafx.event.EventHandler
import javafx.scene.Parent
import javafx.scene.control.*
import javafx.stage.Stage
import org.controlsfx.control.textfield.TextFields.bindAutoCompletion
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
    private val miAbout: MenuItem by fxid()
    private val txtSearchString: TextField by fxid()
    private val btnSearch: Button by fxid()

    private var autoCompletionBinding = bindAutoCompletion(txtSearchString, observableSet<String>())


    override fun onDock() {
        this.currentStage?.let {
            it.isMaximized = true

            initNativeCloseButton(it)
        }
    }

    init {
        initAutocompletion()
        initProperties()
        initMenuItemGlyphs()
        initSearchButton()
    }

    private fun initAutocompletion() {
        mainController.autoCompleteTitles.addListener(SetChangeListener {
            autoCompletionBinding.dispose()
            autoCompletionBinding = bindAutoCompletion(txtSearchString, mainController.autoCompleteTitles)
        })
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
        miAnimeList.disableProperty().bindBidirectional(mainController.disableAnimeListMenuItemProperty)
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
            if(txtSearchString.text.isNotBlank()) {
                if (!tabPane.tabs.contains(searchResultView.tab)) {
                    tabPane.tabs.addAll(searchResultView.tab)
                }

                tabPane.selectionModel.select(searchResultView.tab)

                mainController.search(txtSearchString.text)
                txtSearchString.text = ""
            }
        }
    }


    private fun initNativeCloseButton(stage: Stage) {
        Platform.setImplicitExit(false)
        stage.onCloseRequest = EventHandler {
            mainController.exit()
        }
    }


    fun exit() {
        checkFileSavedContext {
            mainController.exit()
        }
    }

    fun deleteEntry() {}

    fun newList() {
        checkFileSavedContext {
            mainController.newList()
            //TODO: clear everything
        }
    }

    fun showNewEntry() = find(NewEntryView::class).openModal()

    fun open() {
        showOpenFileDialog(primaryStage)?.let {
            if(it.fileExists()) {
                checkFileSavedContext {
                    mainController.open(it)
                    //TODO: clear everything
                }
            }
        }
    }

    fun importFile() {
        showImportFileDialog(primaryStage)?.let {
            checkFileSavedContext {
                mainController.importFile(it)
            }
        }
    }

    fun export() {
        showExportDialog(primaryStage)?.let {
            mainController.export(it)
        }
    }

    fun save() {
        if(mainController.isOpenedFileValid()) {
            mainController.save()
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

            mainController.saveAs(file)
        }
    }

    fun undo() = mainController.undo()

    fun redo() = mainController.redo()

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
        mainController.invalidateCache()
    }

    fun showAbout() = AboutView.showAbout()

    private fun checkFileSavedContext(command: () -> Unit) {
        var dialogDecision = NO

        if(mainController.isFileUnsaved()) {
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