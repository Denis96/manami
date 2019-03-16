package io.github.manamiproject.manami.cache.offlinedatabase

import io.github.manamiproject.manami.entities.InfoLink

data class OfflineDatabaseRelatedAnimeParsedEvent(
        val relatedAnimeEntries: Map<InfoLink, Set<InfoLink>>
)