package io.github.manamiproject.manami.core.tasks

import io.github.manamiproject.manami.cache.Cache
import io.github.manamiproject.manami.cache.CacheFacade
import io.github.manamiproject.manami.core.ListRandomizer.randomizeOrder
import io.github.manamiproject.manami.entities.Anime
import io.github.manamiproject.manami.entities.MinimalEntry
import io.github.manamiproject.manami.persistence.Persistence


/**
 * This task is called whenever a new list is opened. It creates cache entries for recommendations
 * if necessary.
 * Always start {@link BackgroundTask}s using the {@link TaskConductor}!
 */
internal class RecommendationsCacheInitializationTask(
        private val persistence: Persistence
) : AbstractTask() {

    private val cache: Cache = CacheFacade

    override fun execute() {
        val animeList: List<Anime> = persistence.fetchAnimeList()

        if(!animeList.isEmpty()) {
            randomizeOrder(animeList)
            initializeRecommendations(animeList)
        }
    }

    private fun initializeRecommendations(animeList: List<MinimalEntry>) {
        animeList.forEach{ cache.fetchRecommendations(it.infoLink) }
    }
}