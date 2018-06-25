package io.github.manami.persistence.inmemory.filterlist;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newConcurrentMap;
import static io.github.manami.dto.entities.MinimalEntryKt.isValidMinimalEntry;

import com.google.common.collect.ImmutableList;
import io.github.manami.dto.comparator.MinimalEntryComByTitleAsc;
import io.github.manami.dto.entities.Anime;
import io.github.manami.dto.entities.FilterEntry;
import io.github.manami.dto.entities.InfoLink;
import io.github.manami.dto.entities.MinimalEntry;
import io.github.manami.dto.entities.WatchListEntry;
import io.github.manami.persistence.FilterListHandler;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import javax.inject.Named;

@Named
public class InMemoryFilterListHandler implements FilterListHandler {

  private final Map<InfoLink, FilterEntry> filterList;


  public InMemoryFilterListHandler() {
    filterList = newConcurrentMap();
  }


  @Override
  public boolean filterAnime(final MinimalEntry anime) {
    if (!isValidMinimalEntry(anime) || filterList.containsKey(anime.getInfoLink())) {
      return false;
    }

    FilterEntry entry = null;

    if (anime instanceof Anime || anime instanceof WatchListEntry) {
      entry = FilterEntry.valueOf(anime);
    } else if (anime instanceof FilterEntry) {
      entry = (FilterEntry) anime;
    }

    if (entry == null) {
      return false;
    }

    filterList.put(entry.getInfoLink(), entry);
    return true;
  }


  @Override
  public List<FilterEntry> fetchFilterList() {
    final List<FilterEntry> sortList = newArrayList(filterList.values());
    Collections.sort(sortList, new MinimalEntryComByTitleAsc());
    return ImmutableList.copyOf(sortList);
  }


  @Override
  public boolean filterEntryExists(final InfoLink infoLink) {
    return filterList.containsKey(infoLink);
  }


  @Override
  public boolean removeFromFilterList(final InfoLink infoLink) {
    if (infoLink != null && infoLink.isValid()) {
      return filterList.remove(infoLink) != null;
    }

    return false;
  }

  public void clear() {
    filterList.clear();
  }


  public void updateOrCreate(final FilterEntry entry) {
    if (isValidMinimalEntry(entry)) {
      filterList.put(entry.getInfoLink(), entry);
    }
  }
}
