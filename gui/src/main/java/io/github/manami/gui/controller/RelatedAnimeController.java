package io.github.manami.gui.controller;

import static io.github.manamiproject.manami.core.config.Config.NOTIFICATION_DURATION;
import static io.github.manami.gui.components.Icons.createIconCancel;

import io.github.manami.Main;
import io.github.manami.cache.CacheI;
import io.github.manamiproject.manami.core.Manami;
import io.github.manami.core.tasks.RelatedAnimeFinderTask;
import io.github.manami.core.tasks.ServiceRepository;
import io.github.manamiproject.manami.core.tasks.events.ProgressState;
import io.github.manamiproject.manami.dto.entities.Anime;
import io.github.manamiproject.manami.dto.entities.InfoLink;
import io.github.manamiproject.manami.dto.entities.MinimalEntry;
import io.github.manami.gui.wrapper.MainControllerWrapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Tab;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import org.controlsfx.control.Notifications;

/**
 * Controller for finding related anime. Opening as a new tab.
 */
public class RelatedAnimeController extends AbstractAnimeListController implements Observer {

  public static final String RELATED_ANIME_TAB_TITLE = "Related Anime";

  /**
   * Application
   */
  private final Manami app = Main.CONTEXT.getBean(Manami.class);

  /**
   * The corresponding background service.
   */
  private RelatedAnimeFinderTask service;

  /**
   * Instance of the service repository.
   */
  private final ServiceRepository serviceRepo = Main.CONTEXT.getBean(ServiceRepository.class);

  /**
   * Container holding all the progress components.
   */
  @FXML
  private VBox vBoxProgress;

  /**
   * Progress bar
   */
  @FXML
  private ProgressBar progressBar;

  /**
   * Label showing how many entries have been processed.
   */
  @FXML
  private Label lblProgress;

  /**
   * Button for starting the search.
   */
  @FXML
  private Button btnStart;

  /**
   * Button to cancel the service.
   */
  @FXML
  private Button btnCancel;

  /**
   * {@link GridPane} containing the results.
   */
  @FXML
  private GridPane gridPane;

  /**
   * Instance of the tab in which the pane is being shown.
   */
  private Tab tab;


  /**
   * Called from FXML when creating the Object.
   */
  public void initialize() {
    btnStart.setOnAction(event -> start());

    btnCancel.setGraphic(createIconCancel());
    btnCancel.setTooltip(new Tooltip("cancel"));
    btnCancel.setOnAction(event -> cancel());
  }


  /**
   * Starts the service.
   */
  private void start() {
    service = new RelatedAnimeFinderTask(Main.CONTEXT.getBean(CacheI.class), app, app.fetchAnimeList(), this);
    showProgressControls(true);
    clearComponentList();
    serviceRepo.startService(service);
  }


  /**
   * Stops the service if necessary and resets the GUI.
   */
  public void cancel() {
    if (service != null) {
      service.cancel();
    }

    clear();
  }


  /**
   * Shows the progress components and hides the start button or the other way round.
   *
   * @param value Shows the progress components if the value is true and hides the start button.
   */
  private void showProgressControls(final boolean value) {
    Platform.runLater(() -> {
      vBoxProgress.setVisible(value);
      btnCancel.setVisible(value);
      btnStart.setVisible(!value);
    });
  }


  @Override
  public void update(final Observable observable, final Object object) {
    if (object == null) {
      return;
    }

    // it's an update of the progress
    if (object instanceof ProgressState) {
      final ProgressState state = (ProgressState) object;
      final int done = state.getDone();
      final int all = state.getTodo() + done;
      final double percent = ((done * 100.00) / all) / 100.00;

      Platform.runLater(() -> {
        progressBar.setProgress(percent);
        lblProgress.setText(String.format("%s / %s", done, all));
      });
    }

    // adds new Anime entries
    if (object instanceof ArrayList) {
      final ArrayList<Anime> list = (ArrayList<Anime>) object;
      if (list.size() > 0) {
        list.forEach(this::addEntryToGui);
        showEntries();
      }
    }

    // Processing is done
    if (object instanceof Boolean) {
      showProgressControls(false);
      Platform.runLater(() -> Notifications.create().title("Search for related anime finished").text("Finished search for related anime.")
          .hideAfter(NOTIFICATION_DURATION)
          .onAction(Main.CONTEXT.getBean(MainControllerWrapper.class).getMainController().new RelatedAnimeNotificationEventHandler())
          .showInformation());
    }
  }


  @Override
  public void updateChildren() {
    Platform.runLater(() -> tab.setText(String.format("%s (%s)", RELATED_ANIME_TAB_TITLE, getComponentList().size())));
  }


  @Override
  protected GridPane getGridPane() {
    return gridPane;
  }


  public void setTab(final Tab tab) {
    this.tab = tab;
  }


  @Override
  protected List<? extends MinimalEntry> getEntryList() {
    // not needed for this controller
    return null;
  }


  @Override
  boolean isInList(final InfoLink infoLink) {
    // not needed for this controller
    return false;
  }


  public void clear() {
    Platform.runLater(() -> {
      tab.setText(RELATED_ANIME_TAB_TITLE);
      gridPane.getChildren().clear();
      lblProgress.setText("Preparing");
      progressBar.setProgress(-1);
    });
    clearComponentList();
    showProgressControls(false);
  }
}
