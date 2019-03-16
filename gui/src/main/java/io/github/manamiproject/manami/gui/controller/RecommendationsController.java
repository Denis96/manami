package io.github.manamiproject.manami.gui.controller;

import static com.google.common.collect.Lists.newArrayList;

//public class RecommendationsController extends AbstractAnimeListController implements Observer {
//
//  public static final String RECOMMENDATIONS_TAB_TITLE = "Recommendations";
//
//  /**
//   * Instance of the service repository.
//   */
//  private final ServiceRepository serviceRepo = Main.CONTEXT.getBean(ServiceRepository.class);
//
//  private final Manami app = Main.CONTEXT.getBean(Manami.class);
//
//  private final CacheI cacheI = Main.CONTEXT.getBean(CacheI.class);
//
//  /**
//   * Container holding all the progress components.
//   */
//  @FXML
//  private VBox vBoxProgress;
//
//  /**
//   * Progress bar
//   */
//  @FXML
//  private ProgressBar progressBar;
//
//  /**
//   * Label showing how many entries have been processed.
//   */
//  @FXML
//  private Label lblProgress;
//
//  /**
//   * Button for starting the search.
//   */
//  @FXML
//  private Button btnStart;
//
//  /**
//   * Button to cancel the service.
//   */
//  @FXML
//  private Button btnCancel;
//
//  /**
//   * {@link GridPane} containing the results.
//   */
//  @FXML
//  private GridPane gridPane;
//
//  @FXML
//  private Button btnExport;
//
//  /**
//   * Instance of the tab in which the pane is being shown.
//   */
//  private Tab tab;
//
//  /**
//   * Service instance.
//   */
//  private RecommendationsRetrievalTask service;
//
//  private final List<Anime> originalOrder = newArrayList();
//
//
//  /**
//   * Called from FXML when creating the Object.
//   */
//  public void initialize() {
//    btnStart.setOnAction(event -> start());
//
//    btnCancel.setGraphic(createIconCancel());
//    btnCancel.setTooltip(new Tooltip("cancel"));
//    btnCancel.setOnAction(event -> cancel());
//
//    btnExport.setOnAction(event -> exportRecommendations());
//    btnExport.setVisible(false);
//  }
//
//
//  private void exportRecommendations() {
//    final Path file = showExportDialog(Main.CONTEXT.getBean(MainControllerWrapper.class).getMainStage(), JSON_FILTER);
//    final List<Anime> exportList = newArrayList();
//
//    originalOrder.forEach(entry -> exportList.add(entry));
//
//    app.exportList(exportList, file);
//  }
//
//
//  private void showProgressControls(final boolean url) {
//    Platform.runLater(() -> {
//      vBoxProgress.setVisible(url);
//      btnCancel.setVisible(url);
//      btnStart.setVisible(!url);
//    });
//  }
//
//
//  private void start() {
//    service = new RecommendationsRetrievalTask(app, cacheI, this);
//    showProgressControls(true);
//    clearComponentList();
//    serviceRepo.startService(service);
//  }
//
//
//  /**
//   * Stops the service if necessary and resets the GUI.
//   */
//  public void cancel() {
//    if (service != null) {
//      service.cancel();
//    }
//
//    clear();
//  }
//
//
//  @Override
//  public void update(final Observable observable, final Object object) {
//    if (object == null) {
//      return;
//    }
//
//    if (object instanceof AdvancedProgressState) {
//      final AdvancedProgressState state = (AdvancedProgressState) object;
//      final int done = state.getDone();
//      final int all = state.getTodo();
//      final double percent = ((done * 100.00) / all) / 100.00;
//
//      if (state.getAnime() != null) {
//        originalOrder.add(state.getAnime());
//        addEntryToGui(state.getAnime());
//        showEntries();
//        Platform.runLater(() -> {
//          progressBar.setProgress(percent);
//          lblProgress.setText(String.format("Loading %s / %s", done, all));
//          showExportButtonIfPossible();
//        });
//      }
//
//      return;
//    }
//
//    // it's an update of the progress
//    if (object instanceof ProgressState) {
//      final ProgressState state = (ProgressState) object;
//      final int done = state.getDone();
//      final int all = state.getTodo();
//      final double percent = ((done * 100.00) / all) / 100.00;
//
//      Platform.runLater(() -> {
//        progressBar.setProgress(percent);
//        lblProgress.setText(String.format("Calculating %s / %s", done, all));
//        showExportButtonIfPossible();
//      });
//      return;
//    }
//
//    if (object instanceof Boolean) {
//      showProgressControls(false);
//      Platform.runLater(
//          () -> Notifications.create().title("Recommendations finished").text("Finished search for recommendations.").hideAfter(NOTIFICATION_DURATION)
//              .onAction(Main.CONTEXT.getBean(MainControllerWrapper.class).getMainController().new RecommendationsNotificationEventHandler())
//              .showInformation());
//    }
//  }
//
//
//  private void showExportButtonIfPossible() {
//    if (!getComponentList().isEmpty() && !btnExport.isVisible()) {
//      btnExport.setVisible(true);
//    }
//  }
//
//
//  @Override
//  protected GridPane getGridPane() {
//    return gridPane;
//  }
//
//
//  @Override
//  public void updateChildren() {
//    Platform.runLater(() -> tab.setText(String.format("%s (%s)", RECOMMENDATIONS_TAB_TITLE, getComponentList().size())));
//  }
//
//
//  public void setTab(final Tab tab) {
//    this.tab = tab;
//  }
//
//
//  @Override
//  protected List<AnimeGuiComponentsListEntry> sortComponentEntries() {
//    final List<AnimeGuiComponentsListEntry> correctedOrder = newArrayList();
//    originalOrder.forEach(entry -> correctedOrder.add(getComponentList().get(entry.getInfoLink())));
//    return correctedOrder;
//  }
//
//
//  @Override
//  protected List<? extends MinimalEntry> getEntryList() {
//    // not needed for this controller
//    return null;
//  }
//
//
//  @Override
//  boolean isInList(final InfoLink infoLink) {
//    // not needed for this controller
//    return false;
//  }
//
//
//  public void clear() {
//    Platform.runLater(() -> {
//      tab.setText(RECOMMENDATIONS_TAB_TITLE);
//      gridPane.getChildren().clear();
//      lblProgress.setText("Preparing");
//      progressBar.setProgress(-1);
//      btnExport.setVisible(false);
//    });
//    clearComponentList();
//    showProgressControls(false);
//  }
//
//
//  @Override
//  protected AnimeGuiComponentsListEntry addFilterListButton(final AnimeGuiComponentsListEntry componentListEntry) {
//    super.addFilterListButton(componentListEntry);
//
//    componentListEntry.getAddToFilterListButton().setOnAction(event -> {
//      final Optional<FilterListEntry> filterEntry = FilterListEntry.valueOf(componentListEntry.getAnime());
//
//      if (filterEntry.isPresent()) {
//        originalOrder.remove(componentListEntry.getAnime());
//        cmdService.executeCommand(new CmdAddFilterEntry(filterEntry.get(), app));
//        getComponentList().remove(componentListEntry.getAnime().getInfoLink());
//        showEntries();
//      }
//    });
//
//    return componentListEntry;
//  }
//
//
//  @Override
//  protected AnimeGuiComponentsListEntry addWatchListButton(final AnimeGuiComponentsListEntry componentListEntry) {
//    super.addWatchListButton(componentListEntry);
//
//    componentListEntry.getAddToWatchListButton().setOnAction(event -> {
//      final Optional<WatchListEntry> watchListEntry = WatchListEntry.valueOf(componentListEntry.getAnime());
//
//      if (watchListEntry.isPresent()) {
//        originalOrder.remove(componentListEntry.getAnime());
//        cmdService.executeCommand(new CmdAddWatchListEntry(watchListEntry.get(), app));
//        getComponentList().remove(componentListEntry.getAnime().getInfoLink());
//        showEntries();
//      }
//    });
//
//    return componentListEntry;
//  }
//
//
//  @Override
//  protected AnimeGuiComponentsListEntry addRemoveButton(final AnimeGuiComponentsListEntry componentListEntry) {
//    super.addRemoveButton(componentListEntry);
//
//    componentListEntry.getRemoveButton().setOnAction(event -> {
//      originalOrder.remove(componentListEntry.getAnime());
//      getComponentList().remove(componentListEntry.getAnime().getInfoLink());
//      showEntries();
//    });
//
//    return componentListEntry;
//  }
//}
