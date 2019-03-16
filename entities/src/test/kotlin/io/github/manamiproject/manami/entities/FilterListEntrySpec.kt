package io.github.manamiproject.manami.entities

import io.github.manamiproject.manami.entities.NormalizedAnimeBaseUrls.*
import org.assertj.core.api.Assertions.assertThat
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature
import java.net.URL

class FilterListEntrySpec : Spek({

    Feature("Convert another MinimalEntry to a FilterListEntry") {

        Scenario("Convert an Anime to a FilterListEntry") {
            lateinit var anime: Anime

            Given("A valid Anime with all parameters provided") {
                anime = Anime(
                        "Death Note",
                        InfoLink("${MAL.url}1535"),
                        37,
                        AnimeType.TV,
                        "/anime/series/death_note",
                        URL("https://myanimelist.cdn-dena.com/images/anime/9/9453t.jpg"),
                        URL("https://myanimelist.cdn-dena.com/images/anime/9/9453.jpg")
                )
            }

            lateinit var result: FilterListEntry

            When("Creating FilterListEntry from Anime") {
                result = FilterListEntry.valueOf(anime)
            }

            Then("Must generate a valid FilterListEntry") {
                assertThat(result).isNotNull
                assertThat(result.title).isEqualTo(anime.title)
                assertThat(result.infoLink).isEqualTo(anime.infoLink)
                assertThat(result.thumbnail).isEqualTo(anime.thumbnail)
            }
        }

        Scenario("Convert an WatchListEntry to a FilterListEntry") {
            lateinit var watchListEntry: WatchListEntry

            Given("A valid WatchListEntry with all parameters provided") {
                watchListEntry = WatchListEntry(
                        "Death Note",
                        InfoLink("${MAL.url}1535"),
                        URL("https://myanimelist.cdn-dena.com/images/anime/9/9453t.jpg")
                )
            }

            lateinit var result: FilterListEntry

            When("Creating FilterListEntry entry from WatchListEntry") {
                result = FilterListEntry.valueOf(watchListEntry)
            }

            Then("Must generate a valid watch list entry") {
                assertThat(result).isNotNull
                assertThat(result.title).isEqualTo(watchListEntry.title)
                assertThat(result.infoLink).isEqualTo(watchListEntry.infoLink)
                assertThat(result.thumbnail).isEqualTo(watchListEntry.thumbnail)
            }
        }
    }
})