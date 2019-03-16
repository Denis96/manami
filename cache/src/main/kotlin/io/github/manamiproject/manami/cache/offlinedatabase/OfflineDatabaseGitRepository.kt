package io.github.manamiproject.manami.cache.offlinedatabase

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import io.github.manamiproject.manami.cache.CacheEntrySource
import io.github.manamiproject.manami.common.*
import io.github.manamiproject.manami.common.extensions.*
import io.github.manamiproject.manami.entities.AnimeType
import io.github.manamiproject.manami.entities.Anime
import io.github.manamiproject.manami.entities.InfoLink
import io.github.manamiproject.manami.entities.NormalizedAnimeBaseUrls.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.eclipse.jgit.api.Git
import org.eclipse.jgit.api.errors.GitAPIException
import org.slf4j.Logger
import java.io.IOException
import java.lang.Exception
import java.nio.file.Path
import java.nio.file.Paths

internal object OfflineDatabaseGitRepository : CacheEntrySource {

    private val log: Logger by LoggerDelegate()
    private val repo: Path = Paths.get("database")
    private val databaseFile: Path = repo.resolve("anime-offline-database.json")
    private val deadEntriesFile: Path = repo.resolve("dead-entries.json")
    private val animeEntries: MutableMap<InfoLink, Anime?> = mutableMapOf()
    private val relatedAnimeEntries: MutableMap<InfoLink, Set<InfoLink>> = mutableMapOf()
    private val gson: Gson = GsonBuilder().apply {
        registerTypeAdapter(AnimeType::class.java, AnimeTypeDeserializer())
    }.create()

    fun cloneOrUpdate() {
        GlobalScope.launch {
            when(repoExists()) {
                true -> updateRepo()
                false -> cloneRepo()
            }

            try {
                readDatabaseFile()
                EventBus.publish(OfflineDatabaseAnimeEntriesParsedEvent(animeEntries))
                readDeadEntriesFile()
                EventBus.publish(OfflineDatabaseRelatedAnimeParsedEvent(relatedAnimeEntries))
            } catch (e: IOException) {
                log.error("Error reading database files: ", e)
            }
        }
    }

    private fun updateRepo() {
        log.info("Updating offline database.")

        try {
            Git.open(repo.toFile())
                    .pull()
                    .call()
        } catch (e: Exception) {
            log.error("Error updating offline database: ", e)
        }
    }

    private fun cloneRepo() {
        log.info("Initially cloning the offline database repository.")

        try {
            repo.createDirectories()
            Git.cloneRepository()
                    .setURI("https://github.com/manami-project/anime-offline-database.git")
                    .setDirectory(repo.toFile())
                    .call()
            log.info("successfully cloned repo")
        } catch (e: GitAPIException) {
            log.error("Error cloning the repository of the offline database: ", e)
        } catch (e: IOException) {
            log.error("Error creating folder for the offline database: ", e)
        }
    }

    private fun readDatabaseFile() {
        if (databaseFile.exists() && databaseFile.isRegularFile()) {
            log.debug("Reading database file")
            val content = String(databaseFile.readAllBytes())
            val animeMetaData: AnimeMetaData = gson.fromJson(content, AnimeMetaData::class.java)

            animeMetaData.data.forEach { entry ->
                entry.sources.forEach { infoLinkUrl ->
                    val infoLink = InfoLink(infoLinkUrl)

                    val anime = Anime(
                            title = entry.title,
                            infoLink = infoLink,
                            numberOfEpisodes = entry.episodes,
                            type = entry.type,
                            location = "/",
                            thumbnail = entry.thumbnail,
                            picture = entry.picture
                    )

                    animeEntries[infoLink] = anime

                    val relatedAnime = entry.relations
                            .filter { url -> url.host == infoLink.url?.host }
                            .map { InfoLink(it) }
                            .toSet()

                    relatedAnimeEntries[infoLink] = relatedAnime
                }
            }
        }
    }

    private fun readDeadEntriesFile() {
        if (deadEntriesFile.exists() && deadEntriesFile.isRegularFile()) {
            log.debug("reading dead-entries file")
            val content = String(deadEntriesFile.readAllBytes())
            val deadEntries: DeadEntries? = gson.fromJson(content, DeadEntries::class.java)

            deadEntries?.mal?.forEach { id ->
                animeEntries[InfoLink("${MAL.url}$id")] = null
            }

            deadEntries?.anidb?.forEach { id ->
                animeEntries[InfoLink("${ANIDB.url}$id")] = null
            }
        }
    }

    override fun getAnimeCacheEntries(): Map<InfoLink, Anime?> {
        return animeEntries
    }

    override fun getRelatedAnimeCacheEntries(): Map<InfoLink, Set<InfoLink>> {
        return relatedAnimeEntries
    }

    private fun repoExists(): Boolean {
        if (!repo.directoryExists()) {
           return false
        }

        return try {
            Git.open(repo.toFile())
                    .status()
                    .call()
            true
        } catch (throwable: Throwable) {
            false
        }
    }
}