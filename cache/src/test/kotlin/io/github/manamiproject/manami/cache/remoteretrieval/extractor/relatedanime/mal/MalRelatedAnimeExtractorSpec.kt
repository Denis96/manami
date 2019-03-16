package io.github.manamiproject.manami.cache.remoteretrieval.extractor.relatedanime.mal

import io.github.manamiproject.manami.cache.remoteretrieval.extractor.recommendations.mal.MalRecommendationsExtractorSpec
import io.github.manamiproject.manami.common.extensions.readAllLines
import io.github.manamiproject.manami.entities.InfoLink
import io.github.manamiproject.manami.entities.NormalizedAnimeBaseUrls
import io.github.manamiproject.manami.entities.NormalizedAnimeBaseUrls.*
import org.assertj.core.api.Assertions.assertThat
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import java.lang.StringBuilder
import java.nio.file.Paths

object MalRelatedAnimeExtractorSpec : Spek({

    val malRelatedAnimeExtractor = MalRelatedAnimeExtractor()

    given("raw html with related anime") {
        val html = StringBuilder()
        Paths.get(MalRecommendationsExtractorSpec::class.java.classLoader.getResource("extractor/mal/related_anime.html").toURI())
        .readAllLines().map(html::append)

        on("extracting related anime") {
            val relatedAnime: MutableSet<InfoLink> = malRelatedAnimeExtractor.extractRelatedAnime(html.toString())

            it("must return a list with three titles") {
                assertThat(relatedAnime).hasSize(3)
            }

            it("must contain parent story") {
                assertThat(relatedAnime).contains(InfoLink("${MAL.url}849"))
            }

            it("must contain sequel") {
                assertThat(relatedAnime).contains(InfoLink("${MAL.url}7311"))
            }

            it("must contain spin-off") {
                assertThat(relatedAnime).contains(InfoLink("${MAL.url}26351"))
            }
        }
    }


    given("raw html without any relations") {
        val html = StringBuilder()
        Paths.get(MalRecommendationsExtractorSpec::class.java.classLoader.getResource("extractor/mal/no_relations.html").toURI())
        .readAllLines().map(html::append)

        on("extracting related anime") {
            val relatedAnime: MutableSet<InfoLink> = malRelatedAnimeExtractor.extractRelatedAnime(html.toString())

            it("must return an empty list") {
                assertThat(relatedAnime).isEmpty()
            }
        }
    }


    given("raw html without any related anime, but one adaption entry") {
        val html = StringBuilder()
        Paths.get(MalRecommendationsExtractorSpec::class.java.classLoader.getResource("extractor/mal/no_related_anime_but_one_adaption.html").toURI())
        .readAllLines().map(html::append)

        on("extracting related anime") {
            val relatedAnime: MutableSet<InfoLink> = malRelatedAnimeExtractor.extractRelatedAnime(html.toString())

            it("must return an empty list") {
                assertThat(relatedAnime).isEmpty()
            }
        }
    }


    given("a valid MAL infolink") {
        val infoLink = InfoLink("${MAL.url}1535")

        on("checking responsibility") {
            val result: Boolean = malRelatedAnimeExtractor.isResponsible(infoLink)

            it("must return true") {
                assertThat(result).isTrue()
            }
        }
    }


    given("a valid ANIDB infolink") {
        val infoLink = InfoLink("${ANIDB.url}4563")

        on("checking responsibility") {
            val result: Boolean = malRelatedAnimeExtractor.isResponsible(infoLink)

            it("must return true") {
                assertThat(result).isFalse()
            }
        }
    }
})