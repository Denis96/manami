package io.github.manamiproject.manami.common

import io.github.manamiproject.manami.common.extensions.randomizeOrder
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

object ListExtensionFunctionsTest {

    @Test
    fun `randomize an ordered list`() {
        //given
        val list = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9)

        //when
        val result = list.randomizeOrder()

        //then
        assertThat(result).isNotEqualTo(list)
    }
}