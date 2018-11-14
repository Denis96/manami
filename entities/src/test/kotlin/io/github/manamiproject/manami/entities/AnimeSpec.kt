package io.github.manamiproject.manami.entities

import org.assertj.core.api.Assertions.assertThat
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature

class AnimeSpec : Spek({

    Feature("Creating an Anime") {

        Scenario("Changing the number of episodes of a valid Anime with default number of episodes to an invalid value") {
            lateinit var anime: Anime

            Given("A valid Anime created using mandatory parameter only.") {
                anime = Anime("Death Note", InfoLink("${NormalizedAnimeBaseUrls.MAL.value}1535"))
            }

            When("Setting the amount of episodes to an invalid value") {
                anime.episodes = -1
            }

            Then("Must lead to episodes remain on default value 0") {
                assertThat(anime.episodes).isZero()
            }

            var resultIsValid = false

            When("Checking if the Anime is valid") {
                resultIsValid = anime.isValid()
            }

            Then("Must return true, because Anime is still valid") {
                assertThat(resultIsValid).isTrue()
            }
        }

        Scenario("Changing the number of episodes of a valid Anime with a number of episodes > 0") {
            lateinit var anime: Anime

            Given("A valid Anime with 4 episodes") {
                anime = Anime("Death Note", InfoLink("${NormalizedAnimeBaseUrls.MAL.value}1535"), 4)
            }

            When("Setting the number of episodes to an invalid value") {
                anime.episodes = -1
            }

            Then("Number of episodes must not change") {
                assertThat(anime.episodes).isEqualTo(4)
            }

            When("Setting the number of episodes to a valid value") {
                anime.episodes *= 2
            }

            Then("Number of episodes must change to the new value.") {
                assertThat(anime.episodes).isEqualTo(8)
            }
        }


        Scenario("An Anime with an empty title") {
            val anime = Anime("", InfoLink("${NormalizedAnimeBaseUrls.MAL.value}1535"))

            var resultIsValid = true

            When("Checking if the Anime is valid") {
                resultIsValid = anime.isValid()
            }

            Then("Must return false, because a title cannot be empty") {
                assertThat(resultIsValid).isFalse()
            }
        }


        Scenario("An Anime with a blank title") {
            val anime = Anime("     ", InfoLink("${NormalizedAnimeBaseUrls.MAL.value}1535"))

            var resultIsValid = true

            When("Checking if the Anime is valid") {
                resultIsValid = anime.isValid()
            }

            Then("Must return false, because a title cannot solely consist of whitespaces") {
                assertThat(resultIsValid).isFalse()
            }
        }


        Scenario("An Anime with an empty location") {
            val anime = Anime("Death Note", InfoLink("${NormalizedAnimeBaseUrls.MAL.value}1535")).apply {
                location = ""
            }

            var resultIsValid = true

            When("Checking if the Anime is valid") {
                resultIsValid = anime.isValid()
            }

            Then("Must return false, because a location cannot be empty") {
                assertThat(resultIsValid).isFalse()
            }
        }


        Scenario("An Anime with a blank location") {
            val anime = Anime("Death Note", InfoLink("${NormalizedAnimeBaseUrls.MAL.value}1535")).apply {
                location = "   "
            }

            var resultIsValid = true

            When("Checking if the Anime is valid") {
                resultIsValid = anime.isValid()
            }

            Then("Must return false, because a location cannot solely consist of whitespaces") {
                assertThat(resultIsValid).isFalse()
            }
        }
    }
})