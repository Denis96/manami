package io.github.manami.core.tasks;

import static com.google.common.collect.Lists.newArrayList;
import static org.springframework.util.Assert.notNull;

import com.google.common.collect.ImmutableList;
import io.github.manami.cache.Cache;
import io.github.manami.dto.entities.Anime;
import java.security.SecureRandom;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This service is called whenever a new list is opened. It creates cache entries if necessary. Always start {@link BackgroundTask}s using the
 * {@link ServiceRepository}!
 */
public class CacheInitializationTask extends AbstractTask<Void> {

  private static final Logger log = LoggerFactory.getLogger(CacheInitializationTask.class);
  private static final int MAX_THREADS = Runtime.getRuntime().availableProcessors();

  /**
   * Instance of the cache.
   */
  private final Cache cache;

  /**
   * The user's anime list.
   */
  private final List<Anime> list;

  private ExecutorService animeExecutorService;
  private ExecutorService relatedExecutorService;
  private ExecutorService recomExecutorService;


  /**
   * Constructor awaiting a cache.
   *
   * @param cache Cache
   */
  public CacheInitializationTask(final Cache cache, final List<Anime> list) {
    this.cache = cache;
    this.list = list;
    initializeThreadPools();
  }


  private void initializeThreadPools() {
    animeExecutorService = Executors.newFixedThreadPool(MAX_THREADS);
    relatedExecutorService = Executors.newFixedThreadPool(MAX_THREADS);
    recomExecutorService = Executors.newFixedThreadPool(MAX_THREADS);
  }


  @Override
  public Void execute() {
    notNull(list, "List of anime cannot be null");

    if (!list.isEmpty()) {
      // Create copy of anime list and shuffle it
      final List<Anime> shuffledList = newArrayList(ImmutableList.copyOf(list));

      Collections.shuffle(shuffledList, new SecureRandom());
      Collections.shuffle(shuffledList, new SecureRandom());
      Collections.shuffle(shuffledList, new SecureRandom());
      Collections.shuffle(shuffledList, new SecureRandom());

      final List<Callable<Void>> animeTaskList = newArrayList();

      shuffledList.forEach(entry -> {
        animeTaskList.add(() -> {
          if (!isInterrupt()) {
            if (entry.getInfoLink().isValid()) {
              log.debug("Creating cache entry for {} if necessary.", entry.getInfoLink());
              final Optional<Anime> cachedAnime = cache.fetchAnime(entry.getInfoLink());

              if (cachedAnime.isPresent() && !isInterrupt()) {
                loadRecomAndRelated(cachedAnime.get());
              }
            }
          }
          return null;
        });
      });

      if (!isInterrupt()) {
        try {
          animeExecutorService.invokeAll(animeTaskList);
        } catch (final Throwable e) {
          log.error("Error during cache initialization: ", e);
          cancel();
        }
      }
    }

    animeExecutorService.shutdownNow();
    relatedExecutorService.shutdownNow();
    recomExecutorService.shutdownNow();

    return null;
  }


  private void loadRecomAndRelated(final Anime anime) {
    recomExecutorService.execute(new Thread() {

      @Override
      public void run() {
        if (!isInterrupted()) {
          log.debug("Creating recommendations cache entry for {} if necessary.", anime.getInfoLink());
          cache.fetchRecommendations(anime.getInfoLink());
        }
      }
    });

    relatedExecutorService.execute(new Thread() {

      @Override
      public void run() {
        if (!isInterrupted()) {
          log.debug("Creating related anime cache entry for {} if necessary.", anime.getInfoLink());
          cache.fetchRelatedAnime(anime.getInfoLink());
        }
      }
    });
  }


  @Override
  public void reset() {
    animeExecutorService.shutdownNow();
    relatedExecutorService.shutdownNow();
    recomExecutorService.shutdownNow();
    initializeThreadPools();
    cancel();
    super.reset();
  }
}
