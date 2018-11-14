package io.github.manamiproject.manami.entities

import org.assertj.core.api.Assertions.assertThat
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature

object UrlValidatorSpec : Spek({

    Feature("Validate a URLs") {

        Scenario("A valid url with http scheme") {
            val url = "http://github.com"

            var resultIsValid = false

            When("Checking if is valid") {
                resultIsValid = UrlValidator.isValid(url)
            }

            Then("Returns true, because the url is valid") {
                assertThat(resultIsValid).isTrue()
            }

            var resultIsNotValid = true

            When("Checking if is not valid") {
                resultIsNotValid = UrlValidator.isNotValid(url)
            }

            Then("Returns false, because the url is valid") {
                assertThat(resultIsNotValid).isFalse()
            }
        }

        Scenario("A valid url with https scheme") {
            val url = "https://github.com"

            var resultIsValid = false

            When("Checking if is valid") {
                resultIsValid = UrlValidator.isValid(url)
            }

            Then("Returns true, because the url is valid") {
                assertThat(resultIsValid).isTrue()
            }

            var resultIsNotValid = true

            When("Checking if is not valid") {
                resultIsNotValid = UrlValidator.isNotValid(url)
            }

            Then("Returns false, because the url is valid") {
                assertThat(resultIsNotValid).isFalse()
            }
        }

        Scenario("A string which is not a valid url") {
            val url = "value.more"

            var resultIsValid = true

            When("Checking if is valid") {
                resultIsValid = UrlValidator.isValid(url)
            }

            Then("Returns false, because the string is not a valid url") {
                assertThat(resultIsValid).isFalse()
            }

            var resultIsNotValid = false

            When("Checking if is not valid") {
                resultIsNotValid = UrlValidator.isNotValid(url)
            }

            Then("Returns true, because the string is not a valid url") {
                assertThat(resultIsNotValid).isTrue()
            }
        }

        Scenario("A valid url with a non valid scheme") {
            val url = "ftp://127.0.0.1"

            var resultIsValid = true

            When("Checking if is valid") {
                resultIsValid = UrlValidator.isValid(url)
            }

            Then("Returns false, because the scheme is not allowed") {
                assertThat(resultIsValid).isFalse()
            }

            var resultIsNotValid = false

            When("Checking if is not valid") {
                resultIsNotValid = UrlValidator.isNotValid(url)
            }

            Then("Returns true, because the scheme is not allowed") {
                assertThat(resultIsNotValid).isTrue()
            }
        }
    }
})