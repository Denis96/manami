package io.github.manamiproject.manami.entities

import io.github.manamiproject.manami.entities.AnimeType.TV
import io.github.manamiproject.manami.entities.NormalizedAnimeBaseUrls.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.net.URL

class AnimeSpec {

    @Nested
    inner class EpisodeTests {

        @Test
        fun `changing default number of episodes to an invalid value`() {
            //given
            val anime = Anime(
                    title = "Death Note",
                    infoLink = InfoLink("${MAL.url}1535")
            )

            //when
            anime.episodes = -1

            //then
            assertThat(anime.episodes).isZero()
        }

        @Test
        fun `changing valid number of episodes to an invalid value`() {
            //given
            val numberOfEpisodes = 4
            val anime = Anime(
                    title = "Death Note",
                    infoLink = InfoLink("${MAL.url}1535"),
                    numberOfEpisodes = numberOfEpisodes
            )

            //when
            anime.episodes = numberOfEpisodes

            //then
            assertThat(anime.episodes).isEqualTo(numberOfEpisodes)
        }

        @Test
        fun `setting number of episodes to a valid value`() {
            //given
            val numberOfEpisodes = 4
            val anime = Anime(
                    title = "Death Note",
                    infoLink = InfoLink("${MAL.url}1535"),
                    numberOfEpisodes = numberOfEpisodes
            )

            //when
            anime.episodes *= 2

            //then
            assertThat(anime.episodes).isEqualTo(numberOfEpisodes * 2)
        }
    }

    @Nested
    inner class ValidityTests {

        @Test
        fun `an Anime with an empty title is not valid`() {
            //given
            val anime = Anime(
                    title = "",
                    infoLink = InfoLink("${MAL.url}1535")
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
                    title = "        ",
                    infoLink = InfoLink("${MAL.url}1535")
            )

            //when
            val result = anime.isValid()

            //then
            assertThat(result).isFalse()
        }

        @Test
        fun `an Anime with an empty location is not valid`() {
            //given
            val anime = Anime(
                    title = "Death Note",
                    infoLink = InfoLink("${MAL.url}1535"),
                    location = ""
            )

            //when
            val result = anime.isValid()

            //then
            assertThat(result).isFalse()
        }

        @Test
        fun `an Anime with a blank location is not valid`() {
            //given
            val anime = Anime(
                    title = "Death Note",
                    infoLink = InfoLink("${MAL.url}1535"),
                    location = "      "
            )

            //when
            val result = anime.isValid()

            //then
            assertThat(result).isFalse()
        }

        @Test
        fun `valid Anime minimal properties`() {
            //given
            val anime = Anime(
                    title = "Death Note",
                    infoLink = InfoLink("${MAL.url}1535")
            )

            //when
            val result = anime.isValid()

            //then
            assertThat(result).isTrue()
        }

        @Test
        fun `valid Anime all properties set`() {
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
            val result = anime.isValid()

            //then
            assertThat(result).isTrue()
        }
    }
}