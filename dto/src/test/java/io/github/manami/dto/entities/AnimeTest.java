package io.github.manami.dto.entities;

import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;

import java.util.UUID;

import org.testng.annotations.Test;

import io.github.manami.dto.AnimeType;

public class AnimeTest {

    @Test(groups = "unitTest")
    public void testCopyEmptyAnime() {
        // given
        final Anime target = new Anime();

        final Anime anime = new Anime();
        anime.setEpisodes(37);
        anime.setInfoLink("http://myanimelist.net/anime/1535");
        anime.setLocation("/anime/series/death_note");
        anime.setPicture("http://cdn.myanimelist.net/images/anime/9/9453.jpg");
        anime.setThumbnail("http://cdn.myanimelist.net/images/anime/9/9453t.jpg");
        anime.setTitle("Death Note");
        anime.setType(AnimeType.TV);

        // when
        Anime.copyAnime(anime, target);

        // then
        assertEquals(target.getEpisodes(), anime.getEpisodes());
        assertEquals(target.getInfoLink(), anime.getInfoLink());
        assertEquals(target.getLocation(), anime.getLocation());
        assertEquals(target.getPicture(), anime.getPicture());
        assertEquals(target.getThumbnail(), anime.getThumbnail());
        assertEquals(target.getTitle(), anime.getTitle());
        assertEquals(target.getType(), anime.getType());
        assertNotEquals(target.getId(), anime.getId());
    }


    @Test(groups = "unitTest")
    public void testConstructorWithUUID() {
        // given
        final UUID uuid = UUID.randomUUID();

        // when
        final Anime result = new Anime(uuid);

        // then
        assertEquals(result.getEpisodes(), 0);
        assertEquals(result.getPicture(), EMPTY);
        assertEquals(result.getThumbnail(), "http://cdn.myanimelist.net/images/qm_50.gif");
        assertEquals(result.getInfoLink(), null);
        assertEquals(result.getLocation(), EMPTY);
        assertEquals(result.getTitle(), null);
        assertEquals(result.getType(), null);
        assertEquals(result.getId(), uuid);
    }


    @Test(groups = "unitTest")
    public void testSetEpisodesDoesNotChangeToNegativeNumber() {
        // given
        final Anime anime = new Anime();

        // when
        anime.setEpisodes(-1);

        // then
        assertEquals(anime.getEpisodes(), 0);
    }


    @Test(groups = "unitTest")
    public void testSetEpisodesDoesNotChangeToNegativeNumberForNonDefaultValue() {
        // given
        final Anime anime = new Anime();
        final int episodes = 4;
        anime.setEpisodes(episodes);

        // when
        anime.setEpisodes(-1);

        // then
        assertEquals(anime.getEpisodes(), episodes);
    }


    @Test(groups = "unitTest")
    public void testIsValidAnimeWithNull() {
        // given

        // when
        final boolean result = Anime.isValidAnime(null);

        // then
        assertEquals(result, false);
    }


    @Test(groups = "unitTest")
    public void testIsValidAnimeWithValidEntry() {
        // given
        final Anime anime = new Anime();
        anime.setEpisodes(37);
        anime.setInfoLink("http://myanimelist.net/anime/1535");
        anime.setLocation("/anime/series/death_note");
        anime.setPicture("http://cdn.myanimelist.net/images/anime/9/9453.jpg");
        anime.setThumbnail("http://cdn.myanimelist.net/images/anime/9/9453t.jpg");
        anime.setTitle("Death Note");
        anime.setType(AnimeType.TV);

        // when
        final boolean result = Anime.isValidAnime(anime);

        // then
        assertEquals(result, true);
    }


    @Test(groups = "unitTest")
    public void testIsValidAnimeWithTypeNull() {
        // given
        final Anime anime = new Anime();
        anime.setEpisodes(37);
        anime.setInfoLink("http://myanimelist.net/anime/1535");
        anime.setLocation("/anime/series/death_note");
        anime.setPicture("http://cdn.myanimelist.net/images/anime/9/9453.jpg");
        anime.setThumbnail("http://cdn.myanimelist.net/images/anime/9/9453t.jpg");
        anime.setTitle("Death Note");
        anime.setType(null);

        // when
        final boolean result = Anime.isValidAnime(anime);

        // then
        assertEquals(result, false);
    }


    @Test(groups = "unitTest")
    public void testGetTypeAsStringForNull() {
        // given
        final Anime anime = new Anime();
        anime.setType(null);

        // when
        final String result = anime.getTypeAsString();

        // then
        assertEquals(result, null);
    }


    @Test(groups = "unitTest")
    public void testGetTypeAsStringForTv() {
        // given
        final AnimeType type = AnimeType.TV;
        final Anime anime = new Anime();
        anime.setType(type);

        // when
        final String result = anime.getTypeAsString();

        // then
        assertEquals(result, type.getValue());
    }


    @Test(groups = "unitTest")
    public void testGetTypeAsStringForMovie() {
        // given
        final AnimeType type = AnimeType.MOVIE;
        final Anime anime = new Anime();
        anime.setType(type);

        // when
        final String result = anime.getTypeAsString();

        // then
        assertEquals(result, type.getValue());
    }


    @Test(groups = "unitTest")
    public void testGetTypeAsStringForMusic() {
        // given
        final AnimeType type = AnimeType.MUSIC;
        final Anime anime = new Anime();
        anime.setType(type);

        // when
        final String result = anime.getTypeAsString();

        // then
        assertEquals(result, type.getValue());
    }


    @Test(groups = "unitTest")
    public void testGetTypeAsStringForOna() {
        // given
        final AnimeType type = AnimeType.ONA;
        final Anime anime = new Anime();
        anime.setType(type);

        // when
        final String result = anime.getTypeAsString();

        // then
        assertEquals(result, type.getValue());
    }


    @Test(groups = "unitTest")
    public void testGetTypeAsStringForOva() {
        // given
        final AnimeType type = AnimeType.OVA;
        final Anime anime = new Anime();
        anime.setType(type);

        // when
        final String result = anime.getTypeAsString();

        // then
        assertEquals(result, type.getValue());
    }


    @Test(groups = "unitTest")
    public void testGetTypeAsStringForSpecial() {
        // given
        final AnimeType type = AnimeType.SPECIAL;
        final Anime anime = new Anime();
        anime.setType(type);

        // when
        final String result = anime.getTypeAsString();

        // then
        assertEquals(result, type.getValue());
    }


    @Test(groups = "unitTest")
    public void testCopyNullTarget() {
        // given
        final Anime target = new Anime();

        final Anime anime = new Anime();
        anime.setEpisodes(37);
        anime.setInfoLink("http://myanimelist.net/anime/1535");
        anime.setLocation("/anime/series/death_note");
        anime.setPicture("http://cdn.myanimelist.net/images/anime/9/9453.jpg");
        anime.setThumbnail("http://cdn.myanimelist.net/images/anime/9/9453t.jpg");
        anime.setTitle("Death Note");
        anime.setType(AnimeType.TV);

        // when
        Anime.copyNullTarget(anime, target);

        // then
        assertEquals(target.getEpisodes(), anime.getEpisodes());
        assertEquals(target.getInfoLink(), anime.getInfoLink());
        assertEquals(target.getLocation(), anime.getLocation());
        assertEquals(target.getPicture(), anime.getPicture());
        assertEquals(target.getThumbnail(), anime.getThumbnail());
        assertEquals(target.getTitle(), anime.getTitle());
        assertEquals(target.getType(), anime.getType());
        assertNotEquals(target.getId(), anime.getId());
    }


    @Test(groups = "unitTest")
    public void testCopyNullTargetWithFilledTarget() {
        // given
        final Anime target = new Anime();
        target.setEpisodes(4);
        target.setInfoLink("http://myanimelist.net/anime/10863");
        target.setLocation("/anime/series/steins_gate_special");
        target.setPicture("http://cdn.myanimelist.net/images/anime/7/36531.jpg");
        target.setThumbnail("http://cdn.myanimelist.net/images/anime/7/36531t.jpg");
        target.setTitle("Steins;Gate: Oukoubakko no Poriomania");
        target.setType(AnimeType.SPECIAL);

        final Anime anime = new Anime();
        anime.setEpisodes(37);
        anime.setInfoLink("http://myanimelist.net/anime/1535");
        anime.setLocation("/anime/series/death_note");
        anime.setPicture("http://cdn.myanimelist.net/images/anime/9/9453.jpg");
        anime.setThumbnail("http://cdn.myanimelist.net/images/anime/9/9453t.jpg");
        anime.setTitle("Death Note");
        anime.setType(AnimeType.TV);

        // when
        Anime.copyNullTarget(anime, target);

        // then
        assertNotEquals(target.getEpisodes(), anime.getEpisodes());
        assertNotEquals(target.getInfoLink(), anime.getInfoLink());
        assertNotEquals(target.getLocation(), anime.getLocation());
        assertNotEquals(target.getPicture(), anime.getPicture());
        assertNotEquals(target.getThumbnail(), anime.getThumbnail());
        assertNotEquals(target.getTitle(), anime.getTitle());
        assertNotEquals(target.getType(), anime.getType());
        assertNotEquals(target.getId(), anime.getId());
    }
}
