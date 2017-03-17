package io.github.manami.cache;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import javax.inject.Inject;
import javax.inject.Named;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Sets;

import io.github.manami.cache.strategies.daemon.DaemonRestRetrievalStrategy;
import io.github.manami.cache.strategies.headlessbrowser.HeadlessBrowserRetrievalStrategy;
import io.github.manami.dto.entities.Anime;
import io.github.manami.dto.entities.InfoLink;
import io.github.manami.dto.entities.RecommendationList;
import lombok.extern.slf4j.Slf4j;

/**
 * Facade for all inquiries against a cache.
 * It orchestrates the use of concrete {@link io.github.manami.cache.Cache}
 * implementations.
 */
@Slf4j
@Named
public final class CacheManager implements Cache {

    private final DaemonRestRetrievalStrategy daemonRestRetrievalStrategy;
    private final HeadlessBrowserRetrievalStrategy headlessBrowserRetrievalStrategy;

    /**
     * Key: URL of the anime, Value: Instance of the anime including all meta
     * data.
     */
    private LoadingCache<InfoLink, Optional<Anime>> animeEntryCache = null;

    /**
     * Key: URL of the anime, Value: Set of anime urls which are related to the
     * anime url in the key
     */
    private LoadingCache<InfoLink, Set<InfoLink>> relatedAnimeCache = null;

    /**
     * Key: URL of the anime, Value: List of anime urls which are recommended
     * titles to the anime url with their amount of occurence
     */
    private LoadingCache<InfoLink, RecommendationList> recommendationsCache = null;


    @Inject
    public CacheManager(final DaemonRestRetrievalStrategy daemonRestRetrievalStrategy, final HeadlessBrowserRetrievalStrategy headlessBrowserRetrievalStrategy) {
        this.daemonRestRetrievalStrategy = daemonRestRetrievalStrategy;
        this.headlessBrowserRetrievalStrategy = headlessBrowserRetrievalStrategy;
        buildAnimeCache();
        buildRelatedAnimeCache();
        buildRecommendationsCache();
    }


    /**
     * Checks whether a {@link DaemonRestRetrievalStrategy} instance is up an
     * running.
     *
     * @return
     */
    private boolean isDaemonAvailable() {
        return daemonRestRetrievalStrategy.isAvailable();
    }


    @Override
    public Optional<Anime> fetchAnime(final InfoLink infoLink) {
        Optional<Anime> cachedEntry = Optional.empty();

        if (!infoLink.isValid()) {
            return cachedEntry;
        }

        try {
            cachedEntry = animeEntryCache.get(infoLink);

            if (!cachedEntry.isPresent()) {
                log.warn("No Entry for [{}]. Invalidating cache entry and refetching entry.", infoLink);
                animeEntryCache.invalidate(infoLink);
                cachedEntry = animeEntryCache.get(infoLink);
                log.warn("After reinitialising cache entry for [{}] [{}]", infoLink, cachedEntry);
            }
        } catch (final ExecutionException e) {
            log.error("Error fetching anime entry [{}] from cache.", infoLink);
            return Optional.empty();
        }

        return cachedEntry;
    }


    @Override
    public Set<InfoLink> fetchRelatedAnimes(final InfoLink infoLink) {
        Set<InfoLink> ret = Sets.newHashSet();

        if (!infoLink.isValid()) {
            return ret;
        }

        try {
            ret = relatedAnimeCache.get(infoLink);

            if (ret == null || ret.isEmpty()) {
                log.warn("No related animes in cache entry [{}]. Invalidating cache entry and refetching entry.", infoLink);
                relatedAnimeCache.invalidate(infoLink);
                ret = relatedAnimeCache.get(infoLink);
                log.warn("After reinitialising cache entry for [{}] [{}]", infoLink, ret);
            }
        } catch (final ExecutionException e) {
            log.error("Unable to fetch related anime list from cache for [{}]", infoLink);
        }

        return ret;
    }


    @Override
    public RecommendationList fetchRecommendations(final InfoLink infoLink) {
        RecommendationList ret = new RecommendationList();

        if (!infoLink.isValid()) {
            return ret;
        }

        try {
            ret = recommendationsCache.get(infoLink);

            if (ret == null || ret.isEmpty()) {
                log.warn("No recommendations in cache entry [{}]. Invalidating cache entry and refetching entry.", infoLink);
                recommendationsCache.invalidate(infoLink);
                ret = recommendationsCache.get(infoLink);
                log.warn("After reinitialising cache entry for [{}] [{}]", infoLink, ret);
            }
        } catch (final ExecutionException e) {
            log.error("Unable to fetch related anime list from cache for [{}]", infoLink);
        }

        return ret;
    }


    private void buildAnimeCache() {
        animeEntryCache = CacheBuilder.newBuilder().build(new CacheLoader<InfoLink, Optional<Anime>>() {

            @Override
            public Optional<Anime> load(final InfoLink infoLink) throws Exception {
                if (isDaemonAvailable()) {
                    return daemonRestRetrievalStrategy.fetchAnime(infoLink);
                }

                return headlessBrowserRetrievalStrategy.fetchAnime(infoLink);
            }
        });
    }


    private void buildRelatedAnimeCache() {
        relatedAnimeCache = CacheBuilder.newBuilder().build(new CacheLoader<InfoLink, Set<InfoLink>>() {

            @Override
            public Set<InfoLink> load(final InfoLink infoLink) throws Exception {
                if (isDaemonAvailable()) {
                    return daemonRestRetrievalStrategy.fetchRelatedAnimes(infoLink);
                }

                return headlessBrowserRetrievalStrategy.fetchRelatedAnimes(infoLink);
            }
        });
    }


    private void buildRecommendationsCache() {
        recommendationsCache = CacheBuilder.newBuilder().build(new CacheLoader<InfoLink, RecommendationList>() {

            @Override
            public RecommendationList load(final InfoLink infoLink) throws Exception {
                if (isDaemonAvailable()) {
                    return daemonRestRetrievalStrategy.fetchRecommendations(infoLink);
                }

                return headlessBrowserRetrievalStrategy.fetchRecommendations(infoLink);
            }
        });
    }
}