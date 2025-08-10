package commonui.textlist

import androidx.compose.foundation.Image
import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import commonui.SelectableText
import commonui.SurfaceText
import gamelistconverter.composeapp.generated.resources.Res
import gamelistconverter.composeapp.generated.resources.search
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import ktx.thinOutline
import org.jetbrains.compose.resources.stringResource

/**
 * List of selectable text elements. Supports keyboard input for scroll up and down, as well as the [VerticalScrollbar].
 *
 * @param modifier modifier The modifier for the [SelectableTextList] composable.
 * @param viewModel The view model representing the selectable list.
 * @param scrollOffset Used to determine what is the offset at which we should scroll list down or up on keyboard input. This is not the
 * solution I'm proud of, but I just could not find better solution at the moment, and believe me that I have tried. It is hard to get what
 * is the last visible item without ruining the UI performance. It works for now, so I might revisit it in the future.
 * @param T the type of [SelectableItemUiModel] that is used to display items in the list.
 */
@Composable
fun <T : SelectableItemUiModel> SelectableTextList(
    modifier: Modifier = Modifier,
    viewModel: SelectableListViewModel<T>,
    scrollOffset: Int
) {
    val uiModel by viewModel.uiModel.collectAsState()

    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    val itemList = uiModel.items
    val selectedItem = uiModel.selectedItem
    Row(modifier.thinOutline().background(MaterialTheme.colorScheme.surface)) {
        LazyColumn(
            modifier = Modifier
                .then(modifier)
                .fillMaxWidth()
                .padding(start = 0.dp),
            state = listState
        ) {
            itemsIndexed(itemList) { index, item ->
                SelectableText(
                    text = item.text,
                    isSelected = selectedItem == index,
                    onSelected = { viewModel.selectItem(index) },
                    onNext = {
                        autoScrollDownIfNeeded(selectedItem + 1, itemList, listState, scrollOffset, coroutineScope)
                        viewModel.nextItem()
                    },
                    onPrevious = {
                        autoScrollUpIfNeeded(selectedItem - 1, itemList, listState, coroutineScope)
                        viewModel.previousItem()
                    },
                    showLightText = item.disabledOrHiddenItem
                )
            }
        }
        VerticalScrollbar(adapter = rememberScrollbarAdapter(scrollState = listState), modifier = Modifier.width(14.dp).padding(horizontal = 2.dp, vertical = 1.dp))
    }
}

private fun autoScrollDownIfNeeded(
    selectedItem: Int,
    itemList: List<SelectableItemUiModel>,
    listState: LazyListState,
    scrollOffset: Int,
    coroutineScope: CoroutineScope
) {
    val correctedSelectedItem = selectedItem.coerceIn(0, itemList.size - 1)
    if (correctedSelectedItem >= 0 && itemList.isNotEmpty()) {
        val firstVisible = listState.firstVisibleItemIndex
        val selectedInRange = (correctedSelectedItem - scrollOffset).coerceIn(0, itemList.size - 1)
        val shouldScrollDown = (correctedSelectedItem >= (firstVisible + scrollOffset))

        if (shouldScrollDown) {
            coroutineScope.launch {
                listState.scrollToItem(selectedInRange, scrollOffset = 0)
            }
        } else if (firstVisible + scrollOffset - correctedSelectedItem > scrollOffset) {
            coroutineScope.launch {
                listState.scrollToItem(correctedSelectedItem)
            }
        }
    }
}

private fun autoScrollUpIfNeeded(
    selectedItem: Int,
    itemList: List<SelectableItemUiModel>,
    listState: LazyListState,
    coroutineScope: CoroutineScope
) {
    val correctedSelectedItem = selectedItem.coerceIn(0, itemList.size - 1)
    if (correctedSelectedItem >= 0 && itemList.isNotEmpty()) {
        val firstVisible = listState.firstVisibleItemIndex
        val shouldScrollUp = correctedSelectedItem < firstVisible

        if (shouldScrollUp) {
            coroutineScope.launch {
                listState.scrollToItem(correctedSelectedItem)
            }
        }
    }
}

/**
 * Composable that contains search field and [SelectableTextList].
 *
 * @param modifier modifier The modifier for the [SelectableTextList] composable.
 * @param viewModel The view model representing the selectable list.
 * @param scrollOffset Used to determine what is the offset at which we should scroll list down or up on keyboard input. More info on this
 * monstrosity can be found in the documentation for the [SelectableTextList]
 * @param T the type of [SelectableItemUiModel] that is used to display items in the list.
 */
@Composable
fun <T : SelectableItemUiModel> SearchableTextList(
    modifier: Modifier,
    viewModel: SelectableListViewModel<T>,
    scrollOffset: Int,
    listTitle: String
) {
    val uiModel by viewModel.uiModel.collectAsState()
    SearchFragment(currentSearchQuery = uiModel.searchQuery, onSearchQuery = viewModel::search)
    Spacer(modifier = Modifier.height(4.dp))
    if (listTitle.isNotEmpty()) {
        Text(listTitle, color = MaterialTheme.colorScheme.onSurface)
    }
    SelectableTextList(modifier, viewModel, scrollOffset)
}

@Composable
private fun SearchFragment(currentSearchQuery: String, onSearchQuery: (String) -> Unit) {
    val searchQueryEmpty = currentSearchQuery.isEmpty()
    Column(modifier = Modifier.padding(start = 0.dp)) {
        SurfaceText(text = stringResource(Res.string.search))
        Row(modifier = Modifier.thinOutline().height(35.dp), verticalAlignment = Alignment.CenterVertically) {
            Spacer(modifier = Modifier.width(8.dp))
            //Image(Icons.Default.Search, contentDescription = null, colorFilter = ColorFilter.tint(MaterialTheme.colors.onSurface))
            BasicTextField(
                value = currentSearchQuery,
                onValueChange = onSearchQuery,
                modifier = Modifier
                    .weight(1.0f)
                    .padding(8.dp),
                textStyle = TextStyle.Default.copy(color = MaterialTheme.colorScheme.onSurface)
            )

            IconButton(onClick = { if (!searchQueryEmpty) onSearchQuery("") }, enabled = !searchQueryEmpty) {
                Image(
                    imageVector = Icons.Default.Clear,
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(if (searchQueryEmpty) Color.LightGray else MaterialTheme.colorScheme.onSurface)
                )
            }
        }
    }
}