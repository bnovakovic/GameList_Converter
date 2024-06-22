import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.type
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import app.AppViewModel
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import gamelistconverter.composeapp.generated.resources.Res
import gamelistconverter.composeapp.generated.resources.app_name
import gamelistconverter.composeapp.generated.resources.launcher_icon_1
import menus.mainscreen.MainWindowMenuBar
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import screens.mainscreen.MainScreen
import theme.GameListTheme
import theme.basicColorProvider
import theme.extendedColorsProvider
import java.io.File

fun main() {
    nonComposeCrashHandler()
    application {
        val state = rememberWindowState(size = DpSize(1280.dp, 760.dp), position = WindowPosition.Aligned(Alignment.Center))
        val appViewModel = getViewModel(Unit, viewModelFactory { AppViewModel(onRequestApplicationClose = ::exitApplication) })
        Window(
            onCloseRequest = ::exitApplication,
            title = stringResource(Res.string.app_name),
            resizable = false,
            state = state,
            onKeyEvent = {
                if (it.type == KeyEventType.KeyDown && it.key == Key.Escape) {
                    appViewModel.goBack()
                    return@Window true
                }
                return@Window false
            },
            icon = painterResource(Res.drawable.launcher_icon_1)
        ) {
            val uiModel by appViewModel.uiModel.collectAsState()
            val inDarkMode = uiModel.inDarkMode
            GameListTheme(
                extendedColors = extendedColorsProvider(inDarkMode),
                basicColors = basicColorProvider(inDarkMode)
            ) {
                MainWindowMenuBar(
                    onExitApplication = ::exitApplication,
                    enabled = !uiModel.workInProgress,
                    viewModel = appViewModel.mainMenuViewModel
                )
                MainScreen(appViewModel)
            }
        }
    }
}

private fun nonComposeCrashHandler() {
    Thread.setDefaultUncaughtExceptionHandler { _, e ->
        val message = e.message
        val stacktrace = e.stackTraceToString()

        val combined = "Message: $message\n\n $stacktrace"
        val currentTime = System.currentTimeMillis()
        val crashFile = File("Crash_$currentTime.log")
        crashFile.writeText(combined)
    }
}