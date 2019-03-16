package io.github.manamiproject.manami.entities

import io.github.manamiproject.manami.entities.AnimeType.TV
import io.github.manamiproject.manami.entities.NormalizedAnimeBaseUrls.MAL
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.net.URL

class MinimalEntryTest {

    @Nested
    inner class FilterListEntryTests {

        @Test
        fun `valid FilterListEntry`() {
            //given
            val filterListEntry = FilterListEntry(
                    title = "Death Note",
                    infoLink = InfoLink("${MAL.url}1535"),
                    thumbnail = URL("https://myanimelist.cdn-dena.com/images/anime/9/9453t.jpg")
            )
            //when
            val result = filterListEntry.isValid()

            //then
            assertThat(result).isTrue()
        }

        @Test
        fun `A FilterListEntry without title is not valid`() {
            //given
            val filterListEntry = FilterListEntry(
                    title = "",
                    infoLink = InfoLink("${MAL.url}1535"),
                    thumbnail = URL("https://myanimelist.cdn-dena.com/images/anime/9/9453t.jpg")
            )

            //when
            val result = filterListEntry.isValid()

            //then
            assertThat(result).isFalse()
        }

        @Test
        fun `a FilterListEntry with a blank title is not valid`() {
            //given
            val filterListEntry = FilterListEntry(
                    title = "   ",
                    infoLink = InfoLink("${MAL.url}1535"),
                    thumbnail = URL("https://myanimelist.cdn-dena.com/images/anime/9/9453t.jpg")
            )

            //when
            val result = filterListEntry.isValid()

            //then
            assertThat(result).isFalse()
        }

        @Test
        fun `a FilterListEntry without a valid InfoLink is not valid`() {
            //given
            val filterListEntry = FilterListEntry(
                    title = "Death Note",
                    infoLink = InfoLink(""),
                    thumbnail = URL("https://myanimelist.cdn-dena.com/images/anime/9/9453t.jpg")
            )

            //when
            val result = filterListEntry.isValid()

            //then
            assertThat(result).isFalse()
        }
    }

    @Nested
    inner class WatchListEntryTests {

        @Test
        fun `valid WatchListEntry`() {
            //given
            val watchListEntry = WatchListEntry(
                    title = "Death Note",
                    infoLink = InfoLink("${MAL.url}1535"),
                    thumbnail = URL("https://myanimelist.cdn-dena.com/images/anime/9/9453t.jpg")
            )

            //when
            val result = watchListEntry.isValid()

            //then
            assertThat(result).isTrue()
        }

        @Test
        fun `a WatchListEntry without title is not valid`() {
            //given
            val watchListEntry = WatchListEntry(
                    title = "",
                    infoLink = InfoLink("${MAL.url}1535"),
                    thumbnail = URL("https://myanimelist.cdn-dena.com/images/anime/9/9453t.jpg")
            )

            //when
            val result = watchListEntry.isValid()

            //then
            assertThat(result).isFalse()
        }

        @Test
        fun `a WatchListEntry with a blank title is not valid`() {
            //given
            val watchListEntry = WatchListEntry(
                    title = "   ",
                    infoLink = InfoLink("${MAL.url}1535"),
                    thumbnail = URL("https://myanimelist.cdn-dena.com/images/anime/9/9453t.jpg")
            )

            //when
            val result = watchListEntry.isValid()

            //then
            assertThat(result).isFalse()
        }

        @Test
        fun `a WatchListEntry without a valid InfoLink is not valid`() {
            //given
            val watchListEntry = WatchListEntry(
                    title = "Death Note",
                    infoLink = InfoLink(""),
                    thumbnail = URL("https://myanimelist.cdn-dena.com/images/anime/9/9453t.jpg")
            )

            //when
            val result = watchListEntry.isValid()

            //then
            assertThat(result).isFalse()
        }
    }

    @Nested
    inner class AnimeTests {

        @Test
        fun `valid Anime`() {
            //given
            val anime = Anime(
                    title = "Death Note",
                    infoLink = InfoLink("${MAL.url}1535"),
                    numberOfEpisodes = 37,
                    type = TV,
                    location = "/death_note",
                    thumbnail = URL("https://myanimelist.cdn-dena.com/images/anime/9/9453t.jpg"),
                    picture = URL("https://myanimelist.cdn-dena.com/images/anime/9/9453.jpg")
            )

            //when
            val result = anime.isValid()

            //then
            assertThat(result).isTrue()
        }

        @Test
        fun `an Anime without title is not valid`() {
            //given
            val anime = Anime(
                    title = "",
                    infoLink = InfoLink("${MAL.url}1535"),
                    numberOfEpisodes = 37,
                    type = TV,
                    location = "/death_note",
                    thumbnail = URL("https://myanimelist.cdn-dena.com/images/anime/9/9453t.jpg"),
                    picture = URL("https://myanimelist.cdn-dena.com/images/anime/9/9453.jpg")
            )

            //when
            val result = anime.isValid()

            //then
            assertThat(result).isFalse()
        }

        @Test
        fun `an Anime with a blank title is not valid`() {
            //given
            val anime = Anime(
                    title = "       ",
                    infoLink = InfoLink("${MAL.url}1535"),
                    numberOfEpisodes = 37,
                    type = TV,
                    location = "/death_note",
                    thumbnail = URL("https://myanimelist.cdn-dena.com/images/anime/9/9453t.jpg"),
                    picture = URL("https://myanimelist.cdn-dena.com/images/anime/9/9453.jpg")
            )

            //when
            val result = anime.isValid()

            //then
            assertThat(result).isFalse()
        }

        @Test
        fun `an Anime without a valid InfoLink is not valid`() {
            //given
            val anime = Anime(
                    title = "Death Note",
                    infoLink = InfoLink(""),
                    numberOfEpisodes = 37,
                    type = TV,
                    location = "/death_note",
                    thumbnail = URL("https://myanimelist.cdn-dena.com/images/anime/9/9453t.jpg"),
                    picture = URL("https://myanimelist.cdn-dena.com/images/anime/9/9453.jpg")
            )

            //when
            val result = anime.isValid()

            //then
            assertThat(result).isTrue()
        }
    }
}