package io.github.manamiproject.manami.entities.comparator

import io.github.manamiproject.manami.entities.InfoLink
import io.github.manamiproject.manami.entities.NormalizedAnimeBaseUrls.MAL
import io.github.manamiproject.manami.entities.WatchListEntry
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class MinimalEntryCompByTitleAscSpec {

    @Test
    fun `two different and valid WatchListEntries having titles starting with different letters - Must return a greater than 0 to indicate that Steinsgate comes after Gintama`() {
        //given
        val gintama = WatchListEntry(
                title = "Gintama",
                infoLink = InfoLink("${MAL.url}28977")
        )

        val steinsGate = WatchListEntry(
                title = "Steins;Gate",
                infoLink = InfoLink("${MAL.url}9253")
        )

        //when
        val result = MinimalEntryCompByTitleAsc.compare(steinsGate, gintama)

        //then
        assertThat(result).isGreaterThan(0)
    }

    @Test
    fun `two different and valid WatchListEntries having titles starting with different letters - Must return a less than 0 to indicate that Steinsgate comes after Gintama`() {
        //given
        val gintama = WatchListEntry(
                title = "Gintama",
                infoLink = InfoLink("${MAL.url}28977")
        )

        val steinsGate = WatchListEntry(
                title = "Steins;Gate",
                infoLink = InfoLink("${MAL.url}9253")
        )

        //when
        val result = MinimalEntryCompByTitleAsc.compare(gintama, steinsGate)

        //then
        assertThat(result).isLessThan(0)
    }

    @Test
    fun `comparing one entry with itself - Must return 0 to indicate that the titles are equal`() {
        //given
        val steinsGate = WatchListEntry(
                title = "Steins;Gate",
                infoLink = InfoLink("${MAL.url}9253")
        )

        //when
        val result = MinimalEntryCompByTitleAsc.compare(steinsGate, steinsGate)

        //then
        assertThat(result).isZero()
    }

    @Test
    fun `comparing two entries - First entry's title is empty - Must return 0 to indicate that the titles are not comparable`() {
        //given
        val deathNote = WatchListEntry(
                title = "Death Note",
                infoLink = InfoLink("${MAL.url}1535")
        )

        val entryWithEmptTitle = WatchListEntry(
                title = "",
                infoLink = InfoLink("${MAL.url}33352")
        )

        //when
        val result = MinimalEntryCompByTitleAsc.compare(entryWithEmptTitle, deathNote)

        //then
        assertThat(result).isZero()
    }

    @Test
    fun `comparing two entries - Second entry's title is empty - Must return 0 to indicate that the titles are not comparable`() {
        //given
        val deathNote = WatchListEntry(
                title = "Death Note",
                infoLink = InfoLink("${MAL.url}1535")
        )

        val entryWithEmptTitle = WatchListEntry(
                title = "",
                infoLink = InfoLink("${MAL.url}33352")
        )

        //when
        val result = MinimalEntryCompByTitleAsc.compare(entryWithEmptTitle, deathNote)

        //then
        assertThat(result).isZero()
    }
}