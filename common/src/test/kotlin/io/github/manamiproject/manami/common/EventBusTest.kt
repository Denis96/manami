package io.github.manamiproject.manami.common

import com.google.common.eventbus.Subscribe
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

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

object EventBusTest {

    @Test
    fun `register to event bus and receive a test event`() {
        //given
        val eventBusListener = EventBusListener()
        val testEvent = TestEvent("A sample event")

        //when
        EventBus.register(eventBusListener)
        EventBus.publish(testEvent)
        EventBus.unregister(eventBusListener)

        //then
        assertThat(eventBusListener.numberOfMessagesReceived).isOne()
        assertThat(eventBusListener.event).isEqualTo(testEvent)
    }

    @Test
    fun `unregister listener and do not receive further events`() {
        //given
        val eventBusListener = EventBusListener()
        val testEvent = TestEvent("A sample event")
        EventBus.register(eventBusListener)

        //when
        EventBus.unregister(eventBusListener)
        EventBus.publish(testEvent)

        //then
        assertThat(eventBusListener.numberOfMessagesReceived).isZero()
    }
}