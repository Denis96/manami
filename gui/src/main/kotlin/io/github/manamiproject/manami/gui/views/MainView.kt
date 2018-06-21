package io.github.manamiproject.manami.gui.views

import io.github.manamiproject.manami.common.isValidFile
import io.github.manamiproject.manami.core.Manami
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
import io.github.manamiproject.manami.gui.views.UnsavedChangesDialogView.DialogDecision.YES
import io.github.manamiproject.manami.gui.views.animelist.AnimeListTabView
import javafx.application.Platform
import javafx.event.EventHandler
import javafx.scene.Parent
import javafx.scene.control.Button
import javafx.scene.control.MenuItem
import javafx.scene.control.TabPane
import javafx.scene.control.TextField
import javafx.stage.Stage
import tornadofx.View
import tornadofx.runLater
import java.nio.file.Path
import java.nio.file.Paths

private const val FILE_SUFFIX_XML = ".xml"

class MainView : View() {

    override val root: Parent by fxml()

    private val manami = Manami

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

    init {
        title = "manami"
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
        }
    }

    fun showNewEntry() = find(NewEntryView::class).openModal()

    fun open() {
        FileChoosers.showOpenFileDialog(primaryStage)?.let {
            //TODO: clear everything
            if(it.isValidFile()) {
                checkFileSavedContext {
                    manami.open(it)
                }
            }
        }
    }

    fun importFile() {
        FileChoosers.showImportFileDialog(primaryStage)?.let {
            if(it.isValidFile()) {
                checkFileSavedContext {
                    manami.importFile(it)
                }
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

    fun fileChanged(filename: String) {
        runLater {
            title = "manami - $filename"
        }
    }

    private fun checkFileSavedContext(command: () -> Unit) {
        if(!manami.isFileSaved()) {
            if(UnsavedChangesDialogView.showUnsavedChangesDialog() == YES) {
                manami.save()
            }
        }

        runAsync {
            command()
        }
    }
}