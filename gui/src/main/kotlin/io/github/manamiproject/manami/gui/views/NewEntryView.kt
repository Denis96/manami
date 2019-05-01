package io.github.manamiproject.manami.gui.views

import io.github.manamiproject.manami.common.extensions.exists
import io.github.manamiproject.manami.common.extensions.isRegularFile
import io.github.manamiproject.manami.core.Manami
import io.github.manamiproject.manami.entities.*
import io.github.manamiproject.manami.gui.components.FileChoosers.showBrowseForFolderDialog
import io.github.manamiproject.manami.gui.extensions.isValid
import javafx.beans.property.SimpleIntegerProperty
import javafx.concurrent.WorkerStateEvent
import javafx.event.EventHandler
import javafx.scene.Parent
import javafx.scene.control.Button
import javafx.scene.control.TabPane
import javafx.scene.control.TextField
import javafx.scene.input.Clipboard
import org.controlsfx.validation.ValidationResult
import org.controlsfx.validation.ValidationSupport
import org.controlsfx.validation.Validator
import tornadofx.*

private const val DEFAULT_EPISODES = "1"

class NewEntryView : View() {

    override val root = TabPane()

    private var animeTypeIndex = SimpleIntegerProperty(0)

    private val txtTitle: TextField by fxid()
    private val txtType: TextField by fxid()
    private val txtEpisodes: TextField by fxid()
    private val txtInfoLink: TextField by fxid()
    private val txtLocation: TextField by fxid()
    private val btnTypeUp: Button by fxid()
    private val btnTypeDown: Button by fxid()
    private val btnEpisodeUp: Button by fxid()
    private val btnEpisodeDown: Button by fxid()
    private val btnAdd: Button by fxid()


    private val validationSupport: ValidationSupport = ValidationSupport().apply {
        registerValidator(txtTitle, Validator.createEmptyValidator<TextField>("Title is required"))
        registerValidator(txtLocation, Validator.createEmptyValidator<TextField>("Location is required"))
        registerValidator(txtInfoLink, Validator<String> { _, value ->
            return@Validator when {
                value.isNotEmpty() && UrlValidator.isNotValid(value) -> ValidationResult.fromError(txtInfoLink, "Info link must be a valid URL")
                else -> ValidationResult()
            }
        })
    }

    init {
        initEpisodesControls()
        initAnimeTypeControls()
        initInfoLinkControls()
    }

    private fun initInfoLinkControls() {
        txtInfoLink.focusedProperty().addListener(ChangeListener<Boolean> { _, valueBefore, valueAfter ->
            run {
                val infoLink = InfoLink(txtInfoLink.text)
                val host = infoLink.url?.host

                if(infoLink.toString() != txtInfoLink.text) {
                    runLater { txtInfoLink.text = infoLink.toString() }
                }

                if(valueBefore && !valueAfter && SupportedInfoLinkDomains.values().map { it.url }.contains(host)) {
                    autoFillForm()
                }
            }
        })

        val clipboardString: String = Clipboard.getSystemClipboard().string

        if (UrlValidator.isValid(clipboardString)) {
            runLater {
                txtInfoLink.text = clipboardString
                txtInfoLink.requestFocus()
            }
        }
    }

    private fun autoFillForm() {
        disableControls(true)
        runAsync {
            Manami.fetchAnime(InfoLink(txtInfoLink.text))?.let {
                runLater {
                    txtTitle.text = it.title
                    txtEpisodes.text = it.episodes.toString()
                    animeTypeIndex.value = it.type.ordinal
                    disableControls(false)
                }
            }
        }.onFailed = EventHandler<WorkerStateEvent> {
            disableControls(false)
        }
    }

    private fun disableControls(value: Boolean) {
        runLater {
            txtTitle.isDisable = value
            txtType.isDisable = value
            txtEpisodes.isDisable = value
            btnEpisodeUp.isDisable = value
            btnEpisodeDown.isDisable = value
            txtInfoLink.isDisable = value
            btnAdd.isDisable = value

            if(!value) {
                if(animeTypeIndex.value == 0) {
                    btnTypeUp.isDisable = value
                }

                if(animeTypeIndex.value == AnimeType.values().size - 1) {
                    btnTypeDown.isDisable = value
                }
            } else {
                btnTypeUp.isDisable = value
                btnTypeDown.isDisable = value
            }

        }
    }

    private fun initAnimeTypeControls() {
        runLater { txtType.text = AnimeType.values()[animeTypeIndex.value].toString() }

        animeTypeIndex.addListener(ChangeListener<Number> { _, _, valueAfter ->
            run {
                runLater { txtType.text = AnimeType.values()[valueAfter.toInt()].value }

                when (valueAfter) {
                    0 -> runLater { btnTypeDown.isDisable = true }
                    AnimeType.values().size - 1 -> runLater { btnTypeUp.isDisable = true }
                    else -> runLater {
                        btnTypeDown.isDisable = false
                        btnTypeUp.isDisable = false
                    }
                }
            }
        })
    }

    private fun initEpisodesControls() {
        txtEpisodes.textProperty().addListener(ChangeListener<String> { _, _, valueAfter ->
            run {
                if (!valueAfter.isInt() || valueAfter.startsWith("-") || "0" == valueAfter) {
                    runLater { txtEpisodes.text = DEFAULT_EPISODES }
                }

                runLater { btnEpisodeDown.isDisable = txtEpisodes.text == DEFAULT_EPISODES }
            }
        })
    }

    fun add() {
        if(validationSupport.isValid()) {
            Manami.addAnime(Anime(txtTitle.text.trim(), InfoLink(txtInfoLink.text.trim())).apply {
                type = AnimeType.findByName(txtType.text.trim())!!
                episodes = txtEpisodes.text.trim().toInt()
                location = txtLocation.text.trim()
            })

            this.close()
        }
    }

    fun increaseEpisodes() {
        runLater { txtEpisodes.text = (txtEpisodes.text.toInt() + 1).toString() }
    }

    fun decreaseEpisodes() {
        runLater { txtEpisodes.text = (txtEpisodes.text.toInt() - 1).toString() }
    }

    fun typeUp() {
        if(animeTypeIndex.value < AnimeType.values().size - 1) {
            animeTypeIndex.value++
        }
    }

    fun typeDown() {
        if(animeTypeIndex.value > 0) {
            animeTypeIndex.value--
        }
    }

    fun browse() {
        showBrowseForFolderDialog(currentStage!!)?.let {
            val configFile = Manami.getCurrentlyOpenedFile()
            var folder = it.toAbsolutePath().toString()

            if(configFile.exists() && configFile.isRegularFile()) {
                //TODO: not working this way use PathBuilder instead
                folder = configFile.parent.relativize(it).toString().replace("\\", "/")
            }

            runLater { txtLocation.text = folder }
        }
    }
}