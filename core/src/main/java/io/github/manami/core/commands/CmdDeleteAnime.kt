package io.github.manami.core.commands

import io.github.manami.dto.entities.Anime
import io.github.manami.persistence.PersistenceHandler

/**
 * Command for deleting an entry from animelist.
 *
 * @param anime {@link Anime} that is supposed to be deleted.
 * @param persistence
 */
internal class CmdDeleteAnime(
        private val anime: Anime,
        private val persistence: PersistenceHandler
) : AbstractReversibleCommand(persistence) {

    init {
        oldAnime = anime
    }


    override fun execute(): Boolean {
        oldAnime?.let { return persistence.removeAnime(it) }

        return false
    }


    override fun undo() {
        oldAnime?.let { persistence.addAnime(it) }
    }
}
