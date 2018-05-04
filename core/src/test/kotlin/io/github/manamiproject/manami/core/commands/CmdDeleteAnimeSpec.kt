package io.github.manamiproject.manami.core.commands

import io.github.manamiproject.manami.entities.Anime
import io.github.manamiproject.manami.entities.AnimeType
import io.github.manamiproject.manami.entities.InfoLink
import io.github.manamiproject.manami.entities.NORMALIZED_ANIME_DOMAIN
import io.github.manamiproject.manami.persistence.Persistence
import io.github.manamiproject.manami.persistence.PersistenceFacade
import org.assertj.core.api.Assertions.assertThat
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith


@RunWith(JUnitPlatform::class)
class CmdDeleteAnimeSpec : Spek({

    val persistence: Persistence = PersistenceFacade

    afterEachTest {
        persistence.clearAll()
    }

    given("a command with a valid anime") {

        val anime = Anime(
                "Death Note",
                InfoLink("${NORMALIZED_ANIME_DOMAIN.MAL.value}1535"),
                37,
                AnimeType.TV,
                "/death_note"
        )

        persistence.addAnime(anime)

        val cmdDeleteAnime = CmdDeleteAnime(anime, persistence)

        on("executing command") {
            val result = cmdDeleteAnime.execute()

            it("must return true") {
                assertThat(result).isTrue()
            }

            it("must not exist in persistence") {
                assertThat(persistence.animeEntryExists(anime.infoLink)).isFalse()
            }
        }
    }

    given("a command which has been executed already") {
        val anime = Anime(
                "Death Note",
                InfoLink("${NORMALIZED_ANIME_DOMAIN.MAL.value}1535"),
                37,
                AnimeType.TV,
                "/death_note"
        )

        persistence.addAnime(anime)

        val cmdDeleteAnime = CmdDeleteAnime(anime, persistence)
        cmdDeleteAnime.execute()

        on("undo command") {
            cmdDeleteAnime.undo()

            it("must result in anime being restored") {
                assertThat(persistence.animeEntryExists(anime.infoLink)).isTrue()
            }
        }
    }

    given("a command with an invalid anime") {
        val anime = Anime("    ", InfoLink("some-url"))
        val cmdDeleteAnime = CmdDeleteAnime(anime, persistence)

        on("executing command") {
            val result = cmdDeleteAnime.execute()

            it("must return false") {
                assertThat(result).isFalse()
            }
        }
    }
})