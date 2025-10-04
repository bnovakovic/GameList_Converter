package menus.mainscreen

import ENGLISH_LANGUAGE_CODE
import HELP_LINK
import LANGUAGE_ENGLISH
import LANGUAGE_SERBIAN
import SERBIAN_LANGUAGE_CODE
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyShortcut
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.window.FrameWindowScope
import androidx.compose.ui.window.MenuBar
import androidx.compose.ui.window.MenuBarScope
import gamelistconverter.composeapp.generated.resources.Res
import gamelistconverter.composeapp.generated.resources.about
import gamelistconverter.composeapp.generated.resources.about_24dp
import gamelistconverter.composeapp.generated.resources.dark
import gamelistconverter.composeapp.generated.resources.directories_setup
import gamelistconverter.composeapp.generated.resources.exit
import gamelistconverter.composeapp.generated.resources.exit_24dp
import gamelistconverter.composeapp.generated.resources.file
import gamelistconverter.composeapp.generated.resources.help
import gamelistconverter.composeapp.generated.resources.help_24
import gamelistconverter.composeapp.generated.resources.language
import gamelistconverter.composeapp.generated.resources.light
import gamelistconverter.composeapp.generated.resources.restart_required
import gamelistconverter.composeapp.generated.resources.retroarch
import gamelistconverter.composeapp.generated.resources.rom_24
import gamelistconverter.composeapp.generated.resources.scan
import gamelistconverter.composeapp.generated.resources.scan_all
import gamelistconverter.composeapp.generated.resources.scan_retroarch_dir
import gamelistconverter.composeapp.generated.resources.scan_roms_dir
import gamelistconverter.composeapp.generated.resources.search_24dp
import gamelistconverter.composeapp.generated.resources.theme
import gamelistconverter.composeapp.generated.resources.xml_icon_24
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

/**
 * Menu bar for the main screen.
 *
 *  @param onExitApplication Callback invoked when we need to exit the app.
 *  @param enabled true if menu should be enabled, false if not.
 *  @param viewModel ViewModel used to control the menu.
 */
@Composable
fun FrameWindowScope.MainWindowMenuBar(
    onExitApplication: () -> Unit,
    enabled: Boolean,
    viewModel: MainScreenMenuViewModel
) {
    val uiModel by viewModel.uiModel.collectAsState()
    MenuBar {
        FileMenu(
            menuItemSelected = viewModel::menuSelected,
            onExitApplication = onExitApplication,
            enabled = enabled,
        )
        Scan(
            menuItemSelected = viewModel::menuSelected,
            enabled = enabled,
            retroArchDirSet = viewModel.isRetroArchDirSet(),
            gameListDirSet = viewModel.isGameListDirSet()
        )
        Language(
            menuItemSelected = viewModel::menuSelected,
            enabled = enabled,
            currentSelected = uiModel.selectedLanguage
        )
        Theme(
            menuItemSelected = viewModel::menuSelected,
            enabled = enabled,
            inDarkMode = uiModel.inDarkMode
        )
        Help(menuItemSelected = viewModel::menuSelected, enabled = enabled)
    }
}

@Composable
private fun MenuBarScope.FileMenu(
    menuItemSelected: (MainWindowMenuSelection) -> Unit,
    onExitApplication: () -> Unit,
    enabled: Boolean,
) {
    Menu(stringResource(Res.string.file), mnemonic = 'F', enabled = enabled) {
        val dirsString = stringResource(Res.string.directories_setup)
        Item(
            text = dirsString,
            onClick = { menuItemSelected(MainWindowMenuSelection.DirectorySetup) },
            shortcut = KeyShortcut(Key.D, ctrl = true),
            mnemonic = 'D',
            icon = painterResource(Res.drawable.rom_24)
        )
        Item(
            text = stringResource(Res.string.exit),
            onClick = onExitApplication,
            shortcut = KeyShortcut(Key.F4, alt = true),
            mnemonic = 'X',
            icon = painterResource(Res.drawable.exit_24dp)
        )
    }
}

@Composable
private fun MenuBarScope.Scan(
    menuItemSelected: (MainWindowMenuSelection) -> Unit,
    enabled: Boolean,
    retroArchDirSet: Boolean,
    gameListDirSet: Boolean
) {
    val anySet = retroArchDirSet || gameListDirSet

    Menu(stringResource(Res.string.scan), mnemonic = 'S', enabled = enabled && anySet) {
        Item(
            text = stringResource(Res.string.scan_roms_dir),
            onClick = { menuItemSelected(MainWindowMenuSelection.ScanRoms) },
            enabled = gameListDirSet,
            mnemonic = 'G',
            icon = painterResource(Res.drawable.xml_icon_24)
        )
        Item(
            text = stringResource(Res.string.scan_retroarch_dir),
            onClick = { menuItemSelected(MainWindowMenuSelection.ScanRetroArchDir) },
            enabled = retroArchDirSet,
            mnemonic = 'R',
            icon = painterResource(Res.drawable.retroarch)
        )
        Item(
            text = stringResource(Res.string.scan_all),
            onClick = { menuItemSelected(MainWindowMenuSelection.ScanAll) },
            enabled = anySet,
            mnemonic = 'A',
            icon = painterResource(Res.drawable.search_24dp)
        )
    }
}

@Composable
private fun MenuBarScope.Language(
    menuItemSelected: (MainWindowMenuSelection) -> Unit,
    enabled: Boolean,
    currentSelected: String,
) {
    Menu(stringResource(Res.string.language), mnemonic = 'L', enabled = enabled) {
        CheckboxItem(
            text = LANGUAGE_ENGLISH + " (${stringResource(Res.string.restart_required)})",
            enabled = enabled,
            mnemonic = 'E',
            checked = ENGLISH_LANGUAGE_CODE == currentSelected,
            onCheckedChange = { menuItemSelected(MainWindowMenuSelection.LanguageSelected(ENGLISH_LANGUAGE_CODE)) }
        )
        CheckboxItem(
            text = LANGUAGE_SERBIAN + " (${stringResource(Res.string.restart_required)})",
            onCheckedChange = { menuItemSelected(MainWindowMenuSelection.LanguageSelected(SERBIAN_LANGUAGE_CODE)) },
            enabled = enabled,
            mnemonic = 'S',
            checked = SERBIAN_LANGUAGE_CODE == currentSelected,
        )
    }
}

@Composable
private fun MenuBarScope.Help(
    menuItemSelected: (MainWindowMenuSelection) -> Unit,
    enabled: Boolean,
) {
    val uriHandler = LocalUriHandler.current
    Menu(stringResource(Res.string.help), mnemonic = 'H', enabled = enabled) {
        Item(
            text = stringResource(resource = Res.string.about),
            onClick = { menuItemSelected(MainWindowMenuSelection.About) },
            mnemonic = 'A',
            icon = painterResource(Res.drawable.about_24dp)
        )
        Item(
            stringResource(Res.string.help),
            onClick = {
                uriHandler.openUri(HELP_LINK)
            },
            shortcut = KeyShortcut(Key.F1),
            mnemonic = 'H',
            icon = painterResource(Res.drawable.help_24)
        )
    }
}

@Composable
private fun MenuBarScope.Theme(
    menuItemSelected: (MainWindowMenuSelection) -> Unit,
    enabled: Boolean,
    inDarkMode: Boolean
) {
    Menu(stringResource(Res.string.theme), mnemonic = 'T', enabled = enabled) {
        CheckboxItem(
            text = stringResource(resource = Res.string.light),
            onCheckedChange = { menuItemSelected(MainWindowMenuSelection.UseDarkMode(false)) },
            mnemonic = 'L',
            checked = !inDarkMode
        )
        CheckboxItem(
            text = stringResource(resource = Res.string.dark),
            onCheckedChange = { menuItemSelected(MainWindowMenuSelection.UseDarkMode(true)) },
            mnemonic = 'D',
            checked = inDarkMode
        )

    }
}