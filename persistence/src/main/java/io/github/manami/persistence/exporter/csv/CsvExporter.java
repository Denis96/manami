package io.github.manami.persistence.exporter.csv;

import static com.google.common.collect.Lists.newArrayList;
import static io.github.manami.persistence.exporter.csv.CsvConfig.CsvConfigType.ANIMELIST;
import static io.github.manami.persistence.exporter.csv.CsvConfig.CsvConfigType.FILTERLIST;
import static io.github.manami.persistence.exporter.csv.CsvConfig.CsvConfigType.WATCHLIST;
import static org.apache.commons.lang3.StringUtils.EMPTY;

import io.github.manami.dto.entities.Anime;
import io.github.manami.dto.entities.FilterEntry;
import io.github.manami.dto.entities.WatchListEntry;
import io.github.manami.persistence.ApplicationPersistence;
import io.github.manami.persistence.exporter.Exporter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.supercsv.io.CsvListWriter;
import org.supercsv.io.ICsvListWriter;
import org.supercsv.prefs.CsvPreference;

/**
 * Exports a list to a csv file.
 */
public class CsvExporter implements Exporter {

  private static final Logger log = LoggerFactory.getLogger(CsvExporter.class);
  /**
   * Contains configurations for reading and writing csv files.
   */
  private final CsvConfig config;

  private final ApplicationPersistence persistence;

  private ICsvListWriter listWriter;


  /**
   * Constructor
   */
  public CsvExporter(final ApplicationPersistence persistence) {
    this.persistence = persistence;
    config = new CsvConfig();
  }


  @Override
  public boolean exportAll(final Path file) {

    try {
      listWriter = new CsvListWriter(new FileWriter(file.toFile()), CsvPreference.STANDARD_PREFERENCE);

      // write the header
      listWriter.writeHeader(config.getHeaders());

      writeAnimeList();
      writeWatchList();
      writeFilterList();

      listWriter.close();
    } catch (final IOException e) {
      log.error("An error occurred while trying to export the list to CSV: ", e);
      return false;
    }

    return true;
  }


  private void writeAnimeList() throws IOException {
    final List<List<String>> mappedEntryList = newArrayList();
    List<String> curEntry;

    // Map Anime Objects to a list
    for (final Anime entry : persistence.fetchAnimeList()) {
      curEntry = newArrayList();
      curEntry.add(ANIMELIST.getValue());
      curEntry.add(entry.getTitle());
      curEntry.add(entry.getTypeAsString());
      curEntry.add(String.valueOf(entry.getEpisodes()));
      curEntry.add(entry.getInfoLink().getUrl());
      curEntry.add(entry.getLocation());
      mappedEntryList.add(curEntry);
    }

    // write the animeList
    for (final List<String> entry : mappedEntryList) {
      listWriter.write(entry, config.getProcessors());
    }
  }

  private void writeWatchList() throws IOException {
    final List<List<String>> mappedEntryList = newArrayList();
    List<String> curEntry;

    // Map Anime Objects to a list
    for (final WatchListEntry entry : persistence.fetchWatchList()) {
      curEntry = newArrayList();
      curEntry.add(WATCHLIST.getValue());
      curEntry.add(entry.getTitle());
      curEntry.add(EMPTY);
      curEntry.add(EMPTY);
      curEntry.add(entry.getInfoLink().getUrl());
      curEntry.add(EMPTY);
      mappedEntryList.add(curEntry);
    }

    // write the watchList
    for (final List<String> entry : mappedEntryList) {
      listWriter.write(entry, config.getProcessors());
    }
  }


  private void writeFilterList() throws IOException {
    final List<List<String>> mappedEntryList = newArrayList();
    List<String> curEntry;

    // Map Anime Objects to a list
    for (final FilterEntry entry : persistence.fetchFilterList()) {
      curEntry = newArrayList();
      curEntry.add(FILTERLIST.getValue());
      curEntry.add(entry.getTitle());
      curEntry.add(EMPTY);
      curEntry.add(EMPTY);
      curEntry.add(entry.getInfoLink().getUrl());
      curEntry.add(EMPTY);
      mappedEntryList.add(curEntry);
    }

    // write the filterList
    for (final List<String> entry : mappedEntryList) {
      listWriter.write(entry, config.getProcessors());
    }
  }
}