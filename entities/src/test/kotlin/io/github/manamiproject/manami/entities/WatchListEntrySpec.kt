package io.github.manamiproject.manami.entities

import org.assertj.core.api.Assertions.assertThat
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature
import java.net.URL

class WatchListEntrySpec : Spek({

    Feature("Convert another MinimalEntry to a WatchListEntry") {

        Scenario("Convert an Anime to a FilterListEntry") {
            lateinit var anime: Anime

            Given("a valid anime") {
                anime = Anime(
                        "Death Note",
                        InfoLink("${NormalizedAnimeBaseUrls.MAL.value}1535"),
                        37,
                        AnimeType.TV,
                        "/anime/series/death_note",
                        URL("https://myanimelist.cdn-dena.com/images/anime/9/9453t.jpg"),
                        URL("https://myanimelist.cdn-dena.com/images/anime/9/9453.jpg")
                )
            }

            lateinit var result: WatchListEntry

            When("creating watch list entry from anime") {
                result = WatchListEntry.valueOf(anime)
            }

            Then("must generate a valid watch list entry") {
                assertThat(result).isNotNull
                assertThat(result.title).isEqualTo(anime.title)
                assertThat(result.infoLink).isEqualTo(anime.infoLink)
                assertThat(result.thumbnail).isEqualTo(anime.thumbnail)
            }
        }

        Scenario("Convert a FilterListEntry to a WatchListEntry") {
            lateinit var filterEntry: FilterListEntry

            Given("a valid filter list entry") {
                filterEntry = FilterListEntry(
                        "Death Note",
                        InfoLink("${NormalizedAnimeBaseUrls.MAL.value}1535"),
                        URL("https://myanimelist.cdn-dena.com/images/anime/9/9453t.jpg")
                )
            }

            lateinit var result: WatchListEntry

            When("creating watch list entry from filter entry") {
                result = WatchListEntry.valueOf(filterEntry)
            }

            Then("must generate a valid watch list entry") {
                assertThat(result).isNotNull()
                assertThat(result.title).isEqualTo(filterEntry.title)
                assertThat(result.infoLink).isEqualTo(filterEntry.infoLink)
                assertThat(result.thumbnail).isEqualTo(filterEntry.thumbnail)
            }
        }
    }
})