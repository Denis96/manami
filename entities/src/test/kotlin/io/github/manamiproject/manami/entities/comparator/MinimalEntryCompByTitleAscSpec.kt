package io.github.manamiproject.manami.entities.comparator

import io.github.manamiproject.manami.entities.InfoLink
import io.github.manamiproject.manami.entities.NormalizedAnimeBaseUrls
import io.github.manamiproject.manami.entities.NormalizedAnimeBaseUrls.*
import io.github.manamiproject.manami.entities.WatchListEntry
import org.assertj.core.api.Assertions.assertThat
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature
import java.net.URL

class MinimalEntryCompByTitleAscSpec : Spek({

    Feature("Sorting Anime by Title ASC") {

        Scenario("Sort two objects of type WatchListEntry") {
            lateinit var gintama: WatchListEntry
            lateinit var steinsGate: WatchListEntry

            Given("Two different and valid WatchListEntries having titles starting with different letters") {
                gintama = WatchListEntry("Gintama",
                        InfoLink("${MAL.url}28977"),
                        URL("https://myanimelist.cdn-dena.com/images/anime/3/72078t.jpg")
                )

                steinsGate = WatchListEntry(
                        "Steins;Gate",
                        InfoLink("${MAL.url}9253"),
                        URL("https://myanimelist.cdn-dena.com/images/anime/5/73199t.jpg")
                )
            }

            var resultCompareSteinsGateFirstParam = 0

            When("Comparing both with steins;gate as first parameter") {
                resultCompareSteinsGateFirstParam = MinimalEntryCompByTitleAsc.compare(steinsGate, gintama)
            }

            Then("Must return a value > 0 to indicate that [S]teins;gate comes after [G]intama") {
                assertThat(resultCompareSteinsGateFirstParam).isGreaterThan(0)
            }

            var resultCompareGinatamaFirstParam = 0

            When("Comparing both with gintama as first parameter") {
                resultCompareGinatamaFirstParam = MinimalEntryCompByTitleAsc.compare(gintama, steinsGate)
            }

            Then("Must return a value < 0 to indicate that [G]intama comes before [S]teins;gate") {
                assertThat(resultCompareGinatamaFirstParam).isLessThan(0)
            }

            var resultSameEntry = -100

            When("Comparing one entry with itself") {
                resultSameEntry = MinimalEntryCompByTitleAsc.compare(steinsGate, steinsGate)
            }

            Then("Must return 0 to indicate that the titles are equal") {
                assertThat(resultSameEntry).isZero()
            }

            lateinit var deathNote: WatchListEntry
            lateinit var entryWithEmptTitle: WatchListEntry

            Given("A valid WatchListEntry and a WatchListEntry with blank title") {
                deathNote = WatchListEntry(
                        "Death Note",
                        InfoLink("${MAL.url}1535"),
                        URL("https://myanimelist.cdn-dena.com/images/anime/9/9453t.jpg")
                )

                entryWithEmptTitle = WatchListEntry("",
                        InfoLink("${MAL.url}33352"),
                        URL("https://myanimelist.cdn-dena.com/images/anime/11/89398t.jpg")
                )
            }

            var resultEmptyTitleFirstParam = -100

            When("Comparing two entries. First entry's title is empty.") {
                resultEmptyTitleFirstParam = MinimalEntryCompByTitleAsc.compare(entryWithEmptTitle, deathNote)
            }

            Then("Must return 0 to indicate that the titles are not comparable.") {
                assertThat(resultEmptyTitleFirstParam).isZero()
            }

            var resultEmptyTitleSecondParam = -100

            When("Comparing two entries. Second entry's title is empty.") {
                resultEmptyTitleSecondParam = MinimalEntryCompByTitleAsc.compare(deathNote, entryWithEmptTitle)
            }

            Then("Must return 0 to indicate that the titles are not comparable.") {
                assertThat(resultEmptyTitleSecondParam).isZero()
            }
        }
    }
})