package io.github.manamiproject.manami.persistence.importer

import java.nio.file.Path

/**
 * Interface for an importer.
 */
internal interface Importer {

    /**
     * Imports a list from a file and enriches the given list.
     *
     * @param file File
     */
    fun importFile(file: Path)
}