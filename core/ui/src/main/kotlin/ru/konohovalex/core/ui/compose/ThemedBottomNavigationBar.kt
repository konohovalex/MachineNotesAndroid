package ru.konohovalex.core.ui.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.rememberNavController
import ru.konohovalex.core.design.model.Theme
import ru.konohovalex.core.ui.R
import ru.konohovalex.core.ui.extension.unwrap
import ru.konohovalex.core.ui.model.BottomNavigationItem
import ru.konohovalex.core.ui.model.ButtonData
import ru.konohovalex.core.ui.model.ImageWrapper
import ru.konohovalex.core.ui.model.Position
import ru.konohovalex.core.ui.model.TextWrapper

/** To use [BottomNavigationBarPosition] inheritors, which instances can be unambiguously compared, is a recommended solution.
 * Description for the item can be set as [Position.Image.imageWrapper]'s [ImageWrapper.contentDescription] */
@Composable
fun ThemedBottomNavigationBar(
    bottomNavigationItems: List<BottomNavigationItem>,
    selectedBottomNavigationItem: BottomNavigationItem,
    navController: NavController,
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth(),
        color = Theme.palette.backgroundColor,
        elevation = Theme.materialElevations.bottomNavigationBar,
    ) {
        Row(
            modifier = Modifier
                .padding(PaddingValues(vertical = Theme.paddings.bottomNavigationBarVertical)),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            val selectedBottomNavigationItemState = remember {
                mutableStateOf(selectedBottomNavigationItem)
            }

            val onBottomNavigationItemSelected = remember {
                { bottomNavigationItem: BottomNavigationItem ->
                    val isSelectedPosition = bottomNavigationItem == selectedBottomNavigationItemState.value
                    selectedBottomNavigationItemState.value = bottomNavigationItem
                    if (!isSelectedPosition) navController.performBottomNavigation(bottomNavigationItem)
                }
            }

            bottomNavigationItems.forEach {
                BottomNavigationBarPosition(
                    bottomNavigationItem = it,
                    selectedBottomNavigationItemState = selectedBottomNavigationItemState,
                    onBottomNavigationItemSelected = onBottomNavigationItemSelected,
                )
            }
        }
    }
}

@Composable
private fun BottomNavigationBarPosition(
    bottomNavigationItem: BottomNavigationItem,
    selectedBottomNavigationItemState: State<BottomNavigationItem>,
    onBottomNavigationItemSelected: (BottomNavigationItem) -> Unit,
) {
    val content = mutableListOf<ButtonData.Content>(
        ButtonData.Content.Image(imageWrapper = bottomNavigationItem.imageWrapper)
    ).apply {
        val contentDescription = bottomNavigationItem.imageWrapper.contentDescription
        contentDescription
            ?.unwrap()
            ?.takeIf { it.isNotBlank() }
            ?.let {
                add(ButtonData.Content.Text(textWrapper = contentDescription))
            }
    }
    val contentState = remember {
        mutableStateOf(content)
    }
    contentState.value = content

    val onClick = remember {
        {
            onBottomNavigationItemSelected.invoke(bottomNavigationItem)
        }
    }

    ThemedButton(
        buttonData = ButtonData.Outlined(
            onClick = onClick,
            content = contentState.value,
            contentArrangement = ButtonData.ContentArrangement.VERTICAL,
            contentSpacing = Theme.paddings.contentExtraSmall,
            selected = bottomNavigationItem == selectedBottomNavigationItemState.value,
        )
    )
}

private fun NavController.performBottomNavigation(bottomNavigationItem: BottomNavigationItem) {
    navigate(bottomNavigationItem.navigationRoute) {
        // tbd something is wrong
        launchSingleTop = true
        popUpTo(graph.findStartDestination().id) {
            saveState = true
        }
        restoreState = true
    }
}

private sealed class PreviewBottomNavigationItem(
    override val navigationRoute: String = "",
    override val imageWrapper: ImageWrapper,
) : BottomNavigationItem {
    object NoteList : PreviewBottomNavigationItem(
        imageWrapper = ImageWrapper.ImageResource(resourceId = R.drawable.ic_notes),
    )

    object Preferences : PreviewBottomNavigationItem(
        imageWrapper = ImageWrapper.ImageResource(
            resourceId = R.drawable.ic_preferences,
            contentDescription = TextWrapper.PlainText(value = "Настройки"),
        ),
    )

    object Profile : PreviewBottomNavigationItem(
        imageWrapper = ImageWrapper.ImageResource(resourceId = R.drawable.ic_profile),
    )
}

@Preview
@Composable
private fun ThemedBottomAppBarPreview() {
    Theme {
        ThemedBottomNavigationBar(
            bottomNavigationItems = listOf(
                PreviewBottomNavigationItem.NoteList,
                PreviewBottomNavigationItem.Preferences,
                PreviewBottomNavigationItem.Profile,
            ),
            selectedBottomNavigationItem = PreviewBottomNavigationItem.NoteList,
            navController = rememberNavController(),
        )
    }
}
