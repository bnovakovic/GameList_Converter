import java.io.File

sealed class LoadReadResult {
    data class Success(val data: List<File>) : LoadReadResult()
    data object EmptyList : LoadReadResult()

    fun onSuccess(block: (List<File>) -> Unit) {
        if (this is Success) {
            block(this.data)
        }
    }

    fun onEmptyList(block: () -> Unit) {
        if (this is EmptyList) {
            block()
        }
    }
}