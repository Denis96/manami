package io.github.manamiproject.manami.gui.controller

import io.github.manamiproject.manami.common.isValidFile
import io.github.manamiproject.manami.core.Manami
import io.github.manamiproject.manami.entities.Anime
import io.github.manamiproject.manami.entities.FilterListEntry
import io.github.manamiproject.manami.entities.WatchListEntry
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections.observableSet
import tornadofx.Controller


private const val APPLICATION_NAME = "Manami"
private const val DIRTY_FLAG = "*"

class MainController : Controller() {

    private val manami = Manami

    val autoCompleteTitles = observableSet<String>()
    val disableImportMenuItemProperty = SimpleBooleanProperty(false)
    val disableCheckListMenuItemProperty = SimpleBooleanProperty(true)
    val disableSaveMenuItemProperty = SimpleBooleanProperty(true)
    val disableSaveAsMenuItemProperty = SimpleBooleanProperty(true)
    val disableUndoMenuItemProperty = SimpleBooleanProperty(true)
    val disableRedoMenuItemProperty = SimpleBooleanProperty(true)
    val disableExportMenuItemProperty = SimpleBooleanProperty(true)
    val disableRelatedAnimeMenuItemProperty = SimpleBooleanProperty(true)
    val disableRecommendationsMenuItemProperty = SimpleBooleanProperty(true)
    val titleProperty = SimpleStringProperty(APPLICATION_NAME)


    fun updateFileNameInStageTitle() {
        when(manami.getCurrentlyOpenedFile().isValidFile()) {
            true -> titleProperty.value = "$APPLICATION_NAME - ${manami.getCurrentlyOpenedFile().fileName}"
            false -> titleProperty.value = APPLICATION_NAME
        }
    }

    fun fileSavedStatusChanged() {
        updateDirtyFlagInStageTitle()
        updateMenuItemsForSaving()
        updateMenuItemsForUndoAndRedo()
    }

    private fun updateDirtyFlagInStageTitle() {
        if(!manami.isFileUnsaved() && titleProperty.value.endsWith(DIRTY_FLAG)) {
            titleProperty.value = titleProperty.value.substring(0, titleProperty.value.length-1)
        } else if(manami.isFileUnsaved() && !titleProperty.value.endsWith(DIRTY_FLAG)) {
            titleProperty.value = "${titleProperty.value}*"
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

    private fun updateMenuItemsForImportAndExport() {
        val animeListIsNotEmpty = manami.fetchAnimeList().isNotEmpty()
        val watchListIsNotEmpty = manami.fetchWatchList().isNotEmpty()
        val filterListIsNotEmpty = manami.fetchFilterList().isNotEmpty()

        when(animeListIsNotEmpty || watchListIsNotEmpty || filterListIsNotEmpty) {
            true -> {
                disableImportMenuItemProperty.value = true
                disableExportMenuItemProperty.value = false
            }
            false -> {
                disableImportMenuItemProperty.value = false
                disableExportMenuItemProperty.value = true
            }
        }
    }

    private fun updateMenuItemsForAdditionalLists() {
        when(manami.fetchAnimeList().isNotEmpty()) {
            true -> {
                disableCheckListMenuItemProperty.value = false
                disableRelatedAnimeMenuItemProperty.value = false
                disableRecommendationsMenuItemProperty.value = false
            }
            false -> {
                disableCheckListMenuItemProperty.value = true
                disableRelatedAnimeMenuItemProperty.value = true
                disableRecommendationsMenuItemProperty.value = true
            }
        }
    }

    private fun disableSaveMenuItem(value: Boolean) {
        disableSaveMenuItemProperty.value = value
        disableSaveAsMenuItemProperty.value = value
    }

    private fun updateMenuItemsForSaving() {
        when(manami.isFileUnsaved()) {
            true -> disableSaveMenuItem(false)
            false -> disableSaveMenuItem(true)
        }
    }

    private fun updateMenuItemsForUndoAndRedo() {
        when(manami.doneCommandsExist()) {
            true -> disableUndoMenuItemProperty.value = false
            false -> disableUndoMenuItemProperty.value = true
        }

        when(manami.undoneCommandsExist()) {
            true -> disableRedoMenuItemProperty.value = false
            false -> disableRedoMenuItemProperty.value = true
        }
    }

    private fun updateAutocompletionEntries() {
        val animeListTitles = manami.fetchAnimeList().map(Anime::title).toSet()
        val watchListTitles = manami.fetchWatchList().map(WatchListEntry::title).toSet()
        val filterListTitles = manami.fetchFilterList().map(FilterListEntry::title).toSet()

        val allTitles = animeListTitles.union(watchListTitles).union(filterListTitles)

        autoCompleteTitles.clear()
        autoCompleteTitles.addAll(allTitles)
    }
}