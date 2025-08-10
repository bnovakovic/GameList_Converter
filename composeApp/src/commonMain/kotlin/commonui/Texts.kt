package commonui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import ktx.thinOutline
import theme.GameListTheme
import theme.GlText

/**
 * Composable used to display information text surrounded by rectangle.
 *
 * @param text Text to show.
 * @param modifier Modifier used to modify the text.
 * @param useMarquee Marquee used to scroll the text if it does not fit.
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun InfoText(text: String, modifier: Modifier = Modifier, useMarquee: Boolean = true) {
    val additionalModifier = if (useMarquee) Modifier.basicMarquee() else Modifier
    Box(modifier.thinOutline().padding(start = 4.dp, end = 2.dp, top = 2.dp, bottom = 2.dp)) {
        BasicTextField(
            readOnly = true,
            value = text,
            modifier = modifier.then(additionalModifier),
            onValueChange = {},
            textStyle = GlText.InfoTextStyle.copy(color = MaterialTheme.colorScheme.onSurface)
        )

    }
}

/**
 * Just a regular text. Had to add it to make sure correct style is applied.
 *
 * @param text Text to show.
 * @param modifier Modifier used to modify the text properties.
 * @param textAlign Alignment for the text.
 * @param style Style to use with the text.
 */
@Composable
fun SurfaceText(
    text: String,
    modifier: Modifier = Modifier,
    textAlign: TextAlign? = null,
    style: TextStyle = GlText.TextOnSurfaceStyle,
) {
    Text(
        text = text,
        style = style,
        color = MaterialTheme.colorScheme.onSurface,
        modifier = modifier,
        textAlign = textAlign
    )
}

@Composable
fun ErrorSurfaceText(
    text: String,
    modifier: Modifier = Modifier,
    textAlign: TextAlign? = null,
    style: TextStyle = GlText.TextOnSurfaceStyle,
) {
    Text(
        text = text,
        style = style,
        color = MaterialTheme.colorScheme.error,
        modifier = modifier,
        textAlign = textAlign
    )
}

/**
 * Composable used to show the info text with title above it.
 *
 * @param title Title text.
 * @param info Info text.
 * @param containerModifier Modifier for the container.
 * @param titleModifier Modifier for the title.
 * @param textModifier Modifier for the text.
 * @param useMarquee Marquee used to scroll the text if it does not fit.
 */
@Composable
fun InfoWithTitle(
    title: String,
    info: String,
    containerModifier: Modifier = Modifier,
    titleModifier: Modifier = Modifier,
    textModifier: Modifier = Modifier,
    useMarquee: Boolean = true
) {
    Column(modifier = containerModifier) {
        SurfaceText(text = title, modifier = titleModifier)
        InfoText(text = info, modifier = Modifier.then(textModifier).fillMaxWidth(), useMarquee = useMarquee)
    }
}

/**
 * Composable used to display selectable text.
 *
 * @param text Text to display.
 * @param isSelected True if text should be selected, false if not.
 * @param onSelected Callback used to notify when text is selected.
 * @param onNext Callback that is invoked when keyboard down is pressed.
 * @param onPrevious Callback that is invoked when keyboard up is pressed.
 * @param showLightText True if text should have light color, false if it should show regular color
 */
@Composable
fun SelectableText(
    text: String,
    isSelected: Boolean,
    onSelected: () -> Unit,
    onNext: () -> Unit,
    onPrevious: () -> Unit,
    showLightText: Boolean,
) {

    Text(
        text = text,
        modifier = Modifier
            .background(if (isSelected) GameListTheme.colors.listSelection else Color.Transparent)
            .padding(start = 4.dp)
            .onKeyEvent { event ->
                if (event.key == Key.DirectionUp && event.type == KeyEventType.KeyDown) {
                    onPrevious()
                    return@onKeyEvent true
                } else if (event.key == Key.DirectionDown && event.type == KeyEventType.KeyDown) {
                    onNext()
                    return@onKeyEvent true
                }
                return@onKeyEvent false
            }

            .clickable { onSelected() }
            .focusable(true)
            .fillMaxWidth(),
        style = TextStyle(color = if (showLightText) GameListTheme.colors.listDisabledText else MaterialTheme.colorScheme.onSurface),
    )
}

// Thanks Stevdza San!!!
// https://gist.github.com/stevdza-san/ff9dbec0e072d8090e1e6d16e6b73c91
@Composable
fun HyperlinkText(
    modifier: Modifier = Modifier,
    fullText: String,
    linkText: List<String>,
    linkTextColor: Color = MaterialTheme.colorScheme.secondary,
    linkTextFontWeight: FontWeight = FontWeight.Medium,
    linkTextDecoration: TextDecoration = TextDecoration.Underline,
    hyperlinks: List<String>,
    fontSize: TextUnit = TextUnit.Unspecified
) {
    val annotatedString = buildAnnotatedString {
        append(fullText)
        linkText.forEachIndexed { index, link ->
            val startIndex = fullText.indexOf(link)
            val endIndex = startIndex + link.length
            addStyle(
                style = SpanStyle(
                    color = linkTextColor,
                    fontSize = fontSize,
                    fontWeight = linkTextFontWeight,
                    textDecoration = linkTextDecoration
                ),
                start = startIndex,
                end = endIndex
            )
            addStringAnnotation(
                tag = "URL",
                annotation = hyperlinks[index],
                start = startIndex,
                end = endIndex
            )
        }
        addStyle(
            style = SpanStyle(
                fontSize = fontSize
            ),
            start = 0,
            end = fullText.length
        )
    }

    val uriHandler = LocalUriHandler.current

    ClickableText(
        modifier = modifier,
        text = annotatedString,
        onClick = {
            annotatedString
                .getStringAnnotations("URL", it, it)
                .firstOrNull()?.let { stringAnnotation ->
                    uriHandler.openUri(stringAnnotation.item)
                }
        },
        style = GlText.TextOnSurfaceStyle.copy(color = MaterialTheme.colorScheme.onSurface, textAlign = TextAlign.Center, fontSize = TextUnit(14.0f, TextUnitType.Sp),)
    )
}