package io.github.manami.cache.strategies.headlessbrowser.extractor.anime;

import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.apache.commons.lang3.StringUtils.normalizeSpace;

import io.github.manami.dto.AnimeType;
import io.github.manami.dto.entities.Anime;
import io.github.manami.dto.entities.InfoLink;
import io.github.manami.dto.entities.MinimalEntry;
import java.net.URL;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * Abstract class for anime site plugins. Their task is to give the possibility to extract information such as the title, episodes, type, etc.
 */
public abstract class AbstractAnimeSitePlugin implements AnimeEntryExtractor {

  /**
   * Content on from which all meta information are being extracted from.
   */
  protected String siteContent;


  @Override
  public Anime extractAnimeEntry(final InfoLink infoLink, final String siteContent) {
    if (!infoLink.isValid() || isBlank(siteContent)) {
      return null;
    }

    this.siteContent = siteContent;
    Anime ret = null;
    trimContent();

    if (isValidInfoLink()) {
      final URL picture = extractPictureLink();
      final URL thumbnail = extractThumbnail();
      final String title = extractTitle();
      final AnimeType type = extractType();
      Integer episodes = 0;
      if (StringUtils.isNumeric(extractEpisodes())) {
        episodes = Integer.valueOf(extractEpisodes());
      }

      if (isNotBlank(title) && type != null && episodes >= 0) {
        ret = new Anime(StringEscapeUtils.unescapeHtml4(title), normalizeInfoLink(infoLink));
        ret.setType(type);
        ret.setEpisodes(episodes);
        ret.setPicture(picture != null ? extractPictureLink() : MinimalEntry.Companion.getNO_IMG());
        ret.setThumbnail(thumbnail != null ? extractThumbnail() : MinimalEntry.Companion.getNO_IMG_THUMB());
      }
    }

    return ret;
  }


  /**
   * Trims the parameter string.
   */
  private void trimContent() {
    siteContent = siteContent.trim();

    // get rid of newlines and doubled whitespaces
    siteContent = siteContent.replaceAll("(\r\n|\n\r|\r|\n|\t)", EMPTY);
    siteContent = normalizeSpace(siteContent);
  }


  /**
   * Checks whether the gathered anime is a valid.
   *
   * @return true if it's a valid info link site.
   */
  protected abstract boolean isValidInfoLink();


  /**
   * Extracts the anime's title.
   *
   * @return Title
   */
  protected abstract String extractTitle();


  /**
   * Extracts the anime's type.
   *
   * @return Type
   */
  protected abstract AnimeType extractType();


  /**
   * Extracts the number of episodes.
   *
   * @return Number of episodes.
   */
  protected abstract String extractEpisodes();


  /**
   * Extracts a URL for a picture.
   *
   * @return Picture (big)
   */
  protected abstract URL extractPictureLink();


  /**
   * Extracts a URL for a picture in thumbnail size.
   *
   * @return Picture (thumbnail size)
   */
  protected abstract URL extractThumbnail();


  @Override
  public InfoLink normalizeInfoLink(final InfoLink infoLink) {
    return infoLink;
  }
}
