package io.github.manamiproject.manami.entities

import io.github.manamiproject.manami.entities.AnimeType.TV
import io.github.manamiproject.manami.entities.NormalizedAnimeBaseUrls.MAL
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.net.URL

class WatchListEntryTest {

    @Test
    fun `convert an Anime to a WatchListEntry`() {
        //given
        val anime = Anime(
                title = "Death Note",
                infoLink = InfoLink("${MAL.url}1535"),
                numberOfEpisodes = 37,
                type = TV,
                location = "/anime/series/death_note",
                thumbnail = URL("https://myanimelist.cdn-dena.com/images/anime/9/9453t.jpg"),
                picture = URL("https://myanimelist.cdn-dena.com/images/anime/9/9453.jpg")
        )

        //when
        val result = WatchListEntry.valueOf(anime)

        //given
        assertThat(result).isNotNull
        assertThat(result.title).isEqualTo(anime.title)
        assertThat(result.infoLink).isEqualTo(anime.infoLink)
        assertThat(result.thumbnail).isEqualTo(anime.thumbnail)
    }

    @Test
    fun `convert a FilterListEntry to a WatchListEntry`() {
        //given
        val filterEntry = FilterListEntry(
                title = "Death Note",
                infoLink = InfoLink("${MAL.url}1535"),
                thumbnail = URL("https://myanimelist.cdn-dena.com/images/anime/9/9453t.jpg")
        )

        //when
        val result = WatchListEntry.valueOf(filterEntry)

        //then
        assertThat(result).isNotNull
        assertThat(result.title).isEqualTo(filterEntry.title)
        assertThat(result.infoLink).isEqualTo(filterEntry.infoLink)
        assertThat(result.thumbnail).isEqualTo(filterEntry.thumbnail)
    }
}