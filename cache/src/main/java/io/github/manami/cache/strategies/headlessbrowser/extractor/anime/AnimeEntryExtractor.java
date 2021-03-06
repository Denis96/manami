package io.github.manami.cache.strategies.headlessbrowser.extractor.anime;

import io.github.manami.cache.strategies.headlessbrowser.extractor.AnimeExtractor;
import io.github.manami.dto.entities.Anime;
import io.github.manami.dto.entities.InfoLink;

/**
 * Extracts an {@link Anime} from a given prior downloaded website.
 */
public interface AnimeEntryExtractor extends AnimeExtractor {

    /**
     * Returns an instance of the requested {@link Anime} or null in case of an
     * invalid link.
     *
     * @param infoLink infoLink
     * @param sitecontent Content of the info link website.
     * @return Object of type {@link Anime} with every information or null in
     *         case of an invalid link.
     */
    Anime extractAnimeEntry(InfoLink infoLink, String sitecontent);
}
