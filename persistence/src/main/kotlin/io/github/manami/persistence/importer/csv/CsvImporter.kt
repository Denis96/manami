package io.github.manami.persistence.importer.csv

import io.github.manami.dto.AnimeType
import io.github.manami.dto.LoggerDelegate
import io.github.manami.dto.entities.Anime
import io.github.manami.dto.entities.FilterEntry
import io.github.manami.dto.entities.InfoLink
import io.github.manami.dto.entities.WatchListEntry
import io.github.manami.persistence.PersistenceFacade
import io.github.manami.persistence.exporter.csv.CsvConfig
import io.github.manami.persistence.exporter.csv.CsvConfig.CsvConfigType.*
import io.github.manami.persistence.importer.Importer
import org.slf4j.Logger
import org.supercsv.cellprocessor.ift.CellProcessor
import org.supercsv.io.CsvListReader
import org.supercsv.prefs.CsvPreference
import java.io.FileReader
import java.nio.file.Path

class CsvImporter(private val persistence: PersistenceFacade) : Importer {

    private val log: Logger by LoggerDelegate()

    /**
     * Configuration for CSV files.
     */
    private val csvConfig = CsvConfig()

    private val animeListEntries: MutableList<Anime> = mutableListOf()
    private val filterListEntries: MutableList<FilterEntry> = mutableListOf()
    private val watchListEntries: MutableList<WatchListEntry> = mutableListOf()

    fun CsvListReader.readDocument(processors: Array<CellProcessor>): List<List<Any>> {
        val document: MutableList<MutableList<Any>> = mutableListOf()

        var currentLine: MutableList<Any>?
        do {
            currentLine = this.read(*processors)
            currentLine?.let(document::add)
        } while (currentLine != null)

        return document
    }


    override fun importFile(file: Path) {
        val processors: Array<CellProcessor> = csvConfig.getProcessors()
        CsvListReader(FileReader(file.toFile()), CsvPreference.STANDARD_PREFERENCE).use { listReader ->
            listReader.getHeader(true)

            for (line in listReader.readDocument(processors)) {
                val stringList = line.filterIsInstance(String::class.java)
                // get all columns
                val title = stringList.getOrNull(1)?.trim()
                val type = stringList.getOrNull(2)?.let { AnimeType.findByName(it.trim()) } ?: AnimeType.TV
                val episodes: Int = stringList.getOrNull(3)?.toIntOrNull() ?: 0
                val infoLink = stringList.getOrNull(4)?.let { InfoLink(it.trim()) }
                val location = stringList.getOrNull(5)?.trim() ?: "/"

                if (title != null && infoLink != null) {
                    // create object by list type
                    CsvConfig.CsvConfigType.findByName(stringList[0])?.let {
                        when (it) {
                            ANIMELIST -> animeListEntries.add(Anime(title, infoLink, episodes, type, location))
                            WATCHLIST -> watchListEntries.add(WatchListEntry(title, infoLink))
                            FILTERLIST -> filterListEntries.add(FilterEntry(title, infoLink))
                        }
                    }
                }
            }

            persistence.addAnimeList(animeListEntries)
            persistence.addFilterList(filterListEntries)
            persistence.addWatchList(watchListEntries)
        }
    }
}