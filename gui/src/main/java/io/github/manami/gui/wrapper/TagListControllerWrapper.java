package io.github.manami.gui.wrapper;

import static io.github.manami.gui.controller.TagListController.TAG_LIST_TITLE;
import static io.github.manami.gui.utility.DialogLibrary.showExceptionDialog;

import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.Subscribe;

import io.github.manami.dto.events.AnimeListChangedEvent;
import io.github.manami.dto.events.OpenedFileChangedEvent;
import io.github.manami.gui.controller.TagListController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.layout.Pane;

/**
 * @author manami-project
 * @since 2.8.0
 */
@Named
public class TagListControllerWrapper {

    private static final Logger log = LoggerFactory.getLogger(TagListControllerWrapper.class);
    private Tab tagListTab;
    private TagListController tagListController;


    /**
     * @since 2.7.2
     */
    private void init() {
        tagListTab = new Tab(TAG_LIST_TITLE);
        tagListTab.setOnSelectionChanged(event -> {
            if (tagListTab.isSelected()) {
                tagListController.showEntries();
            }
        });

        Parent pane;
        try {
            final FXMLLoader fxmlLoader = new FXMLLoader(new ClassPathResource("io/github/manami/gui/controller/tag_list_tab.fxml").getURL());
            pane = (Pane) fxmlLoader.load();
            tagListController = fxmlLoader.getController();
            tagListController.setTab(tagListTab);
            tagListTab.setContent(pane);
        } catch (final Exception e) {
            log.error("An error occurred while trying to initialize watch list tab: ", e);
            showExceptionDialog(e);
        }
    }


    @Subscribe
    public void changeEvent(final OpenedFileChangedEvent event) {
        if (tagListController == null) {
            init();
        }

        tagListController.clear();
    }


    @Subscribe
    @AllowConcurrentEvents
    public void changeEvent(final AnimeListChangedEvent event) {
        if (tagListController == null) {
            init();
        }

        if (tagListTab.isSelected()) {
            tagListController.showEntries();
        }
    }


    /**
     * @since 2.7.2
     * @return the filterTab
     */
    public Tab getTagListTab() {
        if (tagListTab == null) {
            init();
        }

        return tagListTab;
    }
}
