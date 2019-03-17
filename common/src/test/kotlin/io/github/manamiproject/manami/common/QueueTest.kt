package io.github.manamiproject.manami.common

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test


object QueueTest {

    @Test
    fun `taking a peek by getting the first element without removing it returns null on an empty queue`() {
        //given
        val queue = Queue<String>()

        //when
        val result = queue.peek()

        assertThat(queue.isEmpty()).isTrue()
        assertThat(queue.size()).isZero()
        assertThat(result).isNull()
    }

    @Test
    fun `trying to get the first element and removing it from the queue returns null on an empty queue`() {
        //given
        val queue = Queue<String>()

        //when
        val result = queue.dequeue()

        assertThat(queue.isEmpty()).isTrue()
        assertThat(queue.size()).isZero()
        assertThat(result).isNull()
    }

    @Test
    fun `adding an element to an empty queue`() {
        //given
        val queue = Queue<String>()
        val firstElement = "value"

        //when
        queue.enqueue(firstElement)

        assertThat(queue.isEmpty()).isFalse()
        assertThat(queue.size()).isOne()
        assertThat(queue.peek()).isEqualTo(firstElement)
    }

    @Test
    fun `taking a peek at a queue with two entries returns the first element, that has been added to the queue`() {
        //given
        val queue = Queue<String>()
        val firstEntry = "initialValue"
        val secondEntry = "anotherValue"
        queue.enqueue(firstEntry)
        queue.enqueue(secondEntry)

        //when
        val result = queue.peek()

        //then
        assertThat(result).isEqualTo(firstEntry)
        assertThat(queue.size()).isEqualTo(2)
    }

    @Test
    fun `taking the first element of a queue with two entries returns the first element, that has been added to the queue`() {
        //given
        val queue = Queue<String>()
        val firstEntry = "initialValue"
        val secondEntry = "anotherValue"
        queue.enqueue(firstEntry)
        queue.enqueue(secondEntry)

        //when
        val result = queue.dequeue()

        //then
        assertThat(result).isEqualTo(firstEntry)
        assertThat(queue.size()).isOne()
    }
}