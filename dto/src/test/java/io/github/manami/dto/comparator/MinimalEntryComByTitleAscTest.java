package io.github.manami.dto.comparator;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

import io.github.manami.dto.entities.WatchListEntry;

public class MinimalEntryComByTitleAscTest {

    @Test(groups = "unitTest")
    public void testFirstOneGreater() {
        // given
        final MinimalEntryComByTitleAsc comparator = new MinimalEntryComByTitleAsc();

        final WatchListEntry steinsGate = new WatchListEntry("Steins;Gate", "http://cdn.myanimelist.net/images/anime/5/73199t.jpg", "http://myanimelist.net/anime/9253");
        final WatchListEntry gintama = new WatchListEntry("Gintama", "http://cdn.myanimelist.net/images/anime/3/72078t.jpg", "http://myanimelist.net/anime/28977");

        // when
        final int result = comparator.compare(steinsGate, gintama);

        // then
        assertEquals(result > 0, true);
    }


    @Test(groups = "unitTest")
    public void testFirstOneLesser() {
        // given
        final MinimalEntryComByTitleAsc comparator = new MinimalEntryComByTitleAsc();

        final WatchListEntry steinsGate = new WatchListEntry("Steins;Gate", "http://cdn.myanimelist.net/images/anime/5/73199t.jpg", "http://myanimelist.net/anime/9253");
        final WatchListEntry gintama = new WatchListEntry("Gintama", "http://cdn.myanimelist.net/images/anime/3/72078t.jpg", "http://myanimelist.net/anime/28977");

        // when
        final int result = comparator.compare(gintama, steinsGate);

        // then
        assertEquals(result < 0, true);
    }


    @Test(groups = "unitTest")
    public void testBothEqual() {
        // given
        final MinimalEntryComByTitleAsc comparator = new MinimalEntryComByTitleAsc();

        final WatchListEntry steinsGate = new WatchListEntry("Steins;Gate", "http://cdn.myanimelist.net/images/anime/5/73199t.jpg", "http://myanimelist.net/anime/9253");

        // when
        final int result = comparator.compare(steinsGate, steinsGate);

        // then
        assertEquals(result, 0);
    }


    @Test(groups = "unitTest")
    public void testFirstParameterNull() {
        // given
        final MinimalEntryComByTitleAsc comparator = new MinimalEntryComByTitleAsc();

        final WatchListEntry steinsGate = new WatchListEntry("Steins;Gate", "http://cdn.myanimelist.net/images/anime/5/73199t.jpg", "http://myanimelist.net/anime/9253");

        // when
        final int result = comparator.compare(null, steinsGate);

        // then
        assertEquals(result, 0);
    }


    @Test(groups = "unitTest")
    public void testSecondParameterNull() {
        // given
        final MinimalEntryComByTitleAsc comparator = new MinimalEntryComByTitleAsc();

        final WatchListEntry steinsGate = new WatchListEntry("Steins;Gate", "http://cdn.myanimelist.net/images/anime/5/73199t.jpg", "http://myanimelist.net/anime/9253");

        // when
        final int result = comparator.compare(steinsGate, null);

        // then
        assertEquals(result, 0);
    }


    @Test(groups = "unitTest")
    public void testFirstParameterTitleNullOrEmpty() {
        // given
        final MinimalEntryComByTitleAsc comparator = new MinimalEntryComByTitleAsc();

        final WatchListEntry steinsGate = new WatchListEntry("Steins;Gate", "http://cdn.myanimelist.net/images/anime/5/73199t.jpg", "http://myanimelist.net/anime/9253");
        final WatchListEntry gintama = new WatchListEntry("Gintama", "http://cdn.myanimelist.net/images/anime/3/72078t.jpg", "http://myanimelist.net/anime/28977");
        gintama.setTitle("");

        // when
        final int result = comparator.compare(gintama, steinsGate);

        // then
        assertEquals(result, 0);
    }


    @Test(groups = "unitTest")
    public void testSecondParameterTitleNullOrEmpty() {
        // given
        final MinimalEntryComByTitleAsc comparator = new MinimalEntryComByTitleAsc();

        final WatchListEntry steinsGate = new WatchListEntry("Steins;Gate", "http://cdn.myanimelist.net/images/anime/5/73199t.jpg", "http://myanimelist.net/anime/9253");
        final WatchListEntry gintama = new WatchListEntry("Gintama", "http://cdn.myanimelist.net/images/anime/3/72078t.jpg", "http://myanimelist.net/anime/28977");
        steinsGate.setTitle("");

        // when
        final int result = comparator.compare(gintama, steinsGate);

        // then
        assertEquals(result, 0);
    }
}
