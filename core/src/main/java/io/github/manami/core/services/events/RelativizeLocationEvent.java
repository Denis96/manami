package io.github.manami.core.services.events;

import io.github.manami.core.Manami;
import io.github.manami.core.commands.CmdChangeLocation;
import io.github.manami.dto.entities.Anime;

/**
 * @author manami-project
 * @since 2.10.0
 */
public class RelativizeLocationEvent extends AbstractEvent implements ReversibleCommandEvent {

  private final CmdChangeLocation command;


  public RelativizeLocationEvent(final Anime anime, final String newValue, final Manami app) {
    super(anime);
    command = new CmdChangeLocation(anime, newValue, app);
  }

  @Override
  public CmdChangeLocation getCommand() {
    return command;
  }
}
