package io.github.manamiproject.manami.entities

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

object UrlValidatorTest {

    @Test
    fun `A valid url with http scheme`() {
        //given
        val url = "http://github.com"

        //when
        val resultIsValid = UrlValidator.isValid(url)
        val resultIsNotValid = UrlValidator.isNotValid(url)

        //then
        assertThat(resultIsValid).isTrue()
        assertThat(resultIsNotValid).isFalse()
    }

    @Test
    fun `a valid url with https scheme`() {
        //given
        val url = "https://github.com"

        //when
        val resultIsValid = UrlValidator.isValid(url)
        val resultIsNotValid = UrlValidator.isNotValid(url)

        //then
        assertThat(resultIsValid).isTrue()
        assertThat(resultIsNotValid).isFalse()
    }

    @Test
    fun `a string which is not a valid url`() {
        //given
        val url = "value.more"

        //when
        val resultIsValid = UrlValidator.isValid(url)
        val resultIsNotValid = UrlValidator.isNotValid(url)

        //then
        assertThat(resultIsValid).isFalse()
        assertThat(resultIsNotValid).isTrue()
    }

    @Test
    fun `a valid url with a non valid scheme`() {
        //given
        val url = "ftp://127.0.0.1"

        //when
        val resultIsValid = UrlValidator.isValid(url)
        val resultIsNotValid = UrlValidator.isNotValid(url)

        //then
        assertThat(resultIsValid).isFalse()
        assertThat(resultIsNotValid).isTrue()
    }
}