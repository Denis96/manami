package io.github.manamiproject.manami.cache

import com.google.common.eventbus.Subscribe
import io.github.manamiproject.manami.cache.caches.AnimeCache
import io.github.manamiproject.manami.cache.caches.RecommendationsCache
import io.github.manamiproject.manami.cache.caches.RelatedAnimeCache
import io.github.manamiproject.manami.cache.offlinedatabase.OfflineDatabaseAnimeEntriesParsedEvent
import io.github.manamiproject.manami.cache.offlinedatabase.OfflineDatabaseGitRepository
import io.github.manamiproject.manami.cache.offlinedatabase.OfflineDatabaseRelatedAnimeParsedEvent
import io.github.manamiproject.manami.cache.remoteretrieval.RemoteFetcher
import io.github.manamiproject.manami.cache.remoteretrieval.extractor.Extractors
import io.github.manamiproject.manami.cache.remoteretrieval.extractor.anime.mal.MalAnimeExtractor
import io.github.manamiproject.manami.cache.remoteretrieval.extractor.recommendations.mal.MalRecommendationsExtractor
import io.github.manamiproject.manami.cache.remoteretrieval.extractor.relatedanime.mal.MalRelatedAnimeExtractor
import io.github.manamiproject.manami.common.EventBus
import io.github.manamiproject.manami.common.LoggerDelegate
import io.github.manamiproject.manami.entities.Anime
import io.github.manamiproject.manami.entities.InfoLink
import io.github.manamiproject.manami.entities.RecommendationList


object CacheFacade : Cache {

    private val log by LoggerDelegate()

    init {
        EventBus.register(this)
    }

    private val remoteRetrieval = RemoteFetcher(
        Extractors(
            MalAnimeExtractor(),
            MalRelatedAnimeExtractor(),
            MalRecommendationsExtractor()
        )
    )
    private val animeEntryCache = AnimeCache(remoteRetrieval)
    private val relatedAnimeCache = RelatedAnimeCache(remoteRetrieval)
    private val recommendationsCache = RecommendationsCache(remoteRetrieval)

    init {
        OfflineDatabaseGitRepository.cloneOrUpdate()
    }

    @Subscribe
    private fun populateAnimeEntries(event: OfflineDatabaseAnimeEntriesParsedEvent) {
        log.info("Received OfflineDatabaseAnimeEntriesParsedEvent")

        event.animeEntries.forEach { infoLink, anime ->
            animeEntryCache.populate(infoLink, anime)
        }
    }

    @Subscribe
    private fun populateRelatedAnime(event: OfflineDatabaseRelatedAnimeParsedEvent) {
        log.info("Received OfflineDatabaseRelatedAnimeParsedEvent")

        event.relatedAnimeEntries.forEach { infoLink, relatedAnime ->
            relatedAnimeCache.populate(infoLink, relatedAnime)
        }
    }

    override fun fetchAnime(infoLink: InfoLink): Anime? {
        if (!infoLink.isValid()) {
            return null
        }

        return animeEntryCache.get(infoLink)
    }

    override fun fetchRelatedAnime(infoLink: InfoLink): Set<InfoLink> {
        if (!infoLink.isValid()) {
            return mutableSetOf()
        }

        relatedAnimeCache.get(infoLink)?.let {
            return it
        }

        return hashSetOf()
    }

    override fun fetchRecommendations(infoLink: InfoLink): RecommendationList {
        if (!infoLink.isValid()) {
            return RecommendationList()
        }

        recommendationsCache.get(infoLink)?.let {
            return it
        }

        return RecommendationList()
    }

    override fun invalidate() {
        animeEntryCache.invalidate()
        relatedAnimeCache.invalidate()
        recommendationsCache.invalidate()
    }
}
