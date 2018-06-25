package io.github.manami.persistence.exporter.csv;

import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;

/**
 * Configuration for the CSV import and export.
 */
public class CsvConfig {


  public enum CsvConfigType {

    ANIMELIST("animeList"), WATCHLIST("watchList"), FILTERLIST("filterList");

    CsvConfigType(String value) {
      this.value = value;
    }

    private final String value;

    public String getValue() {
      return value;
    }

    public static CsvConfigType findByName(final String name) {
      for (final CsvConfigType type : values()) {
        if (type.getValue().equalsIgnoreCase((name))) {
          return type;
        }
      }
      return null;
    }
  }


  /**
   * Returns the names of the columns.
   *
   * @return A String with the names of the columns for the csv file.
   */
  public String[] getHeaders() {
    return new String[]{"list", "title", "type", "episodes", "infolink", "location"};
  }


  /**
   * Type of Processors.
   *
   * @return An array with indication of the column's type.
   */
  public CellProcessor[] getProcessors() {
    return new CellProcessor[]{new NotNull(), // List (e.g. animeList,
        // filterList, watchList)
        new NotNull(), // Title
        new Optional(), // Type
        new Optional(), // Episodes
        new Optional(), // InfoLink
        new Optional(), // Location
    };
  }
}
