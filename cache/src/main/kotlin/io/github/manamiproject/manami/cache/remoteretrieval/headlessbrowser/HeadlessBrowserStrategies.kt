package io.github.manamiproject.manami.cache.remoteretrieval.headlessbrowser

import io.github.manamiproject.manami.cache.remoteretrieval.headlessbrowser.strategies.SimpleUrlConnectionStrategy
import io.github.manamiproject.manami.entities.InfoLink
import io.github.manamiproject.manami.entities.NormalizedAnimeBaseUrls
import io.github.manamiproject.manami.entities.NormalizedAnimeBaseUrls.*

object HeadlessBrowserStrategies {

    fun getHeadlessBrowserFor(infoLink: InfoLink): HeadlessBrowser? {
        return when {
            infoLink.toString().startsWith(MAL.url) -> SimpleUrlConnectionStrategy()
            infoLink.toString().startsWith(ANIDB.url) -> SimpleUrlConnectionStrategy()
            else -> null
        }
    }
}