package io.github.manamiproject.manami.entities

import io.github.manamiproject.manami.entities.NormalizedAnimeBaseUrls.*
import org.assertj.core.api.Assertions.assertThat
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature

class RecommendationListSpec : Spek({

    Feature("Recommendation list") {

        Scenario("A newly created RecommendationList") {
            val recommendationList by memoized { RecommendationList() }
            var resultIsEmpty = false
            var resultIsNotEmpty = true

            When("Checking if the list is empty") {
                resultIsEmpty = recommendationList.isEmpty()
                resultIsNotEmpty = recommendationList.isNotEmpty()
            }

            Then("Must return true for isEmpty") {
                assertThat(resultIsEmpty).isTrue()
            }

            Then("Must return false for isNotEmpty") {
                assertThat(resultIsNotEmpty).isFalse()
            }

            Then("Must have a size of 0") {
                assertThat(recommendationList).hasSize(0)
            }

            val infoLink = InfoLink("${MAL.url}1535")
            val recommendation = Recommendation(infoLink, 103)

            When("Adding a new recommendation") {
                recommendationList.addRecommendation(recommendation)
            }

            Then("Must return false for isEmpty") {
                assertThat(recommendationList.isEmpty()).isFalse()
            }

            Then("Must return true for isNotEmpty") {
                assertThat(recommendationList.isNotEmpty()).isTrue()
            }

            Then("Must contain the inserted infoLink") {
                assertThat(recommendationList.contains(infoLink)).isTrue()
            }

            Then("Must be returned upon get() call") {
                assertThat(recommendationList.get(infoLink)).isEqualTo(recommendation)
            }

            var resultContainsRecommendation = true

            When("Checking if the list contains a Recommendation") {
                resultContainsRecommendation = recommendationList.contains(
                        Recommendation(
                                InfoLink("${MAL.url}1535"),
                                231
                        )
                )
            }

            Then("It must not contain that Recommendation") {
                assertThat(resultContainsRecommendation).isFalse()
            }
        }

        Scenario("A recommendation list containing one recommendation") {
            val infoLinkUrl = "${MAL.url}1535"
            val initialValue = 5
            val recommendationList by memoized { RecommendationList().apply {
                addRecommendation(
                        Recommendation(
                                InfoLink(infoLinkUrl),
                                initialValue
                        )
                )
            } }

            val newValue = 15

            When("Adding a new instance of the same InfoLink with a different amount") {
                recommendationList.addRecommendation(
                        Recommendation(
                                InfoLink(infoLinkUrl),
                                newValue
                        )
                )
            }

            Then("Must contain only one entry") {
                assertThat(recommendationList).hasSize(1)
            }

            Then("Must contain the infoLink with the sum of both values as amount") {
                assertThat(recommendationList.get(InfoLink(infoLinkUrl))?.amount).isEqualTo(initialValue + newValue)
            }

            var resultContainsRecommendation = false

            When("Checking if the list contains the Recommendation using a different Recommendation instance") {
                resultContainsRecommendation = recommendationList.contains(
                        Recommendation(
                                InfoLink(infoLinkUrl),
                                initialValue
                        )
                )
            }

            Then("Must return true") {
                assertThat(resultContainsRecommendation).isTrue()
            }

            var resultContainsAllRecommendation = false

            When("Checking if the list contains the recommendations (different instance) using containsAll") {
                resultContainsAllRecommendation = recommendationList.containsAll(
                        listOf(
                                Recommendation(
                                        InfoLink(infoLinkUrl),
                                        initialValue
                                )
                        )
                )
            }

            Then("Must return true") {
                assertThat(resultContainsAllRecommendation).isTrue()
            }

            var resultContainsAllRecommendations = true

            When("Checking if the list containsAll of recommendations which reside in the list and some which don't") {
                resultContainsAllRecommendations = recommendationList.containsAll(
                        listOf(
                                Recommendation(
                                        InfoLink(infoLinkUrl),
                                        initialValue
                                ),
                                Recommendation(
                                        InfoLink("${MAL.url}35180"),
                                        5
                                )
                        )
                )
            }

            Then("Must return false") {
                assertThat(resultContainsAllRecommendations).isFalse()
            }
        }
    }
})