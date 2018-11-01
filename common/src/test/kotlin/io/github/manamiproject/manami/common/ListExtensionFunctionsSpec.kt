package io.github.manamiproject.manami.common

import io.github.manamiproject.manami.common.extensions.randomizeOrder
import org.assertj.core.api.Assertions.assertThat
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature

object ListExtensionFunctionsSpec : Spek({

    Feature("Randomize list") {

        Scenario("randomize an ordered list") {
            lateinit var list: List<Int>

            Given("A list of ordered items") {
                list = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9)
            }

            lateinit var result:  List<Int>

            When("applying randomizer") {
                result = list.randomizeOrder()
            }

            Then("must not have the same order as before") {
                assertThat(result).isNotEqualTo(list)
            }
        }
    }
})