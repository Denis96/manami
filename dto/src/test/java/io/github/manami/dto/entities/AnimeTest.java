package io.github.manami.dto.entities;

import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;

import java.io.IOException;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import io.github.manami.dto.AnimeType;

public class AnimeTest {

    private Anime anime;


    @Before
    public void setUp() throws IOException {
        anime = new Anime();
        anime.setEpisodes(37);
        anime.setInfoLink("http://myanimelist.net/anime/1535");
        anime.setLocation("/anime/series/death_note");
        anime.setPicture("http://cdn.myanimelist.net/images/anime/9/9453.jpg");
        anime.setThumbnail("http://cdn.myanimelist.net/images/anime/9/9453t.jpg");
        anime.setTitle("Death Note");
        anime.setType(AnimeType.TV);
    }


    @Test
    public void testCopyEmptyAnime() {
        // given
        final Anime target = new Anime();

        // when
        Anime.copyAnime(anime, target);

        // then
        assertThat(target.getEpisodes(), equalTo(anime.getEpisodes()));
        assertThat(target.getInfoLink(), equalTo(anime.getInfoLink()));
        assertThat(target.getLocation(), equalTo(anime.getLocation()));
        assertThat(target.getPicture(), equalTo(anime.getPicture()));
        assertThat(target.getThumbnail(), equalTo(anime.getThumbnail()));
        assertThat(target.getTitle(), equalTo(anime.getTitle()));
        assertThat(target.getType(), equalTo(anime.getType()));
        assertThat(target.getId(), not(anime.getId()));
    }


    @Test
    public void testConstructorWithUUID() {
        // given
        final UUID uuid = UUID.randomUUID();

        // when
        final Anime result = new Anime(uuid);

        // then
        assertThat(result.getEpisodes(), equalTo(0));
        assertThat(result.getPicture(), equalTo(EMPTY));
        assertThat(result.getThumbnail(), equalTo("http://cdn.myanimelist.net/images/qm_50.gif"));
        assertThat(result.getInfoLink(), equalTo(null));
        assertThat(result.getLocation(), equalTo(EMPTY));
        assertThat(result.getTitle(), equalTo(null));
        assertThat(result.getType(), equalTo(null));
        assertThat(result.getId(), equalTo(uuid));
    }
}
