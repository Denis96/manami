package io.github.manami.persistence.inmemory.watchlist;

import io.github.manami.dto.comparator.MinimalEntryComByTitleAsc;
import io.github.manami.dto.entities.AbstractMinimalEntry;
import io.github.manami.dto.entities.Anime;
import io.github.manami.dto.entities.FilterEntry;
import io.github.manami.dto.entities.MinimalEntry;
import io.github.manami.dto.entities.WatchListEntry;
import io.github.manami.persistence.WatchListHandler;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * @author manami project
 * @since 2.7.0
 */
@Named
public class InMemoryWatchListHandler implements WatchListHandler {

    private final Map<String, WatchListEntry> watchList;


    public InMemoryWatchListHandler() {
        watchList = Maps.newConcurrentMap();
    }


    @Override
    public List<WatchListEntry> fetchWatchList() {
        final List<WatchListEntry> sortList = Lists.newArrayList(watchList.values());
        Collections.sort(sortList, new MinimalEntryComByTitleAsc());
        return ImmutableList.copyOf(sortList);
    }


    @Override
    public boolean watchListEntryExists(final String url) {
        return watchList.containsKey(url);
    }


    @Override
    public boolean watchAnime(final MinimalEntry anime) {
        if (anime == null || StringUtils.isBlank(anime.getInfoLink()) || watchList.containsKey(anime.getInfoLink())) {
            return false;
        }

        WatchListEntry entry = null;

        if (anime instanceof Anime || anime instanceof FilterEntry) {
            entry = WatchListEntry.valueOf(anime);
        } else if (anime instanceof WatchListEntry) {
            entry = (WatchListEntry) anime;
        }

        if (entry != null && StringUtils.isBlank(entry.getThumbnail())) {
            entry.setThumbnail(AbstractMinimalEntry.NO_IMG_THUMB);
        }

        if (entry != null) {
            watchList.put(entry.getInfoLink(), entry);
            return true;
        }

        return false;
    }


    @Override
    public boolean removeFromWatchList(final String url) {
        return watchList.remove(url) != null;
    }


    /**
     * @since 2.7.0
     */
    public void clear() {
        watchList.clear();
    }


    @Override
    public void updateOrCreate(final WatchListEntry entry) {
        if (entry != null && StringUtils.isNotBlank(entry.getInfoLink())) {
            watchList.put(entry.getInfoLink(), entry);
        }
    }
}
