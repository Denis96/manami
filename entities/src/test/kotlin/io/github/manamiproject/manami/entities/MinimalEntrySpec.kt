package io.github.manamiproject.manami.entities

import org.assertj.core.api.Assertions.assertThat
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature
import java.net.URL

class MinimalEntrySpec : Spek({

    Feature("Check validity of a MinimalEntry") {

        Scenario("A valid MinimalEntry implementation") {
            lateinit var filterListEntry: FilterListEntry

            Given("A valid FilterListEntry") {
                filterListEntry = FilterListEntry(
                        "Death Note",
                        InfoLink("${NormalizedAnimeBaseUrls.MAL.value}1535"),
                        URL("https://myanimelist.cdn-dena.com/images/anime/9/9453t.jpg")
                )
            }

            var resultIsFilterListEntryValid = false

            When("Checking if the FilterListEntry is a valid MinimalEntry") {
                resultIsFilterListEntryValid = filterListEntry.isValid()
            }

            Then("Must return true") {
                assertThat(resultIsFilterListEntryValid).isTrue()
            }

            lateinit var watchListEntry: WatchListEntry

            Given("A valid WatchListEntry") {
                watchListEntry = WatchListEntry("Death Note",
                        InfoLink("${NormalizedAnimeBaseUrls.MAL.value}1535"),
                        URL("https://myanimelist.cdn-dena.com/images/anime/9/9453t.jpg")
                )
            }

            var resultIsWatchListEntryValid = false

            When("Checking if the WatchListEntry is a valid MinimalEntry") {
                resultIsWatchListEntryValid = watchListEntry.isValid()
            }

            Then("Must return true") {
                assertThat(resultIsWatchListEntryValid).isTrue()
            }

            lateinit var anime: Anime

            Given("A valid Anime") {
                anime = Anime(
                        "Death Note",
                        InfoLink("${NormalizedAnimeBaseUrls.MAL.value}1535"),
                        37,
                        AnimeType.TV,
                        "/death_note",
                        URL("https://myanimelist.cdn-dena.com/images/anime/9/9453t.jpg"),
                        URL("https://myanimelist.cdn-dena.com/images/anime/9/9453.jpg")
                )
            }

            var resultIsAnimeValid = false

            When("Checking if the Anime is a valid MinimalEntry") {
                resultIsAnimeValid = anime.isValid()
            }

            Then("Must return true") {
                assertThat(resultIsAnimeValid).isTrue()
            }
        }

        Scenario("A MinimalEntry implementation without a title") {
            lateinit var filterListEntry: FilterListEntry

            Given("A FilterListEntry without title") {
                filterListEntry = FilterListEntry(
                        "",
                        InfoLink("${NormalizedAnimeBaseUrls.MAL.value}1535"),
                        URL("https://myanimelist.cdn-dena.com/images/anime/9/9453t.jpg")
                )
            }

            var resultIsFilterListEntryValid = true

            When("Checking if the FilterListEntry is a valid MinimalEntry") {
                resultIsFilterListEntryValid = filterListEntry.isValid()
            }

            Then("Must return false") {
                assertThat(resultIsFilterListEntryValid).isFalse()
            }

            lateinit var watchListEntry: WatchListEntry

            Given("A WatchListEntry without title") {
                watchListEntry = WatchListEntry(
                        "",
                        InfoLink("${NormalizedAnimeBaseUrls.MAL.value}1535"),
                        URL("https://myanimelist.cdn-dena.com/images/anime/9/9453t.jpg")
                )
            }

            var resultIdWatchListEntryValid = true

            When("Checking if the WatchListEntry is a valid MinimalEntry") {
                resultIdWatchListEntryValid = watchListEntry.isValid()
            }

            Then("Must return false") {
                assertThat(resultIdWatchListEntryValid).isFalse()
            }

            lateinit var anime: Anime

            Given("An Anime without title") {
                anime = Anime(
                        "",
                        InfoLink("${NormalizedAnimeBaseUrls.MAL.value}1535")
                )
            }

            var resultIsAnimeValid = true

            When("Checking if the Anime is a valid MinimalEntry") {
                resultIsAnimeValid = anime.isValid()
            }

            Then("Must return false") {
                assertThat(resultIsAnimeValid).isFalse()
            }
        }

        Scenario("A MinimalEntry implementation with a blank title") {
            lateinit var filterListEntry: FilterListEntry

            Given("A FilterListEntry with a blank title") {
                filterListEntry = FilterListEntry(
                        "   ",
                        InfoLink("${NormalizedAnimeBaseUrls.MAL.value}1535"),
                        URL("https://myanimelist.cdn-dena.com/images/anime/9/9453t.jpg")
                )
            }

            var resultIsFilterListEntryValid = true

            When("checking if the FilterListEntry is a valid MinimalEntry") {
                resultIsFilterListEntryValid = filterListEntry.isValid()
            }

            Then("must return false") {
                assertThat(resultIsFilterListEntryValid).isFalse()
            }

            lateinit var watchListEntry: WatchListEntry

            Given("A WatchListEntry with a blank title") {
                watchListEntry = WatchListEntry(
                        "   ",
                        InfoLink("${NormalizedAnimeBaseUrls.MAL.value}1535"),
                        URL("https://myanimelist.cdn-dena.com/images/anime/9/9453t.jpg")
                )
            }

            var resultIsWatchListEntryValid = true

            When("checking if the WatchListEntry is a valid MinimalEntry") {
                resultIsWatchListEntryValid = watchListEntry.isValid()
            }

            Then("must return false") {
                assertThat(resultIsWatchListEntryValid).isFalse()
            }

            lateinit var anime: Anime

            Given("An Anime with a blank title") {
                anime = Anime(
                        "   ",
                        InfoLink("${NormalizedAnimeBaseUrls.MAL.value}1535")
                )
            }

            var resultIsAnimeValid = true

            When("checking if the Anime is a valid MinimalEntry") {
                resultIsAnimeValid = anime.isValid()
            }

            Then("must return false") {
                assertThat(resultIsAnimeValid).isFalse()
            }
        }

        Scenario("A MinimalEntry implementation without a valid InfoLink") {
            lateinit var filterListEntry: FilterListEntry

            Given("A FilterListEntry without a valid InfoLink") {
                filterListEntry = FilterListEntry(
                        "Death Note",
                        InfoLink(""),
                        URL("https://myanimelist.cdn-dena.com/images/anime/9/9453t.jpg")
                )
            }

            var resultIsFilterListEntryValid = true

            When("Checking if the FilterListEntry is a valid MinimalEntry") {
                resultIsFilterListEntryValid = filterListEntry.isValid()
            }

            Then("Must return false. A FilterListEntry must provide a valid InfoLink, because the InfoLink is it's identifier.") {
                assertThat(resultIsFilterListEntryValid).isFalse()
            }

            lateinit var watchListEntry: WatchListEntry

            Given("A WatchListEntry without a valid InfoLink") {
                watchListEntry = WatchListEntry(
                        "Death Note",
                        InfoLink(""),
                        URL("https://myanimelist.cdn-dena.com/images/anime/9/9453t.jpg")
                )
            }

            var resultIsWatchListEntryValid = true

            When("Checking if the WatchListEntry is a valid MinimalEntry") {
                resultIsWatchListEntryValid = watchListEntry.isValid()
            }

            Then("Must return false. A WatchListEntry must provide a valid InfoLink, because the InfoLink is it's identifier.") {
                assertThat(resultIsWatchListEntryValid).isFalse()
            }

            lateinit var anime: Anime

            Given("An Anime without a valid InfoLink") {
                anime = Anime(
                        "Death Note",
                        InfoLink("")
                )
            }

            var resultIsAnimeValid = false

            When("Checking if the Anime is a valid MinimalEntry") {
                resultIsAnimeValid= anime.isValid()
            }

            Then("Must return true, because Anime have an actual ID as an identifier. They may exist on your HDD, but might not have a corresponding entry on sites like MAL due to their database rules.") {
                assertThat(resultIsAnimeValid).isTrue()
            }
        }
    }
})