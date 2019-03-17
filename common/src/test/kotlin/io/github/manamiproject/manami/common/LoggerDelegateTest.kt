package io.github.manamiproject.manami.common

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

object LoggerDelegateTest {

    @Test
    fun `logger must create logback instance`() {
        //when
        val logger = LoggerDelegate().getValue(LoggerDelegateTest::class.java, LoggerDelegateTest::javaClass)

        //then
        assertThat(logger::class.java.toString()).isEqualToIgnoringCase("class ch.qos.logback.classic.Logger")
    }
}