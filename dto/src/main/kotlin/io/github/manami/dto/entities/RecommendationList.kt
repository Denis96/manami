package io.github.manami.dto.entities

class RecommendationList {

    private val recommendations: MutableMap<InfoLink, Recommendation> = mutableMapOf()


    fun addRecommendation(recommendation: Recommendation): Recommendation? {
        var value: Recommendation = recommendation

        if (containsKey(recommendation.infoLink)) {
            val amountInList = 0
            recommendations[recommendation.infoLink]?.let { value.amount }

            val newAmount: Int = value.amount
            value = Recommendation(recommendation.infoLink, amountInList + newAmount)
        }

        return recommendations.put(recommendation.infoLink, value)
    }


    fun asList() = listOf(recommendations.entries)


    fun isEmpty() = recommendations.isEmpty()


    fun isNotEmpty() = !recommendations.isEmpty()


    fun containsKey(infoLink: InfoLink) = recommendations.containsKey(infoLink)

    fun get(infoLink: InfoLink) = recommendations[infoLink]
}