package commonui.textlist

import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * ViewModel used to manage [SelectableTextList].
 *
 * @param initialItems Initial items to display.
 * @param T Type of the SelectableItemUiModel that will be used to display in the list.
 */
class SelectableListViewModel<T : SelectableItemUiModel>(private var initialItems: List<T> = emptyList()) : ViewModel() {
    private val _uiModel = MutableStateFlow(SelectableListUiModel(items = initialItems, searchQuery = "", selectedItem = -1))
    val uiModel = _uiModel.asStateFlow()

    fun setItems(items: List<T>) {
        initialItems = items
        resetUiData()
    }

    fun nextItem() {
        val uiModel = _uiModel.value
        val items = uiModel.items
        if (items.isNotEmpty()) {
            val currentIndex = uiModel.selectedItem
            val addedIndex = (currentIndex + 1).coerceIn(0, items.size - 1)
            selectItem(addedIndex)
        }
    }

    fun previousItem() {
        val uiModel = _uiModel.value
        val items = uiModel.items
        if (items.isNotEmpty()) {
            val currentIndex = uiModel.selectedItem
            val addedIndex = (currentIndex - 1).coerceIn(0, items.size - 1)
            selectItem(addedIndex)
        }
    }

    fun selectItem(index: Int) {
        _uiModel.value = uiModel.value.copy(selectedItem = index)
    }

    fun getSelectedItem(): T? {
        val uiModel = uiModel.value
        val items = uiModel.items
        val selected = uiModel.selectedItem
        return null
    }

    fun search(searchQuery: String) {
        if (searchQuery == "") {
            resetUiData()
        } else {
            val filtered = initialItems.filter { it.text.lowercase().contains(searchQuery.lowercase()) }
            _uiModel.value = _uiModel.value.copy(searchQuery = searchQuery, items = filtered, selectedItem = 0)
        }
    }

    private fun resetUiData() {
        _uiModel.value = _uiModel.value.copy(searchQuery = "", items = initialItems, selectedItem = 0)
    }
}

data class SelectableListUiModel<T : SelectableItemUiModel>(
    val items: List<T>,
    val selectedItem: Int = -1,
    val searchQuery: String,
)

abstract class SelectableItemUiModel(val text: String, val disabledOrHiddenItem: Boolean = false)