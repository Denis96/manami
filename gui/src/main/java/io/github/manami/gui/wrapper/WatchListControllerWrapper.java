package io.github.manami.gui.wrapper;

import static io.github.manami.gui.utility.DialogLibrary.showExceptionDialog;

import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.Subscribe;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.layout.Pane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//public class WatchListControllerWrapper {
//
//  private static final Logger log = LoggerFactory.getLogger(WatchListControllerWrapper.class);
//  private Tab watchListTab;
//  private WatchListController watchListController;
//
//
//  private void init() {
//    watchListTab = new Tab(WATCH_LIST_TITLE);
//    watchListTab.setOnSelectionChanged(event -> {
//      if (watchListTab.isSelected()) {
//        watchListController.showEntries();
//      }
//    });
//
//    Parent pane;
//    try {
//      final FXMLLoader fxmlLoader = new FXMLLoader(new ClassPathResource("io/github/manami/gui/controller/watch_list_tab.fxml").getURL());
//      pane = (Pane) fxmlLoader.load();
//      watchListController = fxmlLoader.getController();
//      watchListTab.setContent(pane);
//    } catch (final Exception e) {
//      log.error("An error occurred while trying to initialize watch list tab: ", e);
//      showExceptionDialog(e);
//    }
//  }
//
//
//  @Subscribe
//  public void changeEvent(final OpenedFileChangedEvent event) {
//    if (watchListController == null) {
//      init();
//    }
//
//    watchListController.clear();
//  }
//
//
//  @Subscribe
//  @AllowConcurrentEvents
//  public void changeEvent(final AnimeListChangedEvent event) {
//    if (watchListController == null) {
//      init();
//    }
//
//    if (watchListTab.isSelected()) {
//      watchListController.showEntries();
//    }
//  }
//
//
//  /**
//   * @return the filterTab
//   */
//  public Tab getWatchListTab() {
//    if (watchListTab == null) {
//      init();
//    }
//
//    return watchListTab;
//  }
//}
