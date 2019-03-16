package io.github.manamiproject.manami.core.commands

import io.github.manamiproject.manami.core.commands.PersistenceMockCreatorForCommandSpecs.createAnimeListPersistenceMock
import io.github.manamiproject.manami.entities.Anime
import io.github.manamiproject.manami.entities.InfoLink
import io.github.manamiproject.manami.entities.NormalizedAnimeBaseUrls
import io.github.manamiproject.manami.entities.NormalizedAnimeBaseUrls.*
import org.assertj.core.api.Assertions.assertThat
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on


object CmdChangeInfoLinkSpec : Spek({

    given("a command with a valid anime") {
        val newValue = InfoLink("${MAL.url}1535")
        val anime = Anime(
                "Death Note",
                InfoLink("${MAL.url}15355555")
        )

        val persistenceMock = createAnimeListPersistenceMock()

        persistenceMock.addAnime(anime)

        val cmdChangeInfoLink = CmdChangeInfoLink(anime, newValue, persistenceMock)

        on("executing command") {
            val result = cmdChangeInfoLink.execute()

            it("must return true") {
                assertThat(result).isTrue()
            }

            it("must change it's entry in persistence") {
                assertThat(persistenceMock.fetchAnimeList()[0].infoLink).isEqualTo(newValue)
            }
        }
    }

    given("a command with a valid anime which has been executed already") {
        val newValue = InfoLink("${MAL.url}15355555")
        val anime = Anime(
                "Death Note",
                InfoLink("${MAL.url}1535")
        )

        val persistenceMock = createAnimeListPersistenceMock()

        persistenceMock.addAnime(anime)

        val cmdChangeInfoLink = CmdChangeInfoLink(anime, newValue, persistenceMock)
        cmdChangeInfoLink.execute()

        on("undo command") {
            cmdChangeInfoLink.undo()

            it("must restore the initial url in persistence") {
                assertThat(persistenceMock.fetchAnimeList()[0].infoLink).isEqualTo(anime.infoLink)
            }
        }
    }
})