package io.github.manami.gui.controller;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Queues.newConcurrentLinkedQueue;
import static io.github.manami.gui.components.Icons.createIconDelete;
import static org.apache.commons.lang3.StringUtils.EMPTY;

import io.github.manami.Main;
import io.github.manami.cache.Cache;
import io.github.manami.cache.strategies.headlessbrowser.extractor.ExtractorList;
import io.github.manami.cache.strategies.headlessbrowser.extractor.anime.AnimeEntryExtractor;
import io.github.manami.core.ManamiImpl;
import io.github.manami.core.commands.CmdAddWatchListEntry;
import io.github.manami.core.commands.CmdDeleteWatchListEntry;
import io.github.manami.core.commands.CommandService;
import io.github.manami.core.tasks.AnimeRetrievalTask;
import io.github.manami.core.tasks.ServiceRepository;
import io.github.manami.dto.entities.Anime;
import io.github.manami.dto.entities.InfoLink;
import io.github.manami.dto.entities.MinimalEntry;
import io.github.manami.dto.entities.WatchListEntry;
import io.github.manami.gui.components.AnimeGuiComponentsListEntry;
import io.github.manami.gui.utility.ObservableQueue;
import java.util.Arrays;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Optional;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;

public class WatchListController extends AbstractAnimeListController implements Observer {

  public static final String WATCH_LIST_TITLE = "Watch List";

  /**
   * Instance of the application.
   */
  private final ManamiImpl app = Main.CONTEXT.getBean(ManamiImpl.class);

  /**
   * Contains all possible extractors.
   */
  private final ExtractorList extractors = Main.CONTEXT.getBean(ExtractorList.class);

  /**
   * Instance of the cache.
   */
  private final Cache cache = Main.CONTEXT.getBean(Cache.class);

  /**
   * Instance of the main application.
   */
  private final CommandService cmdService = Main.CONTEXT.getBean(CommandService.class);

  /**
   * Instance of the service repository.
   */
  private final ServiceRepository serviceRepo = Main.CONTEXT.getBean(ServiceRepository.class);

  /**
   * List of all actively running services.
   */
  private final ObservableQueue<AnimeRetrievalTask> serviceList = new ObservableQueue<>(newConcurrentLinkedQueue());

  /**
   * {@link TextField} for adding a new entry.
   */
  @FXML
  private TextField txtUrl;

  /**
   * {@link GridPane} which shows the results.
   */
  @FXML
  private GridPane gridPane;

  /**
   * Moving circle indicating a process.
   */
  @FXML
  private ProgressIndicator progressIndicator;

  /**
   * Showing the amount of services running in the background.
   */
  @FXML
  private Label lblProgressMsg;


  /**
   * Fills the GUI with all the entries which are already in the database.
   */
  public void initialize() {
    serviceList.addListener((ListChangeListener<AnimeRetrievalTask>) arg0 -> {
      final int size = serviceList.size();
      final String text = String.format("Preparing entries: %s", size);

      Platform.runLater(() -> {
        if (size == 0) {
          progressIndicator.setVisible(false);
          lblProgressMsg.setVisible(false);
        } else {
          progressIndicator.setVisible(true);
          lblProgressMsg.setText(text);
          lblProgressMsg.setVisible(true);
        }
      });
    });

    app.fetchWatchList().forEach(this::addEntryToGui);
    showEntries();
  }


  @Override
  protected GridPane getGridPane() {
    return gridPane;
  }


  @FXML
  public void addEntry() {
    final List<String> urlList = Arrays.asList(txtUrl.getText().trim().split(" "));
    final List<InfoLink> infoLinkList = newArrayList();
    urlList.forEach(url -> infoLinkList.add(new InfoLink(url)));
    infoLinkList.stream().filter(infoLink -> infoLink.isValid()).forEach(this::addInfoLinkToWatchList);

    txtUrl.setText(EMPTY);
    showEntries();
  }


  private void addInfoLinkToWatchList(final InfoLink infoLink) {
    final Optional<AnimeEntryExtractor> extractor = extractors.getAnimeEntryExtractor(infoLink);
    InfoLink normalizedInfoLink = null;

    if (extractor.isPresent()) {
      normalizedInfoLink = extractor.get().normalizeInfoLink(infoLink);
    }

    if (!app.watchListEntryExists(normalizedInfoLink)) {
      final AnimeRetrievalTask retrievalService = new AnimeRetrievalTask(cache, normalizedInfoLink);
      retrievalService.addObserver(this);
      serviceList.offer(retrievalService);

      if (serviceList.size() == 1) {
        retrievalService.start();
      }
    }
  }


  public void clear() {
    serviceList.clear();
    Platform.runLater(() -> {
      progressIndicator.setVisible(false);
      lblProgressMsg.setVisible(false);
      getGridPane().getChildren().clear();
    });
    clearComponentList();
    showEntries();
  }


  @Override
  protected AnimeGuiComponentsListEntry addWatchListButton(final AnimeGuiComponentsListEntry componentListEntry) {
    return componentListEntry;
  }


  @Override
  protected AnimeGuiComponentsListEntry addRemoveButton(final AnimeGuiComponentsListEntry componentListEntry) {
    final Button removeButton = new Button(EMPTY, createIconDelete());
    removeButton.setTooltip(new Tooltip("delete from watch list"));

    componentListEntry.setRemoveButton(removeButton);

    removeButton.setOnAction(event -> {
      final Optional<WatchListEntry> watchListEntry = WatchListEntry.valueOf(componentListEntry.getAnime());

      if (watchListEntry.isPresent()) {
        cmdService.executeCommand(new CmdDeleteWatchListEntry(watchListEntry.get(), app));
        getComponentList().remove(componentListEntry);
        showEntries();
      }
    });

    return componentListEntry;
  }


  @Override
  protected List<? extends MinimalEntry> getEntryList() {
    return Main.CONTEXT.getBean(ManamiImpl.class).fetchWatchList();
  }


  @Override
  boolean isInList(final InfoLink infoLink) {
    return infoLink.isValid() && app.watchListEntryExists(infoLink);
  }


  @Override
  public void update(final Observable observable, final Object object) {
    if (observable == null || object == null) {
      return;
    }

    if (observable instanceof AnimeRetrievalTask && object instanceof Anime) {
      final Optional<WatchListEntry> anime = WatchListEntry.valueOf((Anime) object);

      if (anime.isPresent()) {
        cmdService.executeCommand(new CmdAddWatchListEntry(anime.get(), app));
        addEntryToGui(anime.get()); // create GUI components
      }

      serviceList.poll();

      if (!serviceList.isEmpty()) {
        serviceRepo.startService(serviceList.peek());
      }
    }
  }
}
