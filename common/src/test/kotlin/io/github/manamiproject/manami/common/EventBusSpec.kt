package io.github.manamiproject.manami.common

import com.google.common.eventbus.Subscribe
import org.assertj.core.api.Assertions.assertThat
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature


object EventBusSpec : Spek({

    Feature("EventBus") {
        val eventBus by memoized { EventBus }
        lateinit var eventBusListener: EventBusListener
        lateinit var testEvent: TestEvent

        Scenario("The EventBus has no listeners") {

            Given("A sample listener and a test event") {
                eventBusListener = EventBusListener()
                testEvent = TestEvent("A sample event")
            }

            When("register listener") {
                eventBus.register(eventBusListener)
            }

            And("publish an event") {
                eventBus.publish(testEvent)
            }

            Then("exactly one event has been received") {
                assertThat(eventBusListener.numberOfMessagesReceived).isOne()
            }

            And("the event is the same that had been published") {
                assertThat(eventBusListener.event).isEqualTo(testEvent)
            }
        }

        Scenario("The EventBus has one listener") {

            Given("A sample listener already registered at the event bus and a test event") {
                eventBusListener = EventBusListener()
                eventBus.register(eventBusListener)
                testEvent = TestEvent("A sample event")
            }

            When("unregister the listener") {
                eventBus.unregister(eventBusListener)
            }

            And("fire an event") {
                eventBus.publish(testEvent)
            }

            Then("listener must not receive any events") {
                assertThat(eventBusListener.numberOfMessagesReceived).isZero()
            }
        }
    }
})

private data class TestEvent(var message: String)

private class EventBusListener {
    var event: TestEvent? = null
    var numberOfMessagesReceived = 0

    @Subscribe
    fun listen(obj: TestEvent) {
        event = obj
        numberOfMessagesReceived++
    }
}