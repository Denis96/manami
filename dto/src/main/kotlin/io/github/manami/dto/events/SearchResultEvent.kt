package io.github.manami.dto.events;

import io.github.manami.dto.entities.MinimalEntry


/**
 * Contains a {@link List} of {@link MinimalEntry} for each list type.
 */
class SearchResultEvent(val searchString: String) {

    private val animeListSearchResultList = mutableListOf<MinimalEntry>()
    private val filterListSearchResultList = mutableListOf<MinimalEntry>()
    private val watchListSearchResultList = mutableListOf<MinimalEntry>()

    /**
     * @since 2.9.0
     * @return The list containing search results from anime list.
     */
    fun getAnimeListSearchResultList() = animeListSearchResultList.toList()


    /**
     * @since 2.9.0
     * @return The list containing search results from filter list.
     */
    fun getFilterListSearchResultList() = filterListSearchResultList.toList()


    /**
     * @since 2.9.0
     * @return The list containing search results from watch list.
     */
    fun getWatchListSearchResultList() = watchListSearchResultList.toList()


    fun addAnimeListSearchResult(entry: MinimalEntry) {
        animeListSearchResultList.add(entry)
    }

    fun addFilterListSearchResult(entry: MinimalEntry) {
        filterListSearchResultList.add(entry)
    }


    fun addWatchListSearchResult(entry: MinimalEntry) {
        watchListSearchResultList.add(entry)
    }
}
