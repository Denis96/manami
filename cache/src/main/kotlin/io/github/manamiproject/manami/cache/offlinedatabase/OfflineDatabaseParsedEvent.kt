package io.github.manamiproject.manami.cache.offlinedatabase

import io.github.manamiproject.manami.entities.Anime
import io.github.manamiproject.manami.entities.InfoLink

data class OfflineDatabaseAnimeEntriesParsedEvent(
        val animeEntries: Map<InfoLink, Anime?>
)