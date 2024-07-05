import java.io.File

sealed class LoadReadResult {
    data class Success(val data: List<File>) : LoadReadResult()
    data object EmptyList : LoadReadResult()

    fun onSuccess(block: (List<File>) -> Unit): LoadReadResult {
        if (this is Success) {
            block(this.data)
        }
        return this
    }

    fun onEmptyList(block: () -> Unit): LoadReadResult {
        if (this is EmptyList) {
            block()
        }
        return this
    }
}