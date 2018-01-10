package io.github.manami.persistence


import io.github.manami.dto.entities.Anime
import io.github.manami.dto.entities.FilterListEntry
import io.github.manami.dto.entities.WatchListEntry
import java.nio.file.Path

interface PersistenceHandler : ApplicationPersistence, InternalPersistenceHandler {

    /**
     * Opens a manami file containing all three lists animelist, filterlist and watchlist which has been saved using {@see PersistenceHandler#save}
     */
    fun open(file: Path)

    /**
     * Imports myanimelist.net file.
     *
     * @param file A myanimelist.net export file.
     */
    fun importMalFile(file: Path)

    /**
     * Import function of a file which has been created using {@see PersistenceHandler#exportListToJsonFile}
     */
    fun importJsonFile(file: Path)

    /**
     * Exports a list of Anime to a specified json file. The json file contains only one array of Anime objects.
     *
     * @param list List of Anime
     * @param file File
     */
    fun exportListToJsonFile(list: MutableList<Anime>, file: Path)

    /**
     * Exports all three lists animelist, filterlist and watchlist toa specified json file.
     * @param file File
     */
    fun exportToJsonFile(file: Path)

    /**
     * Writes all three lists animelist, filterlist and watchlist to a specified file.
     *
     * @param file File
     * @return true if the exportToJsonFile was successful and false if an error occurred.
     */
    fun save(file: Path): Boolean
}
