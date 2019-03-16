package io.github.manamiproject.manami.entities

import io.github.manamiproject.manami.entities.AnimeType.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import java.util.*

open class AnimeTypeTest {

    @ParameterizedTest
    @ValueSource(strings = ["Tv", "TV", "tv"])
    fun `find anime type TV by string`(value: String) {
        //when
        val result = AnimeType.findByName(value)

        //then
        assertThat(result).isEqualTo(TV)
    }

    @ParameterizedTest
    @ValueSource(strings = ["Movie", "MOVIE", "movie"])
    fun `find anime type MOVIE by string`(value: String) {
        //when
        val result = AnimeType.findByName(value)

        //then
        assertThat(result).isEqualTo(MOVIE)
    }

    @ParameterizedTest
    @ValueSource(strings = ["Ova", "OVA", "ova"])
    fun `find anime type OVA by string`(value: String) {
        //when
        val result = AnimeType.findByName(value)

        //then
        assertThat(result).isEqualTo(OVA)
    }

    @ParameterizedTest
    @ValueSource(strings = ["Ona", "ONA", "ona"])
    fun `find anime type ONA by string`(value: String) {
        //when
        val result = AnimeType.findByName(value)

        //then
        assertThat(result).isEqualTo(ONA)
    }

    @ParameterizedTest
    @ValueSource(strings = ["Special", "SPECIAL", "special"])
    fun `find anime type SPECIAL by string`(value: String) {
        //when
        val result = AnimeType.findByName(value)

        //then
        assertThat(result).isEqualTo(SPECIAL)
    }

    @ParameterizedTest
    @ValueSource(strings = ["Music", "MUSIC", "music"])
    fun `find anime type MUSIC by string`(value: String) {
        //when
        val result = AnimeType.findByName(value)

        //then
        assertThat(result).isEqualTo(MUSIC)
    }

    @Test
    fun `unable to find anime type for unknown string`() {
        //given
        val typeTv = "akjbfJKdsd"

        //when
        val result = AnimeType.findByName(typeTv)

        //then
        assertThat(result).isNull()
    }
}