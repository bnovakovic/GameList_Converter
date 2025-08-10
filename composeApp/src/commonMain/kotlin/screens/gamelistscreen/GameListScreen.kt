@file:OptIn(ExperimentalResourceApi::class)

package screens.gamelistscreen

import MAIN_SCREEN_GAME_LIST_OFFSET
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import commonui.DropDownMenu
import commonui.InfoText
import commonui.InfoWithTitle
import commonui.SurfaceText
import commonui.textlist.SearchableTextList
import enums.MediaLoadState
import gamelistconverter.composeapp.generated.resources.Res
import gamelistconverter.composeapp.generated.resources.date
import gamelistconverter.composeapp.generated.resources.description
import gamelistconverter.composeapp.generated.resources.developer
import gamelistconverter.composeapp.generated.resources.export
import gamelistconverter.composeapp.generated.resources.favorite
import gamelistconverter.composeapp.generated.resources.game_name
import gamelistconverter.composeapp.generated.resources.genre
import gamelistconverter.composeapp.generated.resources.hidden
import gamelistconverter.composeapp.generated.resources.image_not_found
import gamelistconverter.composeapp.generated.resources.kid_game
import gamelistconverter.composeapp.generated.resources.number_of_games
import gamelistconverter.composeapp.generated.resources.players
import gamelistconverter.composeapp.generated.resources.publisher
import gamelistconverter.composeapp.generated.resources.rating
import gamelistconverter.composeapp.generated.resources.region
import gamelistconverter.composeapp.generated.resources.select_game
import gamelistconverter.composeapp.generated.resources.select_system
import gamelistconverter.composeapp.generated.resources.system_name
import ktx.thinOutline
import ktx.toMediaLoadState
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import screens.gamelistscreen.data.GameInfoUiModel
import screens.gamelistscreen.data.GameSystemUiModel
import screens.gamelistscreen.mappers.toResourceString
import java.io.File

/**
 * Composable used to display List of systems and games.
 *
 * @param viewModel ViewModel used to control this screen.
 */
@Composable
fun GameListScreen(viewModel: GameListScreenViewModel) {
    val uiModel by viewModel.uiModel.collectAsState()
    val activeSystem = uiModel.activeSystemInfo
    Row(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.surface)) {
        RomsInfo(
            modifier = Modifier.weight(1.0f).fillMaxHeight(),
            viewModel = viewModel
        )
        GameAndSystemInfo(
            modifier = Modifier.weight(1.0f).fillMaxHeight(),
            gameInfo = uiModel.activeGameInfo,
            onExportClicked = viewModel::switchToExportScreen,
            systemInfo = activeSystem
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RomsInfo(modifier: Modifier, viewModel: GameListScreenViewModel) {
    val uiModel by viewModel.uiModel.collectAsState()
    Column(modifier = Modifier.then(modifier).padding(8.dp)) {
        Row(modifier = Modifier.fillMaxWidth().wrapContentHeight()) {
            DropdownItems(
                dropDownItems = uiModel.gameSystemDisplayList,
                selectedItem = uiModel.selectedSystem,
                onItemSelected = { viewModel.systemSelected(it) },
            )
        }
        SearchableTextList(
            modifier = Modifier.weight(1.0f),
            viewModel = viewModel.selectableGameListViewModel,
            scrollOffset = MAIN_SCREEN_GAME_LIST_OFFSET,
            listTitle = stringResource(Res.string.select_game)
        )
        Spacer(modifier = Modifier.height(8.dp))
        val activeGameInfo = uiModel.activeGameInfo
        InfoText(activeGameInfo.romPath, modifier = Modifier.fillMaxWidth().padding(start = 0.dp).basicMarquee())
    }
}

@Composable
private fun DropdownItems(
    dropDownItems: List<String>,
    selectedItem: Int,
    onItemSelected: (Int) -> Unit,
) {
    Column {
        var selectedIndex by remember { mutableIntStateOf(selectedItem) }
        SurfaceText(
            text = stringResource(Res.string.select_system),
            modifier = Modifier.padding(start = 0.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        DropDownMenu(
            selectedValue = selectedIndex,
            items = dropDownItems,
            onItemSelected = {
                selectedIndex = it
                onItemSelected(it)
            },
            modifier = Modifier.fillMaxWidth().height(30.dp).padding(start = 6.dp)
        )
    }
}

@Composable
private fun GameAndSystemInfo(modifier: Modifier, gameInfo: GameInfoUiModel, onExportClicked: () -> Unit, systemInfo: GameSystemUiModel) {

    Column(modifier = Modifier.then(modifier).padding(8.dp)) {
        SystemAndImageContainer(gameInfo.imagePath, systemInfo)
        Spacer(modifier = Modifier.height(4.dp))
        Row {
            InfoWithTitle(stringResource(Res.string.game_name), gameInfo.gameName, containerModifier = Modifier.weight(0.7f))
            Spacer(modifier = Modifier.width(8.dp))
            InfoWithTitle(stringResource(Res.string.region), gameInfo.region, containerModifier = Modifier.weight(0.3f))
            Spacer(modifier = Modifier.width(8.dp))
            InfoWithTitle(stringResource(Res.string.players), gameInfo.players, containerModifier = Modifier.weight(0.2f))
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row {
            InfoWithTitle(
                title = stringResource(Res.string.date),
                gameInfo.date,
                containerModifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            InfoWithTitle(
                title = stringResource(Res.string.rating),
                gameInfo.rating,
                containerModifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            InfoWithTitle(
                title = stringResource(Res.string.kid_game),
                gameInfo.kidGame.toResourceString(),
                containerModifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            InfoWithTitle(
                title = stringResource(Res.string.hidden),
                gameInfo.hidden.toResourceString(),
                containerModifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            InfoWithTitle(
                title = stringResource(Res.string.favorite),
                gameInfo.favorite.toResourceString(),
                containerModifier = Modifier.weight(1f)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row {
            InfoWithTitle(stringResource(Res.string.publisher), gameInfo.publisher, containerModifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.width(8.dp))
            InfoWithTitle(stringResource(Res.string.developer), gameInfo.developer, containerModifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.width(8.dp))
            InfoWithTitle(stringResource(Res.string.genre), gameInfo.genre, containerModifier = Modifier.weight(1f))
        }
        Spacer(modifier = Modifier.height(8.dp))
        InfoWithTitle(
            title = stringResource(Res.string.description),
            info = gameInfo.description,
            containerModifier = Modifier.weight(1.0f),
            textModifier = Modifier.fillMaxHeight(),
            useMarquee = false
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row {
            Spacer(modifier = Modifier.weight(1.0f))
            Spacer(modifier = Modifier.weight(1.0f))
            Button(onClick = onExportClicked, modifier = Modifier.weight(1.0f)) {
                Text(stringResource(Res.string.export))
            }
        }
    }
}

@Composable
private fun SystemAndImageContainer(imagePath: File?, systemInfo: GameSystemUiModel) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .height(300.dp)
                .weight(1.0f),
            contentAlignment = Alignment.Center
        ) {
            Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Bottom) {
                InfoWithTitle(stringResource(Res.string.system_name), systemInfo.system.name)
                Spacer(modifier = Modifier.height(8.dp))
                InfoWithTitle(stringResource(Res.string.number_of_games), systemInfo.games.size.toString())
            }
        }
        Spacer(modifier = Modifier.width(16.dp))
        Box(
            modifier = Modifier
                .height(300.dp)
                .weight(1.0f)
                .thinOutline(),
            contentAlignment = Alignment.Center
        ) {
            var imageLoadState by remember { mutableStateOf(MediaLoadState.LOADING) }
            AsyncImage(
                model = imagePath,
                contentDescription = null,
                onState = { imageLoadState = it.toMediaLoadState() }
            )

            when (imageLoadState) {
                MediaLoadState.LOADING -> {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                }
                MediaLoadState.ERROR -> {
                    SurfaceText(stringResource(Res.string.image_not_found))
                }
                MediaLoadState.SUCCESS -> { /* Show nothing when image is loaded */ }
            }
        }
    }
}