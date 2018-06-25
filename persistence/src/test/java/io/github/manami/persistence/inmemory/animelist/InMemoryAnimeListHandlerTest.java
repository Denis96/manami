package io.github.manami.persistence.inmemory.animelist;

import static io.github.manami.dto.TestConst.UNIT_TEST_GROUP;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.manami.dto.AnimeType;
import io.github.manami.dto.entities.Anime;
import io.github.manami.dto.entities.InfoLink;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class InMemoryAnimeListHandlerTest {

  private InMemoryAnimeListHandler inMemoryAnimeListHandler;


  @BeforeMethod
  public void setUp() throws IOException {
    inMemoryAnimeListHandler = new InMemoryAnimeListHandler();
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
    final boolean result = inMemoryAnimeListHandler.addAnime(entry);

    // then
    assertThat(result).isTrue();
    assertThat(inMemoryAnimeListHandler.fetchAnimeList().size()).isEqualTo(1);
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
    final boolean result = inMemoryAnimeListHandler.addAnime(entry);

    // then
    assertThat(result).isTrue();
    assertThat(inMemoryAnimeListHandler.fetchAnimeList().size()).isEqualTo(1);
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
    final boolean result = inMemoryAnimeListHandler.addAnime(entry);

    // then
    assertThat(result).isTrue();
    assertThat(inMemoryAnimeListHandler.fetchAnimeList().size()).isEqualTo(1);
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
    final boolean result = inMemoryAnimeListHandler.addAnime(entry);

    // then
    assertThat(result).isFalse();
    assertThat(inMemoryAnimeListHandler.fetchAnimeList().size()).isEqualTo(0);
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
    final boolean result = inMemoryAnimeListHandler.addAnime(entry);

    // then
    assertThat(result).isTrue();
    assertThat(inMemoryAnimeListHandler.fetchAnimeList().size()).isEqualTo(1);
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
    final boolean result = inMemoryAnimeListHandler.addAnime(entry);

    // then
    assertThat(result).isTrue();
    assertThat(inMemoryAnimeListHandler.fetchAnimeList().size()).isEqualTo(1);
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
    final boolean result = inMemoryAnimeListHandler.addAnime(entry);

    // then
    assertThat(result).isFalse();
    assertThat(inMemoryAnimeListHandler.fetchAnimeList().size()).isEqualTo(0);
  }


  @Test(groups = UNIT_TEST_GROUP, description = "Default type is TV. so even if not explicitly set, the type is available.")
  public void testaddAnimeIsEntryWithoutType() throws MalformedURLException {
    // given
    final Anime entry = new Anime("Death Note", new InfoLink("http://myanimelist.net/anime/1535"));
    entry.setEpisodes(37);
    entry.setLocation("/death_note");
    entry.setPicture(new URL("http://cdn.myanimelist.net/images/anime/9/9453.jpg"));
    entry.setThumbnail(new URL("http://cdn.myanimelist.net/images/anime/9/9453t.jpg"));

    // when
    final boolean result = inMemoryAnimeListHandler.addAnime(entry);

    // then
    assertThat(result).isTrue();
    assertThat(inMemoryAnimeListHandler.fetchAnimeList().size()).isEqualTo(1);
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
    inMemoryAnimeListHandler.addAnime(entry);

    // when
    final boolean result = inMemoryAnimeListHandler.animeEntryExists(infoLink);

    // then
    assertThat(result).isTrue();
  }


  @Test(groups = UNIT_TEST_GROUP)
  public void testAnimeEntryNotExists() {
    // given

    // when
    final boolean result = inMemoryAnimeListHandler.animeEntryExists(new InfoLink("http://myanimelist.net/anime/1535"));

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
    inMemoryAnimeListHandler.addAnime(entry);

    // when
    final List<Anime> animeList = inMemoryAnimeListHandler.fetchAnimeList();

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
    inMemoryAnimeListHandler.addAnime(entry);

    // when
    final boolean result = inMemoryAnimeListHandler.removeAnime(entry.getId());

    // then
    assertThat(result).isTrue();
    assertThat(inMemoryAnimeListHandler.fetchAnimeList().isEmpty()).isTrue();
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
    inMemoryAnimeListHandler.updateOrCreate(entry);

    // then
    assertThat(inMemoryAnimeListHandler.fetchAnimeList().isEmpty()).isFalse();
    assertThat(inMemoryAnimeListHandler.fetchAnimeList().get(0)).isEqualTo(entry);
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

    inMemoryAnimeListHandler.addAnime(entry);

    final int episodes = 37;
    entry.setEpisodes(episodes);

    // when
    inMemoryAnimeListHandler.updateOrCreate(entry);

    // then
    assertThat(inMemoryAnimeListHandler.fetchAnimeList().isEmpty()).isFalse();
    assertThat(inMemoryAnimeListHandler.fetchAnimeList().get(0).getEpisodes()).isEqualTo(episodes);
  }


  @Test(groups = UNIT_TEST_GROUP)
  public void testClearing() throws MalformedURLException {
    // given
    final Anime entry = new Anime("Death Note", new InfoLink("http://myanimelist.net/anime/1535"));
    entry.setEpisodes(37);
    entry.setLocation("/death_note");
    entry.setPicture(new URL("http://cdn.myanimelist.net/images/anime/9/9453.jpg"));
    entry.setThumbnail(new URL("http://cdn.myanimelist.net/images/anime/9/9453t.jpg"));
    entry.setType(AnimeType.TV);

    inMemoryAnimeListHandler.addAnime(entry);

    // when
    inMemoryAnimeListHandler.clear();

    // then
    assertThat(inMemoryAnimeListHandler.fetchAnimeList().isEmpty()).isTrue();
  }
}
