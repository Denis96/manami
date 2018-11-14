package io.github.manamiproject.manami.entities

import org.assertj.core.api.Assertions.assertThat
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature

class AnimeTypeSpec : Spek({

    Feature("Find anime by string") {

        Scenario("anime type string 'Tv'") {
            val typeTv = "Tv"

            var resultTvFirstLetterUppercase: AnimeType? = null

            When("finding AnimeType for 'Tv'") {
                resultTvFirstLetterUppercase = AnimeType.findByName(typeTv)
            }

            Then("must return the corresponding AnimeType") {
                assertThat(resultTvFirstLetterUppercase).isEqualTo(AnimeType.TV)
            }

            var resultTvUppercase: AnimeType? = null

            When("finding AnimeType for uppercase string 'TV'") {
                resultTvUppercase = AnimeType.findByName(typeTv.toUpperCase())
            }

            Then("must return the corresponding AnimeType") {
                assertThat(resultTvUppercase).isEqualTo(AnimeType.TV)
            }

            var resultTvLowercase: AnimeType? = null


            When("finding AnimeType for lowercase string 'tv'") {
                resultTvLowercase = AnimeType.findByName(typeTv.toLowerCase())
            }

            Then("must return the corresponding AnimeType") {
                assertThat(resultTvLowercase).isEqualTo(AnimeType.TV)
            }
        }

        Scenario("anime type string 'Movie'") {
            val typeMovie = "Movie"

            var resultMovieFirstLetterUppercase: AnimeType? = null

            When("finding AnimeType for'Movie'") {
                resultMovieFirstLetterUppercase = AnimeType.findByName(typeMovie)
            }

            Then("must return the corresponding AnimeType") {
                assertThat(resultMovieFirstLetterUppercase).isEqualTo(AnimeType.MOVIE)
            }

            var resultMovieUppercase: AnimeType? = null

            When("finding AnimeType for uppercase string 'MOVIE'") {
                resultMovieUppercase = AnimeType.findByName(typeMovie.toUpperCase())
            }

            Then("must return the corresponding AnimeType") {
                assertThat(resultMovieUppercase).isEqualTo(AnimeType.MOVIE)
            }

            var resultMovieLowercase: AnimeType? = null

            When("finding AnimeType for lowercase string 'movie'") {
                resultMovieLowercase = AnimeType.findByName(typeMovie.toLowerCase())
            }

            Then("must return the corresponding AnimeType") {
                assertThat(resultMovieLowercase).isEqualTo(AnimeType.MOVIE)
            }
        }

        Scenario("anime type string 'Ova'") {
            val typeOva = "Ova"

            var resultOvaFirstLetterUppercase: AnimeType? = null

            When("finding AnimeType for'Ova'") {
                resultOvaFirstLetterUppercase = AnimeType.findByName(typeOva)
            }

            Then("must return the corresponding AnimeType") {
                assertThat(resultOvaFirstLetterUppercase).isEqualTo(AnimeType.OVA)
            }

            var resultOvaUppercase: AnimeType? = null

            When("finding AnimeType for uppercase string 'OVA'") {
                resultOvaUppercase = AnimeType.findByName(typeOva.toUpperCase())
            }

            Then("must return the corresponding AnimeType") {
                assertThat(resultOvaUppercase).isEqualTo(AnimeType.OVA)
            }

            var resultOvaLowercase: AnimeType? = null

            When("finding AnimeType for lowercase string 'ova'") {
                resultOvaLowercase = AnimeType.findByName(typeOva.toLowerCase())
            }

            Then("must return the corresponding AnimeType") {
                assertThat(resultOvaLowercase).isEqualTo(AnimeType.OVA)
            }
        }

        Scenario("anime type string 'Ona'") {
            val typeOna = "Ona"

            var resultOnaFirstLetterUppercase: AnimeType? = null

            When("finding AnimeType for'Ona'") {
                resultOnaFirstLetterUppercase = AnimeType.findByName(typeOna)
            }

            Then("must return the corresponding AnimeType") {
                assertThat(resultOnaFirstLetterUppercase).isEqualTo(AnimeType.ONA)
            }

            var resultOnaUppercase: AnimeType? = null

            When("finding AnimeType for uppercase string 'ONA'") {
                resultOnaUppercase = AnimeType.findByName(typeOna.toUpperCase())
            }

            Then("must return the corresponding AnimeType") {
                assertThat(resultOnaUppercase).isEqualTo(AnimeType.ONA)
            }

            var resultOnaLowercase: AnimeType? = null

            When("finding AnimeType for lowercase string 'ona'") {
                resultOnaLowercase = AnimeType.findByName(typeOna.toLowerCase())
            }

            Then("must return the corresponding AnimeType") {
                assertThat(resultOnaLowercase).isEqualTo(AnimeType.ONA)
            }
        }


        Scenario("anime type string 'Special'") {
            val typeSpecial = "Special"

            var resultSpecialFirstLetterUppercase: AnimeType? = null

            When("finding AnimeType for'Special'") {
                resultSpecialFirstLetterUppercase = AnimeType.findByName(typeSpecial)
            }

            Then("must return the corresponding AnimeType") {
                assertThat(resultSpecialFirstLetterUppercase).isEqualTo(AnimeType.SPECIAL)
            }

            var resultSpecialUppercase: AnimeType? = null

            When("finding AnimeType for uppercase string 'SPECIAL'") {
                resultSpecialUppercase = AnimeType.findByName(typeSpecial.toUpperCase())
            }

            Then("must return the corresponding AnimeType") {
                assertThat(resultSpecialUppercase).isEqualTo(AnimeType.SPECIAL)
            }

            var resultSpecialLowercase: AnimeType? = null

            When("finding AnimeType for lowercase string 'special'") {
                resultSpecialLowercase = AnimeType.findByName(typeSpecial.toLowerCase())
            }

            Then("must return the corresponding AnimeType") {
                assertThat(resultSpecialLowercase).isEqualTo(AnimeType.SPECIAL)
            }
        }


        Scenario("anime type string 'Music'") {
            val typeMusic = "Music"

            var resultMusicFirstLetterUppercase: AnimeType? = null

            When("finding AnimeType for'Music'") {
                resultMusicFirstLetterUppercase = AnimeType.findByName(typeMusic)
            }

            Then("must return the corresponding AnimeType") {
                assertThat(resultMusicFirstLetterUppercase).isEqualTo(AnimeType.MUSIC)
            }

            var resultMusicUppercase: AnimeType? = null

            When("finding AnimeType for uppercase string 'MUSIC'") {
                resultMusicUppercase = AnimeType.findByName(typeMusic.toUpperCase())
            }

            Then("must return the corresponding AnimeType") {
                assertThat(resultMusicUppercase).isEqualTo(AnimeType.MUSIC)
            }

            var resultMusicLowercase: AnimeType? = null

            When("finding AnimeType for lowercase string 'music'") {
                resultMusicLowercase = AnimeType.findByName(typeMusic.toLowerCase())
            }

            Then("must return the corresponding AnimeType") {
                assertThat(resultMusicLowercase).isEqualTo(AnimeType.MUSIC)
            }
        }

        Scenario("an invalid anime type") {
            val unknownType = "akjbfJKdsd"

            var resultUnknownTypeFirstLetterUppercase: AnimeType? = null


            When("finding AnimeType by unknown string") {
                resultUnknownTypeFirstLetterUppercase = AnimeType.findByName(unknownType)
            }

            Then("must return null") {
                assertThat(resultUnknownTypeFirstLetterUppercase).isNull()
            }

            var resultUnknownTypeUppercase: AnimeType? = null

            When("finding AnimeType by unknown uppercase string") {
                resultUnknownTypeUppercase = AnimeType.findByName(unknownType.toUpperCase())
            }

            Then("must return the corresponding AnimeType") {
                assertThat(resultUnknownTypeUppercase).isNull()
            }

            var resultUnknownTypeLowercase: AnimeType? = null

            When("finding AnimeType by unknown lowercase string") {
                resultUnknownTypeLowercase = AnimeType.findByName(unknownType.toLowerCase())
            }

            Then("must return the corresponding AnimeType") {
                assertThat(resultUnknownTypeLowercase).isNull()
            }
        }
    }
})
