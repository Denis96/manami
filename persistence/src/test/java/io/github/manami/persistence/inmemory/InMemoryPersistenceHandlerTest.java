package io.github.manami.persistence.inmemory;

import static com.google.common.collect.Lists.newArrayList;
import static io.github.manami.dto.TestConst.UNIT_TEST_GROUP;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.manami.dto.AnimeType;
import io.github.manami.dto.entities.Anime;
import io.github.manami.dto.entities.FilterEntry;
import io.github.manami.dto.entities.InfoLink;
import io.github.manami.dto.entities.MinimalEntry;
import io.github.manami.dto.entities.WatchListEntry;
import io.github.manami.persistence.inmemory.animelist.InMemoryAnimeListHandler;
import io.github.manami.persistence.inmemory.filterlist.InMemoryFilterListHandler;
import io.github.manami.persistence.inmemory.watchlist.InMemoryWatchListHandler;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class InMemoryPersistenceHandlerTest {

  private InMemoryPersistenceHandler inMemoryPersistenceHandler;


  @BeforeMethod
  public void setUp() throws IOException {
    inMemoryPersistenceHandler = new InMemoryPersistenceHandler(new InMemoryAnimeListHandler(),
        new InMemoryFilterListHandler(), new InMemoryWatchListHandler());
  }


  @Test(groups = UNIT_TEST_GROUP)
  public void testFilterAnimeIsEntryWithoutTitle() {
    // given
    final FilterEntry entry = new FilterEntry(EMPTY,
        new InfoLink("http://myanimelist.net/anime/1535"));

    // when
    final boolean result = inMemoryPersistenceHandler.filterAnime(entry);

    // then
    assertThat(result).isFalse();
    assertThat(inMemoryPersistenceHandler.fetchFilterList().size()).isEqualTo(0);
  }


  @Test(groups = UNIT_TEST_GROUP)
  public void testFilterAnimeIsEntryWithoutInfoLink() {
    // given
    final FilterEntry entry = new FilterEntry("Death Note", new InfoLink(EMPTY));

    // when
    final boolean result = inMemoryPersistenceHandler.filterAnime(entry);

    // then
    assertThat(result).isFalse();
    assertThat(inMemoryPersistenceHandler.fetchFilterList().size()).isEqualTo(0);
  }


  @Test(groups = UNIT_TEST_GROUP)
  public void testFilterAnimeIsEntryWithoutThumbnail() {
    // given
    final FilterEntry entry = new FilterEntry("Death Note",
        new InfoLink("http://myanimelist.net/anime/1535"));

    // when
    final boolean result = inMemoryPersistenceHandler.filterAnime(entry);

    // then
    assertThat(result).isTrue();
    assertThat(inMemoryPersistenceHandler.fetchFilterList().size()).isEqualTo(1);
  }


  @Test(groups = UNIT_TEST_GROUP)
  public void testFilterAnimeIsFullEntry() throws MalformedURLException {
    // given
    final FilterEntry entry = new FilterEntry(
        "Death Note",
        new InfoLink("http://myanimelist.net/anime/1535"),
        new URL("http://cdn.myanimelist.net/images/anime/9/9453t.jpg")
    );

    // when
    final boolean result = inMemoryPersistenceHandler.filterAnime(entry);

    // then
    assertThat(result).isTrue();
    assertThat(inMemoryPersistenceHandler.fetchFilterList().size()).isEqualTo(1);
  }


  @Test(groups = UNIT_TEST_GROUP)
  public void testFilterEntryExists() {
    // given
    final InfoLink infoLink = new InfoLink("http://myanimelist.net/anime/1535");
    final FilterEntry entry = new FilterEntry("Death Note", infoLink);
    inMemoryPersistenceHandler.filterAnime(entry);

    // when
    final boolean result = inMemoryPersistenceHandler.filterEntryExists(infoLink);

    // then
    assertThat(result).isTrue();
  }


  @Test(groups = UNIT_TEST_GROUP)
  public void testFilterEntryNotExists() {
    // given

    // when
    final boolean result = inMemoryPersistenceHandler
        .filterEntryExists(new InfoLink("http://myanimelist.net/anime/1535"));

    // then
    assertThat(result).isFalse();
  }


  @Test(groups = UNIT_TEST_GROUP)
  public void testFilterAnimeList() {
    // given
    final FilterEntry entry = new FilterEntry("Death Note",
        new InfoLink("http://myanimelist.net/anime/1535"));
    inMemoryPersistenceHandler.filterAnime(entry);

    // when
    final List<FilterEntry> fetchFilterList = inMemoryPersistenceHandler.fetchFilterList();

    // then
    assertThat(fetchFilterList.size()).isEqualTo(1);
  }


  @Test(groups = UNIT_TEST_GROUP)
  public void testFetchWatchList() {
    // given
    final WatchListEntry entry = new WatchListEntry("Death Note",
        new InfoLink("http://myanimelist.net/anime/1535"));
    inMemoryPersistenceHandler.watchAnime(entry);

    // when
    final List<WatchListEntry> fetchWatchList = inMemoryPersistenceHandler.fetchWatchList();

    // then
    assertThat(fetchWatchList.size()).isEqualTo(1);
  }


  @Test(groups = UNIT_TEST_GROUP)
  public void testRemoveFromFilterListWorks() {
    // given
    final InfoLink infoLink = new InfoLink("http://myanimelist.net/anime/1535");
    final FilterEntry entry = new FilterEntry("Death Note", infoLink);
    inMemoryPersistenceHandler.filterAnime(entry);

    // when
    final boolean result = inMemoryPersistenceHandler.removeFromFilterList(infoLink);

    // then
    assertThat(result).isTrue();
    assertThat(inMemoryPersistenceHandler.fetchFilterList().isEmpty()).isTrue();
  }


  @Test(groups = UNIT_TEST_GROUP)
  public void testWatchListEntryExists() {
    // given
    final InfoLink infoLink = new InfoLink("http://myanimelist.net/anime/1535");
    final WatchListEntry entry = new WatchListEntry("Death Note", infoLink);
    inMemoryPersistenceHandler.watchAnime(entry);

    // when
    final boolean result = inMemoryPersistenceHandler.watchListEntryExists(infoLink);

    // then
    assertThat(result).isTrue();
  }


  @Test(groups = UNIT_TEST_GROUP)
  public void testWatchListEntryNotExists() {
    // given

    // when
    final boolean result = inMemoryPersistenceHandler
        .watchListEntryExists(new InfoLink("http://myanimelist.net/anime/1535"));

    // then
    assertThat(result).isFalse();
  }


  @Test(groups = UNIT_TEST_GROUP)
  public void testWatchAnimeIsEntryWithoutTitle() {
    // given
    final WatchListEntry entry = new WatchListEntry(EMPTY,
        new InfoLink("http://myanimelist.net/anime/1535"));

    // when
    final boolean result = inMemoryPersistenceHandler.watchAnime(entry);

    // then
    assertThat(result).isFalse();
    assertThat(inMemoryPersistenceHandler.fetchWatchList().size()).isEqualTo(0);
  }


  @Test(groups = UNIT_TEST_GROUP)
  public void testWatchAnimeIsEntryWithoutInfoLink() {
    // given
    final WatchListEntry entry = new WatchListEntry("Death Note", new InfoLink(EMPTY));

    // when
    final boolean result = inMemoryPersistenceHandler.watchAnime(entry);

    // then
    assertThat(result).isFalse();
    assertThat(inMemoryPersistenceHandler.fetchWatchList().size()).isEqualTo(0);
  }


  @Test(groups = UNIT_TEST_GROUP)
  public void testWatchAnimeIsEntryWithoutThumbnail() {
    // given
    final WatchListEntry entry = new WatchListEntry("Death Note",
        new InfoLink("http://myanimelist.net/anime/1535"));

    // when
    final boolean result = inMemoryPersistenceHandler.watchAnime(entry);

    // then
    assertThat(result).isTrue();
    assertThat(inMemoryPersistenceHandler.fetchWatchList().size()).isEqualTo(1);
  }


  @Test(groups = UNIT_TEST_GROUP)
  public void testWatchAnimeIsFullEntry() throws MalformedURLException {
    // given
    final WatchListEntry entry = new WatchListEntry(
        "Death Note",
        new InfoLink("http://myanimelist.net/anime/1535"),
        new URL("http://cdn.myanimelist.net/images/anime/9/9453t.jpg")
    );

    // when
    final boolean result = inMemoryPersistenceHandler.watchAnime(entry);

    // then
    assertThat(result).isTrue();
    assertThat(inMemoryPersistenceHandler.fetchWatchList().size()).isEqualTo(1);
  }


  @Test(groups = UNIT_TEST_GROUP)
  public void testRemoveFromWatchListWorks() {
    // given
    final InfoLink infoLink = new InfoLink("http://myanimelist.net/anime/1535");
    final WatchListEntry entry = new WatchListEntry("Death Note", infoLink);
    inMemoryPersistenceHandler.watchAnime(entry);

    // when
    final boolean result = inMemoryPersistenceHandler.removeFromWatchList(infoLink);

    // then
    assertThat(result).isTrue();
    assertThat(inMemoryPersistenceHandler.fetchWatchList().isEmpty()).isTrue();
  }


  @Test(groups = UNIT_TEST_GROUP)
  public void testaddAnimeIsFullEntry() throws MalformedURLException {
    // given
    final Anime entry = new Anime("Death Note", new InfoLink("http://myanimelist.net/anime/1535"));
    entry.setEpisodes(37);
    entry.setLocation("/death_note");
    entry.setPicture(new URL("http://cdn.myanimelist.net/images/anime/9/9453.jpg"));
    entry.setThumbnail(new URL("http://cdn.myanimelist.net/images/anime/9/9453t.jpg"));
    entry.setType(AnimeType.TV);

    // when
    final boolean result = inMemoryPersistenceHandler.addAnime(entry);

    // then
    assertThat(result).isTrue();
    assertThat(inMemoryPersistenceHandler.fetchAnimeList().size()).isEqualTo(1);
  }


  @Test(groups = UNIT_TEST_GROUP)
  public void testaddAnimeIsEntryWithoutEpisodes() throws MalformedURLException {
    // given
    final Anime entry = new Anime("Death Note", new InfoLink("http://myanimelist.net/anime/1535"));
    entry.setLocation("/death_note");
    entry.setPicture(new URL("http://cdn.myanimelist.net/images/anime/9/9453.jpg"));
    entry.setThumbnail(new URL("http://cdn.myanimelist.net/images/anime/9/9453t.jpg"));
    entry.setType(AnimeType.TV);

    // when
    final boolean result = inMemoryPersistenceHandler.addAnime(entry);

    // then
    assertThat(result).isTrue();
    assertThat(inMemoryPersistenceHandler.fetchAnimeList().size()).isEqualTo(1);
  }


  @Test(groups = UNIT_TEST_GROUP)
  public void testaddAnimeIsEntryWithoutInfoLink() throws MalformedURLException {
    // given
    final Anime entry = new Anime("Death Note", new InfoLink(""));
    entry.setEpisodes(37);
    entry.setLocation("/death_note");
    entry.setPicture(new URL("http://cdn.myanimelist.net/images/anime/9/9453.jpg"));
    entry.setThumbnail(new URL("http://cdn.myanimelist.net/images/anime/9/9453t.jpg"));
    entry.setType(AnimeType.TV);

    // when
    final boolean result = inMemoryPersistenceHandler.addAnime(entry);

    // then
    assertThat(result).isTrue();
    assertThat(inMemoryPersistenceHandler.fetchAnimeList().size()).isEqualTo(1);
  }


  @Test(groups = UNIT_TEST_GROUP)
  public void testaddAnimeIsEntryWithoutLocation() throws MalformedURLException {
    // given
    final Anime entry = new Anime("Death Note", new InfoLink("http://myanimelist.net/anime/1535"));
    entry.setEpisodes(37);
    entry.setPicture(new URL("http://cdn.myanimelist.net/images/anime/9/9453.jpg"));
    entry.setThumbnail(new URL("http://cdn.myanimelist.net/images/anime/9/9453t.jpg"));
    entry.setType(AnimeType.TV);

    // when
    final boolean result = inMemoryPersistenceHandler.addAnime(entry);

    // then
    assertThat(result).isFalse();
    assertThat(inMemoryPersistenceHandler.fetchAnimeList().size()).isEqualTo(0);
  }


  @Test(groups = UNIT_TEST_GROUP)
  public void testaddAnimeIsEntryWithoutPicture() throws MalformedURLException {
    // given
    final Anime entry = new Anime("Death Note", new InfoLink("http://myanimelist.net/anime/1535"));
    entry.setEpisodes(37);
    entry.setLocation("/death_note");
    entry.setThumbnail(new URL("http://cdn.myanimelist.net/images/anime/9/9453t.jpg"));
    entry.setType(AnimeType.TV);

    // when
    final boolean result = inMemoryPersistenceHandler.addAnime(entry);

    // then
    assertThat(result).isTrue();
    assertThat(inMemoryPersistenceHandler.fetchAnimeList().size()).isEqualTo(1);
  }


  @Test(groups = UNIT_TEST_GROUP)
  public void testaddAnimeIsEntryWithoutThumbnail() throws MalformedURLException {
    // given
    final Anime entry = new Anime("Death Note", new InfoLink("http://myanimelist.net/anime/1535"));
    entry.setEpisodes(37);
    entry.setLocation("/death_note");
    entry.setPicture(new URL("http://cdn.myanimelist.net/images/anime/9/9453.jpg"));
    entry.setType(AnimeType.TV);

    // when
    final boolean result = inMemoryPersistenceHandler.addAnime(entry);

    // then
    assertThat(result).isTrue();
    assertThat(inMemoryPersistenceHandler.fetchAnimeList().size()).isEqualTo(1);
  }


  @Test(groups = UNIT_TEST_GROUP)
  public void testaddAnimeIsEntryWithoutTitle() throws MalformedURLException {
    // given
    final Anime entry = new Anime("", new InfoLink("http://myanimelist.net/anime/1535"));
    entry.setEpisodes(37);
    entry.setLocation("/death_note");
    entry.setPicture(new URL("http://cdn.myanimelist.net/images/anime/9/9453.jpg"));
    entry.setThumbnail(new URL("http://cdn.myanimelist.net/images/anime/9/9453t.jpg"));
    entry.setType(AnimeType.TV);

    // when
    final boolean result = inMemoryPersistenceHandler.addAnime(entry);

    // then
    assertThat(result).isFalse();
    assertThat(inMemoryPersistenceHandler.fetchAnimeList().size()).isEqualTo(0);
  }


  @Test(
      groups = UNIT_TEST_GROUP,
      description = "Fallback for the type is TV. So in case no AnimeType has been set TV is being used. Therefore the entry will be added."
  )
  public void testaddAnimeIsEntryWithoutType() throws MalformedURLException {
    // given
    final Anime entry = new Anime("Death Note", new InfoLink("http://myanimelist.net/anime/1535"));
    entry.setEpisodes(37);
    entry.setLocation("/death_note");
    entry.setPicture(new URL("http://cdn.myanimelist.net/images/anime/9/9453.jpg"));
    entry.setThumbnail(new URL("http://cdn.myanimelist.net/images/anime/9/9453t.jpg"));

    // when
    final boolean result = inMemoryPersistenceHandler.addAnime(entry);

    // then
    assertThat(result).isTrue();
    assertThat(inMemoryPersistenceHandler.fetchAnimeList().size()).isEqualTo(1);
  }


  @Test(groups = UNIT_TEST_GROUP)
  public void testAnimeEntryExists() throws MalformedURLException {
    // given
    final InfoLink infoLink = new InfoLink("http://myanimelist.net/anime/1535");
    final Anime entry = new Anime("Death Note", infoLink);
    entry.setEpisodes(37);
    entry.setLocation("/death_note");
    entry.setPicture(new URL("http://cdn.myanimelist.net/images/anime/9/9453.jpg"));
    entry.setThumbnail(new URL("http://cdn.myanimelist.net/images/anime/9/9453t.jpg"));
    entry.setType(AnimeType.TV);
    inMemoryPersistenceHandler.addAnime(entry);

    // when
    final boolean result = inMemoryPersistenceHandler.animeEntryExists(infoLink);

    // then
    assertThat(result).isTrue();
  }


  @Test(groups = UNIT_TEST_GROUP)
  public void testAnimeEntryNotExists() {
    // given

    // when
    final boolean result = inMemoryPersistenceHandler
        .animeEntryExists(new InfoLink("http://myanimelist.net/anime/1535"));

    // then
    assertThat(result).isFalse();
  }


  @Test(groups = UNIT_TEST_GROUP)
  public void testAnimeList() throws MalformedURLException {
    // given
    final Anime entry = new Anime("Death Note", new InfoLink("http://myanimelist.net/anime/1535"));
    entry.setEpisodes(37);
    entry.setLocation("/death_note");
    entry.setPicture(new URL("http://cdn.myanimelist.net/images/anime/9/9453.jpg"));
    entry.setThumbnail(new URL("http://cdn.myanimelist.net/images/anime/9/9453t.jpg"));
    entry.setType(AnimeType.TV);
    inMemoryPersistenceHandler.addAnime(entry);

    // when
    final List<Anime> animeList = inMemoryPersistenceHandler.fetchAnimeList();

    // then
    assertThat(animeList.size()).isEqualTo(1);
  }


  @Test(groups = UNIT_TEST_GROUP)
  public void testRemoveFromAnimeListWorks() throws MalformedURLException {
    // given
    final Anime entry = new Anime("Death Note", new InfoLink("http://myanimelist.net/anime/1535"));
    entry.setEpisodes(37);
    entry.setLocation("/death_note");
    entry.setPicture(new URL("http://cdn.myanimelist.net/images/anime/9/9453.jpg"));
    entry.setThumbnail(new URL("http://cdn.myanimelist.net/images/anime/9/9453t.jpg"));
    entry.setType(AnimeType.TV);
    inMemoryPersistenceHandler.addAnime(entry);

    // when
    final boolean result = inMemoryPersistenceHandler.removeAnime(entry.getId());

    // then
    assertThat(result).isTrue();
    assertThat(inMemoryPersistenceHandler.fetchAnimeList().isEmpty()).isTrue();
  }


  @Test(groups = UNIT_TEST_GROUP)
  public void testThatClearAllWorks() throws MalformedURLException {
    // given
    final Anime entry = new Anime("Death Note", new InfoLink("http://myanimelist.net/anime/1535"));
    entry.setEpisodes(37);
    entry.setLocation("/death_note");
    entry.setPicture(new URL("http://cdn.myanimelist.net/images/anime/9/9453.jpg"));
    entry.setThumbnail(new URL("http://cdn.myanimelist.net/images/anime/9/9453t.jpg"));
    entry.setType(AnimeType.TV);
    inMemoryPersistenceHandler.addAnime(entry);

    final FilterEntry filterEntry = new FilterEntry(
        "Gintama",
        new InfoLink("http://myanimelist.net/anime/28977"),
        new URL("http://cdn.myanimelist.net/images/anime/3/72078t.jpg")
    );
    inMemoryPersistenceHandler.filterAnime(filterEntry);

    final WatchListEntry watchEntry = new WatchListEntry(
        "Steins;Gate",
        new InfoLink("http://myanimelist.net/anime/9253"),
        new URL("http://cdn.myanimelist.net/images/anime/5/73199t.jpg")
    );
    inMemoryPersistenceHandler.watchAnime(watchEntry);

    // when
    inMemoryPersistenceHandler.clearAll();

    // then
    assertThat(inMemoryPersistenceHandler.fetchAnimeList().isEmpty()).isTrue();
    assertThat(inMemoryPersistenceHandler.fetchWatchList().isEmpty()).isTrue();
    assertThat(inMemoryPersistenceHandler.fetchFilterList().isEmpty()).isTrue();
  }


  @Test(groups = UNIT_TEST_GROUP)
  public void testThatAddFilterListWorks() throws MalformedURLException {
    // given
    final List<FilterEntry> list = newArrayList();

    final FilterEntry entry = new FilterEntry(
        "Death Note",
        new InfoLink("http://myanimelist.net/anime/1535"),
        new URL("http://cdn.myanimelist.net/images/anime/9/9453t.jpg")
    );
    list.add(entry);

    final FilterEntry gintama = new FilterEntry(
        "Gintama",
        new InfoLink("http://myanimelist.net/anime/28977"),
        new URL("http://cdn.myanimelist.net/images/anime/3/72078t.jpg")
    );
    list.add(gintama);

    final FilterEntry steinsGate = new FilterEntry(
        "Steins;Gate",
        new InfoLink("http://myanimelist.net/anime/9253"),
        new URL("http://cdn.myanimelist.net/images/anime/5/73199t.jpg")
    );
    list.add(steinsGate);

    // when
    inMemoryPersistenceHandler.addFilterList(list);

    // then
    assertThat(inMemoryPersistenceHandler.fetchFilterList().size()).isEqualTo(list.size());
  }


  @Test(groups = UNIT_TEST_GROUP)
  public void testThatAddWatchListWorks() throws MalformedURLException {
    // given
    final List<WatchListEntry> list = newArrayList();

    final WatchListEntry entry = new WatchListEntry(
        "Death Note",
        new InfoLink("http://myanimelist.net/anime/1535"),
        new URL("http://cdn.myanimelist.net/images/anime/9/9453t.jpg")
    );
    list.add(entry);

    final WatchListEntry gintama = new WatchListEntry(
        "Gintama",
        new InfoLink("http://myanimelist.net/anime/28977"),
        new URL("http://cdn.myanimelist.net/images/anime/3/72078t.jpg")
    );
    list.add(gintama);

    final WatchListEntry steinsGate = new WatchListEntry(
        "Steins;Gate",
        new InfoLink("http://myanimelist.net/anime/9253"),
        new URL("http://cdn.myanimelist.net/images/anime/5/73199t.jpg")
    );
    list.add(steinsGate);

    // when
    inMemoryPersistenceHandler.addWatchList(list);

    // then
    assertThat(inMemoryPersistenceHandler.fetchWatchList().size()).isEqualTo(list.size());
  }


  @Test(groups = UNIT_TEST_GROUP)
  public void testThatAddAnimeListWorks() throws MalformedURLException {
    // given
    final List<Anime> list = newArrayList();

    final Anime entry = new Anime("Death Note", new InfoLink("http://myanimelist.net/anime/1535"));
    entry.setEpisodes(37);
    entry.setLocation("/death_note");
    entry.setPicture(new URL("http://cdn.myanimelist.net/images/anime/9/9453.jpg"));
    entry.setThumbnail(new URL("http://cdn.myanimelist.net/images/anime/9/9453t.jpg"));
    entry.setType(AnimeType.TV);
    list.add(entry);

    final Anime steinsGate = new Anime("Steins;Gate",
        new InfoLink("http://myanimelist.net/anime/9253"));
    steinsGate.setEpisodes(24);
    steinsGate.setLocation("/steins_gate");
    steinsGate.setPicture(new URL("http://cdn.myanimelist.net/images/anime/5/73199.jpg"));
    steinsGate.setThumbnail(new URL("http://cdn.myanimelist.net/images/anime/5/73199t.jpg"));
    steinsGate.setType(AnimeType.TV);
    list.add(steinsGate);

    // when
    inMemoryPersistenceHandler.addAnimeList(list);

    // then
    assertThat(inMemoryPersistenceHandler.fetchAnimeList().size()).isEqualTo(list.size());
  }


  @Test(groups = UNIT_TEST_GROUP)
  public void testUpdateOrCreateForNewAnimeEntry() throws MalformedURLException {
    // given
    final Anime entry = new Anime("Death Note", new InfoLink("http://myanimelist.net/anime/1535"));
    entry.setEpisodes(37);
    entry.setLocation("/death_note");
    entry.setPicture(new URL("http://cdn.myanimelist.net/images/anime/9/9453.jpg"));
    entry.setThumbnail(new URL("http://cdn.myanimelist.net/images/anime/9/9453t.jpg"));
    entry.setType(AnimeType.TV);

    // when
    inMemoryPersistenceHandler.updateOrCreate(entry);

    // then
    assertThat(inMemoryPersistenceHandler.fetchAnimeList().isEmpty()).isFalse();
    assertThat(inMemoryPersistenceHandler.fetchAnimeList().get(0)).isEqualTo(entry);
  }


  @Test(groups = UNIT_TEST_GROUP)
  public void testUpdateOrCreateForModifiedAnimeEntry() throws MalformedURLException {
    // given
    final Anime entry = new Anime("Death Note", new InfoLink("http://myanimelist.net/anime/1535"));
    entry.setEpisodes(35);
    entry.setLocation("/death_note");
    entry.setPicture(new URL("http://cdn.myanimelist.net/images/anime/9/9453.jpg"));
    entry.setThumbnail(new URL("http://cdn.myanimelist.net/images/anime/9/9453t.jpg"));
    entry.setType(AnimeType.TV);

    inMemoryPersistenceHandler.addAnime(entry);

    final int episodes = 37;
    entry.setEpisodes(episodes);

    // when
    inMemoryPersistenceHandler.updateOrCreate(entry);

    // then
    assertThat(inMemoryPersistenceHandler.fetchAnimeList().isEmpty()).isFalse();
    assertThat(inMemoryPersistenceHandler.fetchAnimeList().get(0).getEpisodes())
        .isEqualTo(episodes);
  }


  @Test(groups = UNIT_TEST_GROUP)
  public void testUpdateOrCreateForNewFilterEntry() throws MalformedURLException {
    // given
    final FilterEntry entry = new FilterEntry(
        "Death Note",
        new InfoLink("http://myanimelist.net/anime/1535"),
        new URL("http://cdn.myanimelist.net/images/anime/9/9453t.jpg")
    );

    // when
    inMemoryPersistenceHandler.updateOrCreate(entry);

    // then
    assertThat(inMemoryPersistenceHandler.fetchFilterList().isEmpty()).isFalse();
    assertThat(inMemoryPersistenceHandler.fetchFilterList().get(0)).isEqualTo(entry);
  }


  @Test(groups = UNIT_TEST_GROUP)
  public void testUpdateOrCreateForModifiedFilterEntry() throws MalformedURLException {
    // given
    final FilterEntry entry = new FilterEntry(
        "Death Note",
        new InfoLink("http://myanimelist.net/anime/1535"),
        MinimalEntry.Companion.getNO_IMG_THUMB()
    );

    inMemoryPersistenceHandler.filterAnime(entry);

    final URL thumbnail = new URL("http://cdn.myanimelist.net/images/anime/9/9453t.jpg");
    entry.setThumbnail(thumbnail);

    // when
    inMemoryPersistenceHandler.updateOrCreate(entry);

    // then
    assertThat(inMemoryPersistenceHandler.fetchFilterList().isEmpty()).isFalse();
    assertThat(inMemoryPersistenceHandler.fetchFilterList().get(0).getThumbnail())
        .isEqualTo(thumbnail);
  }


  @Test(groups = UNIT_TEST_GROUP)
  public void testUpdateOrCreateForNewWatchListEntry() throws MalformedURLException {
    // given
    final WatchListEntry entry = new WatchListEntry(
        "Death Note",
        new InfoLink("http://myanimelist.net/anime/1535"),
        new URL("http://cdn.myanimelist.net/images/anime/9/9453t.jpg")
    );

    // when
    inMemoryPersistenceHandler.updateOrCreate(entry);

    // then
    assertThat(inMemoryPersistenceHandler.fetchWatchList().isEmpty()).isFalse();
    assertThat(inMemoryPersistenceHandler.fetchWatchList().get(0)).isEqualTo(entry);
  }


  @Test(groups = UNIT_TEST_GROUP)
  public void testUpdateOrCreateForModifiedWatchListEntry() throws MalformedURLException {
    // given
    final WatchListEntry entry = new WatchListEntry(
        "Death Note",
        new InfoLink("http://myanimelist.net/anime/1535"),
        MinimalEntry.Companion.getNO_IMG_THUMB()
    );

    inMemoryPersistenceHandler.watchAnime(entry);

    final URL thumbnail = new URL("http://cdn.myanimelist.net/images/anime/9/9453t.jpg");
    entry.setThumbnail(thumbnail);

    // when
    inMemoryPersistenceHandler.updateOrCreate(entry);

    // then
    assertThat(inMemoryPersistenceHandler.fetchWatchList().isEmpty()).isFalse();
    assertThat(inMemoryPersistenceHandler.fetchWatchList().get(0).getThumbnail())
        .isEqualTo(thumbnail);
  }
}