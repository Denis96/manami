package io.github.manamiproject.manami.entities

import io.github.manamiproject.manami.entities.NormalizedAnimeBaseUrls.MAL
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class RecommendationListTest {

    @Test
    fun `adding a new recommendation`() {
        //given
        val recommendationList = RecommendationList()
        val infoLink = InfoLink("${MAL.url}1535")
        val recommendation = Recommendation(infoLink, 103)

        //when
        recommendationList.addRecommendation(recommendation)

        //then
        assertThat(recommendationList.isEmpty()).isFalse()
        assertThat(recommendationList.isNotEmpty()).isTrue()
        assertThat(recommendationList.contains(infoLink)).isTrue()
        assertThat(recommendationList.get(infoLink)).isEqualTo(recommendation)
    }

    @Test
    fun `adding a new instance of the same InfoLink with a different amount adds the amount to the existing value`() {
        //given
        val infoLinkUrl = "${MAL.url}1535"
        val initialValue = 5
        val newValue = 15
        val recommendationList = RecommendationList().apply {
            addRecommendation(
                    Recommendation(
                            infoLink = InfoLink(infoLinkUrl),
                            amount = initialValue
                    )
            )
        }

        //when
        recommendationList.addRecommendation(
                Recommendation(
                        infoLink = InfoLink(infoLinkUrl),
                        amount = newValue
                )
        )

        //then
        assertThat(recommendationList).hasSize(1)
        assertThat(recommendationList.get(InfoLink(infoLinkUrl))?.amount).isEqualTo(initialValue + newValue)
    }

    @Test
    fun `checking if the list contains the Recommendation using a different instance`() {
        //given
        val infoLinkUrl = "${MAL.url}1535"
        val recommendationList = RecommendationList().apply {
            addRecommendation(
                    Recommendation(
                            infoLink = InfoLink(infoLinkUrl),
                            amount = 203
                    )
            )
        }

        //when
        val resultContainsRecommendation = recommendationList.contains(
                Recommendation(
                        infoLink = InfoLink(infoLinkUrl),
                        amount = 131
                )
        )

        //then
        assertThat(resultContainsRecommendation).isTrue()
    }

    @Test
    fun `checking if the list contains the recommendations (different instance) using containsAll`() {
        //given
        val infoLinkUrl = "${MAL.url}1535"
        val recommendationList = RecommendationList().apply {
            addRecommendation(
                    Recommendation(
                            infoLink = InfoLink(infoLinkUrl),
                            amount = 203
                    )
            )
        }

        //when
        val resultContainsAllRecommendation = recommendationList.containsAll(
                listOf(
                        Recommendation(
                                infoLink = InfoLink(infoLinkUrl),
                                amount = 131
                        )
                )
        )

        //then
        assertThat(resultContainsAllRecommendation).isTrue()
    }

    @Test
    fun `checking if the list containsAll of recommendations which reside in the list and some which don't`() {
        //given
        val infoLinkUrl = "${MAL.url}1535"
        val initialValue = 5
        val recommendationList = RecommendationList().apply {
            addRecommendation(
                    Recommendation(
                            infoLink = InfoLink(infoLinkUrl),
                            amount = initialValue
                    )
            )
        }

        //when
        val resultContainsAllRecommendations = recommendationList.containsAll(
                listOf(
                        Recommendation(
                                infoLink = InfoLink(infoLinkUrl),
                                amount = initialValue
                        ),
                        Recommendation(
                                infoLink = InfoLink("${MAL.url}35180"),
                                amount = 5
                        )
                )
        )

        //then
        assertThat(resultContainsAllRecommendations).isFalse()
    }
}