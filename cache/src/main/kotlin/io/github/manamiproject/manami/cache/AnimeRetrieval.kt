package io.github.manamiproject.manami.cache

import io.github.manamiproject.manami.dto.entities.Anime
import io.github.manamiproject.manami.dto.entities.InfoLink
import io.github.manamiproject.manami.dto.entities.RecommendationList

interface AnimeRetrieval {

    /**
     * Retrieves an anime.
     *
     * @param infoLink URL of the info link site.
     * @return Optional of an instance of an {@link Anime} corresponding to the link.
     */
    fun fetchAnime(infoLink: InfoLink): Anime?


    /**
     * Fetches all related anime for this specific anime (not recursively).
     *
     * @param infoLink
     * @return A {@link Set} of all related anime or an empty {@link Set}, but
     *         never null.
     */
    fun fetchRelatedAnime(infoLink: InfoLink): Set<InfoLink>


    /**
     * Fetches all recommendations for a specific anime.
     *
     * @param infoLink
     * @return List of recommendations.
     */
    fun fetchRecommendations(infoLink: InfoLink): RecommendationList
}