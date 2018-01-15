package io.github.manamiproject.manami.persistence.inmemory

import io.github.manamiproject.manami.persistence.inmemory.animelist.InMemoryAnimeList
import io.github.manamiproject.manami.persistence.inmemory.filterlist.InMemoryFilterList
import io.github.manamiproject.manami.persistence.inmemory.watchlist.InMemoryWatchList
import io.github.manamiproject.manami.dto.entities.*
import io.github.manamiproject.manami.persistence.ApplicationPersistence
import io.github.manamiproject.manami.persistence.InternalPersistence


internal class InMemoryPersistence(
        private val animeListHandler: InMemoryAnimeList,
        private val filterListHandler: InMemoryFilterList,
        private val watchListHandler: InMemoryWatchList
) : ApplicationPersistence, InternalPersistence {


    override fun filterAnime(anime: MinimalEntry): Boolean {
        return filterListHandler.filterAnime(anime)
    }


    override fun fetchFilterList(): MutableList<FilterListEntry> {
        return filterListHandler.fetchFilterList()
    }


    override fun filterListEntryExists(infoLink: InfoLink): Boolean {
        return filterListHandler.filterListEntryExists(infoLink)
    }


    override fun removeFromFilterList(anime: MinimalEntry): Boolean {
        return filterListHandler.removeFromFilterList(anime)
    }


    override fun addAnime(anime: Anime): Boolean {
        return animeListHandler.addAnime(anime)
    }


    override fun fetchAnimeList(): MutableList<Anime> {
        return animeListHandler.fetchAnimeList()
    }


    override fun animeEntryExists(infoLink: InfoLink): Boolean {
        return animeListHandler.animeEntryExists(infoLink)
    }


    override fun removeAnime(anime: Anime): Boolean {
        return animeListHandler.removeAnime(anime)
    }


    override fun watchAnime(anime: MinimalEntry): Boolean {
        return watchListHandler.watchAnime(anime)
    }


    override fun fetchWatchList(): MutableList<WatchListEntry> {
        return watchListHandler.fetchWatchList()
    }


    override fun watchListEntryExists(infoLink: InfoLink): Boolean {
        return watchListHandler.watchListEntryExists(infoLink)
    }


    override fun removeFromWatchList(anime: MinimalEntry): Boolean {
        return watchListHandler.removeFromWatchList(anime)
    }


    override fun clearAll() {
        animeListHandler.clear()
        watchListHandler.clear()
        filterListHandler.clear()
    }


    override fun addAnimeList(list: MutableList<Anime>) {
        list.forEach { anime -> animeListHandler.addAnime(anime) }
    }


    override fun addFilterList(list: MutableList<FilterListEntry>) {
        list.forEach { anime -> filterListHandler.filterAnime(anime) }
    }


    override fun addWatchList(list: MutableList<WatchListEntry>) {
        list.forEach { anime -> watchListHandler.watchAnime(anime) }
    }


    override fun updateOrCreate(entry: MinimalEntry) {
        when (entry) {
            is Anime -> animeListHandler.updateOrCreate(entry)
            is FilterListEntry -> filterListHandler.updateOrCreate(entry)
            is WatchListEntry -> watchListHandler.updateOrCreate(entry)
        }
    }
}