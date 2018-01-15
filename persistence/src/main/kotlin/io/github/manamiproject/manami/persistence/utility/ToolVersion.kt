package io.github.manamiproject.manami.persistence.utility

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.*


/**
 * Contains the current artifact version.
 */
object ToolVersion {

    private val log: Logger = LoggerFactory.getLogger(ToolVersion::class.java)

    fun getToolVersion(): String {
        val propertiesPath = "/META-INF/maven/io.github.manami/persistence/pom.properties"

        try {//TODO: check if we can do this the same way we access resources in tests
            ToolVersion::class.java.getResourceAsStream(propertiesPath).use { stream ->
                val properties = Properties()
                properties.load(stream)

                return properties.getProperty("version")
            }
        } catch (e: Exception) {
            log.error("Could not determine software version: ", e)
        }

        return "unknown"
    }
}