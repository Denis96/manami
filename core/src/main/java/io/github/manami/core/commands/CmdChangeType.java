package io.github.manami.core.commands;

import io.github.manami.core.Manami;
import io.github.manami.dto.AnimeType;
import io.github.manami.dto.entities.Anime;

/**
 * Command for changing the type.
 */
public class CmdChangeType extends AbstractReversibleCommand {

  /**
   * Constructor
   *
   * @param anime Anime to change
   * @param newValue The new value.
   * @param application Instance of the application which reveals access to the persistence functionality.
   */
  public CmdChangeType(final Anime anime, final AnimeType newValue, final Manami application) {
    app = application;
    oldAnime = anime;
    newAnime = new Anime(
        oldAnime.getTitle(),
        oldAnime.getInfoLink(),
        oldAnime.getEpisodes(),
        oldAnime.getType(),
        oldAnime.getLocation(),
        oldAnime.getThumbnail(),
        oldAnime.getPicture(),
        oldAnime.getId()
    );
    newAnime.setType(newValue);
  }
}
