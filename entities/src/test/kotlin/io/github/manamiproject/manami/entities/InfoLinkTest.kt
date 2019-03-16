package io.github.manamiproject.manami.entities

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.net.URL

class InfoLinkTest {

    @Nested
    inner class GeneralTests {

        @Test
        fun `URL object is same as input url`() {
            //given
            val url = "https://myanimelist.net/anime/1535"
            val infoLink = InfoLink(url)

            //when
            val result = infoLink.url

            //then
            assertThat(result).isEqualTo(URL(url))
        }

        @Test
        fun `toString returns the raw url`() {
            //given
            val url = "https://myanimelist.net/anime/1535"
            val infoLink = InfoLink(url)

            //when
            val result = infoLink.toString()

            //then
            assertThat(result).isEqualTo(url)
        }

        @Test
        fun `two valid InfoLink using the same url are equal`() {
            //given
            val url = "https://myanimelist.net/anime/1535"
            val infoLink = InfoLink(url)

            //when
            val result = infoLink == InfoLink(url)

            //then
            assertThat(result).isTrue()
        }

        @Test
        fun `a valid InfoLink`() {
            //given
            val url = "https://myanimelist.net/anime/1535"
            val infoLink = InfoLink(url)

            //when
            val result = infoLink.isValid()

            //then
            assertThat(result).isTrue()
        }

        @Test
        fun `a blank InfoLink is not valid`() {
            //given
            val infoLink = InfoLink("   ")

            //when
            val result = infoLink.isValid()

            //then
            assertThat(result).isFalse()
        }

        @Test
        fun `checking two distinct and valid InfoLinks for equality must return false`() {
            //given
            val infoLinkUrlA = InfoLink("https://myanimelist.net/anime/1535")
            val infoLinkUrlB = InfoLink("https://myanimelist.net/anime/15")

            //when
            val result = infoLinkUrlA == infoLinkUrlB

            //then
            assertThat(result).isFalse()
        }

        @Test
        fun `checking a valid InfoLink and an invalid InfoLink for equality must return false`() {
            //given
            val infoLinkUrlA = InfoLink("https://myanimelist.net/anime/1535")
            val infoLinkUrlB = InfoLink("    ")

            //when
            val result = infoLinkUrlA == infoLinkUrlB

            //then
            assertThat(result).isFalse()
        }
    }

    @Nested
    inner class MyAnimeListInfoLinkTests {

        @Test
        fun `a valid MAL url with query parameter for id must be normalized`() {
            //given
            val url = "https://myanimelist.net/anime.php?id=1535"

            //when
            val result = InfoLink(url)

            //then
            assertThat(result.toString()).isEqualTo("https://myanimelist.net/anime/1535")
        }

        @Test
        fun `a valid MAL url with search query must be normalized`() {
            //given
            val url = "https://myanimelist.net/anime/1535/Death_Note?q=death%20note"

            //when
            val result = InfoLink(url)

            //then
            assertThat(result.toString()).isEqualTo("https://myanimelist.net/anime/1535")
        }

        @Test
        fun `a valid default MAL url with SEO title in it must be normalized`() {
            //given
            val url = "https://myanimelist.net/anime/1535/Death_Note"

            //when
            val result = InfoLink(url)

            //then
            assertThat(result.toString()).isEqualTo("https://myanimelist.net/anime/1535")
        }

        @Test
        fun `a valid MAL url which has already been normalized will be returned as is`() {
            //given
            val url = "https://myanimelist.net/anime/1535"

            //when
            val result = InfoLink(url)

            //then
            assertThat(result.toString()).isEqualTo("https://myanimelist.net/anime/1535")
        }

        @Test
        fun `a valid MAL url which has already been normalized, but wuth http instead of https must be normalized`() {
            //given
            val url = "http://myanimelist.net/anime/1535"

            //when
            val result = InfoLink(url)

            //then
            assertThat(result.toString()).isEqualTo("https://myanimelist.net/anime/1535")
        }

        @Test
        fun `invalid MAL InfoLink URL must be returned as is`() {
            //given
            val url = "https://myanimelist.net/news?_location=mal_h_m"

            //when
            val result = InfoLink(url)

            //then
            assertThat(result.toString()).isEqualTo(url)
        }

        @Test
        fun `a valid default MAL url with SEO title in it and blanks at the beginning and at the end must be normalized`() {
            //given
            val url = "https://myanimelist.net/anime/1535/Death_Note"

            //when
            val result = InfoLink(url)

            //then
            assertThat(result.toString()).isEqualTo("https://myanimelist.net/anime/1535")
        }
    }
}