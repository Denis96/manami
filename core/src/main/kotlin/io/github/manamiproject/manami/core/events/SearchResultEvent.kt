package io.github.manamiproject.manami.core.events;

import io.github.manamiproject.manami.entities.Anime
import io.github.manamiproject.manami.entities.FilterListEntry
import io.github.manamiproject.manami.entities.MinimalEntry
import io.github.manamiproject.manami.entities.WatchListEntry


/**
 * Contains a {@link List} of {@link MinimalEntry} for each list typeChecklist.
 */
class SearchResultEvent(val searchString: String) {

    private val animeListSearchResultList: MutableList<Anime> = mutableListOf()
    private val filterListSearchResultList: MutableList<FilterListEntry> = mutableListOf()
    private val watchListSearchResultList: MutableList<WatchListEntry> = mutableListOf()

    /**
     * @return The list containing search results from anime list.
     */
    fun getAnimeListSearchResultList() = animeListSearchResultList.toList()


    /**
     * @return The list containing search results from filter list.
     */
    fun getFilterListSearchResultList() = filterListSearchResultList.toList()


    /**
     * @return The list containing search results from watch list.
     */
    fun getWatchListSearchResultList() = watchListSearchResultList.toList()


    fun addAnimeListSearchResult(entry: Anime) {
        animeListSearchResultList.add(entry)
    }

    fun addFilterListSearchResult(entry: FilterListEntry) {
        filterListSearchResultList.add(entry)
    }


    fun addWatchListSearchResult(entry: WatchListEntry) {
        watchListSearchResultList.add(entry)
    }
}
