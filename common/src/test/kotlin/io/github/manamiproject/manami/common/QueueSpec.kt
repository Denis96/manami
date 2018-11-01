package io.github.manamiproject.manami.common

import org.assertj.core.api.Assertions.assertThat
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature


object QueueSpec : Spek({

    Feature("Simple FIFO Queue") {
        val queue by memoized { Queue<String>() }

        Scenario("An empty Queue") {
            var resultIsEmpty = false

            When("Checked for emptiness") {
                resultIsEmpty = queue.isEmpty()
            }

            Then("It must return true") {
                assertThat(resultIsEmpty).isTrue()
            }

            var resultSize = -1

            When("Getting size") {
                resultSize = queue.size()
            }

            Then("It must return 0") {
                assertThat(resultSize).isZero()
            }

            var resultPeek: String? = null

            When("Taking a peek by getting the first element without removing it") {
                resultPeek = queue.peek()
            }

            Then("It must return null, because there is no element to peek at") {
                assertThat(resultPeek).isNull()
            }

            var resultDeque: String? = null

            When("Trying to get the first element") {
                resultDeque = queue.dequeue()
            }

            Then("It must return nothing, because there is no element to get") {
                assertThat(resultDeque).isNull()
            }

            val firstElement = "value"

            When("Adding the first element to the queue") {
                queue.enqueue(firstElement)
            }

            Then("This must increase the queue's size to 1") {
                assertThat(queue.size()).isOne()
            }

            And("The queue contains the exact same element, that we just added") {
                assertThat(queue.peek()).isEqualTo(firstElement)
            }
        }

        Scenario("A queue containing two entries") {
            val firstEntry = "initialValue"
            val secondEntry = "anotherValue"

            Given("Two entries added to the queue") {
                queue.enqueue(firstEntry)
                queue.enqueue(secondEntry)
            }

            var resultPeek: String? = null

            When("Peeking at the first element") {
                resultPeek = queue.peek()
            }

            Then("We must get the first element that we added to the queue") {
                assertThat(resultPeek).isEqualTo(firstEntry)
            }

            And("Peeking at the first element must not remove the element. The queue's size must remain 2") {
                assertThat(queue.size()).isEqualTo(2)
            }

            var resultDequeue: String? = null

            When("Taking the first element") {
                resultDequeue = queue.dequeue()
            }

            Then("We get the first element that we added to the queue") {
                assertThat(resultDequeue).isEqualTo(firstEntry)
            }

            When("Checking the size must show that we removed the first element") {
                assertThat(queue.size()).isOne()
            }
        }
    }
})