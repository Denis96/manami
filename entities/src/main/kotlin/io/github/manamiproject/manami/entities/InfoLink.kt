package io.github.manamiproject.manami.entities

import java.net.MalformedURLException
import java.net.URL

data class InfoLink internal constructor (val url: URL?) {

    /**
     * First checks a url is present and then checks if the given url is
     * valid.
     *
     * @return
     */
    fun isValid(): Boolean {
        if (!isPresent()) {
            return false
        }

        return UrlValidator.isValid(url.toString())
    }


    /**
     * Checks if a url is actually present
     *
     * @return
     */
    private fun isPresent(): Boolean {
        return url != null && url.toString().isNotBlank()
    }

    override fun toString() = url?.toString() ?: ""


     companion object  {
        operator fun invoke(url: String): InfoLink {
            return InfoLink(createUrl(url.trim()))
        }

        operator fun invoke(url: URL): InfoLink {
            return InfoLink(url)
        }

        private fun createUrl(url: String): URL? {
            val normalizedUrl: String = normalizeUrl(url)

            return try {
                URL(normalizedUrl)
            } catch (e: MalformedURLException) {
                null
            }
        }

        private fun normalizeUrl(url: String): String {
            return when {
                url.contains(SupportedInfoLinkDomains.MAL.url) -> MyAnimeListNormalizer.normalize(url)
                //TODO: needs normalizer for anidb
                else -> url
            }
        }
    }
}


enum class SupportedInfoLinkDomains(val url: String) {
    MAL("myanimelist.net"),
    ANIDB("anidb.net");
}

enum class NormalizedAnimeBaseUrls(val url: String) {
    MAL("https://${SupportedInfoLinkDomains.MAL.url}/anime/"),
    ANIDB("https://${SupportedInfoLinkDomains.ANIDB.url}/a");
}


private object MyAnimeListNormalizer {

    fun normalize(url: String): String {
        var normalizedUrl = url

        //remove everything after the ID and normalize prefix if necessary
        Regex(".*?/[0-9]+").find(normalizedUrl)?.let { matchResult ->
            normalizedUrl = matchResult.value

            if(!normalizedUrl.startsWith(NormalizedAnimeBaseUrls.MAL.url)) {
                Regex("[0-9]+").find(normalizedUrl)?.let { idMatcherResult ->
                    normalizedUrl = "${NormalizedAnimeBaseUrls.MAL.url}${idMatcherResult.value.replace("/", "")}"
                }
            }
        }

        //correct deep links using .php=id=
        if (normalizedUrl.contains(".php?id=")) {
            normalizedUrl = normalizedUrl.replace(".php?id=", "/")
        }

        return normalizedUrl
    }
}