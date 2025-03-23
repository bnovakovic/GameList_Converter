package ktx

import coil3.compose.AsyncImagePainter
import enums.MediaLoadState

fun AsyncImagePainter.State.toMediaLoadState(): MediaLoadState {
    return when (this) {
        AsyncImagePainter.State.Empty,
        is AsyncImagePainter.State.Error -> MediaLoadState.ERROR
        is AsyncImagePainter.State.Loading -> MediaLoadState.LOADING
        is AsyncImagePainter.State.Success -> MediaLoadState.SUCCESS
    }
}